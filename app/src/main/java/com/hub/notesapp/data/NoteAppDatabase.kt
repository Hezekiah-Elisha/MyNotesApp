package com.hub.notesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hub.notesapp.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteAppDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
}