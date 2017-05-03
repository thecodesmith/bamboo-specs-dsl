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

    @Grab('com.thecodesmith.bamboo:bamboo-specs-dsl:0.10.0')

### Gradle

    compile 'com.thecodesmith.bamboo:bamboo-specs-dsl:0.10.0'

### Maven

    <dependency>
        <groupId>com.thecodesmith.bamboo</groupId>
        <artifactId>bamboo-specs-dsl</artifactId>
        <version>0.10.0</version>
    </dependency>


## Implemented Bamboo Specs Features

This list shows the already supported features by the Bamboo Specs DSL.
This list of features is taken directly from the [Bamboo Specs
documentation](https://docs.atlassian.com/bamboo-specs-docs/latest).

- [x] Projects
    - [x] Description
    - [x] Plans
- [ ] Plans
    - [x] Description
    - [x] Stages
    - [x] Linked repositories
    - [x] Plan repositories
    - [ ] Triggers
    - [ ] Plan branch management
    - [ ] Dependencies
    - [x] Variables
    - [ ] Permissions
    - [x] Enabled
- [ ] Plan branches
    - [ ] Automatic branch management
    - [ ] Manual branch management
    - [ ] Automatic branch merging
    - [ ] Branch notifications
    - [ ] Overriding branch settings
- [x] Stages
    - [x] Description
    - [x] Jobs
    - [x] Manual
- [x] Jobs
    - [x] Tasks
    - [x] Artifacts
    - [x] Artifact subscriptions
    - [x] Requirements
- [ ] Tasks
    - [ ] Artifact downloader
    - [ ] Clean working directory
    - [ ] Script
    - [ ] Test results parser
    - [ ] Repository checkout
    - [ ] Maven
    - [ ] Configuring unsupported tasks
    - [ ] Build tasks
    - [ ] Final tasks
- [ ] Repositories
    - [x] Multiple repositories
    - [x] Plan & linked repositories
    - [x] Git repositories
    - [x] Bitbucket Server Git repositories
    - [ ] SVN
    - [ ] Mercurial
    - [ ] CVS
- [ ] Artifacts
    - [x] Defining an artifact
    - [x] Sharing an artifact
    - [x] Using a shared artifact in another job
    - [ ] Using a shared artifact in another plan
    - [ ] Sharing an artifact to a deployment plan
- [ ] Build triggers
    - [x] Polling the repository for changes
    - [x] Repository triggers a build on commit
    - [ ] Cron-based scheduling
    - [ ] Conditional build triggers
    - [ ] Running a build when another plan has completed successfully
    - [ ] Multiple triggers
    - [x] No triggers
- [ ] Requirements
- [x] Variables
- [ ] Utilities


## Contributing

Pull requests are welcome! If there is a feature that is not yet implemented,
create a pull request and I will work to get it merged into the project.


## License

This library is licensed under the terms of the [Apache License, Version
2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
