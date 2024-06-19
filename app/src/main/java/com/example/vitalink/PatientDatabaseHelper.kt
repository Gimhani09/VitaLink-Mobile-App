package com.example.vitalink

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PatientDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "patients.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "patients"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_AGE = "age"
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_CONTACT = "contact"
        private const val COLUMN_HISTORY = "history"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_AGE INTEGER, $COLUMN_GENDER TEXT, $COLUMN_CONTACT TEXT, $COLUMN_HISTORY TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertPatient(patient: Patient) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, patient.name)
            put(COLUMN_AGE, patient.age)
            put(COLUMN_GENDER, patient.gender)
            put(COLUMN_CONTACT, patient.contact)
            put(COLUMN_HISTORY, patient.history)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllPatients(): List<Patient> {
        val patientList = mutableListOf<Patient>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
            val gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER))
            val contact = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT))
            val history = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HISTORY))

            val patient = Patient(id, name, age, gender, contact, history)
            patientList.add(patient)
        }
        cursor.close()
        db.close()
        return patientList
    }

    fun updatePatient(patient: Patient) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, patient.name)
            put(COLUMN_AGE, patient.age)
            put(COLUMN_GENDER, patient.gender)
            put(COLUMN_CONTACT, patient.contact)
            put(COLUMN_HISTORY, patient.history)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(patient.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getPatientByID(patientId: Int): Patient {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $patientId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
        val gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER))
        val contact = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT))
        val history = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HISTORY))

        cursor.close()
        db.close()
        return Patient(id, name, age, gender, contact, history)
    }

    fun deletePatient(patientId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(patientId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun searchPatientsByName(name: String): List<Patient> {
        val patientList = mutableListOf<Patient>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME LIKE ?"
        val cursor = db.rawQuery(query, arrayOf("%$name%"))

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val patientName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
            val gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER))
            val contact = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT))
            val history = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HISTORY))

            val patient = Patient(id, patientName, age, gender, contact, history)
            patientList.add(patient)
        }
        cursor.close()
        db.close()
        return patientList
    }

}
