package com.example.laerntowrite

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val words : Array<String> = startList()
    private var wordSelect= words[(0..5).random()]
    private val soundapool= SoundPool(1, AudioManager.STREAM_MUSIC, 1)
    private var sonido : Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var soundId: Int = getIdRaw(wordSelect)

        sonido=soundapool.load(baseContext, soundId, 1)
        var repB = findViewById<ImageButton>(R.id.button_rep)
        repB.setOnClickListener {
            soundapool.play(sonido, 1.0F, 1.0F, 1, 0, 1.0F)
        }

        val miText= findViewById<TextView>(R.id.texto)
        miText.text=wordSelect
        val boton =findViewById<Button>(R.id.buttontext)
        boton.setOnClickListener {
            val nevotext=findViewById<EditText>(R.id.inputtext)
            if(nevotext.text.toString() == miText.text.toString()){
                Toast.makeText(this, "correcto", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun startList():Array<String>{
        //inicia lista
        return arrayOf("marciano", "pedro", "lobo", "arbol", "naranja")

    }

    private fun getIdRaw(name: String?): Int {
        //retorna id generado por R.java de la carpeta Raw,pasando el nombre
        return this.resources.getIdentifier(name, "raw", this.packageName)
    }
}