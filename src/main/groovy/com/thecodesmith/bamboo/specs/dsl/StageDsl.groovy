package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Job
import com.atlassian.bamboo.specs.api.builders.plan.Stage
import com.thecodesmith.bamboo.specs.dsl.utils.DslUtils

/**
 * @author Brian Stewart
 */
class StageDsl {
    @Delegate Stage stage

    private List<Job> jobs = []

    StageDsl(String name) {
        stage = new Stage(name)
    }

    List<Job> jobs(Closure closure) {
        DslUtils.runWithDelegate(closure, this)
        stage.jobs(jobs as Job[])

        jobs
    }

    Job job(String name, String key, @DelegatesTo(JobDsl) Closure closure) {
        def dsl = new JobDsl(name, key)

        DslUtils.runWithDelegate(closure, dsl)
        jobs << dsl.job

        dsl.job
    }
}
