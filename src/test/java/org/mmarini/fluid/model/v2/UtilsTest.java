/*
 * LineModifierTest.java
 *
 * $Id: LineModifierTestVert.java,v 1.1 2007/08/18 08:28:29 marco Exp $
 *
 * 16/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model.v2;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: LineModifierTestVert.java,v 1.1 2007/08/18 08:28:29 marco Exp $
 *
 */
public class UtilsTest {

	/**
	 *
	 */
	public UtilsTest() {
		Nd4j.create(new double[0]);
	}

	public INDArray padded() {
		return Nd4j.pad(Nd4j.create(new double[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, }), 1, 1);
	}

	@Test
	public void testCatShape() {
		final long[] shape1 = new long[] { 1, 2 };
		final long[] shape2 = new long[] { 3, 4, 5 };
		final long[] shape3 = new long[] { 6 };
		final long[] a = Utils.catShape(shape1, shape2, shape3);
		assertThat(a, equalTo(new long[] { 1, 2, 3, 4, 5, 6 }));
	}

	@Test
	public void testCellDifferences() {
		final INDArray a = Nd4j.create(new double[][] { { 11, 12, 13 }, { 21, 22, 23 }, { 31, 32, 33 } });
		final INDArray b = Utils.cellDifferences(a);
		final INDArray expected = Nd4j.create(new double[][] { { 11 - 22, 12 - 22, 13 - 22 }, { 21 - 22, 0, 23 - 22 },
				{ 31 - 22, 32 - 22, 33 - 22 } });
		assertThat(b, equalTo(expected));
	}

	@Test
	public void testLocal() {
		final INDArray values = padded();

		final INDArray proj00 = Utils.local(values, new int[] { 0, 0 });
		assertThat(proj00, equalTo(Nd4j.create(new double[][] { { 0, 0, 0 }, { 0, 1, 2 }, { 0, 4, 5 }, })));

		final INDArray proj11 = Utils.local(values, new int[] { 1, 1 });
		assertThat(proj11, equalTo(Nd4j.create(new double[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, })));

		final INDArray proj22 = Utils.local(values, new int[] { 2, 2 });
		assertThat(proj22, equalTo(Nd4j.create(new double[][] { { 5, 6, 0 }, { 8, 9, 0 }, { 0, 0, 0 }, })));

		final INDArray proj01 = Utils.local(values, new int[] { 0, 1 });
		assertThat(proj01, equalTo(Nd4j.create(new double[][] { { 0, 0, 0 }, { 1, 2, 3 }, { 4, 5, 6 }, })));

		final INDArray proj10 = Utils.local(values, new int[] { 1, 0 });
		assertThat(proj10, equalTo(Nd4j.create(new double[][] { { 0, 1, 2 }, { 0, 4, 5 }, { 0, 7, 8 }, })));
	}

	@Test
	public void testMmul1() {
		final INDArray a = Nd4j.create(new double[][][] { { { 1, 1 }, { 1, 2 } }, { { 2, 1 }, { 2, 2 } } });
		final INDArray b = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray c = Utils.mmul(a, b);
		assertThat(c, equalTo(Nd4j.create(new double[][][] { { { 1, 1 }, { 2, 4 } }, { { 6, 3 }, { 8, 8 } } })));
	}

	@Test
	public void testMmul2() {
		final INDArray a = Nd4j.create(new double[][][] { { { 1, 1 }, { 1, 2 } }, { { 2, 1 }, { 2, 2 } } });
		final INDArray c = Utils.mmul(a, a);
		assertThat(c, equalTo(Nd4j.create(new double[][] { { 2, 5 }, { 5, 8 } })));
	}

	@Test
	public void testMul1() {
		final INDArray a = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray b = Utils.mul(a, a, 1);
		final INDArray expected = Nd4j.create(new double[][] { { 7, 10 }, { 15, 22 } });
		assertThat(b, equalTo(expected));
	}

