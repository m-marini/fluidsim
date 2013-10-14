/**
 * 
 */
package org.mmarini.fluid.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import org.mmarini.fluid.model.CellModifier;
import org.mmarini.fluid.model.CellValueFunction;
import org.mmarini.fluid.model.CompositeCellModifier;
import org.mmarini.fluid.model.CompositeModifier;
import org.mmarini.fluid.model.ConservativeFunction;
import org.mmarini.fluid.model.ConstantFunction;
import org.mmarini.fluid.model.DefaultRelationFunction;
import org.mmarini.fluid.model.FluidFunction;
import org.mmarini.fluid.model.FluidHandlerImpl;
import org.mmarini.fluid.model.FluxValueFunction;
import org.mmarini.fluid.model.FunctionModifier;
import org.mmarini.fluid.model.IsomorphCellFunction;
import org.mmarini.fluid.model.LineModifier;
import org.mmarini.fluid.model.RectangleModifier;
import org.mmarini.fluid.model.RelationCellModifier;
import org.mmarini.fluid.model.RelationFunction;
import org.mmarini.fluid.model.RelationValueFunction;
import org.mmarini.fluid.model.Simulator;
import org.mmarini.fluid.model.UniverseBuilderImpl;
import org.mmarini.fluid.model.UniverseModifier;
import org.mmarini.fluid.model.ValueCellModifier;

