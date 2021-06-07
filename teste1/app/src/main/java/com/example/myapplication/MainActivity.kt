package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.FrutasAdapter
import com.example.myapplication.callback.FrutaRecyclerViewCallback
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.helper.NoteItemTouchHelperCallBack
import com.example.myapplication.model.Fruta
import java.util.*
import kotlin.collections.ArrayList


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
        Fruta("Abacaxi", "Fruta Doce e Acida", "", R.drawable.abacaxi, 0),
        Fruta("Limao", "Fruta Citrica Azeda","", R.drawable.limao, 1),
        Fruta("Laranja", "Fruta Citrica Adocicada", "", R.drawable.laranja, 2)
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
        verificaListaVazia()
    }

    private fun verificaListaVazia() {
        if (listFrutas.isEmpty()) {
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
        val layoutManager = LinearLayoutManager(this)

        binding.recyclerView.layoutManager = layoutManager

        val itemTouchHelper = ItemTouchHelper(NoteItemTouchHelperCallBack(this,
        object : FrutaRecyclerViewCallback{
            override fun onMoved(initPosition: Int, targetPosition: Int) {
                val frutaOrigem = listFrutas[initPosition]
                val frutaDestino = listFrutas[targetPosition]

                frutaOrigem.ordem = targetPosition + 1
                frutaDestino.ordem = initPosition + 1

                Collections.swap(listFrutas, initPosition, targetPosition)
                mFrutasAdapter.notifyItemMoved(initPosition, targetPosition)
            }

            override fun onSwipe(position: Int, view: RecyclerView.ViewHolder) {
                    dialogApagarFruta(position)
                  //mFrutasAdapter.remove(position)
                   // mFrutasAdapter.notifyDataSetChanged()

              //}
            }
        }))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun dialogApagarFruta(position: Int): Boolean {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.remover_fruta_alert)
        builder.setMessage(R.string.remover_fruta_msg)
        builder.setPositiveButton(R.string.yes) { dialog, which ->
            mFrutasAdapter.remove(position)
            verificaListaVazia()
        }
        builder.setNegativeButton(R.string.no) { dialog, which ->
            mFrutasAdapter.notifyDataSetChanged()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
        return false
    }


}


