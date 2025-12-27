package com.example.vaxforsure.screens.export

import android.content.Context
import android.content.Intent
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File
import java.io.FileOutputStream

/* =========================================================
   DATA MODEL
   ========================================================= */

data class Child(
    val name: String,
    val dateOfBirth: String?
)

/* =========================================================
   MAIN SCREEN
   ========================================================= */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportRecordsScreen(
    selectedChild: Child?
) {

    val context = LocalContext.current

    /* ---------- COLORS ---------- */
    val screenBg = Color(0xFFF8FAFC)
    val cardBg = Color.White
    val primaryText = Color(0xFF0F172A)
    val secondaryText = Color(0xFF94A3B8)
    val teal = Color(0xFF0F766E)
    val noteBg = Color(0xFFD8DFF0)
    val noteText = Color(0xFF7C8DB5)

    /* ---------- EXPORT OPTIONS ---------- */
    val exportOptions = listOf(
        ExportOption(
            title = "Export as PDF",
            description = "Download vaccination records as a PDF file",
            icon = Icons.Default.Description
        ) { exportPdf(context, selectedChild) },

        ExportOption(
            title = "Email Records",
            description = "Send vaccination records to your email",
            icon = Icons.Default.Email
        ) { sendEmail(context, selectedChild) },

        ExportOption(
            title = "Share Records",
            description = "Share records with healthcare provider",
            icon = Icons.Default.Share
        ) { shareRecords(context, selectedChild) }
    )

    Scaffold(
        containerColor = screenBg,
        topBar = {
            TopAppBar(
                title = { Text("Export Records", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { /* navController.popBackStack() */ }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            /* ---------- CHILD CARD ---------- */
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(cardBg)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(teal, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                selectedChild?.name?.first()?.toString() ?: "S",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text(
                                selectedChild?.name ?: "Siva",
                                fontWeight = FontWeight.SemiBold,
                                color = primaryText
                            )
                            Text(
                                selectedChild?.dateOfBirth ?: "Oct 21, 2025",
                                fontSize = 13.sp,
                                color = secondaryText
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Exporting vaccination records for this child",
                        fontSize = 13.sp,
                        color = secondaryText
                    )
                }
            }

            /* ---------- OPTIONS ---------- */
            exportOptions.forEach { option ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable { option.action() },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(cardBg)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(teal, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(option.icon, option.title, tint = Color.White)
                        }

                        Spacer(Modifier.width(16.dp))

                        Column {
                            Text(option.title, fontWeight = FontWeight.SemiBold)
                            Text(option.description, fontSize = 13.sp, color = secondaryText)
                        }
                    }
                }
            }

            /* ---------- NOTE ---------- */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(noteBg, RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Text(
                    "Note: Exported records will include all completed vaccinations, dates, healthcare providers, and batch numbers.",
                    fontSize = 12.sp,
                    color = noteText
                )
            }
        }
    }
}

/* =========================================================
   EXPORT OPTION MODEL
   ========================================================= */

data class ExportOption(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val action: () -> Unit
)

/* =========================================================
   EXPORT FUNCTIONS
   ========================================================= */

fun exportPdf(context: Context, child: Child?) {
    try {
        val pdf = PdfDocument()
        val page = pdf.startPage(
            PdfDocument.PageInfo.Builder(300, 600, 1).create()
        )

        val canvas = page.canvas
        val paint = android.graphics.Paint().apply { textSize = 12f }

        canvas.drawText("Vaccination Records", 40f, 50f, paint)
        canvas.drawText("Child: ${child?.name ?: "Siva"}", 40f, 80f, paint)

        pdf.finishPage(page)

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "Vaccination_Records.pdf"
        )

        pdf.writeTo(FileOutputStream(file))
        pdf.close()

        Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        Toast.makeText(context, "PDF export failed", Toast.LENGTH_SHORT).show()
    }
}

fun sendEmail(context: Context, child: Child?) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Vaccination Records")
        putExtra(Intent.EXTRA_TEXT, "Vaccination records for ${child?.name ?: "child"}")
    }
    context.startActivity(Intent.createChooser(intent, "Send Email"))
}

fun shareRecords(context: Context, child: Child?) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Vaccination records of ${child?.name ?: "child"}")
    }
    context.startActivity(Intent.createChooser(intent, "Share Records"))
}
