// Librerías
import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader

class AyudanteOrtografico {
	// Creación del ayudante ortográfico
	var dicc = Array(27, {PMLI()})
	init {
		var k = 0
    	for (i in 'a'..'n') {
        	dicc[k] = PMLI(i)
        	k++
    	}
    	dicc[k] = PMLI('ñ')
    	k++
    	for (i in 'o'..'z') {
        	dicc[k] = PMLI(i)
        	k++
    	}
	}

	// Procedimientos del TAD Ayudante Ortográfico

	/* Nombre: borrarPalabra
     * Decripción: Este método recibe una palabra, verifica que no haya
     * alguna discrepancia o caracter especial en ella, para luego 
     * proceder a eliminarla del PMLI apropiado
     * Descripción: El método toma como único parámetro un String
     * Precondición: esPalabraValida(p)
     * Postcondición: (\forall int i; 0 <= i < dicc.size; dicc[i].buscarPalabra(p) == false)
     */
    fun borrarPalabra(p: String) {
        var conjuntoDePalabras = buscarPMLI(p)
        conjuntoDePalabras.eliminarPalabra(p)
    }

	/* Nombre: cargarDiccionario
	 * Decripción: Este procedimiento lee un archivo de texto, verifica que cumpla con el formato correcto y agrega las palabras
	 * válidas que contenga dicho archivo al diccionario.
	 * Descripción de los parámetros: el procedimiento recibe un fname: String que corresponde al nombre del archivo de texto que
	 * debe procesar. Dicho nombre deben incluir la terminación .txt o la terminación respectiva del formato de archivo 
	 * de texto que se desee generar
	 * Precondición: fname != null y el archivo de texto debe cumplir con el siguiente formato: 
	 	- Debe estar compuesto por palabras válidas, es decir, palabras en minúscula, sin tildes y con las letras del 
	 	alfabeto castellano de la 'a' a la 'z' incluyendo la 'ñ'. Cada palabra debe estar sola en una línea
	 	- No debe incluir signos de puntuación, espacios en blanco o de tabulaciones, saltos de línea o palabras inválidas
	 * Postcondición: Las palabras válidas del archivo son agregadas al diccionario
	 */
	fun cargarDiccionario(fname: String) {
		var arregloPalabrasValidas = verificarYObtenerPalabrasValidasArchivo(fname)
		if (arregloPalabrasValidas.size == 0) {
			println("El archivo insertado no cumple las precondiciones para agregarse a un diccionario")
			println("Al diccionario únicamente se pueden agregar palabras en minúscula, sin tildes y con las letras del alfabeto castellano: de la 'a' a la 'z' incluyendo la 'ñ'")
			println("Un archivo para cargar en el diccionario sólo puede contener una palabra válida por línea")
			println("Un archivo para cargar en el diccionario no puede contener: palabras inválidas, signos de puntuación, saltos de línea, espacios en blanco o espacios de tabulaciones")
			println(" ")
			return 
		}
		var m = arregloPalabrasValidas.size
		for (i in 0 until m) {
			var palabra = arregloPalabrasValidas[i]
			var caracterInicial = palabra[0]
			for (k in 0 until 27) {
				if (caracterInicial == dicc[k].letra) {
					dicc[k].agregarPalabra(palabra)
					break
				}
			}
		}
		println("El diccionario fue cargado al ayudante ortográfico")
	}

