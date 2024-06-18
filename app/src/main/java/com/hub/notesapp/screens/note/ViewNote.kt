package com.hub.notesapp.screens.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hub.notesapp.R
import com.hub.notesapp.screens.home.Chip

@Composable
fun ViewNote(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel(),
    noteId: String?
) {

    val noteState = viewModel.noteState

    LaunchedEffect(key1 = true) {
        viewModel.getNoteById(noteId?.toInt() ?: 0)
    }

    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = "View Note",
                back = {
                    navController.popBackStack()
                },
                deleteNote = {
                    // Delete note
                    viewModel.deleteNote(noteId?.toInt() ?: 0)
                }
            )
        },
    ){innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ){
            when(noteState){
                is NoteState.Loading -> {
                    LinearProgressIndicator()
                }
                is NoteState.Success -> {
                    val note = noteState.note
                    TopTitle(
                        title = note.title,
                        category = note.category
                    )
                    Text(
                        text = note.content,
                        modifier = Modifier.padding(8.dp),
                    )
                }
                is NoteState.Error -> {
                    val error = noteState.error
                    Text(text = error)
                }
            }
        }
    }
}

@Composable
fun TopTitle(modifier: Modifier = Modifier, title: String, category: String){
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            8.dp
        )
    ){
        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = title.uppercase(),
                modifier = modifier.padding(8.dp),
                fontSize = 24.sp
            )
            Chip(
                label = category,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    deleteNote: () -> Unit = {},
    back: () -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) } // Add this line

    TopAppBar(
        title = {
            Text(
                text = title.uppercase(),
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth(),
                fontSize = 24.sp
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = "Go Back",
                modifier = modifier
                    .padding(8.dp)
                    .clickable {
                        back()
                    },
            )
        },
        actions = {
            // Add actions here
                Icon(
                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                contentDescription = "More",
                modifier = modifier
                    .padding(8.dp)
                    .clickable {
                        showMenu = true
                    },
            )
            DropDown(
                showMenu = showMenu,
                onDismissRequest = { showMenu = false },
                deleteNote = {
                    deleteNote()
                    back()
                }
            )
        }
    )
}

@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    showMenu: Boolean,
    onDismissRequest: () -> Unit = {},
    deleteNote: () -> Unit = {}
) {

    DropdownMenu( // Add this block
        modifier = modifier.width(200.dp),
        expanded = showMenu,
        onDismissRequest = { onDismissRequest() }
    ) {
        DropdownMenuItem(
            onClick = { /* Handle option 1 click */ },
            text={
                Text("Edit Note")
            }
        )
        DropdownMenuItem(
            onClick = { deleteNote() /* Handle Delete note click */},
            text={Text("Delete Note")}
        )
        // Add more DropdownMenuItem for more options
    }
}