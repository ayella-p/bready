package com.example.b_ready.screens.resident.borrow

import android.content.ContentValues

class BorrowPresenter(private val view: BorrowContract.View, private val model: BorrowModel) : BorrowContract.Presenter {
    override fun loadItems() { view.showEquipment(model.fetchItems()) }
    override fun clickReserve(item: ContentValues) {
        val success = model.performBooking(item.getAsInteger("id"), item.getAsString("name"), item.getAsString("deposit"))
        if (success) {
            view.showStatus("Reservation successful!")
            loadItems() // refresh counts
        } else {
            view.showStatus("Error: Item unavailable.")
        }
    }
}