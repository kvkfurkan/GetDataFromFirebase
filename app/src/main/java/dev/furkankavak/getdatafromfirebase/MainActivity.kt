package dev.furkankavak.getdatafromfirebase

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import dev.furkankavak.getdatafromfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private lateinit var userArrayList : ArrayList<User>
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readData()

        with(binding){
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.setHasFixedSize(true)

            userArrayList = arrayListOf()

            adapter = Adapter(userArrayList)

            recyclerView.adapter = adapter
            btnSave.setOnClickListener {
                if (etName.text.isNotEmpty() && etSurname.text.isNotEmpty() && etAge.text.isNotEmpty()){
                    val user = User(etName.text.toString(),etSurname.text.toString(),etAge.text.toString().toInt())
                    saveData(user)
                }else{
                    Toast.makeText(this@MainActivity, "Please fill all fields", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun saveData(user : User){
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Success", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Fail", "Error adding document", e)
            }
    }

    private fun readData(){
        db.collection("users")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?) {

                    if (error != null){
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }

                    for (dc : DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            userArrayList.add(dc.document.toObject(User::class.java))
                        }
                    }

                    adapter.notifyDataSetChanged()

                }
            })

    }
}