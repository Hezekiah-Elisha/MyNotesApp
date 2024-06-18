package com.hub.notesapp.screens.note

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hub.notesapp.screens.home.HomeViewModel

@Composable
fun ViewNote(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Text(
        text = "View Note",
        modifier = modifier
    )
}