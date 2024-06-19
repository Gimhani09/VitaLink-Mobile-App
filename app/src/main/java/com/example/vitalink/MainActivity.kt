package com.example.vitalink

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
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

        // Set the layout manager and adapter for the RecyclerView
        binding.patientsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.patientsRecyclerView.adapter = patientAdapter

        // Set OnClickListener for the "Add" button to navigate to the AddPatientActivity
        binding.addButton.setOnClickListener{
            val intent= Intent(this, AddPatientActivity::class.java)
            startActivity(intent)
        }

        // Set up search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    patientAdapter.refreshData(db.searchPatientsByName(it))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    patientAdapter.refreshData(db.searchPatientsByName(it))
                }
                return false
            }
        })
    }

    //refresh data in adapter
    override fun onResume() {
        super.onResume()
        patientAdapter.refreshData(db.getAllPatients())
    }
}
