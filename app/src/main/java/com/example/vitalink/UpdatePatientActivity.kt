package com.example.vitalink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vitalink.databinding.ActivityUpdatePatientBinding

class UpdatePatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdatePatientBinding
    private lateinit var db: PatientDatabaseHelper
    private var patientId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = PatientDatabaseHelper(this)

        patientId = intent.getIntExtra("patient_id", -1)
        if (patientId == -1) {
            finish()
            return
        }

        val patient = db.getPatientByID(patientId)
        binding.updateNameEditText.setText(patient.name)
        binding.updateAgeEditText.setText(patient.age.toString())
        binding.updateGenderEditText.setText(patient.gender)
        binding.updateContactEditText.setText(patient.contact)
        binding.updateHistoryEditText.setText(patient.history)

        // Retrieve updated patient information from EditText fields
        binding.updateSaveButton.setOnClickListener {
            val newName = binding.updateNameEditText.text.toString()
            val newAge = binding.updateAgeEditText.text.toString().toInt()
            val newGender = binding.updateGenderEditText.text.toString()
            val newContact = binding.updateContactEditText.text.toString()
            val newHistory = binding.updateHistoryEditText.text.toString()

            // Create a new Patient object with updated information
            val updatedPatient = Patient(patientId, newName, newAge, newGender, newContact, newHistory)

            // Update the patient information in the database
            db.updatePatient(updatedPatient)

            finish()
            Toast.makeText(this, "Patient Information Updated", Toast.LENGTH_SHORT).show()
        }
    }
}
