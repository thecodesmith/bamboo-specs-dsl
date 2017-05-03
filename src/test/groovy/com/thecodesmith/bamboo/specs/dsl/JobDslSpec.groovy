package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.requirement.Requirement.MatchType
import com.atlassian.bamboo.specs.api.model.plan.artifact.ArtifactProperties
import com.atlassian.bamboo.specs.api.model.plan.artifact.ArtifactSubscriptionProperties
import com.atlassian.bamboo.specs.api.model.plan.requirement.RequirementProperties
import com.atlassian.bamboo.specs.model.task.TestParserTaskProperties
import spock.lang.Specification

import static com.thecodesmith.bamboo.specs.dsl.JobDsl.job
import static com.thecodesmith.bamboo.specs.dsl.utils.TestUtils.toMap

class JobDslSpec extends Specification {

    // TODO: split this up
    def 'Job tasks'() {
        given:
        def job = job('foo', 'FOO') {
            tasks {
                artifactDownloaderTask {
                    description 'bar'
                    sourcePlan 'PROJECTKEY', 'PLANKEY'

                    artifacts {
                        downloadItem {
                            allArtifacts true
                        }

                        downloadItem {
                            artifact 'an artifact'
                            path 'subdirectory'
                        }
                    }
                }

                cleanWorkingDirectoryTask {
                    description 'clean generated files'
                    enabled true
                }

                scriptTask {
                    inlineBody '''|#!/bin/bash
                                  |echo "hello, world!"
                                  |'''.stripMargin()
                }

                scriptTask {
                    interpreterBinSh()
                    fileFromPath 'build.sh'
                    argument '--verbose'
                    environmentVariables 'JAVA_OPTS=-Xmx700m'
                    workingSubdirectory 'ci'
                }

                testParserTask(TestParserTaskProperties.TestType.JUNIT) {
                    description 'Parse JUnit test results'
                    resultDirectories 'build/test/reports', 'target/test/xml-reports'
                }

                testParserTask(TestParserTaskProperties.TestType.TESTNG) {
                    description 'Parse TestNG test results'
                    defaultResultDirectory()
                    pickUpTestResultsCreatedOutsideOfThisBuild true
                }

                vcsCheckoutTask {
                    description 'Check out a few repositories'
                    cleanCheckout true

                    checkoutItems {
                        checkoutItem {
                            defaultRepository()
                        }
                        checkoutItem {
                            repository 'repo name'
                            path 'subdirectory'
                        }
                    }
                }

                sshTask {
                    host '12.34.56.78'
                    port 2200
                    username 'admin'
                    authenticateWithKey 'key'
                    command 'ls -al'
                }

                scpTask {
                    host '12.34.78.90'
                    port 2222
                    username 'admin'
                    authenticateWithKey 'key'
                    fromArtifact 'artifact-name'
                    toRemotePath '/var/libs/artifact-name'
                }

                mavenTask {
                    goal 'clean install'
                    hasTests true
                    testResultsPath '**/acceptance-tests/target/reports/*.xml'
                    version3()
                    jdk 'JDK 1.8'
                    executableLabel 'Maven 3.2'
                    environmentVariables 'MAVEN_OPTS="-Xmx768m -Xms64m -Dmaven.compiler.fork=true"'
                    workingSubdirectory 'maven-working-dir'
                }

                anyTask {
                    atlassianModule 'com.atlassian.bamboo.plugin.requirementtask:task.requirements'
                    configuration([
                            existingRequirement: 'isLinux',
                            regexMatchValue: 'true',
                            requirementKey: '',
                            requirementMatchType: 'match',
                            requirementMatchValue: ''
                    ])
                }
            }
        }

        when:
        def props = toMap(job)

        then:
        props['tasks'].size() == 11
    }

    def 'Job requirements'() {
        given:
        def job = job('foo', 'FOO') {
            requirement 'java'
            requirement 'python'

            requirements {
                requirement 'xvfb'

                requirement('operating.system') {
                    matchType MatchType.EQUALS
                    matchValue 'Linux'
                }
            }
        }

        when:
        def props = toMap(job)

        then:
        props['requirements'].size() == 4
        props['requirements'].every { it instanceof RequirementProperties }
    }

    def 'Job final tasks'() {
        given:
        def job = job('foo', 'FOO') {
            finalTasks {
                cleanWorkingDirectoryTask {
                    description 'clean generated files'
                    enabled true
                }
                scriptTask {
                    inlineBody 'echo foo'
                }
            }
        }

        when:
        def props = toMap(job)

        then:
        props['finalTasks'].size() == 2
    }

    def 'Job tasks and final tasks do not interfere with each other'() {
        given:
        def job = job('foo', 'FOO') {
            tasks {
                mavenTask {
                    goal 'clean install'
                    executableLabel 'Maven 3.2'
                }
            }

            finalTasks {
                cleanWorkingDirectoryTask {
                    enabled true
                }
                scriptTask {
                    inlineBody 'echo done'
                }
            }
        }

        when:
        def props = toMap(job)

        then:
        props['tasks'].size() == 1
        props['finalTasks'].size() == 2
    }

    def 'Job artifacts'() {
        given:
        def job = job('foo', 'FOO') {
            artifacts {
                artifact('Test Reports') {
                    location 'target/reports'
                    shared true
                }
                artifact {
                    location 'target/reports'
                    copyPattern '**/*.xml'
                }
                artifact 'WAR'
            }
        }

        when:
        def props = toMap(job)

        then:
        props['artifacts'].size() == 3
        props['artifacts'].every { it instanceof ArtifactProperties }
    }

    def 'Job artifact subscriptions'() {
        given:
        def job = job('foo', 'FOO') {
            artifactSuscriptions {
                artifactSubscription {
                    artifact 'WAR'
                    destination 'deploy'
                }
                artifactSubscription {
                    artifact 'JAR'
                }
            }
        }

        when:
        def props = toMap(job)

        then:
        props['subscriptions'].size() == 2
        props['subscriptions'].every { it instanceof ArtifactSubscriptionProperties }
    }
}
