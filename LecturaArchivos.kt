import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader


fun main(args: Array<String>) {

    // Para verificar las funciones de lectura de archivos

	var A = verificarYObtenerPalabrasValidasArchivo(args[0])
	var B = obtenerPalabrasValidasArchivo(args[0])
	if (A.size == 0) {
		println("Hay palabras no válidas en el archivo insertado. No cumple las precondiciones para agregarse a un diccionario. Se aborta el programa")
	} else {
		println("Arreglo producto de verificar y obtener las palabras válidas del archivo:")
		imprimirArreglo(A)
	}
	println(" ")
	println("Arreglo producto de obtener las palabras válidas del archivo:")
	imprimirArreglo(B)

    // Para verificar las funciones que obtienen las palabras cercanas

    // Iniciando un diccionario
    /*var dicc = Array(27, {PMLI()})
    var k = 0
    for (i in 'a'..'n') {
        dicc[k] = PMLI(i)
        if (k%2 == 0) {
           dicc[k].agregarPalabra(i+"prueba") 
        } else {
            dicc[k].agregarPalabra(i+"nombres")
        }
        k++
    }
    dicc[k] = PMLI('ñ')
    dicc[k].agregarPalabra("ñpalabra")
    k++
    for (i in 'o'..'z') {
        dicc[k] = PMLI(i)
        if (k%2 == 0) {
           dicc[k].agregarPalabra(i+"computadora") 
        } else {
            dicc[k].agregarPalabra(i+"palabras")
        }
        k++
    }
    var palabra = "hola"
    var C = obtenerDistancias(palabra, dicc)
    println("La palabra es ${palabra} y el arreglo de distancias con el diccionario es")
    imprimirArreglo2(C)
    println(" ")
    println("El arreglo de distancias ordenado con counting sort es")
    var D = countingSortDistancias(C)
    imprimirArreglo2(D)
    println(" ")
    println("Las 4 palabras con menor distancia a ${palabra} son")
    var E = obtenerPalabrasCercanas(palabra, dicc)
    imprimirArreglo(E)*/

}

// Funciones para la lectura de archivos

fun verificarYObtenerPalabrasValidasArchivo(A: String): Array<String> {
	var i = 0
	var j = 0
    // Se cuenta la cantidad de palabras válidas del archivo
    File(A).forEachLine {line ->
    	if (esPalabraValida(line) == true) {
    		i++
    	}
    	j++
    }
    /* Si la cantidad de palabras válidas del archivo es menor a la cantidad de líneas,
     * entonces el archivo no cumple la precondición del procedimiento cargarDiccionario 
     * del TAD Ayudante Ortográfico y se devuelve un arreglo vacío para abortar el programa
     */
    if (i < j) {
    	var B = Array(0, {""})
    	return B
    }
    var numeroPalabrasValidas = i
    // Se crea arreglo con tamaño igual al número de palabra válidas
    var B = Array(numeroPalabrasValidas){""}
    // Rellenamos cada elemento de B con las palabras válidas del texto
    i = 0
    File(A).forEachLine {line -> B[i++] = line}
    return B
}

fun obtenerPalabrasValidasArchivo(A: String): Array<String> {
    var conjuntoPalabras = ConjuntoPalabras()
    File(A).forEachLine {line ->
        var n = line.length
        var palabra = ""
        for (i in 0 until n) {
            var caracter = line[i]
            if (('a' <= caracter && caracter <= 'z') || caracter == 'ñ') {
                palabra = palabra + caracter.toString()
                if (i == n-1) {
                    conjuntoPalabras.agregar(palabra)
                    palabra = ""
                }
            } else {
                if (palabra.length > 0) {
                    conjuntoPalabras.agregar(palabra)
                    palabra = ""
                }
            }
        }
    }
    var B = conjuntoPalabras.obtenerArregloPalabras()
    return B
}

// Funciones para obtener las 4 palabras con menos distancia DL a una palabra dada

fun obtenerPalabrasCercanas(palabra: String, dicc: Array<PMLI>): Array<String> {
    var arregloDistancias = obtenerDistancias(palabra, dicc)
    var distanciasOrdenadas = countingSortDistancias(arregloDistancias)
    var palabrasCercanas = Array(4, {""})
    for (i in 0 until 4) {
        palabrasCercanas[i] = distanciasOrdenadas[i].second
    }
    return palabrasCercanas
}

fun obtenerDistancias(palabra: String, dicc: Array<PMLI>): Array<Pair<Int,String>> {
    var totalPalabras = 0
    for (k in 0 until 27) {
        totalPalabras = totalPalabras + dicc[k].palabras.totalElementos
    }
    var arregloDistancias = Array(totalPalabras, {Pair(0, "")})
    var j = 0
    for (k in 0 until 27) {
        for (i in 0 until dicc[k].palabras.n) {
            if (dicc[k].palabras.casillaVacia(i) == false) {
                var x: Nodo? = dicc[k].palabras.contenido[i].centi?.next
                while (x?.value != null) {
                    var tmp = x.value!!
                    arregloDistancias[j] = Pair(OSA2(palabra, tmp), tmp)
                    j++
                    x = x.next
                }
            }
        }
    }
    return arregloDistancias
}

fun countingSortDistancias(A: Array<Pair<Int,String>>): Array<Pair<Int,String>> {
    var k = obtenerMaximo(A)
    var B = Array(A.size, {Pair(0, "")})
    var C = Array(k+1, {0})
    for (j in 0 until A.size) {
        C[A[j].first] = C[A[j].first]+1
    }
    for (i in 1 until C.size) {
        C[i] = C[i] + C[i-1]
    }
    for (j in A.size-1 downTo 0) {
        B[C[A[j].first]-1] = A[j] 
        C[A[j].first] = C[A[j].first] -1
    }
    return B
}

fun obtenerMaximo(A: Array<Pair<Int,String>>): Int {
    var maximo = A[0].first
    for (i in 1 until A.size) {
        if (A[i].first > maximo) {
            maximo = A[i].first
        }
    }
    return maximo 
}

// Adicional para probar el programa

fun imprimirArreglo(A: Array<String>) {
	for (i in 0 until A.size) {
		println("${A[i]}")
	}
}

fun imprimirArreglo2(A: Array<Pair<Int,String>>) {
    for (i in 0 until A.size) {
        println("( ${A[i].first}, ${A[i].second} ) ")
    }
}

// Otras versiones de algunas funciones

/*fun obtenerPalabrasValidasArchivo(A: String): Array<String> {
        var listaPalabras = ListaCircular()
        File(A).forEachLine {line ->
            var n = line.length
            var palabra = ""
            for (i in 0 until n) {
                var caracter = line[i]
                if (('a' <= caracter && caracter <= 'z') || caracter == 'ñ') {
                    palabra = palabra + caracter.toString()
                    if (i == n-1) {
                        listaPalabras.agregarAlFinal(palabra)
                        palabra = ""
                    }
                } else {
                    if (palabra.length > 0) {
                        listaPalabras.agregarAlFinal(palabra)
                        palabra = ""
                    }
                }
            }
        }
        var numPalabras = listaPalabras.totalElementos
        var B = Array(numPalabras, {""})
        var i = 0
        var x = listaPalabras.centi?.next 
        while (x?.value != null) {
            B[i] = x.value!!
            i++
            x = x.next
        }
        return B
    }*/