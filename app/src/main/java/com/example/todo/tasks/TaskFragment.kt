package com.example.todo.tasks


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.R
import com.example.todo.databinding.FragmentTaskBinding

/**
 * A simple [Fragment] subclass.
 */
class TaskFragment : Fragment() {

    lateinit var viewFragmentBinding:FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_task, container, false)
        viewFragmentBinding= FragmentTaskBinding.inflate(inflater, container, false)

        return viewFragmentBinding.root
    }



}
