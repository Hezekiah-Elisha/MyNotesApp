package com.hub.notesapp.utils

import com.hub.notesapp.model.Note

object Constants {
    val notes = listOf(
        Note(id = 1, title = "Note 1", content = "Content 1", timestamp=System.currentTimeMillis()),
        Note(id = 2, title="Note 2", content= "Content 2", timestamp=System.currentTimeMillis()),
        Note(id = 3, title="Note 3", content="Content 3", timestamp=System.currentTimeMillis()),
        Note(id = 4, title="Note 4", content="Content 4", timestamp=System.currentTimeMillis()),
        Note(id = 5, title="Note 5", content="Content 5", timestamp=System.currentTimeMillis()),
        Note(id = 6, title="Note 6", content="Content 6", timestamp=System.currentTimeMillis()),
        Note(id = 7, title="Note 7", content="Content 7", timestamp=System.currentTimeMillis()),
    )
    const val CONTENT_PREVIEW_LENGTH = 100
}