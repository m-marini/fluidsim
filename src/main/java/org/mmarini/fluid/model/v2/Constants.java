// Copyright (c) 2019 Marco Marini, marco.marini@mmarini.org
//
// Licensed under the MIT License (MIT);
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// https://opensource.org/licenses/MIT
//
// Permission is hereby granted, free of charge, to any person
// obtaining a copy of this software and associated documentation
// files (the "Software"), to deal in the Software without
// restriction, including without limitation the rights to use,
// copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following
// conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
// OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
// HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// OTHER DEALINGS IN THE SOFTWARE.

package org.mmarini.fluid.model.v2;

/**
 * @author mmarini
 *
 */
public interface Constants {

	public static final double SIZE = 10e-3; // m
	public static final double AREA = SIZE * SIZE; // m^2
	public static final double VOLUME = SIZE * SIZE * SIZE; // m^3
	public static final double R = 8.31446; // J / (Kg mol)
	public static final double ISA_TEMPERATURE = 288.15; // 15 Celsius
	public static final double ISA_PRESSURE = 101325; // Pa
	public static final double ISA_MOLECULAR_MASS_ = 28.96e-3; // Kg/mol
	public static final double ISA_DENSITY = ISA_MOLECULAR_MASS_ * ISA_PRESSURE / R / ISA_TEMPERATURE; // Kg
	public static final double SPEED = 10; // m/s
	public static final double ISA_SPECIFIC_HEAT_CAPACITY = 1004.3; // J/(Kg K)
}
