package com.hub.notesapp.screens.note

import com.hub.notesapp.model.Note


/**
 * A sealed class that represents the state of a note
 * This is used to represent the different states of a note
 * Loading - when the note is still loading
 * Success - when the note has been successfully loaded
 * Error - when there was an error loading the note
 */
sealed interface NoteState {
    object Loading : NoteState
    data class Success(val note: Note) : NoteState
    data class Error(val error: String) : NoteState
}