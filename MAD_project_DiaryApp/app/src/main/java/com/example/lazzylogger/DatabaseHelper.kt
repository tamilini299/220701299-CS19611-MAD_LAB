package com.example.lazzylogger

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "DiaryDB", null, 1) {

    companion object {
        private const val TAG = "DatabaseHelper"
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL("CREATE TABLE diary(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT)")
        } catch (e: SQLException) {
            Log.e(TAG, "Error creating table: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL("DROP TABLE IF EXISTS diary")
            onCreate(db)
        } catch (e: SQLException) {
            Log.e(TAG, "Error upgrading table: ${e.message}")
        }
    }

    fun addEntry(title: String, content: String) {
        if (title.isEmpty() || content.isEmpty()) {
            Log.w(TAG, "Title or content cannot be empty.")
            return
        }

        try {
            val db = writableDatabase
            val values = ContentValues().apply {
                put("title", title)
                put("content", content)
            }
            db.insert("diary", null, values)
            db.close()
        } catch (e: SQLException) {
            Log.e(TAG, "Error inserting entry: ${e.message}")
        }
    }

    fun getAllEntries(): ArrayList<DiaryEntry> {
        val list = ArrayList<DiaryEntry>()
        try {
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT * FROM diary", null)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(0)
                    val title = cursor.getString(1)
                    val content = cursor.getString(2)
                    list.add(DiaryEntry(id, title, content))
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
        } catch (e: SQLException) {
            Log.e(TAG, "Error retrieving entries: ${e.message}")
        }
        return list
    }

    fun getEntry(id: Int): DiaryEntry? {
        var entry: DiaryEntry? = null
        try {
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT * FROM diary WHERE id = ?", arrayOf(id.toString()))
            if (cursor.moveToFirst()) {
                entry = DiaryEntry(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
            }
            cursor.close()
            db.close()
        } catch (e: SQLException) {
            Log.e(TAG, "Error retrieving entry: ${e.message}")
        }
        return entry
    }

    fun updateEntry(id: Int, title: String, content: String) {
        if (title.isEmpty() || content.isEmpty()) {
            Log.w(TAG, "Title or content cannot be empty.")
            return
        }

        try {
            val db = writableDatabase
            val values = ContentValues().apply {
                put("title", title)
                put("content", content)
            }
            db.update("diary", values, "id=?", arrayOf(id.toString()))
            db.close()
        } catch (e: SQLException) {
            Log.e(TAG, "Error updating entry: ${e.message}")
        }
    }

    fun deleteEntry(id: Int) {
        try {
            val db = writableDatabase
            db.delete("diary", "id=?", arrayOf(id.toString()))
            db.close()
        } catch (e: SQLException) {
            Log.e(TAG, "Error deleting entry: ${e.message}")
        }
    }
}
