package com.example.avaliacao_003.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.ItemSpecialityBinding
import com.example.avaliacao_003.models.Speciality

class SpecialityAdapter(val onClick: (Speciality) -> Unit) :
    RecyclerView.Adapter<SpecialityViewHolder>() {

    private var specialitiesList: MutableList<Speciality> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_speciality, parent, false)
        return SpecialityViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialityViewHolder, position: Int) {
        specialitiesList[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int = specialitiesList.size

    fun update(newList: List<Speciality>) {
        specialitiesList = mutableListOf()
        specialitiesList.addAll(newList)
        notifyDataSetChanged()
    }
}

class SpecialityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemSpecialityBinding = ItemSpecialityBinding.bind(itemView)

    fun bind(speciality: Speciality) {
        binding.idTextView.text = speciality.id.toString()
        binding.nameTextView.text = speciality.name
    }

}