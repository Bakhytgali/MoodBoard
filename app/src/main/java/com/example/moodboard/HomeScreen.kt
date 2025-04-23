package com.example.moodboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moodboard.components.HomeScreenContent
import com.example.moodboard.viewmodels.FirebaseViewModel
import com.example.moodboard.viewmodels.MoodViewModel

@Composable
fun HomeScreen(
    viewModel: MoodViewModel,
    fbViewModel: FirebaseViewModel,
    modifier: Modifier = Modifier
) {
    HomeScreenContent(viewModel, fbViewModel, modifier = modifier)
}