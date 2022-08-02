package agg.karun.karun_homeexcercise.ui.home.adapter

import agg.karun.karun_homeexcercise.R
import agg.karun.karun_homeexcercise.databinding.CarListChildItemBinding
import agg.karun.karun_homeexcercise.databinding.CarListParentItemBinding
import agg.karun.karun_homeexcercise.ui.data.model.CarDetails
import agg.karun.karun_homeexcercise.ui.data.model.CarProsConsModel
import agg.karun.karun_homeexcercise.util.CHILD
import agg.karun.karun_homeexcercise.util.CONS
import agg.karun.karun_homeexcercise.util.PARENT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerViewAdapter
 * Create adapter for list item of car with expendable details
 * Author By : Karun
 **/

class CarListAdapter(private var list: MutableList<CarDetails>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == PARENT) {
            val rowView =
                CarListParentItemBinding.inflate(LayoutInflater.from(parent.context))
            ParentViewHolder(rowView)
        } else {
            val rowView =
                CarListChildItemBinding.inflate(LayoutInflater.from(parent.context))
            ChildViewHolder(rowView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        if (data.viewType == PARENT) {   //Binding parent view of car list
            holder as ParentViewHolder
            holder.apply {
                if (data.image == -1) { //Show default view if no record found
                    parentView.carModel.text = data.model
                    parentView.carImage.visibility = View.GONE
                    parentView.carPrice.visibility = View.GONE
                    parentView.rating.visibility = View.GONE
                } else {
                    parentView.carImage.visibility = View.VISIBLE
                    parentView.carPrice.visibility = View.VISIBLE
                    parentView.rating.visibility = View.VISIBLE
                    parentView.carImage.setImageResource(data.image)
                    parentView.carModel.text = "${data.make} ${data.model}"
                    data.price?.let {
                        parentView.carPrice.text = "${R.string.price} ${it / 1000} k"
                    }
                    parentView.rating.rating = (data.ratings ?: 0).toFloat()

                    parentView.parent.setOnClickListener {
                        if (data.prosCons.isNotEmpty()) expandOrCollapseParentItem(
                            data,
                            position,
                            parentView.separator
                        )
                    }
                }
            }
        } else {                        //Binding child (expandable) view of car list
            holder as ChildViewHolder
            holder.apply {
                childView.label.text = data.prosCons.first().type
                childView.prosConsList.layoutManager = LinearLayoutManager(itemView.context)
                childView.prosConsList.adapter =
                    ProsConsListAdapter(itemView.context, data.prosCons[0].value!!)
                if (data.prosCons.first().type == CONS || (data.prosCons.first().type == CONS && data.prosCons.size == 1)) childView.separator.visibility =
                    View.VISIBLE
                else childView.separator.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = list[position].viewType

    /**
     * @Method
     * @param boarding, position and view
     * view is used to hide and show parent separator
     * */
    private fun expandOrCollapseParentItem(boarding: CarDetails, position: Int, view: View) {

        if (boarding.expanded) {
            collapseParentItem(position)
            view.visibility = View.VISIBLE
        } else {
            expandParentItem(position)
            view.visibility = View.GONE
        }
    }

    /**
     * @Method
     * @param position
     * This method is used to expand Parent Item to show child details
     * */
    private fun expandParentItem(position: Int) {
        val currentBoardingRow = list[position]
        val prosCons = currentBoardingRow.prosCons
        currentBoardingRow.expanded = true
        var nextPosition = position
        if (currentBoardingRow.viewType == PARENT) {

            prosCons.forEach { prosCon ->
                if (!prosCon.value.isNullOrEmpty()) {
                    val parentModel = CarDetails("")
                    parentModel.viewType = CHILD
                    val subList: ArrayList<CarProsConsModel> = ArrayList()
                    subList.add(prosCon)
                    parentModel.prosCons = subList
                    list.add(++nextPosition, parentModel)
                }
            }
            notifyDataSetChanged()
        }
    }

    /**
     * @Method
     * @param position
     * This method is used to collapse Parent Item to hide child details
     * */
    private fun collapseParentItem(position: Int) {
        val currentBoardingRow = list[position]
        val prosCons = currentBoardingRow.prosCons
        list[position].expanded = false
        if (list[position].viewType == PARENT) {
            prosCons.forEach { _ ->
                list.removeAt(position + 1)
            }
            notifyDataSetChanged()
        }
    }

    /**
     * View Holder for parent view of RecyclerView
     * */
    class ParentViewHolder(row: CarListParentItemBinding) : RecyclerView.ViewHolder(row.root) {
        val parentView = row
    }

    /**
     * View Holder for child view of RecyclerView
     * */
    class ChildViewHolder(row: CarListChildItemBinding) : RecyclerView.ViewHolder(row.root) {
        val childView = row
    }
}