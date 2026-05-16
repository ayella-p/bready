package com.example.b_ready.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.b_ready.data.InventoryItem
import com.example.b_ready.data.RecentDistribution
import com.example.b_ready.data.Transaction
import com.example.b_ready.data.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "BReady.db"
        private const val DATABASE_VERSION = 3

        //transaction table
        const val TABLE_TRANSACTIONS = "transactions"
        const val COL_ID = "id"
        const val COL_TITLE = "title"
        const val COL_DATE = "date"
        const val COL_STATUS = "status"
        const val COL_PRICE = "price"
        const val COL_IS_RELIEF = "is_relief"

        //user table
        const val TABLE_USERS = "users"
        const val COL_USER_ID = "user_id"
        const val COL_USERNAME = "username"
        const val COL_PASSWORD = "password"
        const val COL_ROLE = "role"

        // --- INVENTORY TABLE ---
        const val TABLE_INVENTORY = "inventory"
        const val COL_INV_NAME = "name"
        const val COL_INV_CURRENT = "current_stock"
        const val COL_INV_MAX = "max_stock"

        // --- DISTRIBUTIONS TABLE (For the Admin List) ---
        const val TABLE_DISTRIBUTIONS = "admin_distributions"
        const val COL_DIST_NAME = "resident_name"
        const val COL_DIST_DETAILS = "details"
        const val COL_DIST_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // create transaction table
        val createTransactionsTable = ("CREATE TABLE $TABLE_TRANSACTIONS ("
                + "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COL_TITLE TEXT,"
                + "$COL_DATE TEXT,"
                + "$COL_STATUS TEXT,"
                + "$COL_PRICE TEXT,"
                + "$COL_IS_RELIEF INTEGER" + ")")
        db.execSQL(createTransactionsTable)

        // create user table
        val createUsersTable = ("CREATE TABLE $TABLE_USERS ("
                + "$COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COL_USERNAME TEXT UNIQUE,"
                + "$COL_PASSWORD TEXT,"
                + "$COL_ROLE TEXT" + ")")
        db.execSQL(createUsersTable)

        // create inventory table
        val createInventoryTable = ("CREATE TABLE $TABLE_INVENTORY ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COL_INV_NAME TEXT,"
                + "$COL_INV_CURRENT INTEGER,"
                + "$COL_INV_MAX INTEGER" + ")")
        db.execSQL(createInventoryTable)

        val createDistTable = ("CREATE TABLE $TABLE_DISTRIBUTIONS ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COL_DIST_NAME TEXT,"
                + "$COL_DIST_DETAILS TEXT,"
                + "$COL_DIST_STATUS TEXT" + ")")
        db.execSQL(createDistTable)

        // insert default data
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // 1. Register a new user
    fun registerUser(user: User): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME, user.username)
        values.put(COL_PASSWORD, user.password)
        values.put(COL_ROLE, user.role)
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    // authenticate login
    fun authenticateUser(username: String, pword: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COL_USERNAME = ? AND $COL_PASSWORD = ?", arrayOf(username, pword))

        var loggedInUser: User? = null

        if (cursor.moveToFirst()) {
            val role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE))
            loggedInUser = User(username, pword, role)
        }

        cursor.close()
        return loggedInUser
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // --- 1. Create the Master Admin Account automatically ---
        val adminValues = ContentValues()
        adminValues.put(COL_USERNAME, "admin")
        adminValues.put(COL_PASSWORD, "admin123")
        adminValues.put(COL_ROLE, "Admin")
        db.insert(TABLE_USERS, null, adminValues)

        val dummyData = listOf(
            Transaction("Relief Pack Claimed", "April 8, 2026 • 2:45 PM", "Completed", "null", true),
            Transaction("Tent Booking Payment", "April 5, 2026 • 10:30 AM", "Paid", "₱100", false),
            Transaction("Relief Pack Claimed", "March 28, 2026 • 3:15 PM", "Completed", "null", true),
            Transaction("Chairs Booking Deposit", "March 20, 2026 • 11:00 AM", "Refunded", "₱200", false)
        )

        for (transaction in dummyData) {
            val values = ContentValues()
            values.put(COL_TITLE, transaction.title)
            values.put(COL_DATE, transaction.date)
            values.put(COL_STATUS, transaction.status)
            values.put(COL_PRICE, transaction.price ?: "null")
            values.put(COL_IS_RELIEF, if (transaction.isRelief) 1 else 0)

            db.insert(TABLE_TRANSACTIONS, null, values)
        }

        val inventoryData = listOf(
            InventoryItem("Rice (5kg)", 340, 500),
            InventoryItem("Canned Goods", 125, 600),
            InventoryItem("Cooking Oil", 280, 400)
        )
        for (item in inventoryData) {
            val v = ContentValues()
            v.put(COL_INV_NAME, item.name)
            v.put(COL_INV_CURRENT, item.currentStock)
            v.put(COL_INV_MAX, item.maxStock)
            db.insert(TABLE_INVENTORY, null, v)
        }

        // 2. Insert Recent Distributions
        val distData = listOf(
            RecentDistribution("Juan Santos", "BR 2026 1234 • 2:45 PM", "Verified"),
            RecentDistribution("Maria Cruz", "BR 2026 0987 • 2:30 PM", "Verified"),
            RecentDistribution("Pedro Reyes", "BR 2026 5432 • 2:15 PM", "Verified")
        )
        for (dist in distData) {
            val v = ContentValues()
            v.put(COL_DIST_NAME, dist.residentName)
            v.put(COL_DIST_DETAILS, dist.transactionDetails)
            v.put(COL_DIST_STATUS, dist.status)
            db.insert(TABLE_DISTRIBUTIONS, null, v)
        }
    }

    fun getAllTransactions(): List<Transaction> {
        val transactionList = ArrayList<Transaction>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TRANSACTIONS", null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE))
                val status = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS))
                val price = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRICE))
                val isReliefInt = cursor.getInt(cursor.getColumnIndexOrThrow(COL_IS_RELIEF))

                val finalPrice = if (price == "null") null else price
                val isRelief = isReliefInt == 1

                transactionList.add(Transaction(title, date, status, finalPrice, isRelief))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return transactionList
    }

    fun getInventory(): List<InventoryItem> {
        val list = ArrayList<InventoryItem>()
        val cursor = this.readableDatabase.rawQuery("SELECT * FROM $TABLE_INVENTORY", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(InventoryItem(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_INV_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_INV_CURRENT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_INV_MAX))
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun getRecentDistributions(): List<RecentDistribution> {
        val list = ArrayList<RecentDistribution>()
        val cursor = this.readableDatabase.rawQuery("SELECT * FROM $TABLE_DISTRIBUTIONS", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(RecentDistribution(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DIST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DIST_DETAILS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DIST_STATUS))
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}