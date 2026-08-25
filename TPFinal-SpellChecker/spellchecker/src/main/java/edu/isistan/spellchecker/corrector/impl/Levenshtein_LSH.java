package edu.isistan.spellchecker.corrector.impl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.corrector.Dictionary;
import info.debatty.java.lsh.LSHMinHash;
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
public class Levenshtein_LSH extends Corrector {
      // Objeto de la librería LSH
      private LSHMinHash lsh;
      
      // Nuestro "índice" donde guardaremos: HashDelBucket -> Set de Palabras
      private Map<Integer, Set<String>> lshIndex;
      
      // Tamaño del universo de N-gramas (para inicializar LSH)
      private final int UNIVERSE_SIZE = 100000;

	/**
	 * Construye un Levenshtein Corrector usando un Dictionary.
	 * Debe arrojar <code>IllegalArgumentException</code> si el diccionario es null.
	 *
	 * @param dict
	 */
	public Levenshtein_LSH(Dictionary dict) {
            if (dict == null) {
                  throw new IllegalArgumentException();
            }
            this.lshIndex = new HashMap<>();
            
            // 1. Configurar los parámetros de LSH
            int stages = 3;       // Número de bandas (aumentar para más sensibilidad/falsos positivos)
            int buckets = 100;    // Cantidad de buckets por banda
            
            this.lsh = new LSHMinHash(stages, buckets, UNIVERSE_SIZE);
            
            // 2. Indexar todo el diccionario al arrancar
            indexarDiccionario(dict);
	}

      // Recorre las palabras válidas del Dictionary, les aplica LSH y las guarda en buckets.
      private void indexarDiccionario(Dictionary dict) {
            // NOTA: Asegúrate de que la clase Dictionary tenga un método getWords() o 
            // algo similar que te devuelva la lista/set de las palabras válidas.
            dict.diccionario.iterator().forEachRemaining(word -> {
                  // Convertimos la palabra a un set de enteros (shingles/n-gramas)
                  boolean[] profile = getProfile(word);

                  // Calculamos en qué buckets debe ir esta palabra
                  int[] hashes = lsh.hash(profile);

                  // Guardamos la palabra en todos los buckets que devolvió LSH
                  for (int hash : hashes) {
                        lshIndex.putIfAbsent(hash, new HashSet<>());
                        lshIndex.get(hash).add(word);
                  }
            });
      }

    /**
     * Convierte un string en un perfil de n-gramas (en este caso bigramas)
     * y los hashea a enteros para que la librería los entienda.
     */
      private boolean[] getProfile(String s) {
            boolean[] profile = new boolean[UNIVERSE_SIZE];
            
            // Si la palabra es muy corta, hasheamos la palabra entera
            if (s.length() < 2) {
                  int index = Math.abs(s.hashCode()) % UNIVERSE_SIZE;
                  profile[index] = true;
                  return profile;
            }
            
            // Generamos los bigramas y encendemos el flag en 'true'
            for (int i = 0; i < s.length() - 1; i++) {
                  String bigram = s.substring(i, i + 2);
                  int index = Math.abs(bigram.hashCode()) % UNIVERSE_SIZE;
                  profile[index] = true;
            }
            return profile;
      }

      @Override
      public Set<String> getCorrections(String wrong) {
            if (wrong == null) {
                  throw new IllegalArgumentException();
            }
            
            String wrongLower = wrong.toLowerCase();
            Set<String> corrections = new HashSet<>();
            Set<String> candidatos = new HashSet<>();
            
            // 1. OBTENER CANDIDATOS VÍA LSH usando el arreglo booleano
            boolean[] profile = getProfile(wrongLower);
            int[] queryHashes = lsh.hash(profile);
            
            for (int hash : queryHashes) {
                  if (lshIndex.containsKey(hash)) {
                        candidatos.addAll(lshIndex.get(hash));
                  }
            }
            
            // 2. FILTRAR CON LEVENSHTEIN MATEMÁTICO (esto queda igual)
            for (String candidato : candidatos) {
                  if (calcularLevenshtein(wrongLower, candidato) == 1) {
                        corrections.add(candidato);
                  }
            }
            
            return matchCase(wrong, corrections);    
      }

      /**
     * Algoritmo de Programación Dinámica para calcular la distancia real.
     */
    private int calcularLevenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= b.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int costoSustitucion = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                
                dp[i][j] = Math.min(
                    dp[i - 1][j] + 1,                    // Borrado
                    Math.min(
                        dp[i][j - 1] + 1,                // Inserción
                        dp[i - 1][j - 1] + costoSustitucion // Sustitución
                    )
                );
            }
        }
        return dp[a.length()][b.length()];
    }
}
