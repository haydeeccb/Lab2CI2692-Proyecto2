KC = kotlinc

KFLAGS = -include-runtime

PROG = TestPO.jar

SRC = PruebaOrtografia.kt AyudanteOrtografico.kt PMLI.kt ConjuntoPalabras.kt preliminares.kt

all:
	$(KC) $(SRC) $(KFLAGS) -d $(PROG)
.PHONY : clean

clean :
	rm -rf $(PROG)