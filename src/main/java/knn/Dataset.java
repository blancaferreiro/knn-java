package knn;

import java.util.List;

/* Contenedor de datos: guarda en un solo objeto lo que necesita el algoritmo para trabajar. */
/* features: Cada fila es una muestra y cada columna una característica 
   labels: etiqueta(clase) de cada muestra
   featureNames: el nombre de cada columna
*/
// record -> usado para declarar una clase que solo guarda datos. Con esto Java genera automáticamente el constructor y los métodos 
public record Dataset(double[][] features, String[] labels, List<String> featuresNames){
   
    // Creo método para devolver cuántas muestras hay
    public int size(){
        return labels.length;
    }

    // Creo método para devolver cuántas características tiene cada muestra
    public int numFeatures(){
        if (features.length == 0){
            return 0;
        } 
        return features[0].length;   
    }
}
    

