package com.paigesoftware.myapplication


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DessertAdapter(context: Context) : RecyclerView.Adapter<DessertAdapter.DessertVh>() {
    private val desserts: List<Dessert>?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DessertVh {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_dessert, parent, false)
        return DessertVh(view)
    }

    override fun onBindViewHolder(holder: DessertVh, position: Int) {
        val dessert: Dessert = desserts!![position]
        holder.mName.text = dessert.name
        holder.mDescription.text = dessert.description
        holder.mFirstLetter.text = dessert.firstLetter
    }

    override fun getItemCount(): Int {
        return desserts?.size ?: 0
    }

    class DessertVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mName: TextView = itemView.findViewById(R.id.txt_name)
        val mDescription: TextView = itemView.findViewById(R.id.txt_desc)
        val mFirstLetter: TextView = itemView.findViewById(R.id.txt_firstletter)

    }

    init {
        desserts = Dessert.prepareDesserts(
            context.resources.getStringArray(R.array.dessert_names),
            context.resources.getStringArray(R.array.dessert_descriptions)
        )
    }
}