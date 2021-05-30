package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity.Companion.MAIN_FRUTA_ADD
import com.example.myapplication.MainActivity.Companion.FRUIT_ACTIVITY_ACTION
import com.example.myapplication.databinding.ActivityInsertFruitBinding
import com.example.myapplication.model.Fruta
import com.squareup.picasso.Picasso

class InsertFruitActivity: AppCompatActivity() {
    private lateinit var binding: ActivityInsertFruitBinding

    private val COD_IMAGE = 100

    companion object{
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insertFruta()
        abrirImagem()
    }

    fun abrirImagem(){
        binding.btnBuscaImagem.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Selecione Imagem"), COD_IMAGE)

        }
    }
    var imageURL2 = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK){
            //val imageURL2 = data?.getStringExtra("imageURL")
            imageURL2 = data?.data.toString()
            val imageURL = R.drawable.abacaxi
            Picasso.get().load(imageURL2).into(binding.imageViewFruit)
        }
    }

    private fun insertFruta(){
        binding.bntCadastra.setOnClickListener{
            val name = binding.editTextNomeFruta.text.toString()
            val descf = binding.editTextDescFruta.text.toString()
            val imgf = imageURL2
            if (isValid(name) and isValid(descf)){
                val returnIntent = Intent(this, MainActivity::class.java)
                val futra_add = Fruta(name, descf, imgf )
                returnIntent.putExtra(MAIN_FRUTA_ADD, futra_add)
                returnIntent.putExtra(FRUIT_ACTIVITY_ACTION, "insert")
                setResult(Activity.RESULT_OK, returnIntent )
                finish()
            }else{
                Toast.makeText(this, "Todos os item são obrigatorios para preenchimento!", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun isValid(name: String): Boolean = !name.isNullOrEmpty()
}