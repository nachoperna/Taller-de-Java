package edu.isistan.spellchecker;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;

import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.corrector.Dictionary;
import edu.isistan.spellchecker.corrector.impl.FileCorrector;
import edu.isistan.spellchecker.corrector.impl.SwapCorrector;
import edu.isistan.spellchecker.corrector.impl.FileCorrector.FormatException;
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

      @Test public void testFileCorrector_EspaciosEnLineas() throws IOException, FormatException {
            Corrector c = new FileCorrector(new FileReader("smallMisspellingsConEspacios.txt"));
            assertNotNull("chimpanze ->  chimpanzee}", c.getCorrections("chimpanze"));
      }

      @Test public void testFileCorrector_SinCorrecciones() throws IOException, FormatException {
            Corrector c = new FileCorrector(new FileReader("smallMisspellings.txt"));
            assertEquals("tigger -> {tiger, trigger}", new TreeSet<>(), c.getCorrections("sincorreccion"));
      }

      @Test public void testFileCorrector_MultipleCorrecciones() throws IOException, FormatException {
            Corrector c = new FileCorrector(new FileReader("smallMisspellings.txt"));
            Set<String> result = c.getCorrections("tigger").stream().map(String::trim).collect(Collectors.toSet());
            assertEquals("tigger -> {tiger, trigger}", new TreeSet<>(Arrays.asList("tiger", "trigger")), result);
      }

      @Test public void testFileCorrector_DistintasCapitalizaciones() throws IOException, FormatException {
            Corrector c = new FileCorrector(new FileReader("smallMisspellings.txt"));
            assertEquals("Gose -> Goose", new TreeSet<>(Arrays.asList("Goose")), c.getCorrections("Gose"));
            assertEquals("gOSe -> goose", new TreeSet<>(Arrays.asList("goose")), c.getCorrections("gOSe"));
            assertEquals("GOSE -> GOOSE", new TreeSet<>(Arrays.asList("Goose")), c.getCorrections("GOSE"));
      }

      @Test public void testSwapCorrector_DiccionarioNull() throws IOException, FormatException  {
            try {
                  Corrector c = new SwapCorrector(null);
                  fail("Se esperaba IllegalArgumentException por diccionario nulo.");
            } catch (Exception e) {

            }
      }

      @Test public void testSwapCorrector_PalabraEnDiccionario() throws IOException, FormatException {
		Reader reader = new FileReader("smallDictionary.txt");
            Corrector c = new SwapCorrector(new Dictionary(new TokenScanner(reader)));
            assertEquals("yuo -> you", new TreeSet<>(Arrays.asList("you")), c.getCorrections("yuo"));
      }

      @Test public void testSwapCorrector_DistintasCapitalizaciones() throws IOException, FormatException {
		Reader reader = new FileReader("smallDictionary.txt");
            Corrector c = new SwapCorrector(new Dictionary(new TokenScanner(reader)));
            assertEquals("ITS' -> it's", new TreeSet<>(Arrays.asList("IT'S")), c.getCorrections("ITS'"));
            assertEquals("Its' -> it's", new TreeSet<>(Arrays.asList("It's")), c.getCorrections("Its'"));
            assertEquals("ItS' -> it's", new TreeSet<>(Arrays.asList("It'S")), c.getCorrections("ItS'"));
      }
}
