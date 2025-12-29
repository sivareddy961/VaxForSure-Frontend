# Quick XAMPP Setup - 5 Minutes

## ⚡ Fast Setup (Copy-Paste Steps)

### 1️⃣ Start XAMPP (30 seconds)
```
1. Open XAMPP Control Panel
2. Click "Start" for Apache → Wait for GREEN ✅
3. Click "Start" for MySQL → Wait for GREEN ✅
```

### 2️⃣ Create Database (1 minute)
```
1. Open: http://localhost:8080/phpmyadmin
2. Click "New" in left sidebar
3. Database name: vaxforsure
4. Collation: utf8mb4_unicode_ci
5. Click "Create"
```

### 3️⃣ Import Database (2 minutes)
```
1. Click on "vaxforsure" database
2. Click "Import" tab
3. Click "Choose File"
4. Select: C:\xampp\htdocs\vaxforsure\database.sql
5. Click "Go"
6. Wait for "Import successful" message ✅
```

### 4️⃣ Verify Setup (1 minute)
```
1. Check tables: Should see 7 tables in vaxforsure
2. Test API: http://localhost:8080/vaxforsure/api/vaccines/schedule.php
3. Should see JSON data ✅
```

### 5️⃣ Done! (30 seconds)
```
✅ XAMPP is ready!
✅ Database is ready!
✅ Backend API is ready!
✅ Android app can connect!
```

---

## 🎯 That's It!

**Total Time: ~5 minutes**

**Your XAMPP is now:**
- ✅ Running on port 8080
- ✅ Database `vaxforsure` created
- ✅ All tables imported
- ✅ Backend API working
- ✅ Ready for Android app connection

**Next:** Run your Android app and test! 🚀

