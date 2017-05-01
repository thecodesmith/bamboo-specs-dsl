package com.thecodesmith.bamboo.specs.dsl.utils

/**
 * @author Brian Stewart
 */
class DslUtils {

    static <T> T runWithDelegate(Closure closure, T delegate) {
        closure.delegate = delegate
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()

        delegate
    }

    static <T> T addToList(List<T> list, T element) {
        list.add(element)

        element
    }
}
