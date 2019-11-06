package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class LocalContextTest implements Constants {

	private static SimulationContext1 context() {
		final INDArray density = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray speed = Nd4j.create(new double[][][] { { { 11, 12 }, { 13, 14 } }, { { 15, 16 }, { 17, 18 } } });
		final INDArray energy = Nd4j.create(new double[][] { { 21, 22 }, { 23, 24 } });
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 2, 2);
		return new SimulationContext1(new UniverseImpl(1, density, speed, energy, 1, 1, massConstraints), 0);
	}

	public LocalContextTest() {
		Nd4j.create(new double[0]);
	}

	@Test
	public void testEnergy() {
		final LocalContext1 ctx = new LocalContext1(context(), 0, 0);
		assertThat(ctx.getEnergy(), equalTo(Nd4j.create(new double[][] { { 0, 0, 0 }, { 0, 21, 44 }, { 0, 69, 96 } })));
	}

	@Test
	public void testMass() {
		final LocalContext1 ctx = new LocalContext1(context(), 0, 0);
		assertThat(ctx.getMass(), equalTo(Nd4j.create(new double[][] { { 0, 0, 0 }, { 0, 1, 2 }, { 0, 3, 4 } })));
	}

	@Test
	public void testMomentum() {
		final LocalContext1 ctx = new LocalContext1(context(), 0, 0);
		assertThat(ctx.getMomentum(), equalTo(Nd4j.create(new double[][][] { { { 0, 0 }, { 0, 0 }, { 0, 0 } },
				{ { 0, 0 }, { 11, 12 }, { 2 * 13, 2 * 14 } }, { { 0, 0 }, { 3 * 15, 3 * 16 }, { 4 * 17, 4 * 18 } } })));
	}
}
