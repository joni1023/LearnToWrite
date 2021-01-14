package com.example.laerntowrite

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_add_word.*

class addWordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //sharedpreference
        val myprefrences= this.getSharedPreferences("sa",Context.MODE_PRIVATE) ?: return
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        var fruits2= mutableListOf<String>()
        var listaset =myprefrences.getStringSet("setlist", mutableSetOf())
        listaset?.forEach {
                a -> fruits2.add(a)
        }
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
           inputMethodManager.hideSoftInputFromWindow(palabra.windowToken, 0)


        val adp:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,fruits2)
        listawords.adapter=adp

        buttonSave.setOnClickListener {

            var getset= myprefrences.getStringSet("setlist", mutableSetOf())
            getset?.add(palabra.text.toString())
            /*editorpref.remove("setlist")
            editorpref.commit()*/
            val editorpref= myprefrences.edit()
            editorpref.clear()
            editorpref.putStringSet("setlist",getset)
            editorpref.commit()
            var listaset2 =myprefrences.getStringSet("setlist", mutableSetOf())
            fruits2.clear()
            listaset2?.forEach {
                a -> fruits2.add(a)
            }

            val adp:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,fruits2)
            listawords.adapter=adp

        }
    }
}