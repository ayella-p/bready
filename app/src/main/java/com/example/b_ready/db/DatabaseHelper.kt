package com.example.b_ready.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.b_ready.data.Transaction
import com.example.b_ready.data.User // Make sure to import User!

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "BReady.db"
        private const val DATABASE_VERSION = 2 // UPGRADED TO VERSION 2

        // --- TRANSACTIONS TABLE ---
        const val TABLE_TRANSACTIONS = "transactions"
        const val COL_ID = "id"
        const val COL_TITLE = "title"
        const val COL_DATE = "date"
        const val COL_STATUS = "status"
        const val COL_PRICE = "price"
        const val COL_IS_RELIEF = "is_relief"

        // --- USERS TABLE ---
        const val TABLE_USERS = "users"
        const val COL_USER_ID = "user_id"
        const val COL_USERNAME = "username"
        const val COL_PASSWORD = "password"
        const val COL_ROLE = "role"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create Transactions Table
        val createTransactionsTable = ("CREATE TABLE $TABLE_TRANSACTIONS ("
                + "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COL_TITLE TEXT,"
                + "$COL_DATE TEXT,"
                + "$COL_STATUS TEXT,"
                + "$COL_PRICE TEXT,"
                + "$COL_IS_RELIEF INTEGER" + ")")
        db.execSQL(createTransactionsTable)

        // Create Users Table
        val createUsersTable = ("CREATE TABLE $TABLE_USERS ("
                + "$COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COL_USERNAME TEXT UNIQUE," // Unique ensures no duplicate usernames
                + "$COL_PASSWORD TEXT,"
                + "$COL_ROLE TEXT" + ")")
        db.execSQL(createUsersTable)

        // Insert default Admin and test data
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // =========================================
    // USER AUTHENTICATION METHODS
    // =========================================

    // 1. Register a new user
    fun registerUser(user: User): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME, user.username)
        values.put(COL_PASSWORD, user.password)
        values.put(COL_ROLE, user.role) // Will usually be "Resident"

        // insert returns -1 if there was an error (like a duplicate username)
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    // 2. Authenticate Login
    fun authenticateUser(username: String, pword: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COL_USERNAME = ? AND $COL_PASSWORD = ?", arrayOf(username, pword))

        var loggedInUser: User? = null

        if (cursor.moveToFirst()) {
            val role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE))
            loggedInUser = User(username, pword, role)
        }

        cursor.close()
        return loggedInUser // Returns the User if found, or null if wrong password/username
    }

    // =========================================
    // DUMMY DATA INJECTION
    // =========================================
    private fun insertInitialData(db: SQLiteDatabase) {
        // --- 1. Create the Master Admin Account automatically ---
        val adminValues = ContentValues()
        adminValues.put(COL_USERNAME, "admin")
        adminValues.put(COL_PASSWORD, "admin123")
        adminValues.put(COL_ROLE, "Admin")
        db.insert(TABLE_USERS, null, adminValues)

        // ... (Keep your existing Transaction dummy data code here) ...
    }

    // ... (Keep your existing getAllTransactions method here) ...
}