	/* Nombre: corregirTexto
 	 * Decripción: Este procedimiento lee un archivo de texto, verifica su contenido y extrae aquellas palabras que son válidas.
 	 * Posteriormente verifica cuáles de esas palabras válidas pertenecen al diccionario y para cada una de aquellas que no pertenecen
 	 * proporciona cuatro sugerencias de palabras que sí pertenecen al diccionario y tienen la menor distancia Damerau-Levenshtein
 	 * con respecto a dicha palabra. Finalmente, genera un archivo de texto mostrando las palabras válidas que no pertenecen al
 	 * diccionario y sus cuatro sugerencias respectivas. 
 	 * A diferencia del procedimiento cargarDiccionario, el procedimiento corregirTexto puede recibir un archivo que contenga
 	 * palabras no válidas, signos de puntuación, espacios en blanco, de tabulaciones o saltos de línea; y las omite al momento de
 	 * extraer las palabras válidas del archivo.
	 * Descripción de los parámetros: El procedimiento recibe un finput: String, que corresponde al nombre del archivo de texto
	 * a leer; y un foutput: String, que corresponde al nombre del archivo de texto a generar. Ambos nombres deben incluir la 
	 * terminación .txt o la terminación respectiva del formato de archivo de texto que se desee generar
 	 * Precondición: finput != null && foutput != null
 	 * Postcondición: se genera un archivo de texto mostrando las palabras válidas que no pertenecen al diccionario y sus 
 	 * cuatro sugerencias respectivas. 
 	 */
	fun corregirTexto(finput: String, foutput: String) {
		var totalPalabras = 0
    	for (k in 0 until 27) {
        	totalPalabras = totalPalabras + dicc[k].palabras.totalElementos
    	}
    	if (totalPalabras == 0) {
    		println("El diccionario está vacío, no se puede corregir el texto")
    		println(" ")
    		return
    	}
		var arregloPalabrasValidas = obtenerPalabrasValidasArchivo(finput)
		if (arregloPalabrasValidas.size == 0) {
			println("Ninguna de las palabras del archivo insertado es válida, no se puede corregir el texto")
			println("Una palabra válida debe estar escrita en minúscula, sin tildes y con las letras del alfabeto castellano: de la 'a' a la 'z' incluyendo la 'ñ'")
			println("Una palabra válida no puede estar acompañada de espacios en blanco o de tabulaciones, signos de puntuación ni otras palabras en la misma línea")
			println(" ")
			return
		}
		var palabrasNoPertenecen = obtenerPalabrasNoPertenecen(arregloPalabrasValidas)
		var numPalabras = palabrasNoPertenecen.size
		if (numPalabras == 0) {
			println("Todas las palabras del archivo insertado pertenecen al diccionario, no hay sugerencias")
			println(" ")
			return
		}
		var sugerenciasCompleta = Array(numPalabras*5, {""})
		var k = 0
		for (i in 0 until numPalabras) {
			sugerenciasCompleta[k] = palabrasNoPertenecen[i]
			k++
			var sugerenciasTmp = obtenerPalabrasCercanas(palabrasNoPertenecen[i])
			for (j in 0 until 4) {
				sugerenciasCompleta[k+j] = sugerenciasTmp[j]
			}
			k = k+4
		}
		generarArchivoSalida(foutput, sugerenciasCompleta)
		println("El archivo de corrección ${foutput} fue generado")
	}

	/* Nombre: imprimirDiccionario
     * Decripción: Este método nos mostrará todas las palabras que contiene 
     * el diccionario hasta el momento
     * Precondición: True
     * Postcondición: (\forall int i; 0 <= i < dicc.size; dicc[i].mostrarPalabras())
     */
    fun imprimirDiccionario() {
        for (i in dicc) {
            var letra = i.letra
            letra = letra.uppercaseChar()
            println("----------------------- LETRA $letra -----------------------")
            println("")
            i.mostrarPalabras()
            println("")
        }
        println("Fin del Diccionario")
        println("")
    }

	// Procedimientos adicionales

	/* Nombre: esPalabraValida
     * Decripción: Este método recibe una palabra y verifica que no haya
     * alguna discrepancia o caracter especial en ella
     * Descripción: El método toma como único parámetro un String y retorna un Boolean
     * Precondición: palabra != null
     * Postcondición: \result == true || \result == false
     */
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

    /* Nombre: OSA (Optimal string alignment distance)
	 * Decripción: Este método permite saber cuántas operaciones son necesarias
	 * para convertir la cadena1 en la cadena2. Las operaciones a usar son: eliminar,
	 * inserción, sustitución y transposición. Este método emplea la distancia Damerau-Levenshtein, específicamente
	 * la versión óptima denominada Optimal string alignment distance
	 * Descripción de los parámetros: El método toma dos parámetros de tipo String y retorna un valor de tipo Int
	 * Precondición: cadena1 != null && cadena2 != null
	 * Postcondición: \result >= 0
	 */
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

	/* Nombre: min
 	 * Decripción: Este método recibe un arreglo de números y 
 	 * devolverá el número con menor valor
 	 * Descripción de los parámetros: El método toma como único parámetro un String
 	 * Precondición: A.size > 0
 	 * Postcondición: \result >= 0 && (\forall int i; 0 <= i < A.size; A[i] >= \result)
	 */
	fun min (A: Array<Int>): Int {
		var x = A[0] 
    	for (i in A) {
    		if (x >= i) {
        	x = i
        	}
    	}
    	return x
	}

    /* Nombre: buscarPMLI
     * Decripción: Este método recibe una palabra, luego verifica cada PMLI que está en el diccionario.
     * Si la letra del PMLI es igual a la inicial de la palabra este método procede a delvover ese PMLI.
     * Descripción: El método toma como único parámetro un String y retorna una clase PMLI
     * Precondición: palabra != null
     * Postcondición: \result == true || \result == false
     */
    fun buscarPMLI (p: String): PMLI {
        var conjunto = PMLI()
        for (i in dicc) {
            if (i.letra == p[0]) {
                conjunto = i
                break
            }
        }
        return conjunto
    }

