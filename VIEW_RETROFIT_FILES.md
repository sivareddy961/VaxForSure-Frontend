# How to View Retrofit Files in Android Studio

## 📁 Retrofit Files Location

### File Structure in Android Studio:

```
VAXFORSURE (Project Root)
└── app
    └── src
        └── main
            └── java
                └── com
                    └── example
                        └── vaxforsure
                            ├── api/                          ← RETROFIT FILES HERE
                            │   ├── RetrofitClient.kt        ← HTTP Client
                            │   └── ApiService.kt            ← API Interface
                            │
                            ├── models/                       ← DATA MODELS
                            │   └── ApiResponse.kt            ← Request/Response Models
                            │
                            ├── utils/                        ← UTILITIES
                            │   ├── ApiConstants.kt           ← API URLs
                            │   └── ConnectionHelper.kt       ← Connection Utilities
                            │
                            └── screens/
                                └── auth/
                                    ├── LoginScreen.kt        ← Uses Retrofit
                                    └── RegisterScreen.kt     ← Uses Retrofit
```

---

## 🔍 Method 1: Project View (Easiest)

### Steps:
1. **Open Android Studio**
2. **Look at left sidebar** → Click **"Project"** tab
3. **Navigate:** 
   ```
   app → src → main → java → com → example → vaxforsure
   ```
4. **Expand folders:**
   - Click `api/` → See `RetrofitClient.kt` and `ApiService.kt`
   - Click `models/` → See `ApiResponse.kt`
   - Click `utils/` → See `ApiConstants.kt` and `ConnectionHelper.kt`

---

## 🔍 Method 2: Quick Search (Fastest)

### Steps:
1. **Press:** `Ctrl + Shift + N` (Windows) or `Cmd + Shift + O` (Mac)
2. **Type:** 
   - `RetrofitClient` → Opens RetrofitClient.kt
   - `ApiConstants` → Opens ApiConstants.kt
   - `ApiService` → Opens ApiService.kt
   - `ApiResponse` → Opens ApiResponse.kt
3. **Press Enter** → File opens!

---

## 🔍 Method 3: Navigate from Code

### Steps:
1. **Open any screen file** (e.g., `LoginScreen.kt` or `RegisterScreen.kt`)
2. **Find line with:** `RetrofitClient.apiService` or `ApiConstants.BASE_URL`
3. **Right-click** on `RetrofitClient` or `ApiConstants`
4. **Select:** "Go to Declaration" (or press `Ctrl + B`)
5. **File opens automatically!**

---

## 📋 Files Overview

### 1. RetrofitClient.kt
**Path:** `app/src/main/java/com/example/vaxforsure/api/RetrofitClient.kt`

**What it does:**
- Configures HTTP client
- Sets timeouts
- Adds logging
- Creates API service instance

**Key Code:**
```kotlin
val apiService: ApiService = retrofit.create(ApiService::class.java)
```

---

### 2. ApiService.kt
**Path:** `app/src/main/java/com/example/vaxforsure/api/ApiService.kt`

**What it does:**
- Defines API endpoints
- Maps HTTP methods (POST, GET)
- Defines request/response types

**Key Code:**
```kotlin
@POST("auth/login.php")
fun login(@Body request: LoginRequest): Call<ApiResponse<AuthResponse>>
```

---

### 3. ApiConstants.kt
**Path:** `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`

**What it does:**
- Stores base URL
- Defines endpoint paths
- Easy to change URLs

**Key Code:**
```kotlin
const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
```

---

### 4. ApiResponse.kt
**Path:** `app/src/main/java/com/example/vaxforsure/models/ApiResponse.kt`

**What it does:**
- Defines data models
- LoginRequest, RegisterRequest
- User, AuthResponse models

---

## 🛠️ How to Edit Retrofit Configuration

### Change Base URL:

1. **Open:** `ApiConstants.kt`
2. **Find:** `const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"`
3. **Change to:** Your desired URL
4. **Save:** `Ctrl + S`

### Add New Endpoint:

1. **Open:** `ApiService.kt`
2. **Add new function:**
   ```kotlin
   @POST("auth/new_endpoint.php")
   fun newEndpoint(@Body request: Request): Call<ApiResponse<Response>>
   ```
3. **Save:** `Ctrl + S`

### Change Timeout:

1. **Open:** `RetrofitClient.kt`
2. **Find:** `.connectTimeout(30, TimeUnit.SECONDS)`
3. **Change:** `30` to desired seconds
4. **Save:** `Ctrl + S`

---

## 🧪 Testing After Changes

### After Changing Base URL:
1. **Sync Project:** Click "Sync Now" if prompted
2. **Rebuild:** `Build → Rebuild Project`
3. **Run App:** Test registration/login

### Check Logcat:
1. **Open Logcat** tab (bottom of Android Studio)
2. **Filter:** `OkHttp` or `Retrofit`
3. **Look for:** API calls and responses

---

## ✅ Quick Checklist

**To View Files:**
- [ ] Open Android Studio
- [ ] Click "Project" tab
- [ ] Navigate to `app/src/main/java/com/example/vaxforsure`
- [ ] Expand `api/`, `models/`, `utils/` folders

**To Edit Configuration:**
- [ ] Open `ApiConstants.kt` → Change URL
- [ ] Open `RetrofitClient.kt` → Change timeout
- [ ] Open `ApiService.kt` → Add endpoints

**To Test:**
- [ ] Sync project
- [ ] Run app
- [ ] Check Logcat for API calls

---

## 📞 Quick Reference

**Files Location:**
- RetrofitClient: `app/src/main/java/com/example/vaxforsure/api/RetrofitClient.kt`
- ApiService: `app/src/main/java/com/example/vaxforsure/api/ApiService.kt`
- ApiConstants: `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`
- ApiResponse: `app/src/main/java/com/example/vaxforsure/models/ApiResponse.kt`

**Quick Open:** `Ctrl + Shift + N` → Type filename → Enter

---

**Now you know where all Retrofit files are!** 🎉

