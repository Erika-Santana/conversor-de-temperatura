package br.edu.ifsp.dmo1.conversordetemperatura.model

object KelvinStrategy : ConversorTemperatura {
    override fun getScale(): String {
        return "Kº"
    }

    //De Kelvin pra Celsius
    override fun converter(temperature: Double) : Double{
        return temperature - 273.15
    }

    //De Kelvin pra Fahrenheit
    override fun converterXparaKelvinOuKelvinParaX(temperature: Double): Double {
            return (((temperature - 273.15) * 9) /5 ) + 32
    }
}