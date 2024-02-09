package com.example.realmdbapproach

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdbapproach.schema.Course

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.VH>() {
    private var listData = emptyList<Course>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListChange(listData: List<Course>) {
        this.listData = listData
        notifyDataSetChanged()
    }
    class VH(
        view: View
    ) : RecyclerView.ViewHolder(view.rootView) {
        fun bind(data: Course) {
            itemView.findViewById<TextView>(R.id.textView).text = data.name
            itemView.findViewById<TextView>(R.id.linkR).text = data.teacher?.name ?: "Unknown"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return VH(inflater.inflate(R.layout.item, parent, false))
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(listData[position])
    }
}