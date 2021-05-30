package com.example.myapplication.adapter

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemFrutaBinding
import com.example.myapplication.model.Fruta
import com.squareup.picasso.Picasso

class FrutasAdapter(private val context: Context,
                    private val frutas: List<Fruta>,
                    private val callback: (Fruta) -> Unit): RecyclerView.Adapter<FrutasAdapter.VH>(){

    private val ifrutas: TypedArray by lazy {
        context.resources.obtainTypedArray(R.array.frutas)
    }

    override fun getItemCount() = frutas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fruta, parent, false)
        val binding = ItemFrutaBinding.bind(view)
        val vh = VH(binding)
        vh.itemView.setOnClickListener{
            val fruta = frutas[vh.adapterPosition]
            callback(fruta)
        }
        return  vh
    }

     class VH(itemView: ItemFrutaBinding): RecyclerView.ViewHolder(itemView.root) {
         val imgFruta : ImageView = itemView.imageViewFlag
         val txtNome: TextView = itemView.textViewFrutaNome
         val txtDesc: TextView = itemView.textViewFrutaDesc
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val (nome, desc,  imgfrut) = frutas[position]
        if (imgfrut.length < 2){
            holder.imgFruta.setImageDrawable(ifrutas.getDrawable(imgfrut.toInt()))
        }else{
            Picasso.get().load(imgfrut).into(holder.imgFruta)
        }

        holder.txtNome.text = nome
        holder.txtDesc.text = desc

    }

}


