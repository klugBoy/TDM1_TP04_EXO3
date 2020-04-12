package com.example.corona.tdm_tp04_exo3

import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.corona.tdm_tp04_exo3.Data.Task
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import java.util.*
import kotlin.Exception
import kotlin.collections.ArrayList





class MainActivity : AppCompatActivity() {

    var listTasks = ArrayList<Task>()
    var listTasksNotDone = ArrayList<String>()
    var list_fragment = ListFragment()
    var add_fragment = AddFragment()
    lateinit var supportFrag : FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        supportFragmentManager.beginTransaction().replace(R.id.addBar,add_fragment).commit()

        init()


        if (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE ) {
            // on a large screen device ...


            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape
                today.setOnClickListener {
                    todayFilter()
                }
                week.setOnClickListener {
                    weekFilter()
                }
                all.setOnClickListener {
                    allFilter()
                }
            } else {
                // In portrait
                val spinner: Spinner = findViewById(R.id.spinner)
                ArrayAdapter.createFromResource(
                    this,
                    R.array.options_array,
                    android.R.layout.simple_spinner_item
                ).also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    spinner.adapter = adapter
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            when (spinner.selectedItem) {
                                "Today" -> {
                                    todayFilter()
                                }
                                "This week" -> {
                                    weekFilter()
                                }
                                "All" -> {
                                    allFilter()
                                }
                            }

                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                    }


                }


            }
        }


    }

    fun addTask(task: Task){
        var found = false
        for (i in listTasks){
            if(task.name == i.name ) {
                    found=true
                    break

            }

        }
        if(!found) listTasks.add(task)

        update()

    }

    fun init(){

        for (i in listTasks){
            if(i.done == false && isToday(i.deadline!!,Calendar.getInstance()) ){
                listTasksNotDone.add(i.name!!)
            }
        }


        try {
            Toast.makeText(applicationContext, listTasksNotDone[0], Toast.LENGTH_SHORT).show()
        } catch (e:Exception){

        }

        var b = Bundle()
        b.putStringArrayList("tasks",listTasksNotDone)
        list_fragment.arguments = b
        supportFrag = supportFragmentManager.beginTransaction().replace(R.id.listTasks,list_fragment)
        supportFrag.commit()
    }
    fun update(){
        listTasksNotDone.clear()
        for (i in listTasks){
            if(i.done == false  && isToday(i.deadline!!,Calendar.getInstance())){
                listTasksNotDone.add(i.name!!)
            }
        }

        list_fragment.update()

    }

    fun deleteTask(task: String){
        for (i in listTasks){
            if(task == i.name) {
                listTasks.remove(i)
                break
            }

        }
        update()
    }



    private fun isCurrentWeekDateSelect(yourSelectedDate: Calendar): Boolean {
        val ddd = yourSelectedDate.time
        val c = Calendar.getInstance()
        c.firstDayOfWeek = Calendar.MONDAY

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)

        val monday = c.time
        val nextMonday = Date(monday.time + 7 * 24 * 60 * 60 * 1000)

        return ddd.after(monday) && ddd.before(nextMonday)
    }

    fun isToday(day :Calendar,day2:Calendar) : Boolean{
        return day.get(Calendar.DAY_OF_MONTH)==day2.get(Calendar.DAY_OF_MONTH) &&
                day.get(Calendar.MONTH)==day2.get(Calendar.MONTH) &&
                day.get(Calendar.YEAR)==day2.get(Calendar.YEAR)

    }

    fun todayFilter(){
        listTasksNotDone.clear()
        for (i in listTasks) {
            if (i.done == false && isToday(i.deadline!!,Calendar.getInstance())) {
                listTasksNotDone.add(i.name!!)
            }
        }

        list_fragment.update()
    }
    fun weekFilter(){
        listTasksNotDone.clear()
        for (i in listTasks) {
            if (i.done == false && isCurrentWeekDateSelect(i.deadline!!)) {
                listTasksNotDone.add(i.name!!)
            }
        }

        list_fragment.update()
    }

    fun allFilter(){
        listTasksNotDone.clear()
        for (i in listTasks) {
            if (i.done == false) {
                listTasksNotDone.add(i.name!!)
            }
        }

        list_fragment.update()
    }

}
