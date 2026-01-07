package com.example.vaxforsure.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vaxforsure.screens.onboarding.*
import com.example.vaxforsure.screens.dashboard.DashboardScreen
import com.example.vaxforsure.screens.notifications.NotificationsScreen
import com.example.vaxforsure.screens.alerts.TodayAlertsScreen
import com.example.vaxforsure.screens.schedule.VaccinationScheduleScreen
import com.example.vaxforsure.screens.records.RecordsScreen
import com.example.vaxforsure.screens.export.ExportRecordsScreen
import com.example.vaxforsure.screens.export.Child
import com.example.vaxforsure.screens.settings.ReminderSettingsScreen
import com.example.vaxforsure.screens.reminder.AddCustomRemainderScreen
import com.example.vaxforsure.screens.profile.ProfileDetailsScreen // ✅ ADDED
import com.example.vaxforsure.screens.profile.EditProfileScreen
import com.example.vaxforsure.screens.vaccinedetails.VaccineDetailsScreen
import com.example.vaxforsure.screens.dashboard.UpcomingVaccinesScreen
import com.example.vaxforsure.screens.schedule.MarkAsCompletedScreen

/* -------------------- Navigation Destinations -------------------- */
object Destinations {
    const val SPLASH = "splash"
    const val WELCOME = "welcome"
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val OTP = "otp-verification"
    const val ADD_CHILD = "add-child"
    const val HEALTH_DETAILS = "health-details"
    const val PROFILE_CONFIRMATION = "profile-confirmation"
    const val FORGOT_PASSWORD = "forgot-password"
    const val RESET_CONFIRMATION = "reset-confirmation"
    const val RESET_PASSWORD = "reset-password"
    const val DASHBOARD = "dashboard"
    const val NOTIFICATIONS = "notifications"
    const val VACCINE_SCHEDULE = "vaccine-schedule"
    const val TODAY_ALERTS = "today-alerts"
    const val RECORDS = "records"
    const val EXPORT_RECORDS = "export-records"
    const val REMINDER_SETTINGS = "reminder-settings"
    const val ADD_CUSTOM_REMINDER = "add-custom-reminder"

    // ✅ NEW (DOES NOT AFFECT EXISTING ROUTES)
    const val PROFILE_DETAILS = "profile-details"
    const val VACCINE_DETAILS = "vaccine-details"
    const val UPCOMING_VACCINES = "upcoming-vaccines"
    const val MARK_AS_COMPLETED = "mark-as-completed"
    const val EDIT_PROFILE = "edit-profile"
}

