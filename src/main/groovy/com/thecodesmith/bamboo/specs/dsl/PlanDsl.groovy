package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.Variable
import com.atlassian.bamboo.specs.api.builders.plan.Plan
import com.atlassian.bamboo.specs.api.builders.plan.Stage
import com.atlassian.bamboo.specs.api.builders.project.Project
import com.atlassian.bamboo.specs.api.builders.repository.VcsRepository
import com.thecodesmith.bamboo.specs.dsl.utils.DslUtils

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

    List<Stage> stages(Closure closure) {
        DslUtils.runWithDelegate(closure, this)
        plan.stages(stages as Stage[])

        stages
    }

    Stage stage(String name, @DelegatesTo(StageDsl) Closure closure) {
        def dsl = new StageDsl(name)

        DslUtils.runWithDelegate(closure, dsl)
        stages << dsl.stage

        dsl.stage
    }

    void linkedRepositories(String... repositories) {
        plan.linkedRepositories(repositories)
    }

    List<VcsRepository> planRepositories(@DelegatesTo(PlanRepositoryDsl) Closure closure) {
        def dsl = new PlanRepositoryDsl()

        DslUtils.runWithDelegate(closure, dsl)
        plan.planRepositories(dsl.repositories as VcsRepository[])

        dsl.repositories
    }

    void variables(Closure closure) {
        DslUtils.runWithDelegate(closure, this)
        plan.variables(variables as Variable[])

        variables
    }

    Variable variable(String name, String variable) {
        def it = new Variable(name, variable)

        variables << it

        it
    }
}
