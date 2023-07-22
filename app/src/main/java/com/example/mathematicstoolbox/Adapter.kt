package com.example.mathematicstoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface OnItemClickListener{
    fun onItemClick(position: Int, Item : content)
}
class Adapter(val contents : MutableList<content>) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    var listener : OnItemClickListener? = null

    fun SetOnClickListener(listener : OnItemClickListener){
        this.listener = listener
    }
    inner class  ViewHolder(view : View) : RecyclerView.ViewHolder(view),View.OnClickListener{
        val name: TextView = view.findViewById(R.id.textView2)
        val img: ImageView = view.findViewById(R.id.imageView)
        init{
            itemView .setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onItemClick(adapterPosition,contents[adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return contents.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            name.text = contents[position].name
            img.setImageResource(contents[position].img)
        }
    }

}