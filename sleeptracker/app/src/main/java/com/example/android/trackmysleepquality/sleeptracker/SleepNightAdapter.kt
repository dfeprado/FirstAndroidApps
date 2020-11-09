package com.example.android.trackmysleepquality.sleeptracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int): Unit {
        val item: SleepNight = getItem(position);
        holder.bind(item);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor (itemView: View): RecyclerView.ViewHolder(itemView) {
        val sleepQualityIcon: ImageView = itemView.findViewById(R.id.sleep_night_icon);
        val sleepQualityText: TextView = itemView.findViewById(R.id.sleep_night_quality);
        val sleepNightDuration: TextView = itemView.findViewById(R.id.sleep_night_duration);

        fun bind(item: SleepNight): Unit {
            val res = itemView.resources;
            sleepNightDuration.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res);
            sleepQualityText.text = convertNumericQualityToString(item.sleepQuality, res);
            sleepQualityIcon.setImageResource(
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
                val view = layoutInflater.inflate(R.layout.sleep_night_list_item, parent, false);
                return ViewHolder(view);
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