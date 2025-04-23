package com.example.moodboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moodboard.models.MoodModel
import com.example.moodboard.models.Moods
import com.example.moodboard.viewmodels.MoodViewModel

@Composable
fun SetMoodWidget(
    chosenMood: Moods,
    onDismiss: () -> Unit,
    viewModel: MoodViewModel,
    onMoodChosen: (Moods) -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(
                text = "Set Mood",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = "Set",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        text = {
            MoodWidgetList(
                viewModel,
                chosenMood,
                onMoodChosen
            )
        }
    )
}

@Composable
fun MoodWidgetList(
    viewModel: MoodViewModel,
    chosenMood: Moods,
    onMoodsChosen: (Moods) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.height(400.dp)
    ) {
        items(viewModel.moods.size) {
            val mood = viewModel.moods[it]
            MoodWidgetCard(
                mood,
                onChosen = onMoodsChosen,
                isChosen = mood.type == chosenMood
            )
        }
    }
}

@Composable
fun MoodWidgetCard(
    mood: MoodModel,
    isChosen: Boolean,
    onChosen: (Moods) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = mood.tint
        ),
        modifier = modifier
            .height(100.dp)
            .clickable {
                onChosen(mood.type)
            }
    ) {
        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            if(isChosen) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Chosen Mood",
                    tint = MaterialTheme.colorScheme.onBackground.copy(0.6f),
                    modifier = modifier.size(25.dp).align(Alignment.TopEnd)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    text = mood.emoji,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = mood.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}