	/* Nombre: verificarYObtenerPalabrasValidasArchivo
 	 * Decripción: Este procedimieto lee un archivo de texto, extrae las palabras válidas de dicho archivo y las retorna en un
 	 * Array<String>. Si el archivo contiene palabras no válidas u otro tipo de símbolo no válido (espacios, saltos de línea, 
	 * signos de puntuación, etc.), entonces retorna un arreglo de tamaño 0. 
	 * Descripción de los parámetros: Recibe un A: String, que corresponde al nombre del archivo de texto a procesar; y retorna
	 * un Array<String> que contiene las palabras válidas del archivo de nombre A
 	 * Precondición: A != null
 	 * Postcondición: \result.size >= 0 && (\forall int i; 0 <= i && i < \result.size; \forall int j; 0 <= j && j < \result[i].size; ('a' <= \result[i][j] && \result[i][j] <= z) || \result[i][j] == 'ñ'))
 	 */
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

	/* Nombre: obtenerPalabrasValidasArchivo
 	 * Decripción: Este procedimieto lee un archivo de texto, extrae las palabras válidas de dicho archivo y las retorna en un
 	 * Array<String>. Si el archivo contiene palabras no válidas u otro tipo de símbolo no válido (espacios, saltos de línea, 
	 * signos de puntuación, etc.), el procedimiento los ignora y extrae sólo las palabras válidas
	 * Descripción de los parámetros: Recibe un A: String, que corresponde al nombre del archivo de texto a procesar; y retorna
	 * un Array<String> que contiene las palabras válidas del archivo de nombre A
 	 * Precondición: A != null
 	 * Postcondición: \result.size >= 0 && (\forall int i; 0 <= i && i < \result.size; \forall int j; 0 <= j && j < \result[i].size; ('a' <= \result[i][j] && \result[i][j] <= z) || \result[i][j] == 'ñ'))
 	 */
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

	/* Nombre: obtenerPalabrasNoPertenecen
 	 * Decripción: Este procedimiento recibe un arreglo A: Array<String> de palabras válidas y verifica cuáles de esas palabras
 	 * no pertenecen al diccionario. Luego, retorna las palabras que no pertenecen en un Array<String>
	 * Descripción de los parámetros: Recibe un arreglo A: Array<String> de palabras válidas y retorna un Array<String> de palabras
	 * válidas
 	 * Precondición: A.size > 0 && (\forall int i; 0 <= i && i < A.size; \forall int j; 0 <= j && j < A[i].size; ('a' <= A[i][j] && A[i][j] <= z) || A[i][j] == 'ñ')
 	 * Postcondición: \result.size >= 0 && (\forall int i; 0 <= i && i < \result.size; \forall int j; 0 <= j && j < \result[i].size; ('a' <= \result[i][j] && \result[i][j] <= z) || \result[i][j] == 'ñ'))
 	 */
	fun obtenerPalabrasNoPertenecen(A: Array<String>): Array<String> {
		var numPalabras = 0
		for (i in 0 until A.size) {
			var caracterInicial = A[i][0]
			for (k in 0 until 27) {
				if (caracterInicial == dicc[k].letra) {
					if (dicc[k].buscarPalabra(A[i]) == false) {
						numPalabras++
					}
					break
				}
			}
		}
		var palabrasNoPertenecen = Array(numPalabras, {""})
		var j = 0
		for (i in 0 until A.size) {
			var caracterInicial = A[i][0]
			for (k in 0 until 27) {
				if (caracterInicial == dicc[k].letra) {
					if (dicc[k].buscarPalabra(A[i]) == false) {
						palabrasNoPertenecen[j] = A[i]
						j++
					}
					break
				}
			}
		}
		return palabrasNoPertenecen
	}

	/* Nombre: obtenerPalabrasCercanas
 	 * Decripción: Este método recibe una palabra: String y retorna un arreglo Array<String> con máximo 4 palabras 
 	 * del diccionario que tienen la menor distancia Damerau-Levenshtein respecto a la palabra dada
	 * Descripción de los parámetros: recibe una palabra: String que debe ser una palabra válida y retorna un arreglo de palabras
	 * válidas
 	 * Precondición: palabra != null && (\forall int j; 0 <= j && j < palabra.length; ('a' <= palabra[j] && palabra[j] <= 'z') || palabra[j] == 'ñ')
 	 * Postcondición: \result.size == 4 && (\forall int i; 0 <= i && i < \result.size; \forall int j; 0 <= j && j < \result[i].size; ('a' <= \result[i][j] && \result[i][j] <= z) || \result[i][j] == 'ñ'))
 	 */
	fun obtenerPalabrasCercanas(palabra: String): Array<String> {
    	var arregloDistancias = obtenerDistancias(palabra)
    	var distanciasOrdenadas = countingSortDistancias(arregloDistancias)
    	var palabrasCercanas = Array(4, {""})
    	for (i in 0 until 4) {
        	palabrasCercanas[i] = distanciasOrdenadas[i].second
    	}
    	return palabrasCercanas
	}

