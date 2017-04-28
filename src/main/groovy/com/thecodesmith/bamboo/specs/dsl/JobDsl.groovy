package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Job
import com.atlassian.bamboo.specs.api.builders.plan.artifact.Artifact
import com.atlassian.bamboo.specs.api.builders.plan.artifact.ArtifactSubscription
import com.atlassian.bamboo.specs.api.builders.requirement.Requirement
import com.atlassian.bamboo.specs.api.builders.task.Task
import com.thecodesmith.bamboo.specs.dsl.utils.DslUtils

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

    List<Task> tasks(@DelegatesTo(TaskDsl) Closure closure) {
        def dsl = new TaskDsl()

        DslUtils.runWithDelegate(closure, dsl)
        job.tasks(dsl.tasks.toArray() as Task[])

        dsl.tasks
    }

    List<Task> finalTasks(@DelegatesTo(TaskDsl) Closure closure) {
        def dsl = new TaskDsl()

        DslUtils.runWithDelegate(closure, dsl)
        job.finalTasks(dsl.tasks as Task[])

        dsl.tasks
    }

    List<Artifact> artifacts(Closure closure) {
        DslUtils.runWithDelegate(closure, this)
        job.artifacts(artifacts as Artifact[])

        artifacts
    }

    Artifact artifact(String name, @DelegatesTo(ArtifactDsl) Closure closure = {}) {
        def artifact = new Artifact(name)

        DslUtils.runWithDelegate(closure, artifact)
        artifacts << artifact

        artifact
    }

    Artifact artifact(@DelegatesTo(ArtifactDsl) Closure closure) {
        def dsl = new ArtifactDsl()

        DslUtils.runWithDelegate(closure, dsl)
        artifacts << dsl.artifact

        dsl.artifact
    }

    List<ArtifactSubscription> artifactSuscriptions(Closure closure) {
        DslUtils.runWithDelegate(closure, this)
        job.artifactSubscriptions(artifactSubscriptions as ArtifactSubscription[])

        artifactSubscriptions
    }

    ArtifactSubscription artifactSubscription(@DelegatesTo(ArtifactSubscription) Closure closure) {
        def subscription = new ArtifactSubscription()

        DslUtils.runWithDelegate(closure, subscription)
        artifactSubscriptions << subscription

        subscription
    }

    List<Requirement> requirements(Closure closure) {
        DslUtils.runWithDelegate(closure, this)
        job.requirements(requirements as Requirement[])

        requirements
    }

    Requirement requirement(String key, @DelegatesTo(Requirement) Closure closure = {}) {
        def requirement = new Requirement(key)

        DslUtils.runWithDelegate(closure, requirement)
        requirements << requirement

        requirement
    }
}
