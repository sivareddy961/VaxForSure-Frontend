# 🔧 Fix MySQL Connection Error - Step by Step

## 🚨 Error Message:
```
No connection could be made because the target machine actively refused it
```

**This means:** MySQL is **NOT RUNNING** in XAMPP

---

## ✅ SOLUTION: Start MySQL in XAMPP

### Step 1: Open XAMPP Control Panel
1. Find XAMPP Control Panel on your computer
2. Open it

### Step 2: Start MySQL
1. Look for **MySQL** in the list
2. Click **"Start"** button next to MySQL
3. **Wait** for status to turn **GREEN** ✅
4. You should see "Running" status

### Step 3: Verify MySQL is Running
- Status should be **GREEN** ✅
- Should show "Running" text
- Port should show **3306**

---

## 🧪 Test Database Connection

### Test 1: Test Connection File
1. Open browser
2. Go to: `http://localhost:8080/vaxforsure/test_db_connection.php`
3. Should see **GREEN** ✅ checks if MySQL is running

### Test 2: Test phpMyAdmin
1. Open browser
2. Go to: `http://localhost:8080/phpmyadmin`
3. Should open phpMyAdmin (confirms MySQL is running)

### Test 3: Test Registration API
1. Open browser
2. Go to: `http://localhost:8080/vaxforsure/api/auth/register.php`
3. Should see JSON response (not connection error)

---

## 📋 Checklist

Before testing your app:

- [ ] XAMPP Control Panel open ✅
- [ ] MySQL status is **GREEN** ✅
- [ ] MySQL shows "Running" ✅
- [ ] Test connection file shows success ✅
- [ ] phpMyAdmin opens successfully ✅

---

## 🔍 If MySQL Won't Start

### Issue: Port 3306 Already in Use

**Solution:**
1. Check if another MySQL is running
2. Stop other MySQL services
3. Or change MySQL port in XAMPP config

### Issue: MySQL Keeps Stopping

**Solution:**
1. Check XAMPP error logs
2. Restart XAMPP Control Panel
3. Run as Administrator
4. Check Windows Firewall

---

## ✅ After MySQL is Running

1. **Test connection:** `http://localhost:8080/vaxforsure/test_db_connection.php`
2. **Check database exists:** Should see database name
3. **If database missing:** Import `database.sql` in phpMyAdmin
4. **Test app:** Try registration again

---

## 🎯 Quick Fix Summary

**90% of MySQL errors = MySQL not running**

**Fix:**
1. Open XAMPP Control Panel
2. Click "Start" for MySQL
3. Wait for GREEN ✅
4. Try app again

**That's it!** 🚀

---

## 📱 Test Your App Now

After MySQL is GREEN:
1. Open Android app
2. Try registration
3. Should work now! ✅

If still error:
- Check Logcat for exact error
- Test connection file first
- Verify database exists

