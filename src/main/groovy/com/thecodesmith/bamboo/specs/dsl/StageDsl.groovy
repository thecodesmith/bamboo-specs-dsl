package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Job
import com.atlassian.bamboo.specs.api.builders.plan.Stage

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
class StageDsl {
    @Delegate Stage stage

    StageDsl(String name) {
        stage = new Stage(name)
    }

    static Stage stage(String name, @DelegatesTo(StageDsl) Closure builder) {
        call(new StageDsl(name), builder).stage
    }

    void jobs(Closure builder) {
        runWithDelegate(builder, this)
    }

    Job job(String name, String key, @DelegatesTo(JobDsl) Closure builder) {
        call(JobDsl.job(name, key, builder), stage.&jobs)
    }
}