/* -------------------- App Nav Host -------------------- */
@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.SPLASH
    ) {

        composable(Destinations.SPLASH) {
            SplashScreen(navController)
        }

        composable(Destinations.WELCOME) {
            WelcomeScreen(
                isDark = false,
                onToggleTheme = {},
                onGetStarted = { navController.navigate(Destinations.ONBOARDING) },
                onLogin = { navController.navigate(Destinations.LOGIN) }
            )
        }

        composable(Destinations.ONBOARDING) {
            OnboardingScreen {
                navController.navigate(Destinations.REGISTER)
            }
        }

        composable(Destinations.LOGIN) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onLogin = { navController.navigate(Destinations.DASHBOARD) },
                onForgotPassword = { navController.navigate(Destinations.FORGOT_PASSWORD) },
                onGoogleLogin = { navController.navigate(Destinations.DASHBOARD) },
                onSignUp = { navController.navigate(Destinations.REGISTER) }
            )
        }

        composable(Destinations.REGISTER) {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onCreateAccount = { navController.navigate(Destinations.ADD_CHILD) },
                onSignIn = { navController.navigate(Destinations.LOGIN) }
            )
        }

        composable(Destinations.OTP) {
            OTPVerificationScreen(
                onBack = { navController.popBackStack() },
                onVerified = { navController.navigate(Destinations.ADD_CHILD) },
                onPasswordResetVerified = { navController.navigate(Destinations.RESET_PASSWORD) }
            )
        }

        composable(Destinations.ADD_CHILD) {
            AddChildProfileScreen(
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Destinations.HEALTH_DETAILS) }
            )
        }

        composable(Destinations.HEALTH_DETAILS) {
            HealthDetailsScreen(
                onBack = { navController.popBackStack() },
                onSkip = { navController.navigate(Destinations.DASHBOARD) },
                onSubmit = { navController.navigate(Destinations.PROFILE_CONFIRMATION) }
            )
        }

        composable(Destinations.PROFILE_CONFIRMATION) {
            ProfileConfirmationScreen(
                onGoToDashboard = {
                    navController.navigate(Destinations.DASHBOARD) {
                        popUpTo(Destinations.SPLASH) { inclusive = true }
                    }
                },
                onAddAnotherChild = {
                    navController.navigate(Destinations.ADD_CHILD)
                }
            )
        }

        composable(Destinations.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onResetConfirmation = {
                    navController.navigate(Destinations.OTP)
                }
            )
        }

        composable(Destinations.RESET_CONFIRMATION) {
            ResetConfirmationScreen(
                onBackToLogin = { navController.navigate(Destinations.LOGIN) },
                onResend = { navController.navigate(Destinations.FORGOT_PASSWORD) }
            )
        }

        composable(Destinations.RESET_PASSWORD) {
            ResetPasswordScreen(
                onBack = { navController.popBackStack() },
                onPasswordReset = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.FORGOT_PASSWORD) { inclusive = true }
                    }
                }
            )
        }

        /* ---------------- DASHBOARD ---------------- */
        composable(Destinations.DASHBOARD) {
            DashboardScreen(
                profileName = "Siva",
                onNotificationsClick = {
                    navController.navigate(Destinations.NOTIFICATIONS)
                },
                onScheduleClick = {
                    navController.navigate(Destinations.VACCINE_SCHEDULE)
                },
                navController = navController
            )
        }

        /* ---------------- PROFILE DETAILS (NEW) ---------------- */
        composable(Destinations.PROFILE_DETAILS) {
            ProfileDetailsScreen(navController = navController)
        }

        /* ---------------- EDIT PROFILE ---------------- */
        composable(
            route = "${Destinations.EDIT_PROFILE}/{childId}",
            arguments = listOf(
                navArgument("childId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val childId = backStackEntry.arguments?.getInt("childId") ?: 0
            EditProfileScreen(navController = navController, childId = childId)
        }

        /* ---------------- VACCINE DETAILS ---------------- */
        composable(
            route = "${Destinations.VACCINE_DETAILS}/{vaccineName}",
            arguments = listOf(
                navArgument("vaccineName") {
                    type = NavType.StringType
                    defaultValue = "BCG"
                }
            )
        ) { backStackEntry ->
            val vaccineName = backStackEntry.arguments?.getString("vaccineName") ?: "BCG"
            // Get childId from savedStateHandle if coming from records
            val childId = backStackEntry.savedStateHandle.get<Int>("childId") ?: 0
            VaccineDetailsScreen(
                navController = navController,
                vaccineName = vaccineName,
                childId = childId
            )
        }
        composable(Destinations.VACCINE_DETAILS) {
            VaccineDetailsScreen(navController = navController)
        }

        /* ---------------- UPCOMING VACCINES ---------------- */
        composable(Destinations.UPCOMING_VACCINES) {
            UpcomingVaccinesScreen(navController = navController)
        }

        /* ---------------- MARK AS COMPLETED ---------------- */
        composable(
            route = "${Destinations.MARK_AS_COMPLETED}/{vaccineName}",
            arguments = listOf(
                navArgument("vaccineName") {
                    type = NavType.StringType
                    defaultValue = "BCG"
                }
            )
        ) { backStackEntry ->
            val vaccineName = backStackEntry.arguments?.getString("vaccineName") ?: "BCG"
            MarkAsCompletedScreen(
                navController = navController,
                vaccineName = vaccineName
            )
        }

        /* ---------------- RECORDS ---------------- */
        composable(Destinations.RECORDS) {
            RecordsScreen(
                navController = navController,
                selectedChild = null,
                childRecords = emptyList()
            )
        }

        /* ---------------- EXPORT RECORDS ---------------- */
        composable(Destinations.EXPORT_RECORDS) {
            ExportRecordsScreen(
                selectedChild = Child(
                    name = "Siva",
                    dateOfBirth = "Oct 21, 2025"
                )
            )
        }

        /* ---------------- VACCINE SCHEDULE ---------------- */
        composable(Destinations.VACCINE_SCHEDULE) {
            VaccinationScheduleScreen(navController)
        }

        /* ---------------- NOTIFICATIONS ---------------- */
        composable(Destinations.NOTIFICATIONS) {
            NotificationsScreen(navController)
        }

        /* ---------------- TODAY ALERTS ---------------- */
        composable(Destinations.TODAY_ALERTS) {
            TodayAlertsScreen(navController)
        }

        /* ---------------- REMINDER SETTINGS ---------------- */
        composable(Destinations.REMINDER_SETTINGS) {
            ReminderSettingsScreen(
                navController = navController,
                isDarkTheme = false
            )
        }

        /* ---------------- ADD CUSTOM REMINDER ---------------- */
        composable(Destinations.ADD_CUSTOM_REMINDER) {
            AddCustomRemainderScreen(
                navController = navController,
                isDarkTheme = false
            )
        }
    }
}
