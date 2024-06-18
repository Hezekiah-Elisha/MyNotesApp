package com.hub.notesapp.repository

import com.hub.notesapp.data.NoteDao
import com.hub.notesapp.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
){
    suspend fun addNote(note: Note) = noteDao.insert(note)
    suspend fun deleteAll()  = noteDao.deleteAll()
    suspend fun update(note: Note) = noteDao.update(note)
    suspend fun delete(note: Note) = noteDao.delete(note)
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()
    fun categories(): Flow<List<String>> = noteDao.getAllCategories()
}