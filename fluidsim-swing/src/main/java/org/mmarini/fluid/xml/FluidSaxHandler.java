/**
 * 
 */
package org.mmarini.fluid.xml;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.mmarini.fluid.model.CellFunction;
import org.mmarini.fluid.model.CellModifier;
import org.mmarini.fluid.model.CoefficientFunction;
import org.mmarini.fluid.model.CompositeCellModifier;
import org.mmarini.fluid.model.CompositeModifier;
import org.mmarini.fluid.model.ConservativeFunction;
import org.mmarini.fluid.model.ConstantFunction;
import org.mmarini.fluid.model.DefaultRelationFunction;
import org.mmarini.fluid.model.DiffusionFunction;
import org.mmarini.fluid.model.ElasticFunction;
import org.mmarini.fluid.model.FluidFunction;
import org.mmarini.fluid.model.FunctionModifier;
import org.mmarini.fluid.model.IsomorphCellFunction;
import org.mmarini.fluid.model.LineModifier;
import org.mmarini.fluid.model.OscillatorFunction;
import org.mmarini.fluid.model.PointModifier;
import org.mmarini.fluid.model.RectangleModifier;
import org.mmarini.fluid.model.RelationCellModifier;
import org.mmarini.fluid.model.RelationFunction;
import org.mmarini.fluid.model.UniverseModifier;
import org.mmarini.fluid.model.ValueCellModifier;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author US00852
 * 
 */
public class FluidSaxHandler extends DefaultHandler {
	private static final String FLUID_NS = "http://www.mmarini.org/fluid-1.0.0";
	private static final ConservativeFunction CONSERVATIVE_FUNCTION = new ConservativeFunction();
	private UniverseModifier result;
	private Locator documentLocator;
	private String uriFilter;
	private Map<String, ElementHandler> handlerMap;
	private StringBuilder text;
	private Map<String, CellModifier> modifierMap;
	private String id;
	private Queue<CoefficientFunction> functionStack;
	private Queue<CellModifier> cellModifierStack;
	private Queue<Queue<CellModifier>> cellModiferListStack;
	private RelationFunction relationFunction;
	private CellFunction cellFunction;
	private Queue<UniverseModifier> modifierStack;
	private Queue<Queue<UniverseModifier>> modifierListStack;

