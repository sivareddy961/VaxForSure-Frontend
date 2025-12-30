package com.example.vaxforsure.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MedicalCalendar() {
    val calendar = remember { Calendar.getInstance() }
    val selectedDate = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }
    val currentMonth = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val currentYear = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    
    val monthName = remember(currentMonth.value) {
        SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(
            calendar.apply {
                set(Calendar.MONTH, currentMonth.value)
                set(Calendar.YEAR, currentYear.value)
            }.time
        )
    }
    
    val daysInMonth = remember(currentMonth.value, currentYear.value) {
        calendar.apply {
            set(Calendar.MONTH, currentMonth.value)
            set(Calendar.YEAR, currentYear.value)
            set(Calendar.DAY_OF_MONTH, 1)
        }.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
    
    val firstDayOfWeek = remember(currentMonth.value, currentYear.value) {
        calendar.apply {
            set(Calendar.MONTH, currentMonth.value)
            set(Calendar.YEAR, currentYear.value)
            set(Calendar.DAY_OF_MONTH, 1)
        }.get(Calendar.DAY_OF_WEEK) - 1 // Sunday = 0
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFE0F7FA),
                            Color(0xFFF5FAFA)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            // Calendar Header with Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = "Calendar",
                        tint = Color(0xFF00BFA5),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = monthName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00897B)
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (currentMonth.value == 0) {
                                currentMonth.value = 11
                                currentYear.value--
                            } else {
                                currentMonth.value--
                            }
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFF00BFA5).copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous",
                            tint = Color(0xFF00BFA5),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = {
                            if (currentMonth.value == 11) {
                                currentMonth.value = 0
                                currentYear.value++
                            } else {
                                currentMonth.value++
                            }
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFF00BFA5).copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next",
                            tint = Color(0xFF00BFA5),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Day Names Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                    Text(
                        text = day,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF00897B),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Calendar Grid
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var dayCounter = 1
                var weekComplete = false
                
                while (dayCounter <= daysInMonth) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (dayOfWeek in 0..6) {
                            if (dayCounter == 1 && dayOfWeek < firstDayOfWeek) {
                                // Empty space before first day
                                Spacer(modifier = Modifier.weight(1f))
                            } else if (dayCounter <= daysInMonth) {
                                val day = dayCounter++
                                val isSelected = day == selectedDate.value &&
                                        calendar.get(Calendar.MONTH) == currentMonth.value &&
                                        calendar.get(Calendar.YEAR) == currentYear.value
                                val isToday = day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) &&
                                        currentMonth.value == Calendar.getInstance().get(Calendar.MONTH) &&
                                        currentYear.value == Calendar.getInstance().get(Calendar.YEAR)
                                
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .clickable { selectedDate.value = day }
                                        .then(
                                            if (isSelected) {
                                                Modifier
                                                    .background(Color(0xFF00BFA5), CircleShape)
                                            } else if (isToday) {
                                                Modifier
                                                    .border(2.dp, Color(0xFF00BFA5), CircleShape)
                                            } else {
                                                Modifier
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day.toString(),
                                        fontSize = 14.sp,
                                        fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                                        color = when {
                                            isSelected -> Color.White
                                            isToday -> Color(0xFF00BFA5)
                                            dayOfWeek == 0 || dayOfWeek == 6 -> Color(0xFF757575)
                                            else -> Color(0xFF212121)
                                        },
                                        textAlign = TextAlign.Center
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Medical Theme Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFF00BFA5), CircleShape)
                    )
                    Text(
                        text = "Selected",
                        fontSize = 11.sp,
                        color = Color(0xFF757575)
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .border(2.dp, Color(0xFF00BFA5), CircleShape)
                    )
                    Text(
                        text = "Today",
                        fontSize = 11.sp,
                        color = Color(0xFF757575)
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        Icons.Default.MedicalServices,
                        contentDescription = null,
                        tint = Color(0xFF00BFA5),
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "Vaccine Due",
                        fontSize = 11.sp,
                        color = Color(0xFF757575)
                    )
                }
            }
        }
    }
}

