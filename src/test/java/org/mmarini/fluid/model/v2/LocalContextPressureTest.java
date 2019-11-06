package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

public class LocalContextPressureTest implements Constants {

	private static final double EPSILON = 10e-3;

	/**
	 * Returns the context for homogeneous pressure test expect west cell.
	 * <p>
	 * density =
	 *
	 * <pre>
	 * 1   1 1
	 * 1 1.1 1
	 * 1   1 1
	 * </pre>
	 * </p>
	 *
	 * @return
	 */
	private static SimulationContext1 centralPressureContext() {
		final INDArray density = Nd4j.create(new double[][] { { ISA_DENSITY, ISA_DENSITY, ISA_DENSITY },
				{ ISA_DENSITY, ISA_DENSITY * 1.1, ISA_DENSITY }, { ISA_DENSITY, ISA_DENSITY, ISA_DENSITY }, });
		final INDArray speed = Nd4j.zeros(3, 3, 2);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext1(new UniverseImpl(1, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), 0);
	}

	/**
	 * Returns the context for homogeneous pressure test
	 *
	 * @return
	 */
	private static SimulationContext1 homogeneousContext() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Nd4j.zeros(3, 3, 2);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext1(new UniverseImpl(1, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), 0);
	}

	/**
	 * Returns the context for bound pressure test
	 * <p>
	 * mu'=
	 *
	 * <pre>
	 * 1 1 1
	 * 1 0 0
	 * 1 0 0
	 * </pre>
	 *
	 * density =
	 *
	 * <pre>
	 * 1.1 1 1
	 *   1 1 1
	 *   1 1 1
	 * </pre>
	 *
	 * density' =
	 *
	 * <pre>
	 *  -   -    -
	 *  - 1.1  1.0
	 *  - 1.0  1.0
	 * </pre>
	 * </p>
	 */
	private static SimulationContext1 northWestContext() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		density.putScalar(new int[] { 0, 0 }, ISA_DENSITY * 1.1);
		final INDArray speed = Nd4j.zeros(3, 3, 2);
		speed.get(NDArrayIndex.all(), NDArrayIndex.all(), NDArrayIndex.point(0)).assign(SPEED);
		speed.putScalar(new int[] { 1, 1, 0 }, 0);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		massConstraints.putScalar(new int[] { 1, 1 }, 1);
		return new SimulationContext1(new UniverseImpl(1, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), 0);
	}

	/**
	 * Returns the context for homogeneous pressure test expect west cell
	 *
	 * @return
	 */
	private static SimulationContext1 westPressureContext() {
		final INDArray density = Nd4j.create(new double[][] { { ISA_DENSITY, ISA_DENSITY, ISA_DENSITY },
				{ 2 * ISA_DENSITY, ISA_DENSITY, ISA_DENSITY }, { ISA_DENSITY, ISA_DENSITY, ISA_DENSITY }, });
		final INDArray speed = Nd4j.zeros(3, 3, 2);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext1(new UniverseImpl(1, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), 0);
	}

	public LocalContextPressureTest() {
		Nd4j.create(new double[0]);
	}

	@Test
	public void testComputePressureForce1() {
		final LocalContext1 ctx = new LocalContext1(homogeneousContext(), 1, 1);
		final INDArray y = ctx.getPressureForce();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(0, EPSILON));
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
		assertThat(y.getDouble(2, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}

	/**
	 * density' =
	 *
	 * <pre>
	 *  1.0 1.0 1.0
	 *  2.0 1.0 1.0
	 *  1.0 1.0 1.0
	 * </pre>
	 *
	 * Pressure forces =
	 *
	 * <pre>
	 *  (0,0) (0,0) (0,0)
	 *  (1,0) (0,0) (0,0)
	 *  (0,0) (0,0) (0,0)
	 * </pre>
	 */
	@Test
	public void testComputePressureForce2() {
		final LocalContext1 ctx = new LocalContext1(westPressureContext(), 1, 1);
		final INDArray y = ctx.getPressureForce();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0, 0), closeTo(ISA_PRESSURE, EPSILON));
		assertThat(y.getDouble(1, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(2, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}

	/**
	 * density' =
	 *
	 * <pre>
	 *  -   -   -
	 *  - 1.1 1.0
	 *  - 1.0 1.0
	 * </pre>
	 *
	 * Pressure forces =
	 *
	 * <pre>
	 *  (0,0) (0,0) (0,0)
	 *  (0,0) (0,0) (1,0)
	 *  (0,0) (0,1) (0,0)
	 * </pre>
	 */
	@Test
	public void testComputePressureForce3() {
		final LocalContext1 ctx = new LocalContext1(northWestContext(), 0, 0);
		final INDArray y = ctx.getPressureForce();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 0), closeTo(ISA_PRESSURE * 0.1, EPSILON));
		assertThat(y.getDouble(1, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(2, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 1), closeTo(ISA_PRESSURE * 0.1, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}

	/**
	 * density' =
	 *
	 * <pre>
	 *  1.0 1.0 1.0
	 *  1.0 1.1 1.0
	 *  1.0 1.0 1.0
	 * </pre>
	 *
	 * Pressure forces =
	 *
	 * <pre>
	 *  (0,0) (0,-1) (0,0)
	 *  (-1,0) (0,0) (1,0)
	 *  (0,0) (0,1) (0,0)
	 * </pre>
	 */
	@Test
	public void testComputePressureForce4() {
		final LocalContext1 ctx = new LocalContext1(centralPressureContext(), 1, 1);
		final INDArray y = ctx.getPressureForce();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(-ISA_PRESSURE * 0.1, EPSILON));
		assertThat(y.getDouble(0, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0, 0), closeTo(-ISA_PRESSURE * 0.1, EPSILON));
		assertThat(y.getDouble(1, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 0), closeTo(ISA_PRESSURE * 0.1, EPSILON));
		assertThat(y.getDouble(1, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(2, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 1), closeTo(ISA_PRESSURE * 0.1, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}

}
