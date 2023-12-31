class PMLI(var letra: Char = ' ') {
    // Creación del conjunto en donde se guardarán las palabras
    var palabras = ConjuntoPalabras()

    /* Nombre: agregarPalabra
	 * Decripción: Este método se encargará de agregar la palabra al conjunto
     * si cumple con las condiciones.
	 * Descripción de los parámetros: El método recibe un String
	 * Precondición : p[0] == letra
	 * Postcondición: palabra.buscar(p) == true
	 */
    fun agregarPalabra(p: String) {
        if (letra == p[0]) {
            palabras.agregar(p)
        }
    }
    /* Nombre: eliminarPalabra
	 * Decripción: Este método se encargará de eliminar la palabra indicada en el conjunto de palabras
	 * Descripción de los parámetros: El método recibe un String 
	 * Precondición: p[0] == letra
	 * Postcondición: palabra.buscar(p) == false
	 */
    fun eliminarPalabra(p: String){
        if (letra == p[0]) {
            palabras.eliminar(p)
        }
    }

    /* Nombre: mostrarPalabra
	 * Decripción: Este método se encargará de mostrar todas las palabras
     * que se encuentran en el conjunto
	 * Precondición: true
	 * Postcondición: Se imprime el String palabras.toString (Impresión de cada palabra en orden lexográfico)
	 */
    fun mostrarPalabras() {
        println(palabras.toString())
    }

    /* Nombre: buscarPalabra
	 * Decripción: Este método recibe un String y retorna true si dicho String pertenece al ConjuntoPalabras o
	 * false en caso contrario
	 * Descripción de los parámetros: El método recibe un String y retorna un Boolean
	 * Precondición: letra == p[0]
	 * Postcondición: \result == true || \result == false
	 */
    fun buscarPalabra(p: String): Boolean {
        if (letra == p[0]) {
            return palabras.pertenece(p)
        } 
        return false
    }
}