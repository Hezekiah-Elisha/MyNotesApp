package com.hub.notesapp.screens.home

import android.widget.HorizontalScrollView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import java.util.Locale.Category

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

//    val allNotes = viewModel.allNotes.collectAsState(initial = emptyList()).value
    val notesState by viewModel.notesState.collectAsState()
    val categories = viewModel.getAllCategories().collectAsState(initial = emptyList()).value

    when (notesState) {
        is NotesState.Error ->{
            Text(text = "Error")
        }
        is NotesState.Loading -> {
            Text(text = "Loading")
        }
        is NotesState.Success -> {
            Scaffold(
                modifier = modifier,
                containerColor = MaterialTheme.colorScheme.surface,
                topBar = { TopBar() },
                floatingActionButton = {
                    FloatingButton(navController, modifier)
                },
            ) {innerPadding->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    CategoriesSection(
                        modifier = modifier,
                        categories = categories
                    )
                    NotesList(
                        modifier = modifier
                            .fillMaxSize(),
                        notes = (notesState as NotesState.Success).notes,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun FloatingButton(
    navController: NavController,
    modifier: Modifier
) {
    ExtendedFloatingActionButton(
        onClick = {
            navController.navigate("CreateNote")
        }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_edit_square_24),
            contentDescription = stringResource(R.string.add_note),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Add Note",
            modifier = modifier.padding(start = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun NotesList(
    modifier: Modifier = Modifier,
    notes: List<Note>?,
    navController: NavController
) {
    if (notes!= null){
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(notes.size) { index ->
                NotesCard(note = notes[index], navController = navController)
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
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesCard(modifier: Modifier = Modifier, note: Note, navController: NavController) {
    Card(
        onClick = {
            navController.navigate("viewNote/noteId=${note.id}")
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(
            text = note.title.uppercase(),
            modifier = modifier.padding(start=16.dp, end=16.dp, top = 8.dp),
            style = MaterialTheme.typography.headlineMedium,
            fontStyle = MaterialTheme.typography.headlineMedium.fontStyle
        )
        Divider(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onPrimary,
            thickness = 1.dp
        )
        Text(
            text = if (note.content.length > Constants.CONTENT_PREVIEW_LENGTH) {
                "${note.content.take(Constants.CONTENT_PREVIEW_LENGTH)}..."
            } else {
                note.content
            },
            modifier = modifier.padding(start=16.dp, end=16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = getTimestampToDateTime(note.timestamp),
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.bodySmall
        )
        Chip(
            label=note.category,
            modifier = modifier.padding(16.dp)
        )
    }
}
fun getTimestampToDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
    val netDate = Date(timestamp)
    return sdf.format(netDate)
}

@Composable
fun Chip(modifier: Modifier = Modifier, label: String? = "Regular", shape: Shape = SuggestionChipDefaults.shape) {
    SuggestionChip(
        shape = shape,
        modifier = modifier,
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface
        ),
        border = null,
        onClick = { /*TODO*/ },
        label = {
            Text(
                text = label.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    )
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

@Composable
fun CategoriesSection(modifier: Modifier = Modifier, categories: List<String>) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(categories.size) { index ->
            Chip(
                shape = RoundedCornerShape(8.dp),
                modifier = modifier.padding(8.dp),
                label = categories[index]
            )
        }
    }
}