package com.dmitrystonie.sunrisemoonriseapp.ui.testing

import androidx.annotation.Nullable
import androidx.core.os.postDelayed
import android.os.Handler;

internal object MessageDelayer {
    private const val DELAY_MILLIS = 3000L

    /**
     * Takes a String and returns it after [.DELAY_MILLIS] via a [DelayerCallback].
     * @param message the String that will be returned via the callback
     * @param callback used to notify the caller asynchronously
     */
    fun processMessage(
        message: String?, callback: DelayerCallback?,
        @Nullable idlingResource: SimpleIdlingResource?
    ) {
        // The IdlingResource is null in production.
        if (idlingResource != null) {
            idlingResource.setIdleState(false)
        }

        // Delay the execution, return message via callback.
        val handler: Handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (callback != null) {
                    callback.onDone(message)
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true)
                    }
                }
            }
        }, DELAY_MILLIS)
    }

    internal interface DelayerCallback {
        fun onDone(text: String?)
    }
}