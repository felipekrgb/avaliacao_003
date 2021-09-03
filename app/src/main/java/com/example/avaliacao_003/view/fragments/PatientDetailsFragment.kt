package com.example.avaliacao_003.view.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.PatientDetailsFragmentBinding
import com.example.avaliacao_003.models.Patient
import com.example.avaliacao_003.view.activities.DetailsActivity
import com.example.avaliacao_003.view.activities.MainActivity
import com.example.avaliacao_003.view_model.PatientDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientDetailsFragment : Fragment(R.layout.patient_details_fragment) {


    companion object {
        fun newInstance(id: Long): PatientDetailsFragment {
            return PatientDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong("patient_id", id)
                }
            }
        }
    }

    private lateinit var binding: PatientDetailsFragmentBinding
    private lateinit var viewModel: PatientDetailsViewModel
    private lateinit var patient: Patient

    private val observerPatient = Observer<Patient> { patient ->
        this.patient = patient
        (requireActivity() as DetailsActivity).supportActionBar?.title = patient.name

        binding.nameEditText.setText(patient.name)
        binding.ageEditText.setText(patient.age.toString())
        binding.genderEditText.setText(patient.gender)
    }

    private val observerUpdateOrDelete = Observer<Boolean> { didUpdateOrDelete ->
        if (didUpdateOrDelete) {
            val intentToPatients = Intent(activity?.applicationContext, MainActivity::class.java)
            startActivity(intentToPatients)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PatientDetailsFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(PatientDetailsViewModel::class.java)

        val patientId = arguments?.getLong("patient_id") as Long

        viewModel.getPatient(patientId)

        viewModel.patient.observe(viewLifecycleOwner, observerPatient)
        viewModel.didUpdateOrDelete.observe(viewLifecycleOwner, observerUpdateOrDelete)

        setupEditButton()
        setupDeleteButton()

    }

    fun setupEditButton() {
        binding.editButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val age = binding.ageEditText.text.toString()
            val gender = binding.genderEditText.text.toString()

            if (name.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty() &&
                (name != patient.name || age.toInt() != patient.age || gender != patient.gender)
            ) {
                viewModel.updatePatient(
                    Patient(
                        id = patient.id,
                        name = name,
                        age = age.toInt(),
                        gender = gender
                    )
                )
            }
        }
    }

    fun setupDeleteButton() {
        binding.deleteButton.setOnClickListener {
            viewModel.deletePatient(patient)
        }
    }
}