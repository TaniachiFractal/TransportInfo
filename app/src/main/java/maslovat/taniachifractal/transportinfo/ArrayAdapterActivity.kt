package maslovat.taniachifractal.transportinfo

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import maslovat.taniachifractal.transportinfo.databinding.ActivityListViewBinding
import maslovat.taniachifractal.transportinfo.databinding.DialogAddTransportBinding
import java.util.ArrayList
import java.util.Random
import java.util.UUID

/**Class for Array adapter logic*/
class ArrayAdapterActivity : AppCompatActivity()
{
    private lateinit var fld: ActivityListViewBinding
    private lateinit var Tadapter:ArrayAdapter<TransportData>
    private lateinit var selectedTransport: TransportData
    private lateinit var transportDataMutableList: MutableList<TransportData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fld=ActivityListViewBinding.inflate(layoutInflater)
        setContentView(fld.root)
        setupListWithArrayAdapter()
        fld.addButton.setOnClickListener{onAddPressed()}
        fld.deleteButton.setOnClickListener{deleteTransport(selectedTransport)}
        fld.infoButton.setOnClickListener{btInfo_Click()}
    }

    /**Setup Tadapter*/
    private fun setupListWithArrayAdapter()
    {
        /**Data for the list*/
        transportDataMutableList = mutableListOf(
            TransportData(UUID.randomUUID(),"Lada",true,1,1),
            TransportData(UUID.randomUUID(),"Honda",false,2,2),
            TransportData(UUID.randomUUID(),"Ford",true,3,3),
            TransportData(UUID.randomUUID(),"Kia",true,4,4),
            TransportData(UUID.randomUUID(),"Toyota",false,5,5)
        )
        /**Create adapter with default layout */
        Tadapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            transportDataMutableList
        )
        /**Make Tadapter the adapter of the list*/
        fld.listView.adapter=Tadapter
        fld.listView.setOnItemClickListener{parent,view,position,id->
            Tadapter.getItem(position)?.let{
                showFullInfo(it)
                selectedTransport = it
            }
        }
        selectedTransport = transportDataMutableList[transportDataMutableList.count()-1]
    }

    /**Show full transport info on click*/
    private fun showFullInfo(transport: TransportData)
    {
        fld.fullInfoTB.text = transport.FullInfo()
    }

    private fun btInfo_Click()
    {
        var rnd = Random()
        fld.fullInfoTB.setBackgroundColor(Color.argb(255,
            rnd.nextInt(100)+155,
            rnd.nextInt(100)+155,
            rnd.nextInt(100)+155))
        fld.fullInfoTB.text = selectedTransport.FullInfo() +"\n "+
                (transportDataMutableList.indexOf(selectedTransport)+1)+
                " транспорт из "+
                transportDataMutableList.count()
    }

    /**Create dialog for adding data*/
    private fun onAddPressed()
    {
        val dialogBinding = DialogAddTransportBinding.inflate(layoutInflater)
        val dialog=AlertDialog.Builder(this)
            .setTitle("Добавить транспорт")
            .setView(dialogBinding.root)
            .setPositiveButton("Добавить"){d,which->
                var name = dialogBinding.transportNameEditText.text.toString()
                var isMotorcycle = dialogBinding.isMotorcycle.isActivated
                var axis = dialogBinding.axisCount.text.toString()
                var capacity = dialogBinding.capacity.text.toString()

                if (name.isBlank()) name = "undefined"
                if (axis.isBlank()) axis = "1"
                if (capacity.isBlank()) capacity = "1"

                createTransport(name,isMotorcycle,axis.toInt(),capacity.toInt())

            }
            .create()
        dialog.show()
    }
    /**Create a new element*/
    private fun createTransport(name:String,motorcycle:Boolean,axis_:Int,capacity_:Int)
    {
        val transport = TransportData(
            id = UUID.randomUUID(),
            markName = name,
            isMotorcycle = motorcycle,
            axis = axis_,
            capacity = capacity_
        )

        Tadapter.add(transport)
       // transportDataMutableList.add(transport)

        selectedTransport = transportDataMutableList[transportDataMutableList.count()-1]
    }
    /**Delete selected element*/
    private fun deleteTransport(transport: TransportData)
    {
        if (transportDataMutableList.count()==1) return

        val listener = DialogInterface.OnClickListener{dialog, which ->
            if (which==DialogInterface.BUTTON_POSITIVE){
                Tadapter.remove(transport)
                transportDataMutableList.remove(transport)
                selectedTransport = transportDataMutableList[transportDataMutableList.count()-1]
                fld.fullInfoTB.text = "Выберите транспорт"
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setTitle("Удалить транспорт")
            .setMessage("Точно удалить транспорт $transport?")
            .setPositiveButton("Удалить",listener)
            .setNegativeButton("Отмена",listener)
            .create()
        dialog.show()
    }
}