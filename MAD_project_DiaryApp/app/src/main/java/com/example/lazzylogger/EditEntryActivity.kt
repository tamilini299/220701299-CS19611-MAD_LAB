package com.example.lazzylogger

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditEntryActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var contentInput: EditText
    private lateinit var saveButton: Button
    private lateinit var db: DatabaseHelper
    private var entryId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_entry)

        db = DatabaseHelper(this)
        titleInput = findViewById(R.id.editTextTitle)
        contentInput = findViewById(R.id.editTextContent)
        saveButton = findViewById(R.id.buttonSave)

        entryId = intent.getIntExtra("id", -1)
        val entry = db.getEntry(entryId)
        titleInput.setText(entry?.title)
        contentInput.setText(entry?.content)

        saveButton.setOnClickListener {
            db.updateEntry(entryId, titleInput.text.toString(), contentInput.text.toString())
            finish()
        }
    }
}
