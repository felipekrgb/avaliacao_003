package com.example.avaliacao_003.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.avaliacao_003.models.Speciality
import com.example.avaliacao_003.repository.SpecialityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpecialityViewModel @Inject constructor(
    private val repository: SpecialityRepository
) : ViewModel() {

    private val _specialities = MutableLiveData<List<Speciality>>()
    val specialities: LiveData<List<Speciality>> = _specialities

    fun getSpecialities() {
        _specialities.value = repository.getSpecialities()
    }

    fun addSpeciality(speciality: Speciality) {
        repository.addSpeciality(speciality)
        getSpecialities()
    }

}