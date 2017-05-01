package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.artifact.Artifact
import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.*

/**
 * @author Brian Stewart
 */
class ArtifactDsl {

    static Artifact artifact(String name) {
        new Artifact(name)
    }

    static Artifact artifact(@DelegatesTo(Artifact) Closure closure) {
        runWithDelegate(closure, new Artifact())
    }

    static Artifact artifact(String name, @DelegatesTo(Artifact) Closure closure) {
        runWithDelegate(closure, new Artifact(name))
    }
}
