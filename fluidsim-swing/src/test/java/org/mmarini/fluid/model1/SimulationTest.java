package org.mmarini.fluid.model1;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class SimulationTest {

	@Test
	public void test() {
		final Cell cell = mock(Cell.class);
		final Cell[][] cells = new Cell[][] { { cell } };
		final TimeFunctor f = mock(TimeFunctor.class);
		final TimeFunctor[] functors = new TimeFunctor[] { f };
		final Simulation s = new Simulation(cells, functors).apply(1);
		verify(cell).apply();
		verify(f).apply(1.0);
	}

}
