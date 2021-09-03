package com.example.avaliacao_003.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.DetailsActivityBinding
import com.example.avaliacao_003.models.Patient
import com.example.avaliacao_003.utils.replaceFragment
import com.example.avaliacao_003.view.fragments.PatientDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: DetailsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        when (val details = intent.getSerializableExtra("details")) {
            is Patient -> {
                replaceFragment(PatientDetailsFragment.newInstance(details.id), R.id.containerDetails)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}