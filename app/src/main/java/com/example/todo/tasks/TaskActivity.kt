package com.example.todo.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todo.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main activity for the todoapp. Holds the Navigation Host Fragment and the Drawer, Toolbar, etc.
 */

class TaskActivity : AppCompatActivity() {

    private lateinit var navDrawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDrawerLayout()
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))

        val navController:NavController =findNavController(R.id.NavigationHost)
        val appBarConfiguration:AppBarConfiguration=AppBarConfiguration.Builder(R.id.task_details_fragment,R.id.staticsticsFragment,R.id.add_Edit_Task_Fragment)
                                                        .setDrawerLayout(navDrawer)
                                                         .build()

        setupActionBarWithNavController(navController,appBarConfiguration)


        navigationView.setupWithNavController(navController)


    }

    private fun setupDrawerLayout() {

        navDrawer=findViewById<DrawerLayout>(R.id.drawer_layout)
            .apply{
                setStatusBarBackground(R.color.colorPrimaryDark)
            }
    }


}
