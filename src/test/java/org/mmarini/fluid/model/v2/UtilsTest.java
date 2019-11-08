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

	@Test
	public void testCatShape() {
		final long[] shape1 = new long[] { 1, 2 };
		final long[] shape2 = new long[] { 3, 4, 5 };
		final long[] shape3 = new long[] { 6 };
		final long[] a = Utils.catShape(shape1, shape2, shape3);
		assertThat(a, equalTo(new long[] { 1, 2, 3, 4, 5, 6 }));
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
	public void testvsmul1() {
		final INDArray a = Nd4j.create(new double[][][] { { { 1, 1 }, { 1, 2 } }, { { 2, 1 }, { 2, 2 } } });
		final INDArray b = Nd4j.create(new double[][] { { 1, 2 }, { 3, 4 } });
		final INDArray c = Utils.vsmul(a, b);

		assertThat(c.shape(), equalTo(new long[] { 2, 2, 2 }));
		assertThat(c.getDouble(0, 0, 0), equalTo(1.0));
		assertThat(c.getDouble(0, 0, 1), equalTo(1.0));
		assertThat(c.getDouble(0, 1, 0), equalTo(2.0));
		assertThat(c.getDouble(0, 1, 1), equalTo(4.0));
		assertThat(c.getDouble(1, 0, 0), equalTo(6.0));
		assertThat(c.getDouble(1, 0, 1), equalTo(3.0));
		assertThat(c.getDouble(1, 1, 0), equalTo(8.0));
		assertThat(c.getDouble(1, 1, 1), equalTo(8.0));
	}

	@Test
	public void testvsmul2() {
		final INDArray a = Nd4j.create(new double[][][][] { { { { 1, 1 }, { 1, 2 } }, { { 2, 1 }, { 2, 2 } } },
				{ { { 1, 1 }, { 1, 2 } }, { { 2, 1 }, { 2, 2 } } } });
		final INDArray b = Nd4j.create(new double[][][] { { { 1, 2 }, { 3, 4 } }, { { 4, 5 }, { 6, 7 } } });
		final INDArray c = Utils.vsmul(a, b);

		assertThat(c.shape(), equalTo(new long[] { 2, 2, 2, 2 }));
		assertThat(c.getDouble(0, 0, 0, 0), equalTo(1.0));
		assertThat(c.getDouble(0, 0, 0, 1), equalTo(1.0));
		assertThat(c.getDouble(0, 0, 1, 0), equalTo(2.0));
		assertThat(c.getDouble(0, 0, 1, 1), equalTo(4.0));
		assertThat(c.getDouble(0, 1, 0, 0), equalTo(6.0));
		assertThat(c.getDouble(0, 1, 0, 1), equalTo(3.0));
		assertThat(c.getDouble(0, 1, 1, 0), equalTo(8.0));
		assertThat(c.getDouble(0, 1, 1, 1), equalTo(8.0));
		assertThat(c.getDouble(1, 0, 0, 0), equalTo(4.0));
		assertThat(c.getDouble(1, 0, 0, 1), equalTo(4.0));
		assertThat(c.getDouble(1, 0, 1, 0), equalTo(5.0));
		assertThat(c.getDouble(1, 0, 1, 1), equalTo(10.0));
		assertThat(c.getDouble(1, 1, 0, 0), equalTo(12.0));
		assertThat(c.getDouble(1, 1, 0, 1), equalTo(6.0));
		assertThat(c.getDouble(1, 1, 1, 0), equalTo(14.0));
		assertThat(c.getDouble(1, 1, 1, 1), equalTo(14.0));
	}

	@Test
	public void testvvmul1() {
		final INDArray a = Nd4j.create(new double[][][][] { { { { 1, 1 }, { 1, 2 } }, { { 2, 1 }, { 2, 2 } } },
				{ { { 1, 1 }, { 1, 2 } }, { { 2, 1 }, { 2, 2 } } } });
		final INDArray c = Utils.vvmul(a, a);

		assertThat(c.shape(), equalTo(new long[] { 2, 2, 2 }));
		assertThat(c.getDouble(0, 0, 0), equalTo(2.0));
		assertThat(c.getDouble(0, 0, 1), equalTo(5.0));
		assertThat(c.getDouble(0, 1, 0), equalTo(5.0));
		assertThat(c.getDouble(0, 1, 1), equalTo(8.0));
		assertThat(c.getDouble(1, 0, 0), equalTo(2.0));
		assertThat(c.getDouble(1, 0, 1), equalTo(5.0));
		assertThat(c.getDouble(1, 1, 0), equalTo(5.0));
		assertThat(c.getDouble(1, 1, 1), equalTo(8.0));
	}

	@Test
	public void testvvmul2() {
		final INDArray a = Nd4j.create(new double[][][] { { { 1, 1 }, { 1, 2 } }, { { 2, 1 }, { 2, 2 } } });
		final INDArray c = Utils.vvmul(a, a);

		assertThat(c.shape(), equalTo(new long[] { 2, 2 }));
		assertThat(c.getDouble(0, 0), equalTo(2.0));
		assertThat(c.getDouble(0, 1), equalTo(5.0));
		assertThat(c.getDouble(1, 0), equalTo(5.0));
		assertThat(c.getDouble(1, 1), equalTo(8.0));
	}
}
