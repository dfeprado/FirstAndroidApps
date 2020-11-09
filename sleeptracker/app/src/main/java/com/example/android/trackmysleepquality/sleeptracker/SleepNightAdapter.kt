package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.SleepNightListItemBinding

class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int): Unit {
        val item: SleepNight = getItem(position);
        holder.bind(item);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor (val binding: SleepNightListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SleepNight): Unit {
            val res = itemView.resources;
            binding.sleepNightDuration.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res);
            binding.sleepNightQuality.text = convertNumericQualityToString(item.sleepQuality, res);
            binding.sleepNightIcon.setImageResource(
                    when (item.sleepQuality) {
                        0 -> R.drawable.ic_sleep_0;
                        1 -> R.drawable.ic_sleep_1;
                        2 -> R.drawable.ic_sleep_2;
                        3 -> R.drawable.ic_sleep_3;
                        4 -> R.drawable.ic_sleep_4;
                        5 -> R.drawable.ic_sleep_5;
                        else -> R.drawable.sleep_active;
                    }
            );
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context);
                val binding = SleepNightListItemBinding.inflate(layoutInflater, parent, false);
                return ViewHolder(binding);
            }
        }
    }
}

class SleepNightDiffCallback: DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId;
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem;
    }

}