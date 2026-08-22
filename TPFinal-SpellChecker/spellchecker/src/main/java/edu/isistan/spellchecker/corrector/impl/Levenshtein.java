package edu.isistan.spellchecker.corrector.impl;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.corrector.Dictionary;

/**
 *
 * Un corrector inteligente que utiliza "edit distance" para generar correcciones.
 * 
 * La distancia de Levenshtein es el número minimo de ediciones que se deber
 * realizar a un string para igualarlo a otro. Por edición se entiende:
 * <ul>
 * <li> insertar una letra
 * <li> borrar una letra
 * <li> cambiar una letra
 * </ul>
 *
 * Una "letra" es un caracter a-z (no contar los apostrofes).
 * Intercambiar letras (thsi -> this) <it>no</it> cuenta como una edición.
 * <p>
 * Este corrector sugiere palabras que esten a edit distance uno.
 */
public class Levenshtein extends Corrector {
      private Dictionary dictionary;
      private String alfabeto = "abcdefghijklmnñopqrstuvwxyz";

	/**
	 * Construye un Levenshtein Corrector usando un Dictionary.
	 * Debe arrojar <code>IllegalArgumentException</code> si el diccionario es null.
	 *
	 * @param dict
	 */
	public Levenshtein(Dictionary dict) {
            if (dict == null) {
                  throw new IllegalArgumentException();
            }
            dictionary = dict;
	}

	/**
	 * @param s palabra
	 * @return todas las palabras a erase distance uno
	 */
	Set<String> getDeletions(String s) {
            return IntStream.range(0, s.length())
            .mapToObj(i -> s.substring(0, i) + s.substring(i + 1))
            .filter(deletion -> dictionary.isWord(deletion.toLowerCase()))
            .collect(Collectors.toSet());
	}

	/**
	 * @param s palabra
	 * @return todas las palabras a substitution distance uno
	 */
	public Set<String> getSubstitutions(String s) {
            Set<String> corrections = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                  for (int j = 0; j < alfabeto.length(); j++) {
                        String reemplazo = "";
                        if (i+1 == s.length()){
                              reemplazo = s.substring(0, i) + alfabeto.charAt(j);
                        } else {
                              reemplazo = s.substring(0, i) + alfabeto.charAt(j) + s.substring(i+1);
                        }
                        if (!reemplazo.equals(s) && dictionary.isWord(reemplazo.toLowerCase()))
                              corrections.add(reemplazo);
                  }
            }
            return corrections;
	}


	/**
	 * @param s palabra
	 * @return todas las palabras a insert distance uno
	 */
	public Set<String> getInsertions(String s) {
            Set<String> corrections = new HashSet<>();
            for (int i = -1; i < s.length(); i++) {
                  for (int j = 0; j < alfabeto.length(); j++) {
                        String reemplazo = "";
                        if (i+1 == s.length()){
                              reemplazo = s + alfabeto.charAt(j);
                        } else {
                              reemplazo = s.substring(0, i+1) + alfabeto.charAt(j) + s.substring(i+1);
                        }
                        if (dictionary.isWord(reemplazo.toLowerCase()))
                              corrections.add(reemplazo);
                  }
            }
            return corrections;
	}

	public Set<String> getCorrections(String wrong) {
            if (wrong == null)
                  throw new IllegalArgumentException();
            Set<String> corrections = new HashSet<>();
            corrections.addAll(getDeletions(wrong));
            corrections.addAll(getSubstitutions(wrong));
            corrections.addAll(getInsertions(wrong));
            return matchCase(wrong, corrections);
	}
}
