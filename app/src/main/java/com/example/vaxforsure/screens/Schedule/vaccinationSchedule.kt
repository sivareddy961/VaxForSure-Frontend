package com.example.vaxforsure.screens.schedule

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vaxforsure.navigation.Destinations
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import com.example.vaxforsure.utils.VaccineManager
import com.example.vaxforsure.utils.ChildManager
import com.example.vaxforsure.utils.PreferenceManager

/* =========================================================
   DATA MODELS
   ========================================================= */
data class AgeGroup(val id: String, val label: String)

data class Vaccine(
    val id: String,
    val name: String,
    val description: String,
    val doseNumber: Int?,
    val ageGroupId: String,
    val ageDisplay: String
)

enum class VaccineStatus { PENDING, COMPLETED }

/* =========================================================
   AGE GROUPS
   ========================================================= */
private val ageGroups = listOf(
    AgeGroup("birth", "At Birth"),
    AgeGroup("6w", "6 Weeks"),
    AgeGroup("10w", "10 Weeks"),
    AgeGroup("14w", "14 Weeks"),
    AgeGroup("9_12m", "9-12 Months"),
    AgeGroup("16_24m", "16-24 Months"),
    AgeGroup("5_6y", "5-6 Years"),
    AgeGroup("10y", "10 Years"),
    AgeGroup("12y", "12+ Years")
)

/* =========================================================
   VACCINE DATA
   ========================================================= */
