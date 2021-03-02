package au.edu.swin.movieratingskotlin

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.listrow.view.*

class recyleAdapter(private val movies : List<Movie>, private val global_util : Utils) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellRow = layoutInflater.inflate(R.layout.listrow, parent , false)
        return viewHolder(cellRow)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currMovie = movies[position]
        holder.itemView.row_icon.setImageBitmap(global_util.getMovieIcon(currMovie.name, currMovie.rating))
        holder.itemView.row_label.text = currMovie.name
        holder.itemView.row_subtext.text = currMovie.votes + " votes"
        Log.w("ViewHolder", "Creating row view at position " + position + " movie " + currMovie.name)
    }

    class viewHolder (val view: View) : RecyclerView.ViewHolder(view)
}