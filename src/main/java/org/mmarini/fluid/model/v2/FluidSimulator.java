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

import java.net.URL;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class FluidSimulator {
//	private static final Logger logger = LoggerFactory.getLogger(FluidSimulator.class);

	private static final double NANOS = 1e-9;
	private final PublishSubject<Double> rateSubj;
	private final PublishSubject<Universe> universeSubj;
	private Universe universe;
	private boolean started;
	private long prevTime;
	private long time;
	private long counter;

	public FluidSimulator() {
		super();
		rateSubj = PublishSubject.create();
		universeSubj = PublishSubject.create();
		universeSubj.subscribe(universe -> {
			this.universe = universe;
			if (started) {
				scheduleNext();
			}
		});
	}

	public FluidSimulator createNew() {
		universe = UniverseBuilder.create().build();
		counter = 0;
		time = 0;
		return single();
	}

	public Flowable<Double> getRateFlow() {
		return rateSubj.toFlowable(BackpressureStrategy.LATEST);
	}

	public Flowable<Universe> getUniverseFlow() {
		return universeSubj.toFlowable(BackpressureStrategy.LATEST);
	}

	public FluidSimulator loadFromUrl(final URL resource) {
		return createNew();
	}

	/**
	 * Process the universe to produce a new state
	 *
	 * @param universe
	 * @return
	 */
	private Universe next(final Universe universe) {
		final long now = System.nanoTime();
		final long elapsed = now - prevTime;
		prevTime = now;
		time += elapsed;
		final double dt = elapsed * NANOS;
		final double t = time * NANOS;
		counter++;
		rateSubj.onNext(counter / t);
		return universe.step(dt);
	}

	private void scheduleNext() {
		final Universe u = universe;
		Single.fromSupplier(() -> next(u)).subscribeOn(Schedulers.computation())
				.subscribe(universe -> universeSubj.onNext(universe));
	}

	/**
	 * Performs a single step simulation.
	 */
	public FluidSimulator single() {
		if (!started) {
			prevTime = System.nanoTime();
			scheduleNext();
		}
		return this;
	}

	/**
	 * Starts the simulation.
	 */
	public FluidSimulator start() {
		if (!started) {
			started = true;
			prevTime = System.nanoTime();
			scheduleNext();
		}
		return this;
	}

	/**
	 * Starts the simulation.
	 */
	public FluidSimulator stop() {
		started = false;
		return this;
	}
}
