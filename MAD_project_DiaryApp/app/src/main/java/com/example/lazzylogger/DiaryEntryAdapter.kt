package com.example.lazzylogger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryEntryAdapter(private val context: Context, private val entries: ArrayList<DiaryEntry>) :
    RecyclerView.Adapter<DiaryEntryAdapter.EntryViewHolder>() {

    // Create the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_entry, parent, false)
        return EntryViewHolder(itemView)
    }

    // Bind data to the views
    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]
        holder.title.text = entry.title
    }

    // Return the number of entries
    override fun getItemCount(): Int {
        return entries.size
    }

    // ViewHolder class to hold individual entry items
    class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textViewTitle)
    }
}
