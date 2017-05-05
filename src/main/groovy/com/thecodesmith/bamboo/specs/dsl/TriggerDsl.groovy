package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.trigger.Trigger
import com.atlassian.bamboo.specs.builders.trigger.RemoteTrigger
import com.atlassian.bamboo.specs.builders.trigger.RepositoryPollingTrigger
import com.atlassian.bamboo.specs.builders.trigger.ScheduledTrigger
import groovy.transform.CompileStatic

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.buildAndCall

/**
 * @author Brian Stewart
 */
@CompileStatic
class TriggerDsl {
    List<Trigger> triggers = []

    RepositoryPollingTrigger repositoryPollingTrigger(@DelegatesTo(RepositoryPollingTrigger) Closure builder) {
        buildAndCall(new RepositoryPollingTrigger(), builder, triggers.&add)
    }

    RemoteTrigger remoteTrigger(@DelegatesTo(RemoteTrigger) Closure builder) {
        buildAndCall(new RemoteTrigger(), builder, triggers.&add)
    }

    ScheduledTrigger scheduledTrigger(@DelegatesTo(ScheduledTrigger) Closure builder) {
        buildAndCall(new ScheduledTrigger(), builder, triggers.&add)
    }
}
