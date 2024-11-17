package br.edu.ifsp.dmo1.conversordetemperatura.model

class FahrenheitStrategy: ConversorTemperatura{
    override fun getScale(): String {
        return "Fº"
    }

    //Recebe Celsius para Fahrenheit
    override fun converter(temperature: Double) : Double {
        return (temperature * 1.8) + 32
    }

    //Recebe Fahrenheit para Kelvin
    override fun converterXparaKelvinOuKelvinParaX(temperature: Double): Double {
        return ((temperature * 5) /9 ) + 273.15
    }
}