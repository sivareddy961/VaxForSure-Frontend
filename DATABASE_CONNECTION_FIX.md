# 🔧 Fix Database Connection Error

## ❌ Error You're Seeing

```
Warning: mysqli::_construct(): (HY000/2002): No connection could be made...
```

**This means:** MySQL database is NOT running or connection failed!

---

## ✅ QUICK FIX

### Step 1: Start MySQL in XAMPP

1. **Open XAMPP Control Panel**
2. **Find MySQL** in the list
3. **Click "Start"** button
4. **Wait for GREEN** ✅
5. **Status should show:** "Running" on port 3306

**MySQL MUST be GREEN before database will work!**

---

### Step 2: Verify Database Exists

1. **Open phpMyAdmin:** http://localhost:8080/phpmyadmin
2. **Check left sidebar** for `vaxforsure` database
3. **If NOT exists:** Create it (see Step 3)
4. **If exists:** Check if `users` table exists

---

### Step 3: Create Database and Table

**Option A: Using phpMyAdmin**

1. **Open phpMyAdmin:** http://localhost:8080/phpmyadmin
2. **Click "New"** on left sidebar
3. **Database name:** `vaxforsure`
4. **Collation:** `utf8mb4_unicode_ci`
5. **Click "Create"**
6. **Select `vaxforsure` database**
7. **Click "SQL" tab**
8. **Copy and paste SQL from `USERS_TABLE_SQL.sql`**
9. **Click "Go"**

**Option B: Import SQL File**

1. **Open phpMyAdmin:** http://localhost:8080/phpmyadmin
2. **Select `vaxforsure` database** (or create it first)
3. **Click "Import" tab**
4. **Choose file:** `C:\xampp\htdocs\vaxforsure\database.sql`
5. **Click "Go"**

---

## 📋 Database Information

### Database Name:
```
vaxforsure
```

### Table Name:
```
users
```

### Table Structure:
- `id` - Primary key (auto increment)
- `full_name` - User's full name
- `email` - Email address (unique)
- `phone` - Phone number
- `password` - Hashed password
- `email_verified` - Verification status
- `created_at` - Creation timestamp
- `updated_at` - Update timestamp

---

## 🧪 Test Database Connection

### Test 1: Check MySQL is Running

**In XAMPP Control Panel:**
- MySQL should be GREEN ✅
- Port should show 3306

### Test 2: Test Database Connection

**Open browser:**
```
http://localhost:8080/vaxforsure/test_connection.php
```

**Expected:** JSON response with database info

### Test 3: Check Table Exists

**In phpMyAdmin:**
1. Select `vaxforsure` database
2. Should see `users` table
3. Click "Browse" to see data

---

## 🔍 Troubleshooting

### Error: "No connection could be made"

**Cause:** MySQL not running

**Fix:**
1. Open XAMPP Control Panel
2. Start MySQL → Wait for GREEN ✅
3. Try again

### Error: "Unknown database 'vaxforsure'"

**Cause:** Database doesn't exist

**Fix:**
1. Create database `vaxforsure` in phpMyAdmin
2. Import `database.sql` file
3. Try again

### Error: "Table 'users' doesn't exist"

**Cause:** Table not created

**Fix:**
1. Select `vaxforsure` database
2. Run SQL from `USERS_TABLE_SQL.sql`
3. Or import `database.sql` file

---

## ✅ Checklist

**Before Testing:**
- [ ] XAMPP MySQL: GREEN ✅
- [ ] Database `vaxforsure` exists ✅
- [ ] Table `users` exists ✅
- [ ] Test connection works ✅

**Then:**
- [ ] Run app
- [ ] Try registration
- [ ] Should work! ✅

---

## 📞 Quick Reference

**Database Name:** `vaxforsure`  
**Table Name:** `users`  
**SQL File:** `USERS_TABLE_SQL.sql`  
**phpMyAdmin:** http://localhost:8080/phpmyadmin

---

**Start MySQL in XAMPP and it will work!** 🚀

