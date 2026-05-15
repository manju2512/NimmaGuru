package com.nimmaguru.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GuruProfileActivity : BaseActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etVillage: TextInputEditText
    private lateinit var etFreeHours: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var chipGroupSkills: ChipGroup
    private lateinit var btnSave: Button

    private val dbUrl = "https://nimmaguru-7d920-default-rtdb.asia-southeast1.firebasedatabase.app"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guru_profile)

        etName          = findViewById(R.id.etName)
        etVillage       = findViewById(R.id.etVillage)
        etFreeHours     = findViewById(R.id.etFreeHours)
        etPhone         = findViewById(R.id.etPhone)
        chipGroupSkills = findViewById(R.id.chipGroupSkills)
        btnSave         = findViewById(R.id.btnSave)

        // 1. Check Connection Status
        monitorConnection()

        // 2. Check Auth Status
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.w("GURU_SAVE", "User is NOT authenticated. Check your Firebase Rules!")
        } else {
            Log.d("GURU_SAVE", "Authenticated as: ${user.uid}")
        }

        btnSave.setOnClickListener { saveGuruProfile() }
    }

    private fun monitorConnection() {
        val connectedRef = FirebaseDatabase.getInstance(dbUrl).getReference(".info/connected")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                if (connected) {
                    Log.d("GURU_SAVE", "Connected to Firebase Realtime Database")
                } else {
                    Log.d("GURU_SAVE", "Not connected to Firebase (Offline or connecting...)")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("GURU_SAVE", "Connection listener cancelled: ${error.message}")
            }
        })
    }

    private fun getSelectedSkills(): HashMap<String, String> {
        val skills = HashMap<String, String>()
        var index = 0
        for (i in 0 until chipGroupSkills.childCount) {
            val view = chipGroupSkills.getChildAt(i)
            if (view is Chip && view.isChecked) {
                skills["skill_$index"] = view.text.toString()
                index++
            }
        }
        return skills
    }

    private fun saveGuruProfile() {
        val name      = etName.text.toString().trim()
        val village   = etVillage.text.toString().trim()
        val freeHours = etFreeHours.text.toString().trim()
        val phone     = etPhone.text.toString().trim()
        val skills    = getSelectedSkills()

        if (name.isEmpty() || village.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show()
            return
        }
        if (skills.isEmpty()) {
            Toast.makeText(this, "Select at least one skill", Toast.LENGTH_SHORT).show()
            return
        }

        btnSave.isEnabled = false
        btnSave.text = "Saving..."

        val db = FirebaseDatabase.getInstance(dbUrl).reference

        // ✅ Use explicit String values only — no Long, no nested HashMap
        val skillsList = skills.values.joinToString(",")

        val guruData = hashMapOf<String, String>(
            "name"      to name,
            "village"   to village,
            "phone"     to phone,
            "freeHours" to freeHours,
            "skills"    to skillsList
        )

        Log.d("GURU_SAVE", "Saving guru: $guruData")

        db.child("gurus").push().setValue(guruData)
            .addOnCompleteListener { task ->
                btnSave.isEnabled = true
                btnSave.text = "Save Profile"

                if (task.isSuccessful) {
                    Log.d("GURU_SAVE", "Saved successfully!")
                    Toast.makeText(this, "✅ Profile Saved!", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    val error = task.exception?.message ?: "Unknown error"
                    Log.e("GURU_SAVE", "Failed: $error")
                    Toast.makeText(this, "❌ $error", Toast.LENGTH_LONG).show()
                }
            }
    }
}