	/* Nombre: obtenerDistancias
 	 * Decripción: Este método recibe una palabra: String y retorna un arreglo de pares Array<Pair<Int,String>> que contiene
 	 * todas las palabras del diccionario y su distancia Damerau-Levenshtein respecto a la palabra dada
	 * Descripción de los parámetros: recibe una palabra: String que debe ser una palabra válida y retorna un arreglo de pares, los
	 * cuales consisten en un entero no negativo y una palabra válida
 	 * Precondición: palabra != null && (\forall int j; 0 <= j && j < palabra.length; ('a' <= palabra[j] && palabra[j] <= 'z') || palabra[j] == 'ñ')
 	 * Postcondición: \result.size >= 0
 	 */
	fun obtenerDistancias(palabra: String): Array<Pair<Int,String>> {
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
                    	arregloDistancias[j] = Pair(OSA(palabra, tmp), tmp)
                    	j++
                    	x = x.next
                	}
            	}
        	}
    	}
    	return arregloDistancias
	}

	/* Nombre: countingSortDistancias
 	 * Decripción: Este método es una versión modificada del algoritmo de ordenamiento Counting Sort implementado en la librería
	 * Sortlib. Con este algoritmo se ordena un arreglo de pares A: Array<Pair<Int,String>> que contiene pares de palabras válidas
	 * y enteros no negativos. Dicho arreglo se ordena de menor a mayor de acuerdo al valor de los enteros. A diferencia de Counting
	 * Sort, este método no retorna el arreglo recibido ordenado sino que retorna un nuevo arreglo ordenado
	 * Descripción de los parámetros: Recibe un arreglo de pares A: Array<Pair<Int,String>> que contiene pares de palabras válidas
	 * y enteros no negativos; y retorna el contenido de A ordenado de acuerdo a los enteros, en un nuevo arreglo de pares 
	 * Array<Pair<Int,String>>
 	 * Precondición: A.size > 0
 	 * Postcondición: \result.size > 0
 	 */
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

	/* Nombre: obtenerMaximo
 	 * Decripción: Este procedimiento recibe un arreglo de pares A: Array<Pair<Int,String>> y retorna el máximo de los valores
 	 * enteros de dicho arreglo
	 * Descripción de los parámetros: Recibe un arreglo de pares A: Array<Pair<Int,String>> y retorna un entero
 	 * Precondición: A.size > 0
 	 * Postcondición: true
 	 */
	fun obtenerMaximo(A: Array<Pair<Int,String>>): Int {
    	var maximo = A[0].first
    	for (i in 1 until A.size) {
        	if (A[i].first > maximo) {
            	maximo = A[i].first
        	}
    	}
    	return maximo 
	}

	/* Nombre: generarArchivoSalida
 	 * Decripción: Este procedimiento recibe un foutput: String y un arreglo sugerenciasCompleta: Array<String> y genera un archivo
 	 * de tecto de nombre foutput y con el contenido de dicho arreglo. En dicho archivo se coloca en cada línea una palabra válida
 	 * que no pertenece al diccionario, seguida de cuatro palabras válidas sugeridas que tienen la menor distancia Damerau-Levenshtein 
 	 * respecto a la palabra que no pertenece
	 * Descripción de los parámetros: foutput corresponde al nombre del archivo de texto que se deseea generar y sugerenciasCompleta
	 * es un arreglo que contiene las palabras que no pertenecen al diccionario seguida de las cuatro sugerencias con menor distancia
	 * que sí pertenecen
 	 * Precondición: fourtput != null && sugerenciasCompleta.size >= 5
 	 * Postcondición: true
 	 */
    fun generarArchivoSalida(foutput: String, sugerenciasCompleta: Array<String>) {
    	var archivoSalida = File(foutput)
    	var numPalabras = sugerenciasCompleta.size/5
    	var k = 0
    	var textoSalida = sugerenciasCompleta[k]
    	k++
    	for (i in k until k+4) {
    		textoSalida = textoSalida + "," + sugerenciasCompleta[i]
    	}
    	k = k + 4
    	archivoSalida.writeText(textoSalida)
    	for (i in 1 until numPalabras) {
    		textoSalida = sugerenciasCompleta[k]
    		k++
    		for (j in k until k+4) {
    			textoSalida = textoSalida + "," + sugerenciasCompleta[j]
    		}
    		k = k + 4 
        	archivoSalida.appendText("\n"+textoSalida)
    	}
	}
}