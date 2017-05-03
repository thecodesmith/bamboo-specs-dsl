package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.Plan
import spock.lang.Specification

import static com.thecodesmith.bamboo.specs.dsl.ProjectDsl.project
import static com.thecodesmith.bamboo.specs.dsl.utils.TestUtils.toMap

class ProjectDslSpec extends Specification {

    def 'Create standalone project'() {
        given:
        def project = project('foo', 'FOO') {
            oid '42'
            description 'bar'
        }

        when:
        def props = toMap(project)

        then:
        props['name'] == 'foo'
        props['key']['key'] == 'FOO'
        props['oid']['oid'] == '42'
        props['description'] == 'bar'
    }

    def 'Create nested plan inside project'() {
        given:
        Plan nestedPlan = null

        and:
        project('foo', 'FOO') {
            nestedPlan = plan('bar', 'BAR') {
                description "I'm a nested plan"
            }
        }

        when:
        def props = toMap(nestedPlan)

        then:
        println props
        props['project']['name'] == 'foo'
        props['project']['key']['key'] == 'FOO'
    }
}
