package com.convos.core.auth

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.convos.core.logging.Logger

/**
 * Secure storage for sensitive data using EncryptedSharedPreferences
 * Android equivalent of iOS Keychain
 * Ported from iOS KeychainService.swift
 */
class SecureStorage(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "convos_secure_storage",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * Save a string value securely
     */
    fun saveString(key: String, value: String) {
        try {
            sharedPreferences.edit().putString(key, value).apply()
        } catch (e: Exception) {
            Logger.error("Failed to save string to secure storage: $key", e)
            throw SecureStorageException("Failed to save string", e)
        }
    }

    /**
     * Retrieve a string value securely
     */
    fun getString(key: String): String? {
        return try {
            sharedPreferences.getString(key, null)
        } catch (e: Exception) {
            Logger.error("Failed to retrieve string from secure storage: $key", e)
            null
        }
    }

    /**
     * Save binary data securely (Base64 encoded)
     */
    fun saveData(key: String, data: ByteArray) {
        try {
            val encoded = android.util.Base64.encodeToString(data, android.util.Base64.NO_WRAP)
            sharedPreferences.edit().putString(key, encoded).apply()
        } catch (e: Exception) {
            Logger.error("Failed to save data to secure storage: $key", e)
            throw SecureStorageException("Failed to save data", e)
        }
    }

    /**
     * Retrieve binary data securely (Base64 decoded)
     */
    fun getData(key: String): ByteArray? {
        return try {
            val encoded = sharedPreferences.getString(key, null) ?: return null
            android.util.Base64.decode(encoded, android.util.Base64.NO_WRAP)
        } catch (e: Exception) {
            Logger.error("Failed to retrieve data from secure storage: $key", e)
            null
        }
    }

    /**
     * Delete a value
     */
    fun delete(key: String) {
        try {
            sharedPreferences.edit().remove(key).apply()
        } catch (e: Exception) {
            Logger.error("Failed to delete from secure storage: $key", e)
            throw SecureStorageException("Failed to delete", e)
        }
    }

    /**
     * Check if a key exists
     */
    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    /**
     * Get all keys
     */
    fun getAllKeys(): Set<String> {
        return sharedPreferences.all.keys
    }

    /**
     * Clear all stored values
     */
    fun clear() {
        try {
            sharedPreferences.edit().clear().apply()
        } catch (e: Exception) {
            Logger.error("Failed to clear secure storage", e)
            throw SecureStorageException("Failed to clear storage", e)
        }
    }
}

/**
 * Exception thrown by SecureStorage
 */
class SecureStorageException(message: String, cause: Throwable? = null) : Exception(message, cause)

