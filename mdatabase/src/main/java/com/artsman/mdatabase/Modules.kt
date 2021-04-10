package com.artsman.mdatabase

import android.content.Context
import androidx.room.Room
import com.artsman.mdatabase.local_data_providers.ILocalTransactionProvider
import com.artsman.mdatabase.local_data_providers.LocalTransactionProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    lateinit var database: AppDatabase

    private fun getDatabase(context: Context): AppDatabase {
        if (!::database.isInitialized) {
            database = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "mtracedb"
            ).build()
        }
        return database

    }

    @Provides
    @Singleton
    fun providesTransactionDao(@ApplicationContext context: Context): TransactionDao{
        return getDatabase(context).transactionDao()
    }
}

@Module
@InstallIn(ActivityComponent::class)
object LocalProviderModule{

    @Provides
    fun providesLocalTransactions(localTransactionProvider: LocalTransactionProvider): ILocalTransactionProvider{return localTransactionProvider}


}