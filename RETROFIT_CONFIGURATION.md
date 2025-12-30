# Retrofit Configuration - Complete Guide

## 📁 Retrofit Files Location in Android Studio

### File Structure:
```
app/src/main/java/com/example/vaxforsure/
├── api/
│   ├── RetrofitClient.kt      ← HTTP Client Setup
│   └── ApiService.kt          ← API Interface
├── models/
│   └── ApiResponse.kt          ← Data Models
└── utils/
    ├── ApiConstants.kt         ← API URLs
    └── ConnectionHelper.kt     ← Connection Utilities
```

---

## 🔧 Retrofit Configuration Files

### 1. ApiConstants.kt
**Location:** `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`

**Purpose:** Stores API base URL and endpoint paths

**Current Configuration:**
```kotlin
object ApiConstants {
    // For Android Emulator
    const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
    
    // For Physical Device (uncomment when needed)
    // const val BASE_URL = "http://10.148.199.69:8080/vaxforsure/api/"
    
    object Auth {
        const val REGISTER = "auth/register.php"
        const val LOGIN = "auth/login.php"
    }
}
```

**To Switch Between Emulator and Physical Device:**
- **Emulator:** Use `10.0.2.2` (current)
- **Physical Device:** Change to `10.148.199.69` (your IP)

---

### 2. RetrofitClient.kt
**Location:** `app/src/main/java/com/example/vaxforsure/api/RetrofitClient.kt`

**Purpose:** Configures Retrofit HTTP client

**Features:**
- ✅ HTTP logging (for debugging)
- ✅ Connection timeout: 30 seconds
- ✅ Retry on connection failure
- ✅ Gson converter for JSON

**Configuration:**
```kotlin
object RetrofitClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()
    
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
```

---

### 3. ApiService.kt
**Location:** `app/src/main/java/com/example/vaxforsure/api/ApiService.kt`

**Purpose:** Defines API endpoints interface

**Endpoints:**
```kotlin
interface ApiService {
    @POST("auth/login.php")
    fun login(@Body request: LoginRequest): Call<ApiResponse<AuthResponse>>
    
    @POST("auth/register.php")
    fun register(@Body request: RegisterRequest): Call<ApiResponse<AuthResponse>>
}
```

---

### 4. ApiResponse.kt (Models)
**Location:** `app/src/main/java/com/example/vaxforsure/models/ApiResponse.kt`

**Purpose:** Data models for API requests/responses

**Models:**
- `ApiResponse<T>` - Generic response wrapper
- `LoginRequest` - Login request data
- `RegisterRequest` - Registration request data
- `User` - User data model
- `AuthResponse` - Authentication response

---

## 🔍 How to View Retrofit Files in Android Studio

### Method 1: Project View
1. Open Android Studio
2. Click **"Project"** tab on left sidebar
3. Navigate: `app` → `src` → `main` → `java` → `com.example.vaxforsure`
4. Expand folders:
   - `api/` → See RetrofitClient.kt and ApiService.kt
   - `models/` → See ApiResponse.kt
   - `utils/` → See ApiConstants.kt

### Method 2: Quick Search
1. Press `Ctrl + Shift + N` (Windows) or `Cmd + Shift + O` (Mac)
2. Type: `RetrofitClient` or `ApiConstants`
3. Press Enter to open file

### Method 3: Navigate to Declaration
1. In any screen file (LoginScreen.kt, RegisterScreen.kt)
2. Right-click on `RetrofitClient` or `ApiConstants`
3. Select **"Go to Declaration"** or press `Ctrl + B`

---

## 🛠️ Current Configuration Status

### ✅ Configured:
- ✅ Base URL: `http://10.0.2.2:8080/vaxforsure/api/` (Emulator)
- ✅ Login endpoint: `auth/login.php`
- ✅ Register endpoint: `auth/register.php`
- ✅ Error handling improved
- ✅ Connection retry enabled
- ✅ Timeout: 30 seconds

