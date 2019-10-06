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
import org.nd4j.linalg.indexing.NDArrayIndex;

public class SimulationContextTest implements Constants {

	private static final double EPSILON = 10e-3;

	static SimulationContext context() {
		final INDArray density = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray speed = Nd4j.create(new double[][][] { { { 11, 12 }, { 13, 14 } }, { { 15, 16 }, { 17, 18 } } });
		final INDArray energy = Nd4j.create(new double[][] { { 21, 22 }, { 23, 24 } });
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 2, 2);
		return new SimulationContext(new UniverseImpl(1, density, speed, energy, 1, 1, massConstraints));
	}

	static SimulationContext context1() {
		final INDArray density = Nd4j.create(new double[][] { { 1, 2, 3 }, { 3, 4, 5 }, { 6, 7, 8 } });
		final INDArray speed = Nd4j.create(new double[][][] { { { 1.1, -1.1 }, { 1.2, -1.2 }, { 1.3, -1.3 } },
				{ { 2.1, -2.1 }, { 2.2, -2.2 }, { 2.3, -2.3 } }, { { 3.1, -3.1 }, { 3.2, -3.2 }, { 3.3, -3.3 } } });
		final INDArray energy = Nd4j.ones(3, 3);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(1, density, speed, energy, 0, 0, massConstraints));
	}

	static SimulationContext context2() {
		final INDArray density = Nd4j
				.create(new double[][] { { 1.1, 1.2, 1.3 }, { 1.4, 1.5, 1.6 }, { 1.7, 1.8, 1.9 } });
		final INDArray speed = Nd4j.create(new double[][][] { { { 1.1, -1.1 }, { 1.2, -1.2 }, { 1.3, -1.3 } },
				{ { 2.1, -2.1 }, { 2.2, -2.2 }, { 2.3, -2.3 } }, { { 3.1, -3.1 }, { 3.2, -3.2 }, { 3.3, -3.3 } } });
		final INDArray energy = Nd4j.ones(3, 3);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(1, density, speed, energy, 0, 0, massConstraints));
	}

	/**
	 * Returns the context for constant x axis flux test
	 *
	 * @return
	 */
	static SimulationContext context3() {
		final INDArray density = Nd4j.ones(3, 3);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { 0, 1 }), 3, 3);
		final INDArray energy = Nd4j.ones(3, 3);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(1, density, speed, energy, 0, 0, massConstraints));
	}

	/**
	 * Returns the context for constant y axis flux test
	 *
	 * @return
	 */
	static SimulationContext context4() {
		final INDArray density = Nd4j.ones(3, 3);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { 1, 0 }), 3, 3);
		final INDArray energy = Nd4j.ones(3, 3);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(1, density, speed, energy, 0, 0, massConstraints));
	}

	/**
	 * Returns the context for constant y axis flux test with different density
	 *
	 * @return
	 */
	static SimulationContext context5() {
		final INDArray density = Nd4j.create(new double[][] { { 2, 1, 1 }, { 2, 1, 1 }, { 2, 1, 1 }, });
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { 0, 1 }), 3, 3);
		final INDArray energy = Nd4j.ones(3, 3);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(1, density, speed, energy, 0, 0, massConstraints));
	}

	/**
	 * Returns the context for omegeneous pressure test
	 *
	 * @return
	 */
	static SimulationContext context6() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Nd4j.zeros(3, 3, 2);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(1, density, speed, temperature, ISA_MOLECULAR_MASS_,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints));
	}

	/**
	 * Returns the context for omegeneous pressure test expect west cell
	 *
	 * @return
	 */
	static SimulationContext context7() {
		final INDArray density = Nd4j.create(new double[][] { { ISA_DENSITY, ISA_DENSITY, ISA_DENSITY },
				{ 2 * ISA_DENSITY, ISA_DENSITY, ISA_DENSITY }, { ISA_DENSITY, ISA_DENSITY, ISA_DENSITY }, });
		final INDArray speed = Nd4j.zeros(3, 3, 2);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(1, density, speed, temperature, ISA_MOLECULAR_MASS_,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints));
	}

	/**
	 * Returns the context for omegeneous pressure test expect west cell
	 *
	 * @return
	 */
	static SimulationContext context8() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		final INDArray speed = Nd4j.zeros(3, 3, 2);
		speed.get(NDArrayIndex.all(), NDArrayIndex.all(), NDArrayIndex.point(0)).assign(SPEED);
		speed.putScalar(new int[] { 1, 1, 0 }, 0);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		massConstraints.putScalar(new int[] { 1, 1 }, 1);
		return new SimulationContext(new UniverseImpl(1, density, speed, temperature, ISA_MOLECULAR_MASS_,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints));
	}

	public SimulationContextTest() {
		Nd4j.create(new double[0]);
	}

	@Test
	public void testComputeDeltaMass() {
		final SimulationContext ctx = context1();
		final double y = ctx.computeDeltaMass(1, 1);
		assertThat(y, closeTo(14.8, EPSILON));
	}

	@Test
	public void testComputeFreeMomentumFlux10() {
		final SimulationContext ctx = context8();
		final INDArray y = ctx.computeFreeMomentumFlux(1, 0);

		assertThat(y.shape(), equalTo(new long[] { 2 }));
		assertThat(y.getDouble(0), closeTo(1, EPSILON));
		assertThat(y.getDouble(1), closeTo(1, EPSILON));

	}

	@Test
	public void testComputeMomentumFlux1() {
		final SimulationContext ctx = context3();
		final INDArray y = ctx.computeMomentumFlux(1, 1);
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 0, 1), closeTo(2, EPSILON));
		assertThat(y.getDouble(1, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 1), closeTo(-2, EPSILON));

		assertThat(y.getDouble(2, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}

	@Test
	public void testComputeMomentumFlux2() {
		final SimulationContext ctx = context4();
		final INDArray y = ctx.computeMomentumFlux(1, 1);
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(2, EPSILON));
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
		assertThat(y.getDouble(2, 1, 0), closeTo(-2, EPSILON));
		assertThat(y.getDouble(2, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}

	@Test
	public void testComputeMomentumFlux3() {
		final SimulationContext ctx = context5();
		final INDArray y = ctx.computeMomentumFlux(1, 1);
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 0, 1), closeTo(3, EPSILON));
		assertThat(y.getDouble(1, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 2, 1), closeTo(-2, EPSILON));

		assertThat(y.getDouble(2, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(2, 2, 1), closeTo(0, EPSILON));
	}

	@Test
	public void testComputePressureForce1() {
		final SimulationContext ctx = context6();
		final INDArray y = ctx.computePressureForce(1, 1);
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

	@Test
	public void testComputePressureForce2() {
		final SimulationContext ctx = context7();
		final INDArray y = ctx.computePressureForce(1, 1);
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 0, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 1, 1), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(0, 2, 1), closeTo(0, EPSILON));

		assertThat(y.getDouble(1, 0, 0), closeTo(0, EPSILON));
		assertThat(y.getDouble(1, 0, 1), closeTo(ISA_PRESSURE, EPSILON));
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

	@Test
	public void testComputeReactiveSurfaces00() {
		final SimulationContext ctx = context8();
		final List<int[]> y = ctx.computeReactiveSurfaces(0, 0);
		assertThat(y, hasSize(2));
		assertThat(toIntegerArray(y.get(0)), arrayContaining(0, 1));
		assertThat(toIntegerArray(y.get(1)), arrayContaining(1, 0));
	}

	@Test
	public void testComputeReactiveSurfaces01() {
		final SimulationContext ctx = context8();
		final List<int[]> y = ctx.computeReactiveSurfaces(0, 1);
		assertThat(y, hasSize(2));
		assertThat(toIntegerArray(y.get(0)), arrayContaining(0, 1));
		assertThat(toIntegerArray(y.get(1)), arrayContaining(2, 1));
	}

	@Test
	public void testComputeReactiveSurfaces11() {
		final SimulationContext ctx = context8();
		final List<int[]> y = ctx.computeReactiveSurfaces(1, 1);
		assertThat(y, empty());
	}

	@Test
	public void testLocalConstraints00() {
		final SimulationContext ctx = context8();

		final INDArray y = ctx.getLocalConstraints(0, 0);

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
		final SimulationContext ctx = context8();

		final INDArray y = ctx.getLocalConstraints(1, 1);

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

	@Test
	public void testLocalDensity() {
		final SimulationContext ctx = context();
		assertThat(ctx.getLocalMass(0, 0),
				equalTo(Nd4j.create(new double[][] { { 0, 0, 0 }, { 0, 1, 2 }, { 0, 3, 4 } })));
	}

	@Test
	public void testLocalEnergy() {
		final SimulationContext ctx = context();
		assertThat(ctx.getLocalEnergy(0, 0),
				equalTo(Nd4j.create(new double[][] { { 0, 0, 0 }, { 0, 21, 44 }, { 0, 69, 96 } })));
	}

	@Test
	public void testLocalMomentum() {
		final SimulationContext ctx = context();
		assertThat(ctx.getLocalMomentum(0, 0), equalTo(Nd4j.create(new double[][][] { { { 0, 0 }, { 0, 0 }, { 0, 0 } },
				{ { 0, 0 }, { 11, 12 }, { 2 * 13, 2 * 14 } }, { { 0, 0 }, { 3 * 15, 3 * 16 }, { 4 * 17, 4 * 18 } } })));
	}

	@Test
	public void testPadEnergy() {
		final SimulationContext ctx = context();
		assertThat(ctx.getPadEnergy(), equalTo(
				Nd4j.create(new double[][] { { 0, 0, 0, 0 }, { 0, 21, 44, 0 }, { 0, 69, 96, 0 }, { 0, 0, 0, 0 } })));
	}

	@Test
	public void testPadMass() {
		final SimulationContext ctx = context();
		assertThat(ctx.getPadMass(), equalTo(
				Nd4j.create(new double[][] { { 0, 0, 0, 0 }, { 0, 1, 2, 0 }, { 0, 3, 4, 0 }, { 0, 0, 0, 0 } })));
	}

	@Test
	public void testPadMomentum() {
		final SimulationContext ctx = context();
		assertThat(ctx.getPadMomentum(),
				equalTo(Nd4j.create(new double[][][] { { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
						{ { 0, 0 }, { 11, 12 }, { 2 * 13, 2 * 14 }, { 0, 0 } },
						{ { 0, 0 }, { 3 * 15, 3 * 16 }, { 4 * 17, 4 * 18 }, { 0, 0 } },
						{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } } })));
	}

	Integer[] toIntegerArray(final int[] ary) {
		final Integer[] result = new Integer[ary.length];
		for (int i = 0; i < ary.length; i++) {
			result[i] = Integer.valueOf(ary[i]);
		}
		return result;
	}
}
