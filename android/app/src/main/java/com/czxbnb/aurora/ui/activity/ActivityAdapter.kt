package com.czxbnb.aurora.ui.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.czxbnb.aurora.R
import com.czxbnb.aurora.databinding.ItemActivityBinding
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.ui.activityDetail.ActivityDetailActivity


class ActivityAdapter : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {
    private lateinit var activityList: List<Activity>
    private lateinit var binding: ItemActivityBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_activity, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activityList[position])

        // Set onclick listener
        binding.cvItem.setOnClickListener {
            val intent = Intent(context, ActivityDetailActivity::class.java)
            intent.putExtra("activity", activityList[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if (::activityList.isInitialized) activityList.size else 0
    }

    fun updateActivityList(activityList: List<Activity>) {
        this.activityList = activityList
        notifyDataSetChanged()
    }


    class ViewHolder(private val binding: ItemActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ActivityItemViewModel()

        fun bind(activity: Activity) {
            viewModel.bind(activity)
            binding.viewModel = viewModel
        }
    }
}