package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.Variable
import com.atlassian.bamboo.specs.api.builders.plan.Plan
import com.atlassian.bamboo.specs.api.builders.plan.Stage
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement
import com.atlassian.bamboo.specs.api.builders.plan.configuration.PluginConfiguration
import com.atlassian.bamboo.specs.api.builders.project.Project
import com.atlassian.bamboo.specs.api.builders.trigger.Trigger
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
@CompileStatic
class PlanDsl {
    @Delegate Plan plan

    PlanDsl(Project project, String name, String key) {
        plan = new Plan(project, name, key)
    }

    static Plan plan(Project project, String name, String key, @DelegatesTo(PlanDsl) Closure builder) {
        runWithDelegate(builder, new PlanDsl(project, name, key)).plan
    }

    PlanBranchManagement planBranchManagement(@DelegatesTo(PlanBranchManagementDsl) Closure builder) {
        def dsl = new PlanBranchManagementDsl()
        runWithDelegate(builder, dsl)
        plan.planBranchManagement(dsl.planBranchManagement)

        dsl.planBranchManagement
    }

    void stages(Closure builder) {
        runWithDelegate(builder, this)
    }

    Stage stage(String name, @DelegatesTo(StageDsl) Closure builder) {
        def dsl = new StageDsl(name)
        runWithDelegate(builder, dsl)
        plan.stages(dsl.stage)

        dsl.stage
    }

    void linkedRepositories(String... repositories) {
        plan.linkedRepositories(repositories)
    }

    void planRepositories(@DelegatesTo(PlanRepositoryDsl) Closure builder) {
        runWithDelegate(builder, new PlanRepositoryDsl(plan: plan))
    }

    void variables(Closure builder) {
        runWithDelegate(builder, this)
    }

    Variable variable(String name, String value) {
        call(new Variable(name, value), plan.&variables)
    }

    Variable variable(Variable variable) {
        call(variable, plan.&variables)
    }

    void triggers(@DelegatesTo(TriggerDsl) Closure builder) {
        def dsl = new TriggerDsl()
        runWithDelegate(builder, dsl)
        plan.triggers(dsl.triggers as Trigger[])
    }

    void dependencies(@DelegatesTo(DependenciesDsl) Closure builder) {
        def dsl = new DependenciesDsl()
        runWithDelegate(builder, dsl)
        plan.dependencies(dsl.dependencies)
    }

    void pluginConfigurations(@DelegatesTo(PluginConfigurationDsl) Closure builder) {
        def dsl = new PluginConfigurationDsl()
        runWithDelegate(builder, dsl)
        plan.pluginConfigurations(dsl.configurations as PluginConfiguration[])
    }
}
