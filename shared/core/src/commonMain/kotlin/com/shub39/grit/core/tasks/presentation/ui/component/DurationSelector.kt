package com.shub39.grit.core.tasks.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DurationSelector(
    selectedDuration: Long,
    onDurationSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val durations = listOf(15L, 30L, 45L, 60L, 90L, 120L)

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(durations) { duration ->
            val label = if (duration < 60) "${duration}m" else "${duration / 60}h" + if (duration % 60 > 0) " ${duration % 60}m" else ""
            
            FilterChip(
                selected = selectedDuration == duration,
                onClick = { onDurationSelected(duration) },
                label = { Text(label) }
            )
        }
    }
}
