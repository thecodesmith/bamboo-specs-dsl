package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.AtlassianModule
import com.atlassian.bamboo.specs.api.builders.task.AnyTask
import com.atlassian.bamboo.specs.api.builders.task.Task
import com.atlassian.bamboo.specs.builders.repository.git.SshPrivateKeyAuthentication
import com.atlassian.bamboo.specs.builders.task.ArtifactDownloaderTask
import com.atlassian.bamboo.specs.builders.task.BaseSshTask
import com.atlassian.bamboo.specs.builders.task.CleanWorkingDirectoryTask
import com.atlassian.bamboo.specs.builders.task.MavenTask
import com.atlassian.bamboo.specs.builders.task.ScpTask
import com.atlassian.bamboo.specs.builders.task.ScriptTask
import com.atlassian.bamboo.specs.builders.task.SshTask
import com.atlassian.bamboo.specs.builders.task.TestParserTask
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask
import com.atlassian.bamboo.specs.model.task.TestParserTaskProperties.TestType
import groovy.transform.CompileStatic

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
@CompileStatic
class TaskDsl {
    List<Task> tasks = []

    AnyTask anyTask(@DelegatesTo(AnyTaskDsl) Closure builder) {
        def dsl = new AnyTaskDsl()
        runWithDelegate(builder, dsl)
        tasks << dsl.task

        dsl.task
    }

    ArtifactDownloaderTask artifactDownloaderTask(@DelegatesTo(ArtifactDownloaderTaskDsl) Closure builder) {
        def task = runWithDelegate(builder, new ArtifactDownloaderTaskDsl()).task
        tasks << task

        task
    }

    CleanWorkingDirectoryTask cleanWorkingDirectoryTask(@DelegatesTo(CleanWorkingDirectoryTask) Closure builder) {
        buildAndCall(new CleanWorkingDirectoryTask(), builder, tasks.&add)
    }

    MavenTask mavenTask(@DelegatesTo(MavenTask) Closure builder) {
        buildAndCall(new MavenTask(), builder, tasks.&add)
    }

    ScpTask scpTask(@DelegatesTo(ScpTask) Closure builder) {
        buildAndCall(new ScpTask(), builder, tasks.&add)
    }

    ScriptTask scriptTask(@DelegatesTo(ScriptTask) Closure builder) {
        buildAndCall(new ScriptTask(), builder, tasks.&add)
    }

    SshTask sshTask(@DelegatesTo(SshTask) Closure builder) {
        buildAndCall(new SshTask(), builder, tasks.&add)
    }

    TestParserTask testParserTask(TestType type, @DelegatesTo(TestParserTask) Closure builder) {
        buildAndCall(new TestParserTask(type), builder, tasks.&add)
    }

    VcsCheckoutTask vcsCheckoutTask(@DelegatesTo(VcsCheckoutTaskDsl) Closure builder) {
        def dsl = new VcsCheckoutTaskDsl()
        runWithDelegate(builder, dsl)
        tasks << dsl.task

        dsl.task
    }
}
