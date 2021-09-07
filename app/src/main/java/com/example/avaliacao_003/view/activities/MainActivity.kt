package com.example.avaliacao_003.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.MainActivityBinding
import com.example.avaliacao_003.utils.replaceFragment
import com.example.avaliacao_003.view.fragments.MedicFragment
import com.example.avaliacao_003.view.fragments.PatientFragment
import com.example.avaliacao_003.view.fragments.ScheduleFragment
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

        when (intent.getStringExtra("type")) {
            "Speciality" -> replaceFragment(SpecialityFragment.newInstance())
            "Medic" -> replaceFragment(MedicFragment.newInstance())
            "Schedule" -> replaceFragment(ScheduleFragment.newInstance())
            else -> replaceFragment(PatientFragment.newInstance())
        }

        binding.bottomNav.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_patients -> replaceFragment(PatientFragment.newInstance())
                    R.id.nav_specialities -> replaceFragment(SpecialityFragment.newInstance())
                    R.id.nav_medics -> replaceFragment(MedicFragment.newInstance())
                    R.id.nav_schedules -> replaceFragment(ScheduleFragment.newInstance())
                }
                true
            }
        }
    }
}