package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier
import com.atlassian.bamboo.specs.builders.task.ArtifactDownloaderTask
import com.atlassian.bamboo.specs.builders.task.DownloadItem
import groovy.transform.CompileStatic

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
@CompileStatic
class ArtifactDownloaderTaskDsl {
    @Delegate ArtifactDownloaderTask task

    ArtifactDownloaderTaskDsl() {
        task = new ArtifactDownloaderTask()
    }

    PlanIdentifier sourcePlan(String projectKey, String planKey) {
        call(new PlanIdentifier(projectKey, planKey), task.&sourcePlan)
    }

    void artifacts(Closure builder) {
        runWithDelegate(builder, this)
    }

    DownloadItem downloadItem(@DelegatesTo(DownloadItem) Closure builder) {
        buildAndCall(new DownloadItem(), builder, task.&artifacts)
    }
}
