# XAMPP Connection Setup - Port 8080

## ✅ Your XAMPP Configuration

- **Port:** 8080 (not default 80)
- **Database:** vaxforsure ✅
- **phpMyAdmin URL:** http://localhost:8080/phpmyadmin
- **Backend API:** http://localhost:8080/vaxforsure/api/

## 🔧 Android Studio Connection Setup

### Step 1: Update API Base URL

The `ApiConstants.kt` file has been updated with port 8080:

```kotlin
// For Android Emulator
const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
```

### Step 2: Test Backend Connection

**Test in Browser:**
1. Open: http://localhost:8080/vaxforsure/api/vaccines/schedule.php
2. Should see JSON data ✅

**Test in Postman:**
- Base URL: `http://localhost:8080/vaxforsure/api/`
- Test Registration: `POST http://localhost:8080/vaxforsure/api/auth/register.php`

### Step 3: Android Emulator Connection

**For Android Emulator:**
- ✅ Already configured: `http://10.0.2.2:8080/vaxforsure/api/`
- `10.0.2.2` is the special IP that maps to `localhost` on your computer
- Port 8080 is included ✅

**Test:**
1. Run app on Android Emulator
2. Try registration/login
3. Check Logcat for connection errors

### Step 4: Physical Device Connection

**For Physical Device:**

1. **Find Your Computer's IP:**
   ```bash
   # Open Command Prompt and run:
   ipconfig
   
   # Look for IPv4 Address under your network adapter
   # Example: 192.168.1.100
   ```

2. **Update ApiConstants.kt:**
   ```kotlin
   // Comment out emulator URL:
   // const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
   
   // Uncomment and update with your IP:
   const val BASE_URL = "http://192.168.1.100:8080/vaxforsure/api/"
   ```
   ⚠️ Replace `192.168.1.100` with your actual IP address!

3. **Important Requirements:**
   - ✅ Android device and computer must be on **same WiFi network**
   - ✅ Windows Firewall must allow Apache on port 8080
   - ✅ XAMPP Apache must be running

### Step 5: Verify XAMPP Apache Port

**Check Apache Port:**
1. Open XAMPP Control Panel
2. Click **Config** → **httpd.conf**
3. Look for: `Listen 8080`
4. If different, update `ApiConstants.kt` to match

**Or Change Apache Port:**
1. XAMPP Control Panel → Config → **httpd.conf**
2. Find: `Listen 80`
3. Change to: `Listen 8080`
4. Restart Apache

## 🧪 Testing Checklist

### Backend Tests (Browser/Postman)
- [ ] http://localhost:8080/vaxforsure/api/vaccines/schedule.php works
- [ ] http://localhost:8080/phpmyadmin opens
- [ ] Database `vaxforsure` exists
- [ ] Tables are created

### Android Emulator Tests
- [ ] App runs without crashes
- [ ] Registration API call works
- [ ] Login API call works
- [ ] No "Connection refused" errors in Logcat

### Physical Device Tests
- [ ] Computer IP found via `ipconfig`
- [ ] `ApiConstants.kt` updated with correct IP
- [ ] Device and computer on same WiFi
- [ ] App connects successfully

## 🔍 Troubleshooting

### Problem: "Connection refused" on Emulator
**Solution:**
- ✅ Check XAMPP Apache is running (green in Control Panel)
- ✅ Verify port 8080 in `ApiConstants.kt`
- ✅ Test backend URL in browser first
- ✅ Check Logcat for detailed error

### Problem: "Connection refused" on Physical Device
**Solutions:**
1. ✅ Verify IP address is correct (`ipconfig`)
2. ✅ Check device and computer on same WiFi
3. ✅ Test URL in device browser: `http://YOUR_IP:8080/vaxforsure/api/vaccines/schedule.php`
4. ✅ Check Windows Firewall allows Apache
5. ✅ Verify XAMPP Apache is running

### Problem: "Network Security Config" Error
**Solution:**
- ✅ Already handled in `network_security_config.xml`
- ✅ HTTP is allowed for localhost and 10.0.2.2
- ✅ Should work automatically

### Problem: Port 8080 Not Working
**Solutions:**
1. Check if port 8080 is in use:
   ```bash
   netstat -ano | findstr :8080
   ```
2. Change Apache port in `httpd.conf`:
   - Find `Listen 8080`
   - Change to desired port
   - Update `ApiConstants.kt` to match
   - Restart Apache

## 📱 Quick Reference

### URLs

**Backend (Browser/Postman):**
- Base: `http://localhost:8080/vaxforsure/api/`
- phpMyAdmin: `http://localhost:8080/phpmyadmin`
- Test API: `http://localhost:8080/vaxforsure/api/vaccines/schedule.php`

**Android Emulator:**
- Base: `http://10.0.2.2:8080/vaxforsure/api/`

**Physical Device:**
- Base: `http://YOUR_COMPUTER_IP:8080/vaxforsure/api/`
- Find IP: Run `ipconfig` in Command Prompt

### Files Updated
- ✅ `app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt`
- ✅ Port 8080 configured for emulator
- ✅ Ready for physical device (just update IP)

## ✅ Current Configuration

```kotlin
// ApiConstants.kt
const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
```

**This is correct for:**
- ✅ Android Emulator
- ✅ XAMPP on port 8080
- ✅ Database `vaxforsure`

**For Physical Device:**
- Update to: `http://YOUR_IP:8080/vaxforsure/api/`

## 🎯 Next Steps

1. ✅ **Test Backend:** Open http://localhost:8080/vaxforsure/api/vaccines/schedule.php
2. ✅ **Run Android App:** Test on emulator first
3. ✅ **Check Logcat:** Look for API calls and responses
4. ✅ **Test Registration:** Create a new account
5. ✅ **Test Login:** Login with created account

**Everything is configured for port 8080!** 🚀

