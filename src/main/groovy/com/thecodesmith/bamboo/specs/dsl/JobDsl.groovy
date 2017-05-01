package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Job
import com.atlassian.bamboo.specs.api.builders.plan.artifact.Artifact
import com.atlassian.bamboo.specs.api.builders.plan.artifact.ArtifactSubscription
import com.atlassian.bamboo.specs.api.builders.requirement.Requirement
import com.atlassian.bamboo.specs.api.builders.task.Task

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
class JobDsl {
    @Delegate Job job

    private List<Requirement> requirements = []
    private List<Artifact> artifacts = []
    private List<ArtifactSubscription> artifactSubscriptions = []

    JobDsl(String name, String key) {
        job = new Job(name, key)
    }

    static Job job(String name, String key, @DelegatesTo(JobDsl) Closure closure) {
        def dsl = new JobDsl(name, key)

        runWithDelegate(closure, dsl)

        dsl.job
    }

    List<Task> tasks(@DelegatesTo(TaskDsl) Closure closure) {
        def dsl = new TaskDsl()

        runWithDelegate(closure, dsl)
        job.tasks(dsl.tasks as Task[])

        dsl.tasks
    }

    List<Task> finalTasks(@DelegatesTo(TaskDsl) Closure closure) {
        def dsl = new TaskDsl()

        runWithDelegate(closure, dsl)
        job.finalTasks(dsl.tasks as Task[])

        dsl.tasks
    }

    List<Artifact> artifacts(Closure closure) {
        runWithDelegate(closure, this)
        job.artifacts(artifacts as Artifact[])

        artifacts
    }

    Artifact artifact(String name) {
        addToList(artifacts, ArtifactDsl.artifact(name))
    }

    Artifact artifact(String name, @DelegatesTo(Artifact) Closure closure) {
        addToList(artifacts, ArtifactDsl.artifact(name, closure))
    }

    Artifact artifact(@DelegatesTo(Artifact) Closure closure) {
        addToList(artifacts, ArtifactDsl.artifact(closure))
    }

    List<ArtifactSubscription> artifactSuscriptions(Closure closure) {
        runWithDelegate(closure, this)
        job.artifactSubscriptions(artifactSubscriptions as ArtifactSubscription[])

        artifactSubscriptions
    }

    ArtifactSubscription artifactSubscription(@DelegatesTo(ArtifactSubscription) Closure closure) {
        addToList(artifactSubscriptions, runWithDelegate(closure, new ArtifactSubscription()))
    }

    List<Requirement> requirements(Closure closure) {
        runWithDelegate(closure, this)
        job.requirements(requirements as Requirement[])

        requirements
    }

    Requirement requirement(String key) {
        RequirementDsl.requirement(key)
    }

    Requirement requirement(String key, @DelegatesTo(Requirement) Closure closure) {
        addToList(requirements, RequirementDsl.requirement(key, closure))
    }
}
