package com.example.moodboard.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.moodboard.models.Moods
import com.example.moodboard.models.NoteModel

class MoodViewModel(application: Application) : AndroidViewModel(application) {

    val moods = Moods.entries.map {
        it.toMoodModel()
    }

    private val sharedPref = application.getSharedPreferences("MoodPrefs", Context.MODE_PRIVATE)

    private var _currentMood by mutableStateOf(loadCurrentMood())

    val currentMood: Moods
        get() = _currentMood

    var selectedMood by mutableStateOf(_currentMood)
        private set

    private fun loadCurrentMood(): Moods {
        val moodName = sharedPref.getString("current_mood", Moods.HAPPY.name)
        return Moods.valueOf(moodName ?: Moods.HAPPY.name)
    }

    fun updateSelectedMood(mood: Moods) {
        selectedMood = mood
    }

    fun confirmMoodSelection() {
        _currentMood = selectedMood
        with(sharedPref.edit()) {
            putString("current_mood", _currentMood.name)
            apply()
        }
    }

    var isNoteWidgetOpen by mutableStateOf(false)
        private set

    private var _selectedNote = mutableStateOf<NoteModel?>(null)
    val selectedNote: MutableState<NoteModel?> = _selectedNote

    fun openNoteWidget(note: NoteModel) {
        _selectedNote.value = note
        isNoteWidgetOpen = true
    }

    fun closeNoteWidget() {
        isNoteWidgetOpen = false
    }

    var isMoodWidgetOpen by mutableStateOf(false)
        private set

    var isAddNoteWidgetOpen by mutableStateOf(false)
        private set

    fun openMoodWidget() {
        selectedMood = currentMood
        isMoodWidgetOpen = true
    }

    fun closeMoodWidget() {
        isMoodWidgetOpen = false
    }

    fun openAddNoteWidget() {
        isAddNoteWidgetOpen = true
    }

    fun closeAddNoteWidget() {
        isAddNoteWidgetOpen = false
    }

    var noteText by mutableStateOf("")

    fun onNoteTextChange(newText: String) {
        noteText = newText
    }

    var noteTitle by mutableStateOf("")

    fun onNoteTitleChange(newText: String) {
        noteTitle = newText
    }
}