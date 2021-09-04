package com.example.avaliacao_003.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.ItemMedicBinding
import com.example.avaliacao_003.models.MedicWithSpeciality

class MedicAdapter : RecyclerView.Adapter<MedicViewHolder>() {

    private var medicsList: MutableList<MedicWithSpeciality> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medic, parent, false)
        return MedicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicViewHolder, position: Int) {
        medicsList[position].apply {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int = medicsList.size

    fun update(newList: List<MedicWithSpeciality>) {
        medicsList = mutableListOf()
        medicsList.addAll(newList)
        notifyDataSetChanged()
    }

}

class MedicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binding: ItemMedicBinding = ItemMedicBinding.bind(itemView)

    fun bind(medicWithSpeciality: MedicWithSpeciality) {
        binding.idTextView.text = medicWithSpeciality.medic?.id.toString()
        binding.medicTextView.text = medicWithSpeciality.medic?.name
        binding.specialityTextView.text = medicWithSpeciality.speciality?.name
    }

}