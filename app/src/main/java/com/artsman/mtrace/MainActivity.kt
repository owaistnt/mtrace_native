package com.artsman.mtrace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.artsman.mdatabase.local_data_providers.ILocalTransactionProvider
import com.artsman.mtransaction.TransacionListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var localTransactionProvider: ILocalTransactionProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.placeholder, TransacionListFragment()).commit()
    }
}