/**
 * 
 */
package org.mmarini.fluid.model1;

/**
 * @author us00852
 *
 */
public class Fluid {
	private final double temperature;
	private final double molecularMass;

	/**
	 * @param temperature
	 * @param molecularMass
	 */
	public Fluid(final double temperature, final double molecularMass) {
		this.temperature = temperature;
		this.molecularMass = molecularMass;
	}

	/**
	 * @return the molecularMass
	 */
	public double getMolecularMass() {
		return molecularMass;
	}

	/**
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Fluid [temperature=").append(temperature)
				.append(", molecularMass=").append(molecularMass).append("]");
		return builder.toString();
	}

}
