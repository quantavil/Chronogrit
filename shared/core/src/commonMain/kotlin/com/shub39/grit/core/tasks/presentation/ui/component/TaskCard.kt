package com.shub39.grit.core.tasks.presentation.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shub39.grit.core.tasks.domain.Task
import com.shub39.grit.core.utils.toFormattedString

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3ExpressiveApi::class,)
@Composable
fun TaskCard(
    task: Task,
    dragState: Boolean = false,
    reorderIcon: @Composable () -> Unit,
    is24Hr: Boolean,
    modifier: Modifier
) {
    val cardContent by animateColorAsState(
        targetValue = when (task.status) {
            true -> MaterialTheme.colorScheme.onSurface
            else -> MaterialTheme.colorScheme.onSecondaryContainer
        },
        label = "cardContent"
    )
    val cardContainer by animateColorAsState(
        targetValue = when (task.status) {
            true -> MaterialTheme.colorScheme.surfaceContainerHighest
            else -> MaterialTheme.colorScheme.secondaryContainer
        },
        label = "cardContainer"
    )
    val cardColors = CardDefaults.cardColors(
        containerColor = cardContainer,
        contentColor = cardContent
    )

    Card(
        modifier = modifier.animateContentSize(),
        colors = cardColors,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (task.priority != com.shub39.grit.core.tasks.domain.TaskPriority.NONE) {
                Box(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(width = 4.dp, height = 32.dp)
                        .background(
                            Color(task.priority.color),
                            RoundedCornerShape(4.dp)
                        )
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textDecoration = if (task.status) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    },
                )

                if (task.reminder != null || task.startTime != null) {
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Reminder
                        if (task.reminder != null) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Notifications,
                                    contentDescription = "Reminder",
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = task.reminder.toFormattedString(is24Hr),
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp)
                                )
                            }
                        }

                        // Time & Duration
                        if (!task.isAllDayTask()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Schedule,
                                    contentDescription = "Time",
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = task.getFormattedTime(is24Hr),
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp)
                                )
                            }
                        } else {
                            Text(
                                text = "All Day",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp)
                            )
                        }
                    }
                }
                
                // Duration & Repeat Info Row
                if (!task.isAllDayTask() || task.isRepeated) {
                    Row(
                        modifier = Modifier.padding(top = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (!task.isAllDayTask()) {
                             Text(
                                text = task.getFormattedDuration(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            )
                        }
                        
                        if (task.isRepeated) {
                             Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Refresh,
                                    contentDescription = "Repeat",
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = task.getWeekDaysTitle(),
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp)
                                )
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = dragState,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                reorderIcon()
            }
        }
    }
}