package com.example.avaliacao_003.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.avaliacao_003.R
import com.example.avaliacao_003.view_model.SpecialityDetailsViewModel

class SpecialityDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = SpecialityDetailsFragment()
    }

    private lateinit var viewModel: SpecialityDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.speciality_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SpecialityDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}