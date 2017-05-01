package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.builders.repository.git.GitRepository

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.runWithDelegate

/**
 * @author Brian Stewart
 */
class GitRepositoryDsl {
    @Delegate GitRepository repository

    GitRepositoryDsl() {
        repository = new GitRepository()
    }

    static GitRepository gitRepository(@DelegatesTo(GitRepositoryDsl) Closure builder) {
        def dsl = new GitRepositoryDsl()
        runWithDelegate(builder, dsl)

        dsl.repository
    }

    void authentication(@DelegatesTo(GitRepositoryAuthenticationDsl) Closure builder) {
        def dsl = new GitRepositoryAuthenticationDsl(repository)
        runWithDelegate(builder, dsl)
    }
}
