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

	private static final double DELTA_T = 1e-3;
	private static final double EPSILON = 10e-3;

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
		return new SimulationContext(new UniverseImpl(1, density, speed, temperature, ISA_MOLECULAR_MASS_,
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
	 * </p>
	 */
	@Test
	public void testComputeV() {
		final LocalContext ctx = new LocalContext(contextCenter(), 1, 0);
		final INDArray y = ctx.computeV();

		assertThat(y.shape(), equalTo(new long[] { 1 }));
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
