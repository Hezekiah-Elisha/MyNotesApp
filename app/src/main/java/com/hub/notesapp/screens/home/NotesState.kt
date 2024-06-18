package com.hub.notesapp.screens.home

import com.hub.notesapp.model.Note

sealed interface NotesState {
    object Loading : NotesState
    data class Success(val notes: List<Note>) : NotesState
    data class Error(val message: String) : NotesState
}