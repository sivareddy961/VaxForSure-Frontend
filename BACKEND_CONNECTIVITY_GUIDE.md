# Backend Connectivity Guide

## ✅ Your Configuration

**Your Computer's IP Address:** `10.148.199.69`  
**XAMPP Port:** `8080`  
**Database:** `vaxforsure`

---

## 🔧 Configuration Status

### ✅ Already Configured:
- ✅ Internet permission added
- ✅ Network security config allows HTTP
- ✅ API constants file created
- ✅ Your IP address added to network config

---

## 📱 Testing Backend Connection

### Option 1: Android Emulator (Recommended for Testing)

**Current Configuration:**
```kotlin
const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
```

**Steps:**
1. ✅ Start XAMPP (Apache + MySQL)
2. ✅ Run app on Android Emulator
3. ✅ Test registration/login

**No changes needed!** ✅

---

### Option 2: Physical Android Device

**Your IP:** `10.148.199.69`

**Steps to Connect:**

#### Step 1: Update ApiConstants.kt

**File:** `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`

**Change from:**
```kotlin
const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
```

**Change to:**
```kotlin
const val BASE_URL = "http://10.148.199.69:8080/vaxforsure/api/"
```

#### Step 2: Ensure Same WiFi Network

- ✅ Android device and computer must be on **same WiFi network**
- ✅ Your device should connect to: `saveetha.net` (same network)

#### Step 3: Test Connection

**Test 1: Browser on Android Device**
1. Open browser on your Android device
2. Go to: `http://10.148.199.69:8080/vaxforsure/api/auth/login.php`
3. Should see JSON response ✅

**Test 2: Android App**
1. Run app on physical device
2. Try registration/login
3. Should connect successfully ✅

---

## 🧪 Connection Testing Steps

### Step 1: Verify XAMPP is Running

1. Open XAMPP Control Panel
2. ✅ Apache: GREEN (Running)
3. ✅ MySQL: GREEN (Running)

### Step 2: Test Backend API (Browser)

**On Your Computer:**
1. Open browser
2. Go to: http://localhost:8080/vaxforsure/api/auth/login.php
3. **Expected:** JSON response (may show error if no POST data, that's OK)

**On Android Device (if testing physical device):**
1. Open browser
2. Go to: http://10.148.199.69:8080/vaxforsure/api/auth/login.php
3. **Expected:** JSON response ✅

### Step 3: Test Android App

**Registration Test:**
1. Open app
2. Go to "Create Account"
3. Fill form and submit
4. **Expected:** 
   - ✅ Shows "Registration successful"
   - ✅ Navigates to "Add Child Profile"
   - ✅ User appears in database (check phpMyAdmin)

**Login Test:**
1. Go to "Sign In"
2. Enter registered email/password
3. Click "Sign In"
4. **Expected:**
   - ✅ Shows "Login successful"
   - ✅ Navigates to Dashboard

**Wrong Credentials Test:**
1. Enter wrong password
2. **Expected:**
   - ✅ Shows "Invalid email or password" error

---

## 🔍 Troubleshooting Connection Issues

### Problem: "Network error" or "Connection refused"

**Checklist:**
- [ ] Apache is running (GREEN in XAMPP)
- [ ] MySQL is running (GREEN in XAMPP)
- [ ] URL is correct in `ApiConstants.kt`
- [ ] For physical device: Same WiFi network
- [ ] For physical device: IP address is correct (`10.148.199.69`)

**Solutions:**

1. **Test URL in browser first:**
   - Emulator: http://localhost:8080/vaxforsure/api/auth/login.php
   - Physical: http://10.148.199.69:8080/vaxforsure/api/auth/login.php
   - Should see JSON response

2. **Check Windows Firewall:**
   - Allow Apache through firewall
   - Or temporarily disable firewall to test

3. **Verify IP Address:**
   - Run `ipconfig` in Command Prompt
   - Check IPv4 Address matches `10.148.199.69`
   - If IP changed, update `ApiConstants.kt`

4. **Check WiFi Network:**
   - Ensure device and computer on same network
   - Try connecting device to same WiFi as computer

### Problem: "404 Not Found"

**Solution:**
- Check URL path is correct
- Verify files exist: `C:\xampp\htdocs\vaxforsure\api\auth\login.php`
- Test: http://localhost:8080/vaxforsure/api/auth/login.php

### Problem: "Database connection failed"

**Solution:**
- Check MySQL is running (GREEN)
- Verify database `vaxforsure` exists
- Check `config.php` has correct settings

---

## 📋 Quick Connection Checklist

**Before Testing:**
- [ ] XAMPP Apache: GREEN ✅
- [ ] XAMPP MySQL: GREEN ✅
- [ ] Database `vaxforsure` exists
- [ ] 6 tables created in database
- [ ] `ApiConstants.kt` has correct URL
- [ ] Android device on same WiFi (if physical device)

**Test Connection:**
- [ ] Backend API responds in browser ✅
- [ ] App registration works ✅
- [ ] App login works ✅
- [ ] Wrong credentials show error ✅

---

## 🎯 Quick Reference

**For Android Emulator:**
```
http://10.0.2.2:8080/vaxforsure/api/
```

**For Physical Device:**
```
http://10.148.199.69:8080/vaxforsure/api/
```

**Test URLs:**
- Login: http://localhost:8080/vaxforsure/api/auth/login.php
- Register: http://localhost:8080/vaxforsure/api/auth/register.php

**Your IP:** `10.148.199.69`  
**Port:** `8080`  
**Database:** `vaxforsure`

---

## ✅ Success Indicators

**Backend Connected When:**
- ✅ Registration saves user to database
- ✅ Login validates credentials correctly
- ✅ Wrong password shows error
- ✅ User data persists in database

**You're Connected!** 🎉

---

## 🚀 Next Steps

1. **Test Registration:** Create account → Check database
2. **Test Login:** Login with credentials → Should succeed
3. **Test Error:** Wrong password → Should show error
4. **Start Coding:** Backend is ready! ✅

---

**Your backend connectivity is configured!** 🚀

