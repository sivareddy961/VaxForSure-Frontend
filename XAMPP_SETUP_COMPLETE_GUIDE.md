# Complete XAMPP Setup Guide - From Scratch

## 📋 Step-by-Step Instructions

### Step 1: Start XAMPP Services

1. **Open XAMPP Control Panel**
   - Find XAMPP in Start Menu or Desktop
   - Double-click to open

2. **Start Apache**
   - Click **"Start"** button next to Apache
   - Wait until it turns **GREEN** ✅
   - Status should show: "Running"

3. **Start MySQL**
   - Click **"Start"** button next to MySQL
   - Wait until it turns **GREEN** ✅
   - Status should show: "Running"

**✅ Both Apache and MySQL should be GREEN before proceeding**

---

### Step 2: Verify XAMPP is Working

1. **Open Browser**
   - Open Chrome/Firefox/Edge

2. **Test XAMPP Dashboard**
   - Go to: `http://localhost:8080`
   - Should see XAMPP welcome page ✅

3. **Test phpMyAdmin**
   - Go to: `http://localhost:8080/phpmyadmin`
   - Should see phpMyAdmin login page ✅
   - **No password needed** (default XAMPP setup)

**✅ If both pages open, XAMPP is working correctly**

---

### Step 3: Create Database

1. **Open phpMyAdmin**
   - Go to: `http://localhost:8080/phpmyadmin`
   - Click on **"New"** in the left sidebar

2. **Create Database**
   - Database name: `vaxforsure`
   - Collation: Select `utf8mb4_unicode_ci` from dropdown
   - Click **"Create"** button

3. **Verify Database Created**
   - You should see `vaxforsure` in left sidebar ✅
   - Click on it to select

**✅ Database `vaxforsure` is now created**

---

### Step 4: Import Database Schema

**Option A: Using Import Feature (Recommended)**

1. **Select Database**
   - Click on `vaxforsure` in left sidebar
   - Make sure it's selected (highlighted)

2. **Go to Import Tab**
   - Click on **"Import"** tab at the top

3. **Choose File**
   - Click **"Choose File"** or **"Browse"** button
   - Navigate to: `C:\xampp\htdocs\vaxforsure\`
   - Select file: `database.sql`
   - Click **"Open"**

4. **Import**
   - Scroll down
   - Click **"Go"** button at bottom
   - Wait for import to complete
   - Should see: **"Import has been successfully finished"** ✅

**Option B: Using SQL Tab**

1. **Select Database**
   - Click on `vaxforsure` in left sidebar

2. **Go to SQL Tab**
   - Click on **"SQL"** tab at the top

3. **Copy SQL Content**
   - Open file: `C:\xampp\htdocs\vaxforsure\database.sql`
   - Copy ALL content (Ctrl+A, Ctrl+C)

4. **Paste and Execute**
   - Paste into SQL textarea
   - Click **"Go"** button
   - Wait for execution

**✅ Database schema imported successfully**

---

### Step 5: Verify Tables Created

1. **Check Tables**
   - In phpMyAdmin, click on `vaxforsure` database
   - You should see these tables:
     - ✅ `users`
     - ✅ `children`
     - ✅ `vaccination_records`
     - ✅ `reminders`
     - ✅ `notifications`
     - ✅ `reminder_settings`
     - ✅ `vaccine_schedule`

2. **Verify Data**
   - Click on `vaccine_schedule` table
   - Click **"Browse"** tab
   - Should see sample vaccine data ✅

**✅ All tables created successfully**

---

### Step 6: Verify Backend Files

1. **Check File Location**
   - Navigate to: `C:\xampp\htdocs\vaxforsure\`
   - Should see these files/folders:
     ```
     vaxforsure/
     ├── config.php
     ├── database.sql
     ├── .htaccess
     └── api/
         ├── auth/
         ├── children/
         ├── vaccinations/
         ├── reminders/
         ├── notifications/
         └── vaccines/
     ```

2. **Verify Files Exist**
   - ✅ `config.php` exists
   - ✅ `database.sql` exists
   - ✅ `api/` folder exists with subfolders

**✅ Backend files are in place**

---

### Step 7: Test Backend API

1. **Test Vaccine Schedule API**
   - Open browser
   - Go to: `http://localhost:8080/vaxforsure/api/vaccines/schedule.php`
   - Should see JSON data like:
     ```json
     {
       "success": true,
       "message": "Vaccine schedule retrieved successfully",
       "data": [...]
     }
     ```

2. **Test Config File**
   - Go to: `http://localhost:8080/vaxforsure/config.php`
   - Should NOT show errors (blank page is OK)