private val vaccines = listOf(
    // -------------------- AT BIRTH --------------------
    Vaccine("bcg","BCG","Bacillus Calmette–Guérin vaccine",null,"birth","At Birth"),
    Vaccine("hep_b_1","Hepatitis B (1st dose)","First dose of Hepatitis B vaccine",1,"birth","At Birth"),
    Vaccine("opv_0","OPV 0","Oral Polio Vaccine - Birth dose",null,"birth","At Birth"),

    // -------------------- 6 WEEKS --------------------
    Vaccine("dtap_1","DTaP (1st dose)","Diphtheria, Tetanus, and Pertussis vaccine",1,"6w","6 Weeks"),
    Vaccine("ipv_1","IPV (1st dose)","Inactivated Polio Vaccine",1,"6w","6 Weeks"),
    Vaccine("hep_b_2","Hepatitis B (2nd dose)","Second dose of Hepatitis B vaccine",2,"6w","6 Weeks"),
    Vaccine("hib_1","Hib (1st dose)","Haemophilus influenzae type b vaccine",1,"6w","6 Weeks"),
    Vaccine("pcv_1","PCV (1st dose)","Pneumococcal Conjugate Vaccine",1,"6w","6 Weeks"),
    Vaccine("rota_1","Rotavirus (1st dose)","Rotavirus vaccine",1,"6w","6 Weeks"),

    // -------------------- 10 WEEKS --------------------
    Vaccine("dtap_2","DTaP (2nd dose)","Diphtheria, Tetanus, and Pertussis vaccine",2,"10w","10 Weeks"),
    Vaccine("ipv_2","IPV (2nd dose)","Inactivated Polio Vaccine",2,"10w","10 Weeks"),
    Vaccine("hib_2","Hib (2nd dose)","Haemophilus influenzae type b vaccine",2,"10w","10 Weeks"),
    Vaccine("pcv_2","PCV (2nd dose)","Pneumococcal Conjugate Vaccine",2,"10w","10 Weeks"),
    Vaccine("rota_2","Rotavirus (2nd dose)","Rotavirus vaccine",2,"10w","10 Weeks"),

    // -------------------- 14 WEEKS --------------------
    Vaccine("dtap_3","DTaP (3rd dose)","Diphtheria, Tetanus, and Pertussis vaccine",3,"14w","14 Weeks"),
    Vaccine("ipv_3","IPV (3rd dose)","Inactivated Polio Vaccine",3,"14w","14 Weeks"),
    Vaccine("hep_b_3","Hepatitis B (3rd dose)","Third dose of Hepatitis B vaccine",3,"14w","14 Weeks"),
    Vaccine("hib_3","Hib (3rd dose)","Haemophilus influenzae type b vaccine",3,"14w","14 Weeks"),
    Vaccine("pcv_3","PCV (3rd dose)","Pneumococcal Conjugate Vaccine",3,"14w","14 Weeks"),
    Vaccine("rota_3","Rotavirus (3rd dose)","Rotavirus vaccine",3,"14w","14 Weeks"),

    /* ---- 9–12 MONTHS ---- */
    Vaccine("mmr_1","MMR (1st dose)","Measles, Mumps, and Rubella vaccine",1,"9_12m","9-12 Months"),
    Vaccine("pcv_booster","PCV (Booster)","Pneumococcal Conjugate Vaccine Booster",4,"9_12m","9-12 Months"),
    Vaccine("typhoid","Typhoid Conjugate Vaccine","Typhoid fever vaccine",null,"9_12m","9-12 Months"),

    /* ---- 16–24 MONTHS ---- */
    Vaccine("dtap_boost1","DTaP (1st Booster)","Diphtheria, Tetanus, and Pertussis booster",4,"16_24m","16-24 Months"),
    Vaccine("ipv_boost","IPV (Booster)","Inactivated Polio Vaccine Booster",4,"16_24m","16-24 Months"),
    Vaccine("mmr_2","MMR (2nd dose)","Measles, Mumps, and Rubella vaccine",2,"16_24m","16-24 Months"),
    Vaccine("varicella_1","Varicella (1st dose)","Chickenpox vaccine",1,"16_24m","16-24 Months"),
    Vaccine("hep_a_1","Hepatitis A (1st dose)","Hepatitis A vaccine",1,"16_24m","16-24 Months"),

    /* ---- 5–6 YEARS ---- */
    Vaccine("dtap_boost2","DTaP (2nd Booster)","Diphtheria, Tetanus, and Pertussis booster",5,"5_6y","5-6 Years"),
    Vaccine("mmr_3","MMR (3rd dose)","Measles, Mumps, and Rubella vaccine",3,"5_6y","5-6 Years"),
    Vaccine("varicella_2","Varicella (2nd dose)","Chickenpox vaccine",2,"5_6y","5-6 Years"),
    Vaccine("ipv_final","IPV (Final dose)","Inactivated Polio Vaccine",5,"5_6y","5-6 Years"),

    /* ---- 10 YEARS ---- */
    Vaccine("tdap","Tdap","Tetanus, Diphtheria, and Pertussis booster",null,"10y","10 Years"),

    /* ---- 12+ YEARS ---- */
    Vaccine("hpv_1","HPV (1st dose)","Human Papillomavirus vaccine",1,"12y","12+ Years"),
    Vaccine("hpv_2","HPV (2nd dose)","Human Papillomavirus vaccine",2,"12y","12+ Years"),
    Vaccine("meningococcal","Meningococcal Vaccine","Meningococcal conjugate vaccine",null,"12y","12+ Years"),
)

/* =========================================================
   MAIN SCREEN
   ========================================================= */
