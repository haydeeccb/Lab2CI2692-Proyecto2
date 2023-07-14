// Librerias importadas
import kotlin.math.max
import kotlin.math.roundToInt

class ConjuntoPalabras {

	var n = 7
	var totalElementos = 0
	var factorDeCarga: Double = (totalElementos.toDouble())/(n.toDouble())
	var contenido = Array(n, {ListaCircular()})

	// Procedimientos del TAD Contenido 

	/* Nombre: agregar
	 * Decripción: Este método permite agregar un elemento a un ConjuntoPalabras. 
	 * Si el elemento a agregar ya pertenecía al conjunto, no se efectúan cambios
	 * Descripción de los parámetros: El método toma como único parámetro un String
	 * Precondición: palabra != null
	 * Postcondición: ConjuntoPalabras.contenido = ConjuntoPalabras.contenido0 U {palabra}
	 */
	fun agregar(palabra: String) {
		var i = funcionDeHash(palabra)
		if (contenido[i].buscar(palabra) == null) {
			contenido[i].agregarAlFrente(palabra)
			totalElementos++
			ajustarTabla()	
		}
	}

	/* Nombre: eliminar
	 * Decripción: Este método permite eliminar un elemento de un ConjuntoPalabras. 
	 * Si el elemento a eliminar no pertenecía al conjunto, no se efectúan cambios
	 * Descripción: El método toma como único parámetro un String
	 * Precondición: palabra != null
	 * Postcondición: ConjuntoPalabras.contenido = ConjuntoPalabras.contenido0 - {palabra}
	 */
	fun eliminar(palabra: String) {
		var i = funcionDeHash(palabra)
		var x = contenido[i].buscar(palabra)
		if (x != null) {
			contenido[i].eliminar(x)
			totalElementos--
			ajustarTabla()	
		}
	}

	/* Nombre: extraer
	 * Decripción: Este método extra un elemento de un ConjuntoPalabras. Específicamente, extrae el primer
	 * elemento de la primera casilla no vacía de la tabla de hash de ConjuntoPalabras
	 * Descripción de los parámetros: El método no recibe ningún parámetro y retorna un String?
	 * Precondición: true
	 * Postcondición: \result == null || (\exists int i; 0 <= i && i < n; ConjuntoPalabras.contenido[i].centi?.next == \result)
	 */
	fun extraer(): String? {
		for (i in 0 until n) {
			if (casillaVacia(i) == false) {
				var primero = contenido[i].centi?.next
				return primero?.value!!
			}
		}
		return null
	}

	/* Nombre: pertenece
	 * Decripción: Este método recibe un String y retorna true si dicho String pertenece al ConjuntoPalabras o
	 * false en caso contrario
	 * Descripción de los parámetros: El método recibe un String y retorna un Boolean
	 * Precondición: palabra != null
	 * Postcondición: \result == true || \result == false
	 */
	fun pertenece(palabra: String): Boolean {
		var i = funcionDeHash(palabra)
		var x = contenido[i].buscar(palabra)
		if (x != null) {
			return true
		} else {
			return false
		}
	}

	/* Nombre: vacio
	 * Decripción: Este método retorna true si el ConjuntoPalabras es vacío y false en caso contrario
	 * Descripción de los parámetros: El método no recibe ningún parámetro y retorna un Boolean
	 * Precondición: true
	 * Postcondición: \result == true || \result == false
	 */
	fun vacio(): Boolean {
		if (totalElementos == 0) {
			return true
		} else {
			return false
		}
	}

