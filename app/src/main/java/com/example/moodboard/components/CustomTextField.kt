package com.example.moodboard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    text: String,
    placeholderText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    limit: Int,
    linesNum: Int = 1,
) {
    Box(contentAlignment = Alignment.Center) {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            onValueChange = {
                if(it.length <= limit) {
                    onValueChange(it)
                }
            },
            maxLines = linesNum,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = MaterialTheme.typography.bodySmall,
                )
            },
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.6f),
            )
        )
        Text(
            text = "${text.length}/$limit",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp)
        )
    }
}