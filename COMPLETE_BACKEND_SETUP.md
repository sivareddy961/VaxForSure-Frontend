# Complete Backend Setup Guide - Step by Step

## 📋 Prerequisites Checklist

Before starting, ensure you have:
- ✅ XAMPP installed on your computer
- ✅ Android Studio with your project open
- ✅ Basic understanding of file paths

---

## 🚀 PART 1: Start XAMPP Services

### Step 1: Open XAMPP Control Panel

1. **Find XAMPP Control Panel**
   - Press `Windows Key` and type "XAMPP"
   - Click on "XAMPP Control Panel"
   - Or navigate to: `C:\xampp\xampp-control.exe`

2. **Verify XAMPP Control Panel is Open**
   - You should see a window with Apache, MySQL, FileZilla, etc.

### Step 2: Start Apache Server

1. **Find Apache** in the list
2. **Click "Start"** button next to Apache
3. **Wait for status to turn GREEN** ✅
4. **Verify:**
   - Status shows "Running"
   - Port shows `8080` (or `80`)

### Step 3: Start MySQL Database

1. **Find MySQL** in the list
2. **Click "Start"** button next to MySQL
3. **Wait for status to turn GREEN** ✅
4. **Verify:**
   - Status shows "Running"
   - Port shows `3306`

### Step 4: Verify Services are Running

**Both Apache and MySQL should be GREEN** ✅

**If Apache won't start:**
- Port 8080 (or 80) might be in use
- Close other applications using that port
- Or change Apache port in XAMPP config

**If MySQL won't start:**
- Port 3306 might be in use
- Close other MySQL services
- Restart XAMPP Control Panel as Administrator

---

## 🗄️ PART 2: Create Database

### Step 1: Open phpMyAdmin

1. **Open your web browser** (Chrome, Firefox, Edge, etc.)
2. **Go to:** http://localhost:8080/phpmyadmin
   - If port 8080 doesn't work, try: http://localhost/phpmyadmin
3. **phpMyAdmin should open** (database management interface)

### Step 2: Create Database

1. **Click "New"** on the left sidebar (or top menu)
2. **Database name:** Type `vaxforsure`
3. **Collation:** Select `utf8mb4_unicode_ci` (from dropdown)
4. **Click "Create"** button
5. **Verify:** You should see `vaxforsure` database in the left sidebar

### Step 3: Import Database Schema

1. **Click on `vaxforsure`** database in the left sidebar (to select it)
2. **Click "Import"** tab at the top
3. **Click "Choose File"** button
4. **Navigate to:** `C:\xampp\htdocs\vaxforsure\database.sql`
   - If file doesn't exist, check the file location
5. **Click "Go"** button at the bottom
6. **Wait for import to complete**
7. **Verify Success:**
   - You should see "Import has been successfully finished" message
   - You should see 6 tables created:
     - ✅ `users`
     - ✅ `children`
     - ✅ `health_details`
     - ✅ `vaccinations`
     - ✅ `reminders`
     - ✅ `notifications`

### Step 4: Verify Database Structure

1. **Click on `vaxforsure`** database in left sidebar
2. **You should see 6 tables listed**
3. **Click on `users` table** to see its structure
   - Should have columns: id, full_name, email, phone, password, etc.

---

## 🔧 PART 3: Verify Backend Files

### Step 1: Check Backend Files Exist

**Navigate to:** `C:\xampp\htdocs\vaxforsure\`

**You should have these files:**
- ✅ `config.php` - Database configuration
- ✅ `database.sql` - Database schema
- ✅ `api/auth/login.php` - Login endpoint
- ✅ `api/auth/register.php` - Registration endpoint
- ✅ `README.md` - API documentation

### Step 2: Test Backend API (Browser)

**Test Registration Endpoint:**
1. Open browser
2. Go to: http://localhost:8080/vaxforsure/api/auth/register.php
3. **Expected:** JSON response (may show error if no POST data, that's OK)

**Test Login Endpoint:**
1. Open browser
2. Go to: http://localhost:8080/vaxforsure/api/auth/login.php
3. **Expected:** JSON response

**If you see errors:**
- Check Apache is running (GREEN in XAMPP)
- Check file paths are correct
- Check `config.php` has correct database settings

---

## 📱 PART 4: Configure Android App

### Step 1: Check API Configuration

**File:** `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`

**For Android Emulator (Default):**
```kotlin
const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
```
✅ This is already configured correctly!

**For Physical Device:**
1. Find your computer's IP address:
   - Open Command Prompt (Press `Win + R`, type `cmd`, press Enter)
   - Type: `ipconfig` and press Enter
   - Look for "IPv4 Address" under your network adapter
   - Example: `192.168.1.100`

2. Update `ApiConstants.kt`:
```kotlin
// Comment out emulator URL:
// const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"

