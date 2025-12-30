# 🔥 FIX: "Failed to connect" Error - Complete Solution

## ❌ Error You're Seeing

```
Network error: failed to connect to /10.148.199.69 (port 8080) from /100.82.0.2:xxxxx
```

**This means:** Your Android device cannot reach your computer's backend server.

---

## ✅ STEP-BY-STEP FIX (Do These in Order)

### Step 1: Check XAMPP Apache is Running

1. **Open XAMPP Control Panel**
2. **Look at Apache row**
3. **Must be GREEN** ✅
4. **If RED:** Click "Start" → Wait for GREEN ✅

**This is CRITICAL - Apache MUST be running!**

---

### Step 2: Check WiFi Network

**Your device IP shows:** `100.82.0.2`

**This suggests:**
- Device might be on different WiFi network
- Or using mobile data instead of WiFi

**FIX:**
1. **On Android device:**
   - Go to Settings → WiFi
   - Connect to SAME WiFi as your computer
   - Verify connected (not mobile data)

2. **Verify same network:**
   - Computer WiFi: `saveetha.net` (or your WiFi name)
   - Device WiFi: Must be SAME network

---

### Step 3: Test Backend on Device Browser

**BEFORE testing in app, test in browser:**

1. **On Android device, open browser**
2. **Go to:**
   ```
   http://10.148.199.69:8080/vaxforsure/api/auth/login.php
   ```
3. **Expected:** Should see JSON response

**If browser works:** App will work!
**If browser doesn't work:** Continue to Step 4

---

### Step 4: Check Windows Firewall

**Firewall might be blocking connection:**

1. **Open Windows Defender Firewall**
2. **Click:** "Allow an app through firewall"
3. **Find:** Apache HTTP Server
4. **Check:** Both Private and Public
5. **If not found:** Click "Allow another app" → Browse → `C:\xampp\apache\bin\httpd.exe`

**OR Temporarily disable firewall to test:**
1. Open Windows Defender Firewall
2. Turn off firewall temporarily
3. Test app
4. If works → Re-enable firewall and add Apache exception

---

### Step 5: Verify IP Address

**Check your computer's IP:**

1. **Open Command Prompt**
2. **Type:** `ipconfig`
3. **Look for:** IPv4 Address under WiFi adapter
4. **Should be:** `10.148.199.69`

**If different:**
- Update `ApiConstants.kt` with correct IP
- Sync project
- Rebuild

---

### Step 6: Test Apache Configuration

**Check Apache allows network connections:**

1. **Open:** `C:\xampp\apache\conf\httpd.conf`
2. **Find:** `Listen 8080`
3. **Should be:** `Listen 0.0.0.0:8080` or `Listen 8080`
4. **If different:** Change to `Listen 0.0.0.0:8080`
5. **Restart Apache**

---

## 🧪 Complete Test Procedure

### Test 1: Computer Browser
```
http://localhost:8080/vaxforsure/api/auth/login.php
```
**Expected:** JSON response ✅

### Test 2: Device Browser (SAME WiFi)
```
http://10.148.199.69:8080/vaxforsure/api/auth/login.php
```
**Expected:** JSON response ✅

### Test 3: Android App
- Run app
- Try registration
- Should work! ✅

---

## 🔍 Troubleshooting Checklist

**If still not working, check:**

- [ ] XAMPP Apache: GREEN ✅
- [ ] XAMPP MySQL: GREEN ✅
- [ ] Device on SAME WiFi as computer ✅
- [ ] Device NOT using mobile data ✅
- [ ] Windows Firewall allows Apache ✅
- [ ] Browser test works on device ✅
- [ ] IP address correct (10.148.199.69) ✅
- [ ] Apache listening on 0.0.0.0:8080 ✅

---

## 🚨 Most Common Issues

### Issue 1: Device on Different WiFi
**Symptom:** Error shows different IP range
**Fix:** Connect device to same WiFi as computer

### Issue 2: Apache Not Running
**Symptom:** "Connection refused"
**Fix:** Start Apache in XAMPP → Wait for GREEN

### Issue 3: Firewall Blocking
**Symptom:** Browser works on computer but not device
**Fix:** Allow Apache through Windows Firewall

### Issue 4: Wrong IP Address
**Symptom:** Cannot resolve host
**Fix:** Check `ipconfig` → Update `ApiConstants.kt`

---

## ✅ Quick Fix Summary

1. **Start XAMPP Apache** → GREEN ✅
2. **Connect device to SAME WiFi**
3. **Test in device browser first**
4. **Allow Apache through firewall**
5. **Test app**

---

## 📞 Test URLs

**Computer:**
- http://localhost:8080/vaxforsure/api/auth/login.php

**Device (same WiFi):**
- http://10.148.199.69:8080/vaxforsure/api/auth/login.php

**If device browser works → App will work!** ✅

---

## 🎯 Final Checklist

**Before Testing:**
- [ ] Apache: GREEN ✅
- [ ] Same WiFi ✅
- [ ] Firewall allows Apache ✅
- [ ] Browser test works ✅

**Then:**
- [ ] Run app
- [ ] Should connect! ✅

---

**Follow these steps and it WILL work!** 🚀

