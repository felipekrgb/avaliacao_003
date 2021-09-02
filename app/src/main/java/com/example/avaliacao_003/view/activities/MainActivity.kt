package com.example.avaliacao_003.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.avaliacao_003.R
import com.example.avaliacao_003.view.fragments.PatientFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PatientFragment.newInstance())
                .commitNow()
        }
    }
}