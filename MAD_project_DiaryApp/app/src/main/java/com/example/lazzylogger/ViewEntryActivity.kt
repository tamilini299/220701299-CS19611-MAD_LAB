package com.example.lazzylogger

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ViewEntryActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var contentInput: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var db: DatabaseHelper

    private var entryId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_entry) // Make sure this XML exists

        db = DatabaseHelper(this)

        titleInput = findViewById(R.id.textViewTitle)
        contentInput = findViewById(R.id.textViewContent)
        updateButton = findViewById(R.id.buttonEdit)
        deleteButton = findViewById(R.id.deleteButton)

        entryId = intent.getIntExtra("id", -1)

        if (entryId != -1) {
            loadEntry(entryId)
        } else {
            Toast.makeText(this, "Entry not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        updateButton.setOnClickListener {
            val newTitle = titleInput.text.toString().trim()
            val newContent = contentInput.text.toString().trim()

            if (newTitle.isNotEmpty() && newContent.isNotEmpty()) {
                db.updateEntry(entryId, newTitle, newContent)
                Toast.makeText(this, "Entry Updated", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please fill both fields", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            db.deleteEntry(entryId)
            Toast.makeText(this, "Entry Deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadEntry(id: Int) {
        val entry = db.getEntry(id)
        if (entry != null) {
            titleInput.setText(entry.title)
            contentInput.setText(entry.content)
        } else {
            Toast.makeText(this, "Entry not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
