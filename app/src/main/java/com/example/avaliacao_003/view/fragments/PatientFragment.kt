package com.example.avaliacao_003.view.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avaliacao_003.R
import com.example.avaliacao_003.adapter.PatientAdapter
import com.example.avaliacao_003.databinding.PatientFragmentBinding
import com.example.avaliacao_003.models.Gender
import com.example.avaliacao_003.models.Patient
import com.example.avaliacao_003.models.Speciality
import com.example.avaliacao_003.utils.hideKeyboard
import com.example.avaliacao_003.utils.replaceFragment
import com.example.avaliacao_003.view.activities.DetailsActivity
import com.example.avaliacao_003.view.activities.MainActivity
import com.example.avaliacao_003.view_model.PatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientFragment : Fragment(R.layout.patient_fragment) {

    companion object {
        fun newInstance() = PatientFragment()
    }

    private lateinit var binding: PatientFragmentBinding
    private lateinit var viewModel: PatientViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter = PatientAdapter() { patient ->
        val intentToDetails = Intent(activity?.applicationContext, DetailsActivity::class.java)
        intentToDetails.putExtra("details", patient)
        startActivity(intentToDetails)
    }
    private var selectedGender: Gender? = null

    private val observerPatients = Observer<List<Patient>> { patients ->
        if (patients.isEmpty()) {
            binding.emptyPacientsTextView.visibility = View.VISIBLE
        } else {
            binding.emptyPacientsTextView.visibility = View.GONE
            adapter.update(patients)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PatientFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(PatientViewModel::class.java)

        recyclerView = binding.patientsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.patients.observe(viewLifecycleOwner, observerPatients)

        setupAddPatient()
        setupSpinnerGender()

        viewModel.getPatients()
    }

    private fun setupAddPatient() {
        binding.saveButton.setOnClickListener {
            var nameEditText = binding.nameEditText
            var ageEditText = binding.ageEditText

            if (nameEditText.text.toString().isNotEmpty() &&
                ageEditText.text.toString().isNotEmpty() &&
                selectedGender != null
            ) {
                viewModel.addPatient(
                    Patient(
                        name = nameEditText.text.toString(),
                        age = ageEditText.text.toString().toInt(),
                        gender = selectedGender!!
                    )
                )

                (requireActivity() as AppCompatActivity).hideKeyboard()

                nameEditText.text = null
                ageEditText.text = null
                binding.genderTextInputLayout.editText?.setText("")
                binding.genderTextInputLayout.clearFocus()
            }
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

}