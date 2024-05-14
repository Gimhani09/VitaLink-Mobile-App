package com.example.vitalink

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PatientAdapter(private var patients: List<Patient>, context: Context) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    private val db: PatientDatabaseHelper = PatientDatabaseHelper(context)

    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val ageTextView: TextView = itemView.findViewById(R.id.ageTextView)
        val genderTextView: TextView = itemView.findViewById(R.id.genderTextView)
        val contactTextView: TextView = itemView.findViewById(R.id.contactTextView)
        val historyTextView: TextView = itemView.findViewById(R.id.historyTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.patient_item, parent, false)
        return PatientViewHolder(view)
    }

    override fun getItemCount(): Int = patients.size

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.nameTextView.text = patient.name
        holder.ageTextView.text = "Age: ${patient.age}"
        holder.genderTextView.text = "Gender: ${patient.gender}"
        holder.contactTextView.text = "Contact: ${patient.contact}"
        holder.historyTextView.text = "History: ${patient.history}"

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdatePatientActivity::class.java).apply {
                putExtra("patient_id", patient.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deletePatient(patient.id)
            refreshData(db.getAllPatients())
            Toast.makeText(holder.itemView.context, "Patient Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newPatients: List<Patient>) {
        patients = newPatients
        notifyDataSetChanged()
    }
}
