package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchIntegration
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.buildAndCall

/**
 * @author Brian Stewart
 */
class PlanBranchManagementDsl {
    @Delegate PlanBranchManagement planBranchManagement

    PlanBranchManagementDsl() {
        planBranchManagement = new PlanBranchManagement()
    }

    BranchIntegration branchIntegration(@DelegatesTo(BranchIntegration) Closure builder) {
        buildAndCall(new BranchIntegration(), builder, planBranchManagement.&branchIntegration)
    }

    BranchCleanup delete(@DelegatesTo(BranchCleanup) Closure builder) {
        buildAndCall(new BranchCleanup(), builder, planBranchManagement.&delete)
    }
}
