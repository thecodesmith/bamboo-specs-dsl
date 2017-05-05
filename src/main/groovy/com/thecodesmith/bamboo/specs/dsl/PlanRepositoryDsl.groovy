package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Plan
import com.atlassian.bamboo.specs.builders.repository.bitbucket.server.BitbucketServerRepository
import com.atlassian.bamboo.specs.builders.repository.git.GitRepository
import groovy.transform.CompileStatic

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
@CompileStatic
class PlanRepositoryDsl {
    Plan plan

    GitRepository gitRepository(@DelegatesTo(GitRepositoryDsl) Closure builder) {
        call(GitRepositoryDsl.gitRepository(builder), plan.&planRepositories)
    }

    BitbucketServerRepository bitbucketServerRepository(@DelegatesTo(BitbucketServerRepositoryDsl) Closure builder) {
        call(BitbucketServerRepositoryDsl.bitbucketServerRepository(builder), plan.&planRepositories)
    }
}
