# ⚡ Quick Fix Network Error - 3 Steps

## 🚨 Most Likely Issues (90% of cases)

### ✅ FIX 1: Start XAMPP Apache (Most Common!)

**Problem:** XAMPP Apache not running

**Solution:**
1. Open XAMPP Control Panel
2. Click **"Start"** for Apache
3. Wait for **GREEN** ✅
4. Try app again

**Test:** Open `http://localhost:8080` → Should see XAMPP page

---

### ✅ FIX 2: Check Backend Files Exist

**Problem:** Backend files missing

**Check:**
1. Open: `C:\xampp\htdocs\vaxforsure\api\auth\`
2. Should see: `register.php` file ✅

**If missing:**
- Backend files need to be there
- Check `C:\xampp\htdocs\vaxforsure\` folder exists

---

### ✅ FIX 3: Test Backend URL

**Problem:** Backend not responding

**Test:**
1. Open browser
2. Go to: `http://localhost:8080/vaxforsure/api/vaccines/schedule.php`
3. Should see JSON data ✅

**If error:**
- Backend files missing
- Need to setup backend

---

## 🔍 Check Logcat for Exact Error

**In Android Studio:**
1. Run app
2. Try registration
3. Open **Logcat** tab
4. Look for error (red text)

**Common Errors:**

```
"Connection refused"
→ XAMPP Apache not running
→ Fix: Start Apache

"404 Not Found"
→ Backend file missing
→ Fix: Check backend files exist

"UnknownHostException"
→ Wrong URL
→ Fix: Check ApiConstants.kt
```

---

## ✅ Quick Checklist

- [ ] XAMPP Apache GREEN? ✅
- [ ] Backend test URL works? ✅
- [ ] Backend files exist? ✅
- [ ] Checked Logcat error? ✅

**If all checked → Check detailed guide: `FIX_NETWORK_ERROR.md`**

---

**99% of network errors are because XAMPP Apache is not running!**

**Check that first!** 🚀


