package com.convos.core.logging

import timber.log.Timber

/**
 * Logging facade for the application
 * Ported from iOS Logger.swift
 */
object Logger {

    private var isInitialized = false

    fun initialize() {
        if (!isInitialized) {
            isInitialized = true
        }
    }

    fun verbose(message: String, throwable: Throwable? = null) {
        Timber.v(throwable, message)
    }

    fun debug(message: String, throwable: Throwable? = null) {
        Timber.d(throwable, message)
    }

    fun info(message: String, throwable: Throwable? = null) {
        Timber.i(throwable, message)
    }

    fun warn(message: String, throwable: Throwable? = null) {
        Timber.w(throwable, message)
    }

    fun error(message: String, throwable: Throwable? = null) {
        Timber.e(throwable, message)
    }

    fun wtf(message: String, throwable: Throwable? = null) {
        Timber.wtf(throwable, message)
    }

    /**
     * Log a message with a tag
     */
    fun tag(tag: String): TagLogger {
        return TagLogger(tag)
    }

    class TagLogger(private val tag: String) {
        fun verbose(message: String, throwable: Throwable? = null) {
            Timber.tag(tag).v(throwable, message)
        }

        fun debug(message: String, throwable: Throwable? = null) {
            Timber.tag(tag).d(throwable, message)
        }

        fun info(message: String, throwable: Throwable? = null) {
            Timber.tag(tag).i(throwable, message)
        }

        fun warn(message: String, throwable: Throwable? = null) {
            Timber.tag(tag).w(throwable, message)
        }

        fun error(message: String, throwable: Throwable? = null) {
            Timber.tag(tag).e(throwable, message)
        }
    }
}

