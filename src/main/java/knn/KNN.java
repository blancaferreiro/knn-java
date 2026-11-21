package knn;

import java.util.HashMap;
import java.util.Map;

public class KNN {
    
    private final int k;
    private double[][] trainFeatures;
    private String[] trainLabels;

    public KNN(int k){
       if (k <= 0){
        throw new IllegalArgumentException("k must be positive");
       } 
       this.k = k;
    }

    public void fit(double[][] features, String[] labels){
        // Debe haber una etiqueta por cada muestra
        if (features.length != labels.length ){
            throw new IllegalArgumentException("features and labels must have the same length"); 
        }
        // Se necesita al menos k muestras para popder buscar k vecinos
        if (k > labels.length){
            throw new IllegalArgumentException("k cannot be larger than the number of training samples");
        }
        // k-NN no entrena: solo guarda los datos
        trainFeatures = features;
        trainLabels = labels;
    }

    public String predict(double[] sample){
        // Una distancia por cada muestra de entrenamiento
        double[] distances = new double[trainFeatures.length];
        for (int i = 0; i < trainFeatures.length; i++){
            distances[i] = euclideanDistance(sample, trainFeatures[i]);
        }

        // Posiciones de los k vecinos más cercanos
        int[] neighbors = new int[k];
        // used[i] es true si la muestra i ya se ha elegido como vecino
        boolean[] used = new boolean[distances.length];
        
        for (int n = 0; n < k; n++){
            // Posición de la menor distancia no usada todavía
            int best = -1;
            for (int i = 0; i < distances.length; i++){
                if (!used[i] && (best == -1 || distances[i] < distances[best])){
                    best = i;
                }
            }
            neighbors[n] = best;
            used[best] = true;
        } 

        Map<String, Integer> counts = new HashMap<>();
        for (int n = 0; n < k; n++){
            String label = trainLabels[neighbors[n]];
            // getOrDefault devuelve cuántas veces ha salido la etiqueta hasta ahora, o 0 si no ha aparecido
            counts.put(label, counts.getOrDefault(label, 0) + 1);
        }
        
    }

    static double euclideanDistance(double[] a, double[] b){
        
        double sum = 0;
        // Recorrer columnas y acumular el cuadrado de cada difererncia
        for (int j = 0; j < a.length; j++){
            double diff = a[j] - b[j];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
