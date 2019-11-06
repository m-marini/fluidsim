package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class SimulationContext1Test implements Constants {

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

	private static SimulationContext1 context() {
		final INDArray density = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray speed = Nd4j.create(new double[][][] { { { 11, 12 }, { 13, 14 } }, { { 15, 16 }, { 17, 18 } } });
		final INDArray energy = Nd4j.create(new double[][] { { 21, 22 }, { 23, 24 } });
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 2, 2);
		return new SimulationContext1(new UniverseImpl(1, density, speed, energy, 1, 1, massConstraints), 0);
	}

	public SimulationContext1Test() {
		Nd4j.create(new double[0]);
	}

	@Test
	public void testPadEnergy() {
		final SimulationContext1 ctx = context();
		assertThat(ctx.getPadEnergy(), equalTo(
				Nd4j.create(new double[][] { { 0, 0, 0, 0 }, { 0, 21, 44, 0 }, { 0, 69, 96, 0 }, { 0, 0, 0, 0 } })));
	}

	@Test
	public void testPadMass() {
		final SimulationContext1 ctx = context();
		assertThat(ctx.getPadMass(), equalTo(
				Nd4j.create(new double[][] { { 0, 0, 0, 0 }, { 0, 1, 2, 0 }, { 0, 3, 4, 0 }, { 0, 0, 0, 0 } })));
	}

	@Test
	public void testPadMomentum() {
		final SimulationContext1 ctx = context();
		assertThat(ctx.getPadMomentum(),
				equalTo(Nd4j.create(new double[][][] { { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
						{ { 0, 0 }, { 11, 12 }, { 2 * 13, 2 * 14 }, { 0, 0 } },
						{ { 0, 0 }, { 3 * 15, 3 * 16 }, { 4 * 17, 4 * 18 }, { 0, 0 } },
						{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } } })));
	}

	@Test
	public void testPadPressure() {
		final SimulationContext1 ctx = centralPressureContext();
		final INDArray y = ctx.getPadPressure();
		assertThat(y.shape(), equalTo(new long[] { 5, 5 }));

		assertThat(y.getDouble(1, 1), closeTo(ISA_PRESSURE, EPSILON));
		assertThat(y.getDouble(1, 2), closeTo(ISA_PRESSURE, EPSILON));
		assertThat(y.getDouble(1, 3), closeTo(ISA_PRESSURE, EPSILON));

		assertThat(y.getDouble(2, 1), closeTo(ISA_PRESSURE, EPSILON));
		assertThat(y.getDouble(2, 2), closeTo(ISA_PRESSURE * 1.1, EPSILON));
		assertThat(y.getDouble(2, 3), closeTo(ISA_PRESSURE, EPSILON));

		assertThat(y.getDouble(3, 1), closeTo(ISA_PRESSURE, EPSILON));
		assertThat(y.getDouble(3, 2), closeTo(ISA_PRESSURE, EPSILON));
		assertThat(y.getDouble(3, 3), closeTo(ISA_PRESSURE, EPSILON));
	}
}
