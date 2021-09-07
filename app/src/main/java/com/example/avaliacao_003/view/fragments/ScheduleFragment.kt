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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avaliacao_003.R
import com.example.avaliacao_003.adapter.ScheduleAdapter
import com.example.avaliacao_003.databinding.ScheduleFragmentBinding
import com.example.avaliacao_003.models.*
import com.example.avaliacao_003.utils.hideKeyboard
import com.example.avaliacao_003.utils.snackBar
import com.example.avaliacao_003.view.activities.DetailsActivity
import com.example.avaliacao_003.view.activities.MainActivity
import com.example.avaliacao_003.view_model.MedicViewModel
import com.example.avaliacao_003.view_model.PatientViewModel
import com.example.avaliacao_003.view_model.ScheduleViewModel
import com.example.avaliacao_003.view_model.SpecialityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment : Fragment(R.layout.schedule_fragment) {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    private lateinit var binding: ScheduleFragmentBinding
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var viewModelPatient: PatientViewModel
    private lateinit var viewModelMedic: MedicViewModel
    private lateinit var viewModelSpeciality: SpecialityViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter = ScheduleAdapter() { schedule ->
        val intentToDetails = Intent(activity?.applicationContext, DetailsActivity::class.java)
        intentToDetails.putExtra("details", schedule)
        startActivity(intentToDetails)
    }
    private var selectedMedic: MedicWithSpeciality? = null
    private var selectedPatient: Patient? = null

    private val observerSchedules = Observer<List<ScheduleWithMedicAndPatient>> { schedules ->
        if (schedules.isEmpty()) {
            binding.schedulesRecyclerView.visibility = View.GONE
            binding.emptySchedulesTextView.visibility = View.VISIBLE
        } else {
            binding.schedulesRecyclerView.visibility = View.VISIBLE
            binding.emptySchedulesTextView.visibility = View.GONE
            adapter.update(schedules)
        }
    }

    private val observerMedics = Observer<List<MedicWithSpeciality>> { medics ->
        setupSpinnerMedics(medics)
    }

    private val observerPatients = Observer<List<Patient>> { patients ->
        setupSpinnerPatients(patients)
    }

    private val observerSpecialities = Observer<List<Speciality>> { specialities ->
        setupSpinnerSpecialities(specialities)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ScheduleFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        viewModelPatient = ViewModelProvider(this).get(PatientViewModel::class.java)
        viewModelMedic = ViewModelProvider(this).get(MedicViewModel::class.java)
        viewModelSpeciality = ViewModelProvider(this).get(SpecialityViewModel::class.java)

        recyclerView = binding.schedulesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.schedules.observe(viewLifecycleOwner, observerSchedules)
        viewModelMedic.medics.observe(viewLifecycleOwner, observerMedics)
        viewModelPatient.patients.observe(viewLifecycleOwner, observerPatients)
        viewModelSpeciality.specialities.observe(viewLifecycleOwner, observerSpecialities)

        setupAddSchedule()
        setupSpinnerGender()
        setupClearFilters()

        viewModel.getSchedules()
        viewModelMedic.getMedics()
        viewModelPatient.getPatients()
        viewModelSpeciality.getSpecialities()

    }

    private fun setupAddSchedule() {
        binding.saveButton.setOnClickListener {
            if (selectedMedic != null && selectedPatient != null) {
                viewModel.addSchedule(
                    Schedule(
                        medicFK = selectedMedic!!.medic!!.id,
                        patientFK = selectedPatient!!.id
                    )
                )

                showSnackbar(R.string.schedule_added, R.color.green)

                binding.medicTextInputLayout.editText?.setText("")
                binding.patientTextInputLayout.editText?.setText("")
                binding.patientTextInputLayout.clearFocus()
                selectedMedic = null
                selectedPatient = null
            } else {
                showSnackbar(R.string.medic_no_fields, R.color.red)
            }
        }
    }

    private fun setupSpinnerMedics(medics: List<MedicWithSpeciality>) {
        val adapterSpinner =
            ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                medics
            )

        val autoCompleteBrand: AutoCompleteTextView? =
            binding.medicTextInputLayout.editText as? AutoCompleteTextView

        autoCompleteBrand?.threshold = 1

        autoCompleteBrand?.setAdapter(adapterSpinner)

        autoCompleteBrand?.setOnItemClickListener { parent, view, position, id ->
            selectedMedic = parent.getItemAtPosition(position) as MedicWithSpeciality
        }
    }

    private fun setupSpinnerPatients(patients: List<Patient>) {
        val adapterSpinner =
            ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                patients
            )

        val autoCompleteBrand: AutoCompleteTextView? =
            binding.patientTextInputLayout.editText as? AutoCompleteTextView

        autoCompleteBrand?.threshold = 1

        autoCompleteBrand?.setAdapter(adapterSpinner)

        autoCompleteBrand?.setOnItemClickListener { parent, view, position, id ->
            selectedPatient = parent.getItemAtPosition(position) as Patient
        }
    }

    private fun setupSpinnerSpecialities(specialities: List<Speciality>) {
        val adapterSpinner =
            ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                specialities
            )

        val autoCompleteBrand: AutoCompleteTextView? =
            binding.specialityTextInputLayout.editText as? AutoCompleteTextView

        autoCompleteBrand?.threshold = 1

        autoCompleteBrand?.setAdapter(adapterSpinner)

        autoCompleteBrand?.setOnItemClickListener { parent, view, position, id ->
            val selected = parent.getItemAtPosition(position) as Speciality
            viewModel.getSchedulesBySpeciality(selected.id)

            binding.patientGenderTextInputLayout.editText?.setText("")
            (requireActivity() as MainActivity).hideKeyboard()
        }
    }

    private fun setupSpinnerGender() {
        val listOfGender = Gender.values().map { gender ->
            gender.type
        }

        val adapterSpinner =
            ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                listOfGender
            )

        val autoCompleteBrand: AutoCompleteTextView? =
            binding.patientGenderTextInputLayout.editText as? AutoCompleteTextView

        autoCompleteBrand?.threshold = 1

        autoCompleteBrand?.setAdapter(adapterSpinner)

        autoCompleteBrand?.setOnItemClickListener { parent, view, position, id ->
            val selected = parent.getItemAtPosition(position) as String
            val gender = Gender.values().find {
                it.type == selected
            }

            viewModel.getSchedulesByGender(gender!!)
            binding.specialityTextInputLayout.editText?.setText("")
            (requireActivity() as MainActivity).hideKeyboard()
        }
    }

    fun setupClearFilters() {
        binding.clearFiltersButton.setOnClickListener {
            viewModel.getSchedules()
            binding.patientGenderTextInputLayout.editText?.setText("")
            binding.specialityTextInputLayout.editText?.setText("")
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as MainActivity
        val bottomNav = activity.binding.bottomNav
        activity.snackBar(bottomNav, msgId, colorId)
    }
}