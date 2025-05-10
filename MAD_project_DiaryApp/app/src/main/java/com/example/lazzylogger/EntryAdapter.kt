package com.example.lazzylogger


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryAdapter(
    private val context: Context,
    private val entries: List<DiaryEntry>,
    private val onEditClick: (DiaryEntry) -> Unit,
    private val onDeleteClick: (DiaryEntry) -> Unit
) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

    // Create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_entry, parent, false)
        return EntryViewHolder(view)
    }

    // Bind the data to the ViewHolder
    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]
        holder.titleTextView.text = entry.title
        holder.contentTextView.text = entry.content

        // Edit button click
        holder.editButton.setOnClickListener {
            onEditClick(entry)
        }

        // Delete button click
        holder.deleteButton.setOnClickListener {
            onDeleteClick(entry)
        }
    }

    override fun getItemCount() = entries.size

    // ViewHolder class to hold references to the views in each item
    class EntryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.entryTitle)
        val contentTextView: TextView = view.findViewById(R.id.entryContent)
        val editButton: Button = view.findViewById(R.id.buttonEdit)
        val deleteButton: Button = view.findViewById(R.id.buttonDelete)
    }
}
