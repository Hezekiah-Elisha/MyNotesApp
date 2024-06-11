package com.hub.notesapp.di

import android.content.Context
import androidx.room.Room
import com.hub.notesapp.data.NoteAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDao(noteAppDatabase: NoteAppDatabase) = noteAppDatabase.noteDao()

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context):NoteAppDatabase
        = Room.databaseBuilder(
            context,
            NoteAppDatabase::class.java,
            name="note_database"
        ).fallbackToDestructiveMigration()
            .build()

}