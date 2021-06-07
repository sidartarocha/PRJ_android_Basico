package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.MainActivity.Companion.MAIN_FRUTA_DETAIL
import com.example.myapplication.MainActivity.Companion.MAIN_FRUTA_REMOVE
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
            if (it.ImagDraw > 0) {
                // binding.DetailFruitImageView.setImageDrawable(getDrawable(R.array.frutas[it.nome]))
                Picasso.get().load(it.ImagDraw).into(binding.DetailFruitImageView)
            }else {
                Picasso.get().load(it.imagemFruta).into(binding.DetailFruitImageView)
            }

        }
    }

    private fun apagarFruta() {
        binding.btnApagarFruta.setOnClickListener{
            intent.getParcelableExtra<Fruta>(MAIN_FRUTA_DETAIL)?.let{
                val returnIntent = Intent(this, MainActivity::class.java)
                dialogApagarFruta(returnIntent, it)
            }
        }
    }

    private fun dialogApagarFruta(returnIntent: Intent, fruta: Fruta) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.remover_fruta_alert)
        builder.setMessage(R.string.remover_fruta_msg)
        builder.setPositiveButton(R.string.yes) { _, _ ->
            returnIntent.putExtra(MAIN_FRUTA_REMOVE,fruta )
            setResult(Activity.RESULT_OK, returnIntent )
            finish()
        }
        builder.setNegativeButton(R.string.no) { _, _ ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()


    }



}