package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.model.task.TestParserTaskProperties
import spock.lang.Specification

import static com.thecodesmith.bamboo.specs.dsl.utils.TestUtils.toMap

class JobDslSpec extends Specification {

    def 'Job tasks'() {
        given:
        def job = JobDsl.job('foo', 'FOO') {
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
        props['tasks'].size() == 9
    }
}
