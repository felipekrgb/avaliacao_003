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
class MedicDetailsViewModel @Inject constructor(
    val repository: MedicRepository
) : ViewModel() {

    private val _medic = MutableLiveData<MedicWithSpeciality>()
    val medic: LiveData<MedicWithSpeciality> = _medic

    private val _didUpdateOrDelete = MutableLiveData(false)
    val didUpdateOrDelete: LiveData<Boolean> = _didUpdateOrDelete

    fun getMedic(id: Long) {
        _medic.value = repository.getMedic(id)
    }

    fun updateMedic(medic: Medic) {
        repository.updateMedic(medic)
        _didUpdateOrDelete.value = true
    }

    fun deleteMedic(medic: Medic) {
        repository.deleteMedic(medic)
        _didUpdateOrDelete.value = true
    }

}