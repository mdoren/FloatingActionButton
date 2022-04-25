package com.example.floatingactionbutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {


    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null
    lateinit var listView: ListView
    lateinit var fab1: FloatingActionButton
    lateinit var fab2: FloatingActionButton
    lateinit var fab3: FloatingActionButton

    lateinit var undoOnClickListener: View.OnClickListener

    //creating variables that handle animations loading
    // and initializing them with animation files
    private val rotateOpen : Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose : Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}
    //used to check if fab menu are opened or closed
    private var closed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        listView = findViewById(R.id.lv1)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listItems
        )
        listView.adapter = adapter
        fab1 = findViewById(R.id.FAB1)
        fab2 = findViewById(R.id.FABAdd)
        fab3 = findViewById(R.id.setting_f)

        fab1.setOnClickListener{
            setAnimation(closed)
            setVisibility(closed)
            closed = !closed
        }

        fab2.setOnClickListener{
            addListItem()
            Snackbar.make(it, "Added an item", Snackbar.LENGTH_LONG)
                .setAction("UNDO", undoOnClickListener).show()
        }

        fab3.setOnClickListener{
            Toast.makeText(this, "This does nothing yet.", LENGTH_SHORT).show()
        }

        undoOnClickListener = View.OnClickListener{
            listItems.removeAt(listItems.size - 1)
            adapter?.notifyDataSetChanged()
            Snackbar.make(it, "Removed an item", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    // function used to set the animation effect
    private fun setAnimation(closed:Boolean) {

        if (!closed) {
            fab2.startAnimation(fromBottom)
            fab3.startAnimation(fromBottom)
            fab1.startAnimation(rotateOpen)
        } else {
            fab2.startAnimation(toBottom)
            fab3.startAnimation(toBottom)
            fab1.startAnimation(rotateClose)
        }
    }

    //function used to set visibility
    private fun setVisibility(closed:Boolean){

        if(!closed) {
            fab2.visibility = View.VISIBLE
            fab3.visibility = View.VISIBLE
        }else{
            fab2.visibility = View.INVISIBLE
            fab3.visibility = View.INVISIBLE
        }
    }

    private fun addListItem() {
        listItems.add("Item 1")
        adapter?.notifyDataSetChanged()
    }
}
