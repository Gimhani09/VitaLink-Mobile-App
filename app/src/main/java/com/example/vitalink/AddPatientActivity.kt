package com.example.vitalink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vitalink.databinding.ActivityAddPatientBinding

class AddPatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPatientBinding
    private lateinit var db: PatientDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = PatientDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val name = binding.nameEditText.text.toString()
            val age = binding.ageEditText.text.toString().toInt()
            val gender = binding.genderEditText.text.toString()
            val contact = binding.contactEditText.text.toString()
            val history = binding.historyEditText.text.toString()

            val patient = Patient(0, name, age, gender, contact, history)
            db.insertPatient(patient)

            finish()
            Toast.makeText(this, "Patient Added", Toast.LENGTH_SHORT).show()
        }
    }
}