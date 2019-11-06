package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mmarini.fluid.TestUtils.around;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class SimulationContextMassFluxTest implements Constants {

	private static final int[] CENTER_INDICES = new int[] { 1, 1 };
	private static final double ISA_DENSITY12 = ISA_DENSITY * 1.2;
	private static final double INTERVAL = SIZE / SPEED / 10;

	private static SimulationContext centerContext() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		density.putScalar(CENTER_INDICES, ISA_DENSITY12);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray speed = Nd4j.ones(3, 3, 2).mul(SPEED);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), INTERVAL);
	}

	public SimulationContextMassFluxTest() {
		Nd4j.create(new double[0]);
	}

	/**
	 * expected
	 * 
	 * <pre>
	 * -2   -1.1    0
	 * -1.1    0  1.1
	 *  0    1.1    2
	 * </pre>
	 */
	@Test
	public void testDeltaRho() {
		final double expected11 = ISA_DENSITY * SPEED * INTERVAL * AREA / VOLUME * 1.1;
		final double expected2 = ISA_DENSITY * SPEED * INTERVAL * AREA / VOLUME * 2;
		final double expected0 = 0;

		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getDeltaRho();
		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), around(-expected2));
		assertThat(y.getDouble(0, 1), around(-expected11));
		assertThat(y.getDouble(0, 2), around(expected0));

		assertThat(y.getDouble(1, 0), around(-expected11));
		assertThat(y.getDouble(1, 1), around(expected0));
		assertThat(y.getDouble(1, 2), around(expected11));

		assertThat(y.getDouble(2, 0), around(expected0));
		assertThat(y.getDouble(2, 1), around(expected11));
		assertThat(y.getDouble(2, 2), around(expected2));
	}

}
