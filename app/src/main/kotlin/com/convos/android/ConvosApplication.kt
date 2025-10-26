package com.convos.android

import android.app.Application
import com.convos.core.config.ConfigManager
import com.convos.core.logging.Logger
import timber.log.Timber

class ConvosApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize logging
        initializeLogging()

        // Initialize configuration
        val environment = ConfigManager.currentEnvironment
        Logger.info("App starting with environment: $environment")

        // TODO: Initialize ConvosClient
        // TODO: Initialize Firebase
    }

    private fun initializeLogging() {
        val environment = ConfigManager.currentEnvironment

        if (environment.isProduction) {
            // Production logging - only errors and warnings
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    if (priority >= android.util.Log.WARN) {
                        // TODO: Send to crash reporting service
                        super.log(priority, tag, message, t)
                    }
                }
            })
        } else {
            // Debug logging - everything
            Timber.plant(Timber.DebugTree())
        }

        Logger.initialize()
    }
}

