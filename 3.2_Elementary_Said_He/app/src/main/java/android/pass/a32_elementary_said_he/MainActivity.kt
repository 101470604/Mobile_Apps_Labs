package android.pass.a32_elementary_said_he

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var my_book_list : List<BookData> = mutableListOf(
            BookData("Vendetta in Death","J.D. Robb", R.drawable.vendetta_in_death),
            BookData("Call Sign Chaos","Jim Mattis, Bing West", R.drawable.call_sign_chaos),
            BookData("Where the Crawdads Sing","Delia Owens", R.drawable.where_the_crawdads_sing),
            BookData("Dog Man: For Whom the Ball Rolls","Dav Pilkey", R.drawable.dog_man),
            BookData("The Oracle","Jonathan Cahn", R.drawable.the_oracle),
            BookData("Dark Illusion","Christine Feehan", R.drawable.dark_illusion),
            BookData("How To","Randall Munroe", R.drawable.how_to),
            BookData("The Baby-Sitters Club Graphix: Boy-Crazy Stacey","M. Martin", R.drawable.baby_sitters_club),
            BookData("Educated","Tara Westover", R.drawable.educated),
            BookData("The Goldfinch","Donna Tartt", R.drawable.the_goldfinch)
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(my_book_list)

    }
}
