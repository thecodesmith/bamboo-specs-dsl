package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.dependencies.Dependencies
import com.atlassian.bamboo.specs.api.builders.plan.dependencies.DependenciesConfiguration

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.buildAndCall
import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.runWithDelegate

/**
 * @author Brian Stewart
 */
class DependenciesDsl {
    @Delegate Dependencies dependencies

    DependenciesDsl() {
        dependencies = new Dependencies()
    }

    void configuration(Closure builder) {
        runWithDelegate(builder, this)
    }

    DependenciesConfiguration dependenciesConfiguration(@DelegatesTo(DependenciesConfiguration) Closure builder) {
        buildAndCall(new DependenciesConfiguration(), builder, dependencies.&configuration)
    }
}