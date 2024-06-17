package com.hub.notesapp.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hub.notesapp.R
import com.hub.notesapp.model.Note
import com.hub.notesapp.ui.theme.NotesAppTheme
import com.hub.notesapp.utils.Constants
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val allNotes = viewModel.allNotes.collectAsState(initial = emptyList()).value

    Scaffold(
        topBar = { TopBar() },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate("CreateNote")
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_square_24),
                    contentDescription = "Add Note",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Add Note",
                    modifier = modifier.padding(start = 8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        
    ) {innerPadding->
        NotesList(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            notes = allNotes
        )
    }
}

@Composable
fun NotesList(
    modifier: Modifier = Modifier,
    notes: List<Note>?
) {
    if (notes!= null){
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(notes.size) { index ->
                NotesCard(note = notes[index])
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No notes available",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(16.dp).fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesCard(modifier: Modifier = Modifier, note: Note) {
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = note.title,
            modifier = modifier.padding(start=16.dp, end=16.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = note.content,
            modifier = modifier.padding(start=16.dp, end=16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = getTimestampToDateTime(note.timestamp),
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
fun getTimestampToDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
    val netDate = Date(timestamp)
    return sdf.format(netDate)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            scrolledContainerColor = MaterialTheme.colorScheme.onSecondary,
        ),
        title = {
            Text(
                text = "Notes",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = modifier.fillMaxWidth()
            )
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//private fun HomeScreenPreview() {
//    NotesAppTheme {
//        HomeScreen()
//    }
//}