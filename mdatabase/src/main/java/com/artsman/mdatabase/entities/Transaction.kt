package com.artsman.mdatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "transaction")
data class Transaction(@PrimaryKey val id: String= UUID.randomUUID().toString(),
                       val date: Long,
                       val amount: Long) {

}