@Composable
fun VaccinationScheduleScreen(
    navController: NavController,
    childName: String? = null
) {
    val context = LocalContext.current
    var selectedAgeGroup by remember { mutableStateOf("birth") }
    val filteredVaccines = vaccines.filter { it.ageGroupId == selectedAgeGroup }
    
    // Get childId from savedStateHandle (passed from dashboard)
    // Try multiple ways to get childId
    val childId = remember {
        var id = navController.currentBackStackEntry?.savedStateHandle?.get<Int>("childId") ?: 0
        if (id == 0) {
            id = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("childId") ?: 0
        }
        // If still 0, try to get from children list (first child as fallback)
        if (id == 0) {
            val userId = PreferenceManager.getUserId(context)
            val children = ChildManager.getAllChildren(context, userId)
            id = children.firstOrNull()?.id ?: 0
        }
        id
    }
    
    // Get child name from savedStateHandle or parameter
    val displayChildName = remember(childName, childId) {
        if (childName != null) {
            childName
        } else if (childId > 0) {
            ChildManager.getChildById(context, childId)?.name ?: "Child"
        } else {
            val pref = context.getSharedPreferences("temp_child", Context.MODE_PRIVATE)
            pref.getString("name", "Child") ?: "Child"
        }
    }
    
    // Get child object for display
    val child = remember(childId) {
        if (childId > 0) {
            ChildManager.getChildById(context, childId)
        } else {
            null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6FBFB))
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        /* ================= HEADER ================= */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF6EC6A6),
                    RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White.copy(0.2f), CircleShape)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            null,
                            tint = Color.White
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "Vaccination Schedule",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White.copy(0.25f),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            "Tracking for",
                            color = Color.White.copy(0.8f),
                            fontSize = 12.sp
                        )
                        Text(
                            displayChildName,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        /* ================= AGE TABS ================= */
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ageGroups.forEach { group ->
                val selected = selectedAgeGroup == group.id
                Box(
                    modifier = Modifier
                        .background(
                            if (selected) Color(0xFF4DB6AC) else Color.White,
                            RoundedCornerShape(50)
                        )
                        .border(
                            if (selected) 0.dp else 1.dp,
                            Color(0xFFE0E0E0),
                            RoundedCornerShape(50)
                        )
                        .clickable { selectedAgeGroup = group.id }
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        group.label,
                        color = if (selected) Color.White else Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        /* ================= VACCINE LIST ================= */
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredVaccines) { vaccine ->
                VaccineCard(vaccine, navController, childId)
            }
        }
    }
}

/* =========================================================
   VACCINE CARD
   ========================================================= */
