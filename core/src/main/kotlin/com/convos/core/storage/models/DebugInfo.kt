package com.convos.core.storage.models

import kotlinx.serialization.Serializable

/**
 * Debug information for conversations
 * Ported from iOS DBConversation.DebugInfo
 */
@Serializable
data class DebugInfo(
    val epoch: Long = 0,
    val maybeForked: Boolean = false,
    val forkDetails: String = "",
    val localCommitLog: String = "",
    val remoteCommitLog: String = "",
    val commitLogForkStatus: CommitLogForkStatus = CommitLogForkStatus.UNKNOWN
) {
    companion object {
        val EMPTY = DebugInfo()
    }
}

