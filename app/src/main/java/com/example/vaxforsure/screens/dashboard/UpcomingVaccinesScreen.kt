package com.example.vaxforsure.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vaxforsure.navigation.Destinations

/* =========================================================
   UPCOMING VACCINES DATA
   ========================================================= */
data class UpcomingVaccine(
    val name: String,
    val description: String,
    val ageGroup: String
)

val upcomingVaccinesList = listOf(
    UpcomingVaccine("BCG", "Bacillus Calmette-Guérin vaccine", "At Birth"),
    UpcomingVaccine("Hepatitis B (1st dose)", "First dose of Hepatitis B vaccine", "At Birth"),
    UpcomingVaccine("OPV 0", "Oral Polio Vaccine - Birth dose", "At Birth"),
    UpcomingVaccine("DTaP (1st dose)", "Diphtheria, Tetanus, and Pertussis vaccine", "6 Weeks"),
    UpcomingVaccine("IPV (1st dose)", "Inactivated Polio Vaccine", "6 Weeks"),
    UpcomingVaccine("Hepatitis B (2nd dose)", "Second dose of Hepatitis B vaccine", "6 Weeks"),
    UpcomingVaccine("Hib (1st dose)", "Haemophilus influenzae type b vaccine", "6 Weeks"),
    UpcomingVaccine("PCV (1st dose)", "Pneumococcal Conjugate Vaccine", "6 Weeks"),
    UpcomingVaccine("Rotavirus (1st dose)", "Rotavirus vaccine", "6 Weeks"),
    UpcomingVaccine("DTaP (2nd dose)", "Diphtheria, Tetanus, and Pertussis vaccine", "10 Weeks"),
    UpcomingVaccine("IPV (2nd dose)", "Inactivated Polio Vaccine", "10 Weeks"),
    UpcomingVaccine("Hib (2nd dose)", "Haemophilus influenzae type b vaccine", "10 Weeks"),
    UpcomingVaccine("PCV (2nd dose)", "Pneumococcal Conjugate Vaccine", "10 Weeks"),
    UpcomingVaccine("Rotavirus (2nd dose)", "Rotavirus vaccine", "10 Weeks"),
    UpcomingVaccine("DTaP (3rd dose)", "Diphtheria, Tetanus, and Pertussis vaccine", "14 Weeks"),
    UpcomingVaccine("IPV (3rd dose)", "Inactivated Polio Vaccine", "14 Weeks"),
    UpcomingVaccine("Hepatitis B (3rd dose)", "Third dose of Hepatitis B vaccine", "14 Weeks"),
    UpcomingVaccine("Hib (3rd dose)", "Haemophilus influenzae type b vaccine", "14 Weeks"),
    UpcomingVaccine("PCV (3rd dose)", "Pneumococcal Conjugate Vaccine", "14 Weeks"),
    UpcomingVaccine("Rotavirus (3rd dose)", "Rotavirus vaccine", "14 Weeks"),
    UpcomingVaccine("MMR (1st dose)", "Measles, Mumps, and Rubella vaccine", "9-12 Months"),
    UpcomingVaccine("PCV (Booster)", "Pneumococcal Conjugate Vaccine Booster", "9-12 Months"),
    UpcomingVaccine("Typhoid Conjugate Vaccine", "Typhoid fever vaccine", "9-12 Months"),
    UpcomingVaccine("DTaP (1st Booster)", "Diphtheria, Tetanus, and Pertussis booster", "16-24 Months"),
    UpcomingVaccine("IPV (Booster)", "Inactivated Polio Vaccine Booster", "16-24 Months"),
    UpcomingVaccine("MMR (2nd dose)", "Measles, Mumps, and Rubella vaccine", "16-24 Months"),
    UpcomingVaccine("Varicella (1st dose)", "Chickenpox vaccine", "16-24 Months"),
    UpcomingVaccine("Hepatitis A (1st dose)", "Hepatitis A vaccine", "16-24 Months"),
    UpcomingVaccine("DTaP (2nd Booster)", "Diphtheria, Tetanus, and Pertussis booster", "5-6 Years"),
    UpcomingVaccine("MMR (3rd dose)", "Measles, Mumps, and Rubella vaccine", "5-6 Years"),
    UpcomingVaccine("Varicella (2nd dose)", "Chickenpox vaccine", "5-6 Years"),
    UpcomingVaccine("IPV (Final dose)", "Inactivated Polio Vaccine", "5-6 Years"),
    UpcomingVaccine("Tdap", "Tetanus, Diphtheria, and Pertussis booster", "10 Years"),
    UpcomingVaccine("HPV (1st dose)", "Human Papillomavirus vaccine", "12+ Years"),
    UpcomingVaccine("HPV (2nd dose)", "Human Papillomavirus vaccine", "12+ Years"),
    UpcomingVaccine("Meningococcal Vaccine", "Meningococcal conjugate vaccine", "12+ Years")
)

