package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.Variable
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup
import com.atlassian.bamboo.specs.api.builders.project.Project
import com.atlassian.bamboo.specs.api.model.VariableProperties
import com.atlassian.bamboo.specs.api.model.plan.branches.PlanBranchManagementProperties
import com.atlassian.bamboo.specs.api.model.plan.dependencies.DependenciesProperties
import com.atlassian.bamboo.specs.api.builders.plan.dependencies.DependenciesConfiguration.DependencyBlockingStrategy
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.TimeUnit

import static com.thecodesmith.bamboo.specs.dsl.PlanDsl.plan
import static com.thecodesmith.bamboo.specs.dsl.utils.TestUtils.toMap

class PlanDslSpec extends Specification {

    @Shared name = 'foo'
    @Shared key = 'FOO'
    @Shared Project project

    def setupSpec() {
        project = Mock(Project)
    }

    def plan(@DelegatesTo(PlanDsl) Closure builder) {
        plan(project, name, key, builder)
    }

    def 'Create standalone plan'() {
        given:
        def plan = plan { }

        when:
        def props = toMap(plan)

        then:
        props['name'] == name
        props['key']['key'] == key
    }

    def 'Plan properties are empty for empty plan definition'() {
        given:
        def plan = plan { }

        when:
        def props = toMap(plan)

        then:
        props['description'] == ''
        props['stages'] == []
        props['repositories'] == []
        props['triggers'] == []
        props['variables'] == []
        props['enabled'] == true
        props['dependencies'] == GroovyMock(DependenciesProperties)
    }

    def 'Plan description'() {
        given:
        def plan = plan {
            description 'bar'
        }

        expect:
        toMap(plan)['description'] == 'bar'
    }

    def 'Plan with single variable'() {
        given:
        def plan = plan {
            variable 'foo', '42'
        }

        expect:
        def props = toMap(plan)['variables'][0]
        props['name'] == 'foo'
        props['value'] == '42'
    }

    def 'Plan with a variable of class Variable'() {
        given:
        def plan = plan {
            variable new Variable('foo', '42')
        }

        when:
        def props = toMap(plan)['variables'][0]

        then:
        props['name'] == 'foo'
        props['value'] == '42'
    }

    def 'Plan with multiple variables'() {
        given:
        def plan = plan {
            variables {
                variable 'foo', '4'
                variable 'bar', '8'
                variable 'baz', '15'
            }
        }

        when:
        def props = toMap(plan)

        then:
        props['variables'].size() == 3
        props['variables'].every { it instanceof VariableProperties }

        and:
        def map = props['variables'].collectEntries { [(it.name): it.value] }
        map == [foo: '4', bar: '8', baz: '15']
    }

    @Unroll
    def 'Plan #state'() {
        given:
        def plan = plan {
            enabled isEnabled
        }

        expect:
        toMap(plan)['enabled'] == isEnabled

        where:
        state      | isEnabled
        'enabled'  | true
        'disabled' | false
    }

    def 'Plan with automatic branch management'() {
        given:
        def plan = plan {
            planBranchManagement {
                createForVcsBranchMatching '^JIRA-[0-9]+'
                triggerBuildsLikeParentPlan()
                branchIntegration {
                    integrationBranchKey 'MASTER'
                    gatekeeper false
                    pushOnSuccessfulBuild true
                }
                delete {
                    whenRemovedFromRepository true
                }
                notificationForCommitters()
            }
        }

        when:
        def props = toMap(toMap(plan)['planBranchManagement'])

        then:
        props['triggeringOption'] == PlanBranchManagementProperties.TriggeringOption.INHERITED
    }

    def 'Plan with single stage'() {
        given:
        def plan = plan {
            stage('foo') { }
        }

        when:
        def props = toMap(plan)

        then:
        props['stages'].size() == 1
        props['stages'][0]['name'] == 'foo'
    }

    def 'Plan with multiple stages'() {
        given:
        def plan = plan {
            stages {
                stage('a') { }
                stage('b') { }
            }
        }

        when:
        def props = toMap(plan)

        then:
        props['stages'].size() == 2
        ['a', 'b'] == props['stages'].collect { toMap(it)['name'] }
    }

    def 'Plan repositories'() {
        given:
        def plan = plan {
            linkedRepositories 'a', 'b'

            planRepositories {
                gitRepository {
                    name 'bar'
                    url 'ssh://git@github.com:foo/bar'
                }

                bitbucketServerRepository {
                    name 'baz'
                    server { applicationLink { name 'bitbucket-server' } }
                    projectKey 'PROJ'
                    repositorySlug 'baz'
                }
            }
        }

        when:
        def props = toMap(plan)

        then:
        props['repositories'].size() == 4
    }

    def 'Plan triggers'() {
        given:
        def plan = plan {
            triggers {
                repositoryPollingTrigger {
                    pollEvery 10, TimeUnit.MINUTES
                }
                remoteTrigger {
                    triggerIPAddresses '12.34.56.78'
                }
                scheduledTrigger {
                    scheduleEvery 4, TimeUnit.HOURS
                }
                scheduledTrigger {
                    cronExpression '0 0/30 9-19 ? * MON-FRI'
                }
            }
        }

        when:
        def props = toMap(plan)

        then:
        props['triggers'].size() == 4
    }

    def 'Plan dependencies'() {
        given:
        def plan = plan {
            dependencies {
                configuration {
                    dependenciesConfiguration {
                        enabledForBranches enabled
                        requireAllStagesPassing passing
                        blockingStrategy strategy
                    }
                }
            }
        }

        when:
        def props = toMap(toMap(plan)['dependencies']['dependenciesConfigurationProperties'])

        then:
        props['enabledForBranches']      == enabled
        props['requireAllStagesPassing'] == passing
        props['blockingStrategy']        == strategy

        where:
        enabled | passing | strategy
        true    | true    | DependencyBlockingStrategy.NONE
        true    | true    | DependencyBlockingStrategy.BLOCK_IF_PARENT_HAS_CHANGES
        true    | true    | DependencyBlockingStrategy.BLOCK_IF_PARENT_IN_PROGRESS
        true    | false   | DependencyBlockingStrategy.BLOCK_IF_PARENT_IN_PROGRESS
        false   | true    | DependencyBlockingStrategy.BLOCK_IF_PARENT_IN_PROGRESS
        false   | false   | DependencyBlockingStrategy.BLOCK_IF_PARENT_IN_PROGRESS
    }
}
