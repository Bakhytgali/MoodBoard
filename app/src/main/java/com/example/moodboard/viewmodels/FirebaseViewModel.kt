package com.example.moodboard.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.moodboard.models.Moods
import com.example.moodboard.models.NoteModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class FirebaseViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _notes = MutableStateFlow<List<NoteModel>>(emptyList())
    val notes: StateFlow<List<NoteModel>> = _notes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    var selectedDate by mutableStateOf(LocalDate.now())
        private set

    fun onDateSelected(date: LocalDate) {
        selectedDate = date
        getNotes(date)
    }

    fun addNote(note: NoteModel) {
        val noteMap = hashMapOf(
            "title" to note.title,
            "content" to note.content,
            "date" to FieldValue.serverTimestamp(),
            "mood" to note.mood?.name
        )

        db.collection("notes")
            .add(noteMap)
            .addOnSuccessListener {
                getNotes(selectedDate)
            }
    }

    fun getNotes(chosenDate: LocalDate) {
        _isLoading.value = true

        val startOfDay = chosenDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val endOfDay = chosenDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()

        val startTimestamp = com.google.firebase.Timestamp(startOfDay.epochSecond, startOfDay.nano)
        val endTimestamp = com.google.firebase.Timestamp(endOfDay.epochSecond, endOfDay.nano)

        db.collection("notes")
            .whereGreaterThanOrEqualTo("date", startTimestamp)
            .whereLessThan("date", endTimestamp)
            .get()
            .addOnSuccessListener { result ->
                val noteList = result.mapNotNull { document ->
                    val title = document.getString("title") ?: return@mapNotNull null
                    val content = document.getString("content") ?: return@mapNotNull null
                    val timestamp = document.getTimestamp("date") ?: return@mapNotNull null
                    val moodString = document.getString("mood")
                    val mood = moodString?.let {
                        Moods.valueOf(it)
                    }

                    val date = timestamp.toDate().toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDateTime()
                        ?: LocalDateTime.now()

                    NoteModel(title, content, date, mood)
                }

                _notes.value = noteList
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }
}