	/**
	 * 
	 */
	public FluidSaxHandler() {
		uriFilter = FLUID_NS;
		text = new StringBuilder();
		modifierStack = Collections
				.asLifoQueue(new ArrayDeque<UniverseModifier>());
		modifierListStack = Collections
				.asLifoQueue(new ArrayDeque<Queue<UniverseModifier>>());
		functionStack = Collections
				.asLifoQueue(new ArrayDeque<CoefficientFunction>());
		cellModifierStack = Collections
				.asLifoQueue(new ArrayDeque<CellModifier>());
		cellModiferListStack = Collections
				.asLifoQueue(new ArrayDeque<Queue<CellModifier>>());
		modifierMap = new HashMap<String, CellModifier>();
		handlerMap = new HashMap<String, ElementHandler>();

		handlerMap.put("const", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				push(new ConstantFunction(parseDouble(text)));
			}
		});
		handlerMap.put("conservative", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				push(CONSERVATIVE_FUNCTION);
			}
		});
		handlerMap.put("diffusion", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				push(new DiffusionFunction(parseDouble(text)));
			}
		});
		handlerMap.put("elastic", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				push(new ElasticFunction(parseDouble(text)));
			}
		});
		handlerMap.put("fluid", new ElementHandler() {
			@Override
			public void startElement(Attributes attrs) throws SAXException {
				push(new FluidFunction(parseDoubleAttr(attrs, "speed"),
						parseDoubleAttr(attrs, "viscosity")));
			}
		});
		handlerMap.put("sin", new ElementHandler() {
			@Override
			public void startElement(Attributes attrs) throws SAXException {
				push(new OscillatorFunction(parseDoubleAttr(attrs, "value"),
						parseDoubleAttr(attrs, "period")));
			}
		});
		handlerMap.put("cell", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				push(new IsomorphCellFunction(popFunction()));
			}
		});
		handlerMap.put("relation", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				push(new DefaultRelationFunction(popFunction()));
			}
		});
		handlerMap.put("modifyCell", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				RelationFunction r = popRelation();
				CellFunction c = popCell();
				push(new FunctionModifier(c, r));
			}
		});
		handlerMap.put("modifyRelations", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				RelationFunction r5 = new DefaultRelationFunction(popFunction());
				RelationFunction r4 = new DefaultRelationFunction(popFunction());
				RelationFunction r3 = new DefaultRelationFunction(popFunction());
				RelationFunction r2 = new DefaultRelationFunction(popFunction());
				RelationFunction r1 = new DefaultRelationFunction(popFunction());
				RelationFunction r0 = new DefaultRelationFunction(popFunction());
				push(new RelationCellModifier(r0, r1, r2, r3, r4, r5));
			}
		});
		handlerMap.put("modifyValue", new ElementHandler() {
			@Override
			public void startElement(Attributes attrs) throws SAXException {
				push(new ValueCellModifier(parseDoubleAttr(attrs, "value"),
						parseDoubleAttr(attrs, "right"), parseDoubleAttr(attrs,
								"upRight"), parseDoubleAttr(attrs, "upLeft")));
			}
		});
		handlerMap.put("modifyGroup", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				push(popCellModifierList());
			}

			@Override
			public void startElement(Attributes attrs) throws SAXException {
				pushCellModifierList();
			}
		});
		handlerMap.put("def", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				modifierMap.put(popDefId(), popCellModifier());
			}

			@Override
			public void startElement(Attributes attrs) throws SAXException {
				pushDefId(attrs.getValue("id"));
			}
		});
		handlerMap.put("point", new ElementHandler() {
			@Override
			public void startElement(Attributes attrs) throws SAXException {
				push(new PointModifier(getModifier(attrs.getValue("function")),
						parseDoubleAttr(attrs, "x"),
						parseDoubleAttr(attrs, "y")));
			}
		});
		handlerMap.put("rect", new ElementHandler() {
			@Override
			public void startElement(Attributes attrs) throws SAXException {
				push(new RectangleModifier(getModifier(attrs
						.getValue("function")), parseDoubleAttr(attrs, "x0"),
						parseDoubleAttr(attrs, "y0"), parseDoubleAttr(attrs,
								"x1"), parseDoubleAttr(attrs, "y1")));
			}
		});
		handlerMap.put("line", new ElementHandler() {
			@Override
			public void startElement(Attributes attrs) throws SAXException {
				push(new LineModifier(getModifier(attrs.getValue("function")),
						parseDoubleAttr(attrs, "x0"), parseDoubleAttr(attrs,
								"y0"), parseDoubleAttr(attrs, "x1"),
						parseDoubleAttr(attrs, "y1")));
			}
		});
		handlerMap.put("group", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				push(popGroup());
			}

			@Override
			public void startElement(Attributes attrs) throws SAXException {
				pushGroup();
			}
		});
		handlerMap.put("universe", new ElementHandler() {
			@Override
			public void endElement(String text) throws SAXException {
				result = popModifier();
			}
		});
	}

	/**
	 * 
	 * @param relationFunction
	 */
	private void push(RelationFunction relationFunction) {
		this.relationFunction = relationFunction;
	}

	/**
	 * 
	 * @param cellFunction
	 */
	private void push(CellFunction cellFunction) {
		this.cellFunction = cellFunction;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] bfr, int offset, int length)
			throws SAXException {
		text.append(bfr, offset, length);
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (uriFilter.equals(uri)) {
			ElementHandler h = handlerMap.get(qName);
			if (h != null) {
				h.endElement(text.toString());
			}
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#error(org.xml.sax.SAXParseException)
	 */
	@Override
	public void error(SAXParseException e) throws SAXException {
		throw e;
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @throws SAXParseException
	 */
	private CellModifier getModifier(String key) throws SAXParseException {
		CellModifier m = modifierMap.get(key);
		if (m == null)
			throw new SAXParseException("function \"" + key
					+ "\" not found at (" + documentLocator.getLineNumber()
					+ ", " + documentLocator.getColumnNumber(), documentLocator);
		return m;
	}

	/**
	 * @return the result
	 */
	public UniverseModifier getResult() {
		return result;
	}

	/**
	 * 
	 * @param text
	 * @return
	 * @throws SAXParseException
	 */
	private double parseDouble(String text) throws SAXParseException {
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException e) {
			throw new SAXParseException(e.getMessage(), documentLocator, e);
		}
	}

	/**
	 * 
	 * @param attrs
	 * @param key
	 * @return
	 * @throws SAXParseException
	 */
	private double parseDoubleAttr(Attributes attrs, String key)
			throws SAXParseException {
		return parseDouble(attrs.getValue(key));
	}

	/**
	 * 
	 * @return
	 */
	private CellFunction popCell() {
		return cellFunction;
	}

	/**
	 * 
	 * @return
	 */
	private CellModifier popCellModifier() {
		return cellModifierStack.poll();
	}

	/**
	 * 
	 * @return
	 */
	private CellModifier popCellModifierList() {
		List<CellModifier> list = new ArrayList<CellModifier>(cellModifierStack);
		Collections.reverse(list);
		cellModifierStack = cellModiferListStack.poll();
		return new CompositeCellModifier(list);
	}

	/**
	 * 
	 * @return
	 */
	private String popDefId() {
		return id;
	}

	/**
	 * 
	 * @return
	 */
	private CoefficientFunction popFunction() {
		return functionStack.poll();
	}

	/**
	 * 
	 * @return
	 */
	private UniverseModifier popGroup() {
		List<UniverseModifier> list = new ArrayList<UniverseModifier>(
				modifierStack);
		Collections.reverse(list);
		modifierStack = modifierListStack.poll();
		return new CompositeModifier(list);
	}

	/**
	 * 
	 * @return
	 */
	private UniverseModifier popModifier() {
		return modifierStack.poll();
	}

	/**
	 * 
	 * @return
	 */
	private RelationFunction popRelation() {
		return relationFunction;
	}

	/**
	 * 
	 * @param modifier
	 */
	private void push(CellModifier modifier) {
		cellModifierStack.offer(modifier);
	}

	/**
	 * 
	 * @param function
	 */
	private void push(CoefficientFunction function) {
		functionStack.offer(function);
	}

	/**
	 * 
	 * @param modifier
	 */
	private void push(UniverseModifier modifier) {
		modifierStack.offer(modifier);
	}

	/**
	 * 
	 */
	private void pushCellModifierList() {
		cellModiferListStack.offer(cellModifierStack);
		cellModifierStack = Collections
				.asLifoQueue(new ArrayDeque<CellModifier>());
	}

	/**
	 * 
	 * @param id
	 */
	private void pushDefId(String id) {
		this.id = id;
	}

	/**
	 * 
	 */
	private void pushGroup() {
		modifierListStack.offer(modifierStack);
		modifierStack = Collections
				.asLifoQueue(new ArrayDeque<UniverseModifier>());
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#setDocumentLocator(org.xml.sax.Locator
	 *      )
	 */
	@Override
	public void setDocumentLocator(Locator documentLocator) {
		this.documentLocator = documentLocator;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attrs) throws SAXException {
		text.setLength(0);
		if (uriFilter.equals(uri)) {
			ElementHandler h = handlerMap.get(qName);
			if (h != null) {
				h.startElement(attrs);
			}
		}
	}

}
