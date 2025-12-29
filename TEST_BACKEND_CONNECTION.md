# 🧪 Test Backend Connection - Find the Problem

## Step 1: Test XAMPP (30 seconds)

**Open Browser:**
```
http://localhost:8080
```

**Expected:** XAMPP welcome page ✅

**If Error:**
- ❌ Apache not running
- ❌ Wrong port
- **Fix:** Start Apache in XAMPP Control Panel

---

## Step 2: Test Backend API (30 seconds)

**Open Browser:**
```
http://localhost:8080/vaxforsure/api/vaccines/schedule.php
```

**Expected:** JSON data like:
```json
{
  "success": true,
  "message": "Vaccine schedule retrieved successfully",
  "data": [...]
}
```

**If Error:**
- ❌ 404 Not Found → Backend files missing
- ❌ Connection refused → Apache not running
- ❌ Blank page → Check database connection

---

## Step 3: Test Registration API (1 minute)

**Using Browser (GET request - will show error but confirms API exists):**
```
http://localhost:8080/vaxforsure/api/auth/register.php
```

**Using Postman (Proper test):**
1. Method: POST
2. URL: `http://localhost:8080/vaxforsure/api/auth/register.php`
3. Headers: `Content-Type: application/json`
4. Body:
```json
{
  "full_name": "Test",
  "email": "test@test.com",
  "phone": "123",
  "password": "pass123",
  "confirm_password": "pass123"
}
```

**Expected:** Success response with user_id ✅

**If Error:**
- Check error message
- Check database exists
- Check tables created

---

## Step 4: Check Android Logcat

**In Android Studio:**
1. Run app
2. Try registration
3. Open Logcat tab
4. Filter: "OkHttp" or "Retrofit"
5. Look for error

**What to Look For:**

✅ **Success:**
```
D/OkHttp: --> POST http://10.0.2.2:8080/vaxforsure/api/auth/register.php
D/OkHttp: <-- 200 OK
```

❌ **Connection Refused:**
```
java.net.ConnectException: Connection refused
```
→ XAMPP Apache not running

❌ **404 Not Found:**
```
<-- 404 Not Found
```
→ Backend file missing or wrong path

❌ **Unknown Host:**
```
java.net.UnknownHostException: 10.0.2.2
```
→ Wrong URL or emulator issue

---

## 🔧 Quick Fixes Based on Error

### Error: "Connection refused"
**Fix:**
1. Open XAMPP Control Panel
2. Start Apache (wait for GREEN)
3. Try again

### Error: "404 Not Found"
**Fix:**
1. Check: `C:\xampp\htdocs\vaxforsure\api\auth\register.php` exists
2. If missing → Copy backend files
3. Test URL in browser

### Error: "Timeout"
**Fix:**
1. Check firewall settings
2. Check device/computer on same network
3. Try restarting XAMPP

---

## ✅ Most Common Solution

**90% of network errors = XAMPP Apache not running**

**Quick Fix:**
1. Open XAMPP Control Panel
2. Click "Start" for Apache
3. Wait for GREEN ✅
4. Try app again

**That's it!** 🚀


