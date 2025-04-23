package com.example.moodboard.models

import androidx.compose.ui.graphics.Color

enum class Moods(private val emoji: String, private val color: Color) {
    HAPPY("😊", Color(0xFFFFEB3B)),
    SAD("😢", Color(0xFF90CAF9)),
    ANGRY("😡", Color(0xFFE57373)),
    RELAXED("😌", Color(0xFFA5D6A7)),
    EXCITED("🤩", Color(0xFFFFC107)),
    BORED("\uD83E\uDD71", Color(0xFFBFD7EA)),
    TIRED("\uD83D\uDE29", Color(0xFFE0E0E0)),
    CALM("🧘", Color(0xFFB2DFDB)),
    CONFIDENT("😎", Color(0xFFFFF176)),
    LONELY("😔", Color(0xFFB0BEC5)),
    IN_LOVE("😍", Color(0xFFFFCDD2));

    fun toMoodModel(): MoodModel {
        return MoodModel(
            name = name,
            emoji = emoji,
            tint = color,
            type = this
        )
    }

    fun getEmoji() = emoji
    fun getMoodText() = name
    fun getColor() = color
}