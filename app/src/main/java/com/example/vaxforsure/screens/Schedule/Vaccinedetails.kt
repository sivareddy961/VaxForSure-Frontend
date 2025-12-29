package com.example.vaxforsure.screens.vaccinedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/* =========================================================
   Vaccine Data Model
   ========================================================= */
data class VaccineInfo(
    val name: String,
    val fullName: String,
    val ageGroup: String,
    val purpose: String,
    val diseasesPrevented: String,
    val dosage: String,
    val guidelines: String
)

val vaccineData = mapOf(
    "BCG" to VaccineInfo(
        name = "BCG",
        fullName = "Bacillus Calmette-Guérin vaccine",
        ageGroup = "At Birth",
        purpose = "Provides protection against severe forms of tuberculosis in children",
        diseasesPrevented = "Tuberculosis (TB), TB Meningitis, Miliary TB",
        dosage = "0.05 ml intradermal injection in left upper arm",
        guidelines = "Should be given as soon as possible after birth. A small scar will form at the injection site within 2-6 weeks."
    ),
    "Hepatitis B (1st dose)" to VaccineInfo(
        name = "Hepatitis B",
        fullName = "Hepatitis B Vaccine",
        ageGroup = "At Birth",
        purpose = "Protects against Hepatitis B virus infection",
        diseasesPrevented = "Hepatitis B, Liver cirrhosis, Liver cancer",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Should be administered within 24 hours of birth. Critical for preventing mother-to-child transmission."
    ),
    "Hepatitis B" to VaccineInfo(
        name = "Hepatitis B",
        fullName = "Hepatitis B Vaccine",
        ageGroup = "At Birth",
        purpose = "Protects against Hepatitis B virus infection",
        diseasesPrevented = "Hepatitis B, Liver cirrhosis, Liver cancer",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Should be administered within 24 hours of birth. Critical for preventing mother-to-child transmission."
    ),
    "OPV 0" to VaccineInfo(
        name = "OPV 0",
        fullName = "Oral Polio Vaccine - Birth dose",
        ageGroup = "At Birth",
        purpose = "Provides early protection against polio virus",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "2 drops orally",
        guidelines = "Given orally at birth. Part of the global polio eradication initiative."
    ),
    "OPV" to VaccineInfo(
        name = "OPV 0",
        fullName = "Oral Polio Vaccine - Birth dose",
        ageGroup = "At Birth",
        purpose = "Provides early protection against polio virus",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "2 drops orally",
        guidelines = "Given orally at birth. Part of the global polio eradication initiative."
    ),
    "DTaP (1st dose)" to VaccineInfo(
        name = "DTaP (1st dose)",
        fullName = "Diphtheria, Tetanus, and Pertussis vaccine",
        ageGroup = "6 Weeks",
        purpose = "Protects against three serious bacterial diseases",
        diseasesPrevented = "Diphtheria, Tetanus, Pertussis (Whooping Cough)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "First dose of the DTaP series. May cause mild fever or soreness at injection site."
    ),
    "DTaP (1st)" to VaccineInfo(
        name = "DTaP (1st dose)",
        fullName = "Diphtheria, Tetanus, and Pertussis vaccine",
        ageGroup = "6 Weeks",
        purpose = "Protects against three serious bacterial diseases",
        diseasesPrevented = "Diphtheria, Tetanus, Pertussis (Whooping Cough)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "First dose of the DTaP series. May cause mild fever or soreness at injection site."
    ),
    "DTaP" to VaccineInfo(
        name = "DTaP (1st dose)",
        fullName = "Diphtheria, Tetanus, and Pertussis vaccine",
        ageGroup = "6 Weeks",
        purpose = "Protects against three serious bacterial diseases",
        diseasesPrevented = "Diphtheria, Tetanus, Pertussis (Whooping Cough)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "First dose of the DTaP series. May cause mild fever or soreness at injection site."
    ),
    "DTaP (2nd dose)" to VaccineInfo(
        name = "DTaP (2nd dose)",
        fullName = "Diphtheria, Tetanus, and Pertussis vaccine",
        ageGroup = "10 Weeks",
        purpose = "Strengthens immunity against DTaP diseases",
        diseasesPrevented = "Diphtheria, Tetanus, Pertussis (Whooping Cough)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Second dose of DTaP series."
    ),
    "DTaP (2nd)" to VaccineInfo(
        name = "DTaP (2nd dose)",
        fullName = "Diphtheria, Tetanus, and Pertussis vaccine",
        ageGroup = "10 Weeks",
        purpose = "Strengthens immunity against DTaP diseases",
        diseasesPrevented = "Diphtheria, Tetanus, Pertussis (Whooping Cough)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Second dose of DTaP series."
    ),
    "IPV (1st dose)" to VaccineInfo(
        name = "IPV (1st dose)",
        fullName = "Inactivated Polio Vaccine",
        ageGroup = "6 Weeks",
        purpose = "Provides immunity against polio virus",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Injectable polio vaccine. Safer than oral vaccine for immunocompromised children."
    ),
    "IPV (1st)" to VaccineInfo(
        name = "IPV (1st dose)",
        fullName = "Inactivated Polio Vaccine",
        ageGroup = "6 Weeks",
        purpose = "Provides immunity against polio virus",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Injectable polio vaccine. Safer than oral vaccine for immunocompromised children."
    ),
    "IPV" to VaccineInfo(
        name = "IPV (1st dose)",
        fullName = "Inactivated Polio Vaccine",
        ageGroup = "6 Weeks",
        purpose = "Provides immunity against polio virus",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Injectable polio vaccine. Safer than oral vaccine for immunocompromised children."
    ),
    "IPV (2nd dose)" to VaccineInfo(
        name = "IPV (2nd dose)",
        fullName = "Inactivated Polio Vaccine",
        ageGroup = "10 Weeks",
        purpose = "Boosts polio immunity",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Second dose of IPV series."
    ),
    "IPV (2nd)" to VaccineInfo(
        name = "IPV (2nd dose)",
        fullName = "Inactivated Polio Vaccine",
        ageGroup = "10 Weeks",
        purpose = "Boosts polio immunity",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Second dose of IPV series."
    ),
    "Hepatitis B (2nd dose)" to VaccineInfo(
        name = "Hepatitis B (2nd dose)",
        fullName = "Second dose of Hepatitis B vaccine",
        ageGroup = "6 Weeks",
        purpose = "Continues protection against Hepatitis B",
        diseasesPrevented = "Hepatitis B, Liver cirrhosis, Liver cancer",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Second dose in the Hepatitis B series."
    ),
    "Hepatitis B (2nd)" to VaccineInfo(
        name = "Hepatitis B (2nd dose)",
        fullName = "Second dose of Hepatitis B vaccine",
        ageGroup = "6 Weeks",
        purpose = "Continues protection against Hepatitis B",
        diseasesPrevented = "Hepatitis B, Liver cirrhosis, Liver cancer",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Second dose in the Hepatitis B series."
    ),
    "Hib (1st dose)" to VaccineInfo(
        name = "Hib (1st dose)",
        fullName = "Haemophilus influenzae type b vaccine",
        ageGroup = "6 Weeks",
        purpose = "Protects against Hib bacterial infection",
        diseasesPrevented = "Meningitis, Pneumonia, Epiglottitis, Sepsis",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Prevents serious bacterial infections in young children."
    ),
    "Hib (1st)" to VaccineInfo(
        name = "Hib (1st dose)",
        fullName = "Haemophilus influenzae type b vaccine",
        ageGroup = "6 Weeks",
        purpose = "Protects against Hib bacterial infection",
        diseasesPrevented = "Meningitis, Pneumonia, Epiglottitis, Sepsis",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Prevents serious bacterial infections in young children."
    ),
    "Hib" to VaccineInfo(
        name = "Hib (1st dose)",
        fullName = "Haemophilus influenzae type b vaccine",
        ageGroup = "6 Weeks",
        purpose = "Protects against Hib bacterial infection",
        diseasesPrevented = "Meningitis, Pneumonia, Epiglottitis, Sepsis",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Prevents serious bacterial infections in young children."
    ),
    "Hib (2nd dose)" to VaccineInfo(
        name = "Hib (2nd dose)",
        fullName = "Haemophilus influenzae type b vaccine",
        ageGroup = "10 Weeks",
        purpose = "Continues protection against Hib",
        diseasesPrevented = "Meningitis, Pneumonia, Epiglottitis, Sepsis",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Second dose in Hib series."
    ),
    "Hib (2nd)" to VaccineInfo(
        name = "Hib (2nd dose)",
        fullName = "Haemophilus influenzae type b vaccine",
        ageGroup = "10 Weeks",
        purpose = "Continues protection against Hib",
        diseasesPrevented = "Meningitis, Pneumonia, Epiglottitis, Sepsis",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Second dose in Hib series."
    ),
    "PCV (1st dose)" to VaccineInfo(
        name = "PCV (1st dose)",
        fullName = "Pneumococcal Conjugate Vaccine",
        ageGroup = "6 Weeks",
        purpose = "Protects against pneumococcal bacteria",
        diseasesPrevented = "Pneumonia, Meningitis, Blood infections, Ear infections",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Protects against 13 types of pneumococcal bacteria."
    ),
    "PCV (1st)" to VaccineInfo(
        name = "PCV (1st dose)",
        fullName = "Pneumococcal Conjugate Vaccine",
        ageGroup = "6 Weeks",
        purpose = "Protects against pneumococcal bacteria",
        diseasesPrevented = "Pneumonia, Meningitis, Blood infections, Ear infections",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Protects against 13 types of pneumococcal bacteria."
    ),
    "PCV" to VaccineInfo(
        name = "PCV (1st dose)",
        fullName = "Pneumococcal Conjugate Vaccine",
        ageGroup = "6 Weeks",
        purpose = "Protects against pneumococcal bacteria",
        diseasesPrevented = "Pneumonia, Meningitis, Blood infections, Ear infections",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Protects against 13 types of pneumococcal bacteria."
    ),
    "PCV (2nd dose)" to VaccineInfo(
        name = "PCV (2nd dose)",
        fullName = "Pneumococcal Conjugate Vaccine",
        ageGroup = "10 Weeks",
        purpose = "Continues pneumococcal protection",
        diseasesPrevented = "Pneumonia, Meningitis, Blood infections, Ear infections",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Second dose of PCV series."
    ),
    "PCV (2nd)" to VaccineInfo(
        name = "PCV (2nd dose)",
        fullName = "Pneumococcal Conjugate Vaccine",
        ageGroup = "10 Weeks",
        purpose = "Continues pneumococcal protection",
        diseasesPrevented = "Pneumonia, Meningitis, Blood infections, Ear infections",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Second dose of PCV series."
    ),
    "Rotavirus (1st dose)" to VaccineInfo(
        name = "Rotavirus (1st dose)",
        fullName = "Rotavirus vaccine",
        ageGroup = "6 Weeks",
        purpose = "Prevents severe rotavirus diarrhea",
        diseasesPrevented = "Rotavirus gastroenteritis, Severe diarrhea, Dehydration",
        dosage = "Oral drops",
        guidelines = "Oral vaccine. Should be given before 15 weeks of age."
    ),
    "Rotavirus" to VaccineInfo(
        name = "Rotavirus (1st dose)",
        fullName = "Rotavirus vaccine",
        ageGroup = "6 Weeks",
        purpose = "Prevents severe rotavirus diarrhea",
        diseasesPrevented = "Rotavirus gastroenteritis, Severe diarrhea, Dehydration",
        dosage = "Oral drops",
        guidelines = "Oral vaccine. Should be given before 15 weeks of age."
    ),
    "Rotavirus (2nd dose)" to VaccineInfo(
        name = "Rotavirus (2nd dose)",
        fullName = "Rotavirus vaccine",
        ageGroup = "10 Weeks",
        purpose = "Boosts rotavirus immunity",
        diseasesPrevented = "Rotavirus gastroenteritis, Severe diarrhea, Dehydration",
        dosage = "Oral drops",
        guidelines = "Second oral dose."
    ),
    "Rotavirus (2nd)" to VaccineInfo(
        name = "Rotavirus (2nd dose)",
        fullName = "Rotavirus vaccine",
        ageGroup = "10 Weeks",
        purpose = "Boosts rotavirus immunity",
        diseasesPrevented = "Rotavirus gastroenteritis, Severe diarrhea, Dehydration",
        dosage = "Oral drops",
        guidelines = "Second oral dose."
    ),
    "DTaP (3rd dose)" to VaccineInfo(
        name = "DTaP (3rd dose)",
        fullName = "Diphtheria, Tetanus, and Pertussis vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes primary DTaP series",
        diseasesPrevented = "Diphtheria, Tetanus, Pertussis (Whooping Cough)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Third and final dose of primary DTaP series."
    ),
    "DTaP (3rd)" to VaccineInfo(
        name = "DTaP (3rd dose)",
        fullName = "Diphtheria, Tetanus, and Pertussis vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes primary DTaP series",
        diseasesPrevented = "Diphtheria, Tetanus, Pertussis (Whooping Cough)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Third and final dose of primary DTaP series."
    ),
    "IPV (3rd dose)" to VaccineInfo(
        name = "IPV (3rd dose)",
        fullName = "Inactivated Polio Vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes primary polio series",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Third dose of IPV series."
    ),
    "IPV (3rd)" to VaccineInfo(
        name = "IPV (3rd dose)",
        fullName = "Inactivated Polio Vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes primary polio series",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Third dose of IPV series."
    ),
    "Hepatitis B (3rd dose)" to VaccineInfo(
        name = "Hepatitis B (3rd dose)",
        fullName = "Third dose of Hepatitis B vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes Hepatitis B series",
        diseasesPrevented = "Hepatitis B, Liver cirrhosis, Liver cancer",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Completes the primary Hepatitis B vaccination series."
    ),
    "Hepatitis B (3rd)" to VaccineInfo(
        name = "Hepatitis B (3rd dose)",
        fullName = "Third dose of Hepatitis B vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes Hepatitis B series",
        diseasesPrevented = "Hepatitis B, Liver cirrhosis, Liver cancer",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Completes the primary Hepatitis B vaccination series."
    ),
    "Hib (3rd dose)" to VaccineInfo(
        name = "Hib (3rd dose)",
        fullName = "Haemophilus influenzae type b vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes primary Hib series",
        diseasesPrevented = "Meningitis, Pneumonia, Epiglottitis, Sepsis",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Completes primary Hib vaccination series."
    ),
    "Hib (3rd)" to VaccineInfo(
        name = "Hib (3rd dose)",
        fullName = "Haemophilus influenzae type b vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes primary Hib series",
        diseasesPrevented = "Meningitis, Pneumonia, Epiglottitis, Sepsis",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Completes primary Hib vaccination series."
    ),
    "PCV (3rd dose)" to VaccineInfo(
        name = "PCV (3rd dose)",
        fullName = "Pneumococcal Conjugate Vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes primary PCV series",
        diseasesPrevented = "Pneumonia, Meningitis, Blood infections, Ear infections",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Completes primary PCV series."
    ),
    "PCV (3rd)" to VaccineInfo(
        name = "PCV (3rd dose)",
        fullName = "Pneumococcal Conjugate Vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes primary PCV series",
        diseasesPrevented = "Pneumonia, Meningitis, Blood infections, Ear infections",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Completes primary PCV series."
    ),
    "Rotavirus (3rd dose)" to VaccineInfo(
        name = "Rotavirus (3rd dose)",
        fullName = "Rotavirus vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes rotavirus series",
        diseasesPrevented = "Rotavirus gastroenteritis, Severe diarrhea, Dehydration",
        dosage = "Oral drops",
        guidelines = "Final dose of rotavirus series."
    ),
    "Rotavirus (3rd)" to VaccineInfo(
        name = "Rotavirus (3rd dose)",
        fullName = "Rotavirus vaccine",
        ageGroup = "14 Weeks",
        purpose = "Completes rotavirus series",
        diseasesPrevented = "Rotavirus gastroenteritis, Severe diarrhea, Dehydration",
        dosage = "Oral drops",
        guidelines = "Final dose of rotavirus series."
    ),
    "MMR (1st dose)" to VaccineInfo(
        name = "MMR (1st dose)",
        fullName = "Measles, Mumps, and Rubella vaccine",
        ageGroup = "9-12 Months",
        purpose = "Protects against three viral diseases",
        diseasesPrevented = "Measles, Mumps, Rubella (German Measles)",
        dosage = "0.5 ml subcutaneous injection",
        guidelines = "Live vaccine. May cause mild rash or fever 7-10 days after vaccination."
    ),
    "MMR (1st)" to VaccineInfo(
        name = "MMR (1st dose)",
        fullName = "Measles, Mumps, and Rubella vaccine",
        ageGroup = "9-12 Months",
        purpose = "Protects against three viral diseases",
        diseasesPrevented = "Measles, Mumps, Rubella (German Measles)",
        dosage = "0.5 ml subcutaneous injection",
        guidelines = "Live vaccine. May cause mild rash or fever 7-10 days after vaccination."
    ),
    "PCV (Booster)" to VaccineInfo(
        name = "PCV (Booster)",
        fullName = "Pneumococcal Conjugate Vaccine Booster",
        ageGroup = "9-12 Months",
        purpose = "Boosts pneumococcal immunity",
        diseasesPrevented = "Pneumonia, Meningitis, Blood infections, Ear infections",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Booster dose for continued protection."
    ),
    "PCV Booster" to VaccineInfo(
        name = "PCV (Booster)",
        fullName = "Pneumococcal Conjugate Vaccine Booster",
        ageGroup = "9-12 Months",
        purpose = "Boosts pneumococcal immunity",
        diseasesPrevented = "Pneumonia, Meningitis, Blood infections, Ear infections",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Booster dose for continued protection."
    ),
    "Typhoid Conjugate Vaccine" to VaccineInfo(
        name = "Typhoid Conjugate Vaccine",
        fullName = "Typhoid fever vaccine",
        ageGroup = "9-12 Months",
        purpose = "Protects against typhoid fever",
        diseasesPrevented = "Typhoid fever",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Recommended in areas with high typhoid prevalence."
    ),
    "Typhoid" to VaccineInfo(
        name = "Typhoid Conjugate Vaccine",
        fullName = "Typhoid fever vaccine",
        ageGroup = "9-12 Months",
        purpose = "Protects against typhoid fever",
        diseasesPrevented = "Typhoid fever",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Recommended in areas with high typhoid prevalence."
    ),
    "DTaP (1st Booster)" to VaccineInfo(
        name = "DTaP (1st Booster)",
        fullName = "Diphtheria, Tetanus, and Pertussis booster",
        ageGroup = "16-24 Months",
        purpose = "Boosts immunity against DTaP diseases",
        diseasesPrevented = "Diphtheria, Tetanus, Pertussis (Whooping Cough)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "First booster dose for continued protection."
    ),
    "IPV (Booster)" to VaccineInfo(
        name = "IPV (Booster)",
        fullName = "Inactivated Polio Vaccine Booster",
        ageGroup = "16-24 Months",
        purpose = "Boosts polio immunity",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Booster dose for continued polio protection."
    ),
    "MMR (2nd dose)" to VaccineInfo(
        name = "MMR (2nd dose)",
        fullName = "Measles, Mumps, and Rubella vaccine",
        ageGroup = "16-24 Months",
        purpose = "Ensures complete immunity against MMR diseases",
        diseasesPrevented = "Measles, Mumps, Rubella (German Measles)",
        dosage = "0.5 ml subcutaneous injection",
        guidelines = "Second dose ensures 97% protection against measles."
    ),
    "MMR (2nd)" to VaccineInfo(
        name = "MMR (2nd dose)",
        fullName = "Measles, Mumps, and Rubella vaccine",
        ageGroup = "16-24 Months",
        purpose = "Ensures complete immunity against MMR diseases",
        diseasesPrevented = "Measles, Mumps, Rubella (German Measles)",
        dosage = "0.5 ml subcutaneous injection",
        guidelines = "Second dose ensures 97% protection against measles."
    ),
    "Varicella (1st dose)" to VaccineInfo(
        name = "Varicella (1st dose)",
        fullName = "Chickenpox vaccine",
        ageGroup = "16-24 Months",
        purpose = "Protects against chickenpox",
        diseasesPrevented = "Varicella (Chickenpox), Shingles (later in life)",
        dosage = "0.5 ml subcutaneous injection",
        guidelines = "Live vaccine. Prevents chickenpox and reduces shingles risk."
    ),
    "Varicella (1st)" to VaccineInfo(
        name = "Varicella (1st dose)",
        fullName = "Chickenpox vaccine",
        ageGroup = "16-24 Months",
        purpose = "Protects against chickenpox",
        diseasesPrevented = "Varicella (Chickenpox), Shingles (later in life)",
        dosage = "0.5 ml subcutaneous injection",
        guidelines = "Live vaccine. Prevents chickenpox and reduces shingles risk."
    ),
    "Hepatitis A (1st dose)" to VaccineInfo(
        name = "Hepatitis A (1st dose)",
        fullName = "Hepatitis A vaccine",
        ageGroup = "16-24 Months",
        purpose = "Protects against Hepatitis A virus",
        diseasesPrevented = "Hepatitis A, Liver infection",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Recommended for all children. Two-dose series."
    ),
    "Hepatitis A" to VaccineInfo(
        name = "Hepatitis A (1st dose)",
        fullName = "Hepatitis A vaccine",
        ageGroup = "16-24 Months",
        purpose = "Protects against Hepatitis A virus",
        diseasesPrevented = "Hepatitis A, Liver infection",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Recommended for all children. Two-dose series."
    ),
    "DTaP (2nd Booster)" to VaccineInfo(
        name = "DTaP (2nd Booster)",
        fullName = "Diphtheria, Tetanus, and Pertussis booster",
        ageGroup = "5-6 Years",
        purpose = "Maintains immunity before school entry",
        diseasesPrevented = "Diphtheria, Tetanus, Pertussis (Whooping Cough)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Required for school entry in most regions."
    ),
    "MMR (3rd dose)" to VaccineInfo(
        name = "MMR (3rd dose)",
        fullName = "Measles, Mumps, and Rubella vaccine",
        ageGroup = "5-6 Years",
        purpose = "Booster for school-age children",
        diseasesPrevented = "Measles, Mumps, Rubella (German Measles)",
        dosage = "0.5 ml subcutaneous injection",
        guidelines = "Additional protection before school entry."
    ),
    "MMR (3rd)" to VaccineInfo(
        name = "MMR (3rd dose)",
        fullName = "Measles, Mumps, and Rubella vaccine",
        ageGroup = "5-6 Years",
        purpose = "Booster for school-age children",
        diseasesPrevented = "Measles, Mumps, Rubella (German Measles)",
        dosage = "0.5 ml subcutaneous injection",
        guidelines = "Additional protection before school entry."
    ),
    "Varicella (2nd dose)" to VaccineInfo(
        name = "Varicella (2nd dose)",
        fullName = "Chickenpox vaccine",
        ageGroup = "5-6 Years",
        purpose = "Completes varicella series",
        diseasesPrevented = "Varicella (Chickenpox), Shingles (later in life)",
        dosage = "0.5 ml subcutaneous injection",
        guidelines = "Second dose ensures maximum protection."
    ),
    "Varicella (2nd)" to VaccineInfo(
        name = "Varicella (2nd dose)",
        fullName = "Chickenpox vaccine",
        ageGroup = "5-6 Years",
        purpose = "Completes varicella series",
        diseasesPrevented = "Varicella (Chickenpox), Shingles (later in life)",
        dosage = "0.5 ml subcutaneous injection",
        guidelines = "Second dose ensures maximum protection."
    ),
    "IPV (Final dose)" to VaccineInfo(
        name = "IPV (Final dose)",
        fullName = "Inactivated Polio Vaccine",
        ageGroup = "5-6 Years",
        purpose = "Final polio booster",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Completes polio vaccination series."
    ),
    "IPV (Final)" to VaccineInfo(
        name = "IPV (Final dose)",
        fullName = "Inactivated Polio Vaccine",
        ageGroup = "5-6 Years",
        purpose = "Final polio booster",
        diseasesPrevented = "Poliomyelitis (Polio)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Completes polio vaccination series."
    ),
    "Tdap" to VaccineInfo(
        name = "Tdap",
        fullName = "Tetanus, Diphtheria, and Pertussis booster",
        ageGroup = "10 Years",
        purpose = "Pre-adolescent booster",
        diseasesPrevented = "Tetanus, Diphtheria, Pertussis (Whooping Cough)",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Booster recommended for pre-teens."
    ),
    "HPV (1st dose)" to VaccineInfo(
        name = "HPV (1st dose)",
        fullName = "Human Papillomavirus vaccine",
        ageGroup = "12+ Years",
        purpose = "Protects against HPV-related cancers",
        diseasesPrevented = "Cervical cancer, Genital warts, Other HPV-related cancers",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Recommended for both boys and girls. Two or three-dose series."
    ),
    "HPV (1st)" to VaccineInfo(
        name = "HPV (1st dose)",
        fullName = "Human Papillomavirus vaccine",
        ageGroup = "12+ Years",
        purpose = "Protects against HPV-related cancers",
        diseasesPrevented = "Cervical cancer, Genital warts, Other HPV-related cancers",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Recommended for both boys and girls. Two or three-dose series."
    ),
    "HPV (2nd dose)" to VaccineInfo(
        name = "HPV (2nd dose)",
        fullName = "Human Papillomavirus vaccine",
        ageGroup = "12+ Years",
        purpose = "Continues HPV protection",
        diseasesPrevented = "Cervical cancer, Genital warts, Other HPV-related cancers",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Given 6-12 months after first dose."
    ),
    "HPV (2nd)" to VaccineInfo(
        name = "HPV (2nd dose)",
        fullName = "Human Papillomavirus vaccine",
        ageGroup = "12+ Years",
        purpose = "Continues HPV protection",
        diseasesPrevented = "Cervical cancer, Genital warts, Other HPV-related cancers",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Given 6-12 months after first dose."
    ),
    "Meningococcal Vaccine" to VaccineInfo(
        name = "Meningococcal Vaccine",
        fullName = "Meningococcal conjugate vaccine",
        ageGroup = "12+ Years",
        purpose = "Protects against meningococcal disease",
        diseasesPrevented = "Meningococcal meningitis, Meningococcal sepsis",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Recommended for adolescents and college students."
    ),
    "Meningococcal" to VaccineInfo(
        name = "Meningococcal Vaccine",
        fullName = "Meningococcal conjugate vaccine",
        ageGroup = "12+ Years",
        purpose = "Protects against meningococcal disease",
        diseasesPrevented = "Meningococcal meningitis, Meningococcal sepsis",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Recommended for adolescents and college students."
    ),
    "Meningococcal vaccine" to VaccineInfo(
        name = "Meningococcal Vaccine",
        fullName = "Meningococcal conjugate vaccine",
        ageGroup = "12+ Years",
        purpose = "Protects against meningococcal disease",
        diseasesPrevented = "Meningococcal meningitis, Meningococcal sepsis",
        dosage = "0.5 ml intramuscular injection",
        guidelines = "Recommended for adolescents and college students."
    )
)

