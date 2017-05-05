package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Job
import com.atlassian.bamboo.specs.api.builders.plan.artifact.Artifact
import com.atlassian.bamboo.specs.api.builders.plan.artifact.ArtifactSubscription
import com.atlassian.bamboo.specs.api.builders.requirement.Requirement
import com.atlassian.bamboo.specs.api.builders.task.Task
import groovy.transform.CompileStatic

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
@CompileStatic
class JobDsl {
    @Delegate Job job

    JobDsl(String name, String key) {
        job = new Job(name, key)
    }

    static Job job(String name, String key, @DelegatesTo(JobDsl) Closure builder) {
        runWithDelegate(builder, new JobDsl(name, key)).job
    }

    void requirements(Closure builder) {
        runWithDelegate(builder, this)
    }

    Requirement requirement(String key) {
        call(RequirementDsl.requirement(key), job.&requirements)
    }

    Requirement requirement(String key, @DelegatesTo(Requirement) Closure builder) {
        call(RequirementDsl.requirement(key, builder), job.&requirements)
    }

    List<Task> tasks(@DelegatesTo(TaskDsl) Closure builder) {
        def dsl = new TaskDsl()

        runWithDelegate(builder, dsl)
        job.tasks(dsl.tasks as Task[])

        dsl.tasks
    }

    List<Task> finalTasks(@DelegatesTo(TaskDsl) Closure builder) {
        def dsl = new TaskDsl()

        runWithDelegate(builder, dsl)
        job.finalTasks(dsl.tasks as Task[])

        dsl.tasks
    }

    void artifacts(Closure builder) {
        runWithDelegate(builder, this)
    }

    Artifact artifact(String name) {
        call(ArtifactDsl.artifact(name), job.&artifacts)
    }

    Artifact artifact(String name, @DelegatesTo(Artifact) Closure builder) {
        call(ArtifactDsl.artifact(name, builder), job.&artifacts)
    }

    Artifact artifact(@DelegatesTo(Artifact) Closure builder) {
        call(ArtifactDsl.artifact(builder), job.&artifacts)
    }

    void artifactSuscriptions(Closure builder) {
        runWithDelegate(builder, this)
    }

    ArtifactSubscription artifactSubscription(@DelegatesTo(ArtifactSubscription) Closure builder) {
        buildAndCall(new ArtifactSubscription(), builder, job.&artifactSubscriptions)
    }
}
