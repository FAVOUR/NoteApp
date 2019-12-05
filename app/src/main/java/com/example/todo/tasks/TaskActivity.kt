package com.example.todo.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.todo.R

/**
 * Main activity for the todoapp. Holds the Navigation Host Fragment and the Drawer, Toolbar, etc.
 */

class TaskActivity : AppCompatActivity() {

    private lateinit var navDrawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDrawerLayout()

    }

    private fun setupDrawerLayout() {

        navDrawer=findViewById<DrawerLayout>(R.id.drawer_layout)
            .apply{
                setStatusBarBackground(R.color.colorPrimaryDark)
            }
    }


}
