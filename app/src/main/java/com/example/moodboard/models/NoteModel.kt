package com.example.moodboard.models

import java.time.LocalDateTime

data class NoteModel(
    val title: String,
    val content: String,
    val date: LocalDateTime,
    val mood: Moods? = null,
)
