package com.example.myapplication.helper

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.FrutasAdapter
import androidx.appcompat.app.AppCompatActivity


class NoteItemTouchHelperCallBack(private val frutasAdapter: FrutasAdapter) : ItemTouchHelper.Callback(){
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
       val swipe = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(drag, swipe)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val initPosition = viewHolder.adapterPosition
        val targetPosition = target.adapterPosition
        frutasAdapter.swap(initPosition,targetPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        /*val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.remover_fruta_alert)
        builder.setMessage(R.string.remover_fruta_msg)
        builder.setPositiveButton(R.string.yes) { _, _ ->
            frutasAdapter.remove(position)
        }

        builder.setNegativeButton(R.string.no) { _, _ ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()*/
        frutasAdapter.remove(position)
    }


}


