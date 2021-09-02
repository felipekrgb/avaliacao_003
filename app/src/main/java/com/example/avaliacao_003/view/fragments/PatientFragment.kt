package com.example.avaliacao_003.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.PatientFragmentBinding
import com.example.avaliacao_003.view_model.PatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientFragment : Fragment(R.layout.patient_fragment) {

    companion object {
        fun newInstance() = PatientFragment()
    }

    private lateinit var binding: PatientFragmentBinding
    private lateinit var viewModel: PatientViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PatientFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(PatientViewModel::class.java)

    }

}