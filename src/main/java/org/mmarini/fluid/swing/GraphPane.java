/*
 * GraphPane.java
 *
 * $Id: GraphPane.java,v 1.4 2007/08/18 08:29:55 marco Exp $
 *
 * 02/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.function.Function;

import javax.swing.JPanel;

import org.mmarini.fluid.model.v2.Universe;
import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * The GraphPane shows the graphic of different universe functions.
 * <p>
 * It draws the bee structure (exagonal structure) of a two dimension
 * function.<br>
 * Each exagoanl cell is droaw in a rainbow color scale from red (higher value)
 * to violet (lower value).
 *
 * @author US00852
 * @version $Id: GraphPane.java,v 1.4 2007/08/18 08:29:55 marco Exp $
 *
 */
public class GraphPane extends JPanel {
	private static final float BRIGHTNESS_OFFSET = 0.3f;
	private static final float BRIGHTNESS_SCALE = 0.5f;
	private static final float HUE_SCALE = 0.8f;
	private static final int MAX = 1;
	private static final long serialVersionUID = 1L;
//	private static Logger log = LoggerFactory.getLogger(GraphPane.class);
	private Universe universe;

	private final Function<Universe, INDArray> valuesFunc;
	private final CellShape cellShape;

	/**
	 *
	 * @param shapeFunc
	 * @param valuesFunc
	 */
	public GraphPane(final Function<Universe, INDArray> valuesFunc) {
		super();
		setDoubleBuffered(true);
		this.valuesFunc = valuesFunc;
		cellShape = RectangularCellShape.getInstance();
	}

	/**
	 * Calculates the lower integer greater or equal to the value n / d.
	 *
	 * @param n the numerator
	 * @param d the denominator
	 * @return the value
	 */
//	private int ceilDiv(final int n, final int d) {
//		return (n + d - 1) / d;
//	}

	/**
	 * Calculates the color int the rainbow color scale for a value.
	 *
	 * @param value the value
	 * @return the color
	 */
	private Color getColor(double value) {
		value = Math.max(0, Math.min(value, MAX));
		final float s = 1f;
		final float h = HUE_SCALE - (float) value * HUE_SCALE;
		final float b = (float) value * BRIGHTNESS_SCALE + BRIGHTNESS_OFFSET;
		return new Color(Color.HSBtoRGB(h, s, b));
	}

	public void onUniverse(final Universe universe) {
		this.universe = universe;
		repaint();
	}

	/**
	 * Paints the component.
	 *
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(final Graphics gr) {
		gr.setColor(Color.BLACK);
		final Dimension size = getSize();
		gr.fillRect(0, 0, size.width, size.height);
		final Universe u = universe;
		if (u != null) {
			paintRectStructure(gr, u);
		}
	}

	/**
	 * Paints the structure.
	 *
	 * @param gr the graphics context
	 * @param u  the universe
	 */
//	private void paintIsoStructure(final Graphics gr, final Universe u) {
//		final INDArray values = valuesFunc.apply(u);
//		final long[] shape = values.shape();
//		final Dimension uSize = new Dimension((int) shape[0], (int) shape[1]);
//		final Dimension gSize = getSize();
//		final int uh = uSize.height;
//		final int uw = uSize.width;
//		final int gw = gSize.width - 1;
//		final int gh = gSize.height - 1;
//		final int kw = (2 * uw + 1); // Divider constant of width
//		final int cw = ceilDiv(2 * gw, kw); // Cell width
//		final int nh = gh; // Multiplier constant of height
//		final int dh = 3 * uh + 1; // Diverder constant of height
//		final int nh2 = 3 * nh; // Multiplier constant2 of height
//		final int ch = ceilDiv(4 * nh, dh); // Cell height
//		final int[] indexes = new int[2];
//		for (int i = 0; i < uh; i += 2) {
//			final int y = gh - ch - ceilDiv(i * nh2, dh);
//			for (int j = 0; j < uw; ++j) {
//				final double v = values.getDouble(i, j);
//				final Color col = getColor(v);
//				gr.setColor(col);
//				int x = j * 2 * gw;
//				x = ceilDiv(x, kw);
//				cellShape.draw(gr, x, y, cw, ch);
//			}
//		}
//		for (int i = 1; i < uh; i += 2) {
//			indexes[0] = i;
//			final int y = gh - ch - ceilDiv(i * nh2, dh);
//			for (int j = 0; j < uw; ++j) {
//				indexes[1] = j;
//				final double v = values.getDouble(i, j);
//				final Color col = getColor(v);
//				gr.setColor(col);
//				int x = (j * 2 + 1) * gw;
//				x = ceilDiv(x, kw);
//				cellShape.draw(gr, x, y, cw, ch);
//			}
//		}
//	}

	/**
	 * Paints the structure.
	 *
	 * @param gr the graphics context
	 * @param u  the universe
	 */
	private void paintRectStructure(final Graphics gr, final Universe u) {
		final INDArray values = valuesFunc.apply(u);
		final long[] shape = values.shape();
//		final Dimension uSize = new Dimension((int) shape[0], (int) shape[1]);
		final Dimension gSize = getSize();
		final int uh = (int) shape[0];
		final int uw = (int) shape[1];
		final int gw = gSize.width - 1;
		final int gh = gSize.height - 1;
		for (int i = 0; i < uh; i++) {
			final int y = i * gh / uh;
			final int ch = (i + 1) * gh / uh - y;
			for (int j = 0; j < uw; ++j) {
				final double v = values.getDouble(i, j);
				final Color col = getColor(v);
				gr.setColor(col);
				final int x = j * gw / uw;
				final int cw = (j + 1) * gw / uw - x;
				cellShape.draw(gr, x, y, cw, ch);
			}
		}
	}
}
