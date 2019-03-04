package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.api.builders.plan.configuration.AllOtherPluginsConfiguration
import com.atlassian.bamboo.specs.api.builders.plan.configuration.PluginConfiguration
import groovy.transform.CompileStatic

/**
 * @author Brian Stewart
 */
@CompileStatic
class PluginConfigurationDsl {

    List<PluginConfiguration> configurations

    PluginConfigurationDsl() {
        configurations = []
    }

    AllOtherPluginsConfiguration custom(String name, Map args) {
        def configuration = new AllOtherPluginsConfiguration()
                .configuration([(name): args] as Map<String, Object>)
        configurations << configuration
        configuration
    }
}
