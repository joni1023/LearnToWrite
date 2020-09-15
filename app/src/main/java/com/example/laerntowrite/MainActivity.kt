package com.example.laerntowrite

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.speech.tts.TextToSpeech
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
            if(inputtext.text.toString() == wordSelect){

                Snackbar.make(contraind,"correcto",Snackbar.LENGTH_SHORT).show()
                clearandselect()
            }else {
                Snackbar.make(contraind,"incorrecto",Snackbar.LENGTH_SHORT).show()
                clearandselect()
            }
        }
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
    }


    private fun startList(){
        //inicia lista
        words= arrayOf("marciano", "pedro", "lobo", "arbol", "naranja")

    }

    private fun selectWord(){
        wordSelect=words[(0..5).random()]
    }
}