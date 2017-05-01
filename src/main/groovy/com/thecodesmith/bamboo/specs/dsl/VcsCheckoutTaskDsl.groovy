package com.thecodesmith.bamboo.specs.dsl

import com.atlassian.bamboo.specs.builders.task.CheckoutItem
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask

import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.buildAndCall
import static com.thecodesmith.bamboo.specs.dsl.utils.DslUtils.runWithDelegate

/**
 * @author Brian Stewart
 */
class VcsCheckoutTaskDsl {
    @Delegate VcsCheckoutTask task

    VcsCheckoutTaskDsl() {
        task = new VcsCheckoutTask()
    }

    void checkoutItems(Closure builder) {
        runWithDelegate(builder, this)
    }

    CheckoutItem checkoutItem(@DelegatesTo(CheckoutItem) Closure builder) {
        buildAndCall(new CheckoutItem(), builder, task.&checkoutItems)
    }
}
