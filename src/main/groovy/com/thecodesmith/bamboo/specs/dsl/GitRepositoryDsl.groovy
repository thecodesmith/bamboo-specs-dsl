package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.repository.VcsChangeDetection
import com.atlassian.bamboo.specs.builders.repository.git.GitRepository
import groovy.transform.CompileStatic

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.buildAndCall
import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.runWithDelegate

/**
 * @author Brian Stewart
 */
@CompileStatic
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

    def authentication(@DelegatesTo(GitRepositoryAuthenticationDsl) Closure builder) {
        def dsl = new GitRepositoryAuthenticationDsl(repository)
        runWithDelegate(builder, dsl)
    }

    void changeDetection(@DelegatesTo(GitRepositoryDsl) Closure builder) {
        runWithDelegate(builder, this)
    }

    VcsChangeDetection vcsChangeDetection(@DelegatesTo(VcsChangeDetection) Closure builder) {
        buildAndCall(new VcsChangeDetection(), builder, repository.&changeDetection)
    }
}
