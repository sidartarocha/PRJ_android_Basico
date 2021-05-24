package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.adapter.FrutasAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Fruta



class MainActivity: AppCompatActivity() {
    companion object {
        const val MAIN_ACTIVITY_REQUEST_ID = 0
        const val MAIN_SAVED_INSTANCE_ID = "saved_instance"
    }
    private lateinit var binding : ActivityMainBinding

    private var listFrutas = mutableListOf(
        Fruta("Abacaxi", "Fruta Doce e Acida", 0),
        Fruta("Limao", "Fruta Citrica Azeda", 1),
        Fruta("Laranja", "Fruta Citrica Adocicada", 2)
    )

    private val mFrutasAdapter = FrutasAdapter(this, listFrutas, this::onFrutaClickListiner)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            //val saved = savedInstanceState.getParcelableArrayList(MAIN_SAVED_INSTANCE_ID)
        }

        chamaCadastro()
        setupRecylerview()


        
   }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            Toast.makeText(this, "Result: $requestCode = $(data?.getStringArrayExtra(MAIN_ACTIVITY_REQUEST_ID))", Toast.LENGTH_SHORT).show()
            if (MAIN_ACTIVITY_REQUEST_ID == requestCode) {
                //val vNome = data?.getStringArrayExtra(SecondActivity.SECOND_ACTIVITY_NOME_FRUTA_ID)
                val vNome = data?.getStringArrayExtra(SecondActivity.SECOND_ACTIVITY_NOME_FRUTA_ID)

                Toast.makeText(this, "Result: ${vNome.toString()}", Toast.LENGTH_SHORT).show()
                val vDesc = data?.getStringArrayExtra(SecondActivity.SECOND_ACTIVITY_DESC_FRUTA_ID)
                val vimg = data?.getStringArrayExtra(SecondActivity.SECOND_ACTIVITY_IMG_FRUTA_ID)
                if (isValid(vNome.toString()) and isValid(vDesc.toString()) and isValid(vimg.toString())){
                    //insereRegistro(vNome.toString(),vDesc.toString(),vimg.toString())
                    insereRegistro("morango", "fruta doce", "2")
                    Toast.makeText(this, "Result: sc $vNome", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun insereRegistro(vNome: String, vDesc: String, vimg: String) {
        val lastState = listFrutas[listFrutas.lastIndex]
        listFrutas[listFrutas.lastIndex] = Fruta(vNome, vDesc, vimg.toInt())
        listFrutas.add(lastState)
        mFrutasAdapter.notifyItemChanged(
            listFrutas.lastIndex-1, listFrutas.lastIndex)

    }


    private fun isValid(name: String): Boolean = !name.isNullOrEmpty()
    


    private fun chamaCadastro() {
        binding.fabAdd.setOnClickListener {
            val secondActivity = Intent(this, SecondActivity::class.java)
            startActivityForResult(secondActivity , MAIN_ACTIVITY_REQUEST_ID)
        }
    }

    private fun setupRecylerview() {
        binding.recyclerView.adapter = mFrutasAdapter
        val layoutManager = GridLayoutManager(this, 1)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return  1
            }
        }
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun onFrutaClickListiner(fruta: Fruta) {
        Toast.makeText(this, "Fruta: ${fruta.nome}",
            Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.getParcelableArrayList("listaF", listOf(mFrutasAdapter))
        //putAll(listFrutas)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
       // val saved = savedInstanceState.getFloat(MAIN_SAVED_INSTANCE_ID)


    }


}