// Add your computer's IP:
const val BASE_URL = "http://192.168.1.100:8080/vaxforsure/api/"
```
⚠️ Replace `192.168.1.100` with your actual IP address!

### Step 2: Verify Dependencies

**File:** `app/build.gradle.kts`

**Ensure these dependencies exist:**
```kotlin
// Retrofit for API calls
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// OkHttp for HTTP client
implementation("com.squareup.okhttp3:okhttp:4.12.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// Gson for JSON parsing
implementation("com.google.code.gson:gson:2.10.1")
```

**If missing:**
1. Add them to `build.gradle.kts`
2. Click "Sync Now" in Android Studio

### Step 3: Check Network Security Config

**File:** `app/src/main/res/xml/network_security_config.xml`

**Should allow HTTP connections:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true">
        <trust-anchors>
            <certificates src="system" />
        </trust-anchors>
    </base-config>
</network-security-config>
```

**File:** `app/src/main/AndroidManifest.xml`

**Should have internet permission:**
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## 🧪 PART 5: Test Complete Flow

### Test 1: Test Backend with Postman (Recommended)

**Install Postman** (if not installed):
- Download from: https://www.postman.com/downloads/

**Test Registration:**
1. Open Postman
2. Create new request: **POST**
3. URL: `http://localhost:8080/vaxforsure/api/auth/register.php`
4. Headers tab:
   - Key: `Content-Type`
   - Value: `application/json`
5. Body tab:
   - Select "raw"
   - Select "JSON" from dropdown
   - Paste this:
```json
{
  "fullName": "Test User",
  "email": "test@example.com",
  "phone": "1234567890",
  "password": "password123"
}
```
6. Click "Send"
7. **Expected Response:**
```json
{
  "success": true,
  "message": "Registration successful",
  "data": {
    "user": {
      "id": 1,
      "full_name": "Test User",
      "email": "test@example.com",
      "phone": "1234567890",
      "email_verified": 0
    }
  }
}
```

**Test Login:**
1. Create new request: **POST**
2. URL: `http://localhost:8080/vaxforsure/api/auth/login.php`
3. Headers: `Content-Type: application/json`
4. Body (raw JSON):
```json
{
  "email": "test@example.com",
  "password": "password123"
}
```
5. Click "Send"
6. **Expected Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "user": {
      "id": 1,
      "full_name": "Test User",
      "email": "test@example.com",
      "phone": "1234567890",
      "email_verified": 0
    }
  }
}
```

**Test Wrong Password:**
1. Same login request
2. Change password to: `wrongpassword`
3. **Expected Response:**
```json
{
  "success": false,
  "message": "Invalid email or password"
}
```

### Test 2: Test in Android App

**Test Registration:**
1. **Run app** in Android Studio (Emulator or Physical Device)
2. Navigate to "Sign Up" / "Create Account"
3. Fill form:
   - Full Name: `Test User`
   - Email: `test@example.com`
   - Phone: `1234567890`
   - Password: `password123`
   - Confirm Password: `password123`
4. Click "Create Account"
5. **Expected:**
   - ✅ Shows "Registration successful" toast
   - ✅ Navigates to "Add Child Profile" screen
   - ✅ User saved in database (check phpMyAdmin)

**Test Login (Correct Credentials):**
1. Navigate to "Sign In"
2. Enter:
   - Email: `test@example.com`
   - Password: `password123`
3. Click "Sign In"
4. **Expected:**
   - ✅ Shows "Login successful" toast
   - ✅ Navigates to Dashboard
   - ✅ User session saved

**Test Login (Wrong Credentials):**
1. Navigate to "Sign In"
2. Enter:
   - Email: `test@example.com`
   - Password: `wrongpassword`
3. Click "Sign In"
4. **Expected:**
   - ✅ Shows "Invalid email or password" error
   - ✅ Stays on login screen

---

## 🔍 PART 6: Troubleshooting

### Problem: Apache Won't Start

**Error:** Port 8080 (or 80) already in use

**Solutions:**
1. **Check what's using the port:**
   - Open Command Prompt
   - Type: `netstat -ano | findstr :8080`
   - Find the process ID (PID)
   - End that process

2. **Change Apache port:**
   - Open XAMPP Control Panel
   - Click "Config" next to Apache
   - Select "httpd.conf"
   - Find `Listen 8080` and change to `Listen 8081`
   - Save and restart Apache
   - Update Android `ApiConstants.kt` with new port

### Problem: MySQL Won't Start

**Error:** Port 3306 already in use

**Solutions:**
1. **Close other MySQL services:**
   - Check Windows Services (services.msc)
   - Stop any MySQL services
   - Restart MySQL in XAMPP

2. **Change MySQL port:**
   - Open XAMPP Control Panel
   - Click "Config" next to MySQL
   - Select "my.ini"
   - Find `port=3306` and change to `port=3307`
   - Update `config.php` with new port
   - Restart MySQL

### Problem: Database Connection Failed

**Error:** "Database connection failed" in API response

**Solutions:**
1. **Check MySQL is running** (GREEN in XAMPP)
2. **Verify database exists:**
   - Go to phpMyAdmin
   - Check `vaxforsure` database exists
3. **Check `config.php`:**
   - Open: `C:\xampp\htdocs\vaxforsure\config.php`
   - Verify:
     ```php
     define('DB_HOST', 'localhost');
     define('DB_USER', 'root');
     define('DB_PASS', ''); // Empty for default XAMPP
     define('DB_NAME', 'vaxforsure');
     ```
4. **If you set MySQL password:**
   - Update `DB_PASS` in `config.php` with your password

### Problem: API Not Responding

**Error:** "Network error" or "Connection refused" in Android app

**Solutions:**

**For Android Emulator:**
1. **Check URL in `ApiConstants.kt`:**
   ```kotlin
   const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
   ```
   ✅ Should be `10.0.2.2` (not `localhost`)

2. **Test URL in browser:**
   - http://localhost:8080/vaxforsure/api/auth/login.php
   - Should show JSON response

**For Physical Device:**
1. **Find your computer's IP:**
   - Command Prompt: `ipconfig`
   - Look for IPv4 Address

2. **Update `ApiConstants.kt`:**
   ```kotlin
   const val BASE_URL = "http://YOUR_IP:8080/vaxforsure/api/"
   ```

3. **Ensure same WiFi network:**
   - Android device and computer must be on same WiFi

4. **Check Windows Firewall:**
   - Allow Apache through firewall
   - Or temporarily disable firewall to test

5. **Test URL on device browser:**
   - Open browser on Android device
   - Go to: `http://YOUR_IP:8080/vaxforsure/api/auth/login.php`
   - Should show JSON response

