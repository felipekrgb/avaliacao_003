package com.example.avaliacao_003.view.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.PatientDetailsFragmentBinding
import com.example.avaliacao_003.models.Gender
import com.example.avaliacao_003.models.Patient
import com.example.avaliacao_003.utils.snackBar
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
    private var selectedGender: Gender? = null

    private val observerPatient = Observer<Patient> { patient ->
        this.patient = patient
        selectedGender = Gender.values().find {
            it == patient.gender
        }
        (requireActivity() as DetailsActivity).supportActionBar?.title = patient.name

        binding.nameEditText.setText(patient.name)
        binding.ageEditText.setText(patient.age.toString())
        binding.genderTextInputLayout.editText?.setText(selectedGender?.type)
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

        viewModel.patient.observe(viewLifecycleOwner, observerPatient)
        viewModel.didUpdateOrDelete.observe(viewLifecycleOwner, observerUpdateOrDelete)

        setupEditButton()
        setupDeleteButton()
        setupSpinnerGender()

        viewModel.getPatient(patientId)

    }

    private fun setupEditButton() {
        binding.editButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val age = binding.ageEditText.text.toString()

            if (name.isNotEmpty() && age.isNotEmpty() && selectedGender != null &&
                (name != patient.name || age.toInt() != patient.age || selectedGender?.type != patient.gender.type)
            ) {
                viewModel.updatePatient(
                    Patient(
                        id = patient.id,
                        name = name,
                        age = age.toInt(),
                        gender = selectedGender!!
                    )
                )
            } else {
                showSnackbar(R.string.no_fields_changed, R.color.red)
            }
        }
    }

    private fun setupDeleteButton() {
        binding.deleteButton.setOnClickListener {
            viewModel.deletePatient(patient)
        }
    }

    private fun setupSpinnerGender() {
        val listOfGender = Gender.values().map { gender ->
            gender.type
        }

        val adapterSpinner =
            ArrayAdapter(
                requireContext(),
                R.layout.spinner_item_gender,
                listOfGender
            )

        val autoCompleteBrand: AutoCompleteTextView? =
            binding.genderTextInputLayout.editText as? AutoCompleteTextView

        autoCompleteBrand?.threshold = 1

        autoCompleteBrand?.setAdapter(adapterSpinner)

        autoCompleteBrand?.setOnItemClickListener { parent, view, position, id ->
            val selected = parent.getItemAtPosition(position) as String
            selectedGender = Gender.values().find {
                it.type == selected
            }
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as DetailsActivity
        activity.snackBar(binding.nameEditText, msgId, colorId)
    }
}