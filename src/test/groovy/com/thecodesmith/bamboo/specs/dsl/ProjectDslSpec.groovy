package com.thecodesmith.bamboo.specs.dsl

import com.thecodesmith.bamboo.specs.dsl.utils.TestUtils
import spock.lang.Specification

class ProjectDslSpec extends Specification {

    def 'Create standalone project'() {
        given:
        def project = ProjectDsl.project('foo', 'FOO') {
            oid '42'
            description 'bar'
        }

        when:
        def props = TestUtils.toMap(project)
        println props

        then:
        props['name'] == 'foo'
        props['key']['key'] == 'FOO'
        props['oid']['oid'] == '42'
        props['description'] == 'bar'
    }
}
