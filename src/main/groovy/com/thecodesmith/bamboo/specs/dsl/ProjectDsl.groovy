package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Plan
import com.atlassian.bamboo.specs.api.builders.project.Project
import com.thecodesmith.bamboo.specs.dsl.utils.DslUtils

/**
 * @author Brian Stewart
 */
class ProjectDsl {
    private String name
    private String key
    @Delegate Project project

    ProjectDsl(String name, String key) {
        project = new Project()
                .name(name)
                .key(key)
    }

    Plan plan(String name, String key, @DelegatesTo(PlanDsl) Closure closure) {
        def dsl = new PlanDsl(project, name, key)

        DslUtils.runWithDelegate(closure, dsl)

        dsl.plan
    }
}
