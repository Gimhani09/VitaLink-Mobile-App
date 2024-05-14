package com.example.vitalink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vitalink.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: PatientDatabaseHelper
    private lateinit var patientAdapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = PatientDatabaseHelper(this)

        patientAdapter = PatientAdapter(db.getAllPatients(), this)

        binding.patientsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.patientsRecyclerView.adapter = patientAdapter

        binding.addButton.setOnClickListener{
            val intent= Intent(this, AddPatientActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        patientAdapter.refreshData(db.getAllPatients())
    }
}