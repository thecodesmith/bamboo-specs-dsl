package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Plan
import com.atlassian.bamboo.specs.api.builders.project.Project

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

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

    static Project project(String name, String key, @DelegatesTo(ProjectDsl) Closure builder) {
        runWithDelegate(builder, new ProjectDsl(name, key)).project
    }

    Plan plan(String name, String key, @DelegatesTo(PlanDsl) Closure builder) {
        PlanDsl.plan(project, name, key, builder)
    }
}
