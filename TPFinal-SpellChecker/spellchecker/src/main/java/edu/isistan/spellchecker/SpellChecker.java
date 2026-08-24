package edu.isistan.spellchecker;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

/**
 * El SpellChecker usa un Dictionary, un Corrector, and I/O para chequear
 * de forma interactiva un stream de texto. Despues escribe la salida corregida
 * en un stream de salida. Los streams pueden ser archivos, sockets, o cualquier
 * otro stream de Java.
 * <p>
 * Nota:
 * <ul>
 * <li> La implementación provista provee métodos utiles para implementar el SpellChecker.
 * <li> Toda la salida al usuario deben enviarse a System.out (salida estandar)
 * </ul>
 * <p>
 * El SpellChecker es usado por el SpellCkecherRunner. Ver:
 * @see SpellCheckerRunner
 */
public class SpellChecker {
	private Corrector corr;
	private Dictionary dict;

	/**
	 * Constructor del SpellChecker
	 * 
	 * @param c un Corrector
	 * @param d un Dictionary
	 */
	public SpellChecker(Corrector c, Dictionary d) {
		corr = c;
		dict = d;
	}

	/**
	 * Returna un entero desde el Scanner provisto. El entero estará en el rango [min, max].
	 * Si no se ingresa un entero o este está fuera de rango, repreguntará.
	 *
	 * @param min
	 * @param max
	 * @param sc
	 */
	private int getNextInt(int min, int max, Scanner sc) {
		while (true) {
			try {
				int choice = Integer.parseInt(sc.next());
				if (choice >= min && choice <= max) {
					return choice;
				}
			} catch (NumberFormatException ex) {
				// Was not a number. Ignore and prompt again.
			}
			System.out.println("Entrada invalida. Pruebe de nuevo!");
		}
	}

	/**
	 * Retorna el siguiente String del Scanner.
	 *
	 * @param sc
	 */
	private String getNextString(Scanner sc) {
		return sc.next();
	}



	/**
	 * checkDocument interactivamente chequea un archivo de texto..  
	 * Internamente, debe usar un TokenScanner para parsear el documento.  
	 * Tokens de tipo palabra que no se encuentran en el diccionario deben ser corregidos
	 * ; otros tokens deben insertarse tal cual en en documento de salida.
	 * <p>
	 *
	 * @param in stream donde se encuentra el documento de entrada.
	 * @param input entrada interactiva del usuario. Por ejemplo, entrada estandar System.in
	 * @param out stream donde se escribe el documento de salida.
	 * @throws IOException si se produce algún error leyendo el documento.
	 */
	public void checkDocument(Reader in, InputStream input, Writer out) throws IOException {
		Scanner sc = new Scanner(input);
            TokenScanner tokenizer = new TokenScanner(in);
            while (tokenizer.hasNext()){
                  String token = tokenizer.nextConEspacios();
                  if (!token.isEmpty()){
                        boolean palabra_valida = dict.isWord(token);
                        if (palabra_valida || token.matches("\\d+") || (!palabra_valida && token.length() == 1)){
                              out.write(token);
                        } else {
                              Set<String> correcciones = corr.getCorrections(token);
                              mostrarCorrecciones(token, correcciones);
                              int opcion = getNextInt(0, correcciones.size() + 2, sc);
                              String corregida = token;
                              if (opcion == 1){
                                    System.out.print("\nIngrese la palabra corregida: ");
                                    corregida = getNextString(sc);
                              } else if (opcion != 0){
                                    corregida = correcciones.toArray()[opcion-2].toString();
                              }
                              out.write(corregida);
                        }
                  }
            }
	}

      private void mostrarCorrecciones(String mal_escrita, Set<String> correcciones) {
            System.out.println("\n========================================");
            System.out.println("         REVISION ORTOGRAFICA");
            System.out.println("========================================");
 
            System.out.println("\nSe detecto un posible error en la palabra:");
            System.out.println(">> '" + mal_escrita + "'\n");

            System.out.println("Sugerencias de correccion:\n");
            System.out.println("[0] Ignorar y continuar");
            System.out.println("[1] Reemplazar con otra palabra");

            Iterator<String> it = correcciones.iterator();
            int i = 2;
            while (it.hasNext()) {
                  System.out.printf("\n[%d] Reemplazar con \"%s\"", i, it.next());
                  i++;
            }
 
            System.out.println("\n----------------------------------------");
            System.out.printf("Por favor, seleccione una opcion: ");
      }
}
