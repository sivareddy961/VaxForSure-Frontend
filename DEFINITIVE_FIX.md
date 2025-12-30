# 🔥 DEFINITIVE FIX - Backend Connection

## ⚠️ CRITICAL: Check These First!

### Step 1: XAMPP Apache MUST Be Running

**DO THIS NOW:**
1. Open XAMPP Control Panel
2. Look at Apache row
3. **Is it GREEN?** ✅
   - **If NO** → Click "Start" button
   - **Wait for GREEN** ✅
4. **Is it RED?** ❌
   - Click "Start" button
   - Wait for GREEN ✅

**Apache MUST be GREEN before anything else works!**

---

### Step 2: Check Port Number

**In XAMPP Control Panel:**
- Look at Apache row
- What port does it show?
  - **Port 8080** → Use `http://10.0.2.2:8080`
  - **Port 80** → Use `http://10.0.2.2` (no port number)

---

### Step 3: Test Backend RIGHT NOW

**Open browser and test these URLs:**

1. **Test Connection:**
   ```
   http://localhost:8080/vaxforsure/test_connection.php
   ```
   **Expected:** JSON response with "Backend is working!"

2. **Test Login API:**
   ```
   http://localhost:8080/vaxforsure/api/auth/login.php
   ```
   **Expected:** JSON response (may show error if no POST data, that's OK)

3. **Test Register API:**
   ```
   http://localhost:8080/vaxforsure/api/auth/register.php
   ```
   **Expected:** JSON response

**If these DON'T work:**
- Apache is NOT running → Go back to Step 1
- Port is wrong → Check Step 2

---

## 🔧 Fix Android App Configuration

### Option A: If Apache is on Port 8080

**File:** `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`

**Set to:**
```kotlin
const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
```

### Option B: If Apache is on Port 80

**File:** `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`

**Set to:**
```kotlin
const val BASE_URL = "http://10.0.2.2/vaxforsure/api/"
```
**(Remove :8080)**

---

## 🧪 Complete Test Procedure

### Test 1: XAMPP Status
- [ ] Apache: GREEN ✅
- [ ] MySQL: GREEN ✅
- [ ] Port number noted

### Test 2: Browser Test
- [ ] http://localhost:8080/vaxforsure/test_connection.php works ✅
- [ ] http://localhost:8080/vaxforsure/api/auth/login.php works ✅

### Test 3: Android App
- [ ] `ApiConstants.kt` has correct URL ✅
- [ ] App registration works ✅
- [ ] App login works ✅

---

## 🚨 Common Issues & Solutions

### Issue 1: "Failed to connect"
**Cause:** Apache not running
**Fix:** Start Apache in XAMPP → Wait for GREEN ✅

### Issue 2: "Connection refused"
**Cause:** Apache not running
**Fix:** Start Apache in XAMPP → Wait for GREEN ✅

### Issue 3: Browser test fails
**Cause:** Apache not running OR wrong port
**Fix:** 
1. Start Apache
2. Check port number
3. Update URL accordingly

### Issue 4: Browser works but app doesn't
**Cause:** Wrong URL in `ApiConstants.kt`
**Fix:** 
1. Check port (8080 or 80)
2. Update `ApiConstants.kt` with correct port
3. Sync project in Android Studio

---

## ✅ FINAL CHECKLIST

**Before Testing App:**
1. [ ] XAMPP Control Panel open
2. [ ] Apache: GREEN ✅
3. [ ] MySQL: GREEN ✅
4. [ ] Port number: _____ (write it down)
5. [ ] Browser test works ✅
6. [ ] `ApiConstants.kt` has correct URL ✅
7. [ ] Android Studio project synced ✅

**Then Test App:**
8. [ ] Run app
9. [ ] Try registration
10. [ ] Should work! ✅

---

## 📞 Quick Reference

**XAMPP Control Panel:** `C:\xampp\xampp-control.exe`

**Test URLs:**
- Connection: http://localhost:8080/vaxforsure/test_connection.php
- Login: http://localhost:8080/vaxforsure/api/auth/login.php
- Register: http://localhost:8080/vaxforsure/api/auth/register.php

**Android Config:**
- File: `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`
- Emulator: `http://10.0.2.2:8080/vaxforsure/api/` (or without :8080 if port is 80)

---

## 🎯 ONE MORE TIME - Step by Step

1. **Open XAMPP Control Panel**
2. **Click "Start" for Apache**
3. **WAIT for GREEN** ✅
4. **Test:** http://localhost:8080/vaxforsure/test_connection.php
5. **If works:** Update `ApiConstants.kt` and test app
6. **If doesn't work:** Check port number and try again

---

**Apache MUST be GREEN. That's the #1 requirement!** 🔥

