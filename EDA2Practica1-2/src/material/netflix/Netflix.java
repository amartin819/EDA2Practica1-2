package material.netflix;

import java.util.Set;

/**
 *
 * @author jvelez
 */
public class Netflix {

    
    /**
     * Carga un fichero de películas
     * @param file 
     */
    public Netflix(String file) {
        
    }
            
    /**
     * Consulta por nombre: dado un nombre de una película, se recuperarán todas
     * las que coincidan con el título dado.
     */
    public Movie findTitle(String title) {
        throw new RuntimeException("Implementa este método");
    }

    /**
     * Consulta por año: dado un año, se devolverán todas las películas
     * almacenadas que se encuentren en dicho año.
     *
     * @param year
     * @return
     */
    public Movie findYear(int year) {
        throw new RuntimeException("Implementa este método");
    }

    /**
     * Consulta por puntuación: dado una puntuación, se devolverán todas las
     * películas almacenadas que tengan dicha puntuación o superior.
     *
     * @param score
     * @return
     */
    public Movie findScore(float score) {
        throw new RuntimeException("Implementa este método");

    }

    /**
     * Consulta por tipo: dado un tipo, se devolverán
     * todas las películas que pertenezcan a ese tipo.
     *
     * @param type
     * @return
     */
    public Movie findType(String type) {
        throw new RuntimeException("Implementa este método");

    }

    /**
     * Consulta por tipos: dado un conjunto de tipos, se devolverán
     * todas las películas que pertenezcan a esos tipos.
     *
     * @param type
     * @return
     */
    public Movie findType(Set<String> type) {
        throw new RuntimeException("Implementa este método");

    }

    /**
     * Puntuar una película. Para ello se almacenará la suma de puntuaciones
     * obtenidas hasta el momento, y el número de puntuaciones realizadas. Así,
     * al añadir una nueva puntuación, basta con sumar la puntuación a la previa
     * y aumentar en una unidad el número de puntuaciones realizadas. La
     * puntuación final será la división entre la suma de puntuaciones y el
     * número de puntuaciones realizadas. 
     * @param title
     * @param score
     */
    public void addScore(String title, float score) {
        throw new RuntimeException("Implementa este método");

    }
}
