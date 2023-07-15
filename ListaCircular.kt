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