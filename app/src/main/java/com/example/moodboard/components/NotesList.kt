package com.example.moodboard.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodboard.models.NoteModel
import com.example.moodboard.viewmodels.FirebaseViewModel
import com.example.moodboard.viewmodels.MoodViewModel
import java.time.format.DateTimeFormatter

@Composable
fun NotesList(
    fbViewModel: FirebaseViewModel,
    viewModel: MoodViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        fbViewModel.getNotes(fbViewModel.selectedDate)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Notes ✒\uFE0F ",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(20.dp))
        NotesDisplay(
            fbViewModel,
            viewModel,
            modifier
        )
    }

    if(viewModel.isNoteWidgetOpen) {
        NoteWidget(
            onDismiss = {
                viewModel.closeNoteWidget()
            },
            note = viewModel.selectedNote.value!!
        )
    }
}

@Composable
fun NotesDisplay(
    fbViewModel: FirebaseViewModel,
    viewModel: MoodViewModel,
    modifier: Modifier = Modifier
) {
    val notes by fbViewModel.notes.collectAsState()
    Log.d("My Log", notes.toString())

    val isLoading by fbViewModel.isLoading.collectAsState()

    if (!isLoading) {
        if(notes.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(notes) { note ->
                    NoteCard(
                        note = note,
                        modifier = modifier.clickable {
                            viewModel.openNoteWidget(note)
                        }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Notes For Chosen Day.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Spacer(Modifier.height(30.dp))
            CircularProgressIndicator()
        }
    }
}

@Composable
fun NoteCard(
    note: NoteModel,
    modifier: Modifier = Modifier
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val time = note.date.format(timeFormatter)

    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}