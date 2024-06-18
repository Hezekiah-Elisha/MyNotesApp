package com.hub.notesapp.screens.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.notesapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    var noteState : NoteState by mutableStateOf(NoteState.Loading)
        private set

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            noteState = NoteState.Loading
            try {
                val note = repository.getNoteById(id)
                noteState = NoteState.Success(note)
            } catch (e: Exception) {
                noteState = NoteState.Error(e.message.toString())
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch (Dispatchers.IO){
            val note = repository.getNoteById(id)
            repository.delete(note)
        }
    }
}