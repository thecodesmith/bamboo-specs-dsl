package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Plan
import com.atlassian.bamboo.specs.api.builders.project.Project
import com.thecodesmith.bamboo.specs.dsl.utils.DslUtils

/**
 * @author Brian Stewart
 */
class ProjectDsl {
    @Delegate Project project

    ProjectDsl(String name, String key) {
        project = new Project()
                .name(name)
                .key(key)
    }

    static Project project(String name, String key, @DelegatesTo(ProjectDsl) Closure closure) {
        def dsl = new ProjectDsl(name, key)

        DslUtils.runWithDelegate(closure, dsl)

        dsl.project
    }

    Plan plan(String name, String key, @DelegatesTo(PlanDsl) Closure closure) {
        PlanDsl.plan(project, name, key, closure)
    }
}
