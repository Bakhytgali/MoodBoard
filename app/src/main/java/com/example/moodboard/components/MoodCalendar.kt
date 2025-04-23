package com.example.moodboard.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moodboard.viewmodels.FirebaseViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MoodCalendar(
    fbViewModel: FirebaseViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 15.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calendar \uD83D\uDCC6",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(20.dp))
        WeeklyCalendar(
            onDateSelected = {
                fbViewModel.onDateSelected(it)
            },
            fbViewModel = fbViewModel,
        )
    }
}

@Composable
fun WeeklyCalendar(
    onDateSelected: (LocalDate) -> Unit,
    fbViewModel: FirebaseViewModel,
) {
    val today = LocalDate.now()
    val week = (-3..3).map {
        today.plusDays(it.toLong())
    }
    val month = today.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = month,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            week.forEach { day ->
                DayItem(
                    day = day,
                    isToday = day == today,
                    onDateSelected = onDateSelected,
                    isSelected = fbViewModel.selectedDate == day
                )

            }
        }
    }
}

@Composable
fun DayItem(
    onDateSelected: (LocalDate) -> Unit,
    day: LocalDate,
    isToday: Boolean,
    isSelected: Boolean
) {
    val dayFormatted = DateTimeFormatter.ofPattern("dd")
    val dayText = day.format(dayFormatted)

    val backgroundColor = when {
        isToday && isSelected -> MaterialTheme.colorScheme.primary
        isToday -> MaterialTheme.colorScheme.primaryContainer
        isSelected -> MaterialTheme.colorScheme.primary
        else -> Color.Transparent
    }

    val contentColor = when {
        isSelected -> MaterialTheme.colorScheme.background
        isToday -> MaterialTheme.colorScheme.onBackground
        else -> MaterialTheme.colorScheme.onBackground
    }

    val borderColor = when {
        isSelected && isToday -> MaterialTheme.colorScheme.primary
        isSelected -> MaterialTheme.colorScheme.primary
        isToday -> MaterialTheme.colorScheme.primary
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = CircleShape
            )
            .clickable {
                Log.d("My Log", "$day --- $isSelected}")
                onDateSelected(day)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayText,
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor
        )
    }
}