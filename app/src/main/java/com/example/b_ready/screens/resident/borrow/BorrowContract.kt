package com.example.b_ready.screens.resident.borrow

import android.content.ContentValues

class BorrowContract {
    interface View {
        fun showEquipment(list: List<ContentValues>)
        fun showStatus(msg: String)
    }
    interface Presenter {
        fun loadItems()
        fun clickReserve(item: ContentValues)
    }
}