// Librerías
import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader

import java.util.*

fun main() {
	println("Este programa sirve para generar archivos de prueba para el TAD Ayudante Ortográfico")
	println("Con este programa se generarán dos archivos, uno con palabras válidas y el otro con palabras válidas y no válidas")
	println("Ambos archivos tendrán la misma cantidad n de palabras y ambas palabras tendrán un tamaño aleatorio entre 1 y n/3")
	println("Por favor ingrese la cantidad n de palabras. Dicha cantidad debe ser mayor o igual a 3, preferiblemente mayor o igual a 30")
	var n = readLine()!!.toInt()
	println("Por favor ingrese el nombre del archivo que contiene sólo palabras válidas, no debe ser nulo")
	var nombre1 = readLine()!!
	println("Por favor ingrese el nombre del archivo que contiene palabras válidas y no válidas, no debe ser nulo")
	var nombre2 = readLine()!!
	generarArchivoValido(n, nombre1)
	generarArchivoNoValido(n, nombre2)
	println("Ambos archivos ya fueron generados")
}

fun generarArchivoValido(n: Int, nombre1: String) {
    var archivoSalida = File(nombre1)
    var palabra = generarPalabraValida(n)
    archivoSalida.writeText(palabra)
    for (k in 1 until n) {
    	palabra = generarPalabraValida(n)
    	archivoSalida.appendText("\n"+palabra)
    }
}

fun generarArchivoNoValido(n: Int, nombre2: String) {
    var archivoSalida = File(nombre2)
    var palabra = generarPalabraNoValida(n)
    archivoSalida.writeText(palabra)
    for (k in 1 until n) {
    	if (k%2 == 0) {
    		palabra = generarPalabraNoValida(n)
    	} else {
    		palabra = generarPalabraValida(n)
    	} 
    	archivoSalida.appendText("\n"+palabra)
    	if (k%4 == 0) {
    		archivoSalida.appendText("\n"+"")
    	}
    }
}

fun generarPalabraValida(n: Int): String {
	var tamañoPalabra = (1..n/3).random()
    var palabra: String = ('a'..'z').random().toString()
    for (i in 1 until tamañoPalabra) {
    	if (i%4 == 0) {
    		palabra = palabra + 'ñ'
    	} else {
    		palabra = palabra + ('a'..'z').random()
    	}
    }
    return palabra
}

fun generarPalabraNoValida(n: Int): String {
	var tamañoPalabra = (1..n/3).random()
	var palabra: String = ('a'..'z').random().toString()
	for (i in 1 until tamañoPalabra) {
    	var aleatorio = (0..8).random()
    	when (aleatorio) {
    		0 -> palabra = palabra + ('A'..'Z').random()
    		1 -> palabra = palabra + ('!'..'/').random()
    		2 -> palabra = palabra + "ñ"
    		3 -> palabra = palabra + " "
    		4 -> palabra = palabra + ('a'..'z').random()
    		5 -> palabra = palabra + "."
    		6 -> palabra = palabra + ";"
    		7 -> palabra = palabra + ","
    		8 -> palabra = palabra + ":"
    	}
    }
    return palabra
}