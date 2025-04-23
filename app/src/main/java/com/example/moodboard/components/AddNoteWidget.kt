package com.example.moodboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodboard.models.Moods
import com.example.moodboard.models.NoteModel
import java.time.LocalDateTime

@Composable
fun AddNoteWidget(
    titleHandler: String,
    textHandler: String,
    currentMood: Moods,
    onDismiss: () -> Unit,
    onConfirm: (NoteModel) -> Unit,
    onNoteTextValueChange: (String) -> Unit,
    onNoteTitleValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        NoteModel(
                            title = titleHandler,
                            content = textHandler,
                            date = LocalDateTime.now(),
                            mood = currentMood
                        )
                    )
                }
            ) {
                Text(
                    text = "Add"
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Cancel"
                )
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add a Note",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CustomTextField(
                    text = titleHandler,
                    placeholderText = "Note Title",
                    onValueChange = onNoteTitleValueChange,
                    limit = 30,
                    linesNum = 2,
                    modifier = Modifier.height(100.dp)
                )
                CustomTextField(
                    text = textHandler,
                    placeholderText = "Note Text",
                    onValueChange = onNoteTextValueChange,
                    linesNum = 5,
                    limit = 100,
                    modifier = Modifier.height(150.dp)
                )
            }
        }
    )
}
