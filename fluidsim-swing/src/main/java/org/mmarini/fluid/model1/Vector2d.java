package org.mmarini.fluid.model1;

/**
 * 
 * @author us00852
 *
 */
public class Vector2d {
	public static final Vector2d ZERO = new Vector2d(0.0, 0.0);
	private final double x;
	private final double y;

	/**
	 * @param x
	 * @param y
	 */
	public Vector2d(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	public Vector2d add(final Vector2d v) {
		return new Vector2d(x + v.x, y + v.y);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vector2d other = (Vector2d) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public double getLength() {
		return Math.sqrt(getSquare());
	}

	/**
	 * 
	 * @return
	 */
	public double getSquare() {
		return x * x + y * y;
	}

	/**
	 * 
	 * @return
	 */
	public Vector2d getVersor() {
		final double l = getLength();
		return new Vector2d(x / l, y / l);
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * 
	 * @param l
	 * @return
	 */
	public Vector2d mul(final double l) {
		return new Vector2d(x * l, y * l);
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	public double mul(final Vector2d v) {
		return x * v.x + y * v.y;
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	public Vector2d sub(final Vector2d v) {
		return new Vector2d(x - v.x, y - v.y);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("(").append(x).append(", ").append(y).append(")");
		return builder.toString();
	}

}
