class AyudanteOrtografico{
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

    /* Nombre: borrarPalabra
    * Decripción: Este método recibe una palabra, verifica que no haya
    * alguna discrepancia o caracter especial en ella, para luego 
    * proceder a eliminarla del PMLI apropiado
    * Descripción: El método toma como único parámetro un String
    * Precondición: esPalabraValida(p)
    * Postcondición: (\forall int i; 0 <= i < dicc.size; dicc[i].buscarPalabra(p) == false)
    */
    fun borrarPalabra(p: Strig) {
        var conjuntoDePalabras = PMLI()
        if (esPalabraValida(p)) {
            conjuntoDePalabras = buscarPMLI(p)
            conjuntoDePalabras.eliminarPalabra(p)
        } else {
            println("La palabra ingresada no es valida")
            println("Por favor revise si cumple estas condiciones: ")
            println("-Esta escrita en minuscula")
            println("-No posee acentos o caracteres especiales")
        }
    }

    /* Nombre: imprimirDiccionario
    * Decripción: Este método nos mostrara todas las palabras que contiene 
    * el diccionario hasta el momento
    * Precondición: True
    * Postcondición: (\forall int i; 0 <= i < dicc.size; dicc[i].mostrarPalabra())
    */
    fun imprimirDiccionario() {
        println("Procederemos a mostrar el diccionario de forma alfabetica")
        for (i in dicc) {
            var letra = i.letra.toUpper()
            //letra = letra.toUpper()
            println("LETRA $letra")
            i.mostrarPalabra()
        }
        println("Fin del Diccionario")
    }
}

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


/* Nombre: buscarPMLI
 * Decripción: Este método recibe una palabra, luego verifica cada PMLI que esta en el diccionario.
 * Si la letra del PMLI es igual a la inicial de la palabra este procede a delvover ese PMLI.
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