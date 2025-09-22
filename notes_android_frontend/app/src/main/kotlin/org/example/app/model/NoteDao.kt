package org.example.app.model

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * PUBLIC_INTERFACE
 * NoteDao
 * Data access object for CRUD operations on notes.
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    fun getById(id: Long): LiveData<Note?>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getByIdOnce(id: Long): Note?

    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}