	@Test
	public void testMul2() {
		final INDArray a = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray b = Nd4j.create(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
		final INDArray c = Utils.mul(a, b, 1);
		final INDArray expected = Nd4j.create(new double[][] { { 9, 12, 15 }, { 19, 26, 33 } });
		assertThat(c, equalTo(expected));
	}

	@Test
	public void testMul3() {
		final INDArray a = Nd4j.create(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
		final INDArray b = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } });
		final INDArray c = Utils.mul(a, b, 1);
		final INDArray expected = Nd4j.create(new double[][] { { 22, 28 }, { 49, 64 } });
		assertThat(c, equalTo(expected));
	}

	@Test
	public void testMul4() {
		final INDArray a = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray b = Utils.mul(a, a, 2);
		final INDArray expected = Nd4j.create(new double[] { 30. }).reshape(new long[0]);
		assertThat(b, equalTo(expected));
	}

	@Test
	public void testMul5() {
		final INDArray a = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray b = Utils.mul(a, a, 0);
		assertThat(b, equalTo(Nd4j.create(new double[][][][] { { { { 1, 2 }, { 3, 4 } }, { { 2, 4 }, { 6, 8 } } },
				{ { { 3, 6 }, { 9, 12 } }, { { 4, 8 }, { 12, 16 } } } })));
	}

	@Test
	public void testPrefixBroadcast2() {
		final INDArray a = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray b = Utils.prefixBroadcast(a, 2);
		final INDArray expected = Nd4j.create(new double[][][] { { { 1, 2 }, { 3, 4 } }, { { 1, 2 }, { 3, 4 } } });
		assertThat(b, equalTo(expected));
	}

	@Test
	public void testPrefixBroadcast22() {
		final INDArray a = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray b = Utils.prefixBroadcast(a, 2, 2);
		final INDArray expected = Nd4j.create(new double[][][][] { { { { 1, 2 }, { 3, 4 } }, { { 1, 2 }, { 3, 4 } } },
				{ { { 1, 2 }, { 3, 4 } }, { { 1, 2 }, { 3, 4 } } } });
		assertThat(b, equalTo(expected));
	}

	@Test
	public void testReversePermute1() {
		final int[] a = Utils.reversePermute(3, 2, 1, 0);
		assertThat(a, equalTo(new int[] { 3, 2, 1, 0 }));
	}

	public void testReversePermute2() {
		final int[] a = Utils.reversePermute(0, 1, 2, 3);
		assertThat(a, equalTo(new int[] { 0, 1, 2, 3 }));
	}

	public void testReversePermute3() {
		final int[] a = Utils.reversePermute(0, 2, 1, 3);
		assertThat(a, equalTo(new int[] { 0, 2, 1, 3 }));
	}

	public void testReversePermute4() {
		final int[] a = Utils.reversePermute(3, 0, 1, 2);
		assertThat(a, equalTo(new int[] { 1, 2, 3, 0 }));
	}

	@Test
	public void testSuffixBroadcast2() {
		final INDArray a = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray b = Utils.suffixBroadcast(a, 2);
		final INDArray expected = Nd4j.create(new double[][][] { { { 1, 1 }, { 2, 2 } }, { { 3, 3 }, { 4, 4 } } });
		assertThat(b, equalTo(expected));
	}

	@Test
	public void testSuffixBroadcast22() {
		final INDArray a = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray b = Utils.suffixBroadcast(a, 2, 2);
		final INDArray expected = Nd4j.create(new double[][][][] { { { { 1, 1 }, { 1, 1 } }, { { 2, 2 }, { 2, 2 } } },
				{ { { 3, 3 }, { 3, 3 } }, { { 4, 4 }, { 4, 4 } } } });
		assertThat(b, equalTo(expected));
	}

	@Test
	public void testVectorFlux() {
		final INDArray a = Nd4j.create(new double[][][] { { { 1, 1 }, { 1, 2 }, { 1, 3 } },
				{ { 2, 1 }, { 2, 2 }, { 2, 3 } }, { { 3, 1 }, { 3, 2 }, { 3, 3 } }, });
		final INDArray b = Utils.cellAddStamp(a);
		final INDArray expected = Nd4j.create(new double[][][] { { { 3, 3 }, { 3, 4 }, { 3, 5 } },
				{ { 4, 3 }, { 0, 0 }, { 4, 5 } }, { { 5, 3 }, { 5, 4 }, { 5, 5 } }, });
		assertThat(b, equalTo(expected));
	}
}
