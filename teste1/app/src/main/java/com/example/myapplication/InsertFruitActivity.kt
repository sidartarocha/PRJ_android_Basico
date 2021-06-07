package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity.Companion.MAIN_FRUTA_ADD
import com.example.myapplication.MainActivity.Companion.FRUIT_ACTIVITY_ACTION
import com.example.myapplication.MainActivity.Companion.MAIN_FRUTA_LIST
import com.example.myapplication.databinding.ActivityInsertFruitBinding
import com.example.myapplication.model.Fruta
import com.squareup.picasso.Picasso
import java.lang.Boolean.TRUE

class InsertFruitActivity: AppCompatActivity() {
    private lateinit var binding: ActivityInsertFruitBinding

    private val COD_IMAGE = 100

    companion object{
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.add_fruta)
       // insertFruta()
        abrirImagem()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_insert_fruit_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addfruitmenu){
            insertFruta()
        }
        return super.onOptionsItemSelected(item)
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
        //binding.bntCadastra.setOnClickListener{

            val name = binding.editTextNomeFruta.text.toString()
            val descf = binding.editTextDescFruta.text.toString()
            val imgf = imageURL2
            if (isValid(name) and isValid(descf) and imageURL2.isNotEmpty()){
                val returnIntent = Intent(this, MainActivity::class.java)
                val futra_add = Fruta(name, descf, imgf , 0, 5)
                if (validaFrutaCadastrada(futra_add) == TRUE){
                    returnIntent.putExtra(MAIN_FRUTA_ADD, futra_add)
                    returnIntent.putExtra(FRUIT_ACTIVITY_ACTION, "insert")
                    setResult(Activity.RESULT_OK, returnIntent )
                    finish()
                }else{
                    dialogFrutaInsert()
                }


            }else{
                Toast.makeText(this, R.string.msg_itens_obrigatorios, Toast.LENGTH_SHORT).show()
            }

        //}
    }

    private fun validaFrutaCadastrada(futraAdd: Fruta): Boolean {
        val listFruta = intent.getParcelableArrayListExtra<Fruta>(MAIN_FRUTA_LIST)
        if (listFruta != null) {
            for (i in 0..(listFruta.size -1))
            if (listFruta[i].nome.uppercase() == futraAdd.nome.uppercase()){
                return false
            }
        }

        return true
    }

    private fun isValid(name: String): Boolean = !name.isNullOrEmpty()

    private fun dialogFrutaInsert(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.atention)
        builder.setMessage(R.string.fruit_registered)

        builder.setPositiveButton(R.string.ok) { _, _ ->}

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}