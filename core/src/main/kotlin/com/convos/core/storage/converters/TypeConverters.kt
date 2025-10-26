package com.convos.core.storage.converters

import androidx.room.TypeConverter
import com.convos.core.storage.models.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

/**
 * Room type converters for custom types
 */
class ConvosTypeConverters {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    // Date converters
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // ConversationKind converters
    @TypeConverter
    fun fromConversationKind(value: ConversationKind): String {
        return value.value
    }

    @TypeConverter
    fun toConversationKind(value: String): ConversationKind {
        return ConversationKind.fromString(value)
    }

    // Consent converters
    @TypeConverter
    fun fromConsent(value: Consent): String {
        return value.value
    }

    @TypeConverter
    fun toConsent(value: String): Consent {
        return Consent.fromString(value)
    }

    // MemberRole converters
    @TypeConverter
    fun fromMemberRole(value: MemberRole): String {
        return value.value
    }

    @TypeConverter
    fun toMemberRole(value: String): MemberRole {
        return MemberRole.fromString(value)
    }

    // MessageStatus converters
    @TypeConverter
    fun fromMessageStatus(value: MessageStatus): String {
        return value.value
    }

    @TypeConverter
    fun toMessageStatus(value: String): MessageStatus {
        return MessageStatus.fromString(value)
    }

    // MessageSource converters
    @TypeConverter
    fun fromMessageSource(value: MessageSource): String {
        return value.value
    }

    @TypeConverter
    fun toMessageSource(value: String): MessageSource {
        return MessageSource.fromString(value)
    }

    // DBMessageType converters
    @TypeConverter
    fun fromDBMessageType(value: DBMessageType): String {
        return value.value
    }

    @TypeConverter
    fun toDBMessageType(value: String): DBMessageType {
        return DBMessageType.fromString(value)
    }

    // MessageContentType converters
    @TypeConverter
    fun fromMessageContentType(value: MessageContentType): String {
        return value.value
    }

    @TypeConverter
    fun toMessageContentType(value: String): MessageContentType {
        return MessageContentType.fromString(value)
    }

    // CommitLogForkStatus converters
    @TypeConverter
    fun fromCommitLogForkStatus(value: CommitLogForkStatus): String {
        return value.value
    }

    @TypeConverter
    fun toCommitLogForkStatus(value: String): CommitLogForkStatus {
        return CommitLogForkStatus.fromString(value)
    }

    // List<String> converters
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let { json.decodeFromString<List<String>>(it) }
    }

    // DebugInfo converter
    @TypeConverter
    fun fromDebugInfo(value: DebugInfo?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toDebugInfo(value: String?): DebugInfo? {
        return value?.let { json.decodeFromString<DebugInfo>(it) }
    }

    // MessageUpdate converter
    @TypeConverter
    fun fromMessageUpdate(value: MessageUpdate?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toMessageUpdate(value: String?): MessageUpdate? {
        return value?.let { json.decodeFromString<MessageUpdate>(it) }
    }
}

