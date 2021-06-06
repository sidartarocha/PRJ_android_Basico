package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.myapplication.adapter.FrutasAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.helper.NoteItemTouchHelperCallBack
import com.example.myapplication.model.Fruta
import java.text.FieldPosition


class MainActivity: AppCompatActivity() {
    companion object {
        const val MAIN_ACTIVITY_REQUEST_ID_INSERT = 0
        const val MAIN_ACTIVITY_REQUEST_ID_REMOVE = 1
        const val MAIN_FRUTA_POSITION = "MAIN_FRUTA_POSITION"
        const val MAIN_FRUTA_LIST = "MAIN_FRUIT_LIST"
        const val MAIN_FRUTA_DETAIL = "MAIN_FRUTA_DETAIL"
        const val MAIN_FRUTA_ADD = "MAIN_FRUIT_LIST"
        const val MAIN_FRUTA_REMOVE = "MAIN_FRUTA_REMOVE"
        const val FRUIT_ACTIVITY_ACTION = "FRUIT_ACTIVITY_ACTION"
    }
    private lateinit var binding : ActivityMainBinding

    private var listFrutas = mutableListOf(
        Fruta("Abacaxi", "Fruta Doce e Acida", "", R.drawable.abacaxi),
        Fruta("Limao", "Fruta Citrica Azeda","", R.drawable.limao),
        Fruta("Laranja", "Fruta Citrica Adocicada", "", R.drawable.laranja)
    )



    //private val mFrutasAdapter = FrutasAdapter(this, listFrutas, this::onFrutaClickListiner)
    private val mFrutasAdapter by lazy { FrutasAdapter(listFrutas, this::onFrutaClickListiner) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
       //  val  listFrutas = savedInstanceState.getParcelableArrayList<Fruta>(MAIN_FRUTA_LIST)?.toMutableList()?:listFrutas
        }
        chamaCadastro()
        setupRecylerview()
   }
    //chamada da tela de detalhes
    private fun onFrutaClickListiner(fruta: Fruta, position: Int) {
        val fruitDetail = Intent(this, DetailFruitActivity::class.java)
        fruitDetail.putExtra("MAIN_FRUTA_DETAIL", fruta)
        fruitDetail.putExtra("MAIN_FRUTA_POSITION", position)
        startActivityForResult(fruitDetail , MAIN_ACTIVITY_REQUEST_ID_REMOVE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(MAIN_FRUTA_LIST, ArrayList(listFrutas))
        //putAll(listFrutas)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // val saved = savedInstanceState.getFloat(MAIN_SAVED_INSTANCE_ID)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MAIN_ACTIVITY_REQUEST_ID_INSERT) {
                data?.getParcelableExtra<Fruta>(MAIN_FRUTA_ADD)?.let {
                    insereRegistro(it)
                    Toast.makeText(this, "Fruta:  ${it.nome} adicionada", Toast.LENGTH_SHORT).show()
                }
            }
            if (requestCode == MAIN_ACTIVITY_REQUEST_ID_REMOVE) {
                data?.getParcelableExtra<Fruta>(MAIN_FRUTA_REMOVE)?.let {
                    removerRegistro(it)
                    Toast.makeText(this, "Fruta:  ${it.nome} adicionada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun removerRegistro(vFruta: Fruta) {
        val positionF = listFrutas.indexOf(vFruta)
        listFrutas.remove(vFruta)
        mFrutasAdapter.notifyItemRemoved(positionF)
        if (listFrutas.isEmpty()){
            binding.recyclerView.visibility = INVISIBLE
            binding.cardView.visibility = VISIBLE
        }
    }


    private fun insereRegistro(vFruta: Fruta) {
        if (listFrutas.isEmpty()){
            listFrutas.add(vFruta)
            mFrutasAdapter.notifyItemChanged(1)
            binding.recyclerView.visibility = VISIBLE
            binding.cardView.visibility = INVISIBLE
        }else {
            val lastState = listFrutas[listFrutas.lastIndex]
            listFrutas[listFrutas.lastIndex] = vFruta
            listFrutas.add(lastState)
            mFrutasAdapter.notifyItemChanged(
                listFrutas.lastIndex - 1, listFrutas.lastIndex
            )
        }
    }

   private fun isValid(name: String): Boolean = !name.isNullOrEmpty()

    private fun chamaCadastro() {
        binding.fabAdd.setOnClickListener {
            val secondActivity = Intent(this, InsertFruitActivity::class.java)
            secondActivity.putExtra(MAIN_FRUTA_LIST, ArrayList(listFrutas))
            startActivityForResult(secondActivity , MAIN_ACTIVITY_REQUEST_ID_INSERT)
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

        val itemTouchHelper = ItemTouchHelper(NoteItemTouchHelperCallBack(mFrutasAdapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }




}


