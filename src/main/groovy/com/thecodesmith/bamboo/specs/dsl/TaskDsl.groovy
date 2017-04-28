package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.task.Task
import com.atlassian.bamboo.specs.builders.task.ArtifactDownloaderTask
import com.atlassian.bamboo.specs.builders.task.ScriptTask
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask
import com.thecodesmith.bamboo.specs.dsl.utils.DslUtils

/**
 * @author Brian Stewart
 */
class TaskDsl {
    List<Task> tasks = []

    VcsCheckoutTask vcsCheckoutTask(@DelegatesTo(VcsCheckoutTask) Closure closure) {
        def task = new VcsCheckoutTask()

        DslUtils.runWithDelegate(closure, task)
        tasks << task

        task
    }

    ScriptTask scriptTask(@DelegatesTo(ScriptTask) Closure closure) {
        def task = new ScriptTask()

        DslUtils.runWithDelegate(closure, task)
        tasks << task

        task
    }

    ArtifactDownloaderTask artifactDownloaderTask(@DelegatesTo(ArtifactDownloaderTaskDsl) Closure closure) {
        def dsl = new ArtifactDownloaderTaskDsl()

        DslUtils.runWithDelegate(closure, dsl)
        tasks << dsl.task

        dsl.task
    }
}