	/* Nombre: unir
	 * Decripción: Este método permite unir dos conjuntos del tipo ConjuntoPalabras
	 * Descripción de los parámetros: El método recibe dos ConjuntoPalabras, c1 y c2, y retorna un ConjuntoPalabras
	 * Precondición: true
	 * Postcondición: \result.totalElementos <= c1.totalElementos + c2.totalElementos
	 */
	fun unir(c1: ConjuntoPalabras, c2: ConjuntoPalabras): ConjuntoPalabras {
		if (c1.vacio() == true) {
			return c2
		}
		if (c2.vacio() == true) {
			return c1
		}
		var c3 = ConjuntoPalabras()
		for (i in 0 until c1.n) {
			if (c1.casillaVacia(i) == false) {
				var y = c1.contenido[i].centi?.next
				while(y?.value != null) {
					var x = y
					y = y.next
					c3.agregar(x.value!!)
				}
			}
		}
		for (i in 0 until c2.n) {
			if (c2.casillaVacia(i) == false) {
				var y = c2.contenido[i].centi?.next
				while(y?.value != null) {
					var x = y
					y = y.next
					c3.agregar(x.value!!)
				}
			}
		}
		return c3
	}

	/* Nombre: intersectar
	 * Decripción: Este método permite intersectar dos conjuntos del tipo ConjuntoPalabras
	 * Descripción de los parámetros: El método recibe dos ConjuntoPalabras, c1 y c2, y retorna un ConjuntoPalabras
	 * Precondición: true
	 * Postcondición: \result.totalElementos <= Math.min(c1.totalElementos, c2.totalElementos)
	 */
	fun intersectar(c1: ConjuntoPalabras, c2: ConjuntoPalabras): ConjuntoPalabras {
		var c3 = ConjuntoPalabras()
		if (c1.vacio() == true || c2.vacio() == true) {
			return c3
		}
		if (c1.n <= c2.n) {
			for (i in 0 until c1.n) {
				if (c1.casillaVacia(i) == false) {
					var y = c1.contenido[i].centi?.next
					while(y?.value != null) {
						if (c2.pertenece(y.value!!) == true) {
							var x = y
							y = y.next
							c3.agregar(x.value!!)
						} else {
							y = y.next
						}
					}
				}
			} 
		} else {
			for (i in 0 until c2.n) {
				if (c2.casillaVacia(i) == false) {
					var y = c2.contenido[i].centi?.next
					while(y?.value != null) {
						if (c1.pertenece(y.value!!) == true) {
							var x = y
							y = y.next
							c3.agregar(x.value!!)
						} else {
							y = y.next
						}
					}
				}
			}
		}
		return c3
	}

	/* Nombre: restar
	 * Decripción: Este método recibe dos ConjuntoPalabras, c1 y c2, y efectúa la resta c1-c2
	 * Descripción de los parámetros: El método recibe dos ConjuntoPalabras, c1 y c2, y retorna un ConjuntoPalabras
	 * Precondición: true
	 * Postcondición: \result.totalElementos <= c1.totalElementos
	 */
	fun restar(c1: ConjuntoPalabras, c2: ConjuntoPalabras): ConjuntoPalabras {
		var c3 = ConjuntoPalabras()
		if (c2.vacio() == true) {
			return c1
		}
		if (c1.vacio() == true) {
			return c3
		}
		for (i in 0 until c1.n) {
			if (c1.casillaVacia(i) == false) {
				var y = c1.contenido[i].centi?.next
				while(y?.value != null) {
					if (c2.pertenece(y.value!!) == false) {
						var x = y
						y = y.next
						c3.agregar(x.value!!)
					} else {
						y = y.next
					}
				}
			}
		} 
		return c3
	}

	// Procedimientos para la tabla de hash y extras del TAD Contenido 

	/* Nombre: funcionDeHash
	 * Decripción: Este método corresponde a la función de hash respecto a la cual se organiza la tabla de hash
	 * Dicha función emplea la herramienta hashCode() de kotlin para obtener un valor entero para un String dado
	 * Descripción de los parámetros: El método recibe un String y retorna un entero
	 * Precondición: palabra != null
	 * Postcondición: \result >= 0
	 */
	fun funcionDeHash(palabra: String): Int {
		var tmp = (palabra.hashCode())%n
		if (tmp < 0) {
			tmp = -1*tmp
		}
		return tmp
	}

