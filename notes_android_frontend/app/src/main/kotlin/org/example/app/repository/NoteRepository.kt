package org.example.app.repository

import android.content.Context
import androidx.lifecycle.LiveData
import org.example.app.model.Note
import org.example.app.model.NotesDatabase

/**
 * PUBLIC_INTERFACE
 * NoteRepository
 * Simple repository wrapping Room DAO.
 */
class NoteRepository(context: Context) {
    private val dao = NotesDatabase.getInstance(context).noteDao()

    val allNotes: LiveData<List<Note>> = dao.getAll()

    fun getById(id: Long) = dao.getById(id)

    suspend fun getByIdOnce(id: Long) = dao.getByIdOnce(id)

    suspend fun insert(note: Note) = dao.insert(note)

    suspend fun update(note: Note) = dao.update(note)

    suspend fun delete(note: Note) = dao.delete(note)
}
