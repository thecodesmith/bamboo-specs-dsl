package com.thecodesmith.bamboo.specs.dsl.utils

/**
 * @author Brian Stewart
 */
class TestUtils {

    /**
     * Convert object to a Map for ease of testing the object's properties.
     *
     * @param object
     * @return
     */
    static Map toMap(object) {
        def fields = object.class.declaredFields.findAll { !it.synthetic }

        fields.collectEntries {
            try {
                [(it.name): object."$it.name"]
            } catch (ignore) {
                [(it.name): null]
            }
        }
    }
}
