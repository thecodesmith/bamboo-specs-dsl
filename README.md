# Bamboo Specs DSL

A Groovy DSL for writing [Bamboo
Specs](https://confluence.atlassian.com/bamboo/bamboo-specs-894743906.html).
Configuration as code, made simple.

[![Download](https://api.bintray.com/packages/thecodesmith/maven/bamboo-specs-dsl/images/download.svg)](https://bintray.com/thecodesmith/maven/bamboo-specs-dsl/_latestVersion)
[![Build Status](https://travis-ci.org/thecodesmith/bamboo-specs-dsl.svg?branch=master)](https://travis-ci.org/thecodesmith/bamboo-specs-dsl)
[![Coveralls Coverage Status](https://coveralls.io/repos/github/thecodesmith/bamboo-specs-dsl/badge.svg?branch=master)](https://coveralls.io/github/thecodesmith/bamboo-specs-dsl?branch=master)
[![CodeCov Coverage Status](https://codecov.io/gh/thecodesmith/bamboo-specs-dsl/branch/master/graph/badge.svg)](https://codecov.io/gh/thecodesmith/bamboo-specs-dsl)

**Note:** This project is still very much in the experimental stage. Contribution by
way of ideas, suggestions and pull requests is very welcome. The plan is to get
the API to a stable point and release version 1.0 as soon as possible.

Usage examples will be written once the examples from Bamboo's documentation are
fully implemented.


## Getting Started

To start using the library, add it to your project using Groovy's `@Grab` (the
simplest way to get started), or Gradle or Maven for more advanced build
configurations. The library is available through Bintray's JCenter.

### Grab

    @Grab('com.thecodesmith.bamboo:bamboo-specs-dsl:0.10.1')

### Gradle

    compile 'com.thecodesmith.bamboo:bamboo-specs-dsl:0.10.1'

### Maven

    <dependency>
        <groupId>com.thecodesmith.bamboo</groupId>
        <artifactId>bamboo-specs-dsl</artifactId>
        <version>0.10.1</version>
    </dependency>

### Requirements

* Groovy is required to post plan definition to Bamboo. It can be installed
  easily with [SDKMAN!](http://sdkman.io):

```
    curl -s "https://get.sdkman.io" | bash
    sdk install groovy
```

### Optional Tools

* IntelliJ IDEA or another IDE is helpful for code completion, but a text
  editor works just fine


## Minimalist Plan Definition

Create a Groovy script, for example named `BambooPlan.groovy`:

    @Grab('com.thecodesmith.bamboo:bamboo-specs-dsl:0.10.1')

    import com.thecodesmith.bamboo.specs.dsl.ProjectDsl
    import com.thecodesmith.bamboo.specs.dsl.PlanDsl

    project = ProjectDsl.project('foo', 'FOO') {
        description 'Project description here'
    }

    plan = PlanDsl.plan(project, 'bar', 'BAR') {
        description 'Build and test the project'

        planRepositories {
            gitRepository {
                name 'my-repo'
                url 'ssh://git@bitbucket.org:my-company/my-repo.git'
            }
        }

        stage('Build') {
            job('Build WAR', 'WAR') {
                tasks {
                    mavenTask {
                        goal 'clean package'
                        jdk 'JDK 1.8'
                        version3()
                        executionLabel 'Maven 3.2'
                        hasTests true
                        testResultsPath '**/target/reports/*.xml'
                    }
                }
            }
        }
    }


## Posting Definition to Bamboo

Add this to the bottom of the definition script:

    BAMBOO_URL = 'https://bamboo.my-company.com'
    BAMBOO_CREDENTIALS = new FileUserPasswordCredentials()

    server = new BambooServer(BAMBOO_URL, BAMBOO_CREDENTIALS)
    server.publish(plan)

This code could be extracted to a separate file if needed to run multiple plan
definitions.

Then run the script:

    groovy BambooPlan.groovy


## Troubleshooting

This line can be used to print the generated YAML for a Plan definition:

    println BambooSpecSerializer.dump(plan)

Enable debug logging for the Bamboo Specs library by running with system
property `-Dbamboo.specs.log.level=DEBUG`.


## Implementation Status

The DSL currently supports all features listed in the Bamboo Specs [official
documentation](https://docs.atlassian.com/bamboo-specs-docs/latest). If you find
any missing features, please let me know by opening an issue.


## Contributing

Pull requests are welcome! If there is a feature that is not yet implemented,
create a pull request and I will work to get it merged into the project.
Reporting issues is a big help as well.


## License

This library is licensed under the terms of the [Apache License, Version
2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
