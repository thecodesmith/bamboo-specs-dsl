package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.task.Task
import com.atlassian.bamboo.specs.builders.task.ArtifactDownloaderTask
import com.atlassian.bamboo.specs.builders.task.ScriptTask
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask
import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
class TaskDsl {
    List<Task> tasks = []

    VcsCheckoutTask vcsCheckoutTask(@DelegatesTo(VcsCheckoutTask) Closure closure) {
        (VcsCheckoutTask) addToList(tasks, runWithDelegate(closure, new VcsCheckoutTask()))
    }

    ScriptTask scriptTask(@DelegatesTo(ScriptTask) Closure closure) {
        (ScriptTask) addToList(tasks, runWithDelegate(closure, new ScriptTask()))
    }

    ArtifactDownloaderTask artifactDownloaderTask(@DelegatesTo(ArtifactDownloaderTaskDsl) Closure closure) {
        (ArtifactDownloaderTask) addToList(tasks, runWithDelegate(closure, new ArtifactDownloaderTaskDsl()).task)
    }
}
