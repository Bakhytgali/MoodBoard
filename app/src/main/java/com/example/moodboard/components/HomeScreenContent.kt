package com.example.moodboard.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodboard.viewmodels.FirebaseViewModel
import com.example.moodboard.viewmodels.MoodViewModel

@Composable
fun HomeScreenContent(
    viewModel: MoodViewModel,
    fbViewModel: FirebaseViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(
            rememberScrollState()
        )
            .fillMaxHeight()
            .fillMaxWidth(0.9f)
    ) {

        Spacer(Modifier.height(20.dp))
        MoodCard(
            chosenMood = viewModel.currentMood,
            openSetMood = { viewModel.openMoodWidget() },
            modifier = modifier
        )

        Spacer(Modifier.height(40.dp))
        MoodCalendar(modifier = modifier, fbViewModel = fbViewModel)

        Spacer(Modifier.height(40.dp))
        NotesList(viewModel = viewModel, fbViewModel = fbViewModel, modifier = modifier)

        if(viewModel.isMoodWidgetOpen) {
            SetMoodWidget(
                onConfirm = {
                    viewModel.confirmMoodSelection()
                    viewModel.closeMoodWidget()
                },
                onDismiss = {
                    viewModel.closeMoodWidget()
                },
                chosenMood = viewModel.selectedMood,
                viewModel = viewModel,
                onMoodChosen = {
                    viewModel.updateSelectedMood(it)
                }
            )
        }

        if(viewModel.isAddNoteWidgetOpen) {
            AddNoteWidget(
                textHandler = viewModel.noteText,
                titleHandler = viewModel.noteTitle,
                currentMood = viewModel.currentMood,
                onNoteTitleValueChange = {
                    viewModel.onNoteTitleChange(it)
                },
                onNoteTextValueChange = {
                    viewModel.onNoteTextChange(it)
                },
                onConfirm = {
                    fbViewModel.addNote(it)
                    viewModel.noteText = ""
                    viewModel.noteTitle = ""
                    viewModel.closeAddNoteWidget()
                },
                onDismiss = {
                    viewModel.noteText = ""
                    viewModel.noteTitle = ""
                    viewModel.closeAddNoteWidget()
                }
            )
        }
    }
}