import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader


fun main(args: Array<String>) {
	
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
}

fun esPalabraValida (palabra: String): Boolean {
	if (palabra.length == 0) {
		return false
	}
    for (i in palabra) {
        var  codigo = i.hashCode()
        if (codigo < 97 || 122 < codigo) {
        	if (i.toString() != "ñ") {
        		return false
        	} 
        }
    }
    return true
}

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
	var i = 0
    // Se cuenta la cantidad de palabras válidas del archivo
    File(A).forEachLine {line ->
    	if (esPalabraValida(line) == true) {
    		i++
    	}
    }
    var numeroPalabrasValidas = i
    // Se crea arreglo con tamaño igual al número de palabra válidas
    var B = Array(numeroPalabrasValidas){""}
    // Rellenamos cada elemento de B con las palabras válidas del texto
    i = 0
    File(A).forEachLine {line ->
    	if (esPalabraValida(line) == true) {
    		B[i] = line
    		i++
    	}
    }
    return B
}

// Adicional para probar el programa

fun imprimirArreglo(A: Array<String>) {
	for (i in 0 until A.size) {
		println("${A[i]}")
	}
}