	/* Nombre: ajustarTabla
	 * Decripción: Este método se emplea para ajusta el factor de carga de la tabla de Hash y en caso de que dicho factor
	 * sea mayor o igual a 0.7, llama al método rehashing para ajustar el tamaño de dicha tabla
	 * Descripción de los parámetros: El método no recibe ni retorna ningún parámetro
	 * Precondición: true
	 * Postcondición: true
	 */
	fun ajustarTabla() {
		factorDeCarga = (totalElementos.toDouble())/(n.toDouble())
		if (factorDeCarga >= 0.7) {
			rehashing(n, contenido)
		}
	}

	/* Nombre: rehashing
	 * Decripción: Este método se emplea para ajustar el tamaño de la tabla de Hash, cambiándola al doble de su tamaño anterior,
	 * y reorganiza los elementos en la nueva tabla de hash de acuerdo a la función de hash y al nuevo tamaño
	 * Descripción de los parámetros: Recibe un entero n1: Int y un arreglo de listas circulares contenidoA: Array<ListaCircular>
	 * Precondición: n1 >= 0
	 * Postcondición: n = 2*n_0
	 */
	fun rehashing(n1: Int, contenidoA: Array<ListaCircular>) {
		var nuevoN = 2*n
		n = nuevoN
		var contenidoNuevo = Array(nuevoN, {ListaCircular()})
		for (i in 0 until n1) {
			var y = contenidoA[i].centi?.next
			while (y?.value != null) {
				var x = y
				y = y.next 
				var j = funcionDeHash(x.value!!)
				contenidoNuevo[j].agregarAlFrente(x.value!!)
			}
		}
		contenido = contenidoNuevo
	}

	/* Nombre: casillaVacia
	 * Decripción: Este método verifica si una casilla dada de un ConjuntoPalabras está vacía. Si la casilla está vacía retorna 
	 * true y en caso contrario retorna false
	 * Descripción de los parámetros: Este método recibe un entero i: Int y retorna un Boolean
	 * Precondición: 0 <= i && i < n
	 * Postcondición: \result == true || \result == false
	 */
	fun casillaVacia(i: Int): Boolean {
		var primero: Nodo? = contenido[i].centi?.next
		if (primero == null) {
			return true
		}
		return false
	}

	/* Nombre: obtenerArregloPalabras
	 * Decripción: Este método organiza el contenido de un ConjuntoPalabras en un arreglo de String que contiene una palabra
	 * en cada entrada
	 * Descripción de los parámetros: El método no recibe ningún parámetro y retorna un Array<String>
	 * Precondición: true
	 * Postcondición: \result.size >= 0
	 */
	fun obtenerArregloPalabras(): Array<String> {
		var arregloPalabras = Array(totalElementos, {""})
		var j = 0
		for (i in 0 until n) {
			if (casillaVacia(i) == false) {
				var x: Nodo? = contenido[i].centi?.next
				while (x?.value != null) {
					arregloPalabras[j] = x.value!!
					j++
					x = x.next
				}
			}
		}
		return arregloPalabras
	}

	/* Nombre: toString
	 * Decripción: Este método retorna un String con las palabras de un ConjuntoPalabras organizadas por orden lexicográfico
	 * y separadas por un espacio y una coma
	 * Descripción de los parámetros: Este método no recibe ningún parámetro y retorna un String
	 * Precondición: true
	 * Postcondición: true
	 */
	override fun toString(): String {
		var arregloPalabras = obtenerArregloPalabras()
		if (arregloPalabras.size == 0) {
			return ""
		}
		radixSortPalabras(arregloPalabras)
		var stringPalabras = arregloPalabras[0]
		for (i in 1 until arregloPalabras.size) {
			stringPalabras = stringPalabras + ", " + arregloPalabras[i]
		}
		return stringPalabras
	}

