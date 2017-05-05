package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.applink.ApplicationLink
import com.atlassian.bamboo.specs.api.builders.repository.VcsChangeDetection
import com.atlassian.bamboo.specs.builders.repository.bitbucket.server.BitbucketServerRepository
import groovy.transform.CompileStatic

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.buildAndCall
import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.runWithDelegate

/**
 * @author Brian Stewart
 */
@CompileStatic
class BitbucketServerRepositoryDsl {
    @Delegate BitbucketServerRepository repository = new BitbucketServerRepository()

    static BitbucketServerRepository bitbucketServerRepository(@DelegatesTo(BitbucketServerRepositoryDsl) Closure builder) {
        def dsl = new BitbucketServerRepositoryDsl()
        runWithDelegate(builder, dsl)

        dsl.repository
    }

    void server(@DelegatesTo(BitbucketServerRepositoryDsl) Closure builder) {
        runWithDelegate(builder, this)
    }

    ApplicationLink applicationLink(@DelegatesTo(ApplicationLink) Closure builder) {
        buildAndCall(new ApplicationLink(), builder, repository.&server)
    }

    void changeDetection(@DelegatesTo(BitbucketServerRepositoryDsl) Closure builder) {
        runWithDelegate(builder, this)
    }

    VcsChangeDetection vcsChangeDetection(@DelegatesTo(VcsChangeDetection) Closure builder) {
        buildAndCall(new VcsChangeDetection(), builder, repository.&changeDetection)
    }
}
