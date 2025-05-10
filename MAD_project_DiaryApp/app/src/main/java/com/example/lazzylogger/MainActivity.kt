package com.example.lazzylogger

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private var entries = ArrayList<DiaryEntry>()
    private lateinit var entryAdapter: EntryAdapter  // Declare the entryAdapter here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)  // Initialize db here

        recyclerView = findViewById(R.id.recyclerViewEntries)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addButton = findViewById(R.id.addButton)

        // Set up the add button
        addButton.setOnClickListener {
            startActivity(Intent(this, AddEntryActivity::class.java))
        }

        // Load entries from the database and set up the RecyclerView
        loadEntries()

        // Set up the item click listener to view an entry
        recyclerView.adapter = entryAdapter
    }

    private fun loadEntries() {
        entries = db.getAllEntries()  // Get the list of entries from the database

        // Initialize the adapter with edit and delete functionality
        entryAdapter = EntryAdapter(this, entries, ::editEntry, ::deleteEntry)

        // Set the adapter to RecyclerView
        recyclerView.adapter = entryAdapter
    }


    // Handle the edit click
    private fun editEntry(entry: DiaryEntry) {
        // Open AddEntryActivity to edit the entry
        val intent = Intent(this, AddEntryActivity::class.java)
        intent.putExtra("entryId", entry.id)
        intent.putExtra("entryTitle", entry.title)
        intent.putExtra("entryContent", entry.content)
        startActivity(intent)
    }

    // Handle the delete click
    private fun deleteEntry(entry: DiaryEntry) {
        db.deleteEntry(entry.id) // Delete from DB

        // Refresh the list of entries
        entries = db.getAllEntries()
        entryAdapter = EntryAdapter(this, entries, ::editEntry, ::deleteEntry)
        recyclerView.adapter = entryAdapter

        Toast.makeText(this, "Entry deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadEntries()
    }
}
