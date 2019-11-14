package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mmarini.fluid.TestUtils.around;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class SimulationContextTest implements Constants, TamperConstants {

	private static SimulationContext centerContext() {
		final INDArray density = TAMPER.mul(ISA_DENSITY);
		final INDArray speed = Utils.vsmul(Utils.prefixBroadcast(Nd4j.ones(2).mul(SPEED), 3, 3), TAMPER);
		final INDArray mu = Nd4j.ones(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(SIZE, density, speed, ISA_TEMPERATURE, ISA_MOLECULAR_MASS, mu),
				0);
	}

	public SimulationContextTest() {
		Nd4j.create(new double[0]);
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * 0   0 0
	 * 1 1.1 1
	 * 1 1.1 1
	 * 0   0 0
	 * </pre>
	 */
	@Test
	public void testHDensity() {
		final double expectedDensity = ISA_DENSITY;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.buildHDensity();
		assertThat(y.shape(), equalTo(new long[] { 4, 3 }));

		assertThat(y.getDouble(0, 0), around(expectedDensity * TAMPERH00));
		assertThat(y.getDouble(0, 1), around(expectedDensity * TAMPERH01));
		assertThat(y.getDouble(0, 2), around(expectedDensity * TAMPERH02));

		assertThat(y.getDouble(1, 0), around(expectedDensity * TAMPERH10));
		assertThat(y.getDouble(1, 1), around(expectedDensity * TAMPERH11));
		assertThat(y.getDouble(1, 2), around(expectedDensity * TAMPERH12));

		assertThat(y.getDouble(2, 0), around(expectedDensity * TAMPERH20));
		assertThat(y.getDouble(2, 1), around(expectedDensity * TAMPERH21));
		assertThat(y.getDouble(2, 2), around(expectedDensity * TAMPERH22));

		assertThat(y.getDouble(3, 0), around(expectedDensity * TAMPERH30));
		assertThat(y.getDouble(3, 1), around(expectedDensity * TAMPERH31));
		assertThat(y.getDouble(3, 2), around(expectedDensity * TAMPERH32));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * (0,0) (0,0)   (0,0)
	 * (1,0) (1.1,0) (1,0)
	 * (1,0) (1.1,0) (1,0)
	 * (0,0) (0,0)   (0,0)
	 * </pre>
	 */
	@Test
	public void testHSpeed() {
		final double expected0 = 0;
		final double expected = SPEED;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.buildHSpeed();
		assertThat(y.shape(), equalTo(new long[] { 4, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(expected0));
		assertThat(y.getDouble(0, 0, 1), around(expected0));
		assertThat(y.getDouble(0, 1, 0), around(expected0));
		assertThat(y.getDouble(0, 1, 1), around(expected0));
		assertThat(y.getDouble(0, 2, 0), around(expected0));
		assertThat(y.getDouble(0, 2, 1), around(expected0));

		assertThat(y.getDouble(1, 0, 0), around(expected * TAMPERH10));
		assertThat(y.getDouble(1, 0, 1), around(expected * TAMPERH10));
		assertThat(y.getDouble(1, 1, 0), around(expected * TAMPERH11));
		assertThat(y.getDouble(1, 1, 1), around(expected * TAMPERH11));
		assertThat(y.getDouble(1, 2, 0), around(expected * TAMPERH12));
		assertThat(y.getDouble(1, 2, 1), around(expected * TAMPERH12));

		assertThat(y.getDouble(2, 0, 0), around(expected * TAMPERH20));
		assertThat(y.getDouble(2, 0, 1), around(expected * TAMPERH20));
		assertThat(y.getDouble(2, 1, 0), around(expected * TAMPERH21));
		assertThat(y.getDouble(2, 1, 1), around(expected * TAMPERH21));
		assertThat(y.getDouble(2, 2, 0), around(expected * TAMPERH22));
		assertThat(y.getDouble(2, 2, 1), around(expected * TAMPERH22));

		assertThat(y.getDouble(3, 0, 0), around(expected0));
		assertThat(y.getDouble(3, 0, 1), around(expected0));
		assertThat(y.getDouble(3, 1, 0), around(expected0));
		assertThat(y.getDouble(3, 1, 1), around(expected0));
		assertThat(y.getDouble(3, 2, 0), around(expected0));
		assertThat(y.getDouble(3, 2, 1), around(expected0));
	}

	@Test
	public void testMass() {
		final double expectedMass = ISA_DENSITY * VOLUME;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getMass();
		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), around(expectedMass * TAMPER00));
		assertThat(y.getDouble(0, 1), around(expectedMass * TAMPER01));
		assertThat(y.getDouble(0, 2), around(expectedMass * TAMPER02));

		assertThat(y.getDouble(1, 0), around(expectedMass * TAMPER10));
		assertThat(y.getDouble(1, 1), around(expectedMass * TAMPER11));
		assertThat(y.getDouble(1, 2), around(expectedMass * TAMPER12));

		assertThat(y.getDouble(2, 0), around(expectedMass * TAMPER20));
		assertThat(y.getDouble(2, 1), around(expectedMass * TAMPER21));
		assertThat(y.getDouble(2, 2), around(expectedMass * TAMPER22));
	}

	@Test
	public void testPressure() {
		final double expectedPressure = ISA_PRESSURE;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getPressure();
		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), around(expectedPressure * TAMPER00));
		assertThat(y.getDouble(0, 1), around(expectedPressure * TAMPER01));
		assertThat(y.getDouble(0, 2), around(expectedPressure * TAMPER02));

		assertThat(y.getDouble(1, 0), around(expectedPressure * TAMPER10));
		assertThat(y.getDouble(1, 1), around(expectedPressure * TAMPER11));
		assertThat(y.getDouble(1, 2), around(expectedPressure * TAMPER12));

		assertThat(y.getDouble(2, 0), around(expectedPressure * TAMPER20));
		assertThat(y.getDouble(2, 1), around(expectedPressure * TAMPER21));
		assertThat(y.getDouble(2, 2), around(expectedPressure * TAMPER22));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * 0   1   1 0
	 * 0 1.1 1.1 0
	 * 0   1   1 0
	 * </pre>
	 */

	@Test
	public void testVDensity() {
		final double expectedDensity = ISA_DENSITY;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.buildVDensity();
		assertThat(y.shape(), equalTo(new long[] { 3, 4 }));

		assertThat(y.getDouble(0, 0), around(expectedDensity * TAMPERV00));
		assertThat(y.getDouble(0, 1), around(expectedDensity * TAMPERV01));
		assertThat(y.getDouble(0, 2), around(expectedDensity * TAMPERV02));
		assertThat(y.getDouble(0, 3), around(expectedDensity * TAMPERV03));

		assertThat(y.getDouble(1, 0), around(expectedDensity * TAMPERV10));
		assertThat(y.getDouble(1, 1), around(expectedDensity * TAMPERV11));
		assertThat(y.getDouble(1, 2), around(expectedDensity * TAMPERV12));
		assertThat(y.getDouble(1, 3), around(expectedDensity * TAMPERV13));

		assertThat(y.getDouble(2, 0), around(expectedDensity * TAMPERV20));
		assertThat(y.getDouble(2, 1), around(expectedDensity * TAMPERV21));
		assertThat(y.getDouble(2, 2), around(expectedDensity * TAMPERV22));
		assertThat(y.getDouble(2, 3), around(expectedDensity * TAMPERV23));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * (0,0) (1,0) (1,0)   (0,0)
	 * (0,0) (1,0) (1.1,0) (0,0)
	 * (0,0) (1,0) (1,0)   (0,0)
	 * </pre>
	 */
	@Test
	public void testVSpeed() {
		final double expected0 = 0;
		final double expected = SPEED;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.buildVSpeed();
		assertThat(y.shape(), equalTo(new long[] { 3, 4, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(expected0));
		assertThat(y.getDouble(0, 0, 1), around(expected0));
		assertThat(y.getDouble(0, 1, 0), around(expected * TAMPERV01));
		assertThat(y.getDouble(0, 1, 1), around(expected * TAMPERV01));
		assertThat(y.getDouble(0, 2, 0), around(expected * TAMPERV02));
		assertThat(y.getDouble(0, 2, 1), around(expected * TAMPERV02));
		assertThat(y.getDouble(0, 3, 0), around(expected0));
		assertThat(y.getDouble(0, 3, 1), around(expected0));

		assertThat(y.getDouble(1, 0, 0), around(expected0));
		assertThat(y.getDouble(1, 0, 1), around(expected0));
		assertThat(y.getDouble(1, 1, 0), around(expected * TAMPERV11));
		assertThat(y.getDouble(1, 1, 1), around(expected * TAMPERV11));
		assertThat(y.getDouble(1, 2, 0), around(expected * TAMPERV12));
		assertThat(y.getDouble(1, 2, 1), around(expected * TAMPERV12));
		assertThat(y.getDouble(1, 3, 0), around(expected0));
		assertThat(y.getDouble(1, 3, 1), around(expected0));

		assertThat(y.getDouble(2, 0, 0), around(expected0));
		assertThat(y.getDouble(2, 0, 1), around(expected0));
		assertThat(y.getDouble(2, 1, 0), around(expected * TAMPERV21));
		assertThat(y.getDouble(2, 1, 1), around(expected * TAMPERV21));
		assertThat(y.getDouble(2, 2, 0), around(expected * TAMPERV22));
		assertThat(y.getDouble(2, 2, 1), around(expected * TAMPERV22));
		assertThat(y.getDouble(2, 3, 0), around(expected0));
		assertThat(y.getDouble(2, 3, 1), around(expected0));
	}
}
