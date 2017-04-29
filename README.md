# Bamboo Specs DSL

A Groovy DSL for writing [Bamboo
Specs](https://confluence.atlassian.com/bamboo/bamboo-specs-894743906.html).
Configuration as code, made simple.


**Note:** This project is still very much in the experimental stage. Contribution by
way of ideas, suggestions and pull requests is very welcome. The plan is to get
the API to a stable point and release version 1.0 as soon as possible.


## Getting Started

To start using the library, add it to your project using Groovy's `@Grab` (the
simplest way to get started), or Gradle or Maven for more advanced build
configurations. The library is available through Bintray's JCenter.

### Grab

    @Grab('com.thecodesmith.bamboo:bamboo-specs-dsl:0.1.0-SNAPSHOT')

### Gradle

    compile 'com.thecodesmith.bamboo:bamboo-specs-dsl:0.1.0-SNAPSHOT'

### Maven

    <dependency>
        <groupId>com.thecodesmith.bamboo</groupId>
        <artifactId>bamboo-specs-dsl</artifactId>
        <version>0.1.0-SNAPSHOT</version>
    </dependency>


## Implemented Bamboo Specs Features

This list shows the already supported features by the Bamboo Specs DSL.
This list of features is taken directly from the [Bamboo Specs
documentation](https://docs.atlassian.com/bamboo-specs-docs/latest).

- [ ] Projects
    - [ ] Description
    - [ ] Plans
- [ ] Plans
    - [ ] Description
    - [ ] Stages
    - [ ] Linked repositories
    - [ ] Plan repositories
    - [ ] Triggers
    - [ ] Plan branch management
    - [ ] Dependencies
    - [ ] Variables
    - [ ] Permissions
    - [ ] Enabled
- [ ] Plan branches
    - [ ] Automatic branch management
    - [ ] Manual branch management
    - [ ] Automatic branch merging
    - [ ] Branch notifications
    - [ ] Overriding branch settings
- [ ] Stages
    - [ ] Description
    - [ ] Jobs
    - [ ] Manual
- [ ] Jobs
    - [ ] Tasks
    - [ ] Artifacts
    - [ ] Artifact subscriptions
    - [ ] Requirements
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
    - [ ] Multiple repositories
    - [ ] Plan & linked repositories
    - [ ] Git repositories
    - [ ] Bitbucket Server Git repositories
    - [ ] SVN
    - [ ] Mercurial
    - [ ] CVS
- [ ] Artifacts
    - [ ] Defining an artifact
    - [ ] Sharing an artifact
    - [ ] Using a shared artifact in another job
    - [ ] Using a shared artifact in another plan
    - [ ] Sharing an artifact to a deployment plan
- [ ] Build triggers
    - [ ] Polling the repository for changes
    - [ ] Repository triggers a build on commit
    - [ ] Cron-based scheduling
    - [ ] Conditional build triggers
    - [ ] Running a build when another plan has completed successfully
    - [ ] Multiple triggers
    - [ ] No triggers
- [ ] Requirements
- [ ] Variables
- [ ] Utilities


## Contributing

Pull requests are welcome! If there is a feature that is not yet implemented,
create a pull request and I will work to get it merged into the project.


## License

This library is licensed under the terms of the [Apache License, Version
2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
