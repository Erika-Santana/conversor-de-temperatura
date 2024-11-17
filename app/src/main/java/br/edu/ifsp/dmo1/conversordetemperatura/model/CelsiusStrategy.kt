package br.edu.ifsp.dmo1.conversordetemperatura.model

class CelsiusStrategy() : ConversorTemperatura {

    override fun getScale(): String {
        return "Cº"
    }

    //Recebe em Fahrenheit para Celsius
    override fun converter(temperature: Double) : Double {
       return (temperature - 32) /1.8
    }

    ///Celsius pra Kelvin
    override fun converterXparaKelvinOuKelvinParaX(temperature: Double): Double {
        return temperature + 273.15
    }
}