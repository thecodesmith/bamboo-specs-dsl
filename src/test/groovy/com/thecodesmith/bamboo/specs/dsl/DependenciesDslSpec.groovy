package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.dependencies.Dependencies
import com.atlassian.bamboo.specs.api.builders.plan.dependencies.DependenciesConfiguration
import spock.lang.Specification

import static com.atlassian.bamboo.specs.api.builders.plan.dependencies.DependenciesConfiguration.DependencyBlockingStrategy.*
import static com.thecodesmith.bamboo.specs.dsl.utils.TestUtils.toMap

class DependenciesDslSpec extends Specification {

    def 'Dependencies with configuration'() {
        given:
        def dependencies = DependenciesDsl.dependencies {
            configuration {
                dependenciesConfiguration {
                    enabledForBranches true
                    requireAllStagesPassing true
                    blockingStrategy BLOCK_IF_PARENT_IN_PROGRESS
                }
            }
        }

        when:
        def props = toMap(toMap(dependencies)['dependenciesConfigurationProperties'])

        then:
        props['enabledForBranches'] == true
        props['requireAllStagesPassing'] == true
        props['blockingStrategy'] == BLOCK_IF_PARENT_IN_PROGRESS
    }

    def 'Delegates to raw Dependencies object'() {
        given:
        def dsl = new DependenciesDsl()

        when:
        dsl.dependencies

        then:
        noExceptionThrown()
    }
}
