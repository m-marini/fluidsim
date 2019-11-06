package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class LocalContextMomentumFluxTest implements Constants {

	private static final double EPSILON = 10e-3;

	/**
	 * Returns the context for constant y axis flux test
	 *
	 * @return
	 */
	private static SimulationContext1 contextDownward() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { 0, SPEED }), 3, 3);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext1(new UniverseImpl(SIZE, density, speed, temperature, 0, 0, massConstraints), 0);
	}

	/**
	 * Returns the context for constant x axis flux test
	 *
	 * speed =
	 *
	 * @return
	 */
	private static SimulationContext1 contextRightContraint() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { SPEED, 0 }), 3, 3);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		massConstraints.putScalar(new int[] { 1, 1 }, 1.0);
		return new SimulationContext1(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), 0);
	}

	/**
	 * Returns the context for constant y axis flux test with different density
	 *
	 * @return
	 */
	private static SimulationContext1 contextRightInomogeneus() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		density.putScalar(new int[] { 1, 1 }, ISA_DENSITY * 2);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { SPEED, 0 }), 3, 3);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext1(new UniverseImpl(SIZE, density, speed, temperature, 0, 0, massConstraints), 0);
	}

	/**
	 * Returns the context for constant x axis flux test
	 *
	 * speed =
	 *
	 * @return
	 */
	private static SimulationContext1 contextRightSpeed() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { SPEED, 0 }), 3, 3);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext1(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), 0);
	}

	public LocalContextMomentumFluxTest() {
		Nd4j.create(new double[0]);
	}

	/**
	 * Test constant flux to right
	 * <p>
	 * input:
	 *
	 * <pre>
	 * (0, 0), (0,0), (0,0)
	 * (0, 0), (1,0), (1,0)
	 * (0, 0), (1,0), (1,0)
	 * </pre>
	 *
	 * flux
	 *
	 * <pre>
	 * (0, 0), (0,0), (0,0)
	 * (0, 0), (0,0), (-2,0)
	 * (0, 0), (0,0), (0,0)
	 * </pre>
	 *
	 * force
	 *
	 * <pre>
	 * (0, 0), (0,0), (0,0)
	 * (0, 0), (0,0), (0,0)
	 * (0, 0), (0,0), (0,0)
	 * </pre>
	 *
	 * expected flux
	 *
	 * <pre>
	 * (-2, 0)
	 * </pre>
	 */
	@Test
	public void testComputeFreeMomentumFlux00() {
		final LocalContext1 ctx = new LocalContext1(contextRightSpeed(), 1, 1);
		final INDArray y = ctx.getFreeMomentumFlux();

		assertThat(y.shape(), equalTo(new long[] { 2 }));
		assertThat(y.getDouble(0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1), closeTo(0, EPSILON));

	}

	/**
	 * Test constant flux to right
	 * <p>
	 * input:
	 *
	 * <pre>
	 * (0, 0), (1,0), (1,0)
	 * (0, 0), (1,0), (0,0)
	 * (0, 0), (1,0), (1,0)
	 * </pre>
	 *
	 * flux
	 *
	 * <pre>
	 * (0, 0), (0,0), (0,0)
	 * (0, 0), (0,0), (-1,0)
	 * (0, 0), (0,0), (0,0)
	 * </pre>
	 *
	 * force
	 *
	 * <pre>
	 * (0, 0), (0,0), (0,0)
	 * (0, 0), (0,0), (0,0)
	 * (0, 0), (0,0), (0,0)
	 * </pre>
	 *
	 * expected flux
	 *
	 * <pre>
	 * (-1, 0)
	 * </pre>
	 */
	@Test
	public void testComputeFreeMomentumFlux10_1() {
		final LocalContext1 ctx = new LocalContext1(contextRightContraint(), 1, 1);
		final INDArray y = ctx.getFreeMomentumFlux();

		assertThat(y.shape(), equalTo(new long[] { 2 }));
		assertThat(y.getDouble(0), closeTo(SPEED * ISA_DENSITY * VOLUME, EPSILON));
		assertThat(y.getDouble(1), closeTo(0, EPSILON));

	}

	/**
	 * Test constant flux to right
	 * <p>
	 * input:
	 *
	 * <pre>
	 * (1, 0), (1,0), (1,0)
	 * (1, 0), (1,0), (1,0)
	 * (1, 0), (1,0), (1,0)
	 * </pre>
	 *
	 * expected flux
	 *
	 * <pre>
	 * (0, 0)
	 * </pre>
	 */
	@Test
	public void testComputeFreeMomentumFlux11() {
		final LocalContext1 ctx = new LocalContext1(contextRightSpeed(), 1, 1);
		final INDArray y = ctx.getFreeMomentumFlux();

		assertThat(y.shape(), equalTo(new long[] { 2 }));
		assertThat(y.getDouble(0), closeTo(-2 * SPEED * ISA_DENSITY * VOLUME, EPSILON));
		assertThat(y.getDouble(1), closeTo(0, EPSILON));

	}

	/**
	 * Test constant flux to right
	 * <p>
	 * input:
	 *
	 * <pre>
	 * (1, 0), (1,0), (1,0)
	 * (1, 0), (1,0), (1,0)
	 * (1, 0), (1,0), (1,0)
	 * </pre>
	 *
	 * expected:
	 *
	 * <pre>
	 * (0, 0), (0,0), (0,0)
	 * (2, 0), (0,0), (-2,0)
	 * (0, 0), (0,0), (0,0)
	 * </pre>
	 * </p>
	 */
	@Test
	public void testComputeMomentumFlux1() {
		final LocalContext1 ctx = new LocalContext1(contextRightSpeed(), 1, 1);
		final INDArray y = ctx.computeMomentumFlux();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0, 0), closeTo(SPEED * ISA_DENSITY * VOLUME * 2, EPSILON));
		assertThat(y.getDouble(1, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 0), closeTo(-2 * SPEED * ISA_DENSITY * VOLUME, EPSILON));
		assertThat(y.getDouble(1, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(2, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}

	/**
	 * Test constant flux downward
	 * <p>
	 * input:
	 *
	 * <pre>
	 * (0,1), (0,1), (0,1)
	 * (0,1), (0,1), (0,1)
	 * (0,1), (0,1), (0,1)
	 * </pre>
	 *
	 * expected:
	 *
	 * <pre>
	 * (0, 0), (0,2), (0,0)
	 * (0, 0), (0,0), (0,0)
	 * (0, 0), (0,-2), (0,0)
	 * </pre>
	 * </p>
	 */
	@Test
	public void testComputeMomentumFlux2() {
		final LocalContext1 ctx = new LocalContext1(contextDownward(), 1, 1);
		final INDArray y = ctx.computeMomentumFlux();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(SPEED * ISA_DENSITY * VOLUME * 2, EPSILON));
		assertThat(y.getDouble(0, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(2, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 1), closeTo(-SPEED * ISA_DENSITY * VOLUME * 2, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}

	/**
	 * Test constant flux to right with center double density
	 * <p>
	 * input:
	 *
	 * <pre>
	 * (1, 0), (1,0), (1,0)
	 * (1, 0), (2,0), (1,0)
	 * (1, 0), (1,0), (1,0)
	 * </pre>
	 *
	 * expected:
	 *
	 * <pre>
	 * (0, 0), (0,0), (0,0)
	 * (3, 0), (0,0), (-1,0)
	 * (0, 0), (0,0), (0,0)
	 * </pre>
	 * </p>
	 */
	@Test
	public void testComputeMomentumFlux3() {
		final LocalContext1 ctx = new LocalContext1(contextRightInomogeneus(), 1, 1);
		final INDArray y = ctx.computeMomentumFlux();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0, 0), closeTo(3 * SPEED * ISA_DENSITY * VOLUME, EPSILON));
		assertThat(y.getDouble(1, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 0), closeTo(-1 * SPEED * ISA_DENSITY * VOLUME, EPSILON));
		assertThat(y.getDouble(1, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(2, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}

	/**
	 * Test constant flux to right
	 * <p>
	 * input:
	 *
	 * <pre>
	 * (0, 0), (0,0), (0,0)
	 * (0, 0), (1,0), (1,0)
	 * (0, 0), (1,0), (1,0)
	 * </pre>
	 *
	 * expected:
	 *
	 * <pre>
	 * (0, 0), (0,0), (0,0)
	 * (1, 0), (0,0), (-2,0)
	 * (0, 0), (0,0), (0,0)
	 * </pre>
	 * </p>
	 */
	@Test
	public void testComputeMomentumFlux4() {
		final LocalContext1 ctx = new LocalContext1(contextRightSpeed(), 0, 0);
		final INDArray y = ctx.computeMomentumFlux();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0, 0), closeTo(SPEED * ISA_DENSITY * VOLUME, EPSILON));
		assertThat(y.getDouble(1, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 0), closeTo(-2 * SPEED * ISA_DENSITY * VOLUME, EPSILON));
		assertThat(y.getDouble(1, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(2, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}
}
