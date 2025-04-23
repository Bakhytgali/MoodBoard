package com.example.moodboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodboard.components.CustomBottomBar
import com.example.moodboard.viewmodels.FirebaseViewModel
import com.example.moodboard.viewmodels.MoodViewModel

@Composable
fun AppScreen(modifier: Modifier = Modifier) {
    val viewModel: MoodViewModel = viewModel()
    val fbViewModel: FirebaseViewModel = viewModel()

    Scaffold(
        bottomBar = {
            CustomBottomBar(viewModel)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeScreen(viewModel, fbViewModel, modifier = modifier)
        }
    }
}