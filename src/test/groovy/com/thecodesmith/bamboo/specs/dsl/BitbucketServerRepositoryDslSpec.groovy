package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.model.repository.VcsChangeDetectionProperties
import spock.lang.Specification

import static com.thecodesmith.bamboo.specs.dsl.BitbucketServerRepositoryDsl.bitbucketServerRepository
import static com.thecodesmith.bamboo.specs.dsl.utils.TestUtils.toMap

class BitbucketServerRepositoryDslSpec extends Specification {

    def 'Bitbucket Server example'() {
        given:
        def server = bitbucketServerRepository {
            name 'foo'
            server {
                applicationLink {
                    name 'main'
                }
            }
            projectKey 'FOO'
            repositorySlug 'bar'
            branch 'master'
            sshPrivateKey '/foo/id_rsa'
            sshPublicKey '/foo/id_rsa.pub'
        }

        when:
        def props = toMap(server)

        then:
        props['server']['name'] == 'main'
        props['projectKey'] == 'FOO'
        props['repositorySlug'] == 'bar'
        props['branch'] == 'master'
        props['sshPrivateKey'] == '/foo/id_rsa'
        props['sshPublicKey'] == '/foo/id_rsa.pub'
    }

    def 'Change detection'() {
        given:
        def repo = bitbucketServerRepository {
            changeDetection {
                vcsChangeDetection {
                    quietPeriodEnabled true
                }
            }
        }

        when:
        def props = toMap(repo)
        def cd = toMap(props['vcsChangeDetection'])

        then:
        props['vcsChangeDetection'] instanceof VcsChangeDetectionProperties
        cd['quietPeriodEnabled'] == true
    }
}
