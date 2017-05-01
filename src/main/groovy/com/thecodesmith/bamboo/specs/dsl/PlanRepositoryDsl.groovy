package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.repository.VcsRepository
import com.atlassian.bamboo.specs.builders.repository.git.GitRepository

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
class PlanRepositoryDsl {
    List<VcsRepository> repositories = []

    GitRepository gitRepository(@DelegatesTo(GitRepository) Closure closure) {
        (GitRepository) addToList(repositories, runWithDelegate(closure, new GitRepository()))
    }
}
