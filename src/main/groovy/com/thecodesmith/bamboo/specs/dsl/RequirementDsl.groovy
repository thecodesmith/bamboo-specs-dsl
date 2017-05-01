package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.requirement.Requirement
import com.thecodesmith.bamboo.specs.dsl.utils.DslUtils

/**
 * @author Brian Stewart
 */
class RequirementDsl {

    static Requirement requirement(String key) {
        new Requirement(key)
    }

    static Requirement requirement(String key, @DelegatesTo(Requirement) Closure closure) {
        DslUtils.runWithDelegate(closure, new Requirement(key))
    }
}
