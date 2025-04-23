package com.example.moodboard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.moodboard.viewmodels.MoodViewModel

@Composable
fun CustomBottomBar(
    viewModel: MoodViewModel,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        actions = {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AddNoteButton(
                    onClick = {
                        viewModel.openAddNoteWidget()
                    }
                )
            }
        },
        containerColor = Color.Transparent
    )
}