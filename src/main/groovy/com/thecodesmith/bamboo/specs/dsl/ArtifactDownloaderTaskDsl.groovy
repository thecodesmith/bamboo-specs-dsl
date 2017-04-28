package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier
import com.atlassian.bamboo.specs.builders.task.ArtifactDownloaderTask
import com.atlassian.bamboo.specs.builders.task.DownloadItem
import com.thecodesmith.bamboo.specs.dsl.utils.DslUtils

/**
 * @author Brian Stewart
 */
class ArtifactDownloaderTaskDsl {
    @Delegate ArtifactDownloaderTask task

    private List<DownloadItem> artifacts = []

    ArtifactDownloaderTaskDsl() {
        task = new ArtifactDownloaderTask()
    }

    PlanIdentifier sourcePlan(String projectKey, String planKey) {
        def planIdentifier = new PlanIdentifier(projectKey, planKey)

        task.sourcePlan(planIdentifier)

        planIdentifier
    }

    List<DownloadItem> artifacts(Closure closure) {
        DslUtils.runWithDelegate(closure, this)
        task.artifacts(artifacts as DownloadItem[])

        artifacts
    }

    DownloadItem downloadItem(@DelegatesTo(DownloadItem) Closure closure) {
        def item = new DownloadItem()

        DslUtils.runWithDelegate(closure, item)
        artifacts << item

        item
    }
}
