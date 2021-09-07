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
import com.example.avaliacao_003.databinding.MedicDetailsFragmentBinding
import com.example.avaliacao_003.models.Medic
import com.example.avaliacao_003.models.MedicWithSpeciality
import com.example.avaliacao_003.models.Speciality
import com.example.avaliacao_003.utils.snackBar
import com.example.avaliacao_003.view.activities.DetailsActivity
import com.example.avaliacao_003.view.activities.MainActivity
import com.example.avaliacao_003.view_model.MedicDetailsViewModel
import com.example.avaliacao_003.view_model.SpecialityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicDetailsFragment : Fragment(R.layout.medic_details_fragment) {

    companion object {
        fun newInstance(id: Long): MedicDetailsFragment {
            return MedicDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong("medic_id", id)
                }
            }
        }
    }

    private lateinit var binding: MedicDetailsFragmentBinding
    private lateinit var viewModel: MedicDetailsViewModel
    private lateinit var viewModelSpeciality: SpecialityViewModel
    private lateinit var medic: MedicWithSpeciality
    private var selectedSpeciality: Speciality? = null

    private val observerMedic = Observer<MedicWithSpeciality> { medic ->
        this.medic = medic
        selectedSpeciality = medic.speciality

        (requireActivity() as DetailsActivity).supportActionBar?.title =
            "${medic.medic!!.id} - ${medic.medic!!.name}"

        binding.nameEditText.setText(medic.medic!!.name)
        binding.specialityTextInputLayout.editText?.setText(selectedSpeciality!!.name)
    }

    private val observerSpecialities = Observer<List<Speciality>> { specialities ->
        setupSpinnerSpecialities(specialities)
    }

    private val observerUpdateOrDelete = Observer<Boolean> { didUpdateOrDelete ->
        if (didUpdateOrDelete) {
            val intentToMedics = Intent(activity?.applicationContext, MainActivity::class.java)
            intentToMedics.putExtra("type", "Medic")
            startActivity(intentToMedics)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MedicDetailsFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(MedicDetailsViewModel::class.java)
        viewModelSpeciality = ViewModelProvider(this).get(SpecialityViewModel::class.java)

        val medicId = arguments?.getLong("medic_id") as Long

        viewModel.medic.observe(viewLifecycleOwner, observerMedic)
        viewModel.didUpdateOrDelete.observe(viewLifecycleOwner, observerUpdateOrDelete)
        viewModelSpeciality.specialities.observe(viewLifecycleOwner, observerSpecialities)

        setupEditButton()
        setupDeleteButton()

        viewModel.getMedic(medicId)
        viewModelSpeciality.getSpecialities()
    }

    private fun setupEditButton() {
        binding.editButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()

            if (name.isNotEmpty() && selectedSpeciality != null &&
                (name != medic.medic!!.name || selectedSpeciality != medic.speciality)
            ) {
                viewModel.updateMedic(
                    Medic(
                        id = medic.medic!!.id,
                        name = name,
                        specialityFK = selectedSpeciality!!.id
                    )
                )
            } else {
                showSnackbar(R.string.no_fields_changed, R.color.red)
            }
        }
    }

    private fun setupDeleteButton() {
        binding.deleteButton.setOnClickListener {
            viewModel.deleteMedic(medic.medic!!)
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
            selectedSpeciality = selected
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as DetailsActivity
        activity.snackBar(binding.nameEditText, msgId, colorId)
    }
}