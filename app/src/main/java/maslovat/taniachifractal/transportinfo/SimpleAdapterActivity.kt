package maslovat.taniachifractal.transportinfo

import android.app.AlertDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import maslovat.taniachifractal.transportinfo.databinding.ActivitySimpleListViewBinding

class SimpleAdapterActivity:AppCompatActivity() {
    private lateinit var fld: ActivitySimpleListViewBinding



    /**Create adapter*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fld = ActivitySimpleListViewBinding.inflate(layoutInflater)
        setContentView(fld.root)
     //   setupListViewSimple()
        setupListViewWithSimpleGeneratedData()
    }
    /**Generate elements*/
    private fun setupListViewWithSimpleGeneratedData()
    {
        val data = (1..100).map{
            mapOf(
                KEY_TITLE to "Транспорт №$it",
                KEY_DESCRIPTION to "Описание №$it"
            )
        }
        val adapter = SimpleAdapter(
            this,
            data,
            R.layout.item_custom,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(R.id.titleTextView,R.id.descriptionTextView)
        )
        fld.listView.adapter = adapter
    }
    /**Setup data to show*/
    private fun setupListViewSimple() {
        val data = listOf(
            mapOf(
                KEY_TITLE to "title 1",
                KEY_DESCRIPTION to "desc 1"
            ),
            mapOf(
                KEY_TITLE to "title 2",
                KEY_DESCRIPTION to "desc 2"
            ),
            mapOf(
                KEY_TITLE to "title 3",
                KEY_DESCRIPTION to "desc 3"
            )
        )
        val Tadapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(android.R.id.text1,android.R.id.text2)
        )
        fld.listView.adapter = Tadapter
        /**Popup about element*/
        fld.listView.onItemClickListener =
            AdapterView.OnItemClickListener{ parent, view, position, id ->
                val selectedItemTitle = data[position][KEY_TITLE]
                val selectedItemDescriptor = data[position][KEY_DESCRIPTION]
                val dialog = AlertDialog.Builder(this)
                    .setTitle(selectedItemTitle)
                    .setMessage(getString(R.string.item_selected_message,selectedItemDescriptor))
                    .setPositiveButton("ok") {dialog,whis->}
                    .create()
                dialog.show()
            }
    }

    /**Data of keys*/
    companion object{
        val KEY_TITLE = "titl"
        val KEY_DESCRIPTION = "desc"
    }
}