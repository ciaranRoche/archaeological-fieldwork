package org.wit.archaeologicalfieldwork.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.helpers.readImageFromPath
import org.wit.archaeologicalfieldwork.models.data.DataModel

interface HillfortListener {
    fun onHillfortClick(hillfort: DataModel)
}

class HillfortAdapter constructor(private var hillforts: List<DataModel>, private val listener: HillfortListener) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hillfort = hillforts[holder.adapterPosition]
        holder.bind(hillfort, listener)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {

        fun bind(hillfort: DataModel, listener: HillfortListener) {
            itemView.hillfortName.text = hillfort.title
            itemView.description.text = hillfort.description
            if (hillfort.images.isNotEmpty()) itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.images.get(0)))
            itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
        }
    }
}
