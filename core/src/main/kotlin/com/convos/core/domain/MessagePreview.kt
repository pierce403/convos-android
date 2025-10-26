package com.convos.core.domain

import java.util.Date

/**
 * Message preview for conversation list
 * Ported from iOS MessagePreview.swift
 */
data class MessagePreview(
    val text: String,
    val createdAt: Date
)

