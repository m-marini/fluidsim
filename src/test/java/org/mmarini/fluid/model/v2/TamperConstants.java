package org.mmarini.fluid.model.v2;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public interface TamperConstants {

	public static final double TAMPER00 = 1.00;
	public static final double TAMPER01 = 1.01;
	public static final double TAMPER02 = 1.02;
	public static final double TAMPER10 = 1.10;
	public static final double TAMPER11 = 1.11;
	public static final double TAMPER12 = 1.12;
	public static final double TAMPER20 = 1.20;
	public static final double TAMPER21 = 1.21;
	public static final double TAMPER22 = 1.22;
	public static final INDArray TAMPER = Nd4j.create(new double[][] { { TAMPER00, TAMPER01, TAMPER02 },
			{ TAMPER10, TAMPER11, TAMPER12 }, { TAMPER20, TAMPER21, TAMPER22 }, });;

	public static final double TAMPERH00 = TAMPER00;
	public static final double TAMPERH01 = TAMPER01;
	public static final double TAMPERH02 = TAMPER02;
	public static final double TAMPERH10 = (TAMPER00 + TAMPER10) / 2;
	public static final double TAMPERH11 = (TAMPER01 + TAMPER11) / 2;
	public static final double TAMPERH12 = (TAMPER02 + TAMPER12) / 2;
	public static final double TAMPERH20 = (TAMPER10 + TAMPER20) / 2;
	public static final double TAMPERH21 = (TAMPER11 + TAMPER21) / 2;
	public static final double TAMPERH22 = (TAMPER12 + TAMPER22) / 2;
	public static final double TAMPERH30 = TAMPER20;
	public static final double TAMPERH31 = TAMPER21;
	public static final double TAMPERH32 = TAMPER22;

	public static final double TAMPERV00 = TAMPER00;
	public static final double TAMPERV01 = (TAMPER00 + TAMPER01) / 2;
	public static final double TAMPERV02 = (TAMPER01 + TAMPER02) / 2;
	public static final double TAMPERV03 = TAMPER02;
	public static final double TAMPERV10 = TAMPER10;
	public static final double TAMPERV11 = (TAMPER10 + TAMPER11) / 2;
	public static final double TAMPERV12 = (TAMPER11 + TAMPER12) / 2;
	public static final double TAMPERV13 = TAMPER12;
	public static final double TAMPERV20 = TAMPER20;
	public static final double TAMPERV21 = (TAMPER20 + TAMPER21) / 2;
	public static final double TAMPERV22 = (TAMPER21 + TAMPER22) / 2;
	public static final double TAMPERV23 = TAMPER22;

	public static final double TAMPER00_POW2 = TAMPER00 * TAMPER00;
	public static final double TAMPER01_POW2 = TAMPER01 * TAMPER01;
	public static final double TAMPER02_POW2 = TAMPER02 * TAMPER02;
	public static final double TAMPER10_POW2 = TAMPER10 * TAMPER10;
	public static final double TAMPER11_POW2 = TAMPER11 * TAMPER11;
	public static final double TAMPER12_POW2 = TAMPER12 * TAMPER12;
	public static final double TAMPER20_POW2 = TAMPER20 * TAMPER20;
	public static final double TAMPER21_POW2 = TAMPER21 * TAMPER21;
	public static final double TAMPER22_POW2 = TAMPER22 * TAMPER22;

	public static final double TAMPERH00_POW2 = TAMPERH00 * TAMPERH00;
	public static final double TAMPERH01_POW2 = TAMPERH01 * TAMPERH01;
	public static final double TAMPERH02_POW2 = TAMPERH02 * TAMPERH02;

	public static final double TAMPERH10_POW2 = TAMPERH10 * TAMPERH10;
	public static final double TAMPERH11_POW2 = TAMPERH11 * TAMPERH11;
	public static final double TAMPERH12_POW2 = TAMPERH12 * TAMPERH12;

	public static final double TAMPERH20_POW2 = TAMPERH20 * TAMPERH20;
	public static final double TAMPERH21_POW2 = TAMPERH21 * TAMPERH21;
	public static final double TAMPERH22_POW2 = TAMPERH22 * TAMPERH22;

	public static final double TAMPERH30_POW2 = TAMPERH30 * TAMPERH30;
	public static final double TAMPERH31_POW2 = TAMPERH31 * TAMPERH31;
	public static final double TAMPERH32_POW2 = TAMPERH32 * TAMPERH32;

	public static final double TAMPERV00_POW2 = TAMPERV00 * TAMPERV00;
	public static final double TAMPERV01_POW2 = TAMPERV01 * TAMPERV01;
	public static final double TAMPERV02_POW2 = TAMPERV02 * TAMPERV02;
	public static final double TAMPERV03_POW2 = TAMPERV03 * TAMPERV03;

	public static final double TAMPERV10_POW2 = TAMPERV10 * TAMPERV10;
	public static final double TAMPERV11_POW2 = TAMPERV11 * TAMPERV11;
	public static final double TAMPERV12_POW2 = TAMPERV12 * TAMPERV12;
	public static final double TAMPERV13_POW2 = TAMPERV13 * TAMPERV13;

	public static final double TAMPERV20_POW2 = TAMPERV20 * TAMPERV20;
	public static final double TAMPERV21_POW2 = TAMPERV21 * TAMPERV21;
	public static final double TAMPERV22_POW2 = TAMPERV22 * TAMPERV22;
	public static final double TAMPERV23_POW2 = TAMPERV23 * TAMPERV23;

	public static final double TAMPERH10_POW3 = TAMPERH10 * TAMPERH10 * TAMPERH10;
	public static final double TAMPERH11_POW3 = TAMPERH11 * TAMPERH11 * TAMPERH11;
	public static final double TAMPERH12_POW3 = TAMPERH12 * TAMPERH12 * TAMPERH12;

	public static final double TAMPERH20_POW3 = TAMPERH20 * TAMPERH20 * TAMPERH20;
	public static final double TAMPERH21_POW3 = TAMPERH21 * TAMPERH21 * TAMPERH21;
	public static final double TAMPERH22_POW3 = TAMPERH22 * TAMPERH22 * TAMPERH22;

	public static final double TAMPERV01_POW3 = TAMPERV01 * TAMPERV01 * TAMPERV01;
	public static final double TAMPERV02_POW3 = TAMPERV02 * TAMPERV02 * TAMPERV02;

	public static final double TAMPERV11_POW3 = TAMPERV11 * TAMPERV11 * TAMPERV11;
	public static final double TAMPERV12_POW3 = TAMPERV12 * TAMPERV12 * TAMPERV12;

	public static final double TAMPERV21_POW3 = TAMPERV21 * TAMPERV21 * TAMPERV21;
	public static final double TAMPERV22_POW3 = TAMPERV22 * TAMPERV22 * TAMPERV22;
}
