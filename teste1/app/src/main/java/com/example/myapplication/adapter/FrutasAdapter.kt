package com.example.myapplication.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemFrutaBinding
import com.example.myapplication.model.Fruta
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.reflect.KFunction1

class FrutasAdapter(//private val context: Context,
    private val frutas: MutableList<Fruta>,
    private val callback: (Fruta, Int)-> Unit): RecyclerView.Adapter<FrutasAdapter.VH>(){

   /* private val ifrutas: TypedArray by lazy {
        context.resources.obtainTypedArray(R.array.frutas)
    }*/

    override fun getItemCount() = frutas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fruta, parent, false)
        val binding = ItemFrutaBinding.bind(view)

        val vh = VH(binding)

        vh.itemView.setOnClickListener{
            val fruta = frutas[vh.adapterPosition]
            callback(fruta, vh.adapterPosition)
        }
        return  vh
    }

    class VH(itemView: ItemFrutaBinding): RecyclerView.ViewHolder(itemView.root) {
         val imgFruta : ImageView = itemView.imageViewFlag
         val txtNome: TextView = itemView.textViewFrutaNome
         val txtDesc: TextView = itemView.textViewFrutaDesc
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val (nome, desc,  imgfrut , imgDraw) = frutas[position]
        if (imgDraw > 0){
           Picasso.get().load(imgDraw).into(holder.imgFruta)
        }else{
            Picasso.get().load(imgfrut).into(holder.imgFruta)
        }

        holder.txtNome.text = nome
        holder.txtDesc.text = desc

    }

    fun swap(initPosition: Int, targePosition: Int){
        Collections.swap(frutas, initPosition, targePosition)
        notifyItemMoved(initPosition, targePosition)
    }

    fun remove(position: Int){
        frutas.removeAt(position)
        notifyItemRemoved(position)
    }
}


