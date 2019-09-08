/**
 *
 */
package org.mmarini.fluid.xml;

import java.io.IOException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.mmarini.fluid.model.UniverseModifier;
import org.xml.sax.SAXException;

/**
 * @author US00852
 *
 */
public class FluidParser {

	private static final String FLUID_1_0_0_XSD = "/fluid-1.0.0.xsd";

	/**
	 *
	 */
	public FluidParser() {
	}

	/**
	 *
	 * @param url
	 * @return
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public UniverseModifier parse(final URL url) throws ParserConfigurationException, SAXException, IOException {
		final FluidSaxHandler h = new FluidSaxHandler();

		final SAXParserFactory f = SAXParserFactory.newInstance();
		f.setNamespaceAware(true);
		f.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
				.newSchema(getClass().getResource(FLUID_1_0_0_XSD)));
		f.newSAXParser().parse(url.openStream(), h);
		return h.getResult();
	}
}
