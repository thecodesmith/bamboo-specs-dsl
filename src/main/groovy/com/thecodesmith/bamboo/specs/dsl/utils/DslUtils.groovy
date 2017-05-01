package com.thecodesmith.bamboo.specs.dsl.utils

/**
 * Helpful functions to reduce boilerplate for repeated patterns.
 *
 * @author Brian Stewart
 */
class DslUtils {

    /**
     * Calls a function with a parameter, then returns the parameter.
     * This simplifies the repeated usage of this pattern:
     *
     * {@code
     * def thing = new Thing()
     * parent.things(thing)
     * return thing
     * }
     *
     * to:
     *
     * {@code call(new Thing(), parent.&things) }
     *
     * @param thing
     * @param callback
     * @return
     */
    static <T> T call(T thing, Closure callback) {
        callback.call(thing)

        thing
    }

    /**
     * This function does three things:
     * - Runs builder closure with "thing" as the delegate
     * - Calls the function using "thing" as a parameter
     * - Returns "thing"
     *
     * This simplifies usage of the pattern:
     *
     * {@code
     * def thing = new Thing()
     * runWithDelegate(builder, thing)
     * parent.things(thing)
     * return thing
     * }
     *
     * to:
     *
     * {@code buildAndCall(new Thing(), builder, parent.&things) }
     *
     * @param thing
     * @param builder
     * @param callback
     * @return
     */
    static <T> T buildAndCall(T thing, Closure builder, Closure callback) {
        runWithDelegate(builder, thing)
        callback.call(thing)

        thing
    }

    /**
     * Runs the closure, using the provided delegate.
     *
     * @param closure
     * @param delegate
     * @return
     */
    static <T> T runWithDelegate(Closure closure, T delegate) {
        closure.delegate = delegate
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()

        delegate
    }
}
