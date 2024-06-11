package com.hub.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hub.notesapp.screens.home.HomeScreen
import com.hub.notesapp.screens.home.HomeViewModel
import com.hub.notesapp.screens.note.CreateNoteScreen

@Composable
fun NoteNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable("createNote") {
             CreateNoteScreen()
        }
    }
}