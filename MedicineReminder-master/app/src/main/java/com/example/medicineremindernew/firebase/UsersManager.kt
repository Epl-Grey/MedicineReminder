package com.example.medicineremindernew.firebase

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.security.MessageDigest

class UsersManager() {
    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    var users: ArrayList<UserData> = arrayListOf<UserData>()

    var listener: (users: ArrayList<UserData>) -> Unit = {}

    init {
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                users.clear()
                if(snapshot.exists()){
                    for (userSnap in snapshot.children){
                        val userData = userSnap.getValue<UserData>()
                        users.add(userData!!)
                    }
                }
                listener(users)
                System.out.println("Users: ${users.size}")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException())
            }
        }
        dbRef.addValueEventListener(userListener)
    }

    fun saveData(userLogin: String, userPassword: String){
        val empId = dbRef.push().key!!

        val passwordHash: String = MessageDigest.getInstance("SHA-512")
                .digest(userPassword.toByteArray())
                .joinToString(separator = "") {
                    ((it.toInt() and 0xff) + 0x100)
                            .toString(16)
                            .substring(1)
                }

        val employeeModel = UserData(empId, userLogin, passwordHash)

        dbRef.child(empId).setValue(employeeModel)
    }
}