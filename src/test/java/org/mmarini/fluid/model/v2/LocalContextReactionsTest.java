package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class LocalContextReactionsTest implements Constants {

	private static final double DELTA_T = SIZE / SPEED / 2;
	private static final double EPSILON = 10e-6;

	/**
	 * <p>
	 * speed
	 *
	 * <pre>
	 * (1,0) (1,0) (1,0)
	 * (1,0) (0,0) (1,0)
	 * (1,0) (1,0) (1,0)
	 * </pre>
	 *
	 * constraints
	 *
	 * <pre>
	 * 0 0 0
	 * 0 1 0
	 * 0 0 0
	 * </pre>
	 * </p>
	 *
	 * @return
	 */
	private static SimulationContext contextCenter() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { SPEED, 0 }), 3, 3);
		speed.putScalar(new int[] { 1, 1, 0 }, 0);
		speed.putScalar(new int[] { 1, 1, 1 }, 0);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		massConstraints.putScalar(new int[] { 1, 1 }, 1);
		return new SimulationContext(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS_,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), DELTA_T);
	}

	/**
	 * <p>
	 * speed
	 *
	 * <pre>
	 * (0,0) (0,0) (0,0)
	 * (1,0) (0,0) (0,0)
	 * (0,0) (0,0) (0,0)
	 * </pre>
	 *
	 * constraints
	 *
	 * <pre>
	 * 0 0 0
	 * 0 0 1
	 * 0 0 0
	 * </pre>
	 * </p>
	 *
	 * @return
	 */
	private static SimulationContext contextWest() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Nd4j.zeros(3, 3, 2);
		speed.putScalar(new int[] { 1, 0, 0 }, SPEED);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		massConstraints.putScalar(new int[] { 1, 2 }, 1);
		return new SimulationContext(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS_,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), DELTA_T);
	}

	/**
	 * <p>
	 * speed
	 *
	 * <pre>
	 * (0,0) (0,0) (0,0)
	 * (1,1) (0,0) (0,0)
	 * (0,0) (0,0) (0,0)
	 * </pre>
	 *
	 * constraints
	 *
	 * <pre>
	 * 0 0 0
	 * 0 0 1
	 * 0 1 0
	 * </pre>
	 * </p>
	 *
	 * @return
	 */
	private static SimulationContext contextWestAndSouth() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Nd4j.zeros(3, 3, 2);
		speed.putScalar(new int[] { 1, 0, 0 }, SPEED);
		speed.putScalar(new int[] { 1, 0, 1 }, SPEED);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		massConstraints.putScalar(new int[] { 1, 2 }, 1);
		massConstraints.putScalar(new int[] { 2, 1 }, 1);
		return new SimulationContext(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS_,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), DELTA_T);
	}

	private static Integer[] toIntegerArray(final int[] ary) {
		final Integer[] result = new Integer[ary.length];
		for (int i = 0; i < ary.length; i++) {
			result[i] = Integer.valueOf(ary[i]);
		}
		return result;
	}

	public LocalContextReactionsTest() {
		Nd4j.create(new double[0]);
	}

	@Test
	public void testComputeReactionSurfaces00() {
		final LocalContext ctx = new LocalContext(contextCenter(), 0, 0);
		final List<int[]> y = ctx.computeReactionSurfaces();
		assertThat(y, empty());
	}

	@Test
	public void testComputeReactionSurfaces01() {
		final LocalContext ctx = new LocalContext(contextCenter(), 0, 1);
		final List<int[]> y = ctx.computeReactionSurfaces();
		assertThat(y, empty());
	}

	@Test
	public void testComputeReactionSurfaces10() {
		final LocalContext ctx = new LocalContext(contextCenter(), 1, 0);
		final List<int[]> y = ctx.computeReactionSurfaces();
		assertThat(y, hasSize(1));
		assertThat(toIntegerArray(y.get(0)), arrayContaining(1, 2));
	}

	@Test
	public void testComputeReactionSurfaces11() {
		final LocalContext ctx = new LocalContext(contextCenter(), 1, 1);
		final List<int[]> y = ctx.computeReactionSurfaces();
		assertThat(y, empty());
	}

	@Test
	public void testComputeReactiveSurfaces00() {
		final LocalContext ctx = new LocalContext(contextCenter(), 0, 0);
		final List<int[]> y = ctx.computeReactiveSurfaces();
		assertThat(y, hasSize(2));
		assertThat(toIntegerArray(y.get(0)), arrayContaining(0, 1));
		assertThat(toIntegerArray(y.get(1)), arrayContaining(1, 0));
	}

	@Test
	public void testComputeReactiveSurfaces01() {
		final LocalContext ctx = new LocalContext(contextCenter(), 0, 1);
		final List<int[]> y = ctx.computeReactiveSurfaces();
		assertThat(y, hasSize(2));
		assertThat(toIntegerArray(y.get(0)), arrayContaining(0, 1));
		assertThat(toIntegerArray(y.get(1)), arrayContaining(2, 1));
	}

	@Test
	public void testComputeReactiveSurfaces10() {
		final LocalContext ctx = new LocalContext(contextCenter(), 1, 0);
		final List<int[]> y = ctx.computeReactiveSurfaces();
		assertThat(y, hasSize(2));
		assertThat(toIntegerArray(y.get(0)), arrayContaining(1, 0));
		assertThat(toIntegerArray(y.get(1)), arrayContaining(1, 2));
	}

	@Test
	public void testComputeReactiveSurfaces11() {
		final LocalContext ctx = new LocalContext(contextCenter(), 1, 1);
		final List<int[]> y = ctx.computeReactiveSurfaces();
		assertThat(y, empty());
	}

	/**
	 * Test compute V
	 * <p>
	 *
	 * <pre>
	 * V = (q22 + Delta q') n_k / Delta t
	 * </pre>
	 *
	 * constraint
	 *
	 * <pre>
	 * 0 0 0
	 * 0 0 1
	 * 0 0 0
	 * </pre>
	 *
	 * speed
	 *
	 * <pre>
	 * (0,0) (0,0) (0,0)
	 * (1,0) (0,0) (0,0)
	 * (0,0) (0,0) (0,0)
	 * </pre>
	 *
	 * </p>
	 *
	 * expected v = -q u s = rho v u u s
	 */
	@Test
	public void testComputeVWest() {
		final LocalContext ctx = new LocalContext(contextWest(), 1, 1);
		final INDArray y = ctx.computeV();

		final double expected = ISA_DENSITY * VOLUME * SPEED * SPEED * AREA;

		assertThat(y.shape(), equalTo(new long[] { 1 }));
		assertThat(y.getDouble(0), closeTo(expected, EPSILON));
	}

	/**
	 * Test compute V
	 * <p>
	 *
	 * <pre>
	 * V = (q22 + Delta q') n_k / Delta t
	 * </pre>
	 *
	 * constraint
	 *
	 * <pre>
	 * 0 0 0
	 * 0 0 1
	 * 0 0 0
	 * </pre>
	 *
	 * speed
	 *
	 * <pre>
	 * (0,0) (0,0) (0,0)
	 * (1,0) (0,0) (0,0)
	 * (0,0) (0,0) (0,0)
	 * </pre>
	 *
	 * </p>
	 *
	 * expected v = -q u s = rho v u u s
	 */
	@Test
	public void testComputeVWestAndSouth() {
		final LocalContext ctx = new LocalContext(contextWestAndSouth(), 1, 1);
		final INDArray y = ctx.computeV();

		final double expected = ISA_DENSITY * VOLUME * SPEED * SPEED * AREA;

		assertThat(y.shape(), equalTo(new long[] { 2 }));
		assertThat(y.getDouble(0), closeTo(expected, EPSILON));
		assertThat(y.getDouble(1), closeTo(expected, EPSILON));
	}

	@Test
	public void testLocalConstraints00() {
		final LocalContext ctx = new LocalContext(contextCenter(), 0, 0);

		final INDArray y = ctx.getConstraints();

		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), closeTo(1, EPSILON));
		assertThat(y.getDouble(0, 1), closeTo(1, EPSILON));
		assertThat(y.getDouble(0, 2), closeTo(1, EPSILON));

		assertThat(y.getDouble(1, 0), closeTo(1, EPSILON));
		assertThat(y.getDouble(1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2), closeTo(0, EPSILON));

		assertThat(y.getDouble(2, 0), closeTo(1, EPSILON));
		assertThat(y.getDouble(2, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2), closeTo(1, EPSILON));
	}

	@Test
	public void testLocalConstraints11() {
		final LocalContext ctx = new LocalContext(contextCenter(), 1, 1);

		final INDArray y = ctx.getConstraints();

		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1), closeTo(1, EPSILON));
		assertThat(y.getDouble(1, 2), closeTo(0, EPSILON));

		assertThat(y.getDouble(2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2), closeTo(0, EPSILON));
	}

}