### Problem: Login Fails with Correct Credentials

**Error:** "Invalid email or password"

**Solutions:**
1. **Check user exists in database:**
   - phpMyAdmin → `vaxforsure` → `users` table
   - Click "Browse" to see users
   - Verify email exists

2. **Check password:**
   - Passwords are hashed in database
   - Try registering again with same email
   - Or delete user and register again

3. **Check email format:**
   - Ensure email is valid format
   - No spaces before/after

### Problem: Registration Fails

**Error:** "Email already registered"

**Solutions:**
1. **Use different email** for testing
2. **Or delete existing user:**
   - phpMyAdmin → `users` table
   - Select user → Delete

---

## ✅ Final Checklist

Before considering setup complete:

- [ ] XAMPP Control Panel open
- [ ] Apache started (GREEN ✅)
- [ ] MySQL started (GREEN ✅)
- [ ] phpMyAdmin opens: http://localhost:8080/phpmyadmin
- [ ] Database `vaxforsure` created
- [ ] Database schema imported (6 tables visible)
- [ ] Backend files exist in `C:\xampp\htdocs\vaxforsure\`
- [ ] API endpoints accessible in browser
- [ ] Postman tests pass (registration & login)
- [ ] Android `ApiConstants.kt` configured correctly
- [ ] Android app registration works
- [ ] Android app login works (correct credentials)
- [ ] Android app shows error (wrong credentials)
- [ ] User data visible in phpMyAdmin `users` table

---

## 🎉 Success Indicators

You'll know everything is working when:

1. ✅ **Registration:**
   - User can register in app
   - User appears in database (phpMyAdmin)
   - Navigates to "Add Child Profile"

2. ✅ **Login (Correct):**
   - User can login with registered credentials
   - Shows "Login successful"
   - Navigates to Dashboard

3. ✅ **Login (Wrong):**
   - Shows "Invalid email or password" error
   - Stays on login screen

4. ✅ **Database:**
   - Users visible in `users` table
   - Data persists after app restart

---

## 📞 Quick Reference

**XAMPP Control Panel:** `C:\xampp\xampp-control.exe`

**phpMyAdmin:** http://localhost:8080/phpmyadmin

**Backend Files:** `C:\xampp\htdocs\vaxforsure\`

**API Base URL (Emulator):** `http://10.0.2.2:8080/vaxforsure/api/`

**API Base URL (Physical Device):** `http://YOUR_IP:8080/vaxforsure/api/`

**Test Registration:** http://localhost:8080/vaxforsure/api/auth/register.php

**Test Login:** http://localhost:8080/vaxforsure/api/auth/login.php

---

## 🚀 You're All Set!

Your backend is now fully configured and connected to your Android app!

If you encounter any issues, refer to the troubleshooting section above.

Happy coding! 🎉

