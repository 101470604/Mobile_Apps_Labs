package au.edu.swin.sdmd.sunrisekotlin

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import au.edu.swin.sdmd.sunrisekotlin.R.color.selected
import kotlinx.android.synthetic.main.times_row.view.*
import java.util.*

class RecyclerAdapter(private val riseandSetTimes : List<RiseAndSetTimes>,
                      private val itemListener : OnItemClickedListener) : RecyclerView.Adapter<RecyclerAdapter.TimesViewHolder>() {

    var selectedPosition : Int = -1
    set (value){
        if (value <= itemCount){
            field = value
        }
        notifyDataSetChanged()
    }// Default none selected

    fun getTimes(): List<RiseAndSetTimes> { return riseandSetTimes }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimesViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellRow = layoutInflater.inflate(R.layout.times_row, parent, false)
        return TimesViewHolder(cellRow, itemListener)
    }

    override fun getItemCount(): Int {
        return riseandSetTimes.size
    }

    override fun onBindViewHolder(holder: TimesViewHolder, position: Int) {
        val times: RiseAndSetTimes = riseandSetTimes.get(position)
        holder.view.label_date.setText(times.date)
        holder.view.value_sunrise_time.setText(times.sunRise)
        holder.view.value_sunset_time.setText(times.sunSet)

        if (position == selectedPosition)
        {
            holder.view.setBackgroundColor(Color.parseColor("#595959"))
        }
        else
        {
            holder.view.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    class TimesViewHolder(val view: View,
                          val listener : OnItemClickedListener
                        ) : RecyclerView.ViewHolder(view), View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            listener.onItemClick(adapterPosition)
        }
    }
}
