package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.MainActivity.Companion.MAIN_FRUTA_DETAIL
import com.example.myapplication.MainActivity.Companion.MAIN_FRUTA_REMOVE
import com.example.myapplication.adapter.FrutasAdapter
import com.example.myapplication.databinding.ActivityDetailFruitBinding
import com.example.myapplication.model.Fruta
import com.squareup.picasso.Picasso

class DetailFruitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFruitBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prencherDetalhes()
        apagarFruta()

    }

    private fun prencherDetalhes() {
        intent.getParcelableExtra<Fruta>(MAIN_FRUTA_DETAIL)?.let{
            binding.nameFruit.text = it.nome
            binding.descFruit.text = it.descricao
            if (it.imagemFruta.length < 2 ) {
                // binding.DetailFruitImageView.setImageDrawable(getDrawable(R.array.frutas[it.nome]))
            }else {
                Picasso.get().load(it.imagemFruta).into(binding.DetailFruitImageView)
            }

        }
    }

    private fun apagarFruta() {
        binding.btnApagarFruta.setOnClickListener{
            intent.getParcelableExtra<Fruta>(MAIN_FRUTA_DETAIL)?.let{
                val returnIntent = Intent(this, MainActivity::class.java)
                returnIntent.putExtra(MAIN_FRUTA_REMOVE,it )
                setResult(Activity.RESULT_OK, returnIntent )
                finish()
            }
        }
    }




}