package com.example.laerntowrite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var words : ArrayList<String>
    private lateinit var wordSelect:String
    private lateinit var mtts:TextToSpeech


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animation_view.visibility=View.INVISIBLE
        animation_view2.visibility=View.INVISIBLE
        iniciador()

        textadd.setOnClickListener {
            val intent = Intent(this@MainActivity, addWordActivity::class.java)
            startActivity(intent)
        }
        button_rep.setOnClickListener {
            mtts.setSpeechRate(1.0F)
            mtts.speak(wordSelect, TextToSpeech.QUEUE_FLUSH, null, null)
        }
        rep_low.setOnClickListener {
            mtts.setSpeechRate(0.5F)
            mtts.speak(wordSelect, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        buttontext.setOnClickListener {
            buttontext.requestFocus()
            //--lower teclado
//            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputMethodManager.hideSoftInputFromWindow(buttontext.windowToken, 0)
            //---
            if(inputtext.text.toString().toLowerCase().trim() == wordSelect){
                animation_view.visibility=View.VISIBLE
                animation_view.playAnimation()
                correcto()
                Handler().postDelayed(Runnable {
                        animation_view.visibility=View.INVISIBLE
                        }, animation_view.duration)
            }else {

                animation_view2.visibility=View.VISIBLE
                animation_view2.playAnimation()
                textResult.visibility=View.VISIBLE
                textResult.text= "Es: "+wordSelect
                incorrecto()
                Handler().postDelayed(Runnable {
                    animation_view2.visibility=View.INVISIBLE
                    textResult.visibility=View.INVISIBLE
                }, animation_view2.duration)
            }
        }

        //retry
        retry.setOnClickListener {
            clearandselect()
        }

        inputtext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                buttontext.isEnabled = p0!!.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun onResume() {
        super.onResume()
        val mypref= this.getSharedPreferences("sa",Context.MODE_PRIVATE)
        var listaset =mypref.getStringSet("setlist", mutableSetOf("banana","manzana"))
        listaset?.forEach {
                a -> words.add(a)
        }
    }
    



    private fun correcto() {
        mtts.speak("correcto", TextToSpeech.QUEUE_FLUSH, null, null)
        Snackbar.make(contraind, "correcto", Snackbar.LENGTH_SHORT).show()
        clearandselect()
    }
    private fun incorrecto() {
        mtts.speak("incorrecto", TextToSpeech.QUEUE_FLUSH, null, null)
        Snackbar.make(contraind, "incorrecto", Snackbar.LENGTH_SHORT).show()
        clearandselect()
    }

    private fun clearandselect() {
        inputtext.text.clear()
        selectWord()
    }

    private fun iniciador() {
        //inicio de TextToSpeech
        mtts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                //locale segun ISO
                mtts.language = Locale("es", "ES")
            }
        })
        startList()
        selectWord()
        buttontext.isEnabled=false
    }


    private fun startList(){
        //inicia lista
        words= arrayListOf()
        val mypref= this.getSharedPreferences("sa",Context.MODE_PRIVATE)
        var listaset =mypref.getStringSet("setlist", mutableSetOf("banana","manzana"))
        listaset?.forEach {
                a -> words.add(a)
        }





    }

    private fun selectWord(){
        wordSelect=words.random()
    }
}