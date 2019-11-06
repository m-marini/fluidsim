package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class LocalContextEnergyFluxTest implements Constants {

	private static final double DELTA_T = SIZE / SPEED / 2;

	/**
	 * <p>
	 * energy
	 *
	 * <pre>
	 * 1 1 1
	 * 1 1 1
	 * 1 1 1
	 * </pre>
	 *
	 * speed
	 *
	 * <pre>
	 * (0,0) (0,0) (0,0)
	 * (1,0) (0,0) (0,0)
	 * (0,0) (0,0) (0,0)
	 * </pre>
	 * </p>
	 *
	 * @return
	 */
	private static SimulationContext1 context() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Nd4j.zeros(DataType.DOUBLE, 3, 3, 2);
		speed.putScalar(new int[] { 1, 0, 0 }, SPEED);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext1(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), DELTA_T);
	}

	/**
	 * <p>
	 * temp
	 *
	 * <pre>
	 * 1 1 1
	 * 1 1 1
	 * 1 1 1
	 * </pre>
	 *
	 * density
	 *
	 * <pre>
	 * 1 1 1
	 * 1 1 1.5
	 * 1 1 1
	 * </pre>
	 *
	 * speed
	 *
	 * <pre>
	 * (1,0) (1,0) (1,0)
	 * (1,0) (1,0) (1,0)
	 * (1,0) (1,0) (1,0)
	 * </pre>
	 * </p>
	 *
	 * @return
	 */
	private static SimulationContext1 context1() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		density.putScalar(new int[] { 1, 2 }, ISA_DENSITY * 1.5);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { SPEED, 0 }), 3, 3);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext1(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), DELTA_T);
	}

	/**
	 * <p>
	 * expected
	 *
	 * <pre>
	 * 0 0 0
	 * 1 0 0
	 * 0 0 0
	 * </pre>
	 * </p>
	 *
	 * chi * T * rho * V * S * U
	 *
	 * @return
	 */
	@Test
	public void testComputeEnergyFlux() {
		final double energy = ISA_TEMPERATURE * ISA_DENSITY * VOLUME * ISA_SPECIFIC_HEAT_CAPACITY;
		final double expected = energy * SPEED * AREA;
		final LocalContext1 ctx = new LocalContext1(context(), 1, 1);
		final INDArray y = ctx.computeEnergyFlux();

		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));
		assertThat(y.getDouble(0, 0), closeTo(0, 0));
		assertThat(y.getDouble(0, 1), closeTo(0, 0));
		assertThat(y.getDouble(0, 2), closeTo(0, 0));
		assertThat(y.getDouble(1, 0), closeTo(expected, expected * 1e-3));
		assertThat(y.getDouble(1, 1), closeTo(0, 0));
		assertThat(y.getDouble(1, 2), closeTo(0, 0));
		assertThat(y.getDouble(2, 0), closeTo(0, 0));
		assertThat(y.getDouble(2, 1), closeTo(0, 0));
		assertThat(y.getDouble(2, 2), closeTo(0, 0));
	}

	/**
	 * <p>
	 * expected
	 *
	 * <pre>
	 * 0 0 0
	 * 0 0 1
	 * 0 0 0
	 * </pre>
	 * </p>
	 *
	 * chi * T * rho * V * S * U
	 *
	 * @return
	 */
	@Test
	public void testComputePressurePower() {
		final double pressure = ISA_PRESSURE;
		final double expected = pressure * AREA * SPEED;

		final LocalContext1 ctx = new LocalContext1(context1(), 1, 1);
		final INDArray y = ctx.computePressurePower();

		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));
		assertThat(y.getDouble(0, 0), closeTo(0, 0));
		assertThat(y.getDouble(0, 1), closeTo(0, 0));
		assertThat(y.getDouble(0, 2), closeTo(0, 0));
		assertThat(y.getDouble(1, 0), closeTo(0, 0));
		assertThat(y.getDouble(1, 1), closeTo(0, 0));
		assertThat(y.getDouble(1, 2), closeTo(-expected, expected * 1e-3));
		assertThat(y.getDouble(2, 0), closeTo(0, 0));
		assertThat(y.getDouble(2, 1), closeTo(0, 0));
		assertThat(y.getDouble(2, 2), closeTo(0, 0));
	}
}
