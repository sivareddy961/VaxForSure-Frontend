package com.example.vaxforsure.screens.records

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vaxforsure.utils.ChildManager
import com.example.vaxforsure.utils.VaccineManager
import com.example.vaxforsure.utils.PreferenceManager
import com.example.vaxforsure.screens.profile.Child
import com.example.vaxforsure.utils.VaccinationRecord
import android.widget.Toast

/* -------------------- MAIN SCREEN -------------------- */
@Composable
fun RecordsScreen(
    navController: NavController,
    selectedChild: Any? = null,
    childRecords: List<Any> = emptyList()
) {
    val context = LocalContext.current
    var filterType by remember { mutableStateOf("all") }
    
    val userId = remember { PreferenceManager.getUserId(context) }
    
    // Load all children and their vaccination records (filtered by user)
    var refreshKey by remember { mutableStateOf(0) }
    val children = remember(userId, refreshKey) { ChildManager.getAllChildren(context, userId) }
    val allRecordsMap = remember(refreshKey) { VaccineManager.getAllVaccinationRecords(context) }
    
    // Convert to list of records grouped by child
    val recordsByChild = remember(children, allRecordsMap) {
        children.mapNotNull { child ->
            val records = allRecordsMap[child.id] ?: emptyList()
            if (records.isNotEmpty()) {
                ChildRecords(
                    child = child,
                    records = records
                )
            } else null
        }
    }
    
    val totalRecords = recordsByChild.sumOf { it.records.size }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6FBFB))
    ) {

        /* ================= HEADER ================= */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF63C7A4),
                            Color(0xFF4FB392)
                        )
                    ),
                    shape = RoundedCornerShape(
                        bottomStart = 28.dp,
                        bottomEnd = 28.dp
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                /* -------- TOP ROW (BACK ONLY) -------- */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White.copy(alpha = 0.25f), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                /* -------- TITLE + DOWNLOAD (MOVED DOWN) -------- */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Vaccination Records",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )

                    IconButton(
                        onClick = { navController.navigate("export-records") },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White.copy(alpha = 0.25f), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = "Export",
                            tint = Color.White
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                /* -------- RECORDS SUMMARY CARD -------- */
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White.copy(alpha = 0.35f),
                            RoundedCornerShape(18.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Total Records",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                    Text(
                        text = "$totalRecords vaccination${if (totalRecords != 1) "s" else ""} across ${recordsByChild.size} child${if (recordsByChild.size != 1) "ren" else ""}",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        /* ================= FILTER CHIPS ================= */
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf("All", "Recent").forEach { label ->
                val selected = filterType == label.lowercase()

                Box(
                    modifier = Modifier
                        .background(
                            if (selected) Color(0xFF4DB6AC) else Color.Transparent,
                            RoundedCornerShape(50)
                        )
                        .border(
                            if (selected) BorderStroke(0.dp, Color.Transparent)
                            else BorderStroke(1.dp, Color(0xFFCCE6E2)),
                            RoundedCornerShape(50)
                        )
                        .clickable { filterType = label.lowercase() }
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = label,
                        color = if (selected) Color.White else Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        /* ================= RECORDS LIST ================= */
        if (recordsByChild.isEmpty()) {
            /* ================= EMPTY STATE ================= */
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .border(
                        BorderStroke(1.dp, Color(0xFFD6EDEA)),
                        RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        tint = Color(0xFF90A4AE),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "No vaccination records yet",
                        color = Color(0xFF607D8B)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(recordsByChild) { childRecords ->
                    ChildRecordsSection(childRecords.child, childRecords.records, navController)
                }
            }
        }
    }
}

/* -------------------- DATA MODELS -------------------- */
data class ChildRecords(
    val child: com.example.vaxforsure.screens.profile.Child,
    val records: List<com.example.vaxforsure.utils.VaccinationRecord>
)

/* -------------------- CHILD RECORDS SECTION -------------------- */
@Composable
fun ChildRecordsSection(
    child: com.example.vaxforsure.screens.profile.Child,
    records: List<com.example.vaxforsure.utils.VaccinationRecord>,
    navController: NavController? = null,
    onRecordDeleted: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Child Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFF00BFA5), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = child.name.firstOrNull()?.toString() ?: "C",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                
                Spacer(Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = child.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color(0xFF212121)
                    )
                    Text(
                        text = "${records.size} vaccination${if (records.size != 1) "s" else ""}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            Divider(color = Color(0xFFE0E0E0))
            
            Spacer(Modifier.height(12.dp))
            
            // Records List
            records.forEachIndexed { index, record ->
                key(record.vaccineName + record.childId + record.dateAdministered) {
                    VaccinationRecordItem(record, navController, onDeleted = onRecordDeleted)
                }
                if (index < records.lastIndex) {
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

/* -------------------- VACCINATION RECORD ITEM -------------------- */
@Composable
fun VaccinationRecordItem(
    record: com.example.vaxforsure.utils.VaccinationRecord,
    navController: NavController? = null,
    onDeleted: () -> Unit = {}
) {
    val context = LocalContext.current
    var showDetailsDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Vaccine Icon - Clickable to show details
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFE0F2F1), CircleShape)
                    .clickable { showDetailsDialog = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.MedicalServices,
                    contentDescription = null,
                    tint = Color(0xFF00BFA5),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(Modifier.width(12.dp))
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { showDetailsDialog = true }
            ) {
                Text(
                    text = record.vaccineName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF212121)
                )
                
                Spacer(Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (record.healthcareFacility.isNotEmpty()) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = record.healthcareFacility,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                
                Spacer(Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )
                    Text(
                        text = if (record.dateAdministered.isNotEmpty()) {
                            record.dateAdministered
                        } else if (record.completedAt > 0) {
                            val date = java.util.Date(record.completedAt)
                            val format = java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                            format.format(date)
                        } else {
                            "Date not available"
                        },
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                if (record.batchLotNumber.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Batch: ${record.batchLotNumber}",
                        fontSize = 11.sp,
                        color = Color(0xFF757575)
                    )
                }
            }
            
            // Delete Button
            IconButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFE91E63),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
    
    // Details Dialog
    if (showDetailsDialog) {
        AlertDialog(
            onDismissRequest = { showDetailsDialog = false },
            title = {
                Text(
                    text = record.vaccineName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column {
                    if (record.healthcareFacility.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = Color(0xFF00BFA5)
                            )
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Location",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = record.healthcareFacility,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color(0xFF00BFA5)
                        )
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Date Administered",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = if (record.dateAdministered.isNotEmpty()) {
                                    record.dateAdministered
                                } else if (record.completedAt > 0) {
                                    val date = java.util.Date(record.completedAt)
                                    val format = java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                                    format.format(date)
                                } else {
                                    "Date not available"
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    
                    if (record.batchLotNumber.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Description,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = Color(0xFF00BFA5)
                            )
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Batch/Lot Number",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = record.batchLotNumber,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDetailsDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
    
    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text("Delete Vaccination Record")
            },
            text = {
                Text("Are you sure you want to delete this vaccination record? The vaccine will show as pending in the dashboard.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        isDeleting = true
                        // Delete from local storage
                        VaccineManager.deleteVaccinationRecord(context, record.childId, record.vaccineName)
                        isDeleting = false
                        showDeleteDialog = false
                        onDeleted() // Trigger refresh
                        Toast.makeText(context, "Vaccination record deleted. Dashboard will show as pending.", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                    enabled = !isDeleting
                ) {
                    if (isDeleting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White
                        )
                    } else {
                        Text("Delete")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
