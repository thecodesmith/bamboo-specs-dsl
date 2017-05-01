package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.Variable
import com.atlassian.bamboo.specs.api.builders.plan.Plan
import com.atlassian.bamboo.specs.api.builders.plan.Stage
import com.atlassian.bamboo.specs.api.builders.project.Project
import com.atlassian.bamboo.specs.api.builders.repository.VcsRepository

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
class PlanDsl {
    @Delegate Plan plan

    private List<Stage> stages = []
    private List<Variable> variables = []

    PlanDsl(Project project, String name, String key) {
        plan = new Plan(project, name, key)
    }

    static Plan plan(Project project, String name, String key, @DelegatesTo(PlanDsl) Closure closure) {
        def dsl = new PlanDsl(project, name, key)

        runWithDelegate(closure, dsl)

        dsl.plan
    }

    List<Stage> stages(Closure closure) {
        runWithDelegate(closure, this)
        plan.stages(stages as Stage[])

        stages
    }

    Stage stage(String name, @DelegatesTo(StageDsl) Closure closure) {
        addToList(stages, StageDsl.stage(name, closure))
    }

    void linkedRepositories(String... repositories) {
        plan.linkedRepositories(repositories)
    }

    List<VcsRepository> planRepositories(@DelegatesTo(PlanRepositoryDsl) Closure closure) {
        def dsl = new PlanRepositoryDsl()

        runWithDelegate(closure, dsl)
        plan.planRepositories(dsl.repositories as VcsRepository[])

        dsl.repositories
    }

    void variables(Closure closure) {
        runWithDelegate(closure, this)
        plan.variables(variables as Variable[])

        variables
    }

    Variable variable(String name, String value) {
        addToList(variables, new Variable(name, value))
    }

    Variable variable(Variable variable) {
        addToList(variables, variable)
    }
}
