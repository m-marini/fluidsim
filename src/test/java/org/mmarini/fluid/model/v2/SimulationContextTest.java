package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mmarini.fluid.TestUtils.around;

import org.junit.Test;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class SimulationContextTest implements Constants {

	private static final int[] CENTER_INDICES = new int[] { 1, 1 };
	private static final double ISA_DENSITY12 = ISA_DENSITY * 1.2;

	private static SimulationContext centerContext() {
		final INDArray density = Nd4j.ones(3, 3).mul(ISA_DENSITY);
		density.putScalar(CENTER_INDICES, ISA_DENSITY12);
		final INDArray temperature = Nd4j.ones(3, 3).mul(ISA_TEMPERATURE);
		final INDArray speed = Utils.prefixBroadcast(Nd4j.create(new double[] { SPEED, 0 }), 3, 3);
		final INDArray massConstraints = Nd4j.zeros(DataType.DOUBLE, 3, 3);
		return new SimulationContext(new UniverseImpl(SIZE, density, speed, temperature, ISA_MOLECULAR_MASS,
				ISA_SPECIFIC_HEAT_CAPACITY, massConstraints), 0);
	}

	public SimulationContextTest() {
		Nd4j.create(new double[0]);
	}

	@Test
	public void testEnergy() {
		final double expectedEnergy = ISA_SPECIFIC_HEAT_CAPACITY * ISA_DENSITY * ISA_TEMPERATURE * VOLUME;
		final double expectedEnergy12 = ISA_SPECIFIC_HEAT_CAPACITY * ISA_DENSITY * 1.2 * ISA_TEMPERATURE * VOLUME;

		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getEnergy();
		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), around(expectedEnergy));
		assertThat(y.getDouble(0, 1), around(expectedEnergy));
		assertThat(y.getDouble(0, 2), around(expectedEnergy));

		assertThat(y.getDouble(1, 0), around(expectedEnergy));
		assertThat(y.getDouble(1, 1), around(expectedEnergy12));
		assertThat(y.getDouble(1, 2), around(expectedEnergy));

		assertThat(y.getDouble(2, 0), around(expectedEnergy));
		assertThat(y.getDouble(2, 1), around(expectedEnergy));
		assertThat(y.getDouble(2, 2), around(expectedEnergy));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * 0   0 0
	 * 1 1.1 1
	 * 1 1.1 1
	 * 0   0 0
	 * </pre>
	 */
	@Test
	public void testHDensity() {
		final double nullDensity = 0;
		final double expectedDensity = ISA_DENSITY;
		final double expectedDensity11 = ISA_DENSITY * 1.1;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getHDensity();
		assertThat(y.shape(), equalTo(new long[] { 4, 3 }));

		assertThat(y.getDouble(0, 0), around(nullDensity));
		assertThat(y.getDouble(0, 1), around(nullDensity));
		assertThat(y.getDouble(0, 2), around(nullDensity));

		assertThat(y.getDouble(1, 0), around(expectedDensity));
		assertThat(y.getDouble(1, 1), around(expectedDensity11));
		assertThat(y.getDouble(1, 2), around(expectedDensity));

		assertThat(y.getDouble(2, 0), around(expectedDensity));
		assertThat(y.getDouble(2, 1), around(expectedDensity11));
		assertThat(y.getDouble(2, 2), around(expectedDensity));

		assertThat(y.getDouble(3, 0), around(nullDensity));
		assertThat(y.getDouble(3, 1), around(nullDensity));
		assertThat(y.getDouble(3, 2), around(nullDensity));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * 0   0 0
	 * 1 1.1 1
	 * 1 1.1 1
	 * 0   0 0
	 * </pre>
	 */
	@Test
	public void testHEnergy() {
		final double nullDensity = 0;
		final double expectedEnergy = ISA_SPECIFIC_HEAT_CAPACITY * ISA_DENSITY * ISA_TEMPERATURE * VOLUME;
		final double expectedEnergy11 = ISA_SPECIFIC_HEAT_CAPACITY * ISA_DENSITY * 1.1 * ISA_TEMPERATURE * VOLUME;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getHEnergy();
		assertThat(y.shape(), equalTo(new long[] { 4, 3 }));

		assertThat(y.getDouble(0, 0), around(nullDensity));
		assertThat(y.getDouble(0, 1), around(nullDensity));
		assertThat(y.getDouble(0, 2), around(nullDensity));

		assertThat(y.getDouble(1, 0), around(expectedEnergy));
		assertThat(y.getDouble(1, 1), around(expectedEnergy11));
		assertThat(y.getDouble(1, 2), around(expectedEnergy));

		assertThat(y.getDouble(2, 0), around(expectedEnergy));
		assertThat(y.getDouble(2, 1), around(expectedEnergy11));
		assertThat(y.getDouble(2, 2), around(expectedEnergy));

		assertThat(y.getDouble(3, 0), around(nullDensity));
		assertThat(y.getDouble(3, 1), around(nullDensity));
		assertThat(y.getDouble(3, 2), around(nullDensity));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * (0,0) (0,0)   (0,0)
	 * (1,0) (1.1,0) (1,0)
	 * (1,0) (1.1,0) (1,0)
	 * (0,0) (0,0)   (0,0)
	 * </pre>
	 */
	@Test
	public void testHMassFlux() {
		final double expected0 = 0;
		final double expected = ISA_DENSITY * SPEED * AREA;
		final double expected11 = ISA_DENSITY * SPEED * AREA * 1.1;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getHMassFlux();
		assertThat(y.shape(), equalTo(new long[] { 4, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(expected0));
		assertThat(y.getDouble(0, 0, 1), around(expected0));
		assertThat(y.getDouble(0, 1, 0), around(expected0));
		assertThat(y.getDouble(0, 1, 1), around(expected0));
		assertThat(y.getDouble(0, 2, 0), around(expected0));
		assertThat(y.getDouble(0, 2, 1), around(expected0));

		assertThat(y.getDouble(1, 0, 0), around(expected));
		assertThat(y.getDouble(1, 0, 1), around(expected0));
		assertThat(y.getDouble(1, 1, 0), around(expected11));
		assertThat(y.getDouble(1, 1, 1), around(expected0));
		assertThat(y.getDouble(1, 2, 0), around(expected));
		assertThat(y.getDouble(1, 2, 1), around(expected0));

		assertThat(y.getDouble(2, 0, 0), around(expected));
		assertThat(y.getDouble(2, 0, 1), around(expected0));
		assertThat(y.getDouble(2, 1, 0), around(expected11));
		assertThat(y.getDouble(2, 1, 1), around(expected0));
		assertThat(y.getDouble(2, 2, 0), around(expected));
		assertThat(y.getDouble(2, 2, 1), around(expected0));

		assertThat(y.getDouble(3, 0, 0), around(expected0));
		assertThat(y.getDouble(3, 0, 1), around(expected0));
		assertThat(y.getDouble(3, 1, 0), around(expected0));
		assertThat(y.getDouble(3, 1, 1), around(expected0));
		assertThat(y.getDouble(3, 2, 0), around(expected0));
		assertThat(y.getDouble(3, 2, 1), around(expected0));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * (0,0) (0,0)   (0,0)
	 * (1,0) (1.1,0) (1,0)
	 * (1,0) (1.1,0) (1,0)
	 * (0,0) (0,0)   (0,0)
	 * </pre>
	 */
	@Test
	public void testHMomentum() {
		final double expected0 = 0;
		final double expected = ISA_DENSITY * VOLUME * SPEED;
		final double expected11 = ISA_DENSITY * VOLUME * SPEED * 1.1;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getHMomentum();
		assertThat(y.shape(), equalTo(new long[] { 4, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(expected0));
		assertThat(y.getDouble(0, 0, 1), around(expected0));
		assertThat(y.getDouble(0, 1, 0), around(expected0));
		assertThat(y.getDouble(0, 1, 1), around(expected0));
		assertThat(y.getDouble(0, 2, 0), around(expected0));
		assertThat(y.getDouble(0, 2, 1), around(expected0));

		assertThat(y.getDouble(1, 0, 0), around(expected));
		assertThat(y.getDouble(1, 0, 1), around(expected0));
		assertThat(y.getDouble(1, 1, 0), around(expected11));
		assertThat(y.getDouble(1, 1, 1), around(expected0));
		assertThat(y.getDouble(1, 2, 0), around(expected));
		assertThat(y.getDouble(1, 2, 1), around(expected0));

		assertThat(y.getDouble(2, 0, 0), around(expected));
		assertThat(y.getDouble(2, 0, 1), around(expected0));
		assertThat(y.getDouble(2, 1, 0), around(expected11));
		assertThat(y.getDouble(2, 1, 1), around(expected0));
		assertThat(y.getDouble(2, 2, 0), around(expected));
		assertThat(y.getDouble(2, 2, 1), around(expected0));

		assertThat(y.getDouble(3, 0, 0), around(expected0));
		assertThat(y.getDouble(3, 0, 1), around(expected0));
		assertThat(y.getDouble(3, 1, 0), around(expected0));
		assertThat(y.getDouble(3, 1, 1), around(expected0));
		assertThat(y.getDouble(3, 2, 0), around(expected0));
		assertThat(y.getDouble(3, 2, 1), around(expected0));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * (0,0) (0,0)   (0,0)
	 * (1,0) (1.1,0) (1,0)
	 * (1,0) (1.1,0) (1,0)
	 * (0,0) (0,0)   (0,0)
	 * </pre>
	 */
	@Test
	public void testHSpeed() {
		final double expected0 = 0;
		final double expected = SPEED;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getHSpeed();
		assertThat(y.shape(), equalTo(new long[] { 4, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(expected0));
		assertThat(y.getDouble(0, 0, 1), around(expected0));
		assertThat(y.getDouble(0, 1, 0), around(expected0));
		assertThat(y.getDouble(0, 1, 1), around(expected0));
		assertThat(y.getDouble(0, 2, 0), around(expected0));
		assertThat(y.getDouble(0, 2, 1), around(expected0));

		assertThat(y.getDouble(1, 0, 0), around(expected));
		assertThat(y.getDouble(1, 0, 1), around(expected0));
		assertThat(y.getDouble(1, 1, 0), around(expected));
		assertThat(y.getDouble(1, 1, 1), around(expected0));
		assertThat(y.getDouble(1, 2, 0), around(expected));
		assertThat(y.getDouble(1, 2, 1), around(expected0));

		assertThat(y.getDouble(2, 0, 0), around(expected));
		assertThat(y.getDouble(2, 0, 1), around(expected0));
		assertThat(y.getDouble(2, 1, 0), around(expected));
		assertThat(y.getDouble(2, 1, 1), around(expected0));
		assertThat(y.getDouble(2, 2, 0), around(expected));
		assertThat(y.getDouble(2, 2, 1), around(expected0));

		assertThat(y.getDouble(3, 0, 0), around(expected0));
		assertThat(y.getDouble(3, 0, 1), around(expected0));
		assertThat(y.getDouble(3, 1, 0), around(expected0));
		assertThat(y.getDouble(3, 1, 1), around(expected0));
		assertThat(y.getDouble(3, 2, 0), around(expected0));
		assertThat(y.getDouble(3, 2, 1), around(expected0));
	}

	@Test
	public void testMass() {
		final double expectedMass = ISA_DENSITY * VOLUME;
		final double expectedMass12 = ISA_DENSITY12 * VOLUME;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getMass();
		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), around(expectedMass));
		assertThat(y.getDouble(0, 1), around(expectedMass));
		assertThat(y.getDouble(0, 2), around(expectedMass));

		assertThat(y.getDouble(1, 0), around(expectedMass));
		assertThat(y.getDouble(1, 1), around(expectedMass12));
		assertThat(y.getDouble(1, 2), around(expectedMass));

		assertThat(y.getDouble(2, 0), around(expectedMass));
		assertThat(y.getDouble(2, 1), around(expectedMass));
		assertThat(y.getDouble(2, 2), around(expectedMass));
	}

	@Test
	public void testMomentum() {
		final double expectedXMomentum = ISA_DENSITY * VOLUME * SPEED;
		final double expectedXMomentum12 = ISA_DENSITY12 * VOLUME * SPEED;
		final double expectedYMomentum = 0;

		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getMomentum();
		assertThat(y.shape(), equalTo(new long[] { 3, 3, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(expectedXMomentum));
		assertThat(y.getDouble(0, 0, 1), around(expectedYMomentum));
		assertThat(y.getDouble(0, 1, 0), around(expectedXMomentum));
		assertThat(y.getDouble(0, 1, 1), around(expectedYMomentum));
		assertThat(y.getDouble(0, 2, 0), around(expectedXMomentum));
		assertThat(y.getDouble(0, 2, 1), around(expectedYMomentum));

		assertThat(y.getDouble(1, 0, 0), around(expectedXMomentum));
		assertThat(y.getDouble(0, 0, 1), around(expectedYMomentum));
		assertThat(y.getDouble(1, 1, 0), around(expectedXMomentum12));
		assertThat(y.getDouble(1, 1, 1), around(expectedYMomentum));
		assertThat(y.getDouble(1, 2, 0), around(expectedXMomentum));
		assertThat(y.getDouble(1, 2, 1), around(expectedYMomentum));

		assertThat(y.getDouble(2, 0, 0), around(expectedXMomentum));
		assertThat(y.getDouble(2, 0, 1), around(expectedYMomentum));
		assertThat(y.getDouble(2, 1, 0), around(expectedXMomentum));
		assertThat(y.getDouble(2, 1, 1), around(expectedYMomentum));
		assertThat(y.getDouble(2, 2, 0), around(expectedXMomentum));
		assertThat(y.getDouble(2, 2, 1), around(expectedYMomentum));
	}

	@Test
	public void testPressure() {
		final double expectedPressure = ISA_PRESSURE;
		final double expectedPressure12 = ISA_PRESSURE * 1.2;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getPressure();
		assertThat(y.shape(), equalTo(new long[] { 3, 3 }));

		assertThat(y.getDouble(0, 0), around(expectedPressure));
		assertThat(y.getDouble(0, 1), around(expectedPressure));
		assertThat(y.getDouble(0, 2), around(expectedPressure));

		assertThat(y.getDouble(1, 0), around(expectedPressure));
		assertThat(y.getDouble(1, 1), around(expectedPressure12));
		assertThat(y.getDouble(1, 2), around(expectedPressure));

		assertThat(y.getDouble(2, 0), around(expectedPressure));
		assertThat(y.getDouble(2, 1), around(expectedPressure));
		assertThat(y.getDouble(2, 2), around(expectedPressure));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * 0   1   1 0
	 * 0 1.1 1.1 0
	 * 0   1   1 0
	 * </pre>
	 */

	@Test
	public void testVDensity() {
		final double nullDensity = 0;
		final double expectedDensity = ISA_DENSITY;
		final double expectedDensity11 = ISA_DENSITY * 1.1;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getVDensity();
		assertThat(y.shape(), equalTo(new long[] { 3, 4 }));

		assertThat(y.getDouble(0, 0), around(nullDensity));
		assertThat(y.getDouble(0, 1), around(expectedDensity));
		assertThat(y.getDouble(0, 2), around(expectedDensity));
		assertThat(y.getDouble(0, 3), around(nullDensity));

		assertThat(y.getDouble(1, 0), around(nullDensity));
		assertThat(y.getDouble(1, 1), around(expectedDensity11));
		assertThat(y.getDouble(1, 2), around(expectedDensity11));
		assertThat(y.getDouble(1, 3), around(nullDensity));

		assertThat(y.getDouble(2, 0), around(nullDensity));
		assertThat(y.getDouble(2, 1), around(expectedDensity));
		assertThat(y.getDouble(2, 2), around(expectedDensity));
		assertThat(y.getDouble(2, 3), around(nullDensity));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * 0   1   1 0
	 * 0 1.1 1.1 0
	 * 0   1   1 0
	 * </pre>
	 */
	@Test
	public void testVEnergy() {
		final double nullDensity = 0;
		final double expectedEnergy = ISA_SPECIFIC_HEAT_CAPACITY * ISA_DENSITY * ISA_TEMPERATURE * VOLUME;
		final double expectedEnergy11 = ISA_SPECIFIC_HEAT_CAPACITY * ISA_DENSITY * 1.1 * ISA_TEMPERATURE * VOLUME;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getVEnergy();
		assertThat(y.shape(), equalTo(new long[] { 3, 4 }));

		assertThat(y.getDouble(0, 0), around(nullDensity));
		assertThat(y.getDouble(0, 1), around(expectedEnergy));
		assertThat(y.getDouble(0, 2), around(expectedEnergy));
		assertThat(y.getDouble(0, 3), around(nullDensity));

		assertThat(y.getDouble(1, 0), around(nullDensity));
		assertThat(y.getDouble(1, 1), around(expectedEnergy11));
		assertThat(y.getDouble(1, 2), around(expectedEnergy11));
		assertThat(y.getDouble(1, 3), around(nullDensity));

		assertThat(y.getDouble(2, 0), around(nullDensity));
		assertThat(y.getDouble(2, 1), around(expectedEnergy));
		assertThat(y.getDouble(2, 2), around(expectedEnergy));
		assertThat(y.getDouble(2, 3), around(nullDensity));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * (0,0) (1,0) (1,0)   (0,0)
	 * (0,0) (1,0) (1.1,0) (0,0)
	 * (0,0) (1,0) (1,0)   (0,0)
	 * </pre>
	 */

	@Test
	public void testVMassFlux() {
		final double expected0 = 0;
		final double expected = ISA_DENSITY * SPEED * AREA;
		final double expected11 = ISA_DENSITY * SPEED * AREA * 1.1;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getVMassFlux();
		assertThat(y.shape(), equalTo(new long[] { 3, 4, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(expected0));
		assertThat(y.getDouble(0, 0, 1), around(expected0));
		assertThat(y.getDouble(0, 1, 0), around(expected));
		assertThat(y.getDouble(0, 1, 1), around(expected0));
		assertThat(y.getDouble(0, 2, 0), around(expected));
		assertThat(y.getDouble(0, 2, 1), around(expected0));
		assertThat(y.getDouble(0, 3, 0), around(expected0));
		assertThat(y.getDouble(0, 3, 1), around(expected0));

		assertThat(y.getDouble(1, 0, 0), around(expected0));
		assertThat(y.getDouble(2, 0, 1), around(expected0));
		assertThat(y.getDouble(1, 1, 0), around(expected11));
		assertThat(y.getDouble(2, 1, 1), around(expected0));
		assertThat(y.getDouble(1, 2, 0), around(expected11));
		assertThat(y.getDouble(2, 2, 1), around(expected0));
		assertThat(y.getDouble(1, 3, 0), around(expected0));
		assertThat(y.getDouble(2, 3, 1), around(expected0));

		assertThat(y.getDouble(2, 0, 0), around(expected0));
		assertThat(y.getDouble(2, 0, 1), around(expected0));
		assertThat(y.getDouble(2, 1, 0), around(expected));
		assertThat(y.getDouble(2, 1, 1), around(expected0));
		assertThat(y.getDouble(2, 2, 0), around(expected));
		assertThat(y.getDouble(2, 2, 1), around(expected0));
		assertThat(y.getDouble(2, 3, 0), around(expected0));
		assertThat(y.getDouble(2, 3, 1), around(expected0));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * (0,0) (1,0) (1,0)   (0,0)
	 * (0,0) (1,0) (1.1,0) (0,0)
	 * (0,0) (1,0) (1,0)   (0,0)
	 * </pre>
	 */

	@Test
	public void testVMomentum() {
		final double expected0 = 0;
		final double expected = ISA_DENSITY * VOLUME * SPEED;
		final double expected11 = ISA_DENSITY * VOLUME * SPEED * 1.1;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getVMomentum();
		assertThat(y.shape(), equalTo(new long[] { 3, 4, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(expected0));
		assertThat(y.getDouble(0, 0, 1), around(expected0));
		assertThat(y.getDouble(0, 1, 0), around(expected));
		assertThat(y.getDouble(0, 1, 1), around(expected0));
		assertThat(y.getDouble(0, 2, 0), around(expected));
		assertThat(y.getDouble(0, 2, 1), around(expected0));
		assertThat(y.getDouble(0, 3, 0), around(expected0));
		assertThat(y.getDouble(0, 3, 1), around(expected0));

		assertThat(y.getDouble(1, 0, 0), around(expected0));
		assertThat(y.getDouble(2, 0, 1), around(expected0));
		assertThat(y.getDouble(1, 1, 0), around(expected11));
		assertThat(y.getDouble(2, 1, 1), around(expected0));
		assertThat(y.getDouble(1, 2, 0), around(expected11));
		assertThat(y.getDouble(2, 2, 1), around(expected0));
		assertThat(y.getDouble(1, 3, 0), around(expected0));
		assertThat(y.getDouble(2, 3, 1), around(expected0));

		assertThat(y.getDouble(2, 0, 0), around(expected0));
		assertThat(y.getDouble(2, 0, 1), around(expected0));
		assertThat(y.getDouble(2, 1, 0), around(expected));
		assertThat(y.getDouble(2, 1, 1), around(expected0));
		assertThat(y.getDouble(2, 2, 0), around(expected));
		assertThat(y.getDouble(2, 2, 1), around(expected0));
		assertThat(y.getDouble(2, 3, 0), around(expected0));
		assertThat(y.getDouble(2, 3, 1), around(expected0));
	}

	/**
	 * Expected
	 *
	 * <pre>
	 * (0,0) (1,0) (1,0)   (0,0)
	 * (0,0) (1,0) (1.1,0) (0,0)
	 * (0,0) (1,0) (1,0)   (0,0)
	 * </pre>
	 */

	@Test
	public void testVSpeed() {
		final double expected0 = 0;
		final double expected = SPEED;
		final SimulationContext ctx = centerContext();
		final INDArray y = ctx.getVSpeed();
		assertThat(y.shape(), equalTo(new long[] { 3, 4, 2 }));

		assertThat(y.getDouble(0, 0, 0), around(expected0));
		assertThat(y.getDouble(0, 0, 1), around(expected0));
		assertThat(y.getDouble(0, 1, 0), around(expected));
		assertThat(y.getDouble(0, 1, 1), around(expected0));
		assertThat(y.getDouble(0, 2, 0), around(expected));
		assertThat(y.getDouble(0, 2, 1), around(expected0));
		assertThat(y.getDouble(0, 3, 0), around(expected0));
		assertThat(y.getDouble(0, 3, 1), around(expected0));

		assertThat(y.getDouble(1, 0, 0), around(expected0));
		assertThat(y.getDouble(2, 0, 1), around(expected0));
		assertThat(y.getDouble(1, 1, 0), around(expected));
		assertThat(y.getDouble(2, 1, 1), around(expected0));
		assertThat(y.getDouble(1, 2, 0), around(expected));
		assertThat(y.getDouble(2, 2, 1), around(expected0));
		assertThat(y.getDouble(1, 3, 0), around(expected0));
		assertThat(y.getDouble(2, 3, 1), around(expected0));

		assertThat(y.getDouble(2, 0, 0), around(expected0));
		assertThat(y.getDouble(2, 0, 1), around(expected0));
		assertThat(y.getDouble(2, 1, 0), around(expected));
		assertThat(y.getDouble(2, 1, 1), around(expected0));
		assertThat(y.getDouble(2, 2, 0), around(expected));
		assertThat(y.getDouble(2, 2, 1), around(expected0));
		assertThat(y.getDouble(2, 3, 0), around(expected0));
		assertThat(y.getDouble(2, 3, 1), around(expected0));
	}
}
