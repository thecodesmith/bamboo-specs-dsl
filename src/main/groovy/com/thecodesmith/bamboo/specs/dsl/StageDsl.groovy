package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Job
import com.atlassian.bamboo.specs.api.builders.plan.Stage
import groovy.transform.CompileStatic

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
@CompileStatic
class StageDsl {
    @Delegate Stage stage

    StageDsl(String name) {
        stage = new Stage(name)
    }

    static Stage stage(String name, @DelegatesTo(StageDsl) Closure builder) {
        runWithDelegate(builder, new StageDsl(name)).stage
    }

    void jobs(Closure builder) {
        runWithDelegate(builder, this)
    }

    Job job(String name, String key, @DelegatesTo(JobDsl) Closure builder) {
        call(JobDsl.job(name, key, builder), stage.&jobs)
    }
}
