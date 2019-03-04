package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.requirement.Requirement.MatchType
import com.atlassian.bamboo.specs.api.rsbs.RunnerSettings
import com.atlassian.bamboo.specs.util.BambooServer
import com.atlassian.bamboo.specs.util.BambooSpecSerializer

import static com.thecodesmith.bamboo.specs.dsl.PlanDsl.plan
import static com.thecodesmith.bamboo.specs.dsl.ProjectDsl.project

project = project('foo', 'FOO') {
    description 'project desc'
}

plan = plan(project, 'bar', 'BAR') {
    description 'plan desc'

    linkedRepositories 'global-repo', 'another-repo'

    planRepositories {
        gitRepository {
            name 'my-repo'
            url 'ssh://git@bitbucket.org:my-company/my-repo.git'
            branch 'master'
        }
    }

    variables {
        variable 'PRO_VERSION', '9.98'
        variable 'FOO', 'bar'
    }

    stages {
        stage('Build') {
            jobs {
                job('Build WAR', 'BUILD') {
                    requirements {
                        requirement 'java'
                        requirement('operating.system') {
                            matchType MatchType.EQUALS
                            matchValue 'Linux'
                        }
                    }

                    tasks {
                        artifactDownloaderTask {
                            sourcePlan 'PROJECTKEY', 'PLANKEY'
                            artifacts {
                                downloadItem {
                                    allArtifacts true
                                }
                            }
                        }
                        vcsCheckoutTask {
                            addCheckoutOfDefaultRepository()
                        }
                        scriptTask {
                            fileFromPath 'build.sh'
                        }
                    }

                    finalTasks {
                        scriptTask {
                            fileFromPath 'cleanup.sh'
                        }
                    }

                    artifacts {
                        artifact('WAR') {
                            location 'build/libs'
                            copyPattern '*.war'
                            shared true
                        }
                        artifact {
                            location 'build/reports'
                            copyPattern '*.xml'
                        }
                    }
                }
            }
        }

        stage('Test') {
            manual true

            jobs {
                job('Test app', 'TEST') {
                    artifactSuscriptions {
                        artifactSubscription {
                            artifact 'WAR'
                            destination 'deploy'
                        }
                    }
                }
            }
        }
    }
}

// Set restEnabled to false, otherwise it will attempt to post to Bamboo
RunnerSettings.restEnabled = false
RunnerSettings.yamlDir = new File(".").toPath()
server = new BambooServer("http://localhost:8085")
//server = new BambooServer('https://localhost:8085', new SimpleUserPasswordCredentials('user', 'pass'))
//server = new BambooServer('https://localhost:8085', new FileUserPasswordCredentials())

// Write YAML to file in the current directory
server.publish(plan)

// Print YAML to console
println BambooSpecSerializer.dump(plan)
