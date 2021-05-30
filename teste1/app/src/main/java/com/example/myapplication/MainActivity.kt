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
        const val MAIN_FRUTA_LIST = "MAIN_FRUIT_LIST"
        const val MAIN_FRUTA_DETAIL = "MAIN_FRUTA_DETAIL"
        const val MAIN_FRUTA_ADD = "MAIN_FRUIT_LIST"
    }
    private lateinit var binding : ActivityMainBinding

    private var listFrutas = mutableListOf(
        Fruta("Abacaxi", "Fruta Doce e Acida", "0"),
        Fruta("Limao", "Fruta Citrica Azeda", "1"),
        Fruta("Laranja", "Fruta Citrica Adocicada", "2")
    )

    private val mFrutasAdapter = FrutasAdapter(this, listFrutas, this::onFrutaClickListiner)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
          val  frutas = savedInstanceState.getParcelableArrayList<Fruta>(MAIN_FRUTA_LIST)?.toMutableList()?:listFrutas
        }
        chamaCadastro()
        setupRecylerview()
   }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            Toast.makeText(this, "Result: $requestCode = $(data?.getStringArrayExtra(MAIN_ACTIVITY_REQUEST_ID))", Toast.LENGTH_SHORT).show()
            if (MAIN_ACTIVITY_REQUEST_ID == requestCode) {
                data?.getParcelableExtra<Fruta>(MAIN_FRUTA_ADD)?.let {
                    insereRegistro(it)
                    Toast.makeText(this, "Fruta:  ${it.nome} adicionada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun insereRegistro(vFruta: Fruta) {
        val lastState = listFrutas[listFrutas.lastIndex]
        listFrutas[listFrutas.lastIndex] = vFruta
        listFrutas.add(lastState)
        mFrutasAdapter.notifyItemChanged(
            listFrutas.lastIndex-1, listFrutas.lastIndex)

    }

   private fun isValid(name: String): Boolean = !name.isNullOrEmpty()

    private fun chamaCadastro() {
        binding.fabAdd.setOnClickListener {
            val secondActivity = Intent(this, InsertFruitActivity::class.java)
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
        val fruitDetail = Intent(this, DetailFruitActivity::class.java)
        fruitDetail.putExtra("MAIN_FRUTA_DETAIL", fruta)
        startActivity(fruitDetail)

        /*Toast.makeText(this, "Fruta: ${fruta.nome}",
            Toast.LENGTH_SHORT).show()*/
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


