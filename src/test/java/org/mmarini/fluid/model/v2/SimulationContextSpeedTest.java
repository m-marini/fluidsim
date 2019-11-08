package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mmarini.fluid.TestUtils.around;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class SimulationContextSpeedTest implements Constants, TamperConstants {

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

	public SimulationContextSpeedTest() {
		Nd4j.create(new double[0]);
	}

	/**
	 * <pre>
	 * flux = u^2 S dt / V
	 * force = p u S dt / rho / V
	 * </pre>
	 *
	 * <pre>
	 * (-2,-2)     (-1.1,-1.1) (0,0)
	 * (-1.1,-1.1) (0,0)       (1.1,1.1)
	 *  (0,0)      (1.1,1.1)   (2,2)
	 * </pre>
	 */
	@Test
	public void testDeltaSpeed() {
		final double flux = SPEED * SPEED * INTERVAL * AREA / VOLUME;
		final double force = ISA_PRESSURE * INTERVAL * AREA / VOLUME / ISA_DENSITY;

		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.computeDeltaSpeed();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(
				(flux * (-TAMPERH10_POW3 - TAMPERV01_POW3) + force * -(-TAMPERV00_POW2 + TAMPERV01_POW2)) / TAMPER00));
		assertThat(y.getDouble(0, 0, 1), around(
				(flux * (-TAMPERH10_POW3 - TAMPERV01_POW3) + force * -(-TAMPERH00_POW2 + TAMPERH10_POW2)) / TAMPER00));
		assertThat(y.getDouble(0, 1, 0), around((flux * (-TAMPERH11_POW3 + TAMPERV01_POW3 - TAMPERV02_POW3)
				+ force * -(-TAMPERV01_POW2 + TAMPERV02_POW2)) / TAMPER01));
		assertThat(y.getDouble(0, 1, 1), around((flux * (-TAMPERH11_POW3 + TAMPERV01_POW3 - TAMPERV02_POW3)
				+ force * -(-TAMPERH01_POW2 + TAMPERH11_POW2)) / TAMPER01));
		assertThat(y.getDouble(0, 2, 0), around(
				(flux * (-TAMPERH12_POW3 + TAMPERV02_POW3) + force * -(-TAMPERV02_POW2 + TAMPERV03_POW2)) / TAMPER02));
		assertThat(y.getDouble(0, 2, 1), around(
				(flux * (-TAMPERH12_POW3 + TAMPERV02_POW3) + force * -(-TAMPERH02_POW2 + TAMPERH12_POW2)) / TAMPER02));

		assertThat(y.getDouble(1, 0, 0), around((flux * (TAMPERH10_POW3 - TAMPERH20_POW3 - TAMPERV11_POW3)
				+ force * -(-TAMPERV10_POW2 + TAMPERV11_POW2)) / TAMPER10));
		assertThat(y.getDouble(1, 0, 1), around((flux * (TAMPERH10_POW3 - TAMPERH20_POW3 - TAMPERV11_POW3)
				+ force * -(-TAMPERH10_POW2 + TAMPERH20_POW2)) / TAMPER10));
		assertThat(y.getDouble(1, 1, 0),
				around((flux * (TAMPERH11_POW3 - TAMPERH21_POW3 + TAMPERV11_POW3 - TAMPERV12_POW3)
						+ force * -(-TAMPERV11_POW2 + TAMPERV12_POW2)) / TAMPER11));
		assertThat(y.getDouble(1, 1, 1),
				around((flux * (TAMPERH11_POW3 - TAMPERH21_POW3 + TAMPERV11_POW3 - TAMPERV12_POW3)
						+ force * -(-TAMPERH11_POW2 + TAMPERH21_POW2)) / TAMPER11));
		assertThat(y.getDouble(1, 2, 0), around((flux * (TAMPERH12_POW3 - TAMPERH22_POW3 + TAMPERV12_POW3)
				+ force * -(-TAMPERV12_POW2 + TAMPERV13_POW2)) / TAMPER12));
		assertThat(y.getDouble(1, 2, 1), around((flux * (TAMPERH12_POW3 - TAMPERH22_POW3 + TAMPERV12_POW3)
				+ force * -(-TAMPERH12_POW2 + TAMPERH22_POW2)) / TAMPER12));

		assertThat(y.getDouble(2, 0, 0), around(
				(flux * (TAMPERH20_POW3 - TAMPERV21_POW3) + force * -(-TAMPERV20_POW2 + TAMPERV21_POW2)) / TAMPER20));
		assertThat(y.getDouble(2, 0, 1), around(
				(flux * (TAMPERH20_POW3 - TAMPERV21_POW3) + force * -(-TAMPERH20_POW2 + TAMPERH30_POW2)) / TAMPER20));
		assertThat(y.getDouble(2, 1, 0), around((flux * (TAMPERH21_POW3 + TAMPERV21_POW3 - TAMPERV22_POW3)
				+ force * -(-TAMPERV21_POW2 + TAMPERV22_POW2)) / TAMPER21));
		assertThat(y.getDouble(2, 1, 1), around((flux * (TAMPERH21_POW3 + TAMPERV21_POW3 - TAMPERV22_POW3)
				+ force * -(-TAMPERH21_POW2 + TAMPERH31_POW2)) / TAMPER21));
		assertThat(y.getDouble(2, 2, 0), around(
				(flux * (TAMPERH22_POW3 + TAMPERV22_POW3) + force * -(-TAMPERV22_POW2 + TAMPERV23_POW2)) / TAMPER22));
		assertThat(y.getDouble(2, 2, 1), around(
				(flux * (TAMPERH22_POW3 + TAMPERV22_POW3) + force * -(-TAMPERH22_POW2 + TAMPERH32_POW2)) / TAMPER22));
	}

	/**
	 * <p>
	 *
	 * <pre>
	 * flux = rho u^2
	 * </pre>
	 *
	 * <pre>
	 * <code>
	 * -H10^3-V01^3      -H11^3+V01^3-V02^3      -H12^3+V02^3
	 * H10^3-H20^3-V11^3 H11^3-H21^3+V11^3-V12^3 H12^3-H22^3+V12^3
	 * H20^3-V21^3       H21^2+V21^3-V22^3       H22^2+V22^3
	 * <code>
	 * </pre>
	 * <p>
	 */
	@Test
	public void testMomentFlux() {
		final double flux = ISA_DENSITY * SPEED * SPEED;

		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.computeMomentFlux();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(flux * (-TAMPERH10_POW3 - TAMPERV01_POW3)));
		assertThat(y.getDouble(0, 0, 1), around(flux * (-TAMPERH10_POW3 - TAMPERV01_POW3)));
		assertThat(y.getDouble(0, 1, 0), around(flux * (-TAMPERH11_POW3 + TAMPERV01_POW3 - TAMPERV02_POW3)));
		assertThat(y.getDouble(0, 1, 1), around(flux * (-TAMPERH11_POW3 + TAMPERV01_POW3 - TAMPERV02_POW3)));
		assertThat(y.getDouble(0, 2, 0), around(flux * (-TAMPERH12_POW3 + TAMPERV02_POW3)));
		assertThat(y.getDouble(0, 2, 1), around(flux * (-TAMPERH12_POW3 + TAMPERV02_POW3)));

		assertThat(y.getDouble(1, 0, 0), around(flux * (TAMPERH10_POW3 - TAMPERH20_POW3 - TAMPERV11_POW3)));
		assertThat(y.getDouble(1, 0, 1), around(flux * (TAMPERH10_POW3 - TAMPERH20_POW3 - TAMPERV11_POW3)));
		assertThat(y.getDouble(1, 1, 0),
				around(flux * (TAMPERH11_POW3 - TAMPERH21_POW3 + TAMPERV11_POW3 - TAMPERV12_POW3)));
		assertThat(y.getDouble(1, 1, 1),
				around(flux * (TAMPERH11_POW3 - TAMPERH21_POW3 + TAMPERV11_POW3 - TAMPERV12_POW3)));
		assertThat(y.getDouble(1, 2, 0), around(flux * (TAMPERH12_POW3 - TAMPERH22_POW3 + TAMPERV12_POW3)));
		assertThat(y.getDouble(1, 2, 1), around(flux * (TAMPERH12_POW3 - TAMPERH22_POW3 + TAMPERV12_POW3)));

		assertThat(y.getDouble(2, 0, 0), around(flux * (TAMPERH20_POW3 - TAMPERV21_POW3)));
		assertThat(y.getDouble(2, 0, 1), around(flux * (TAMPERH20_POW3 - TAMPERV21_POW3)));
		assertThat(y.getDouble(2, 1, 0), around(flux * (TAMPERH21_POW3 + TAMPERV21_POW3 - TAMPERV22_POW3)));
		assertThat(y.getDouble(2, 1, 1), around(flux * (TAMPERH21_POW3 + TAMPERV21_POW3 - TAMPERV22_POW3)));
		assertThat(y.getDouble(2, 2, 0), around(flux * (TAMPERH22_POW3 + TAMPERV22_POW3)));
		assertThat(y.getDouble(2, 2, 1), around(flux * (TAMPERH22_POW3 + TAMPERV22_POW3)));
	}

	/**
	 * <pre>
	 * force = p
	 * </pre>
	 *
	 * <pre>
	 * <code>
	 * -(-V00^2+V01^2,-H00^2+H10^2)      -H11^3+V01^3-V02^3      -H12^3+V02^3
	 * H10^3-H20^3-V11^3 H11^3-H21^3+V11^3-V12^3 H12^3-H22^3+V12^3
	 * H20^3-V21^3       H21^2+V21^3-V22^3       H22^2+V22^3
	 * </code>
	 * </pre>
	 */
	@Test
	public void testPressureForce() {
		final double force = ISA_PRESSURE;

		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.computePressureForce();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(force * -(-TAMPERV00_POW2 + TAMPERV01_POW2)));
		assertThat(y.getDouble(0, 0, 1), around(force * -(-TAMPERH00_POW2 + TAMPERH10_POW2)));
		assertThat(y.getDouble(0, 1, 0), around(force * -(-TAMPERV01_POW2 + TAMPERV02_POW2)));
		assertThat(y.getDouble(0, 1, 1), around(force * -(-TAMPERH01_POW2 + TAMPERH11_POW2)));
		assertThat(y.getDouble(0, 2, 0), around(force * -(-TAMPERV02_POW2 + TAMPERV03_POW2)));
		assertThat(y.getDouble(0, 2, 1), around(force * -(-TAMPERH02_POW2 + TAMPERH12_POW2)));

		assertThat(y.getDouble(1, 0, 0), around(force * -(-TAMPERV10_POW2 + TAMPERV11_POW2)));
		assertThat(y.getDouble(1, 0, 1), around(force * -(-TAMPERH10_POW2 + TAMPERH20_POW2)));
		assertThat(y.getDouble(1, 1, 0), around(force * -(-TAMPERV11_POW2 + TAMPERV12_POW2)));
		assertThat(y.getDouble(1, 1, 1), around(force * -(-TAMPERH11_POW2 + TAMPERH21_POW2)));
		assertThat(y.getDouble(1, 2, 0), around(force * -(-TAMPERV12_POW2 + TAMPERV13_POW2)));
		assertThat(y.getDouble(1, 2, 1), around(force * -(-TAMPERH12_POW2 + TAMPERH22_POW2)));

		assertThat(y.getDouble(2, 0, 0), around(force * -(-TAMPERV20_POW2 + TAMPERV21_POW2)));
		assertThat(y.getDouble(2, 0, 1), around(force * -(-TAMPERH20_POW2 + TAMPERH30_POW2)));
		assertThat(y.getDouble(2, 1, 0), around(force * -(-TAMPERV21_POW2 + TAMPERV22_POW2)));
		assertThat(y.getDouble(2, 1, 1), around(force * -(-TAMPERH21_POW2 + TAMPERH31_POW2)));
		assertThat(y.getDouble(2, 2, 0), around(force * -(-TAMPERV22_POW2 + TAMPERV23_POW2)));
		assertThat(y.getDouble(2, 2, 1), around(force * -(-TAMPERH22_POW2 + TAMPERH32_POW2)));
	}
}
