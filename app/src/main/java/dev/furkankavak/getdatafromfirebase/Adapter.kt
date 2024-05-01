package dev.furkankavak.getdatafromfirebase


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.furkankavak.getdatafromfirebase.databinding.ListItemBinding

class Adapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val user : User = userList[position]
        with(holder.binding){
            tvName.text = user.name
            tvSurname.text = user.surname
            tvAge.text = user.age.toString()
        }
    }

    override fun getItemCount(): Int {

        return userList.size
    }



    public class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}