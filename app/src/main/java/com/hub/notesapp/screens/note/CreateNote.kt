package com.hub.notesapp.screens.note

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hub.notesapp.model.Note
import com.hub.notesapp.screens.home.HomeViewModel
import com.hub.notesapp.ui.theme.NotesAppTheme

private const val TAG = "CreateNoteScreen"

@Composable
fun CreateNoteScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    CreateNoteScreenContent(modifier = modifier){title, content, category ->
        Log.d(TAG, "CreateNoteScreen: title=$title, content=$content, category=$category")
        val note = Note(
            title = title,
            content = content,
            category = category,
            timestamp = System.currentTimeMillis(),
        )
        viewModel.addNote(note)
        navController.popBackStack()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreenContent(
    modifier: Modifier = Modifier,
    onDone: (String, String, String) -> Unit = { title, content, category -> }
) {
    val title = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }

    val enabled = title.value.trim().isNotBlank() && content.value.trim().isNotBlank()
    val error = remember { mutableStateOf("") }

    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ){
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Create Note",
                    textAlign = TextAlign.Center,
                )
            }
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InputField(
                    valueState = title,
                    labelName = "Title"
                )
                InputField(
                    valueState = category,
                    labelName = "Category"
                )
                InputField(
                    valueState = content,
                    labelName = "Content",
                    isSingleLine = false
                )

                if (error.value.isNotEmpty()) {
                    Text(
                        text = error.value,
                        color = MaterialTheme.colorScheme.error,
                        modifier = modifier.fillMaxWidth()
                    )

                }
            }
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (title.value.isEmpty() || content.value.isEmpty()){
                            error.value = "Please fill in all fields***"
                            return@Button
                        } else {
                            onDone(title.value, content.value, category.value)
                        }
                    },
                    enabled = enabled,

                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}


@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState : MutableState<String>,
    labelName: String = "Title",
    isSingleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
//    var text by remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(labelName) },
        singleLine = isSingleLine,
        maxLines = if (isSingleLine) 1 else 5,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAmazingInputField() {
    NotesAppTheme {
        CreateNoteScreenContent()
    }
}
