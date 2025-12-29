# XAMPP Setup - Step by Step (Clear Instructions)

## 🎯 Complete Setup Instructions

### STEP 1: Start XAMPP Services ⏱️ 1 minute

1. **Open XAMPP Control Panel**
   - Look for XAMPP icon on desktop or Start Menu
   - Double-click to open

2. **Start Apache**
   - Find "Apache" in the list
   - Click the **"Start"** button
   - Wait for the status to turn **GREEN** ✅
   - You should see "Running" status

3. **Start MySQL**
   - Find "MySQL" in the list  
   - Click the **"Start"** button
   - Wait for the status to turn **GREEN** ✅
   - You should see "Running" status

**✅ CHECKPOINT:** Both Apache and MySQL should be GREEN before moving to next step.

---

### STEP 2: Open phpMyAdmin ⏱️ 30 seconds

1. **Open Browser**
   - Open Chrome, Firefox, or Edge

2. **Go to phpMyAdmin**
   - Type in address bar: `http://localhost:8080/phpmyadmin`
   - Press Enter
   - Should see phpMyAdmin login page

3. **Login (if asked)**
   - Username: `root`
   - Password: Leave **EMPTY** (default XAMPP)
   - Click "Go"

**✅ CHECKPOINT:** phpMyAdmin should open successfully.

---

### STEP 3: Create Database ⏱️ 1 minute

1. **Click "New" Button**
   - Look at the left sidebar
   - Click the **"New"** button/link at the top

2. **Enter Database Name**
   - In "Database name" field, type: `vaxforsure`
   - (Type exactly: vaxforsure - all lowercase)

3. **Select Collation**
   - Find "Collation" dropdown
   - Select: `utf8mb4_unicode_ci`
   - (Scroll to find it if needed)

4. **Create Database**
   - Click **"Create"** button
   - Wait a moment

5. **Verify Database Created**
   - Look at left sidebar
   - You should see `vaxforsure` listed ✅
   - Click on it to select it

**✅ CHECKPOINT:** Database `vaxforsure` should appear in left sidebar.

---

### STEP 4: Import Database Schema ⏱️ 2 minutes

**Method 1: Using Import Tab (Easiest)**

1. **Make Sure Database is Selected**
   - Click on `vaxforsure` in left sidebar
   - It should be highlighted/selected

2. **Go to Import Tab**
   - Look at the top tabs
   - Click on **"Import"** tab

3. **Choose SQL File**
   - Click **"Choose File"** or **"Browse"** button
   - Navigate to: `C:\xampp\htdocs\vaxforsure\`
   - Select file: `database.sql`
   - Click **"Open"**

4. **Import the File**
   - Scroll down the page
   - Click the **"Go"** button at the bottom
   - Wait for import to complete
   - Should see green message: **"Import has been successfully finished"** ✅

**Method 2: Using SQL Tab (If Import doesn't work)**

1. **Select Database**
   - Click on `vaxforsure` in left sidebar

2. **Go to SQL Tab**
   - Click **"SQL"** tab at the top

3. **Open SQL File**
   - Open file: `C:\xampp\htdocs\vaxforsure\database.sql`
   - Copy ALL content (Ctrl+A, then Ctrl+C)

4. **Paste and Execute**
   - Paste into the SQL textarea box
   - Click **"Go"** button
   - Wait for execution

**✅ CHECKPOINT:** Should see success message after import.

---

### STEP 5: Verify Tables Created ⏱️ 30 seconds

1. **Check Tables List**
   - Make sure `vaxforsure` database is selected
   - Look at the main area
   - You should see a list of tables:

   **Required Tables:**
   - ✅ `users`
   - ✅ `children`
   - ✅ `vaccination_records`
   - ✅ `reminders`
   - ✅ `notifications`
   - ✅ `reminder_settings`
   - ✅ `vaccine_schedule`

2. **Verify Sample Data**
   - Click on `vaccine_schedule` table
   - Click **"Browse"** tab
   - Should see some vaccine data rows ✅

**✅ CHECKPOINT:** All 7 tables should be visible.

---

### STEP 6: Test Backend API ⏱️ 1 minute

1. **Test API Endpoint**
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

2. **If You See JSON Data**
   - ✅ Backend is working correctly!
   - ✅ API is responding!

3. **If You See Error**
   - Check files exist in `C:\xampp\htdocs\vaxforsure\`
   - Check Apache is running (GREEN)
   - Check database `vaxforsure` exists

**✅ CHECKPOINT:** API should return JSON data.

---

### STEP 7: Verify Backend Files ⏱️ 30 seconds

1. **Open File Explorer**
   - Press Windows Key + E

2. **Navigate to Backend Folder**
   - Go to: `C:\xampp\htdocs\vaxforsure\`

3. **Check Required Files**
   - Should see:
     - ✅ `config.php`
     - ✅ `database.sql`
     - ✅ `.htaccess` (may be hidden)
     - ✅ `api/` folder

4. **Check API Folders**
   - Open `api/` folder
   - Should see subfolders:
     - ✅ `auth/`
     - ✅ `children/`
     - ✅ `vaccinations/`
     - ✅ `reminders/`
     - ✅ `notifications/`
     - ✅ `vaccines/`

**✅ CHECKPOINT:** All backend files should be present.

---

## ✅ Final Verification Checklist

Before connecting Android app, verify:

- [ ] ✅ XAMPP Control Panel: Apache is GREEN
- [ ] ✅ XAMPP Control Panel: MySQL is GREEN  
- [ ] ✅ phpMyAdmin opens: http://localhost:8080/phpmyadmin
- [ ] ✅ Database `vaxforsure` exists in phpMyAdmin
- [ ] ✅ All 7 tables visible in `vaxforsure` database
- [ ] ✅ API test works: http://localhost:8080/vaxforsure/api/vaccines/schedule.php
- [ ] ✅ Backend files exist in `C:\xampp\htdocs\vaxforsure\`

**If all checked ✅, your XAMPP is ready!**

---

## 🚀 Next Steps

**After XAMPP setup:**

1. **Android App is Already Configured**
   - URL: `http://10.0.2.2:8080/vaxforsure/api/`
   - Ready to connect ✅

2. **Test Connection**
   - Run Android app
   - Try registration/login
   - Check Logcat for API calls

3. **If Testing on Physical Device**
   - Find your IP: Run `ipconfig` in Command Prompt
   - Update `ApiConstants.kt` with your IP
   - Make sure device and computer on same WiFi

---

## 📋 Quick Reference

**XAMPP URLs:**
- Dashboard: `http://localhost:8080`
- phpMyAdmin: `http://localhost:8080/phpmyadmin`
- API Test: `http://localhost:8080/vaxforsure/api/vaccines/schedule.php`

**Database Info:**
- Name: `vaxforsure`
- Username: `root`
- Password: `` (empty)
- Host: `localhost`
- Port: `3306`

**File Locations:**
- Backend: `C:\xampp\htdocs\vaxforsure\`
- Database SQL: `C:\xampp\htdocs\vaxforsure\database.sql`

---

## 🆘 Common Issues

**Apache won't start?**
- Port 8080 might be in use
- Check other applications using port 8080
- Or change Apache port in httpd.conf

**MySQL won't start?**
- Port 3306 might be in use
- Check other MySQL instances running
- Restart XAMPP

**Database import fails?**
- Check file path is correct
- Try SQL tab method instead
- Check file isn't corrupted

**API returns 404?**
- Verify files in `C:\xampp\htdocs\vaxforsure\`
- Check Apache is running
- Verify URL is correct

---

**Follow these steps exactly and your XAMPP will be fully set up!** 🎉

