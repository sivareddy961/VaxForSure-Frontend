# 🔍 Debug Network Error - Step by Step

## Common Causes & Solutions

### ✅ Step 1: Check XAMPP is Running

**Check:**
1. Open XAMPP Control Panel
2. Apache should be **GREEN** ✅
3. MySQL should be **GREEN** ✅

**If not running:**
- Click "Start" for both
- Wait for GREEN status

---

### ✅ Step 2: Test Backend API in Browser

**Test Registration API:**
1. Open browser
2. Go to: `http://localhost:8080/vaxforsure/api/auth/register.php`
3. Should see JSON response (even if error, means API is reachable)

**If 404 Error:**
- Check files exist in `C:\xampp\htdocs\vaxforsure\api\auth\`
- Verify `register.php` file exists

**If Connection Refused:**
- Apache is not running
- Port 8080 is wrong
- Check XAMPP Control Panel

---

### ✅ Step 3: Check Android App URL

**For Android Emulator:**
- Should be: `http://10.0.2.2:8080/vaxforsure/api/`
- Check `ApiConstants.kt` file

**For Physical Device:**
- Should be: `http://YOUR_COMPUTER_IP:8080/vaxforsure/api/`
- Find IP: Run `ipconfig` in Command Prompt
- Update `ApiConstants.kt` with your IP

---

### ✅ Step 4: Check Logcat for Detailed Error

**In Android Studio:**
1. Open Logcat tab (bottom)
2. Filter by: "OkHttp" or "Retrofit"
3. Look for error messages:
   - `Connection refused` = XAMPP not running or wrong URL
   - `404 Not Found` = Wrong API path
   - `Timeout` = Network/firewall issue
   - `UnknownHostException` = Wrong URL/IP

**Common Logcat Errors:**

```
❌ Connection refused
   → XAMPP Apache not running
   → Wrong port number
   → Wrong URL

❌ 404 Not Found  
   → API file doesn't exist
   → Wrong path in URL

❌ Timeout
   → Firewall blocking
   → Device not on same network
   → XAMPP not accessible

❌ UnknownHostException
   → Wrong IP address
   → Wrong URL format
```

---

### ✅ Step 5: Verify Backend Files Exist

**Check these files exist:**
```
C:\xampp\htdocs\vaxforsure\
├── config.php ✅
├── database.sql ✅
├── .htaccess ✅
└── api/
    └── auth/
        └── register.php ✅
```

**If files missing:**
- Copy from project folder
- Or recreate backend files

---

### ✅ Step 6: Test with Postman First

**Before testing in app, test in Postman:**

1. **Install Postman** (if not installed)
2. **Create Request:**
   - Method: POST
   - URL: `http://localhost:8080/vaxforsure/api/auth/register.php`
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
   - ✅ Success = Backend works, issue is in Android app
   - ❌ Error = Backend issue, fix backend first

---

### ✅ Step 7: Check Network Security Config

**Verify file exists:**
- `app/src/main/res/xml/network_security_config.xml`

**Check AndroidManifest.xml:**
- Should have: `android:networkSecurityConfig="@xml/network_security_config"`

---

### ✅ Step 8: Check Internet Permission

**Verify AndroidManifest.xml has:**
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

## 🔧 Quick Fixes

### Fix 1: Restart XAMPP
```
1. Stop Apache and MySQL
2. Start Apache and MySQL again
3. Wait for GREEN status
4. Try app again
```

### Fix 2: Check Port Number
```
1. Verify XAMPP is on port 8080
2. Check ApiConstants.kt has :8080 in URL
3. Test: http://localhost:8080/vaxforsure/api/vaccines/schedule.php
```

### Fix 3: Check Firewall
```
1. Windows Firewall might block Apache
2. Allow Apache through firewall
3. Or temporarily disable firewall to test
```

### Fix 4: Verify URL Format
```
✅ Correct: http://10.0.2.2:8080/vaxforsure/api/
❌ Wrong:   http://localhost:8080/vaxforsure/api/  (won't work in emulator)
❌ Wrong:   http://10.0.2.2/vaxforsure/api/  (missing port)
```

---

## 🧪 Test Checklist

- [ ] XAMPP Apache running (GREEN)
- [ ] XAMPP MySQL running (GREEN)
- [ ] Backend test URL works in browser
- [ ] Postman test works (if using)
- [ ] ApiConstants.kt has correct URL with port 8080
- [ ] Network security config exists
- [ ] Internet permission in AndroidManifest
- [ ] Check Logcat for specific error message

---

## 📱 For Physical Device Testing

**Additional Checks:**
1. Device and computer on **same WiFi**
2. Computer IP found via `ipconfig`
3. `ApiConstants.kt` updated with computer IP
4. Test URL in device browser: `http://YOUR_IP:8080/vaxforsure/api/vaccines/schedule.php`
5. Windows Firewall allows Apache

---

## 🆘 Still Not Working?

**Check Logcat for exact error:**
1. Open Android Studio
2. Run app
3. Try registration
4. Check Logcat tab
5. Look for red error messages
6. Copy error message and check above solutions

**Most Common Issue:**
- XAMPP Apache not running
- Wrong URL (missing port 8080)
- Files not in correct location


