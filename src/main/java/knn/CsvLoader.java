package knn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* Creado para que lea el CSV y lo covierta en un Dataset */

public class CsvLoader {
    
    /* Creamos 4 datos de configuracion, que una vez asignados en el constructor no cambian
    delimiter = con qué caractér se separan las columnas
    hasHeader = si la primera línea son los nombres de las columnas
    labelColumn = en qué columna está la etiqueta
    ignoredCollumns = columnas que no hay que leer */

    private final String delimiter;  
    private final boolean hasHeader;
    private final int labelColumn;
    private final Set<Integer> ignoredColumns;

    public CsvLoader(String delimiter, boolean hasHeader, int labelColumn, Set<Integer> ignoredColumns) {
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        this.labelColumn = labelColumn;
        this.ignoredColumns = ignoredColumns;
    }

    // Path path: esel dato qeu recibe, la ruta de un archivo
    // IOException: error, el archivo, no existe no se puede leer
    public Dataset load(Path path) throws IOException{
        
        // Lee archivo y devuelve una lista con una líndea de texto por elemento
        List<String> lines = Files.readAllLines(path);
        // Guarda los nombres de columnas, si los hay
        String[] header = null;
        List<double[]> rows = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        

        // Bucle para recorrer una línea cada vez, si la línea está vacía, la salta
        for (int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            if (line.isBlank()){
                continue;
            }
            // Dividimos correctamente la línea en sus componentes
            String[] parts = line.strip().split(delimiter);
            // Si estoy en la primera línea y el archivo tiene cabecera
            if (i == 0 && hasHeader){
                // Guardar partes como los nombres de las columnas
                header = parts;
                continue;
            }
            // A partir de aquí la línea son datos, no cabecera
            // Tamaño = todas las columnas - la etiqueta y las ignoradas
            double[] values = new double[parts.length - 1 - ignoredColumns.size()];
            int j = 0;
            // Recorrer las columnas de la línea una a una
            for (int c = 0; c < parts.length; c++){
                // Si la columna es la etiqueta, la guardamos como texto
                if (c == labelColumn){
                    labels.add(parts[c].strip());
                // Si la columna es una característica
                } else if (!ignoredColumns.contains(c)){
                    // Convertimos la característica a número y la guardamos
                    values[j] = Double.parseDouble(parts[c].strip());
                    j++;
                }
            }
            // Recorrió ya toda la fila: la añadimos a la lista de filas
            rows.add(values);
        }
        // Creamos la lista de nombres vacía  
        List<String> names = new ArrayList<>();
        // Contar las columnas
        int numCols = 0;
        if (rows.size() > 0){
            numCols = rows.get(0).length;
        }
        // Si el archivo tiene cabecera: se aprovechan los nombres que tiene el archivo (solo los de las características)
        if (header != null){
            for (int c = 0; c < header.length; c++){
                if (c!= labelColumn && !ignoredColumns.contains(c)){
                    names.add(header[c].strip());
                }              
            }
        // Si el archivo no tiene cabecera: no hay nombre reales, así que se inventan 
        } else {
            for (int i = 0; i < numCols; i++){
                names.add("f" + i);
            }
        }
        return new Dataset(rows.toArray(new double[0][]), labels.toArray(new String[0]), names);
    }
}
