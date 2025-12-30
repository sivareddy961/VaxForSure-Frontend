# Backend Startup Checklist

## 🚀 Daily Startup (When Starting Development)

### Step 1: Start XAMPP Services
- [ ] Open XAMPP Control Panel
- [ ] Start Apache → Status: GREEN ✅
- [ ] Start MySQL → Status: GREEN ✅

### Step 2: Verify Services
- [ ] Apache port: `8080` (or `80`)
- [ ] MySQL port: `3306`
- [ ] Both showing "Running"

### Step 3: Quick Test
- [ ] Open: http://localhost:8080/phpmyadmin
- [ ] Should open phpMyAdmin ✅
- [ ] Database `vaxforsure` visible ✅

---

## 📋 Initial Setup (One-Time Only)

### Database Setup
- [ ] Database `vaxforsure` created
- [ ] Database schema imported (`database.sql`)
- [ ] 6 tables visible:
  - [ ] `users`
  - [ ] `children`
  - [ ] `health_details`
  - [ ] `vaccinations`
  - [ ] `reminders`
  - [ ] `notifications`

### Backend Files
- [ ] `config.php` exists
- [ ] `database.sql` exists
- [ ] `api/auth/login.php` exists
- [ ] `api/auth/register.php` exists

### Android Configuration
- [ ] `ApiConstants.kt` configured
- [ ] Retrofit dependencies added
- [ ] Network security config set
- [ ] Internet permission added

---

## 🧪 Testing Checklist

### Backend API Tests
- [ ] Registration endpoint responds (Postman/Browser)
- [ ] Login endpoint responds (Postman/Browser)
- [ ] Wrong credentials return error

### Android App Tests
- [ ] Registration creates user in database
- [ ] Login with correct credentials works
- [ ] Login with wrong credentials shows error
- [ ] User session saved after login

---

## ⚠️ Common Issues Quick Fix

| Issue | Quick Fix |
|-------|-----------|
| Apache won't start | Port in use → Change port or close app |
| MySQL won't start | Port 3306 in use → Close other MySQL |
| Database connection failed | Check MySQL is GREEN → Verify database exists |
| App can't connect | Check Apache is GREEN → Test URL in browser |
| Wrong credentials accepted | Check database → Verify password hash |

---

## 📞 Quick Reference

**XAMPP Control Panel:** `C:\xampp\xampp-control.exe`

**phpMyAdmin:** http://localhost:8080/phpmyadmin

**Backend Files:** `C:\xampp\htdocs\vaxforsure\`

**Test Login API:** http://localhost:8080/vaxforsure/api/auth/login.php

**Test Register API:** http://localhost:8080/vaxforsure/api/auth/register.php

---

## ✅ Ready to Code!

When all checkboxes are checked, you're ready to develop! 🎉

