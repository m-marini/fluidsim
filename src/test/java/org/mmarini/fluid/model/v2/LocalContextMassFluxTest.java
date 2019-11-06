package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class LocalContextMassFluxTest implements Constants {

	private static final double EPSILON = 1e-12;

	private static SimulationContext1 context() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { SPEED, 0 }), 3, 3);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext1(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), 0);
	}

	private static SimulationContext1 context1() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { SPEED, 0 }), 3, 3);
		speed.putScalar(new int[] { 1, 0, 0 }, SPEED * 1.5);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext1(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), 0);
	}

	public LocalContextMassFluxTest() {
		Nd4j.create(new double[0]);
	}

	/**
	 * <p>
	 * density
	 *
	 * <pre>
	 * 1 1 1
	 * 1 1 1
	 * 1 1 1
	 * </pre>
	 * </p>
	 *
	 * @return
	 */

	@Test
	public void testComputeDeltaMass() {
		final LocalContext1 ctx = new LocalContext1(context(), 1, 1);
		final double y = ctx.computeDeltaMass();
		assertThat(y, closeTo(0, EPSILON));
	}

	/**
	 * <p>
	 * speed
	 *
	 * <pre>
	 * (1,0) (1,0) (1,0)
	 * (1.5,0) (1,0) (1,0)
	 * (1,0) (1,0) (1,0)
	 * </pre>
	 * </p>
	 *
	 * @return
	 */

	@Test
	public void testComputeDeltaMass1() {
		final LocalContext1 ctx = new LocalContext1(context1(), 1, 1);
		final double y = ctx.computeDeltaMass();
		assertThat(y, closeTo(0.5 * SPEED * ISA_DENSITY * VOLUME * AREA, EPSILON));
	}
}