/* =========================================================
   UPCOMING VACCINES SCREEN
   ========================================================= */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingVaccinesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Upcoming Vaccines",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF5FAFA)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(upcomingVaccinesList) { vaccine ->
                UpcomingVaccineListItem(vaccine, navController)
            }
        }
    }
}

/* =========================================================
   UPCOMING VACCINE LIST ITEM
   ========================================================= */
@Composable
fun UpcomingVaccineListItem(
    vaccine: UpcomingVaccine,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Only navigate if vaccine details screen exists
                val vaccineName = when (vaccine.name) {
                    "BCG" -> "BCG"
                    "Hepatitis B (1st dose)" -> "Hepatitis B (1st dose)"
                    "OPV 0" -> "OPV 0"
                    "DTaP (1st dose)" -> "DTaP (1st dose)"
                    "DTaP (2nd dose)" -> "DTaP (2nd dose)"
                    "DTaP (3rd dose)" -> "DTaP (3rd dose)"
                    "IPV (1st dose)" -> "IPV (1st dose)"
                    "IPV (2nd dose)" -> "IPV (2nd dose)"
                    "IPV (3rd dose)" -> "IPV (3rd dose)"
                    "Hepatitis B (2nd dose)" -> "Hepatitis B (2nd dose)"
                    "Hepatitis B (3rd dose)" -> "Hepatitis B (3rd dose)"
                    "Hib (1st dose)" -> "Hib (1st dose)"
                    "Hib (2nd dose)" -> "Hib (2nd dose)"
                    "Hib (3rd dose)" -> "Hib (3rd dose)"
                    "PCV (1st dose)" -> "PCV (1st dose)"
                    "PCV (2nd dose)" -> "PCV (2nd dose)"
                    "PCV (3rd dose)" -> "PCV (3rd dose)"
                    "Rotavirus (1st dose)" -> "Rotavirus (1st dose)"
                    "Rotavirus (2nd dose)" -> "Rotavirus (2nd dose)"
                    "Rotavirus (3rd dose)" -> "Rotavirus (3rd dose)"
                    "MMR (1st dose)" -> "MMR (1st dose)"
                    "MMR (2nd dose)" -> "MMR (2nd dose)"
                    "PCV (Booster)" -> "PCV (Booster)"
                    "Typhoid Conjugate Vaccine" -> "Typhoid Conjugate Vaccine"
                    "DTaP (1st Booster)" -> "DTaP (1st Booster)"
                    "DTaP (2nd Booster)" -> "DTaP (2nd Booster)"
                    "IPV (Booster)" -> "IPV (Booster)"
                    "IPV (Final dose)" -> "IPV (Final dose)"
                    "Varicella (1st dose)" -> "Varicella (1st dose)"
                    "Varicella (2nd dose)" -> "Varicella (2nd dose)"
                    "Hepatitis A (1st dose)" -> "Hepatitis A (1st dose)"
                    "MMR (3rd dose)" -> "MMR (3rd dose)"
                    "Tdap" -> "Tdap"
                    "HPV (1st dose)" -> "HPV (1st dose)"
                    "HPV (2nd dose)" -> "HPV (2nd dose)"
                    "Meningococcal Vaccine" -> "Meningococcal Vaccine"
                    else -> null
                }
                vaccineName?.let {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/$it")
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Calendar Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF1E3A3A), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = Color(0xFF00BFA5),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Vaccine Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = vaccine.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color(0xFF212121)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = vaccine.description,
                    fontSize = 13.sp,
                    color = Color(0xFF757575)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Age Group Tag
                Box(
                    modifier = Modifier
                        .background(
                            Color(0xFF8D6E63),
                            RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = vaccine.ageGroup,
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

