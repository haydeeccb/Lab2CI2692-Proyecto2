// Librerías
import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader
import java.util.*

fun main() {
	var salirAplicacion = false
	var intentos = 0
	var ayudanteOrtografico = AyudanteOrtografico()
	println("Bienvenido al programa de ayudante ortográfico")
	while (salirAplicacion == false && intentos < 3) {
		println("Por favor seleccione una opción a ejecutar")
		println("1. Crear un nuevo ayudante ortográfico")
		println("2. Cargar un diccionario")
		println("3. Eliminar palabra")
		println("4. Corregir texto")
		println("5. Mostrar diccionario")
		println("6. Salir de la aplicación")
		var accion = readLine()
		when(accion) {
			"1" -> {
				ayudanteOrtografico = AyudanteOrtografico()
				println("Fue creado un nuevo ayudante ortográfico")
			}			
			"2" -> {
				var fname = readLine()
				if (fname == null) {
					println("El nombre ingresado no es válido. El nombre del archivo no puede ser vacío")
				} else {
					ayudanteOrtografico.cargarDiccionario(fname)
				}
			}
			"3" -> {
				println("Por favor ingrese la palabra a eliminar")
				var palabraEliminar = readLine()
				if (palabraEliminar == null) {
					println("La entrada no es válida. La palabra a eliminar no puede ser vacía")
				} else {
					//ayudanteOrtografico.borrarPalabra(palabraEliminar)
				}
			}
			"4" -> {
				println("Por favor ingrese el nombre del archivo a corregir")
				var finput = readLine()
				if (finput == null) {
					println("El nombre ingresado no es válido. El nombre del archivo no puede ser vacío")
				} else {
					println("Por favor ingrese el nombre que debe tener el archivo de corrección generado por el programa")
					var foutput = readLine()
					if (foutput == null) {
						println("El nombre ingresado no es válido. El nombre del archivo no puede ser vacío")
					} else {
						ayudanteOrtografico.corregirTexto(finput, foutput)
					}
				}
			}
			"5" -> //ayudanteOrtografico.imprimirDiccionario()
			"6" -> salirAplicacion = true
			else -> {
				intentos++
				println("La opción ingresada no es válida")
				println("Quedan ${3-intentos} oportunidades para ingresar una opción válida o se saldrá del programa")
			}
		}
	}
	println("Gracias por utilizar el programa ayudante ortográfico")
}