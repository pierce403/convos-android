package com.convos.core.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Inbox database entity
 * Ported from iOS DBInbox.swift
 */
@Entity(tableName = "inbox")
data class DBInboxEntity(
    @PrimaryKey
    @ColumnInfo(name = "inbox_id")
    val inboxId: String,

    @ColumnInfo(name = "client_id")
    val clientId: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
)

