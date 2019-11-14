package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mmarini.fluid.TestUtils.around;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class SimulationContextBuildTest implements Constants, TamperConstants {

	private static final double INTERVAL = CELL_PERIOD / 10;

	private static SimulationContext centerContext() {
		final INDArray density = TAMPER.mul(ISA_DENSITY);
		final INDArray speed = Utils.vsmul(Utils.prefixBroadcast(Nd4j.ones(2).mul(SPEED), 3, 3), TAMPER);
		final INDArray mu = Nd4j.ones(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(SIZE, density, speed, ISA_TEMPERATURE, ISA_MOLECULAR_MASS, mu),
				INTERVAL);
	}

	public SimulationContextBuildTest() {
		Nd4j.create(new double[0]);
	}

	@Test
	public void testBuild() {

		final SimulationContext ctx = centerContext();
		final Universe uni = ctx.build().blockingGet();

		assertThat(uni, notNullValue());

		// Density assertions

		final INDArray rho = uni.getDensity();
		final double rho0 = ISA_DENSITY;
		final double dRho0 = ISA_DENSITY * SPEED * INTERVAL * AREA / VOLUME;

		assertThat(rho.shape(), equalTo(new long[] { 3, 3 }));
		assertThat(rho.getDouble(0, 0), around(rho0 * TAMPER00 + dRho0 * (-TAMPERH10_POW2 - TAMPERV01_POW2)));
		assertThat(rho.getDouble(0, 1),
				around(rho0 * TAMPER01 + dRho0 * (-TAMPERH11_POW2 + TAMPERV01_POW2 - TAMPERV02_POW2)));
		assertThat(rho.getDouble(0, 2), around(rho0 * TAMPER02 + dRho0 * (-TAMPERH12_POW2 + TAMPERV02_POW2)));

		assertThat(rho.getDouble(1, 0),
				around(rho0 * TAMPER10 + dRho0 * (TAMPERH10_POW2 - TAMPERH20_POW2 - TAMPERV11_POW2)));
		assertThat(rho.getDouble(1, 1),
				around(rho0 * TAMPER11 + dRho0 * (TAMPERH11_POW2 - TAMPERH21_POW2 + TAMPERV11_POW2 - TAMPERV12_POW2)));
		assertThat(rho.getDouble(1, 2),
				around(rho0 * TAMPER12 + dRho0 * (TAMPERH12_POW2 - TAMPERH22_POW2 + TAMPERV12_POW2)));

		assertThat(rho.getDouble(2, 0), around(rho0 * TAMPER20 + dRho0 * (TAMPERH20_POW2 - TAMPERV21_POW2)));
		assertThat(rho.getDouble(2, 1),
				around(rho0 * TAMPER21 + dRho0 * (TAMPERH21_POW2 + TAMPERV21_POW2 - TAMPERV22_POW2)));
		assertThat(rho.getDouble(2, 2), around(rho0 * TAMPER22 + dRho0 * (TAMPERH22_POW2 + TAMPERV22_POW2)));

		// Speed assertions

		final double flux0 = SPEED * SPEED * INTERVAL * AREA / VOLUME;
		final double force0 = ISA_PRESSURE * INTERVAL * AREA / VOLUME / ISA_DENSITY;
		final double u0 = SPEED;

		final INDArray y = uni.getSpeed();
		assertThat(y.getDouble(0, 0, 0), around(u0 * TAMPER00
				+ (flux0 * (-TAMPERH10_POW3 - TAMPERV01_POW3) + force0 * -(-TAMPERV00 + TAMPERV01)) / TAMPER00));
		assertThat(y.getDouble(0, 0, 1), around(u0 * TAMPER00
				+ (flux0 * (-TAMPERH10_POW3 - TAMPERV01_POW3) + force0 * -(-TAMPERH00 + TAMPERH10)) / TAMPER00));
		assertThat(y.getDouble(0, 1, 0), around(u0 * TAMPER01
				+ (flux0 * (-TAMPERH11_POW3 + TAMPERV01_POW3 - TAMPERV02_POW3) + force0 * -(-TAMPERV01 + TAMPERV02))
						/ TAMPER01));
		assertThat(y.getDouble(0, 1, 1), around(u0 * TAMPER01
				+ (flux0 * (-TAMPERH11_POW3 + TAMPERV01_POW3 - TAMPERV02_POW3) + force0 * -(-TAMPERH01 + TAMPERH11))
						/ TAMPER01));
		assertThat(y.getDouble(0, 2, 0), around(u0 * TAMPER02
				+ (flux0 * (-TAMPERH12_POW3 + TAMPERV02_POW3) + force0 * -(-TAMPERV02 + TAMPERV03)) / TAMPER02));
		assertThat(y.getDouble(0, 2, 1), around(u0 * TAMPER02
				+ (flux0 * (-TAMPERH12_POW3 + TAMPERV02_POW3) + force0 * -(-TAMPERH02 + TAMPERH12)) / TAMPER02));

		assertThat(y.getDouble(1, 0, 0), around(u0 * TAMPER10
				+ (flux0 * (TAMPERH10_POW3 - TAMPERH20_POW3 - TAMPERV11_POW3) + force0 * -(-TAMPERV10 + TAMPERV11))
						/ TAMPER10));
		assertThat(y.getDouble(1, 0, 1), around(u0 * TAMPER10
				+ (flux0 * (TAMPERH10_POW3 - TAMPERH20_POW3 - TAMPERV11_POW3) + force0 * -(-TAMPERH10 + TAMPERH20))
						/ TAMPER10));
		assertThat(y.getDouble(1, 1, 0),
				around(u0 * TAMPER11 + (flux0 * (TAMPERH11_POW3 - TAMPERH21_POW3 + TAMPERV11_POW3 - TAMPERV12_POW3)
						+ force0 * -(-TAMPERV11 + TAMPERV12)) / TAMPER11));
		assertThat(y.getDouble(1, 1, 1),
				around(u0 * TAMPER11 + (flux0 * (TAMPERH11_POW3 - TAMPERH21_POW3 + TAMPERV11_POW3 - TAMPERV12_POW3)
						+ force0 * -(-TAMPERH11 + TAMPERH21)) / TAMPER11));
		assertThat(y.getDouble(1, 2, 0), around(u0 * TAMPER12
				+ (flux0 * (TAMPERH12_POW3 - TAMPERH22_POW3 + TAMPERV12_POW3) + force0 * -(-TAMPERV12 + TAMPERV13))
						/ TAMPER12));
		assertThat(y.getDouble(1, 2, 1), around(u0 * TAMPER12
				+ (flux0 * (TAMPERH12_POW3 - TAMPERH22_POW3 + TAMPERV12_POW3) + force0 * -(-TAMPERH12 + TAMPERH22))
						/ TAMPER12));

		assertThat(y.getDouble(2, 0, 0), around(u0 * TAMPER20
				+ (flux0 * (TAMPERH20_POW3 - TAMPERV21_POW3) + force0 * -(-TAMPERV20 + TAMPERV21)) / TAMPER20));
		assertThat(y.getDouble(2, 0, 1), around(u0 * TAMPER20
				+ (flux0 * (TAMPERH20_POW3 - TAMPERV21_POW3) + force0 * -(-TAMPERH20 + TAMPERH30)) / TAMPER20));
		assertThat(y.getDouble(2, 1, 0), around(u0 * TAMPER21
				+ (flux0 * (TAMPERH21_POW3 + TAMPERV21_POW3 - TAMPERV22_POW3) + force0 * -(-TAMPERV21 + TAMPERV22))
						/ TAMPER21));
		assertThat(y.getDouble(2, 1, 1), around(u0 * TAMPER21
				+ (flux0 * (TAMPERH21_POW3 + TAMPERV21_POW3 - TAMPERV22_POW3) + force0 * -(-TAMPERH21 + TAMPERH31))
						/ TAMPER21));
		assertThat(y.getDouble(2, 2, 0), around(u0 * TAMPER22
				+ (flux0 * (TAMPERH22_POW3 + TAMPERV22_POW3) + force0 * -(-TAMPERV22 + TAMPERV23)) / TAMPER22));
		assertThat(y.getDouble(2, 2, 1), around(u0 * TAMPER22
				+ (flux0 * (TAMPERH22_POW3 + TAMPERV22_POW3) + force0 * -(-TAMPERH22 + TAMPERH32)) / TAMPER22));
	}
}
