package com.example.avaliacao_003.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.avaliacao_003.R
import com.example.avaliacao_003.databinding.ItemScheduleBinding
import com.example.avaliacao_003.models.ScheduleWithMedicAndPatient

class ScheduleAdapter(val onClick: (ScheduleWithMedicAndPatient) -> Unit) : RecyclerView.Adapter<ScheduleViewHolder>() {

    private var schedulesList: MutableList<ScheduleWithMedicAndPatient> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        schedulesList[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int = schedulesList.size

    fun update(newList: List<ScheduleWithMedicAndPatient>) {
        schedulesList = mutableListOf()
        schedulesList.addAll(newList)
        notifyDataSetChanged()
    }
}

class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemScheduleBinding.bind(itemView)

    fun bind(schedule: ScheduleWithMedicAndPatient) {
        schedule.let {
            binding.medicTextView.text = it.medic.name
            binding.patientTextView.text = it.patient.name
        }
    }

}