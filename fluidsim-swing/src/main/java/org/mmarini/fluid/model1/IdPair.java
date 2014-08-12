/**
 * 
 */
package org.mmarini.fluid.model1;

import java.awt.Point;

/**
 * @author us00852
 *
 */
public class IdPair {
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static IdPair[] createAdjacents(final Point p) {
		return new IdPair[] { new IdPair(p, new Point(p.x, p.y + 1)),
				new IdPair(p, new Point(p.x + 1, p.y)) };
	}

	private final Point id0;

	private final Point id1;

	public IdPair(final int i0, final int j0, final int i1, final int j1) {
		this(new Point(i0, j0), new Point(i1, j1));
	}

	/**
	 * @param id0
	 * @param id1
	 */
	public IdPair(final Point id0, final Point id1) {
		this.id0 = id0;
		this.id1 = id1;
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
		final IdPair other = (IdPair) obj;
		if (id0 == null) {
			if (other.id0 != null)
				return false;
		} else if (!id0.equals(other.id0))
			return false;
		if (id1 == null) {
			if (other.id1 != null)
				return false;
		} else if (!id1.equals(other.id1))
			return false;
		return true;
	}

	/**
	 * @return the id0
	 */
	public Point getId0() {
		return id0;
	}

	/**
	 * @return the id1
	 */
	public Point getId1() {
		return id1;
	}

	/**
	 * 
	 * @param t
	 * @return
	 */
	public Vector2d getVector(final SpaceTopology t) {
		return t.getVector(id1).sub(t.getVector(id0));
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id0 == null) ? 0 : id0.hashCode());
		result = prime * result + ((id1 == null) ? 0 : id1.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[").append(id0).append(", ").append(id1).append("]");
		return builder.toString();
	}
}
