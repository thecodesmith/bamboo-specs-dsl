package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.AtlassianModule
import com.atlassian.bamboo.specs.api.builders.task.AnyTask
import groovy.transform.CompileStatic

/**
 * @author Brian Stewart
 */
@CompileStatic
class AnyTaskDsl {
    @Delegate AnyTask task

    void atlassianModule(String completeModuleKey) {
        task = new AnyTask(new AtlassianModule(completeModuleKey))
    }
}
