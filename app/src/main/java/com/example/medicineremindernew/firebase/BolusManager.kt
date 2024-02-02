package com.example.medicineremindernew.firebase

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.medicineremindernew.BolusHelper
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

    class BolusManager(val context: Context) {
    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("BolusInfo")
    var bolus: ArrayList<BolusData> = arrayListOf<BolusData>()

    var listener: (users: ArrayList<BolusData>) -> Unit = {}

        init {
            val pillListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    bolus.clear()
                    if (snapshot.exists()) {
                        for (userSnap in snapshot.children) {
                            val userData = userSnap.getValue<BolusData>()
                            bolus.add(userData!!)
                        }
                    }

                    System.out.println(bolus)
                    val dbHelper: BolusHelper = BolusHelper(context)
                    val sharedPreference =
                        context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                    val userName = sharedPreference.getString("userName", "userId don't set")

                    println("userName: $userName")
                    dbHelper.cleanDB()
                    bolus.forEach {
                        if (it.userId == userName) {
                            dbHelper.insertBolus(it)
                        }
                    }
                    listener(bolus)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(ContentValues.TAG, "loadPill:onCancelled", databaseError.toException())
                }
            }
            dbRef.addValueEventListener(pillListener)
        }
    fun saveData(userId: String,
                 glukoza: String,
                 XE: String,
                 eat: String,
                 insulin: String,
                 corect: String,
                 result: String,
                 calcEat: String,
                 calcCorect: String,
                 time: String){
        var bolusId = dbRef.push().key!!
        val bolusData = BolusData(bolusId, userId, glukoza, XE, eat, insulin, corect, result, calcEat, calcCorect, time)
        dbRef.child(bolusId).setValue(bolusData)
    }

    }