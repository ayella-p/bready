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
        private const val DATABASE_VERSION = 6

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
        const val COL_INV_UNIT = "unit"

        // --- DISTRIBUTIONS TABLE (For the Admin List) ---
        const val TABLE_DISTRIBUTIONS = "admin_distributions"
        const val COL_DIST_NAME = "resident_name"
        const val COL_DIST_DETAILS = "details"
        const val COL_DIST_STATUS = "status"

        const val TABLE_EQUIPMENT = "equipment"
        const val COL_EQ_ID = "eq_id"
        const val COL_EQ_NAME = "eq_name"
        const val COL_EQ_DEPOSIT = "eq_deposit"
        const val COL_EQ_AVAILABLE = "eq_available"
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
                + "$COL_INV_MAX INTEGER,"
                + "$COL_INV_UNIT TEXT" + ")")
        db.execSQL(createInventoryTable)

        val createDistTable = ("CREATE TABLE $TABLE_DISTRIBUTIONS ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COL_DIST_NAME TEXT,"
                + "$COL_DIST_DETAILS TEXT,"
                + "$COL_DIST_STATUS TEXT" + ")")
        db.execSQL(createDistTable)

        val createEquipmentTable = ("CREATE TABLE $TABLE_EQUIPMENT ("
                + "$COL_EQ_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COL_EQ_NAME TEXT,"
                + "$COL_EQ_DEPOSIT TEXT,"
                + "$COL_EQ_AVAILABLE INTEGER" + ")")
        db.execSQL(createEquipmentTable)
        // insert default data
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INVENTORY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DISTRIBUTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EQUIPMENT")
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
            InventoryItem(1, "Rice (5kg)", 340, 500, "bags"),
            InventoryItem(2, "Canned Goods", 125, 600, "canned" ),
            InventoryItem(3, "Cooking Oil", 280, 400, "bottle")
        )
        for (item in inventoryData) {
            val v = ContentValues()
            v.put(COL_INV_NAME, item.name)
            v.put(COL_INV_CURRENT, item.currentStock)
            v.put(COL_INV_MAX, item.maxStock)
            v.put(COL_INV_UNIT, item.unit)
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
        val equipmentData = listOf(
            ContentValues().apply { put(COL_EQ_NAME, "Event Tent (10x10)"); put(COL_EQ_DEPOSIT, "₱500"); put(COL_EQ_AVAILABLE, 3) },
            ContentValues().apply { put(COL_EQ_NAME, "Plastic Chairs (Set of 50)"); put(COL_EQ_DEPOSIT, "₱200"); put(COL_EQ_AVAILABLE, 8) },
            ContentValues().apply { put(COL_EQ_NAME, "Sound System"); put(COL_EQ_DEPOSIT, "₱1000"); put(COL_EQ_AVAILABLE, 1) },
            ContentValues().apply { put(COL_EQ_NAME, "Folding Tables (Set of 10)"); put(COL_EQ_DEPOSIT, "₱300"); put(COL_EQ_AVAILABLE, 5) }
        )
        for (row in equipmentData) {
            db.insert(TABLE_EQUIPMENT, null, row)
        }


    }
    fun getAvailableEquipment(): List<ContentValues> {
        val list = ArrayList<ContentValues>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_EQUIPMENT", null)
        if (cursor.moveToFirst()) {
            do {
                val cv = ContentValues()
                cv.put("id", cursor.getInt(cursor.getColumnIndexOrThrow(COL_EQ_ID)))
                cv.put("name", cursor.getString(cursor.getColumnIndexOrThrow(COL_EQ_NAME)))
                cv.put("deposit", cursor.getString(cursor.getColumnIndexOrThrow(COL_EQ_DEPOSIT)))
                cv.put("available", cursor.getInt(cursor.getColumnIndexOrThrow(COL_EQ_AVAILABLE)))
                list.add(cv)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    // Handles the reservation system math
    fun reserveEquipmentItem(itemId: Int, name: String, deposit: String, dateStr: String): Boolean {
        val db = this.writableDatabase

        // Check current remaining availability counts
        val cursor = db.rawQuery("SELECT $COL_EQ_AVAILABLE FROM $TABLE_EQUIPMENT WHERE $COL_EQ_ID = ?", arrayOf(itemId.toString()))
        var currentAvailable = 0
        if (cursor.moveToFirst()) {
            currentAvailable = cursor.getInt(0)
        }
        cursor.close()

        if (currentAvailable <= 0) {
            db.close()
            return false // None left!
        }

        // Deduct 1 item count from the equipment inventory table
        val updateValues = ContentValues()
        updateValues.put(COL_EQ_AVAILABLE, currentAvailable - 1)
        db.update(TABLE_EQUIPMENT, updateValues, "$COL_EQ_ID = ?", arrayOf(itemId.toString()))

        // Inject this item directly into the ledger transactions history table for Admin tracking
        val txValues = ContentValues()
        txValues.put(COL_TITLE, "$name Reserved")
        txValues.put(COL_DATE, dateStr)
        txValues.put(COL_STATUS, "Paid")
        txValues.put(COL_PRICE, deposit)
        txValues.put(COL_IS_RELIEF, 0) // marked as 0 (Booking category item)
        db.insert(TABLE_TRANSACTIONS, null, txValues)

        db.close()
        return true
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
    fun getResidentNameByCode(code: String): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COL_USERNAME FROM $TABLE_USERS", null)
        var foundName = "Resident ($code)" // default fallback if no match

        if (cursor.moveToFirst()) {
            do {
                val username = cursor.getString(0)
                // Apply our signature extraction rule: match digits or default to 7777
                val digits = username.filter { it.isDigit() }
                val calculatedCode = if (digits.isNotEmpty()) digits else "7777"

                // If the code typed matches the user's generated code, capture their real username!
                if (calculatedCode == code || username.equals(code, ignoreCase = true)) {
                    foundName = username
                    break
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return foundName
    }
    fun isResidentIdClaimed(claimId: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_DISTRIBUTIONS WHERE $COL_DIST_DETAILS LIKE ? AND $COL_DIST_STATUS = ?",
            arrayOf("%$claimId%", "Verified")
        )
        val claimed = cursor.count > 0
        cursor.close()
        return claimed
    }
    fun insertDistribution(residentName: String, details: String, status: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_DIST_NAME, residentName)
        values.put(COL_DIST_DETAILS, details)
        values.put(COL_DIST_STATUS, status)

        val result = db.insert(TABLE_DISTRIBUTIONS, null, values)
        db.close()
        return result != -1L
    }

    fun getTodayVerificationCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_DISTRIBUTIONS", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    fun getInventory(): List<InventoryItem> {
        val list = ArrayList<InventoryItem>()
        val cursor = this.readableDatabase.rawQuery("SELECT * FROM $TABLE_INVENTORY", null)

        if (cursor.moveToFirst()) {
            do {
                list.add(InventoryItem(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_INV_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_INV_CURRENT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_INV_MAX)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_INV_UNIT))
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

    fun updateInventoryStock(itemId: Int, newStock: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_INV_CURRENT, newStock)

        val result = db.update(TABLE_INVENTORY, values, "$COL_ID=?", arrayOf(itemId.toString()))
        db.close()
        return result > 0
    }
}