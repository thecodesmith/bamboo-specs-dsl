package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.requirement.Requirement
import groovy.transform.CompileStatic

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.runWithDelegate

/**
 * @author Brian Stewart
 */
@CompileStatic
class RequirementDsl {

    static Requirement requirement(String key) {
        new Requirement(key)
    }

    static Requirement requirement(String key, @DelegatesTo(Requirement) Closure builder) {
        runWithDelegate(builder, new Requirement(key))
    }
}