@Composable
fun VaccineCard(vaccine: Vaccine, navController: NavController, childId: Int = 0) {
    val context = LocalContext.current
    
    // Check if this vaccine is completed for the specific child
    val status = remember(vaccine.name, childId) {
        if (childId > 0) {
            if (VaccineManager.isVaccineCompleted(context, childId, vaccine.name)) {
                VaccineStatus.COMPLETED
            } else {
                VaccineStatus.PENDING
            }
        } else {
            VaccineStatus.PENDING
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (vaccine.name == "BCG" || vaccine.id == "bcg") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/BCG")
                } else if (vaccine.name == "Hepatitis B (1st dose)" || vaccine.id == "hep_b_1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Hepatitis B (1st dose)")
                } else if (vaccine.name == "Hepatitis B (2nd dose)" || vaccine.id == "hep_b_2") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Hepatitis B (2nd dose)")
                } else if (vaccine.name == "OPV 0" || vaccine.id == "opv_0") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/OPV 0")
                } else if (vaccine.name == "DTaP (1st dose)" || vaccine.id == "dtap_1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/DTaP (1st dose)")
                } else if (vaccine.name == "DTaP (2nd dose)" || vaccine.id == "dtap_2") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/DTaP (2nd dose)")
                } else if (vaccine.name == "IPV (1st dose)" || vaccine.id == "ipv_1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/IPV (1st dose)")
                } else if (vaccine.name == "IPV (2nd dose)" || vaccine.id == "ipv_2") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/IPV (2nd dose)")
                } else if (vaccine.name == "Hib (1st dose)" || vaccine.id == "hib_1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Hib (1st dose)")
                } else if (vaccine.name == "Hib (2nd dose)" || vaccine.id == "hib_2") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Hib (2nd dose)")
                } else if (vaccine.name == "PCV (1st dose)" || vaccine.id == "pcv_1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/PCV (1st dose)")
                } else if (vaccine.name == "PCV (2nd dose)" || vaccine.id == "pcv_2") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/PCV (2nd dose)")
                } else if (vaccine.name == "Rotavirus (1st dose)" || vaccine.id == "rota_1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Rotavirus (1st dose)")
                } else if (vaccine.name == "Rotavirus (2nd dose)" || vaccine.id == "rota_2") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Rotavirus (2nd dose)")
                } else if (vaccine.name == "DTaP (3rd dose)" || vaccine.id == "dtap_3") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/DTaP (3rd dose)")
                } else if (vaccine.name == "IPV (3rd dose)" || vaccine.id == "ipv_3") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/IPV (3rd dose)")
                } else if (vaccine.name == "Hepatitis B (3rd dose)" || vaccine.id == "hep_b_3") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Hepatitis B (3rd dose)")
                } else if (vaccine.name == "Hib (3rd dose)" || vaccine.id == "hib_3") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Hib (3rd dose)")
                } else if (vaccine.name == "PCV (3rd dose)" || vaccine.id == "pcv_3") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/PCV (3rd dose)")
                } else if (vaccine.name == "Rotavirus (3rd dose)" || vaccine.id == "rota_3") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Rotavirus (3rd dose)")
                } else if (vaccine.name == "MMR (1st dose)" || vaccine.id == "mmr_1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/MMR (1st dose)")
                } else if (vaccine.name == "PCV (Booster)" || vaccine.id == "pcv_booster") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/PCV (Booster)")
                } else if (vaccine.name == "Typhoid Conjugate Vaccine" || vaccine.id == "typhoid") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Typhoid Conjugate Vaccine")
                } else if (vaccine.name == "DTaP (1st Booster)" || vaccine.id == "dtap_boost1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/DTaP (1st Booster)")
                } else if (vaccine.name == "IPV (Booster)" || vaccine.id == "ipv_boost") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/IPV (Booster)")
                } else if (vaccine.name == "MMR (2nd dose)" || vaccine.id == "mmr_2") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/MMR (2nd dose)")
                } else if (vaccine.name == "Varicella (1st dose)" || vaccine.id == "varicella_1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Varicella (1st dose)")
                } else if (vaccine.name == "Hepatitis A (1st dose)" || vaccine.id == "hep_a_1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Hepatitis A (1st dose)")
                } else if (vaccine.name == "DTaP (2nd Booster)" || vaccine.id == "dtap_boost2") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/DTaP (2nd Booster)")
                } else if (vaccine.name == "MMR (3rd dose)" || vaccine.id == "mmr_3") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/MMR (3rd dose)")
                } else if (vaccine.name == "Varicella (2nd dose)" || vaccine.id == "varicella_2") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Varicella (2nd dose)")
                } else if (vaccine.name == "IPV (Final dose)" || vaccine.id == "ipv_final") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/IPV (Final dose)")
                } else if (vaccine.name == "Tdap" || vaccine.id == "tdap") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Tdap")
                } else if (vaccine.name == "HPV (1st dose)" || vaccine.id == "hpv_1") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/HPV (1st dose)")
                } else if (vaccine.name == "HPV (2nd dose)" || vaccine.id == "hpv_2") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/HPV (2nd dose)")
                } else if (vaccine.name == "Meningococcal Vaccine" || vaccine.id == "meningococcal") {
                    navController.navigate("${Destinations.VACCINE_DETAILS}/Meningococcal Vaccine")
                }
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFFE0F7FA),
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Schedule,
                    null,
                    tint = Color(0xFFFFA000)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        vaccine.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    vaccine.doseNumber?.let {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFF1F3F4),
                                    RoundedCornerShape(50)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "Dose $it",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    vaccine.description,
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .background(
                                if (status == VaccineStatus.COMPLETED) Color(0xFFE0F2F1) else Color(0xFFFFF3E0),
                                RoundedCornerShape(50)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            if (status == VaccineStatus.COMPLETED) "Completed" else "Pending",
                            fontSize = 11.sp,
                            color = if (status == VaccineStatus.COMPLETED) Color(0xFF00BFA5) else Color(0xFFFFA000),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        vaccine.ageDisplay,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
