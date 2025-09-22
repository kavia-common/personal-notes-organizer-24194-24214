package org.example.app.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.model.Note

/**
 * PUBLIC_INTERFACE
 * NotesAdapter
 * Recycler adapter that renders each note in a minimalist card style.
 */
class NotesAdapter(
    private val onClick: (Note) -> Unit,
    private val onLongClick: (Note) -> Boolean
) : ListAdapter<Note, NotesAdapter.NoteVH>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteVH(view)
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.txtTitle)
        private val content: TextView = itemView.findViewById(R.id.txtContent)

        fun bind(note: Note) {
            title.text = if (note.title.isNullOrBlank()) "Untitled" else note.title
            content.text = note.content ?: ""
            itemView.setOnClickListener { onClick(note) }
            itemView.setOnLongClickListener { onLongClick(note) }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
        }
    }
}
