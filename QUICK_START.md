# Quick Start Guide: Connect Android App to XAMPP Backend

## 🚀 Step-by-Step Setup (15 minutes)

### Part 1: XAMPP Setup (5 minutes)

1. **Start XAMPP**
   - Open XAMPP Control Panel
   - Click **Start** for Apache and MySQL (both should be green)

2. **Create Database**
   - Open: http://localhost/phpmyadmin
   - Click **"New"** → Database name: `vaxforsure` → **Create**
   - Click **"Import"** tab
   - Choose file: `C:\xampp\htdocs\vaxforsure\database.sql`
   - Click **"Go"**

3. **Verify Backend Works**
   - Open: http://localhost/vaxforsure/api/vaccines/schedule.php
   - Should see JSON data (if you see this, backend is working! ✅)

---

### Part 2: Android Studio Setup (5 minutes)

1. **Sync Gradle**
   - Open Android Studio
   - Click **"Sync Now"** when prompted
   - Wait for sync to complete

2. **Update API URL (if using physical device)**
   - Find your computer's IP: Run `ipconfig` in Command Prompt
   - Look for **IPv4 Address** (e.g., 192.168.1.100)
   - Open: `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`
   - Change line 5:
   ```kotlin
   // For emulator (default):
   const val BASE_URL = "http://10.0.2.2/vaxforsure/api/"
   
   // For physical device (update with your IP):
   // const val BASE_URL = "http://192.168.1.100/vaxforsure/api/"
   ```

3. **Verify Files Created**
   - ✅ `ApiConstants.kt` - API URLs
   - ✅ `ApiResponse.kt` - Data models
   - ✅ `ApiService.kt` - API interface
   - ✅ `RetrofitClient.kt` - HTTP client
   - ✅ `network_security_config.xml` - Network config
   - ✅ `AndroidManifest.xml` - Updated with permissions

---

### Part 3: Test Connection (5 minutes)

#### Option A: Test in Postman First (Recommended)

1. **Install Postman** (if not installed)
   - Download: https://www.postman.com/downloads/

2. **Test Registration API**
   - Method: **POST**
   - URL: `http://localhost/vaxforsure/api/auth/register.php`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):
   ```json
   {
     "full_name": "Test User",
     "email": "test@example.com",
     "phone": "1234567890",
     "password": "password123",
     "confirm_password": "password123"
   }
   ```
   - Click **Send**
   - Should get success response ✅

3. **Test Login API**
   - Method: **POST**
   - URL: `http://localhost/vaxforsure/api/auth/login.php`
   - Headers: `Content-Type: application/json`
   - Body:
   ```json
   {
     "email": "test@example.com",
     "password": "password123"
   }
   ```
   - Should get user data ✅

#### Option B: Test in Android App

1. **Update LoginScreen.kt**
   - Add API call code (see SETUP_GUIDE.md for full example)
   - Run app on emulator or device
   - Try logging in with test credentials

---

## 🔧 Common Issues & Fixes

### ❌ "Connection refused"
**Fix:**
- ✅ Check XAMPP Apache is running (green)
- ✅ Test in browser: http://localhost/vaxforsure/api/vaccines/schedule.php
- ✅ For emulator: Use `10.0.2.2` not `localhost`
- ✅ For device: Use computer's IP, not `localhost`

### ❌ "Network Security Config" error
**Fix:**
- ✅ Check `network_security_config.xml` exists in `app/src/main/res/xml/`
- ✅ Check `AndroidManifest.xml` has `android:networkSecurityConfig="@xml/network_security_config"`

### ❌ "404 Not Found"
**Fix:**
- ✅ Verify files are in `C:\xampp\htdocs\vaxforsure\`
- ✅ Test: http://localhost/vaxforsure/config.php (should not error)

### ❌ "Database connection failed"
**Fix:**
- ✅ Check MySQL is running in XAMPP
- ✅ Verify database `vaxforsure` exists
- ✅ Check `config.php` DB credentials

---

## 📱 Testing URLs

### For Browser/Postman:
- Base URL: `http://localhost/vaxforsure/api/`
- Test: `http://localhost/vaxforsure/api/vaccines/schedule.php`

### For Android Emulator:
- Base URL: `http://10.0.2.2/vaxforsure/api/`

### For Physical Device:
- Base URL: `http://YOUR_COMPUTER_IP/vaxforsure/api/`
- Find IP: Run `ipconfig` → Look for IPv4 Address

---

## ✅ Checklist

- [ ] XAMPP Apache and MySQL running (green)
- [ ] Database `vaxforsure` created and imported
- [ ] Backend test URL works in browser
- [ ] Android Studio Gradle synced
- [ ] API URL updated in `ApiConstants.kt`
- [ ] Test API in Postman
- [ ] Test login in Android app

---

## 📚 Next Steps

1. **Read Full Guide:** `C:\xampp\htdocs\vaxforsure\SETUP_GUIDE.md`
2. **API Documentation:** `C:\xampp\htdocs\vaxforsure\README.md`
3. **Update Android Screens:** Add API calls to LoginScreen, RegisterScreen, etc.

---

## 🆘 Need Help?

1. Check `SETUP_GUIDE.md` for detailed steps
2. Test backend in Postman first
3. Check Logcat in Android Studio for errors
4. Verify all files are in correct locations

**Good luck! 🚀**

