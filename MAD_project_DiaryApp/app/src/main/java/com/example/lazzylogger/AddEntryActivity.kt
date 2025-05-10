package com.example.lazzylogger

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEntryActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var contentInput: EditText
    private lateinit var saveButton: Button
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_entry)

        db = DatabaseHelper(this)
        titleInput = findViewById(R.id.editTextTitle)
        contentInput = findViewById(R.id.editTextContent)
        saveButton = findViewById(R.id.buttonSave)

        saveButton.setOnClickListener {
            db.addEntry(titleInput.text.toString(), contentInput.text.toString())
            finish()
        }
    }
}
