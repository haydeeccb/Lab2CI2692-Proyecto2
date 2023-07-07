import kotlin.math.max
fun main() {
  val cars = arrayOf("Volvo", "BMW", "Ford", "Mazda")
  var x = "pala"
  var y = "pata"
  var r = OSA(x,y)
  for (x in cars) {
    println(x)
  }
  println(r)
}
fun esPalabraValida (palabra: String): Boolean {
    for (i in palabra) {
        var  codigo = i.toInt()
        if (97 <= codigo && codigo <= 122) {
            return true
        } else if (i == "ñ") {
            return true
        } else {
            return false
        }
    }
}
fun OSA2(cadena1: String, cadena2: String): Int {

    var d = Array(cadena1.length+1){Array(cadena2.length+1){0}}
    var minimo = Array(1){0}
    for (i in 0 until cadena1.length+1) {
        d[i][0] = i
    }
    for (j in 0 until cadena2.length+1) {
        d[0][j] = j
    }
    for (i in 1 until cadena1.length+1) {
        for (j in 1 until cadena2.length+1) {
            var cost = 0
            if(cadena1[i-1] == cadena2[j-1]) {
                d[i][j] = d[i-1][j-1]
            } else {
                minimo = arrayOf(d[i-1][j-1], d[i][j-1], d[i-1][j])
                //println(minimo.contentToString())
                //println("$i $j")
                d[i][j] =1 + min(minimo)
            }
        }
    }
    /*
    for (x in d) {
    	println(x.contentToString())
  	}
    */
    return d[cadena1.length][cadena2.length]
}
fun OSA(cadena1: String, cadena2: String): Int {

    var d = Array(cadena1.length+1){Array(cadena2.length+1){0}}
    var minimo = Array(1){0}
    for (i in 0 until cadena1.length+1) {
        d[i][0] = i
    }
    for (j in 0 until cadena2.length+1) {
        d[0][j] = j
    }
    for (i in 1 until cadena1.length+1) {
        for (j in 1 until cadena2.length+1) {
            var cost = 0
            if(cadena1[i-1] == cadena2[j-1]) {
                cost = 0
                
            } else {
                cost = 1
            }
            minimo = arrayOf(d[i-1][j-1]+cost, d[i][j-1]+1, d[i-1][j]+1)
			if (i > 1 && j > 1 && cadena1[i-1] == cadena2[j-2] && cadena1[i-2] == cadena2[j-1]) {
				minimo = arrayOf(d[i-2][j-2]) 
			}
            //println(minimo.contentToString())
            //println("$i $j")
            d[i][j] = min(minimo)
        }
    }
    /*
    for (x in d) {
    	println(x.contentToString())
  	}
    */
    return d[cadena1.length][cadena2.length]
}
/* 
fun DLD(cadena1: String, cadena2: String): Int {
    var da = Array(26){0}
    var d = Array(cadena1.length+2){Array(cadena2.length+2){0}}
    var maxdist = cadena1.length+cadena2.length
    d[0][0] = maxdist
    for (i in 1 until cadena1.length+2) {
        d[i][0] = maxdist
        d[i][1] = i
    }
    for (j in 1 until cadena2.length+2) {
        d[0][j] = maxdist
        d[1][j] = j
    }
    for (i in 2 until cadena1.length+2) {
        var db = 0
        for (j in 2 until cadena2.length+2) {
            var k = da[cadena2[j-2]]
            var l = db
            var cost = 0
            if(cadena1[i-2] == cadena2[j-2]) {
                cost = 0
                db = j
            } else {
                cost = 1
            }
            var minimo = arrayOf(d[i-1][j-1]+cost, d[i][j-1]+1, d[i-1][j]+1, d[k-1][l-1]+(i-k-1)+1+(j-l-1))
            radixSort(minimo)
            d[i][j] = minimo[0]
        }
        da[cadena1[i-2] = i]
    }
    return d[cadena1.length+2][cadena2.length+2]
}
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