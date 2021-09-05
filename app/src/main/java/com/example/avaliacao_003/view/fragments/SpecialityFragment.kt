package com.example.avaliacao_003.view.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avaliacao_003.R
import com.example.avaliacao_003.adapter.SpecialityAdapter
import com.example.avaliacao_003.databinding.SpecialityFragmentBinding
import com.example.avaliacao_003.models.Speciality
import com.example.avaliacao_003.utils.hideKeyboard
import com.example.avaliacao_003.utils.snackBar
import com.example.avaliacao_003.view.activities.DetailsActivity
import com.example.avaliacao_003.view.activities.MainActivity
import com.example.avaliacao_003.view_model.SpecialityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecialityFragment : Fragment(R.layout.speciality_fragment) {

    companion object {
        fun newInstance() = SpecialityFragment()
    }

    private lateinit var binding: SpecialityFragmentBinding
    private lateinit var viewModel: SpecialityViewModel
    private lateinit var recyclerView: RecyclerView
    private val adapter = SpecialityAdapter() { speciality ->
        val intentToDetails = Intent(activity?.applicationContext, DetailsActivity::class.java)
        intentToDetails.putExtra("details", speciality)
        startActivity(intentToDetails)
    }

    private val observerPatients = Observer<List<Speciality>> { specialities ->
        if (specialities.isEmpty()) {
            binding.emptySpecialitiesTextView.visibility = View.VISIBLE
        } else {
            binding.emptySpecialitiesTextView.visibility = View.GONE
            adapter.update(specialities)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SpecialityFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(SpecialityViewModel::class.java)

        recyclerView = binding.specialitiesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.specialities.observe(viewLifecycleOwner, observerPatients)

        setupAddPatient()

        viewModel.getSpecialities()
    }

    private fun setupAddPatient() {
        binding.saveButton.setOnClickListener {
            val nameEditText = binding.nameEditText

            if (nameEditText.text.toString().isNotEmpty()) {
                viewModel.addSpeciality(
                    Speciality(
                        name = nameEditText.text.toString()
                    )
                )

                showSnackbar(R.string.speciality_added, R.color.green)

                nameEditText.text = null
                nameEditText.clearFocus()
            } else {
                showSnackbar(R.string.speciality_no_fields, R.color.red)
            }
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as MainActivity
        val bottomNav = activity.binding.bottomNav
        activity.snackBar(bottomNav, msgId, colorId)
    }
}