package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Job
import com.atlassian.bamboo.specs.api.builders.plan.Stage

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
class StageDsl {
    @Delegate Stage stage

    private List<Job> jobs = []

    StageDsl(String name) {
        stage = new Stage(name)
    }

    static Stage stage(String name, @DelegatesTo(StageDsl) Closure closure) {
        def dsl = new StageDsl(name)
        runWithDelegate(closure, dsl)

        dsl.stage
    }

    List<Job> jobs(Closure closure) {
        runWithDelegate(closure, this)
        stage.jobs(jobs as Job[])

        jobs
    }

    Job job(String name, String key, @DelegatesTo(JobDsl) Closure closure) {
        addToList(jobs, JobDsl.job(name, key, closure))
    }
}
