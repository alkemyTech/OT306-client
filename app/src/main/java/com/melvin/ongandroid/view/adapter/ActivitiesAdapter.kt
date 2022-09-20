package com.melvin.ongandroid.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.activities.Activity

class ActivitiesAdapter(private val activitiesList: List<Activity>):
    RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_list, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
       // return activitiesList.size
        return 5
    }

    inner class ActivityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}