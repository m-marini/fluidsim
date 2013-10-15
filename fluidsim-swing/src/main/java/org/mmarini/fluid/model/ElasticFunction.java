/*
 * ElasticFunction.java
 *
 * $Id: ElasticFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The ElasticFunction simulates the elastic effect.
 * <p>
 * The difference of value between two adjacent cells determine a pressure
 * between the cells. This pressure determines an acceleration of the values
 * exchanged. The change of the flux is proportianal to the pressure and the
 * time.<br>
 * The flux difference is incoming the cell if the adiacent cells have greater
 * values.
 * <p>
 * &phi;(t+&Delta;t)=&omega;(t) * k * &Delta;t + &phi;(t)
 * </p>
 * <p>
 * <ul>
 * <li>a = k * &Delta;t</li>
 * <li>b = 1</li>
 * <li>c = 0</li>
 * </ul>
 * </p>
 * <p>
 * The constant k determines how high is the acceleration.
 * </p>
 * 
 * @author US00852
 * @version $Id: ElasticFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 * 
 */
public class ElasticFunction implements CoefficientFunction {
	/**
	 * @param speed
	 */
	public ElasticFunction(double speed) {
		this.speed = speed;
	}

	/**
	 * 
	 */
	public ElasticFunction() {
		super();
	}

	private double speed;

	/**
	 * @see CoefficientFunction#getA(TimeContext)
	 */
	@Override
	public double getA(TimeContext timeContext) {
		return getSpeed() * timeContext.getDeltaTime();
	}

	/**
	 * @see CoefficientFunction#getB(TimeContext)
	 */
	@Override
	public double getB(TimeContext timeContext) {
		return 1;
	}

	/**
	 * @see CoefficientFunction#getC(TimeContext)
	 */
	@Override
	public double getC(TimeContext timeContext) {
		return 0;
	}

	/**
	 * Returns the speed.
	 * 
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed.
	 * 
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
