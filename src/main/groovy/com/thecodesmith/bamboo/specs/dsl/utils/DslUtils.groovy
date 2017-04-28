package com.thecodesmith.bamboo.specs.dsl.utils

/**
 * @author Brian Stewart
 */
class DslUtils {

    static void runWithDelegate(Closure closure, delegate) {
        closure.delegate = delegate
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }
}
