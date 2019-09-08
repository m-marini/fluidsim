/**
 * 
 */
package org.mmarini.fluid.model1;

/**
 * @author us00852
 *
 */
public class Cell {
	private double mass;
	private Vector2d momentum;
	private double massAcc;
	private Vector2d momentumAcc;

	/**
	 * 
	 */
	public Cell() {
		this(0.0, Vector2d.ZERO);
	}

	/**
	 * @param mass
	 * @param momentum
	 */
	public Cell(final double mass, final Vector2d momentum) {
		this.mass = mass;
		this.momentum = momentum;
		momentumAcc = Vector2d.ZERO;
	}

	/**
	 * 
	 * @param mass
	 * @return
	 */
	public Cell addMass(final double mass) {
		massAcc += mass;
		return this;
	}

	/**
	 * 
	 * @param momentum
	 * @return
	 */
	public Cell addMomentum(final Vector2d momentum) {
		momentumAcc = momentumAcc.add(momentum);
		return this;
	}

	/**
	 * 
	 */
	public Cell apply() {
		mass += massAcc;
		momentum = momentum.add(momentumAcc);
		massAcc = 0;
		momentumAcc = Vector2d.ZERO;
		return this;
	}

	/**
	 * @return the mass
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * @return the momentum
	 */
	public Vector2d getMomentum() {
		return momentum;
	}

	/**
	 * 
	 * @return
	 */
	public Vector2d getVelocity() {
		return momentum.mul(1 / mass);
	}

	/**
	 * @param mass
	 *            the mass to set
	 * @return
	 */
	public Cell setMass(final double mass) {
		this.mass = mass;
		return this;
	}

	/**
	 * @param momentum
	 *            the momentum to set
	 * @return
	 */
	public Cell setMomentum(final Vector2d momentum) {
		this.momentum = momentum;
		return this;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Cell [mass=").append(mass).append(", momentum=")
				.append(momentum).append("]");
		return builder.toString();
	}
}
