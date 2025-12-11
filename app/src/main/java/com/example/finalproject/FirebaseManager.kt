package com.example.finalproject

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseManager {
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")

    fun checkUsernameExists(username: String, callback: (Boolean) -> Unit) {
        usersRef.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot.exists())
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false) 
            }
        })
    }

    fun createUser(username: String, callback: (Boolean) -> Unit) {
        val newUser = UserScore(username, 0)
        usersRef.child(username).setValue(newUser)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    fun updateHighScore(username: String, newScore: Int) {
        usersRef.child(username).runTransaction(object : com.google.firebase.database.Transaction.Handler {
            override fun doTransaction(currentData: com.google.firebase.database.MutableData): com.google.firebase.database.Transaction.Result {
                val p = currentData.getValue(UserScore::class.java)
                if (p == null) {
                    currentData.value = UserScore(username, newScore)
                    return com.google.firebase.database.Transaction.success(currentData)
                }

                if (newScore > p.maxScore) {
                    currentData.value = UserScore(username, newScore)
                }
                
                return com.google.firebase.database.Transaction.success(currentData)
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {
            }
        })
    }

    fun getLeaderboard(limit: Int, callback: (List<UserScore>) -> Unit) {
        usersRef.orderByChild("maxScore").limitToLast(limit)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<UserScore>()
                    for (child in snapshot.children) {
                        val user = child.getValue(UserScore::class.java)
                        if (user != null) {
                            list.add(user)
                        }
                    }
                    callback(list.reversed())
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }
            })
    }
}
