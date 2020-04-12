package com.example.corona.tdm_tp04_exo3


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ListFragment : Fragment() {

    lateinit var tasks : ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_list, container, false)
        // Inflate the layout for this fragment
        var b = arguments
        tasks = b?.getStringArrayList("tasks") as ArrayList<String>
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adptee = ArrayAdapter(
            context!!,
            android.R.layout.simple_list_item_1,
            tasks

        )
        mListView.adapter = adptee
        mListView.setOnItemClickListener { adapterView, view, i, l ->
            (context as MainActivity).deleteTask(i)

        }
    }

    fun update(){
        getFragmentManager()?.beginTransaction()?.detach(this)?.attach(this)?.commit()
    }





}
