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
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.ScheduleDetailsFragmentBinding
import com.example.avaliacao_003.databinding.ScheduleFragmentBinding
import com.example.avaliacao_003.models.*
import com.example.avaliacao_003.utils.snackBar
import com.example.avaliacao_003.view.activities.DetailsActivity
import com.example.avaliacao_003.view.activities.MainActivity
import com.example.avaliacao_003.view_model.MedicViewModel
import com.example.avaliacao_003.view_model.PatientViewModel
import com.example.avaliacao_003.view_model.ScheduleDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleDetailsFragment : Fragment(R.layout.schedule_details_fragment) {

    companion object {
        fun newInstance(id: Long): ScheduleDetailsFragment {
            return ScheduleDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong("schedule_id", id)
                }
            }
        }
    }

    private lateinit var binding: ScheduleDetailsFragmentBinding
    private lateinit var viewModel: ScheduleDetailsViewModel
    private lateinit var viewModelPatient: PatientViewModel
    private lateinit var viewModelMedic: MedicViewModel
    private lateinit var schedule: ScheduleWithMedicAndPatient
    private var selectedMedicId: Long? = null
    private var selectedPatient: Patient? = null

    private val observerSchedule = Observer<ScheduleWithMedicAndPatient> { schedule ->
        this.schedule = schedule
        selectedMedicId = schedule.medic.id
        selectedPatient = schedule.patient

        (requireActivity() as DetailsActivity).supportActionBar?.title =
            "${schedule.medic.name} - ${schedule.patient.name}"

        binding.medicTextInputLayout.editText?.setText(schedule.medic.name)
        binding.patientTextInputLayout.editText?.setText(schedule.patient.name)
    }

    private val observerMedics = Observer<List<MedicWithSpeciality>> { medics ->
        setupSpinnerMedics(medics)
    }

    private val observerPatients = Observer<List<Patient>> { patients ->
        setupSpinnerPatients(patients)
    }

    private val observerUpdateOrDelete = Observer<Boolean> { didUpdateOrDelete ->
        if (didUpdateOrDelete) {
            val intentToSchedules = Intent(activity?.applicationContext, MainActivity::class.java)
            intentToSchedules.putExtra("type", "Schedule")
            startActivity(intentToSchedules)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ScheduleDetailsFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(ScheduleDetailsViewModel::class.java)
        viewModelPatient = ViewModelProvider(this).get(PatientViewModel::class.java)
        viewModelMedic = ViewModelProvider(this).get(MedicViewModel::class.java)

        val scheduleId = arguments?.getLong("schedule_id") as Long

        viewModel.schedule.observe(viewLifecycleOwner, observerSchedule)
        viewModelMedic.medics.observe(viewLifecycleOwner, observerMedics)
        viewModelPatient.patients.observe(viewLifecycleOwner, observerPatients)
        viewModel.didUpdateOrDelete.observe(viewLifecycleOwner, observerUpdateOrDelete)

        setupEditButton()
        setupDeleteButton()

        viewModel.getSchedule(scheduleId)
        viewModelMedic.getMedics()
        viewModelPatient.getPatients()
    }

    private fun setupEditButton() {
        binding.editButton.setOnClickListener {
            if (selectedMedicId != null && selectedPatient != null &&
                (selectedMedicId != schedule.medic.id || selectedPatient != schedule.patient)
            ) {
                viewModel.updateSchedule(
                    Schedule(
                        id = schedule.schedule.id,
                        patientFK = selectedPatient!!.id,
                        medicFK = selectedMedicId!!,
                    )
                )
            } else {
                showSnackbar(R.string.no_fields_changed, R.color.red)
            }
        }
    }

    private fun setupDeleteButton() {
        binding.deleteButton.setOnClickListener {
            viewModel.deleteSchedule(schedule.schedule)
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
            selectedMedicId = (parent.getItemAtPosition(position) as MedicWithSpeciality).medic!!.id
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

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as DetailsActivity
        activity.snackBar(binding.patientTextInputLayout, msgId, colorId)
    }

}