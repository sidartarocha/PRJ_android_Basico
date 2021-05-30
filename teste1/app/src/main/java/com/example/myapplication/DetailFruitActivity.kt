package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityDetailFruitBinding
import com.example.myapplication.model.Fruta

class DetailFruitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFruitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //prencherDetalhes()
        //apagarFruta()
        intent.getParcelableExtra<Fruta>(MainActivity.MAIN_FRUTA_DETAIL)?.let{
            binding.nomeFruta.text = it.nome
            binding.descFruta.text = it.descricao


        }
    }

    private fun apagarFruta() {
        TODO("Not yet implemented")
    }

    private fun prencherDetalhes() {
        intent.getParcelableExtra<Fruta>(MainActivity.MAIN_FRUTA_DETAIL)?.let{
            binding.nomeFruta.text = it.nome
            binding.descFruta.text = it.descricao
            //
        }
    }


}