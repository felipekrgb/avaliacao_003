package com.example.avaliacao_003.view.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.SpecialityDetailsFragmentBinding
import com.example.avaliacao_003.models.Speciality
import com.example.avaliacao_003.utils.snackBar
import com.example.avaliacao_003.view.activities.DetailsActivity
import com.example.avaliacao_003.view.activities.MainActivity
import com.example.avaliacao_003.view_model.SpecialityDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecialityDetailsFragment : Fragment(R.layout.speciality_details_fragment) {

    companion object {
        fun newInstance(id: Long): SpecialityDetailsFragment {
            return SpecialityDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong("speciality_id", id)
                }
            }
        }
    }

    private lateinit var binding: SpecialityDetailsFragmentBinding
    private lateinit var viewModel: SpecialityDetailsViewModel
    private lateinit var speciality: Speciality

    private val observerSpeciality = Observer<Speciality> { speciality ->
        this.speciality = speciality

        (requireActivity() as DetailsActivity).supportActionBar?.title =
            "${speciality.id} - ${speciality.name}"

        binding.nameEditText.setText(speciality.name)
    }

    private val observerUpdateOrDelete = Observer<Boolean> { didUpdateOrDelete ->
        if (didUpdateOrDelete) {
            val intentToSpecialities = Intent(activity?.applicationContext, MainActivity::class.java)
            intentToSpecialities.putExtra("type", "Speciality")
            startActivity(intentToSpecialities)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SpecialityDetailsFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(SpecialityDetailsViewModel::class.java)

        val specialityId = arguments?.getLong("speciality_id") as Long

        viewModel.speciality.observe(viewLifecycleOwner, observerSpeciality)
        viewModel.didUpdateOrDelete.observe(viewLifecycleOwner, observerUpdateOrDelete)

        setupEditButton()
        setupDeleteButton()

        viewModel.getSpeciality(specialityId)

    }

    private fun setupEditButton() {
        binding.editButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()

            if (name.isNotEmpty() && name != speciality.name) {
                viewModel.updateSpeciality(
                    Speciality(
                        id = speciality.id,
                        name = name,
                    )
                )
            } else {
                showSnackbar(R.string.no_fields_changed, R.color.red)
            }
        }
    }

    private fun setupDeleteButton() {
        binding.deleteButton.setOnClickListener {
            viewModel.deleteSpeciality(speciality)
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as DetailsActivity
        activity.snackBar(binding.nameEditText, msgId, colorId)
    }

}