package org.mmarini.fluid.model.v2;

import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class UniverseImplTest implements Constants {

	static UniverseImpl universe() {
		final INDArray density = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray speed = Nd4j.create(new double[][][] { { { 11, 12 }, { 13, 14 } }, { { 15, 16 }, { 17, 18 } } });
		final INDArray massContraints = Nd4j.zeros(DataType.DOUBLE, 2, 2);
		return new UniverseImpl(1, density, speed, 0, 0, massContraints);
	}

	public UniverseImplTest() {
		Nd4j.create(new double[0]);
	}
}
