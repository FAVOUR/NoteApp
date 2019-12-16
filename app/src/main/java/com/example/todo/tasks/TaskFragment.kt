package com.example.todo.tasks


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.example.todo.R
import com.example.todo.databinding.FragmentTaskBinding

/**
 * A simple [Fragment] subclass.
 */
class TaskFragment : Fragment() {

    lateinit var viewFragmentBinding:FragmentTaskBinding

    private val  viewModel by viewModels<TaskViewModel> ()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_task, container, false)
        viewFragmentBinding= FragmentTaskBinding.inflate(inflater, container, false).apply {

          viewmodel=viewModel
        }

        return viewFragmentBinding.root


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
        when(item.itemId){

            R.id.filter ->
                showFiteringpopupMenu()
            R.id.clear->
                viewModel.clearCompletedTask()

            R.id.refresh->viewModel.loadTasks(true)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.task_fragment_menu,menu)
    }



    fun showFiteringpopupMenu(){

        val view = activity?.findViewById<View>(R.id.filter)   ?: return
        PopupMenu(requireContext(),view).run {

            menuInflater.inflate(R.menu.filter_tasks,menu)


            setOnMenuItemClickListener {

                viewModel.startFiltering(

                    when(it.itemId){

                        R.id.active ->TaskFilterType.ACTIVE_TASK
                        R.id.completed ->TaskFilterType.COMPLETED_TASK
                            else->
                                TaskFilterType.ALLTASK
                    }
                )
                true
            }

            show()


        }


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }



}
