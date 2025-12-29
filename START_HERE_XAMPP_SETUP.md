# 🚀 START HERE - XAMPP Setup Guide

## ⚡ Quick Setup (Follow These Steps)

### ✅ STEP 1: Start XAMPP (1 minute)

```
1. Open XAMPP Control Panel
   └─> Find XAMPP icon → Double-click

2. Start Apache
   └─> Click "Start" button → Wait for GREEN ✅

3. Start MySQL  
   └─> Click "Start" button → Wait for GREEN ✅
```

**✅ Both should be GREEN before continuing**

---

### ✅ STEP 2: Create Database (1 minute)

```
1. Open Browser
   └─> Go to: http://localhost:8080/phpmyadmin

2. Click "New" (left sidebar)
   └─> Database name: vaxforsure
   └─> Collation: utf8mb4_unicode_ci
   └─> Click "Create"
```

**✅ Database `vaxforsure` should appear in left sidebar**

---

### ✅ STEP 3: Import Database (2 minutes)

```
1. Click on "vaxforsure" database (left sidebar)

2. Click "Import" tab (top)

3. Click "Choose File"
   └─> Navigate to: C:\xampp\htdocs\vaxforsure\
   └─> Select: database.sql
   └─> Click "Open"

4. Scroll down → Click "Go" button

5. Wait for: "Import has been successfully finished" ✅
```

**✅ Should see success message**

---

### ✅ STEP 4: Verify Setup (1 minute)

```
1. Check Tables
   └─> Click "vaxforsure" database
   └─> Should see 7 tables:
       ✅ users
       ✅ children
       ✅ vaccination_records
       ✅ reminders
       ✅ notifications
       ✅ reminder_settings
       ✅ vaccine_schedule

2. Test API
   └─> Open: http://localhost:8080/vaxforsure/api/vaccines/schedule.php
   └─> Should see JSON data ✅
```

**✅ If you see JSON data, everything is working!**

---

## 🎯 That's It! Setup Complete!

**Your XAMPP is now:**
- ✅ Running on port 8080
- ✅ Database created
- ✅ Tables imported
- ✅ Backend API working
- ✅ Ready for Android app

---

## 📱 Android App Connection

**Already Configured:**
- Emulator: `http://10.0.2.2:8080/vaxforsure/api/` ✅
- Just run the app!

**For Physical Device:**
1. Find your IP: Run `ipconfig` in Command Prompt
2. Update `ApiConstants.kt`: `http://YOUR_IP:8080/vaxforsure/api/`

---

## 🆘 Need Help?

**If something doesn't work:**

1. **Apache won't start?**
   - Check port 8080 isn't in use
   - Restart XAMPP

2. **Database import fails?**
   - Try SQL tab method (see detailed guide)
   - Check file path is correct

3. **API returns 404?**
   - Check files in `C:\xampp\htdocs\vaxforsure\`
   - Verify Apache is running

**See `XAMPP_SETUP_STEP_BY_STEP.md` for detailed troubleshooting**

---

## ✅ Final Checklist

- [ ] Apache running (GREEN)
- [ ] MySQL running (GREEN)
- [ ] Database `vaxforsure` created
- [ ] Tables imported (7 tables visible)
- [ ] API test works (JSON data shown)

**If all checked ✅ → You're ready to go!** 🚀

---

**Follow these 4 steps and you'll be done in 5 minutes!**