	/* Nombre: radixSortPalabras
	 * Decripción: Este método es una versión modificada del algoritmo de ordenamiento Radix Sort implementado en la librería
	 * Sortlib. Con este algoritmo se arregla un arreglo de String de palabras válidas, por orden lexicográfico
	 * Descripción de los parámetros: Recibe un arreglo A: Array<String>
	 * Precondición: A.size > 0
	 * Postcondición: \result.size > 0
	 */
	fun radixSortPalabras(A: Array<String>) {
		var d = obtenerMaximoLongitud(A)
		for (k in d-1 downTo 0) {
			var B = arregloValoresDigito(A, k, d)
			countingSortPalabras(A, B)
		}
	}

	/* Nombre: obtenerMaximoLongitud
	 * Decripción: Este método recibe un A: Array<String> y retorna un entero correspondiente a la longitud del String más largo
	 * del arreglo A
	 * Descripción de los parámetros: Este método recibe un A: Array<String> y retorna un entero
	 * Precondición: A.size > 0
	 * Postcondición: \result >= 0
	 */
	fun obtenerMaximoLongitud(A: Array<String>): Int {
		var maximo = A[0].length
		for (i in 1 until A.size) {
			if (A[i].length >= maximo) {
				maximo = A[i].length
			}
		}
		return maximo
	}

	/* Nombre: arregloValoresDigito
	 * Decripción: Esta método recibe un A: Array<String> de palabras válidas y dos enteros k: Int, d: Int
	 * El entero k corresponde a la posición del caracter a analizar y el entero d corresponde a la longitud de la palabra
	 * más larga del arreglo A. Con este método se obtiene un arreglo B: Array<Int> que contiene en la entrada B[i] el número
	 * correspondiente a la letra A[i][k] en el alfabeto castellano (a es 1, b es 2, c es 3, ..., ñ es 15, ... z es 27)
	 * Si la palabra A[i] posee menos de d letras y k es mayor que el número de letras de A[i], entonces B[i] corresponde a 0.
	 * Esto se implementa para que radixSortPalabras coloque las palabras más cortas primero, siguiendo el orden lexicográfico
	 * en todo el arreglo. 
	 * Descripción de los parámetros: Este método recibe un A: Array<String> de palabras válidas y dos enteros k: Int, d: Int,
	 * y retorna un Array<Int>
	 * Precondición: A.size >= 0, 0 <= k && k < d
	 * Postcondición: \result.size = A.size && (\forall int i; 0 <= i && i < \result.size; 0 <= B[i] && B[i] <= 27)
	 */
	fun arregloValoresDigito(A: Array<String>, k: Int, d: Int): Array<Int> {
		var B = Array(A.size, {0})
		for (i in 0 until A.size) {
			if (A[i].length < d && k >= A[i].length) {
				B[i] = 0
			} else {
				var valorCaracter = A[i][k].hashCode()
				// Caso 1: El caracter está entre 'a' y 'n'
				if (97 <= valorCaracter && valorCaracter <= 110) {
					B[i] = valorCaracter - 96
				// Caso 2: El caracter está entre 'o' y 'z'
				} else if (111 <= valorCaracter && valorCaracter <= 122) {
					B[i] = valorCaracter - 96 + 1
				// Caso 3: El caracter es ñ
				} else {
					B[i] = 15 
				}
			}
		}
		return B
	}

