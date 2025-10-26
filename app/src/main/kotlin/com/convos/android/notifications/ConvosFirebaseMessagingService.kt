package com.convos.android.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.convos.core.logging.Logger

/**
 * Firebase Cloud Messaging service for handling push notifications
 * Ported from iOS NotificationService.swift
 */
class ConvosFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Logger.info("FCM message received from: ${remoteMessage.from}")

        // Handle data payload
        if (remoteMessage.data.isNotEmpty()) {
            Logger.debug("Message data payload: ${remoteMessage.data}")
            handleDataPayload(remoteMessage.data)
        }

        // Handle notification payload
        remoteMessage.notification?.let {
            Logger.debug("Message notification: ${it.title}")
            // TODO: Display notification
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Logger.info("New FCM token: $token")

        // TODO: Send token to backend
        // TODO: Register with XMTP for push notifications
    }

    private fun handleDataPayload(data: Map<String, String>) {
        // TODO: Parse XMTP notification payload
        // TODO: Decrypt message
        // TODO: Show notification with message preview
    }
}

