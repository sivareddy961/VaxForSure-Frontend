# Backend Flow Diagram

## 🔄 Complete Flow: Registration → Login → Database

```
┌─────────────────────────────────────────────────────────────────┐
│                    ANDROID APP (Frontend)                        │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ HTTP Request
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    XAMPP APACHE SERVER                           │
│                    Port: 8080                                    │
│                    http://localhost:8080                         │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ Routes Request
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│              PHP API ENDPOINTS                                   │
│  ┌──────────────────┐      ┌──────────────────┐                │
│  │ register.php     │      │ login.php        │                │
│  │ (POST)           │      │ (POST)           │                │
│  └──────────────────┘      └──────────────────┘                │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ Database Query
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    XAMPP MYSQL DATABASE                         │
│                    Port: 3306                                    │
│                    Database: vaxforsure                         │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                    users TABLE                            │  │
│  │  ┌─────┬────────────┬─────────────┬──────────────┐       │  │
│  │  │ id │ full_name  │ email       │ password     │       │  │
│  │  ├─────┼────────────┼─────────────┼──────────────┤       │  │
│  │  │  1 │ John Doe   │ john@ex.com │ $2y$10$...   │       │  │
│  │  └─────┴────────────┴─────────────┴──────────────┘       │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ Response Data
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    JSON RESPONSE                                │
│  {                                                              │
│    "success": true,                                             │
│    "message": "Login successful",                               │
│    "data": { "user": {...} }                                    │
│  }                                                              │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ HTTP Response
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    ANDROID APP (Frontend)                        │
│                    Updates UI / Saves Session                    │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📱 Registration Flow

```
User fills form
    │
    ▼
Click "Create Account"
    │
    ▼
Android App → POST /api/auth/register.php
    │
    │ JSON: { fullName, email, phone, password }
    ▼
Backend validates input
    │
    ▼
Check if email exists
    │
    ├─→ EXISTS → Return error: "Email already registered"
    │
    └─→ NOT EXISTS → Hash password
            │
            ▼
        Insert into users table
            │
            ▼
        Return success + user data
            │
            ▼
    Android App saves user session
            │
            ▼
    Navigate to "Add Child Profile"
```

---

## 🔐 Login Flow

```
User enters email & password
    │
    ▼
Click "Sign In"
    │
    ▼
Android App → POST /api/auth/login.php
    │
    │ JSON: { email, password }
    ▼
Backend queries database
    │
    ▼
Find user by email
    │
    ├─→ NOT FOUND → Return error: "Invalid email or password"
    │
    └─→ FOUND → Verify password
            │
            ├─→ WRONG → Return error: "Invalid email or password"
            │
            └─→ CORRECT → Return success + user data
                    │
                    ▼
            Android App saves session
                    │
                    ▼
            Navigate to Dashboard
```

---

## 🗄️ Database Structure

```
vaxforsure DATABASE
│
├── users TABLE
│   ├── id (Primary Key)
│   ├── full_name
│   ├── email (Unique)
│   ├── phone
│   ├── password (Hashed)
│   ├── email_verified
│   ├── created_at
│   └── updated_at
│
├── children TABLE
│   ├── id (Primary Key)
│   ├── user_id (Foreign Key → users.id)
│   ├── name
│   ├── date_of_birth
│   ├── gender
│   └── ...
│
├── health_details TABLE
│   └── ...
│
├── vaccinations TABLE
│   └── ...
│
├── reminders TABLE
│   └── ...
│
└── notifications TABLE
    └── ...
```

---

## 🔗 Connection URLs

### For Browser/Postman:
```
http://localhost:8080/vaxforsure/api/auth/login.php
http://localhost:8080/vaxforsure/api/auth/register.php
```

### For Android Emulator:
```
http://10.0.2.2:8080/vaxforsure/api/auth/login.php
http://10.0.2.2:8080/vaxforsure/api/auth/register.php
```
*(10.0.2.2 = special IP that maps to localhost from emulator)*

### For Physical Device:
```
http://YOUR_COMPUTER_IP:8080/vaxforsure/api/auth/login.php
http://YOUR_COMPUTER_IP:8080/vaxforsure/api/auth/register.php
```
*(Replace YOUR_COMPUTER_IP with actual IP from `ipconfig`)*

---

## 🚀 Startup Sequence

```
1. Start XAMPP Control Panel
   │
   ├─→ Start Apache (Port 8080)
   │   └─→ Status: GREEN ✅
   │
   └─→ Start MySQL (Port 3306)
       └─→ Status: GREEN ✅
           │
           ▼
2. Open phpMyAdmin
   │
   └─→ http://localhost:8080/phpmyadmin
       │
       ▼
3. Verify Database
   │
   └─→ Database: vaxforsure exists
       │
       └─→ 6 tables visible
           │
           ▼
4. Test Backend API
   │
   └─→ http://localhost:8080/vaxforsure/api/auth/login.php
       │
       └─→ Should return JSON ✅
           │
           ▼
5. Run Android App
   │
   └─→ Test Registration & Login ✅
```

---

## ✅ Success Indicators

**Backend Working:**
- ✅ Apache: GREEN in XAMPP
- ✅ MySQL: GREEN in XAMPP
- ✅ phpMyAdmin opens
- ✅ Database exists with 6 tables
- ✅ API endpoints return JSON

**App Connected:**
- ✅ Registration saves to database
- ✅ Login validates credentials
- ✅ Wrong password shows error
- ✅ User session saved

---

## 🎯 Key Points

1. **XAMPP** = Web Server (Apache) + Database (MySQL)
2. **Apache** = Serves PHP files (Port 8080)
3. **MySQL** = Stores data (Port 3306)
4. **phpMyAdmin** = Database management tool
5. **API Endpoints** = PHP files that handle requests
6. **Android App** = Makes HTTP requests to API
7. **Database** = Stores user data permanently

---

**Everything connects through HTTP requests!** 🌐

