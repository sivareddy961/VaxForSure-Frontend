-- ============================================
-- VaxForSure - Users Table SQL Query
-- ============================================

-- Database Name: vaxforsure
-- Table Name: users

-- ============================================
-- CREATE DATABASE (if not exists)
-- ============================================
CREATE DATABASE IF NOT EXISTS `vaxforsure` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE `vaxforsure`;

-- ============================================
-- CREATE USERS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `email_verified` tinyint(1) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- TABLE STRUCTURE EXPLANATION
-- ============================================
-- id: Primary key, auto-incrementing user ID
-- full_name: User's full name (required)
-- email: User's email address (required, unique)
-- phone: User's phone number (optional)
-- password: Hashed password (required)
-- email_verified: Email verification status (0 = not verified, 1 = verified)
-- created_at: Account creation timestamp
-- updated_at: Last update timestamp

-- ============================================
-- INSERT TEST USER (Optional)
-- ============================================
-- Password: password123 (hashed)
-- INSERT INTO `users` (`full_name`, `email`, `phone`, `password`, `email_verified`) 
-- VALUES ('Test User', 'test@example.com', '1234567890', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 0);

-- ============================================
-- VIEW ALL USERS
-- ============================================
-- SELECT * FROM `users`;

-- ============================================
-- DELETE ALL USERS (Use with caution!)
-- ============================================
-- DELETE FROM `users`;

-- ============================================
-- DROP TABLE (Use with caution!)
-- ============================================
-- DROP TABLE IF EXISTS `users`;

