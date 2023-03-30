package com.example.medicineremindernew.firebase

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class DiabetesManager {
    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("DiabetesInfo")
    var diabetes: ArrayList<DiabetesData> = arrayListOf<DiabetesData>()

    var listener: (users: ArrayList<DiabetesData>) -> Unit = {}

    init {
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                diabetes.clear()
                if(snapshot.exists()){
                    for (userSnap in snapshot.children){
                        val diabetesData = userSnap.getValue<DiabetesData>()
                        diabetes.add(diabetesData!!)
                    }
                }
                listener(diabetes)
                System.out.println("Users: ${diabetes.size}")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadUser:onCancelled", databaseError.toException())
            }
        }
        dbRef.addValueEventListener(userListener)
    }

    fun saveData(userId: String, diabetesMorning: String, diabetesAfternoon: String, diabetesEvening: String, diabetesFChI: String){
        val diabetesData = DiabetesData(userId, diabetesMorning, diabetesAfternoon, diabetesEvening, diabetesFChI)

        dbRef.child(userId).setValue(diabetesData)
    }
}