/**
 * @author us00852
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main().start();
	}

	private SimFrame frame;
	private FluidHandlerImpl fluidHandler;
	private CompositeModifier universeModifier;

	/**
	 * 
	 */
	public Main() {
		universeModifier = createFluidBeans();
		fluidHandler = createModelBeans();
		frame = createUIBeans();
	}

	/**
	 * 
	 */
	private SimFrame createUIBeans() {
		RateBar rateBar = new RateBar();
		rateBar.setFluidHandler(fluidHandler);
		rateBar.setRefreshPeriod(1000);
		rateBar.setMaximum(200);
		rateBar.setStringPainted(true);
		rateBar.init();

		FluxGraphFunction fluxGraphFunction = new FluxGraphFunction();
		fluxGraphFunction.setFluidHandler(fluidHandler);

		RelationGraphFunction speedGraphFunction = new RelationGraphFunction();
		speedGraphFunction.setFluidHandler(fluidHandler);

		CellGraphFunction cellGraphFunction = new CellGraphFunction();
		cellGraphFunction.setFluidHandler(fluidHandler);

		GraphPane fluxGraphPane = new GraphPane();
		fluxGraphPane.setFunction(fluxGraphFunction);
		fluxGraphPane.init();

		GraphPane relationGraphPane = new GraphPane();
		relationGraphPane.setFunction(speedGraphFunction);
		relationGraphPane.init();

		GraphPane cellGraphPane = new GraphPane();
		cellGraphPane.setFunction(cellGraphFunction);
		cellGraphPane.init();

		TabbedPane tabPane = new TabbedPane();
		tabPane.setCellComponent(cellGraphPane);
		tabPane.setRelationComponent(relationGraphPane);
		tabPane.setFluxComponent(fluxGraphPane);
		tabPane.init();

		JToolBar toolBar = new JToolBar();

		JMenuBar jMenuBar = new JMenuBar();

		ActionHandler actionHandler = new ActionHandler();
		actionHandler.setMenuBar(jMenuBar);
		actionHandler.setToolBar(toolBar);
		actionHandler.setCellPane(cellGraphPane);
		actionHandler.setRelationPane(relationGraphPane);
		actionHandler.setFluxPane(fluxGraphPane);
		actionHandler.setRateBar(rateBar);
		actionHandler.setFluidHandler(fluidHandler);
		actionHandler.init();

		SimFrame frame = new SimFrame();
		frame.setMainPane(tabPane);
		frame.setJMenuBar(jMenuBar);
		frame.setToolBar(toolBar);
		frame.setInfo(rateBar);
		frame.init();

		return frame;
	}

	/**
	 * 
	 * @return
	 */
	private FluidHandlerImpl createModelBeans() {

		UniverseBuilderImpl builder = new UniverseBuilderImpl();
		builder.setUniverseModifier(universeModifier);

		Simulator simulator = new Simulator();
		simulator.setMinElapsed(80);
		simulator.setSimulationRate(1);
		simulator.setSingleStepTime(0.1);

		CellValueFunction cellFunction = new CellValueFunction();
		cellFunction.setScale(10);
		cellFunction.setOffset(0.45);

		RelationValueFunction speedFunction = new RelationValueFunction();
		speedFunction.setOffset(0);
		speedFunction.setScale(2000);

		FluxValueFunction fluxFunction = new FluxValueFunction();
		fluxFunction.setScale(600e-3);
		fluxFunction.setOffset(0);

		FluidHandlerImpl fluidHandler = new FluidHandlerImpl();
		fluidHandler.setBuilder(builder);
		fluidHandler.setSimulator(simulator);
		fluidHandler.setCellFunction(cellFunction);
		fluidHandler.setRelationFunction(speedFunction);
		fluidHandler.setFluxFunction(fluxFunction);
		fluidHandler.createNew();
		return fluidHandler;

	}

	/**
	 * 
	 */
	private CompositeModifier createFluidBeans() {
		FluidFunction fluidFunction = new FluidFunction();
		fluidFunction.setSpeed(10.0);
		fluidFunction.setViscosity(0.4);

		ConstantFunction zeroFunction = new ConstantFunction();
		zeroFunction.setValue(0);

		ConstantFunction generatorFunction = new ConstantFunction();
		generatorFunction.setValue(-50e-3);

		DefaultRelationFunction backgroundRelationFunction = new DefaultRelationFunction();
		backgroundRelationFunction.setFunction(fluidFunction);

		DefaultRelationFunction zeroRelationFunction = new DefaultRelationFunction();
		zeroRelationFunction.setFunction(zeroFunction);

		DefaultRelationFunction generatorRelationFunction = new DefaultRelationFunction();
		generatorRelationFunction.setFunction(generatorFunction);

		ConservativeFunction conservativeFunction = new ConservativeFunction();

		IsomorphCellFunction backgroundCellFunction = new IsomorphCellFunction();
		backgroundCellFunction.setFunction(conservativeFunction);

		IsomorphCellFunction zeroCellFunction = new IsomorphCellFunction();
		zeroCellFunction.setFunction(zeroFunction);

		FunctionModifier initialMaterialCellModifier = new FunctionModifier();
		initialMaterialCellModifier.setCellFunction(backgroundCellFunction);
		initialMaterialCellModifier
				.setRelationFunction(backgroundRelationFunction);

		ValueCellModifier initialValueCellModifier = new ValueCellModifier();
		initialValueCellModifier.setCellValue(0.5);
		initialValueCellModifier.setRightRelation(0);
		initialValueCellModifier.setUpRightRelation(0);
		initialValueCellModifier.setUpLeftRelation(0);

		List<CellModifier> list1 = new ArrayList<CellModifier>();
		list1.add(initialValueCellModifier);
		list1.add(initialMaterialCellModifier);
		CompositeCellModifier initialCompositeCellModifier = new CompositeCellModifier();
		initialCompositeCellModifier.setList(list1);

		List<RelationFunction> list2 = new ArrayList<RelationFunction>();
		list2.add(generatorRelationFunction);
		list2.add(zeroRelationFunction);
		list2.add(zeroRelationFunction);
		list2.add(generatorRelationFunction);
		list2.add(zeroRelationFunction);
		list2.add(zeroRelationFunction);
		RelationCellModifier fluxGeneratorCellModifier = new RelationCellModifier();
		fluxGeneratorCellModifier.setList(list2);

		FunctionModifier shieldCellModifier = new FunctionModifier();
		shieldCellModifier.setCellFunction(zeroCellFunction);
		shieldCellModifier.setRelationFunction(zeroRelationFunction);

		LineModifier lowerWing = new LineModifier();
		lowerWing.setCellModifier(shieldCellModifier);
		lowerWing.setX0(0.3);
		lowerWing.setY0(0.55);
		lowerWing.setX1(0.7);
		lowerWing.setY1(0.45);

		LineModifier upperLeftWing = new LineModifier();
		upperLeftWing.setCellModifier(shieldCellModifier);
		upperLeftWing.setX0(0.3);
		upperLeftWing.setY0(0.55);
		upperLeftWing.setX1(0.35);
		upperLeftWing.setY1(0.6);

		LineModifier upperRightWing = new LineModifier();
		upperRightWing.setCellModifier(shieldCellModifier);
		upperRightWing.setX0(0.35);
		upperRightWing.setY0(0.6);
		upperRightWing.setX1(0.7);
		upperRightWing.setY1(0.45);

		LineModifier leftFluxGenerator = new LineModifier();
		leftFluxGenerator.setCellModifier(fluxGeneratorCellModifier);
		leftFluxGenerator.setX0(0);
		leftFluxGenerator.setY0(0);
		leftFluxGenerator.setX1(0);
		leftFluxGenerator.setY1(1);

		LineModifier rightFluxGenerator = new LineModifier();
		rightFluxGenerator.setCellModifier(fluxGeneratorCellModifier);
		rightFluxGenerator.setX0(1);
		rightFluxGenerator.setY0(0);
		rightFluxGenerator.setX1(1);
		rightFluxGenerator.setY1(1);

		List<UniverseModifier> list7 = new ArrayList<UniverseModifier>();
		list7.add(leftFluxGenerator);
		list7.add(rightFluxGenerator);
		CompositeModifier compositeFluxGenerator = new CompositeModifier();
		compositeFluxGenerator.setList(list7);

		RectangleModifier backgroundModifier = new RectangleModifier();
		backgroundModifier.setCellModifier(initialCompositeCellModifier);
		backgroundModifier.setX0(0);
		backgroundModifier.setY0(0);
		backgroundModifier.setX1(1);
		backgroundModifier.setY1(1);

		List<UniverseModifier> list6 = new ArrayList<UniverseModifier>();
		list6.add(backgroundModifier);
		list6.add(compositeFluxGenerator);
		CompositeModifier windTunnel = new CompositeModifier();
		windTunnel.setList(list6);

		List<UniverseModifier> list4 = new ArrayList<UniverseModifier>();
		list4.add(windTunnel);
		list4.add(lowerWing);
		list4.add(upperLeftWing);
		list4.add(upperRightWing);
		CompositeModifier wing = new CompositeModifier();
		wing.setList(list4);

		LineModifier flap = new LineModifier();
		flap.setCellModifier(shieldCellModifier);
		flap.setX0(0.72);
		flap.setY0(0.44);
		flap.setX1(0.74);
		flap.setY1(0.39);

		List<UniverseModifier> list3 = new ArrayList<UniverseModifier>();
		list3.add(wing);
		list3.add(flap);
		CompositeModifier wingFlap = new CompositeModifier();
		wingFlap.setList(list3);

		LineModifier upperVenturiModifier = new LineModifier();
		upperVenturiModifier.setCellModifier(shieldCellModifier);
		upperVenturiModifier.setX0(0.2);
		upperVenturiModifier.setY0(0.7);
		upperVenturiModifier.setX1(0.8);
		upperVenturiModifier.setY1(0.55);

		LineModifier lowerVenturiModifier = new LineModifier();
		lowerVenturiModifier.setCellModifier(shieldCellModifier);
		lowerVenturiModifier.setX0(0.2);
		lowerVenturiModifier.setY0(0.3);
		lowerVenturiModifier.setX1(0.8);
		lowerVenturiModifier.setY1(0.45);

		List<UniverseModifier> list5 = new ArrayList<UniverseModifier>();
		list5.add(windTunnel);
		list5.add(lowerVenturiModifier);
		list5.add(upperVenturiModifier);
		CompositeModifier venturi = new CompositeModifier();
		venturi.setList(list5);

		return wingFlap;
	}

	/**
	 * 
	 */
	private void start() {
		frame.setVisible(true);
	}

}
