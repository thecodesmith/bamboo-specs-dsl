package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.credentials.SharedCredentialsIdentifier
import com.atlassian.bamboo.specs.builders.repository.git.GitRepository
import com.atlassian.bamboo.specs.builders.repository.git.SshPrivateKeyAuthentication
import com.atlassian.bamboo.specs.builders.repository.git.UserPasswordAuthentication

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.buildAndCall
import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.call

/**
 * @author Brian Stewart
 */
class GitRepositoryAuthenticationDsl {
    @Delegate GitRepository repository

    GitRepositoryAuthenticationDsl(GitRepository repository) {
        this.repository = repository
    }

    SshPrivateKeyAuthentication sshPrivateKeyAuthentication(String sshPrivateKey, @DelegatesTo(SshPrivateKeyAuthentication) Closure builder) {
        buildAndCall(new SshPrivateKeyAuthentication(sshPrivateKey), builder, repository.&authentication)
    }

    UserPasswordAuthentication userPasswordAuthentication(String username, @DelegatesTo(UserPasswordAuthentication) Closure builder) {
        buildAndCall(new UserPasswordAuthentication(username), builder, repository.&authentication)
    }

    SharedCredentialsIdentifier sharedCredentialsIdentifier(String name) {
        call(new SharedCredentialsIdentifier(name), repository.&authentication)
    }
}
