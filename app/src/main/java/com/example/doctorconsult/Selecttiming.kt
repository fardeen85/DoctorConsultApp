package com.example.doctorconsult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_selecttiming.*
import java.text.SimpleDateFormat
import android.widget.*
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.annimon.stream.operator.IntArray
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_doctor_info.*
import kotlinx.android.synthetic.main.activity_selecttiming.view.*
import kotlinx.android.synthetic.main.timelist.*
import java.util.*
import kotlin.collections.ArrayList


class Selecttiming : AppCompatActivity() {

    lateinit  var madapter : FirebaseListAdapter<String>
    lateinit var c : Calendar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecttiming)

        var get : Bundle? = intent.extras
        var id : Any? = get!!.get("id")
        var name : Any? = get.get("name")

        //default date set to hiddent textview to easily get date using gettext when needed



        c = Calendar.getInstance()

        var ref =  FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctimings").child(id.toString())



        //hiddent textview invisible
        textView19.visibility = View.INVISIBLE

        //load data to listview of doctors timings
        var array = ArrayList<String>()

        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctimings").child(id.toString()).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {


            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (h in snapshot.children){

                        array.add(h.child("Time1").getValue().toString())
                        array.add(h.child("Time2").getValue().toString())
                        array.add(h.child("Time3").getValue().toString())
                        array.add(h.child("Time4").getValue().toString())
                        array.add(h.child("Time5").getValue().toString())

                    }

                    spinnertimer.adapter = ArrayAdapter<String>(this@Selecttiming,android.R.layout.simple_spinner_dropdown_item,array)
                }



            }


        })
     /*   val options = FirebaseListOptions.Builder<timeloadmodel>()
            .setQuery(ref, timeloadmodel::class.java)
            .setLayout(R.layout.timelist)
            .build()

        val adapter = object : FirebaseListAdapter<timeloadmodel>(options) {
            override fun populateView(v: View, model: timeloadmodel, position: Int) {
                // Bind the Chat to the view

                v.findViewById<TextView>(R.id.timeview).setText(model.Time1)
                v.findViewById<TextView>(R.id.timeview2).setText(model.Time2)
                v.findViewById<TextView>(R.id.timeview3).setText(model.Time3)
                v.findViewById<TextView>(R.id.timeview4).setText(model.Time4)
                v.findViewById<TextView>(R.id.timeview5).setText(model.Time5)
                // ...
            }
        }*/

        /*timelist.adapter = adapter
        adapter.startListening()*/
/*
        val query = FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctimings").child(id.toString())



        val options = FirebaseListOptions.Builder<String>()
            .setQuery(query, String::class.java)
            .build()

        val adapter = object : FirebaseListAdapter<String>(options) {
            override fun populateView(v: View, model: String, position: Int) {
                // Bind the Chat to the view
                // ...

                val timetext = v.findViewById<TextView>(R.id.timeview)
                timetext.setText(model)
            }
        }

        timelist.adapter = adapter
        */



//        timelist.setOnItemClickListener(object : AdapterView.OnItemClickListener{
//            override fun onItemClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                timingview.setText(timelist.getItemAtPosition(position).toString())
//            }
//
//
//
// e
//        })

        //get date/month/year from calender instance c and convert it to required format

        val date = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        //to setcalender to start with this date
        val selectedDate = "$date/${month+1}/$year"
        calendarView.setDate(
            SimpleDateFormat("dd/MM/yyyy").parse(selectedDate).getTime(),
            true,
            true
        )

        textView19.setText(selectedDate)




        ///to get selected date from calenderview

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->


            textView19.setText("$dayOfMonth/${month+1}/$year")

        }


        //to get to next activity and transfer data from here to there

        btngo.setOnClickListener {

            var intent = Intent(this,Appointset::class.java)
            timingview.setText(spinnertimer.selectedItem.toString())
            intent.putExtra("id",id.toString())
            intent.putExtra("time",timingview.text.toString())
            intent.putExtra("date",textView19.text.toString())
            intent.putExtra("name",name.toString())
            startActivity(intent)

        }
    }


}