### 📝 Files Updated:
- ✅ `RetrofitClient.kt` - Added retry and better error handling
- ✅ `ApiConstants.kt` - Your IP address added as comment
- ✅ `RegisterScreen.kt` - Improved error messages
- ✅ `LoginScreen.kt` - Improved error messages
- ✅ `ConnectionHelper.kt` - New utility for connection checks

---

## 🔧 Fixing Connection Error

### Error: "Failed to connect to /10.0.2.2 (port 8080)"

**This means:** Cannot connect to XAMPP backend

**Solutions:**

#### Step 1: Check XAMPP is Running
1. Open XAMPP Control Panel
2. ✅ Apache: Should be GREEN (Running)
3. ✅ MySQL: Should be GREEN (Running)

#### Step 2: Test Backend in Browser
1. Open browser on your computer
2. Go to: http://localhost:8080/vaxforsure/api/auth/login.php
3. **Expected:** JSON response (may show error if no POST data, that's OK)
4. **If this fails:** Apache is not running or port 8080 is wrong

#### Step 3: Check Port Configuration
**If Apache is on different port:**
1. Check XAMPP Control Panel → Apache → Port number
2. Update `ApiConstants.kt`:
   ```kotlin
   const val BASE_URL = "http://10.0.2.2:YOUR_PORT/vaxforsure/api/"
   ```

#### Step 4: For Physical Device
**If testing on physical Android device:**
1. Update `ApiConstants.kt`:
   ```kotlin
   // Comment emulator URL:
   // const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
   
   // Uncomment physical device URL:
   const val BASE_URL = "http://10.148.199.69:8080/vaxforsure/api/"
   ```
2. Ensure device and computer on same WiFi
3. Test URL on device browser first

---

## 🧪 Testing Retrofit Connection

### Test 1: Check Logcat
1. Run app in Android Studio
2. Open **Logcat** tab (bottom)
3. Filter by: `OkHttp` or `Retrofit`
4. Look for:
   - ✅ `--> POST http://10.0.2.2:8080/vaxforsure/api/auth/register.php`
   - ✅ `<-- 200 OK` (Success)
   - ❌ `<-- HTTP FAILED` (Error)

### Test 2: Test Registration
1. Fill registration form
2. Click "Create Account"
3. **Success:** User saved to database
4. **Error:** Check Logcat for details

### Test 3: Test Login
1. Login with registered credentials
2. **Success:** Navigate to Dashboard
3. **Error:** Check error message

---

## 📋 Quick Checklist

**Before Testing:**
- [ ] XAMPP Apache: GREEN ✅
- [ ] XAMPP MySQL: GREEN ✅
- [ ] Backend URL correct in `ApiConstants.kt`
- [ ] Test backend in browser first

**Check Retrofit Files:**
- [ ] `ApiConstants.kt` - Base URL correct
- [ ] `RetrofitClient.kt` - Timeout and retry configured
- [ ] `ApiService.kt` - Endpoints defined
- [ ] `ApiResponse.kt` - Models created

**Test Connection:**
- [ ] Backend responds in browser ✅
- [ ] App registration works ✅
- [ ] App login works ✅
- [ ] Error messages helpful ✅

---

## 🎯 Common Issues & Fixes

### Issue: "Failed to connect"
**Fix:** Check Apache is running, test URL in browser

### Issue: "Timeout"
**Fix:** Increase timeout in RetrofitClient.kt or check network

### Issue: "404 Not Found"
**Fix:** Check API endpoint path is correct

### Issue: "Unable to resolve host"
**Fix:** Check URL format, ensure correct IP address

---

## 📞 Quick Reference

**Base URL (Emulator):** `http://10.0.2.2:8080/vaxforsure/api/`  
**Base URL (Physical):** `http://10.148.199.69:8080/vaxforsure/api/`

**Test URLs:**
- Login: http://localhost:8080/vaxforsure/api/auth/login.php
- Register: http://localhost:8080/vaxforsure/api/auth/register.php

**Files Location:**
- RetrofitClient: `app/src/main/java/com/example/vaxforsure/api/RetrofitClient.kt`
- ApiConstants: `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`

---

**Your Retrofit configuration is ready!** 🚀

