KC = kotlinc

KFLAGS = -include-runtime

PROG = AyudanteOrtografico.jar

SRC = Nodo.kt ListaCircular.kt ConjuntoPalabras.kt PMLI.kt AyudanteOrtografico.kt PruebaOrtografia.kt

all:
	$(KC) $(SRC) $(KFLAGS) -d $(PROG)
.PHONY : clean

clean :
	rm -rf $(PROG)
