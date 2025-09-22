package org.example.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch
import org.example.app.model.Note
import org.example.app.repository.NoteRepository

/**
 * PUBLIC_INTERFACE
 * NoteViewModel
 * Exposes notes LiveData and CRUD operations to the UI.
 */
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = NoteRepository(application)

    val notes = repo.allNotes

    fun getById(id: Long) = repo.getById(id)

    fun getByIdOnce(id: Long): Note? = runBlocking(Dispatchers.IO) {
        repo.getByIdOnce(id)
    }

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.delete(note)
    }
}
