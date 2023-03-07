package com.example.medicineremindernew.firebase

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.medicineremindernew.DatabaseHelper
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class PillsManager(val context: Context) {
    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Pills")
    var pills: ArrayList<PillData> = arrayListOf<PillData>()

    var listener: (pills: ArrayList<PillData>) -> Unit = {}

    init {
        val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val pillListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                pills.clear()
                if(snapshot.exists()){
                    for (userSnap in snapshot.children){
                        val userData = userSnap.getValue<PillData>()
                        pills.add(userData!!)
                    }
                }

                System.out.println(pills)
                val dbHelper: DatabaseHelper = DatabaseHelper(context)
                val sharedPreference = context.getSharedPreferences("UserInfo",Context.MODE_PRIVATE)
                val userName = sharedPreference.getString("userName", "userId don't set")

                println("userName: $userName")
                dbHelper.cleanDB()
                pills.forEach {
                    if(it.userId == userName) {
                        dbHelper.insertPill(it)
                    }
                }
                listener(pills)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPill:onCancelled", databaseError.toException())
            }
        }
        dbRef.addValueEventListener(pillListener)
    }

    fun saveData(userId: String,
                 pillName: String,
                 pillValue: String,
                 pillDosage: String,
                 pillDateFrom: String,
                 pillDateTo: String,
                 pillTime1: String,
                 pillTime2: String,
                 pillTime3: String,
                 pillTime4: String,
                 pillTime5: String,
                 pillTime6: String,
                 pillTimesPerDay: String){
        val pillId = dbRef.push().key!!

        val employeeModel = PillData(pillId, userId, pillName, pillValue, pillDosage, pillDateFrom, pillDateTo, pillTime1, pillTime2, pillTime3, pillTime4, pillTime5, pillTime6, pillTimesPerDay)

        dbRef.child(pillId).setValue(employeeModel)
    }
}