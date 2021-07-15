package com.example.fluentplayer.utils

import android.database.Cursor

object ContentResolverUtils {

    fun getString(cursor: Cursor, name: String): String {
        return try {
            cursor.getString(cursor.getColumnIndexOrThrow(name))
        } catch (e: Throwable) {
            ""
        }
    }

    fun getLong(cursor: Cursor, name: String): Long {
        return try {
            return cursor.getLong(cursor.getColumnIndexOrThrow(name))
        } catch (e: Throwable) {
            0L
        }
    }

    fun getInt(cursor: Cursor, name: String): Int {
        return try {
            return cursor.getInt(cursor.getColumnIndexOrThrow(name))
        } catch (e: Throwable) {
            0
        }
    }
}