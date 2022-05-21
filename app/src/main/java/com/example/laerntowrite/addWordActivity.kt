package com.example.laerntowrite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_word.*
import kotlinx.android.synthetic.main.activity_main.*

class addWordActivity : AppCompatActivity() {
        //sharedpreference
        lateinit var myprefrences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        myprefrences= this.getSharedPreferences("sa",Context.MODE_PRIVATE) ?: return

        var fruits2= mutableListOf<String>()
        var listaset =myprefrences.getStringSet("setlist", mutableSetOf())
        listaset?.forEach {
                a -> fruits2.add(a)
        }
        inserttolist(fruits2)

        buttonSave.setOnClickListener {
            if (palabra.text.isEmpty()){
                Snackbar.make(listawords,"escriba una palabra",Snackbar.LENGTH_SHORT).show()
            }else{
                var getset= myprefrences.getStringSet("setlist", mutableSetOf())
                if(getset!!.add(palabra.text.toString())){
                    val editorpref= myprefrences.edit()
                    editorpref.clear()
                    editorpref.putStringSet("setlist",getset)
                    editorpref.commit()
                    var listaset2 =myprefrences.getStringSet("setlist", mutableSetOf())
                    fruits2.clear()
                    listaset2?.forEach {
                            a -> fruits2.add(a)
                    }
                    palabra.text.clear()
                    inserttolist(fruits2)
                }else{

                    Snackbar.make(listawords,"ya ha ingresado esa palabra",Snackbar.LENGTH_SHORT).show()
                }

            }


        }

        ib_cancelWord.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun inserttolist(fruits2: MutableList<String>) {
        val adp: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, fruits2)
        listawords.adapter = adp
    }
}