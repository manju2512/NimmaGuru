package com.nimmaguru.app

import android.os.Handler
import android.os.Looper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseRepository {

    private val db = FirebaseDatabase
        .getInstance("https://nimmaguru-7d920-default-rtdb.asia-southeast1.firebasedatabase.app")
        .reference

    // ─── GURUS ───────────────────────────────────────────
    fun saveGuru(guru: Guru, onResult: (Boolean) -> Unit) {

        // Convert skills list to a HashMap Firebase can store
        val skillsMap = HashMap<String, String>()
        guru.skills.forEachIndexed { index, skill ->
            skillsMap["skill_$index"] = skill
        }

        val guruData = hashMapOf<String, Any>(
            "name"      to guru.name,
            "village"   to guru.village,
            "phone"     to guru.phone,
            "freeHours" to guru.freeHours,
            "skills"    to skillsMap
        )

        db.child("gurus").push().setValue(guruData)
            .addOnCompleteListener { task ->
                Handler(Looper.getMainLooper()).post {
                    onResult(task.isSuccessful)
                }
            }
    }

    fun getGurus(onResult: (List<Guru>) -> Unit) {
        db.child("gurus").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val gurus = mutableListOf<Guru>()
                for (child in snapshot.children) {
                    val name      = child.child("name").getValue(String::class.java) ?: ""
                    val village   = child.child("village").getValue(String::class.java) ?: ""
                    val phone     = child.child("phone").getValue(String::class.java) ?: ""
                    val freeHours = child.child("freeHours").getValue(String::class.java) ?: ""
                    val skillsSnap = child.child("skills")
                    val skills = mutableListOf<String>()
                    for (skill in skillsSnap.children) {
                        skill.getValue(String::class.java)?.let { skills.add(it) }
                    }
                    gurus.add(Guru(name, village, phone, freeHours, skills))
                }
                onResult(gurus)
            }
            override fun onCancelled(error: DatabaseError) {
                onResult(emptyList())
            }
        })
    }

    // ─── SESSIONS ────────────────────────────────────────
    fun getSessions(onResult: (List<Session>) -> Unit) {
        db.child("sessions").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val sessions = mutableListOf<Session>()
                for (child in snapshot.children) {
                    val subject  = child.child("subject").getValue(String::class.java) ?: ""
                    val guruName = child.child("guruName").getValue(String::class.java) ?: ""
                    val time     = child.child("time").getValue(String::class.java) ?: ""
                    val venue    = child.child("venue").getValue(String::class.java) ?: ""
                    val date     = child.child("date").getValue(String::class.java) ?: ""
                    sessions.add(Session(subject, guruName, time, venue, date))
                }
                onResult(sessions)
            }
            override fun onCancelled(error: DatabaseError) {
                onResult(emptyList())
            }
        })
    }

    // ─── APPRECIATIONS ───────────────────────────────────
    fun saveAppreciation(appreciation: Appreciation, onResult: (Boolean) -> Unit) {
        val data = hashMapOf(
            "studentName" to appreciation.studentName,
            "guruName"    to appreciation.guruName,
            "message"     to appreciation.message,
            "date"        to appreciation.date
        )
        db.child("appreciations").push().setValue(data)
            .addOnCompleteListener { task ->
                Handler(Looper.getMainLooper()).post {
                    onResult(task.isSuccessful)
                }
            }
    }

    fun getAppreciations(onResult: (List<Appreciation>) -> Unit) {
        db.child("appreciations").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Appreciation>()
                for (child in snapshot.children) {
                    val studentName = child.child("studentName").getValue(String::class.java) ?: ""
                    val guruName    = child.child("guruName").getValue(String::class.java) ?: ""
                    val message     = child.child("message").getValue(String::class.java) ?: ""
                    val date        = child.child("date").getValue(String::class.java) ?: ""
                    list.add(Appreciation(studentName, guruName, message, date))
                }
                onResult(list.reversed())
            }
            override fun onCancelled(error: DatabaseError) {
                onResult(emptyList())
            }
        })
    }
}