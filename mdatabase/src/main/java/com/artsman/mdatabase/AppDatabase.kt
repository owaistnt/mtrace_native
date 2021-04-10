package com.artsman.mdatabase

import androidx.room.*
import com.artsman.mdatabase.entities.Transaction

@Database(entities = arrayOf(Transaction::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}



@Dao
interface TransactionDao{
    @Query("SELECT * FROM `transaction`")
    fun getAll(): List<Transaction>

    @Insert
    fun insertAll(vararg transactions: Transaction)

    @Delete
    fun delete(transaction: Transaction)
}