package agg.karun.karun_homeexcercise.ui.home.adapter

import agg.karun.karun_homeexcercise.databinding.ProsConsListItemBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerViewAdapter
 * Create adapter for list item of Pros and Cons of Car
 * Author By : Karun
 **/
class ProsConsListAdapter (private var context: Context, private var list: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rowView = ProsConsListItemBinding.inflate(LayoutInflater.from(context))
        return ProsConsViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ProsConsViewHolder
        holder.apply {
            //hides the view if list item value is null or empty.
            if (list[position].isEmpty()) {
                holder.view.bullet.visibility = View.GONE
                holder.view.value.visibility = View.GONE
            } else {
                holder.view.value.text = list[position]
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    /**
     * View Holder for raw of RecyclerView
     * */
    class ProsConsViewHolder(row: ProsConsListItemBinding) : RecyclerView.ViewHolder(row.root) {
        val view = row
    }

}