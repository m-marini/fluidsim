/*
 * LineModifier.java
 *
 * $Id: LineModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 03/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The LineModifier modifies the universe applying the cell modifier to the
 * cells in the line defined by the properties.
 * <p>
 * The line is delimited by two opposite corner (x0,y0) and (x1,y1). The
 * coordinate is in 0 ... 1 range.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: LineModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public class LineModifier extends AbstractUniverseModifier {
	private double x0;
	private double y0;
	private double x1;
	private double y1;

	/**
	 * 
	 */
	public LineModifier() {
	}

	/**
	 * 
	 * @param cellModifier
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	public LineModifier(CellModifier cellModifier, double x0, double y0,
			double x1, double y1) {
		super(cellModifier);
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}

	/**
	 * Returns the x0 valu
	 * 
	 * @return the x0
	 */
	public double getX0() {
		return x0;
	}

	/**
	 * Returns the x1 value.
	 * 
	 * @return the x1
	 */
	public double getX1() {
		return x1;
	}

	/**
	 * Returns the y0 value.
	 * 
	 * @return the y0
	 */
	public double getY0() {
		return y0;
	}

	/**
	 * Returns the y1 value.
	 * 
	 * @return the y1
	 */
	public double getY1() {
		return y1;
	}

	/**
	 * @see UniverseModifier#modify(Universe)
	 */
	@Override
	public void modify(Universe universe) {
		double x02 = getX0();
		double y02 = getY0();
		double x12 = getX1();
		double y12 = getY1();
		int x0 = Utils.transformToX(universe, x02, y02);
		int y0 = Utils.transformToY(universe, x02, y02);
		int x1 = Utils.transformToX(universe, x12, y12);
		int y1 = Utils.transformToY(universe, x12, y12);

		if (Math.abs(x1 - x0) >= Math.abs(y1 - y0)) {
			if (x0 > x1) {
				int t = x0;
				x0 = x1;
				x1 = t;
				t = y0;
				y0 = y1;
				y1 = t;
			}
			if (y1 >= y0) {
				modifyHorizontallyUp(universe, x0, y0, x1, y1);
			} else {
				modifyHorizontallyDown(universe, x0, y0, x1, y1);
			}
		} else {
			if (y0 > y1) {
				int t = x0;
				x0 = x1;
				x1 = t;
				t = y0;
				y0 = y1;
				y1 = t;
			}
			modifyVertically(universe, x0, y0, x1, y1);
		}
	}

	/**
	 * Modifies the universe in horizontal order downword.
	 * 
	 * @param universe
	 *            the universe
	 * @param x0
	 *            the x0 left lower column
	 * @param y0
	 *            the y0 left lower row
	 * @param x1
	 *            the x1 right upper column
	 * @param y1
	 *            the y1 right upper row
	 */
	private void modifyHorizontallyDown(Universe universe, int x0, int y0,
			int x1, int y1) {
		int i0 = Utils.transformToRow(universe, x0, y0);
		int j0 = Utils.transformToColumn(universe, x0, y0);
		int j1 = Utils.transformToColumn(universe, x1, y1);
		int i = i0;
		int j = j0;
		getCellModifier().modify(universe, i, j);
		// y = (gy1 - gy0) * (x - gx0) / (gx1 - gx0) + gy0 + 1 / 2
		// y = (2 * (gy1 - gy0) * (x - gx0) / (gx1 - gx0) + 2 * gy0 + 1) / 2
		// y = (2 * (gy1 - gy0) * (x - gx0) + (gx1 - gx0) * (2 * gy0 + 1)) / 2 /
		// (gx1 - gx0)
		// y = (2 * (gy1 - gy0) * x - 2 * (gy1 - gy0) * gx0 + (gx1 - gx0) * (2 *
		// gy0 + 1)) / 2 /
		// (gx1 - gx0)
		int nx = 2 * (y1 - y0);
		int d = 2 * (x1 - x0);
		int n = (x1 - x0) * (2 * y0 + 1) - 2 * x0 * (y1 - y0);
		while (j < j1) {
			int x = Utils.transformToX(i, j + 1);
			int y = (nx * x + n) / d;
			if (y == i) {
				++j;
			} else {
				--i;
				if ((i % 2) == 0)
					++j;
			}
			getCellModifier().modify(universe, i, j);
		}
	}

	/**
	 * Modifies the universe in horizontal order upward
	 * 
	 * @param universe
	 *            the universe
	 * @param x0
	 *            the x0 left lower column
	 * @param y0
	 *            the y0 left lower row
	 * @param x1
	 *            the x1 right upper column
	 * @param y1
	 *            the y1 right upper row
	 */
	private void modifyHorizontallyUp(Universe universe, int x0, int y0,
			int x1, int y1) {
		int i0 = Utils.transformToRow(universe, x0, y0);
		int j0 = Utils.transformToColumn(universe, x0, y0);
		int j1 = Utils.transformToColumn(universe, x1, y1);
		int i = i0;
		int j = j0;
		getCellModifier().modify(universe, i, j);
		// y = (gy1 - gy0) * (x - gx0) / (gx1 - gx0) + gy0 + 1 / 2
		// y = (2 * (gy1 - gy0) * (x - gx0) / (gx1 - gx0) + 2 * gy0 + 1) / 2
		// y = (2 * (gy1 - gy0) * (x - gx0) + (gx1 - gx0) * (2 * gy0 + 1)) / 2 /
		// (gx1 - gx0)
		// y = (2 * (gy1 - gy0) * x - 2 * (gy1 - gy0) * gx0 + (gx1 - gx0) * (2 *
		// gy0 + 1)) / 2 /
		// (gx1 - gx0)
		int nx = 2 * (y1 - y0);
		int d = 2 * (x1 - x0);
		int n = (x1 - x0) * (2 * y0 + 1) - 2 * x0 * (y1 - y0);
		while (j < j1) {
			int x = Utils.transformToX(i, j + 1);
			int y = (nx * x + n) / d;
			if (y == i) {
				++j;
			} else {
				++i;
				if ((i % 2) == 0)
					++j;
			}
			getCellModifier().modify(universe, i, j);
		}
	}

	/**
	 * Modifies the universe in vertical order.
	 * 
	 * @param universe
	 *            the universe
	 * @param x0
	 *            the x lower corner
	 * @param y0
	 *            the y lower corner
	 * @param x1
	 *            the x upper corner
	 * @param y1
	 *            the y upper corner
	 */
	private void modifyVertically(Universe universe, int x0, int y0, int x1,
			int y1) {
		int i0 = Utils.transformToRow(universe, x0, y0);
		int j0 = Utils.transformToColumn(universe, x0, y0);
		int i1 = Utils.transformToRow(universe, x1, y1);
		int i = i0;
		int j = j0;
		// x = (x1 - x0) * (y - y0) / (y1 - y0) + x0 + 1 / 2
		// x = (2 * (x1 - x0) * (y - y0) / (y1 - y0) + 2 * x0 + 1) / 2
		// x = (2 * (x1 - x0) * (y - y0) + (2 * x0 + 1) * (y1 - y0)) / 2/ (y1 -
		// y0)
		// x = (2 * (x1 - x0) * y - y0 * 2 * (x1 - x0) + (2 * x0 + 1) * (y1 -
		// y0)) / 2/ (y1 - y0)
		int ny = 2 * (x1 - x0);
		int n = (2 * x0 + 1) * (y1 - y0) - y0 * 2 * (x1 - x0);
		int d = 2 * (y1 - y0);
		getCellModifier().modify(universe, i, j);
		while (i < i1) {
			++i;
			int y = Utils.transformToY(i, j);
			int x = (ny * y + n) / d;
			j = Utils.transformToColumn(universe, x, y);
			getCellModifier().modify(universe, i, j);
		}
	}

	/**
	 * Sets the x0 value.
	 * 
	 * @param x0
	 *            the x0 to set
	 */
	public void setX0(double x) {
		this.x0 = x;
	}

	/**
	 * Sets the x1 value.
	 * 
	 * @param x1
	 *            the x1 to set
	 */
	public void setX1(double size) {
		this.x1 = size;
	}

	/**
	 * Sets the y0 value.
	 * 
	 * @param y0
	 *            the y0 to set
	 */
	public void setY0(double y) {
		this.y0 = y;
	}

	/**
	 * Sets the y1 value.
	 * 
	 * @param y1
	 *            the y1 to set
	 */
	public void setY1(double height) {
		this.y1 = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LineModifier [x0=").append(x0).append(", y0=")
				.append(y0).append(", x1=").append(x1).append(", y1=")
				.append(y1).append("]");
		return builder.toString();
	}
}