**✅ Backend API is working**

---

### Step 8: Configure Database Connection (If Needed)

1. **Check Config File**
   - Open: `C:\xampp\htdocs\vaxforsure\config.php`
   - Verify these lines:
     ```php
     define('DB_HOST', 'localhost');
     define('DB_USER', 'root');
     define('DB_PASS', '');  // Empty for default XAMPP
     define('DB_NAME', 'vaxforsure');
     ```

2. **If You Set MySQL Password**
   - If you set a password during XAMPP install
   - Update `DB_PASS` with your password:
     ```php
     define('DB_PASS', 'your_password');
     ```

**✅ Database connection configured**

---

## ✅ Setup Complete Checklist

- [ ] XAMPP Control Panel opened
- [ ] Apache started (GREEN)
- [ ] MySQL started (GREEN)
- [ ] XAMPP dashboard opens: http://localhost:8080
- [ ] phpMyAdmin opens: http://localhost:8080/phpmyadmin
- [ ] Database `vaxforsure` created
- [ ] Database schema imported (`database.sql`)
- [ ] All 7 tables visible in phpMyAdmin
- [ ] Backend files exist in `C:\xampp\htdocs\vaxforsure\`
- [ ] API test works: http://localhost:8080/vaxforsure/api/vaccines/schedule.php
- [ ] Config file has correct database settings

---

## 🧪 Quick Test Commands

### Test 1: XAMPP Services
```
✅ Apache: http://localhost:8080
✅ phpMyAdmin: http://localhost:8080/phpmyadmin
```

### Test 2: Database
```
✅ Open phpMyAdmin → Click "vaxforsure" → Should see 7 tables
```

### Test 3: Backend API
```
✅ http://localhost:8080/vaxforsure/api/vaccines/schedule.php
Should return JSON data
```

### Test 4: Registration API (Postman)
```
POST http://localhost:8080/vaxforsure/api/auth/register.php
Headers: Content-Type: application/json
Body:
{
  "full_name": "Test User",
  "email": "test@test.com",
  "phone": "1234567890",
  "password": "password123",
  "confirm_password": "password123"
}
```

---

## 🔧 Troubleshooting

### Problem: Apache won't start
**Solutions:**
1. Check if port 8080 is in use
2. Click "Config" → "httpd.conf" → Change port
3. Or stop other services using port 8080

### Problem: MySQL won't start
**Solutions:**
1. Check if port 3306 is in use
2. Click "Config" → "my.ini" → Check port
3. Restart XAMPP

### Problem: Database import fails
**Solutions:**
1. Check file size (should be small)
2. Try SQL tab method instead
3. Check for syntax errors in SQL file

### Problem: API returns 404
**Solutions:**
1. Verify files in `C:\xampp\htdocs\vaxforsure\`
2. Check `.htaccess` file exists
3. Verify Apache is running

### Problem: API returns database error
**Solutions:**
1. Check database `vaxforsure` exists
2. Verify tables are created
3. Check `config.php` database credentials

---

## 📱 Android App Connection

**After XAMPP setup is complete:**

1. **Android Emulator:**
   - Already configured: `http://10.0.2.2:8080/vaxforsure/api/`
   - Just run the app ✅

2. **Physical Device:**
   - Find your IP: Run `ipconfig` in Command Prompt
   - Update `ApiConstants.kt`:
     ```kotlin
     const val BASE_URL = "http://YOUR_IP:8080/vaxforsure/api/"
     ```

---

## 🎯 Summary

**What You've Done:**
1. ✅ Started Apache and MySQL
2. ✅ Created database `vaxforsure`
3. ✅ Imported database schema
4. ✅ Verified tables created
5. ✅ Tested backend API

**What's Ready:**
- ✅ Backend API running on port 8080
- ✅ Database with all tables
- ✅ Android app configured to connect

**Next Step:**
- ✅ Run Android app and test registration/login

---

## 📞 Quick Reference

**XAMPP URLs:**
- Dashboard: `http://localhost:8080`
- phpMyAdmin: `http://localhost:8080/phpmyadmin`
- API Test: `http://localhost:8080/vaxforsure/api/vaccines/schedule.php`

**File Locations:**
- Backend: `C:\xampp\htdocs\vaxforsure\`
- Database SQL: `C:\xampp\htdocs\vaxforsure\database.sql`
- Config: `C:\xampp\htdocs\vaxforsure\config.php`

**Database:**
- Name: `vaxforsure`
- User: `root`
- Password: `` (empty)
- Port: `3306`

**Everything is ready! Follow the steps above and your XAMPP will be fully set up!** 🚀

