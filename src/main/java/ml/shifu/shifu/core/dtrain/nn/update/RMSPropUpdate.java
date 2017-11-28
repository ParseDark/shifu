/*
 * Encog(tm) Core v3.4 - Java Version
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-core
 
 * Copyright 2008-2016 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package ml.shifu.shifu.core.dtrain.nn.update;

import ml.shifu.shifu.core.dtrain.Weight;

/**
 * Created by jeffh on 7/15/2016.
 * 
 * <p>
 * Copied from https://github.com/encog/encog-java-core/.
 */
public class RMSPropUpdate implements UpdateRule {
    private double[] cache;
    private double eps = 1e-8;
    private double decayRate = 0.99;

    private double learningRate;

    @Override
    public void init(Weight weight) {
        this.learningRate = weight.getLearningRate();
        this.cache = new double[weight.getNumWeight()];
        this.decayRate = weight.getLearningDecay();
    }

    @Override
    public void update(double[] gradients, double[] weights, int iteration) {
        for(int i = 0; i < weights.length; i++) {
            this.cache[i] += gradients[i] * gradients[i];
            this.cache[i] = this.decayRate * cache[i] + (1 - this.decayRate) * gradients[i] * gradients[i];
            final double delta = (this.learningRate * gradients[i]) / (Math.sqrt(cache[i]) + this.eps);
            weights[i] += delta;
        }
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public double getDecayRate() {
        return decayRate;
    }

    public void setDecayRate(double decayRate) {
        this.decayRate = decayRate;
    }
}
