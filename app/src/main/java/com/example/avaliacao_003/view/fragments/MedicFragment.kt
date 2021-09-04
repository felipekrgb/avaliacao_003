package com.example.avaliacao_003.view.fragments

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
import com.example.avaliacao_003.adapter.MedicAdapter
import com.example.avaliacao_003.databinding.MedicFragmentBinding
import com.example.avaliacao_003.models.Medic
import com.example.avaliacao_003.models.MedicWithSpeciality
import com.example.avaliacao_003.models.Speciality
import com.example.avaliacao_003.utils.hideKeyboard
import com.example.avaliacao_003.view_model.MedicViewModel
import com.example.avaliacao_003.view_model.SpecialityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicFragment : Fragment(R.layout.medic_fragment) {

    companion object {
        fun newInstance() = MedicFragment()
    }

    private lateinit var binding: MedicFragmentBinding
    private lateinit var viewModel: MedicViewModel
    private lateinit var viewModelSpeciality: SpecialityViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter = MedicAdapter()
    private var selectedSpeciality: Speciality? = null

    private val observerSpecialities = Observer<List<Speciality>> { specialities ->
        setupSpinnerSpecialities(specialities)
    }

    private val observerMedics = Observer<List<MedicWithSpeciality>> { medics ->
        if (medics.isEmpty()) {
            binding.emptyMedicsTextView.visibility = View.VISIBLE
        } else {
            binding.emptyMedicsTextView.visibility = View.GONE
            adapter.update(medics)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MedicFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(MedicViewModel::class.java)
        viewModelSpeciality = ViewModelProvider(this).get(SpecialityViewModel::class.java)

        recyclerView = binding.medicsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModelSpeciality.specialities.observe(viewLifecycleOwner, observerSpecialities)
        viewModel.medics.observe(viewLifecycleOwner, observerMedics)

        setupAddMedic()

        viewModel.getMedics()
        viewModelSpeciality.getSpecialities()

    }

    private fun setupAddMedic() {
        binding.saveButton.setOnClickListener {
            var nameEditText = binding.nameEditText

            if (nameEditText.text.toString().isNotEmpty() && selectedSpeciality != null) {
                viewModel.addMedic(
                    Medic(
                        name = nameEditText.text.toString(),
                        specialityFK = selectedSpeciality!!.id
                    )
                )

                (requireActivity() as AppCompatActivity).hideKeyboard()

                nameEditText.text = null
                binding.specialitiesTextInputLayout.editText?.setText("")
                binding.specialitiesTextInputLayout.clearFocus()
                selectedSpeciality = null
            }

        }
    }

    private fun setupSpinnerSpecialities(specialities: List<Speciality>) {
        val adapterSpinner =
            ArrayAdapter(
                requireContext(),
                R.layout.spinner_item_specialities,
                specialities
            )

        val autoCompleteBrand: AutoCompleteTextView? =
            binding.specialitiesTextInputLayout.editText as? AutoCompleteTextView

        autoCompleteBrand?.threshold = 1

        autoCompleteBrand?.setAdapter(adapterSpinner)

        autoCompleteBrand?.setOnItemClickListener { parent, view, position, id ->
            val selected = parent.getItemAtPosition(position) as Speciality
            selectedSpeciality = selected
        }
    }

}