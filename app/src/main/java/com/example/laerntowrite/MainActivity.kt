package com.example.laerntowrite

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var words : Array<String>
    private lateinit var wordSelect:String
    private lateinit var mtts:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniciador()



        button_rep.setOnClickListener {
            mtts.speak(wordSelect, TextToSpeech.QUEUE_FLUSH, null,null)
        }

        buttontext.setOnClickListener {
            buttontext.requestFocus()
            //--lower teclado
//            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputMethodManager.hideSoftInputFromWindow(buttontext.windowToken, 0)
            //---
            if(inputtext.text.toString().toLowerCase().trim() == wordSelect){
                correcto()
            }else {
                incorrecto()
            }
        }

        inputtext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                buttontext.isEnabled=p0!!.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun correcto() {
        mtts.speak("correcto", TextToSpeech.QUEUE_FLUSH, null, null)
        Snackbar.make(contraind,"correcto",Snackbar.LENGTH_SHORT).show()
        clearandselect()
    }
    private fun incorrecto() {
        mtts.speak("incorrecto", TextToSpeech.QUEUE_FLUSH, null, null)
        Snackbar.make(contraind,"incorrecto",Snackbar.LENGTH_SHORT).show()
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
        words= arrayOf("exito", "lapiz", "choclo", "arból", "naranja","calabaza","lechuga","caramelo","loro")

    }

    private fun selectWord(){
        wordSelect=words[(0..5).random()]
    }
}