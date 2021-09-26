package com.example.avaliacao_003.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.avaliacao_003.models.Speciality
import com.example.avaliacao_003.repository.SpecialityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpecialityDetailsViewModel @Inject constructor(
    private val repository: SpecialityRepository
) : ViewModel() {

    private val _speciality = MutableLiveData<Speciality>()
    val speciality: LiveData<Speciality> = _speciality

    private val _didUpdateOrDelete = MutableLiveData(false)
    val didUpdateOrDelete: LiveData<Boolean> = _didUpdateOrDelete

    fun getSpeciality(id: Long) {
        _speciality.value = repository.getSpeciality(id)
    }

    fun updateSpeciality(speciality: Speciality) {
        repository.updateSpeciality(speciality)
        _didUpdateOrDelete.value = true
    }

    fun deleteSpeciality(speciality: Speciality) {
        repository.deleteSpeciality(speciality)
        _didUpdateOrDelete.value = true
    }

}