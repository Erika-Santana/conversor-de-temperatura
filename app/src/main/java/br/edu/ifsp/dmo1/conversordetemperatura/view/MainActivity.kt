package br.edu.ifsp.dmo1.conversordetemperatura.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo1.conversordetemperatura.R
import br.edu.ifsp.dmo1.conversordetemperatura.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.conversordetemperatura.model.CelsiusStrategy
import br.edu.ifsp.dmo1.conversordetemperatura.model.FahrenheitStrategy
import br.edu.ifsp.dmo1.conversordetemperatura.model.KelvinStrategy
import kotlin.NumberFormatException
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

   private lateinit var binding : ActivityMainBinding
   private lateinit var fahrenheit : FahrenheitStrategy
   private lateinit var celsius : CelsiusStrategy
   private lateinit var kelvinStrategy: KelvinStrategy
   private val scales = arrayOf("Fº", "Cº", "Kº")
   private var scalaPrimeiro : String = ""
   private var scalaSegundo : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        celsius = CelsiusStrategy()
        fahrenheit = FahrenheitStrategy()
        kelvinStrategy = KelvinStrategy()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter : ArrayAdapter<String>  = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, scales
        )

        binding.spinner1.adapter = adapter
        binding.spinner2.adapter = adapter

        binding.spinner2.onItemSelectedListener = this
        binding.spinner1.onItemSelectedListener = this

        binding.botaoConverter.setOnClickListener(this)

    }

    //verifica através do adapter pois ele pega as informações como posição e conteúdo
    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
             when(adapter?.id){
                 binding.spinner1.id  -> {
                    scalaPrimeiro = scales[position]
                 }
                 binding.spinner2.id -> {
                     scalaSegundo = scales[position]
                 }
             }

    }

    override fun onNothingSelected(adapter: AdapterView<*>?) {

            when (adapter?.id) {
                binding.spinner1.id -> {
                    Toast.makeText(this, getString(R.string.msg_error_insere_escala_inicial), Toast.LENGTH_SHORT).show()
                    scalaPrimeiro = scales[0]
                }
                binding.spinner2.id -> {
                    Toast.makeText(this, getString(R.string.msg_error_insere_escala_final), Toast.LENGTH_SHORT).show()
                    scalaSegundo = scales[0]
                }
            }
    }



    fun readTemperature(): Double{
        try {
            val valorDigitado = binding.valorUsuario.text.toString().toDouble()
            return valorDigitado
        }catch (excecao : NumberFormatException){
            throw NumberFormatException("Error input")
        }

    }

    fun selecionaEstrategia(spinner1 : String, spinner2 : String){

        val valorDigitado = readTemperature()
        var valorConvertido : Double = 0.0
        var scale : String = ""

        when{
            spinner1.equals("Fº") && spinner2.equals("Cº") -> {
                valorConvertido = celsius.converter(valorDigitado)
                scale = celsius.getScale()
            }
            spinner1.equals("Cº") && spinner2.equals("Fº") -> {
                valorConvertido = fahrenheit.converter(valorDigitado)
                scale = fahrenheit.getScale()
            }
            spinner1.equals("Fº") && spinner2.equals("Kº") -> {
                valorConvertido = fahrenheit.converterXparaKelvinOuKelvinParaX(valorDigitado)
                scale = kelvinStrategy.getScale()
            }
            spinner1.equals("Cº") && spinner2.equals("Kº") -> {
                valorConvertido = celsius.converterXparaKelvinOuKelvinParaX(valorDigitado)
                scale = kelvinStrategy.getScale()
            }
            spinner1.equals("Kº") && spinner2.equals("Cº") -> {
                valorConvertido = kelvinStrategy.converter(valorDigitado)
                scale = celsius.getScale()
            }
            spinner1.equals("Kº") && spinner2.equals("Fº") -> {
                valorConvertido = kelvinStrategy.converterXparaKelvinOuKelvinParaX(valorDigitado)
                scale = fahrenheit.getScale()
            }else -> {
                Toast.makeText(this, getString(R.string.conversao_nao_valida), Toast.LENGTH_SHORT).show()
            }
        }

        showResult(valorConvertido, scale)

    }

    fun showResult(valorConvertido : Double, scale : String){
        try {
            binding.resultado.text = String.format("%.2f %s", valorConvertido, scale)
        }catch (excecao : Exception){
            Toast.makeText(this, getString(R.string.msg_error), Toast.LENGTH_SHORT).show()
            Log.e("APP_DMO", excecao.stackTraceToString())

        }
    }

    override fun onClick(button: View) {
        try {
            selecionaEstrategia(scalaPrimeiro, scalaSegundo)
        } catch (ex: NumberFormatException) {
            Toast.makeText(this, getString(R.string.insira_valor_valido), Toast.LENGTH_SHORT).show()
        }

    }
}