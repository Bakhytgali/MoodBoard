package com.example.moodboard.models

import androidx.compose.ui.graphics.Color

data class MoodModel(
    val name: String,
    val emoji: String,
    val tint: Color,
    val type: Moods
)
