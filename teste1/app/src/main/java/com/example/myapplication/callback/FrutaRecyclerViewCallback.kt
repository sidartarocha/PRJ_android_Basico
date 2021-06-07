package com.example.myapplication.callback
import androidx.recyclerview.widget.RecyclerView

interface FrutaRecyclerViewCallback {

    abstract fun onMoved(initPosition: Int, targetPosition1: Int)

    abstract fun onSwipe(position: Int , view : RecyclerView.ViewHolder)
}