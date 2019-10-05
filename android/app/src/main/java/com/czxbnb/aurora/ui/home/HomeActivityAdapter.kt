package com.czxbnb.aurora.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.czxbnb.aurora.R
import com.czxbnb.aurora.databinding.ItemHomeActivityBinding
import com.czxbnb.aurora.model.activity.Activity

class HomeActivityAdapter : RecyclerView.Adapter<HomeActivityAdapter.ViewHolder>() {
    private lateinit var activityList: List<Activity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHomeActivityBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_home_activity, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activityList[position])
    }

    override fun getItemCount(): Int {
        return if (::activityList.isInitialized) activityList.size else 0
    }

    fun updateActivityList(activityList: List<Activity>) {
        this.activityList = activityList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemHomeActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = HomeActivityViewModel()

        fun bind(activity: Activity) {
            viewModel.bind(activity)
            binding.viewModel = viewModel
        }
    }
}