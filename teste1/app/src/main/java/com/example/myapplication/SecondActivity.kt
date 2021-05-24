package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySecondBinding

class SecondActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    private val COD_IMAGE = 100

    companion object{
        const val SECOND_ACTIVITY_NOME_FRUTA_ID = "namef"
        const val SECOND_ACTIVITY_DESC_FRUTA_ID = "descf"
        const val SECOND_ACTIVITY_IMG_FRUTA_ID = "imgf"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insertFruta()
        abrirImagem()
    }

    fun abrirImagem(){
        binding.btnBuscaImagem.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Selecione Imagem"), COD_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK){
            if (data != null) {

            }
        }
    }

    private fun insertFruta(){
        binding.bntCadastra.setOnClickListener{
            val name = binding.editTextNomeFruta.text.toString()
            val descf = binding.editTextDescFruta.text.toString()
            val imgf = "1"
            if (isValid(name) and isValid(descf)){
                val returnIntent = Intent(this, MainActivity::class.java)
                //val returnIntent = Intent()
                Toast.makeText(this, "name - $name, descf - $descf, imgf - $imgf ", Toast.LENGTH_SHORT).show()
                returnIntent.putExtra(SECOND_ACTIVITY_NOME_FRUTA_ID, name)
                returnIntent.putExtra(SECOND_ACTIVITY_DESC_FRUTA_ID, descf )
                returnIntent.putExtra(SECOND_ACTIVITY_IMG_FRUTA_ID, imgf)
                setResult(Activity.RESULT_OK, returnIntent )
                finish()
            }else{
                Toast.makeText(this, "Todos os item são obrigatorios para preenchimento!", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun isValid(name: String): Boolean = !name.isNullOrEmpty()
}