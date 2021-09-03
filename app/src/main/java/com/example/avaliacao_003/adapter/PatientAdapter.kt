package com.example.avaliacao_003.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.ItemPatientBinding
import com.example.avaliacao_003.models.Patient

class PatientAdapter(val onClick: (Patient) -> Unit) : RecyclerView.Adapter<PatientViewHolder>() {

    private var patientsList: MutableList<Patient> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        patientsList[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int = patientsList.size

    fun update(newList: List<Patient>) {
        patientsList = mutableListOf()
        patientsList.addAll(newList)
        notifyDataSetChanged()
    }
}

class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemPatientBinding = ItemPatientBinding.bind(itemView)

    fun bind(patient: Patient) {
        binding.idTextView.text = patient.id.toString()
        binding.nameTextView.text = patient.name
    }

}