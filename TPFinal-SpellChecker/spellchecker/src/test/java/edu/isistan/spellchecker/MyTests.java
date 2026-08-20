package edu.isistan.spellchecker;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

/** Cree sus propios tests. */
public class MyTests {

      @Test public void testTokenScanner_EntradaVacia() throws IOException {
            Reader in = new StringReader(""); 
            TokenScanner d = new TokenScanner(in);
            try {
                  assertFalse("Fin de entrada", d.hasNext());
            } finally {
                  in.close();
            }
      }

      @Test public void testTokenScanner_TokenPalabra() throws IOException {
            Reader in = new StringReader("Palabra"); 
            TokenScanner d = new TokenScanner(in);
            try {
                  assertTrue(d.hasNext());
                  assertEquals("Palabra", d.next());
                  assertFalse("Fin de entrada", d.hasNext());
            } finally {
                  in.close();
            }
      }
      
      @Test public void testTokenScanner_TokenNoPalabra() throws IOException {
            Reader in = new StringReader("."); 
            TokenScanner d = new TokenScanner(in);
            try {
                  assertTrue(d.hasNext());
                  assertEquals(".", d.next());
            } finally {
                  in.close();
            }
      }
      
      @Test public void testTokenScanner_TokenNoPalabraPalabra() throws IOException {
            Reader in = new StringReader(".Palabra"); 
            TokenScanner d = new TokenScanner(in);
            try {
                  assertTrue(d.hasNext());
                  assertEquals(".", d.next());
                  assertTrue(d.hasNext());
                  assertEquals("Palabra", d.next());
            } finally {
                  in.close();
            }
      }
      
      @Test public void testTokenScanner_TokenPalabraNoPalabra() throws IOException {
            Reader in = new StringReader("Palabra."); 
            TokenScanner d = new TokenScanner(in);
            try {
                  assertTrue(d.hasNext());
                  assertEquals("Palabra", d.next());
                  assertTrue(d.hasNext());
                  assertEquals(".", d.next());
            } finally {
                  in.close();
            }
      }

      @Test public void testDictionary_NumeroPalabras() throws IOException {
            Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
            assertEquals(32, d.getNumWords());
      }

      @Test public void testDictionary_StringVacioNoPresente() throws IOException {
            Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
            assertFalse("No existe string vacio en el diccionario", d.isWord(""));
      }

      @Test public void testDictionary_CapitalizacionesPresentes() throws IOException {
            Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
            assertTrue(d.isWord("aPPle"));
            assertTrue(d.isWord("Apple"));
            assertTrue(d.isWord("APPLE"));
      }
}
