package com.example.avaliacao_003.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.MainActivityBinding
import com.example.avaliacao_003.utils.replaceFragment
import com.example.avaliacao_003.view.fragments.PatientFragment
import com.example.avaliacao_003.view.fragments.SpecialityFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        replaceFragment(PatientFragment.newInstance())

        binding.bottomNav.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_patients -> replaceFragment(PatientFragment.newInstance())
                    R.id.nav_specialties -> replaceFragment(SpecialityFragment.newInstance())
                }
                true
            }
        }
    }
}