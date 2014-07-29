package org.mmarini.fluid.model1;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SimulationTest {

	@Test
	public void test() {
		final Cell cell = mock(Cell.class);
		final Map<Point, Cell> cells = new HashMap<Point, Cell>();
		cells.put(new Point(0, 0), cell);
		final TimeFunctor f = mock(TimeFunctor.class);
		final TimeFunctor[] functors = new TimeFunctor[] { f };
		new Simulation(cells, functors).apply(1);
		verify(cell).apply();
		verify(f).apply(1.0);
	}

}
