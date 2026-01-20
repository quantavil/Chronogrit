package com.shub39.grit.core.tasks.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shub39.grit.core.tasks.domain.TaskPriority
import grit.shared.core.generated.resources.Res
import org.jetbrains.compose.resources.stringResource

@Composable
fun PrioritySelector(
    priority: TaskPriority,
    onPrioritySelected: (TaskPriority) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Since 'priority' string resource might not exist, I'll use a hardcoded string or add it later.
        // For now let's assume I can add it or just use "Priority"
        Text(
            text = "Priority", // Placeholder, ideally should be stringResource(Res.string.priority)
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TaskPriority.entries.filter { it != TaskPriority.NONE }.forEach { p ->
                FilterChip(
                    selected = priority == p,
                    onClick = { onPrioritySelected(p) },
                    label = { Text(p.name.lowercase().replaceFirstChar { it.uppercase() }) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(p.color).copy(alpha = 0.2f),
                        selectedLabelColor = Color(p.color),
                        selectedLeadingIconColor = Color(p.color)
                    ),
                    border = if (priority == p) {
                        FilterChipDefaults.filterChipBorder(
                            borderColor = Color(p.color),
                            selectedBorderColor = Color(p.color),
                            enabled = true,
                            selected = true
                        )
                    } else {
                        FilterChipDefaults.filterChipBorder(enabled = true, selected = false)
                    },
                    shape = CircleShape
                )
            }
        }
    }
}
