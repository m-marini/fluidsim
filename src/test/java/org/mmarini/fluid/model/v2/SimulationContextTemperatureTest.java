package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mmarini.fluid.TestUtils.around;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class SimulationContextTemperatureTest implements Constants, TamperConstants {

	private static final double INTERVAL = SIZE / SPEED / 10;

	private static SimulationContext centerContext() {
		final INDArray density = TAMPER.mul(ISA_DENSITY);
		final INDArray temperature = TAMPER.mul(ISA_TEMPERATURE);
		final INDArray speed = Utils.vsmul(Utils.prefixBroadcast(Nd4j.ones(2).mul(SPEED), 3, 3), TAMPER);
		final INDArray mu = Nd4j.ones(DataType.DOUBLE, 3, 3);
		return new SimulationContext(
				new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS, ISA_SPECIFIC_HEAT_CAPACITY, mu),
				INTERVAL);
	}

	public SimulationContextTemperatureTest() {
		Nd4j.create(new double[0]);
	}

	@Test
	public void testDeltaTemperature() {
		final double expected = (ISA_PRESSURE * SPEED
				+ ISA_TEMPERATURE * ISA_SPECIFIC_HEAT_CAPACITY * ISA_DENSITY * VOLUME * SPEED) * AREA * INTERVAL
				/ (VOLUME * ISA_SPECIFIC_HEAT_CAPACITY * ISA_DENSITY * VOLUME);

		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.computeDeltaTemperature();
		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), around(expected * (-TAMPERV01_POW3 - TAMPERH10_POW3) / TAMPER00));
		assertThat(y.getDouble(0, 1), around(expected * (TAMPERV01_POW3 - TAMPERH11_POW3 - TAMPERV02_POW3) / TAMPER01));
		assertThat(y.getDouble(0, 2), around(expected * (TAMPERV02_POW3 - TAMPERH12_POW3) / TAMPER02));

		assertThat(y.getDouble(1, 0), around(expected * (TAMPERH10_POW3 - TAMPERV11_POW3 - TAMPERH20_POW3) / TAMPER10));
		assertThat(y.getDouble(1, 1),
				around(expected * (TAMPERH11_POW3 + TAMPERV11_POW3 - TAMPERH21_POW3 - TAMPERV12_POW3) / TAMPER11));
		assertThat(y.getDouble(1, 2), around(expected * (TAMPERH12_POW3 + TAMPERV12_POW3 - TAMPERH22_POW3) / TAMPER12));

		assertThat(y.getDouble(2, 0), around(expected * (TAMPERH20_POW3 - TAMPERV21_POW3) / TAMPER20));
		assertThat(y.getDouble(2, 1), around(expected * (TAMPERH21_POW3 + TAMPERV21_POW3 - TAMPERV22_POW3) / TAMPER21));
		assertThat(y.getDouble(2, 2), around(expected * (TAMPERH22_POW3 + TAMPERV22_POW3) / TAMPER22));
	}

	/**
	 * <pre>
	 * <code>
	 *
	 * </code>
	 * </pre>
	 */
	@Test
	public void testEnergy() {
		final double expected = ISA_TEMPERATURE * ISA_SPECIFIC_HEAT_CAPACITY * ISA_DENSITY * VOLUME * SPEED;

		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.computeEnergyFlux();
		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), around(expected * (-TAMPERV01_POW3 - TAMPERH10_POW3)));
		assertThat(y.getDouble(0, 1), around(expected * (TAMPERV01_POW3 - TAMPERH11_POW3 - TAMPERV02_POW3)));
		assertThat(y.getDouble(0, 2), around(expected * (TAMPERV02_POW3 - TAMPERH12_POW3)));

		assertThat(y.getDouble(1, 0), around(expected * (TAMPERH10_POW3 - TAMPERV11_POW3 - TAMPERH20_POW3)));
		assertThat(y.getDouble(1, 1),
				around(expected * (TAMPERH11_POW3 + TAMPERV11_POW3 - TAMPERH21_POW3 - TAMPERV12_POW3)));
		assertThat(y.getDouble(1, 2), around(expected * (TAMPERH12_POW3 + TAMPERV12_POW3 - TAMPERH22_POW3)));

		assertThat(y.getDouble(2, 0), around(expected * (TAMPERH20_POW3 - TAMPERV21_POW3)));
		assertThat(y.getDouble(2, 1), around(expected * (TAMPERH21_POW3 + TAMPERV21_POW3 - TAMPERV22_POW3)));
		assertThat(y.getDouble(2, 2), around(expected * (TAMPERH22_POW3 + TAMPERV22_POW3)));
	}

	/**
	 * <pre>
	 * <code>
	 *
	 * </code>
	 * </pre>
	 */
	@Test
	public void testPower() {
		final double expected = ISA_PRESSURE * SPEED;

		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.computePower();
		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), around(expected * (-TAMPERV01_POW3 - TAMPERH10_POW3)));
		assertThat(y.getDouble(0, 1), around(expected * (TAMPERV01_POW3 - TAMPERH11_POW3 - TAMPERV02_POW3)));
		assertThat(y.getDouble(0, 2), around(expected * (TAMPERV02_POW3 - TAMPERH12_POW3)));

		assertThat(y.getDouble(1, 0), around(expected * (TAMPERH10_POW3 - TAMPERV11_POW3 - TAMPERH20_POW3)));
		assertThat(y.getDouble(1, 1),
				around(expected * (TAMPERH11_POW3 + TAMPERV11_POW3 - TAMPERH21_POW3 - TAMPERV12_POW3)));
		assertThat(y.getDouble(1, 2), around(expected * (TAMPERH12_POW3 + TAMPERV12_POW3 - TAMPERH22_POW3)));

		assertThat(y.getDouble(2, 0), around(expected * (TAMPERH20_POW3 - TAMPERV21_POW3)));
		assertThat(y.getDouble(2, 1), around(expected * (TAMPERH21_POW3 + TAMPERV21_POW3 - TAMPERV22_POW3)));
		assertThat(y.getDouble(2, 2), around(expected * (TAMPERH22_POW3 + TAMPERV22_POW3)));
	}
}
