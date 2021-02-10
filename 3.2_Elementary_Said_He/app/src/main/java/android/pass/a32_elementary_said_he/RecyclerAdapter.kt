package android.pass.a32_elementary_said_he

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycleview_item_row.view.*

class RecyclerAdapter(inData: List<BookData>) : RecyclerView.Adapter<BookViewHolder>() {
    private val bookList : List<BookData> = inData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellRow = layoutInflater.inflate(R.layout.recycleview_item_row, parent, false)
        return BookViewHolder(cellRow)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList.get(position)

        holder?.view?.textView_row_title.setText(book.title)
        holder?.view?.textView_row_author.setText(book.author)
        holder?.view?.imageView_book_thumbnail.setImageResource(book.imageId)
    }
}

class BookViewHolder(val view : View) : RecyclerView.ViewHolder(view){
}
