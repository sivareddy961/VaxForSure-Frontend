# Test XAMPP Connection (Port 8080)

## ✅ Quick Connection Test

### 1. Test Backend in Browser
Open these URLs in your browser:

**Test API Endpoint:**
```
http://localhost:8080/vaxforsure/api/vaccines/schedule.php
```
✅ Should return JSON data

**Test phpMyAdmin:**
```
http://localhost:8080/phpmyadmin
```
✅ Should open phpMyAdmin

**Test Config:**
```
http://localhost:8080/vaxforsure/config.php
```
✅ Should not show errors (may show blank page, that's OK)

### 2. Test in Postman

**Registration Test:**
```
POST http://localhost:8080/vaxforsure/api/auth/register.php
Headers: Content-Type: application/json
Body:
{
  "full_name": "Test User",
  "email": "test@example.com",
  "phone": "1234567890",
  "password": "password123",
  "confirm_password": "password123"
}
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Registration successful. OTP sent to email.",
  "data": {
    "user_id": 1,
    "otp_code": "123456",
    "email": "test@example.com"
  }
}
```

### 3. Test Android App Connection

**On Emulator:**
1. Run app on Android Emulator
2. Try to register/login
3. Check Logcat for:
   - ✅ "200 OK" responses
   - ✅ JSON data received
   - ❌ "Connection refused" = wrong URL/port
   - ❌ "404 Not Found" = wrong path

**On Physical Device:**
1. Find your computer's IP: `ipconfig`
2. Update `ApiConstants.kt` with your IP
3. Test URL in device browser: `http://YOUR_IP:8080/vaxforsure/api/vaccines/schedule.php`
4. Run app and test

## 🔍 Debugging Connection Issues

### Check Logcat Output

Look for these in Android Studio Logcat:

**Success:**
```
D/OkHttp: --> POST http://10.0.2.2:8080/vaxforsure/api/auth/login.php
D/OkHttp: <-- 200 OK http://10.0.2.2:8080/vaxforsure/api/auth/login.php
```

**Connection Error:**
```
D/OkHttp: --> POST http://10.0.2.2:8080/vaxforsure/api/auth/login.php
D/OkHttp: <-- HTTP FAILED: java.net.ConnectException: Connection refused
```

**404 Error:**
```
D/OkHttp: <-- 404 Not Found http://10.0.2.2:8080/vaxforsure/api/auth/login.php
```

### Common Issues & Fixes

**Issue: Connection Refused**
- ✅ Check XAMPP Apache is running (green)
- ✅ Verify port 8080 in ApiConstants.kt
- ✅ Test backend URL in browser first

**Issue: 404 Not Found**
- ✅ Check files exist in `C:\xampp\htdocs\vaxforsure\api\`
- ✅ Verify URL path matches file structure
- ✅ Test: http://localhost:8080/vaxforsure/api/vaccines/schedule.php

**Issue: Timeout**
- ✅ Check firewall isn't blocking Apache
- ✅ Verify device/emulator can reach computer
- ✅ Test with Postman first

## 📋 Current Configuration Summary

**Backend:**
- URL: `http://localhost:8080/vaxforsure/api/`
- Port: 8080 ✅
- Database: vaxforsure ✅

**Android Emulator:**
- URL: `http://10.0.2.2:8080/vaxforsure/api/` ✅
- Configured in: `ApiConstants.kt`

**Physical Device:**
- URL: `http://YOUR_IP:8080/vaxforsure/api/`
- Update `ApiConstants.kt` with your IP

## ✅ Verification Steps

1. [ ] XAMPP Apache running (green) on port 8080
2. [ ] Database `vaxforsure` exists in phpMyAdmin
3. [ ] Backend test URL works in browser
4. [ ] `ApiConstants.kt` has correct URL with port 8080
5. [ ] Android app runs without crashes
6. [ ] API calls work (check Logcat)

**If all checked ✅, your connection is working!** 🎉