/* =========================================================
   Vaccine Details Screen
   ========================================================= */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaccineDetailsScreen(
    navController: NavController? = null,
    vaccineName: String? = null
) {
    // Get vaccine name from navigation arguments or use default
    val currentVaccineName = vaccineName ?: "BCG"
    val vaccineInfo = vaccineData[currentVaccineName] ?: vaccineData["BCG"]!!
    Scaffold(
        containerColor = Color(0xFFF5FAFA)
    ) { padding ->
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                            Color(0xFF00BFA5),
                            Color(0xFF00897B)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            /* ---------------- Top App Bar ---------------- */
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            .clickable {
                                navController?.popBackStack()
                },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Text(
                        text = "Vaccine Details",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

            /* ---------------- Vaccine Card ---------------- */
                VaccineHeaderCard(vaccineInfo)

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------------- Content Section ---------------- */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                            color = Color(0xFFF5FAFA),
                        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                    )
                    .padding(16.dp)
            ) {

                InfoCard(
                    icon = Icons.Default.Shield,
                        iconBg = Color(0xFFE0F2F1),
                        iconTint = Color(0xFF00BFA5),
                    title = "Purpose",
                        description = vaccineInfo.purpose
                )

                InfoCard(
                    icon = Icons.Default.Warning,
                        iconBg = Color(0xFFFFEBEE),
                        iconTint = Color(0xFFE91E63),
                    title = "Diseases Prevented",
                        description = vaccineInfo.diseasesPrevented
                )

                InfoCard(
                        icon = Icons.Default.Medication,
                        iconBg = Color(0xFFE3F2FD),
                        iconTint = Color(0xFF2196F3),
                    title = "Dosage & Administration",
                        description = vaccineInfo.dosage
                )

                InfoCard(
                    icon = Icons.Default.Description,
                        iconBg = Color(0xFFE8F5E9),
                        iconTint = Color(0xFF4CAF50),
                    title = "Official Guidelines",
                        description = vaccineInfo.guidelines
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    /* ---------------- Mark as Completed Button ---------------- */
                    Button(
                        onClick = {
                            navController?.navigate("${com.example.vaxforsure.navigation.Destinations.MARK_AS_COMPLETED}/$currentVaccineName")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00BFA5)
                        )
                    ) {
                        Text(
                            text = "Mark as Completed",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

/* =========================================================
   Vaccine Header Card
   ========================================================= */

@Composable
fun VaccineHeaderCard(vaccineInfo: VaccineInfo) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF80CBC4),
                            Color(0xFF4DB6AC)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
        )
                .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, CircleShape),
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

            Column(modifier = Modifier.weight(1f)) {
                Text(
                        text = vaccineInfo.name,
                        fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                        text = vaccineInfo.fullName,
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    TagChip(
                            text = vaccineInfo.ageGroup,
                        bgColor = Color.White.copy(alpha = 0.25f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TagChip(
                            text = "Pending",
                            bgColor = Color(0xFFFF9800)
                    )
                    }
                }
            }
        }
    }
}

/* =========================================================
   Info Card
   ========================================================= */

@Composable
fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(iconBg, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF212121)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = Color(0xFF757575),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

/* =========================================================
   Chip
   ========================================================= */

@Composable
fun TagChip(
    text: String,
    bgColor: Color
) {
    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}
