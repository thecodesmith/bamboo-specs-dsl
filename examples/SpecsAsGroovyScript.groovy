@Grab('com.thecodesmith.bamboo:bamboo-specs-dsl:0.10.0')

import com.atlassian.bamboo.specs.util.BambooServer
import com.atlassian.bamboo.specs.util.BambooSpecSerializer
import com.atlassian.bamboo.specs.util.FileUserPasswordCredentials

import static com.thecodesmith.bamboo.specs.dsl.PlanDsl.plan
import static com.thecodesmith.bamboo.specs.dsl.ProjectDsl.project

project = project('foo', 'FOO') {
    description 'Project description here'
}

plan = plan(project, 'bar', 'BAR') {
    description 'Build and test the project'

    planRepositories {
        gitRepository {
            name 'my-repo'
            url 'ssh://git@bitbucket.org:my-company/my-repo.git'
        }
    }

    stages {
        stage('Build') {

        }
        stage('Test') {

        }
    }
}

// Useful for debugging the generated YAML being posted to the Bamboo API
println BambooSpecSerializer.dump(plan)

// Publish using credentials from file. See JavaDoc for
// FileUserPasswordCredentials for details on the file and format:
// https://docs.atlassian.com/bamboo-specs/latest/com/atlassian/bamboo/specs/util/FileUserPasswordCredentials.html
BAMBOO_URL = 'https://bamboo.my-company.com'
BAMBOO_CREDENTIALS = new FileUserPasswordCredentials()

server = new BambooServer(BAMBOO_URL, BAMBOO_CREDENTIALS)
server.publish(plan)
