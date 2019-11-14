package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mmarini.fluid.TestUtils.around;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class SimulationContextDensityTest implements Constants, TamperConstants {

	private static final double INTERVAL = CELL_PERIOD / 10;

	private static SimulationContext centerContext() {
		final INDArray density = TAMPER.mul(ISA_DENSITY);
		final INDArray speed = Utils.vsmul(Utils.prefixBroadcast(Nd4j.ones(2).mul(SPEED), 3, 3), TAMPER);
		final INDArray mu = Nd4j.ones(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(SIZE, density, speed, ISA_TEMPERATURE, ISA_MOLECULAR_MASS, mu),
				INTERVAL);
	}

	public SimulationContextDensityTest() {
		Nd4j.create(new double[0]);
	}

	/**
	 * expected rho*u*dt*S/V
	 *
	 * <pre>
	 * -h10^2-v01^2 -h11^2+v01^2-v02^2 -h10^2+V02^2
	 * -1.1    0  1.1
	 *  0    1.1    2
	 * </pre>
	 */
	@Test
	public void testDeltaRho() {
		final double expected = ISA_DENSITY * SPEED * INTERVAL * AREA / VOLUME;

		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.computeDeltaRho();
		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), around(expected * (-TAMPERH10_POW2 - TAMPERV01_POW2)));
		assertThat(y.getDouble(0, 1), around(expected * (-TAMPERH11_POW2 + TAMPERV01_POW2 - TAMPERV02_POW2)));
		assertThat(y.getDouble(0, 2), around(expected * (-TAMPERH12_POW2 + TAMPERV02_POW2)));

		assertThat(y.getDouble(1, 0), around(expected * (TAMPERH10_POW2 - TAMPERH20_POW2 - TAMPERV11_POW2)));
		assertThat(y.getDouble(1, 1),
				around(expected * (TAMPERH11_POW2 - TAMPERH21_POW2 + TAMPERV11_POW2 - TAMPERV12_POW2)));
		assertThat(y.getDouble(1, 2), around(expected * (TAMPERH12_POW2 - TAMPERH22_POW2 + TAMPERV12_POW2)));

		assertThat(y.getDouble(2, 0), around(expected * (TAMPERH20_POW2 - TAMPERV21_POW2)));
		assertThat(y.getDouble(2, 1), around(expected * (TAMPERH21_POW2 + TAMPERV21_POW2 - TAMPERV22_POW2)));
		assertThat(y.getDouble(2, 2), around(expected * (TAMPERH22_POW2 + TAMPERV22_POW2)));
	}
}
