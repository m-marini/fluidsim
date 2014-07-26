package org.mmarini.fluid.model1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mmarini.fluid.model1.Matchers.isVector;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class CellTest {
	public static class CoordSupplier extends ParameterSupplier {

		@Override
		public List<PotentialAssignment> getValueSources(
				final ParameterSignature sig) {
			final List<PotentialAssignment> p = new ArrayList<PotentialAssignment>();
			for (final double v : new double[] { -10, -2, -1, 0, 1, 2, 10 })
				p.add(PotentialAssignment.forValue("x=" + v, v));
			return p;
		}
	}

	public static class MassSupplier extends ParameterSupplier {

		@Override
		public List<PotentialAssignment> getValueSources(
				final ParameterSignature sig) {
			final List<PotentialAssignment> p = new ArrayList<PotentialAssignment>();
			for (final double v : new double[] { 1, 2, 10 })
				p.add(PotentialAssignment.forValue("mass=" + v, v));
			return p;
		}
	}

	@Theory
	public void testAddMass(
			@ParametersSuppliedBy(MassSupplier.class) final Double m0,
			@ParametersSuppliedBy(MassSupplier.class) final Double m1,
			@ParametersSuppliedBy(CoordSupplier.class) final Double x,
			@ParametersSuppliedBy(CoordSupplier.class) final Double y) {
		final Cell c = new Cell(m0, new Vector2d(x, y)).addMass(m1);
		assertThat(c, notNullValue());
		assertThat(c.getMass(), equalTo(m0));
		assertThat(c.getMomentum(), isVector(x, y));

		final Cell c1 = c.apply();
		assertThat(c1, notNullValue());
		assertThat(c1.getMass(), equalTo(m0 + m1));
		assertThat(c1.getMomentum(), isVector(x, y));
	}

	@Theory
	public void testAddMass1(
			@ParametersSuppliedBy(MassSupplier.class) final Double m0,
			@ParametersSuppliedBy(MassSupplier.class) final Double m1,
			@ParametersSuppliedBy(CoordSupplier.class) final Double x,
			@ParametersSuppliedBy(CoordSupplier.class) final Double y) {
		final Cell c = new Cell(0, new Vector2d(x, y)).addMass(m0).addMass(m1);
		assertThat(c, notNullValue());
		assertThat(c.getMass(), equalTo(0.0));
		assertThat(c.getMomentum(), isVector(x, y));

		final Cell c1 = c.apply();
		assertThat(c1, notNullValue());
		assertThat(c1.getMass(), equalTo(m0 + m1));
		assertThat(c1.getMomentum(), isVector(x, y));
	}

	@Theory
	public void testAddMomentum(
			@ParametersSuppliedBy(MassSupplier.class) final Double m,
			@ParametersSuppliedBy(CoordSupplier.class) final Double x0,
			@ParametersSuppliedBy(CoordSupplier.class) final Double y0,
			@ParametersSuppliedBy(CoordSupplier.class) final Double x1,
			@ParametersSuppliedBy(CoordSupplier.class) final Double y1) {
		final Cell c = new Cell(m, new Vector2d(x0, y0))
				.addMomentum(new Vector2d(x1, y1));
		assertThat(c, notNullValue());
		assertThat(c.getMass(), equalTo(m));
		assertThat(c.getMomentum(), isVector(x0, y0));

		final Cell c1 = c.apply();
		assertThat(c1, notNullValue());
		assertThat(c1.getMass(), equalTo(m));
		assertThat(c1.getMomentum(), isVector(x0 + x1, y0 + y1));
	}

	@Theory
	public void testAddMomentum1(
			@ParametersSuppliedBy(MassSupplier.class) final Double m,
			@ParametersSuppliedBy(CoordSupplier.class) final Double x0,
			@ParametersSuppliedBy(CoordSupplier.class) final Double y0,
			@ParametersSuppliedBy(CoordSupplier.class) final Double x1,
			@ParametersSuppliedBy(CoordSupplier.class) final Double y1) {
		final Cell c = new Cell(m, Vector2d.ZERO).addMomentum(
				new Vector2d(x0, y0)).addMomentum(new Vector2d(x1, y1));
		assertThat(c, notNullValue());
		assertThat(c.getMass(), equalTo(m));
		assertThat(c.getMomentum(), isVector(0, 0));

		final Cell c1 = c.apply();
		assertThat(c1, notNullValue());
		assertThat(c1.getMass(), equalTo(m));
		assertThat(c1.getMomentum(), isVector(x0 + x1, y0 + y1));
	}

	@Theory
	public void testGetVelocity(
			@ParametersSuppliedBy(MassSupplier.class) final Double m,
			@ParametersSuppliedBy(CoordSupplier.class) final Double x,
			@ParametersSuppliedBy(CoordSupplier.class) final Double y) {
		assertThat(new Cell(m, new Vector2d(x, y)).getVelocity(),
				isVector(x / m, y / m));
	}

	@Test
	public void testNewCell() {
		final Cell c = new Cell();
		assertThat(c.getMass(), equalTo(0.0));
		assertThat(c.getMomentum(), isVector(0, 0));
	}

	@Theory
	public void testNewCell(
			@ParametersSuppliedBy(MassSupplier.class) final Double m,
			@ParametersSuppliedBy(CoordSupplier.class) final Double x,
			@ParametersSuppliedBy(CoordSupplier.class) final Double y) {
		final Cell c = new Cell(m, new Vector2d(x, y));
		assertThat(c.getMass(), equalTo(m));
		assertThat(c.getMomentum(), isVector(x, y));
	}

	@Theory
	public void testSetMass(
			@ParametersSuppliedBy(MassSupplier.class) final Double m) {
		final Cell c = new Cell().setMass(m);
		assertThat(c, notNullValue());
		assertThat(c.getMass(), equalTo(m));
	}

	@Theory
	public void testSetMomentum(
			@ParametersSuppliedBy(CoordSupplier.class) final Double x,
			@ParametersSuppliedBy(CoordSupplier.class) final Double y) {
		final Cell c = new Cell().setMomentum(new Vector2d(x, y));
		assertThat(c, notNullValue());
		assertThat(c.getMomentum(), isVector(x, y));
	}
}
