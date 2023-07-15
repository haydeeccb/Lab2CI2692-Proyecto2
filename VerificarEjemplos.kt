// Librerías
import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader
import java.util.*

fun main() {
	println("Con este programa puede verificar las distancias de un archivo de sugerencias producido por un ayudante ortográfido")
	print("Por favor ingrese el nombre del archivo de texto con las sugerencias: ")
	var nombreArchivoEntrada = readLine()
	print("Por favor ingrese el nombre que debe tener el archivo de texto con las distancias de las sugerencias: ")
	var nombreArchivoSalida = readLine()
	var palabras = obtenerPalabras(nombreArchivoEntrada!!)
	var distancias = obtenerDistancias(palabras)
	//imprimirArregloTriples(distancias)
	generarArchivoSalida(nombreArchivoSalida!!, distancias)
	println("El archivo con las distancias de las sugerencias fue generado")
}

fun generarArchivoSalida(foutput: String, A: Array<Triple<String,String,Int>>) {
    var archivoSalida = File(foutput)
    var palabra1 = A[0].first
    var palabra2 = A[0].second
    var distancia = A[0].third.toInt()
    var textoSalida = "(" + palabra1 + ", " + palabra2 + ", " +distancia + ")"
    archivoSalida.writeText(textoSalida)
    for (i in 1 until A.size) {
    	palabra1 = A[i].first
    	palabra2 = A[i].second
    	distancia = A[i].third.toInt()
    	textoSalida = "(" + palabra1 + ", " + palabra2 + ", " +distancia + ")"
        archivoSalida.appendText("\n"+textoSalida)
        if (i%4 == 3) {
        	archivoSalida.appendText("\n"+ " ")
        }
    }
}

fun imprimirArregloTriples(A: Array<Triple<String,String,Int>>) {
	for (i in 0 until A.size) {
		println("(${A[i].first}, ${A[i].second}, ${A[i].third})")
	}
}

fun obtenerPalabras(A: String): Array<String> {
	var conjuntoPalabras = ListaCircular()
	var numPalabras = 0
    File(A).forEachLine {line ->
        var n = line.length
        var palabra = ""
        for (i in 0 until n) {
            var caracter = line[i]
            if (('a' <= caracter && caracter <= 'z') || caracter == 'ñ') {
                palabra = palabra + caracter.toString()
                if (i == n-1) {
                    conjuntoPalabras.agregarAlFinal(palabra)
                    numPalabras++
                    palabra = ""
                }
            } else {
                if (palabra.length > 0) {
                    conjuntoPalabras.agregarAlFinal(palabra)
                    numPalabras++
                    palabra = ""
                }
            }
        }
    }
    var B = Array(numPalabras, {""})
    var i = 0
    var x = conjuntoPalabras.centi?.next
    while (x?.value != null) {
    	B[i] = x.value!!
    	i++
    	x = x.next
    }
    return B
}

fun obtenerDistancias(A: Array<String>): Array<Triple<String,String, Int>> {
    var arregloDistancias = Array(A.size-A.size/5, {Triple("", "",0)})
    var j = 0
    for (k in 0 until A.size-4 step 5) {
        arregloDistancias[j] = Triple(A[k], A[k+1], OSA(A[k], A[k+1]))
        arregloDistancias[j+1] = Triple(A[k], A[k+2], OSA(A[k], A[k+2]))
        arregloDistancias[j+2] = Triple(A[k], A[k+3], OSA(A[k], A[k+3]))
        arregloDistancias[j+3] = Triple(A[k], A[k+4], OSA(A[k], A[k+4]))
        j = j+4
    }
    return arregloDistancias
}

fun OSA(cadena1: String, cadena2: String): Int {
    var d = Array(cadena1.length+1){Array(cadena2.length+1){0}}
    var minimo: Array<Int>
    for (i in 0 until cadena1.length+1) {
       	d[i][0] = i
    }
    for (j in 0 until cadena2.length+1) {
       	d[0][j] = j
    }
    for (i in 1 until cadena1.length+1) {
       	for (j in 1 until cadena2.length+1) {
           	if(cadena1[i-1] == cadena2[j-1]) {
                d[i][j] = d[i-1][j-1]
           	} else {
               	minimo = arrayOf(d[i-1][j-1], d[i][j-1], d[i-1][j])
               	d[i][j] = 1 + min(minimo)
           	}
        }
    }
    return d[cadena1.length][cadena2.length]
}

fun min (A: Array<Int>): Int {
	var x = A[0] 
    for (i in A) {
    	if (x >= i) {
        x = i
        }
    }
    return x
}

class Nodo (var value: String? = null) {
    
    var prev: Nodo? = null
    var next: Nodo? = null
}

class ListaCircular {
    
    var centi: Nodo? = Nodo()

    /* Nombre: agregarAlFrente
	 * Decripción: Este método agrega un String dado al frente de la ListaCircular
	 * Descripción de los parámetros: Este método recibe un String k y no retorna nada
	 * Precondición: k != null
	 * Postcondición: ListaCircular.centi?.next != null
	 */
    fun agregarAlFrente(k: String) {
        val key = Nodo(k)
        var centinela = centi
        if (centinela?.next == null) {
            centinela?.next = key
            centinela?.prev = key 
            key.prev = centinela
            key.next = centinela
        } else {
            centinela.next?.prev = key
            key.next = centinela.next
            key.prev = centinela
            centinela.next = key
        }
    }

    /* Nombre: agregarAlFinal
	 * Decripción: Este método agrega un String dado al final de la ListaCircular
	 * Descripción de los parámetros: Este método recibe un String k y no retorna nada
	 * Precondición: k != null
	 * Postcondición: ListaCircular.centi?.prev != null
	 */
    fun agregarAlFinal(k: String) {
        val key = Nodo(k)
        var centinela = centi
        if (centinela?.next == null) {
            agregarAlFrente(k)
        } else {
            centinela.prev?.next = key 
            key.prev = centinela.prev
            key.next = centinela
            centinela.prev = key
        }
    }

    /* Nombre: buscar
	 * Decripción: Este método busca un String dado en una ListaCircular, si dicho String pertenece retorna su valor 
	 * (el mismo String) y en caso contrario retorna null
	 * Descripción de los parámetros: Este método recibe un String value y retorna un Nodo?
	 * Precondición: value != null
	 * Postcondición: true
	 */
    fun buscar(value: String): Nodo? {
        var x = centi?.next
        while (x?.value != value && x?.value != null) {
            x = x.next
        }
        if (x?.value == value) {
        	return x
        }
        return null
    }

    /* Nombre: eliminar
	 * Decripción: Este método recibe un Nodo? y si el mismo pertene a la ListaCircular entonces lo elimina, en caso contrario
	 * no hace nada
	 * Descripción de los parámetros: Recibe un Nodo? y no retorna nada
	 * Precondición: true
	 * Postcondición: true
	 */
    fun eliminar(key: Nodo?) {
        // El nodo ingresado debe pertenecer a la lista, en caso contrario no habrá cambios
        if (key?.value != null && key.prev != null && key.next != null) {
            key.next?.prev = key.prev
            key.prev?.next = key.next
   	    }
    }

    /* Nombre: toString
	 * Decripción: Este método retorna los elementos de una ListaCircular como un String
	 * Descripción de los parámetros: El método no recibe ningún parámetro y retorna un String
	 * Precondición: true
	 * Postcondición: true
	 */
    override fun toString(): String {
        var valores= ""
        var e = centi?.next
        while (e?.value != null) {
            valores = valores + " ${e.value} "
            e = e.next
        }
        return valores
    }
}