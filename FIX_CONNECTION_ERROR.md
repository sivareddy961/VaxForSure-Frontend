# Fix Connection Error - Complete Solution

## ✅ What I Fixed

### 1. RetrofitClient.kt - Improved Configuration
- ✅ Increased timeout to 60 seconds
- ✅ Added singleton pattern for better instance management
- ✅ Ensured base URL ends with `/`
- ✅ Better connection retry logic

### 2. Error Messages - More Helpful
- ✅ Detailed error messages with emojis
- ✅ Shows exact URL being used
- ✅ Provides troubleshooting steps
- ✅ Different messages for different error types

### 3. Test Connection File
- ✅ Created `test_connection.php` for easy testing
- ✅ Access: http://localhost:8080/vaxforsure/test_connection.php

---

## 🔧 Step-by-Step Fix

### Step 1: Start XAMPP (CRITICAL!)

**This is the most common issue!**

1. **Open XAMPP Control Panel**
2. **Click "Start" for Apache** → Wait for GREEN ✅
3. **Click "Start" for MySQL** → Wait for GREEN ✅
4. **Both must be GREEN!**

### Step 2: Test Backend in Browser

**Before testing in app, test in browser:**

1. **Open browser**
2. **Go to:** http://localhost:8080/vaxforsure/test_connection.php
3. **Expected:** Should see JSON response:
   ```json
   {
     "success": true,
     "message": "Backend is working!",
     "timestamp": "2025-12-30 10:00:00"
   }
   ```

**If this doesn't work:**
- Apache is not running → Go back to Step 1
- Port might be different → Check XAMPP Control Panel

### Step 3: Test API Endpoints

**Test Registration Endpoint:**
- URL: http://localhost:8080/vaxforsure/api/auth/register.php
- Should see JSON (may show error if no POST data, that's OK)

**Test Login Endpoint:**
- URL: http://localhost:8080/vaxforsure/api/auth/login.php
- Should see JSON (may show error if no POST data, that's OK)

### Step 4: Check Port Number

**If Apache is on different port:**

1. **Check XAMPP Control Panel** → Apache → Port number
2. **If port is 80 (not 8080):**
   - Update `ApiConstants.kt`:
   ```kotlin
   const val BASE_URL = "http://10.0.2.2/vaxforsure/api/"
   ```
   - Remove `:8080` if port is 80

### Step 5: For Physical Device

**If testing on physical Android device:**

1. **Update `ApiConstants.kt`:**
   ```kotlin
   // Comment this:
   // const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
   
   // Uncomment this:
   const val BASE_URL = "http://10.148.199.69:8080/vaxforsure/api/"
   ```

2. **Ensure same WiFi network**

3. **Test URL on device browser first:**
   - http://10.148.199.69:8080/vaxforsure/test_connection.php

---

## 🧪 Testing Checklist

**Before Testing App:**
- [ ] XAMPP Apache: GREEN ✅
- [ ] XAMPP MySQL: GREEN ✅
- [ ] Test URL works in browser ✅
- [ ] Port number correct ✅

**Test in App:**
- [ ] Registration works ✅
- [ ] Login works ✅
- [ ] Error messages helpful ✅

---

## 🔍 Troubleshooting

### Error: "Failed to connect to /10.0.2.2 (port 8080)"

**Solution:**
1. ✅ Check Apache is GREEN in XAMPP
2. ✅ Test http://localhost:8080/vaxforsure/test_connection.php in browser
3. ✅ If browser works but app doesn't → Check `ApiConstants.kt` URL
4. ✅ If browser doesn't work → Apache is not running

### Error: "Connection refused"

**Solution:**
- Apache is definitely not running
- Start Apache in XAMPP Control Panel

### Error: "Timeout"

**Solution:**
- Check internet connection
- Check firewall settings
- Try increasing timeout in RetrofitClient.kt (already set to 60 seconds)

### Error: "Unable to resolve host"

**Solution:**
- Check URL in `ApiConstants.kt`
- For emulator: Use `10.0.2.2`
- For physical device: Use your computer's IP (`10.148.199.69`)

---

## 📋 Quick Fix Steps

1. **Open XAMPP Control Panel**
2. **Start Apache** → GREEN ✅
3. **Start MySQL** → GREEN ✅
4. **Test:** http://localhost:8080/vaxforsure/test_connection.php
5. **If works:** Run Android app
6. **If doesn't work:** Check port number

---

## ✅ Success Indicators

**Backend Working When:**
- ✅ Browser test shows JSON response
- ✅ App registration saves to database
- ✅ App login works
- ✅ No connection errors

---

## 🚀 Updated Files

1. ✅ `RetrofitClient.kt` - Improved instance management
2. ✅ `RegisterScreen.kt` - Better error messages
3. ✅ `LoginScreen.kt` - Better error messages
4. ✅ `test_connection.php` - New test file

---

**The Retrofit instance is now optimized and error handling is improved!** 🎉

**Most important:** Make sure XAMPP Apache is running (GREEN) before testing!

