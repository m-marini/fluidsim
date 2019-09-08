package org.mmarini.fluid.xml;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mmarini.fluid.model.CompositeCellModifier;
import org.mmarini.fluid.model.CompositeModifier;
import org.mmarini.fluid.model.ConservativeFunction;
import org.mmarini.fluid.model.ConstantFunction;
import org.mmarini.fluid.model.DefaultRelationFunction;
import org.mmarini.fluid.model.DiffusionFunction;
import org.mmarini.fluid.model.ElasticFunction;
import org.mmarini.fluid.model.FluidFunction;
import org.mmarini.fluid.model.FunctionModifier;
import org.mmarini.fluid.model.IsomorphCellFunction;
import org.mmarini.fluid.model.LineModifier;
import org.mmarini.fluid.model.OscillatorFunction;
import org.mmarini.fluid.model.PointModifier;
import org.mmarini.fluid.model.RectangleModifier;
import org.mmarini.fluid.model.RelationCellModifier;
import org.mmarini.fluid.model.UniverseModifier;
import org.mmarini.fluid.model.ValueCellModifier;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class FluidParserTest {
	private static Matcher<Object> has2Points(final double x0, final double y0,
			final double x1, final double y1) {
		return allOf(hasProperty("x0", equalTo(x0)),
				hasProperty("y0", equalTo(y0)), hasProperty("x1", equalTo(x1)),
				hasProperty("y1", equalTo(y1)));
	}

	private static Matcher<? super UniverseModifier> hasCellFunction(
			final Matcher<Object> matcher) {
		return hasFunctionModifier(hasProperty("cellFunction", matcher));
	}

	private static Matcher<? super UniverseModifier> hasF1(
			final Matcher<Object> matcher) {
		return hasRect1(hasProperty("cellModifier", matcher));
	}

	private static Matcher<? super UniverseModifier> hasF1Group(
			final Matcher<Object> matcher) {
		return hasF1(hasProperty("list", hasItemAt(1, 2, matcher)));
	}

	private static Matcher<? super UniverseModifier> hasFunction1(
			final Matcher<Object> matcher) {
		return hasCellFunction(hasProperty("function", matcher));
	}

	private static Matcher<? super UniverseModifier> hasFunction2(
			final Matcher<Object> matcher) {
		return hasRelationFunction(hasProperty("function", matcher));
	}

	private static Matcher<? super UniverseModifier> hasFunctionModifier(
			final Matcher<Object> matcher) {
		return hasF1(hasListWithItemAt(0, 2, matcher));
	}

	private static Matcher<? super UniverseModifier> hasFunctionN(
			final int index, final Matcher<Object> matcher) {
		return hasRelationFunctionN(index, hasProperty("function", matcher));
	}

	private static Matcher<? super UniverseModifier> hasGroup1(
			final Matcher<Object> matcher) {
		return hasListWithItemAt(1, 2, matcher);
	}

	private static <E> Matcher<Iterable<? extends E>> hasItemAt(final int idx,
			final int size, final Matcher<? super E> matcher) {
		final List<Matcher<? super E>> list = new ArrayList<Matcher<? super E>>();
		for (int i = 0; i < size; ++i) {
			if (i == idx)
				list.add(matcher);
			else
				list.add(anything());
		}
		return contains(list);
	}

	private static Matcher<? super UniverseModifier> hasLine(
			final Matcher<Object> matcher) {
		return hasGroup1(hasListWithItemAt(1, 2, matcher));
	}

	private static Matcher<Object> hasListWithItemAt(final int index,
			final int size, final Matcher<Object> matcher) {
		return hasProperty("list", hasItemAt(index, size, matcher));
	}

	private static Matcher<? super UniverseModifier> hasPoint(
			final Matcher<Object> matcher) {
		return hasGroup1(hasListWithItemAt(0, 2, matcher));
	}

	private static Matcher<? super UniverseModifier> hasRect1(
			final Matcher<Object> other) {
		return hasListWithItemAt(0, 2, other);
	}

	private static Matcher<? super UniverseModifier> hasRelationCellModifier(
			final Matcher<Object> matcher) {
		return hasPoint(hasProperty("cellModifier", matcher));
	}

	private static Matcher<? super UniverseModifier> hasRelationFunction(
			final Matcher<Object> matcher) {
		return hasFunctionModifier(hasProperty("relationFunction", matcher));
	}

	private static Matcher<? super UniverseModifier> hasRelationFunctionN(
			final int index, final Matcher<Object> matcher) {
		return hasRelationCellModifier(hasListWithItemAt(index, 6, matcher));
	}

	private static Matcher<? super UniverseModifier> hasValueCellModifier3(
			final Matcher<Object> matcher) {
		return hasF1Group(hasListWithItemAt(0, 2, matcher));
	}

	private static Matcher<? super UniverseModifier> hasValueCellModifier4(
			final Matcher<Object> matcher) {
		return hasF1Group(hasListWithItemAt(1, 2, matcher));
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testParse() throws MalformedURLException,
			ParserConfigurationException, SAXException, IOException {
		final FluidParser p = new FluidParser();
		final UniverseModifier m = p.parse(getClass().getResource(
				"/fluid-test.xml"));

		assertThat(m, notNullValue());
		assertThat(m, instanceOf(CompositeModifier.class));

		assertThat(m, hasRect1(instanceOf(RectangleModifier.class)));
		assertThat(m, hasRect1(has2Points(20, 21, 22, 23)));

		assertThat(m, hasF1(instanceOf(CompositeCellModifier.class)));

		assertThat(m, hasFunctionModifier(instanceOf(FunctionModifier.class)));

		assertThat(m, hasCellFunction(instanceOf(IsomorphCellFunction.class)));

		assertThat(m, hasFunction1(instanceOf(ConstantFunction.class)));
		assertThat(m, hasFunction1(hasProperty("value", equalTo(1.))));

		assertThat(m,
				hasRelationFunction(instanceOf(DefaultRelationFunction.class)));

		assertThat(m, hasFunction2(instanceOf(ConstantFunction.class)));
		assertThat(m, hasFunction2(hasProperty("value", equalTo(2.))));

		assertThat(m, hasF1Group(instanceOf(CompositeCellModifier.class)));

		assertThat(m,
				hasValueCellModifier3(instanceOf(ValueCellModifier.class)));
		assertThat(m,
				hasValueCellModifier3(hasProperty("cellValue", equalTo(3.))));

		assertThat(m,
				hasValueCellModifier4(instanceOf(ValueCellModifier.class)));
		assertThat(m,
				hasValueCellModifier4(hasProperty("cellValue", equalTo(4.))));

		assertThat(m, hasGroup1(instanceOf(CompositeModifier.class)));

		assertThat(m, hasPoint(instanceOf(PointModifier.class)));
		assertThat(m, hasPoint(hasProperty("x", equalTo(24.))));
		assertThat(m, hasPoint(hasProperty("y", equalTo(25.))));

		assertThat(m,
				hasRelationCellModifier(instanceOf(RelationCellModifier.class)));

		assertThat(
				m,
				hasRelationFunctionN(0,
						instanceOf(DefaultRelationFunction.class)));

		assertThat(m, hasFunctionN(0, instanceOf(ConservativeFunction.class)));

		assertThat(
				m,
				hasRelationFunctionN(1,
						instanceOf(DefaultRelationFunction.class)));

		assertThat(m, hasFunctionN(1, instanceOf(ConstantFunction.class)));
		assertThat(m, hasFunctionN(1, hasProperty("value", equalTo(0.))));

		assertThat(
				m,
				hasRelationFunctionN(2,
						instanceOf(DefaultRelationFunction.class)));

		assertThat(m, hasFunctionN(2, instanceOf(DiffusionFunction.class)));
		assertThat(m, hasFunctionN(2, hasProperty("diffusion", equalTo(5.))));

		assertThat(
				m,
				hasRelationFunctionN(3,
						instanceOf(DefaultRelationFunction.class)));

		assertThat(m, hasFunctionN(3, instanceOf(ElasticFunction.class)));
		assertThat(m, hasFunctionN(3, hasProperty("speed", equalTo(6.))));

		assertThat(
				m,
				hasRelationFunctionN(4,
						instanceOf(DefaultRelationFunction.class)));

		assertThat(m, hasFunctionN(4, instanceOf(FluidFunction.class)));
		assertThat(m, hasFunctionN(4, hasProperty("speed", equalTo(7.))));
		assertThat(m, hasFunctionN(4, hasProperty("viscosity", equalTo(8.))));

		assertThat(
				m,
				hasRelationFunctionN(5,
						instanceOf(DefaultRelationFunction.class)));

		assertThat(m, hasFunctionN(5, instanceOf(OscillatorFunction.class)));
		assertThat(m, hasFunctionN(5, hasProperty("value", equalTo(9.))));
		assertThat(m, hasFunctionN(5, hasProperty("period", equalTo(10.))));

		assertThat(m, hasLine(instanceOf(LineModifier.class)));
		assertThat(m, hasLine(has2Points(26, 27, 28, 29)));
		assertThat(m, hasLine(instanceOf(LineModifier.class)));
		assertThat(m, hasLine(instanceOf(LineModifier.class)));
	}

	@Test
	public void testParseBad() throws MalformedURLException,
			ParserConfigurationException, SAXException, IOException {
		thrown.expect(SAXParseException.class);
		thrown.expectMessage(containsString("bad value"));
		final FluidParser p = new FluidParser();
		p.parse(getClass().getResource("/fluid-test-bad.xml"));
	}

}
