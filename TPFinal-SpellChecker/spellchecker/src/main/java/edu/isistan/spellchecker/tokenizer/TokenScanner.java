package edu.isistan.spellchecker.tokenizer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.PushbackReader;

/**
 * Dado un archivo provee un método para recorrerlo.
 */
public class TokenScanner implements Iterator<String> {
      private PushbackReader input;

      /**
      * Crea un TokenScanner.
      * <p>
      * Como un iterador, el TokenScanner solo debe leer lo justo y
      * necesario para implementar los métodos next() y hasNext(). 
      * No se debe leer toda la entrada de una.
      * <p>
      *
      * @param in fuente de entrada
      * @throws IOException si hay algún error leyendo.
      * @throws IllegalArgumentException si el Reader provisto es null
      */
      public TokenScanner(java.io.Reader in) throws IOException {
            this.input = new PushbackReader(in);
      }

      /**
      * Determina si un carácer es una caracter válido para una palabra.
      * <p>
      * Un caracter válido es una letra (
      * Character.isLetter) o una apostrofe '\''.
      *
      * @param c 
      * @return true si es un caracter
      */
      public static boolean isWordCharacter(int c) {
            return Character.isLetter(c) || (char) c == '\'';
      }


      /**
      * Determina si un string es una palabra válida.
      * Null no es una palabra válida.
      * Un string que todos sus caracteres son válidos es una 
      * palabra. Por lo tanto, el string vacío es una palabra válida.
      * @param s 
      * @return true si el string es una palabra.
      */
      public static boolean isWord(String s) {
            if (s == null) 
                  return false;
            if (s.isEmpty())
                  return false;
            return s.codePoints().allMatch(c -> isWordCharacter(c));
      }

      /**
      * Determina si hay otro token en el reader.
      */
      public boolean hasNext() {
            int c;
            try {
                  c = input.read();
            } catch (IOException e) {
                  e.printStackTrace();
                  return false;
            }
            if (c == -1)
                  return false;
            try {
                  input.unread(c);
            } catch (IOException e) {
                  e.printStackTrace();
            }
            return true;
      }

      /**
      * Retorna el siguiente token.
      *
      * @throws NoSuchElementException cuando se alcanzó el final de stream
      */
      public String next() throws NoSuchElementException {
            StringBuilder token = new StringBuilder();
            int c = 0;
            char ch = ' ';
            do {
                  try {
                        c = input.read();
                        ch = (char) c;
                  } catch (IOException e) {
                        e.printStackTrace();
                  }
            } while (Character.isWhitespace(c) && c != -1);

            if (!isWordCharacter(c) && c != -1)
                  return String.valueOf(ch);

            while (isWordCharacter(c)){
                  token.append(ch);
                  try {
                        c = input.read();
                        ch = (char) c;
                  } catch (IOException e) {
                        e.printStackTrace();
                  }
            }
            if (c != -1){
                  try {
                        input.unread(c);
                  } catch (IOException e) {
                        e.printStackTrace();
                  }
            }
            return token.toString();
      }


      public String nextConEspacios() throws NoSuchElementException {
            int c = 0;
            char ch = ' ';
            try {
                  c = input.read();
                  ch = (char) c;
            } catch (IOException e) {
                  e.printStackTrace();
            }
            if (Character.isWhitespace(c))
                  return String.valueOf(ch);
            try {
                  input.unread(c);
            } catch (IOException e) {
                  e.printStackTrace();
            }
            return next();
      }
}
