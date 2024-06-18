package com.hub.notesapp.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.notesapp.model.Note
import com.hub.notesapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

//    use flow
    val allNotes: Flow<List<Note>> = repository.getAllNotes()

    private val _notesState = MutableStateFlow<NotesState>(NotesState.Loading)
    val notesState: StateFlow<NotesState> = _notesState

    init {
        getAllNotes()
    }

    private fun getAllNotes() = viewModelScope.launch {
        repository.getAllNotes().collect { notes ->
            _notesState.value = NotesState.Success(notes)
        }
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun addNote(note: Note) = viewModelScope.launch {
        repository.addNote(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }


    fun getAllCategories(): Flow<List<String>> = repository.categories()
}
