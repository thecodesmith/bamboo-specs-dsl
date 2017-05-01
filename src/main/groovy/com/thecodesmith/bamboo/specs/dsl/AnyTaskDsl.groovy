package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.AtlassianModule
import com.atlassian.bamboo.specs.api.builders.task.AnyTask

/**
 * @author Brian Stewart
 */
class AnyTaskDsl {
    @Delegate AnyTask task

    void atlassianModule(String completeModuleKey) {
        task = new AnyTask(new AtlassianModule(completeModuleKey))
    }
}
