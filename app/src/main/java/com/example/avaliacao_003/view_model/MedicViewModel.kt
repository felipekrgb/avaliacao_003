package com.example.avaliacao_003.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.avaliacao_003.models.Medic
import com.example.avaliacao_003.models.MedicWithSpeciality
import com.example.avaliacao_003.repository.MedicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicViewModel @Inject constructor(
    private val repository: MedicRepository
) : ViewModel() {

    private val _medics = MutableLiveData<List<MedicWithSpeciality>>()
    val medics: LiveData<List<MedicWithSpeciality>> = _medics

    fun getMedics() {
        _medics.value = repository.getMedics()
    }

    fun addMedic(medic: Medic) {
        repository.addMedic(medic)
        getMedics()
    }

}