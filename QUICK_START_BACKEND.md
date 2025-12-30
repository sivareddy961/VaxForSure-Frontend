# Quick Start - Backend Setup (5 Minutes)

## ⚡ Fast Setup Steps

### 1️⃣ Start XAMPP (30 seconds)
- Open XAMPP Control Panel
- Click "Start" for **Apache** → Wait for GREEN ✅
- Click "Start" for **MySQL** → Wait for GREEN ✅

### 2️⃣ Create Database (1 minute)
- Open browser: http://localhost:8080/phpmyadmin
- Click "New" → Database name: `vaxforsure` → Click "Create"

### 3️⃣ Import Schema (1 minute)
- Click `vaxforsure` database
- Click "Import" tab
- Choose file: `C:\xampp\htdocs\vaxforsure\database.sql`
- Click "Go"

### 4️⃣ Verify (30 seconds)
- Check 6 tables created: users, children, health_details, vaccinations, reminders, notifications

### 5️⃣ Test (2 minutes)
- Test in browser: http://localhost:8080/vaxforsure/api/auth/login.php
- Should see JSON response ✅

## 📱 Android App

**Already configured!** ✅
- API URL: `http://10.0.2.2:8080/vaxforsure/api/` (for emulator)
- Just run the app and test!

## 🧪 Quick Test

1. **Register:** Create account in app → Should save to database ✅
2. **Login:** Use same email/password → Should login ✅
3. **Wrong Password:** Use wrong password → Should show error ✅

## ❌ Common Issues

**Apache/MySQL won't start?**
→ Port in use → Close other apps or change port

**Database connection failed?**
→ Check MySQL is GREEN → Check database exists

**App can't connect?**
→ Check Apache is GREEN → Test URL in browser first

**Need more help?**
→ See `COMPLETE_BACKEND_SETUP.md` for detailed guide

---

**That's it! Your backend is ready! 🚀**

