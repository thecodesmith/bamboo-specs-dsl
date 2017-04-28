package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.artifact.Artifact
import com.thecodesmith.bamboo.specs.dsl.utils.DslUtils

/**
 * @author Brian Stewart
 */
class ArtifactDsl {
    @Delegate Artifact artifact

    ArtifactDsl() {
        artifact = new Artifact()
    }

    Artifact artifact(@DelegatesTo(Artifact) Closure closure) {
        DslUtils.runWithDelegate(closure, artifact)

        artifact
    }
}