	/* Nombre: countingSortPalabras
	 * Decripción: Este método es una versión modificada del algoritmo de ordenamiento Counting Sort implementado en la librería
	 * Sortlib. Con este algoritmo se ordena un arreglo A: Array<String> de palabras válidas de acuerdo a un caracter determinado
	 * (el primero, el segundo, el último, etc.), empleando un arreglo B: Array<Int> que contiene el valor del caracter correspondiente
	 * de la palabra A[i] en la entrada B[i] y dicho valor corresponde al número del caracter en el alfabeto castellano 
	 * (a es 1, b es 2, c es 3, ..., ñ es 15, ... z es 27). Para ordenar el arreglo A se emplean las frecuencias de los caracteres,
	 * gracias a los datos del arreglo B, siguiendo el esquema del Counting Sort clásico y empleando dos arreglos C: Array<Int>,
	 * D: Array<String> auxiliares
	 * Descripción de los parámetros: Recibe un arreglo A: Array<String> que contiene palabras válidas y un arrelgo B: Array<Int>
	 * que contiene los valores de un caracter determinado de las palabras de A, como se menciona en la descripción de la función.
	 * El método no retorna nada. 
	 * Precondición: A.size > 0 && A.size == B.size
	 * Postcondición: true
	 */
	fun countingSortPalabras(A: Array<String>, B: Array<Int>) {
		var k = B.max()
		var D = Array(B.size, {""})
		var C = Array(k+1){0}
		for (j in 0 until B.size) {
			C[B[j]] = C[B[j]]+1
		}
		for (i in 1 until C.size) {
			C[i] = C[i] + C[i-1]
		}
		for (j in B.size-1 downTo 0) {
			D[C[B[j]]-1] = A[j] 
			C[B[j]] = C[B[j]] -1
		}
		for (i in 0 until A.size) {
			A[i] = D[i]
		}
	}
}

// Clases adicionales para la implementación del TAD Contenido

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


// PARA PROBAR EL PROGRAMA. ESTO SE ELIMINA DESPUÉS

/*fun main(args: Array<String>) {
	var n = args[0].toInt()
	println("Se generaran ${n} palabras válidas aleatorias")
	var palabras = generarPalabrasAleatorias(n)
	println("Las palabras son:")
	imprimirArreglo(palabras)
	println("")
	var conjunto1 = ConjuntoPalabras()
	for (i in 0 until palabras.size) {
		conjunto1.agregar(palabras[i])
	}
	println("El conjunto1 es")
	println(conjunto1.toString())
	println("")

	// Para probar unión, intersección, resta y vacio

	var conjunto2 = ConjuntoPalabras()
	for (i in palabras.size/2+1 until palabras.size) {
		conjunto2.agregar(palabras[i])
	}
	println("El conjunto2 es")
	println(conjunto2.toString())
	println("")
	var conjunto3 = conjunto1.unir(conjunto1, conjunto2)
	println("La unión es")
	println(conjunto3.toString())
	println("")
	var conjunto4 = conjunto1.intersectar(conjunto1, conjunto2)
	println("La intersección es")
	println(conjunto4.toString())
	println("")
	var conjunto5 = conjunto1.restar(conjunto1, conjunto2)
	println("Conjunto1 - conjunto2 es")
	println(conjunto5.toString())
	println("")
	var conjunto6 = conjunto1.restar(conjunto2, conjunto1)
	println("Conjunto2 - conjunto1 es")
	println(conjunto6.toString())
	println("")
	println("Extraemos un elemento del conjunto1 y obtenemos")
	var x = conjunto1.extraer()
	println("${x}")
	println("El conjunto 1 es vacio es ${conjunto1.vacio()}")
	var conjunto7 = ConjuntoPalabras()
	println("El conjunto 7 es vacio es ${conjunto7.vacio()}")
}

fun generarPalabrasAleatorias(n: Int): Array<String> {
	var A = Array(n, {0})
	for (i in 0 until n) {
		A[i] = (1..n/3).random()
	}
	var B = Array(n, {""})
	for (i in 0 until n) {
		for (j in 0 until A[i]) {
			B[i] = B[i] + ('a'..'z').random()
			if (A[i]%2 == 0 && j%2 == 1) {
				B[i] = B[i] + "ñ"
			}
		}
	}
	return B
}

fun imprimirArreglo(A: Array<String>) {
	for (i in 0 until A.size) {
		print("${A[i]} ")
	}
	println(" ")

}*/