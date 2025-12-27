package com.example.vaxforsure.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/* Data Model */
data class OnboardingItem(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val color: Color
)

@Composable
fun OnboardingScreen(
    onRegister: () -> Unit
) {

    /* Onboarding Data (same as React) */
    val onboardingData = listOf(
        OnboardingItem(
            icon = Icons.Filled.CalendarToday,
            title = "Track Vaccines",
            description = "Easily manage and track your child's vaccination schedule based on government-approved guidelines.",
            color = Color(0xFF4DB6AC)
        ),
        OnboardingItem(
            icon = Icons.Filled.Notifications,
            title = "Smart Reminders",
            description = "Never miss a vaccination date with intelligent reminders and notifications sent directly to your phone.",
            color = Color(0xFF81C784)
        ),
        OnboardingItem(
            icon = Icons.Filled.Shield,
            title = "Secure Digital Records",
            description = "Keep all vaccination records in one secure place. Export and share PDFs with healthcare providers anytime.",
            color = Color(0xFF4DB6AC)
        )
    )

    var currentStep by remember { mutableStateOf(0) }
    val current = onboardingData[currentStep]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        /* Skip Button */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Skip",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.clickable { onRegister() }
            )
        }

        /* Content */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            /* Icon Card */
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .shadow(20.dp, RoundedCornerShape(24.dp))
                    .background(current.color, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = current.icon,
                    contentDescription = current.title,
                    tint = Color.White,
                    modifier = Modifier.size(96.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            /* Title */
            Text(
                text = current.title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            /* Description */
            Text(
                text = current.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            /* Dots Indicator */
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                onboardingData.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .width(if (index == currentStep) 32.dp else 8.dp)
                            .background(
                                color = if (index == currentStep)
                                    Color(0xFF4DB6AC)
                                else
                                    MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(50)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            /* Navigation Buttons */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                /* Back Button */
                IconButton(
                    onClick = {
                        if (currentStep > 0) currentStep--
                    },
                    enabled = currentStep > 0
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                /* Next / Finish Button */
                Button(
                    onClick = {
                        if (currentStep < onboardingData.size - 1) {
                            currentStep++
                        } else {
                            onRegister()
                        }
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (currentStep == onboardingData.size - 1)
                            "Get Started"
                        else
                            "Next"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Next"
                    )
                }
            }
        }
    }
}
