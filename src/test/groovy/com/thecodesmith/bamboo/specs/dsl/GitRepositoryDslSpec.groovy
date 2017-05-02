package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.repository.VcsChangeDetection.FileFilteringOption
import com.atlassian.bamboo.specs.api.model.repository.VcsChangeDetectionProperties
import com.atlassian.bamboo.specs.builders.repository.viewer.BitbucketServerRepositoryViewer
import com.atlassian.bamboo.specs.model.repository.git.SharedCredentialsAuthenticationProperties
import com.atlassian.bamboo.specs.model.repository.git.SshPrivateKeyAuthenticationProperties
import com.atlassian.bamboo.specs.model.repository.git.UserPasswordAuthenticationProperties
import groovy.transform.NotYetImplemented
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Duration

import static com.thecodesmith.bamboo.specs.dsl.GitRepositoryDsl.gitRepository
import static com.thecodesmith.bamboo.specs.dsl.utils.TestUtils.*

class GitRepositoryDslSpec extends Specification {

    def 'SSH authentication'() {
        given:
        def repo = gitRepository {
            name 'bar'
            url 'ssh://git@github.com:foo/bar'
            branch 'master'
            authentication {
                sshPrivateKeyAuthentication('/home/me/.ssh/id_rsa') {
                    passphrase 'baz'
                }
            }
        }

        when:
        def props = toMap(repo)
        def auth = toMap(props['authentication'])

        then:
        props['url'] == 'ssh://git@github.com:foo/bar'
        props['branch'] == 'master'
        props['authentication'] instanceof SshPrivateKeyAuthenticationProperties

        and:
        auth['sshPrivateKey'] == '/home/me/.ssh/id_rsa'
        auth['passphrase'] == 'baz'
    }

    def 'User & password authentication'() {
        given:
        def repo = gitRepository {
            url 'ssh://git@github.com:foo/bar'
            authentication {
                userPasswordAuthentication('me') {
                    password 'secret!'
                }
            }
        }

        when:
        def props = toMap(repo)
        def auth = toMap(props['authentication'])

        then:
        props['authentication'] instanceof UserPasswordAuthenticationProperties
        auth['username'] == 'me'
        auth['password'] == 'secret!'
    }

    def 'Shared credentials authentication'() {
        given:
        def repo = gitRepository {
            url 'ssh://git@github.com:foo/bar'
            authentication {
                sharedCredentialsIdentifier 'baz'
            }
        }

        when:
        def props = toMap(repo)
        def auth = toMap(props['authentication'])

        then:
        props['authentication'] instanceof SharedCredentialsAuthenticationProperties
        auth['sharedCredentials']['name'] == 'baz'
    }

    def 'No authentication'() {
        given:
        def repo = gitRepository {
            url 'ssh://git@github.com:foo/bar'
        }

        when:
        def props = toMap(repo)

        then:
        props['authentication'] == null
    }

    @Unroll
    def 'Shallow clones #enabled'() {
        given:
        def repo = gitRepository {
            shallowClonesEnabled state
        }

        expect:
        toMap(repo)['useShallowClones'] == state

        where:
        enabled    | state
        'enabled'  | true
        'disabled' | false
    }

    @Unroll
    def 'Fetch whole repository #enabled'() {
        given:
        def repo = gitRepository {
            fetchWholeRepository state
        }

        expect:
        toMap(repo)['fetchWholeRepository'] == state

        where:
        enabled    | state
        'enabled'  | true
        'disabled' | false
    }

    @Unroll
    def 'Remote agent cache #enabled'() {
        given:
        def repo = gitRepository {
            remoteAgentCacheEnabled state
        }

        expect:
        toMap(repo)['useRemoteAgentCache'] == state

        where:
        enabled    | state
        'enabled'  | true
        'disabled' | false
    }

    @Unroll
    def 'LFS #enabled'() {
        given:
        def repo = gitRepository {
            lfsEnabled state
        }

        expect:
        toMap(repo)['useLfs'] == state

        where:
        enabled    | state
        'enabled'  | true
        'disabled' | false
    }

    @Unroll
    def 'Submodules #enabled'() {
        given:
        def repo = gitRepository {
            submodulesEnabled state
        }

        expect:
        toMap(repo)['useSubmodules'] == state

        where:
        enabled    | state
        'enabled'  | true
        'disabled' | false
    }

    @NotYetImplemented
    def 'Repository viewer'() {
        given:
        def repo = gitRepository {
            repositoryViewer new BitbucketServerRepositoryViewer()
        }

        expect:
        toMap(repo)['repositoryViewer'] != null
    }

    @Unroll
    def 'Verbose logs #enabled'() {
        given:
        def repo = gitRepository {
            verboseLogs state
        }

        expect:
        toMap(repo)['verboseLogs'] == state

        where:
        enabled    | state
        'enabled'  | true
        'disabled' | false
    }

    @Unroll
    def 'Command timeout'() {
        given:
        def repo = gitRepository {
            commandTimeoutInMinutes 10
        }

        expect:
        toMap(repo)['commandTimeout'] == Duration.ofMinutes(10)
    }

    def 'Change detection'() {
        given:
        def repo = gitRepository {
            changeDetection {
                vcsChangeDetection {
                    quietPeriodEnabled true
                    quietPeriod Duration.ofMinutes(1)
                    quietPeriodMaxRetries 10
                    changesetFilterPatternRegex '.*draft.*'
                    filterFilePatternOption FileFilteringOption.INCLUDE_ONLY
                    filterFilePatternRegex '.*\\.java'
                }
            }
        }

        when:
        def props = toMap(repo)
        def cd = toMap(props['vcsChangeDetection'])

        then:
        props['vcsChangeDetection'] instanceof VcsChangeDetectionProperties
        cd['quietPeriodEnabled'] == true
        cd['quietPeriod'] == Duration.ofMinutes(1)
        cd['maxRetries'] == 10
        cd['changesetFilterPatternRegex'] == '.*draft.*'
        cd['filterFilePatternOption'] == FileFilteringOption.INCLUDE_ONLY
        cd['filterFilePatternRegex'] == '.*\\.java'
    }
}
