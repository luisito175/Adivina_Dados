package com.example.tiradados
//imports
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiradados.databinding.ActivityMainBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    private var sum: Int = 0
    private lateinit var handler: Handler //Este es el que nos va a permitir ejecutar el codigo desde el hilo principal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        // Aqui asociamos el handler con el hilo principal
        handler = Handler(Looper.getMainLooper())

        initEvent()
    }

    //Capturamos los botones
    private fun initEvent() {
        bindingMain.txtResultado.visibility = View.INVISIBLE
        bindingMain.imageButton.setOnClickListener {
            bindingMain.txtResultado.visibility = View.VISIBLE
            game()  // Inicia el juego al pulsar el botón
        }
    }

    // Comprueba el número y lanza la tirada con el metodo shedulerun
    private fun game() {
        val texto = bindingMain.numero.text.toString()
        val numero_usuario = texto.toIntOrNull()

        // Comprobamos que el boton sea valido, en caso de que no hacemos un toas y volvemos a debloquear el cubilete
        if (numero_usuario == null || numero_usuario < 3 || numero_usuario > 18) {
            Toast.makeText(this, "Número no válido (3-18)", Toast.LENGTH_SHORT).show()
            bindingMain.imageButton.isEnabled = true
            return
        }

        // Bloqueo el botón mientras se tiran los dados en caso de que el numero esta bien
        bindingMain.imageButton.isEnabled = false
        sheduleRun()
    }

    private fun sheduleRun() {
        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        val msc = 100  //

        //lanzamos los dados de forma que se vea mas estetico
        for (i in 1..5) {
            schedulerExecutor.schedule({
                handler.post { throwDadoInTime() } // Ejecuta en el hilo principal
            }, msc * i.toLong(), TimeUnit.MILLISECONDS)
        }

        //muestra el resultado y desbloquea el botón
        schedulerExecutor.schedule({
            handler.post {
                viewResult() // Muestra la suma final

                //en caso de que el numero sea correcto nos lleva al activity de acierto
                val numero_usuario = bindingMain.numero.text.toString().toIntOrNull()
                if (numero_usuario != null && sum == numero_usuario) {
                    val intent = Intent(this, Acertaste::class.java)
                    startActivity(intent)
                }

                // debloqueamos el cubo
                bindingMain.imageButton.isEnabled = true
            }
        }, msc * 7L, TimeUnit.MILLISECONDS)

        schedulerExecutor.shutdown()
    }
    //Aqui es el metodo donde se generan los numeros para los dados
    private fun throwDadoInTime() {
        val numDados = Array(3) { Random.nextInt(1, 7) }
        val imagViews: Array<ImageView> = arrayOf(
            bindingMain.imagviewDado1,
            bindingMain.imagviewDado2,
            bindingMain.imagviewDado3
        )

        sum = numDados.sum() // Guarda la suma actual

        // y cambiamos las imagenes con el metodo selectview
        for (i in 0..2)
            selectView(imagViews[i], numDados[i])
    }

    // este metodo es el que se usa arriba, este pone segun el numero su imagen
    private fun selectView(imgV: ImageView, v: Int) {
        when (v) {
            1 -> imgV.setImageResource(R.drawable.dado1)
            2 -> imgV.setImageResource(R.drawable.dado2)
            3 -> imgV.setImageResource(R.drawable.dado3)
            4 -> imgV.setImageResource(R.drawable.dado4)
            5 -> imgV.setImageResource(R.drawable.dado5)
            6 -> imgV.setImageResource(R.drawable.dado6)
        }
    }

    // este metodo muestra el resultao
    private fun viewResult() {
        bindingMain.txtResultado.text = sum.toString()
        println(sum)
    }
}
