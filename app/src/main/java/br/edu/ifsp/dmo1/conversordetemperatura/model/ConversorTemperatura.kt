package br.edu.ifsp.dmo1.conversordetemperatura.model

interface ConversorTemperatura {

    fun converter(temperature : Double) : Double
    fun converterXparaKelvinOuKelvinParaX(temperature : Double) : Double
    fun getScale() : String
}