package com.convos.core.storage.models

import kotlinx.serialization.Serializable
import java.util.Date

/**
 * Message update information for conversation updates
 * Ported from iOS DBMessage.Update
 */
@Serializable
data class MessageUpdate(
    val initiatedByInboxId: String,
    val addedInboxIds: List<String> = emptyList(),
    val removedInboxIds: List<String> = emptyList(),
    val metadataChanges: List<MetadataChange> = emptyList(),
    val expiresAt: Long? = null
) {
    @Serializable
    data class MetadataChange(
        val field: String,
        val oldValue: String? = null,
        val newValue: String? = null
    )

    val expiresAtDate: Date?
        get() = expiresAt?.let { Date(it) }
}

