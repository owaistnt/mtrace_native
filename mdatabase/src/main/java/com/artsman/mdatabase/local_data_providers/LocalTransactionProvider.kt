package com.artsman.mdatabase.local_data_providers

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artsman.mdatabase.AppDatabase
import com.artsman.mdatabase.entities.Transaction
import javax.inject.Inject
import javax.inject.Named


interface ILocalTransactionProvider{
    fun getAllTransactions(): List<Transaction>
}

class LocalTransactionProvider @Inject constructor(@Named("MainDatabase") private val db: AppDatabase) : ILocalTransactionProvider{
    override fun getAllTransactions(): List<Transaction> {
        return db.transactionDao().getAll()
    }
}