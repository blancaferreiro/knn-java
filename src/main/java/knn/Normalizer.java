package knn;

public class Normalizer {
    
    private double[] min;
    private double[] max;

    // Calcular mínimo y máximo de cada columna usando solo estos datos
    public void fit(double[][] train){

        int numCols = train[0].length;
        min = new double[numCols];
        max = new double[numCols];

        // inicializar la primera fila
        for (int i = 0; i < numCols; i++){
            min[i] = train[0][i];
            max[i] = train[0][i]; 
        }
        // actualizar resto filas
        for (int j = 1; j < train.length; j++){
            for (int i = 0; i < numCols; i++){
                if (train[j][i] < min[i]){
                    min[i] = train[j][i];
                }
                if (train[j][i] > max[i]){
                    max[i] = train[j][i];
                }
            }        
        }
    }

    //Devuelve una copia de los datos normalizada con los min y max guardados
    public double[][] transform(double[][] data){
        double[][] result = new double[data.length][data[0].length];

        for (int i = 0; i < data.length; i++){
            for (int j = 0; j < data[0].length; j++){
                double range = max[j] - min[j];
                if (range == 0){
                    result[i][j] = 0;
                }else{
                    result[i][j] = (data[i][j] - min[j]) / range;
                }
            }
        }
        return result;
    }
}
