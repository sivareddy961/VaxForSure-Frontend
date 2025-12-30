# Backend Setup Guide - VaxForSure

## ✅ What Has Been Created

### Backend Files (XAMPP)
All files are located in: `C:\xampp\htdocs\vaxforsure\`

1. **Database Schema**
   - ✅ `database.sql` - Complete database structure with users table

2. **Configuration**
   - ✅ `config.php` - Database connection settings

3. **API Endpoints**
   - ✅ `api/auth/login.php` - Login authentication
   - ✅ `api/auth/register.php` - User registration

### Android Files
- ✅ `utils/ApiConstants.kt` - API URLs configuration
- ✅ `api/RetrofitClient.kt` - HTTP client setup
- ✅ `api/ApiService.kt` - API interface
- ✅ `models/ApiResponse.kt` - Data models
- ✅ `screens/auth/LoginScreen.kt` - Updated to use backend API
- ✅ `screens/auth/RegisterScreen.kt` - Updated to use backend API

---

## 🚀 Setup Instructions

### Step 1: Start XAMPP Services

1. Open **XAMPP Control Panel**
2. Start **Apache** (should turn GREEN ✅)
3. Start **MySQL** (should turn GREEN ✅)
4. Verify both are running

### Step 2: Create Database

1. Open browser and go to: **http://localhost:8080/phpmyadmin**
2. Click **"New"** on the left sidebar
3. Database name: `vaxforsure`
4. Collation: `utf8mb4_unicode_ci`
5. Click **"Create"**

### Step 3: Import Database Schema

1. In phpMyAdmin, select the `vaxforsure` database
2. Click on **"Import"** tab at the top
3. Click **"Choose File"** button
4. Navigate to: `C:\xampp\htdocs\vaxforsure\database.sql`
5. Click **"Go"** button at the bottom
6. You should see success message and 6 tables created:
   - ✅ `users`
   - ✅ `children`
   - ✅ `health_details`
   - ✅ `vaccinations`
   - ✅ `reminders`
   - ✅ `notifications`

### Step 4: Verify Database Setup

1. In phpMyAdmin, click on `vaxforsure` database
2. You should see all 6 tables listed
3. Click on `users` table to see its structure

### Step 5: Test Backend API

**Test Registration:**
- Open browser: http://localhost:8080/vaxforsure/api/auth/register.php
- Should see JSON response (may show error if no POST data, that's OK)

**Test Login:**
- Open browser: http://localhost:8080/vaxforsure/api/auth/login.php
- Should see JSON response

**Better Test with Postman:**
1. Install Postman
2. Create POST request: `http://localhost:8080/vaxforsure/api/auth/register.php`
3. Headers: `Content-Type: application/json`
4. Body (raw JSON):
```json
{
  "fullName": "Test User",
  "email": "test@example.com",
  "phone": "1234567890",
  "password": "password123"
}
```
5. Send request - should get success response

---

## 📱 Android App Configuration

### For Android Emulator:
✅ Already configured in `ApiConstants.kt`:
```kotlin
const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
```

### For Physical Device:
1. Find your computer's IP address:
   - Open Command Prompt
   - Run: `ipconfig`
   - Look for IPv4 Address (e.g., 192.168.1.100)

2. Update `ApiConstants.kt`:
```kotlin
const val BASE_URL = "http://192.168.1.100:8080/vaxforsure/api/"
```
⚠️ Replace `192.168.1.100` with your actual IP!

3. Ensure:
   - ✅ Android device and computer on same WiFi network
   - ✅ Windows Firewall allows Apache on port 8080
   - ✅ XAMPP Apache is running

---

## 🧪 Testing the Flow

### Test Registration:
1. Open Android app
2. Go to "Sign Up" / "Create Account"
3. Fill in:
   - Full Name: Test User
   - Email: test@example.com
   - Phone: 1234567890
   - Password: password123
   - Confirm Password: password123
4. Click "Create Account"
5. Should navigate directly to "Add Child Profile" screen ✅

### Test Login:
1. Open Android app
2. Go to "Sign In"
3. Enter:
   - Email: test@example.com (same email from registration)
   - Password: password123 (same password from registration)
4. Click "Sign In"
5. Should login successfully and navigate to Dashboard ✅

### Test Wrong Credentials:
1. Try login with wrong email or password
2. Should show error: "Invalid email or password" ✅

---

## 🔍 Troubleshooting

### Database Connection Error
**Error:** "Database connection failed"
**Solution:**
1. Check MySQL is running (GREEN in XAMPP)
2. Verify database `vaxforsure` exists
3. Check `config.php` credentials

### API Not Responding
**Error:** "Network error" or "Connection refused"
**Solution:**
1. Check Apache is running (GREEN in XAMPP)
2. Test URL in browser: http://localhost:8080/vaxforsure/api/auth/login.php
3. Check Android `ApiConstants.kt` has correct URL
4. For physical device, verify IP address is correct

### Login Fails with Correct Credentials
**Error:** "Invalid email or password"
**Solution:**
1. Check user exists in database (phpMyAdmin → users table)
2. Verify password is correct
3. Check email format is correct

### Registration Fails
**Error:** "Email already registered"
**Solution:**
- Email already exists in database
- Use different email or delete existing user from database

---

## 📋 Database Access

**phpMyAdmin URL:** http://localhost:8080/phpmyadmin

**Database Name:** `vaxforsure`

**View Users Table:**
1. Go to phpMyAdmin
2. Click `vaxforsure` database
3. Click `users` table
4. Click "Browse" to see all registered users

---

## ✅ Checklist

Before testing:
- [ ] XAMPP Control Panel open
- [ ] Apache started (GREEN ✅)
- [ ] MySQL started (GREEN ✅)
- [ ] Database `vaxforsure` created
- [ ] Database schema imported (`database.sql`)
- [ ] All 6 tables visible in phpMyAdmin
- [ ] Backend API files exist in `C:\xampp\htdocs\vaxforsure\`
- [ ] Android `ApiConstants.kt` has correct URL
- [ ] Test registration in app
- [ ] Test login with registered credentials
- [ ] Test login with wrong credentials (should show error)

---

## 🎉 Success!

If everything is set up correctly:
- ✅ Registration creates user in database
- ✅ Login validates credentials from database
- ✅ Wrong credentials show error message
- ✅ User session saved after login
- ✅ Navigation works correctly

Your backend is now fully functional! 🚀

