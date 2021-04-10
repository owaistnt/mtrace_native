package com.artsman.mtrace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.artsman.mdatabase.DatabaseNameProvider
import com.artsman.mdatabase.local_data_providers.ILocalTransactionProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var databaseName: DatabaseNameProvider;

    @Inject
    lateinit var localTransactionProvider: ILocalTransactionProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TEST", "onCreate: ${databaseName.getString()}")
    }
}