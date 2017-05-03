package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.model.plan.JobProperties
import spock.lang.Specification

import static com.thecodesmith.bamboo.specs.dsl.StageDsl.stage
import static com.thecodesmith.bamboo.specs.dsl.utils.TestUtils.toMap

class StageDslSpec extends Specification {

    def 'Stage options'() {
        given:
        def stage = stage('build') {
            description 'build stage'
            manual true
            finalStage false
        }

        when:
        def props = toMap(stage)

        then:
        props['name'] == 'build'
        props['description'] == 'build stage'
        props['manualStage'] == true
        props['finalStage'] == false
        props['jobs'] == []
    }

    def 'Stage jobs'() {
        given:
        def stage = stage('foo') {
            jobs {
                job('bar', 'BAR') {}
                job('baz', 'BAZ') {}
            }
        }

        when:
        def props = toMap(stage)

        then:
        props['jobs'].size() == 2
        props['jobs'].every { it instanceof JobProperties }
    }
}
