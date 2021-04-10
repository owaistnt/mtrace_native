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


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Provides
    fun providesDatabaseName(impl: DatabaseNameProviderImpl): DatabaseNameProvider{
        return impl
    }

    @Named("MainDatabase")
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context, databaseNameProvider: DatabaseNameProvider): AppDatabase{
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, databaseNameProvider.getString()
            ).build()
        }
}

@Module
@InstallIn(ActivityComponent::class)
interface LocalProviderModule{
    @Binds
    fun providesLocalTransactions(localTransactionProvider: LocalTransactionProvider): ILocalTransactionProvider
}


interface DatabaseNameProvider{
    fun getString(): String
}

class DatabaseNameProviderImpl @Inject constructor(): DatabaseNameProvider {
    override fun getString(): String {
        return "app_database"
    }

}