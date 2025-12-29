# 🔧 Fix Network Error - Quick Solutions

## 🚨 Most Common Causes (Check These First!)

### ✅ Issue 1: XAMPP Apache Not Running

**Check:**
1. Open XAMPP Control Panel
2. Is Apache **GREEN**? ✅
3. If RED → Click "Start" → Wait for GREEN

**Test:**
- Open: `http://localhost:8080/vaxforsure/api/vaccines/schedule.php`
- Should see JSON data
- If error → Apache not running or wrong port

---

### ✅ Issue 2: Backend Files Missing

**Check:**
1. Open File Explorer
2. Go to: `C:\xampp\htdocs\vaxforsure\`
3. Check these files exist:
   - ✅ `config.php`
   - ✅ `api/auth/register.php`
   - ✅ `api/auth/login.php`

**If missing:**
- Backend files need to be copied there
- Or recreate backend setup

---

### ✅ Issue 3: Wrong URL in Android App

**Current URL (for Emulator):**
```kotlin
const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
```

**Check:**
- ✅ Port 8080 included
- ✅ Using `10.0.2.2` (not `localhost`)
- ✅ Trailing slash `/` at end

**If testing on Physical Device:**
- Must use your computer's IP
- Find IP: Run `ipconfig` in Command Prompt
- Update: `http://YOUR_IP:8080/vaxforsure/api/`

---

### ✅ Issue 4: Check Logcat for Exact Error

**Steps:**
1. Open Android Studio
2. Run app
3. Try to register
4. Open **Logcat** tab (bottom)
5. Filter by: `OkHttp` or `Retrofit`
6. Look for error messages

**Common Logcat Errors:**

```
❌ "Connection refused"
   → XAMPP Apache not running
   → Fix: Start Apache in XAMPP

❌ "404 Not Found"  
   → API file doesn't exist
   → Fix: Check backend files in C:\xampp\htdocs\vaxforsure\

❌ "UnknownHostException: 10.0.2.2"
   → Wrong URL or emulator issue
   → Fix: Check URL in ApiConstants.kt

❌ "Timeout"
   → Firewall or network issue
   → Fix: Check firewall settings
```

---

## 🔍 Step-by-Step Debugging

### STEP 1: Test Backend First (Browser)

**Test 1: XAMPP Dashboard**
```
http://localhost:8080
```
✅ Should see XAMPP welcome page

**Test 2: phpMyAdmin**
```
http://localhost:8080/phpmyadmin
```
✅ Should open phpMyAdmin

**Test 3: API Endpoint**
```
http://localhost:8080/vaxforsure/api/vaccines/schedule.php
```
✅ Should return JSON data

**If Test 3 fails:**
- Backend files missing
- Need to setup backend files

---

### STEP 2: Test with Postman

**Before testing in app, test in Postman:**

1. **Install Postman** (if needed)
2. **Create POST Request:**
   - URL: `http://localhost:8080/vaxforsure/api/auth/register.php`
   - Method: POST
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):
     ```json
     {
       "full_name": "Test User",
       "email": "test@test.com",
       "phone": "1234567890",
       "password": "password123",
       "confirm_password": "password123"
     }
     ```
3. **Click Send**
4. **Check Response:**
   - ✅ Success (200 OK) = Backend works, Android issue
   - ❌ Error = Backend issue, fix backend first

---

### STEP 3: Check Android App Configuration

**Verify ApiConstants.kt:**
```kotlin
// Should be exactly this for emulator:
const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
```

**Verify AndroidManifest.xml:**
```xml
<!-- Should have these: -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

**Verify network_security_config.xml exists:**
- Path: `app/src/main/res/xml/network_security_config.xml`
- Should allow HTTP for `10.0.2.2`

---

### STEP 4: Check Logcat Output

**In Android Studio:**
1. Run app
2. Try registration
3. Open **Logcat** tab
4. Look for these lines:

**Success (should see):**
```
D/OkHttp: --> POST http://10.0.2.2:8080/vaxforsure/api/auth/register.php
D/OkHttp: <-- 200 OK http://10.0.2.2:8080/vaxforsure/api/auth/register.php
```

**Error (common):**
```
D/OkHttp: <-- HTTP FAILED: java.net.ConnectException: Connection refused
```
→ XAMPP not running

```
D/OkHttp: <-- 404 Not Found
```
→ Backend file missing

---

## 🛠️ Quick Fixes

### Fix 1: Restart Everything
```
1. Stop XAMPP Apache and MySQL
2. Close Android Studio
3. Start XAMPP Apache and MySQL (GREEN)
4. Open Android Studio
5. Run app again
```

### Fix 2: Verify Backend Files
```
1. Check: C:\xampp\htdocs\vaxforsure\api\auth\register.php exists
2. If missing → Copy backend files there
3. Test: http://localhost:8080/vaxforsure/api/auth/register.php
```

### Fix 3: Check Port Number
```
1. Verify XAMPP Apache port is 8080
2. Check ApiConstants.kt has :8080
3. Test: http://localhost:8080 (should work)
```

### Fix 4: For Physical Device
```
1. Find your IP: ipconfig
2. Update ApiConstants.kt: http://YOUR_IP:8080/vaxforsure/api/
3. Device and computer on same WiFi
4. Test in device browser first
```

---

## ✅ Checklist

Before reporting error, check:

- [ ] XAMPP Apache running (GREEN) ✅
- [ ] XAMPP MySQL running (GREEN) ✅
- [ ] Backend test works: http://localhost:8080/vaxforsure/api/vaccines/schedule.php ✅
- [ ] Postman test works (if using) ✅
- [ ] ApiConstants.kt has correct URL with port 8080 ✅
- [ ] Testing on emulator (not physical device) ✅
- [ ] Checked Logcat for specific error ✅
- [ ] Backend files exist in C:\xampp\htdocs\vaxforsure\ ✅

---

## 🆘 Still Not Working?

**Get Exact Error:**
1. Open Android Studio Logcat
2. Try registration
3. Copy the exact error message
4. Look for:
   - Connection refused
   - 404 Not Found
   - Timeout
   - UnknownHostException

**Then check corresponding fix above!**

---

## 📱 Testing on Physical Device?

**Additional Steps:**
1. Find computer IP: `ipconfig` → IPv4 Address
2. Update `ApiConstants.kt`:
   ```kotlin
   const val BASE_URL = "http://192.168.1.XXX:8080/vaxforsure/api/"
   ```
3. Test in device browser:
   `http://YOUR_IP:8080/vaxforsure/api/vaccines/schedule.php`
4. Device and computer on same WiFi
5. Windows Firewall allows Apache

---

**Most likely issue: XAMPP Apache not running or backend files missing!**

**Check these first:**
1. ✅ Apache GREEN in XAMPP?
2. ✅ Backend files exist?
3. ✅ Test URL works in browser?


