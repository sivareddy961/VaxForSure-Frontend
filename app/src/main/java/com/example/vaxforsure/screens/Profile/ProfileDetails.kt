package com.example.vaxforsure.screens.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vaxforsure.utils.PreferenceManager
import com.example.vaxforsure.utils.ChildManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/* -------------------- DATA MODELS -------------------- */
data class Child(
    val id: Int,
    val user_id: Int,
    val name: String,
    val date_of_birth: String,
    val gender: String,
    val birth_weight: Double?,
    val birth_height: Double?,
    val blood_group: String?,
    val created_at: String,
    val updated_at: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailsScreen(
    navController: NavController? = null
) {
    val context = LocalContext.current
    var children by remember { mutableStateOf<List<Child>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val userId = PreferenceManager.getUserId(context)

    // Function to load children - removes duplicates and fake data
    fun loadChildren() {
        // Load all children from ChildManager (already filtered by user_id and duplicates removed)
        children = ChildManager.getAllChildren(context)
        
        // Additional cleanup: ensure only valid children for current user
        children = children.filter { child ->
            child.user_id == userId && 
            child.user_id > 0 &&
            child.name.isNotBlank() && 
            child.date_of_birth.isNotBlank()
        }
    }

    // Reload children whenever screen is displayed and clean duplicates
    LaunchedEffect(Unit) {
        delay(500) // Simulate loading
        // Clean duplicates first
        ChildManager.removeDuplicates(context)
        // Then load children
        loadChildren()
        isLoading = false
    }

    val backgroundColor = Color(0xFFF7FBFB)
    val cardBorderColor = Color(0xFFD6EAEA)
    val primaryGreen = Color(0xFF4DB6AC)

    // Get parent/user name
    val parentName = PreferenceManager.getUserName(context).ifEmpty { "Parent" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = parentName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Children Profiles",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { 
                        navController?.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController?.navigate(com.example.vaxforsure.navigation.Destinations.ADD_CHILD)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Child",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryGreen
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(padding)
                .padding(16.dp)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (children.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "No children added yet",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Click + to add a child profile",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                Column {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(children) { child ->
                            ChildProfileCard(
                                name = child.name,
                                age = child.date_of_birth,
                                gender = child.gender,
                                navController = navController,
                                childId = child.id
                            )
                        }
                    }
                    
                    // Logout Button at Bottom
                    Spacer(modifier = Modifier.height(24.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                PreferenceManager.clearSession(context)
                                navController?.navigate(com.example.vaxforsure.navigation.Destinations.LOGIN) {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(4.dp),
                        border = BorderStroke(1.dp, Color(0xFFE0F2F1))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF00BFA5),
                                            Color(0xFF00897B)
                                        )
                                    )
                                )
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(
                                            Color.White.copy(alpha = 0.2f),
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Logout",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChildProfileCard(
    name: String,
    age: String,
    gender: String,
    navController: NavController? = null,
    childId: Int = 0
) {

    val primaryGreen = Color(0xFF4DB6AC)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFD6EAEA),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(primaryGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ChildCare,
                        contentDescription = "Child",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Name & details
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Age",
                            modifier = Modifier.size(14.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = age,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = gender,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // Edit icon
                IconButton(onClick = {
                    navController?.navigate("${com.example.vaxforsure.navigation.Destinations.EDIT_PROFILE}/$childId")
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = primaryGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // View Profile Button
            Button(
                onClick = { 
                    navController?.navigate("${com.example.vaxforsure.navigation.Destinations.EDIT_PROFILE}/$childId")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2B2B2B)
                )
            ) {
                Text(
                    text = "View Profile",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
