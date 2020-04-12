package com.example.corona.tdm_tp04_exo3


import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.corona.tdm_tp04_exo3.Data.Task
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AddFragment : Fragment() {

    var task : Task? = null
    lateinit var mDateListener : DatePickerDialog.OnDateSetListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addField.setOnEditorActionListener { textView, i, keyEvent ->
            var cal = Calendar.getInstance()
            task = Task(addField.text.toString(),cal,null)
            var year = cal.get(Calendar.YEAR)
            var month = cal.get(Calendar.MONTH)
            var day = cal.get(Calendar.DAY_OF_MONTH)
            val picker = DatePickerDialog(
                context!!,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateListener,year,month,day
            )
            picker.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            picker.show()
            false
        }
        mDateListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var cal = Calendar.getInstance()
            cal.set(year,month,day)
            task?.deadline = cal
            task?.done = false

            (context as MainActivity).addTask(task!!)
            addField.text.clear()
        }
    }


}
