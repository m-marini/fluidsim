package org.mmarini.fluid.model1;

public interface FluidConstants {
	public static final double AIR_MOLECULAR_MASS = 14 * 0.8 * 2 + 16 * 0.2 * 2;
	public static final double TEMPERATURE_0 = -273.15;
	public static final double AMBIENT_TEMPERATURE = 25 - TEMPERATURE_0;
	public static final double R = 8.314472;
	public static final double STANDARD_PRESSURE = 101325;
	public static final double AMBIENT_AIR_MASS = STANDARD_PRESSURE
			* AIR_MOLECULAR_MASS * 1e-3 / R / AMBIENT_TEMPERATURE;
}
