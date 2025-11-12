package com.example.tiradados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.tiradados.databinding.ActivityMainBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMain : ActivityMainBinding
    private var sum : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        initEvent()
    }

    private fun initEvent() {
        bindingMain.txtResultado.visibility = View.INVISIBLE
        bindingMain.imageButton.setOnClickListener{
            bindingMain.txtResultado.visibility = View.VISIBLE
            game()
        }
    }

    private fun game() {
        val texto = bindingMain.numero.text.toString()
        val numero_usuario = texto.toIntOrNull()

        if (numero_usuario == null || numero_usuario < 3 || numero_usuario > 18) {
            Toast.makeText(this, "Número no válido (3-18)", Toast.LENGTH_SHORT).show()
            bindingMain.imageButton.isEnabled = true
            return
        }

        bindingMain.imageButton.isEnabled = false
        sheduleRun()
    }

    private fun sheduleRun() {
        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        val msc = 100
        for (i in 1..5){
            schedulerExecutor.schedule(
                {
                    throwDadoInTime()
                },
                msc * i.toLong(), TimeUnit.MILLISECONDS)
        }

        schedulerExecutor.schedule({
            viewResult()
            val numero_usuario = bindingMain.numero.text.toString().toIntOrNull()
            if (numero_usuario != null && sum == numero_usuario) {
                val intent = Intent(this, Acertaste::class.java)
                startActivity(intent)
            }
            bindingMain.imageButton.isEnabled = true
        }, msc * 7.toLong(), TimeUnit.MILLISECONDS)

        schedulerExecutor.shutdown()
    }

    private fun throwDadoInTime() {
        val numDados = Array(3){Random.nextInt(1, 6)}
        val imagViews : Array<ImageView> = arrayOf(
            bindingMain.imagviewDado1,
            bindingMain.imagviewDado2,
            bindingMain.imagviewDado3
        )

        sum = numDados.sum()
        for (i in 0..2)
            selectView(imagViews[i], numDados[i])
    }

    private fun selectView(imgV: ImageView, v: Int) {
        when (v){
            1 -> imgV.setImageResource(R.drawable.dado1)
            2 -> imgV.setImageResource(R.drawable.dado2)
            3 -> imgV.setImageResource(R.drawable.dado3)
            4 -> imgV.setImageResource(R.drawable.dado4)
            5 -> imgV.setImageResource(R.drawable.dado5)
            6 -> imgV.setImageResource(R.drawable.dado6)
        }
    }

    private fun viewResult() {
        bindingMain.txtResultado.text = sum.toString()
        println(sum)
    }
}
