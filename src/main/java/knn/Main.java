package knn;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException{
        
        CsvLoader loader = new CsvLoader(",", false, 0, Set.of());
        // Indicar dónde está el archivo
        Path ruta = Path.of("data/wine.csv");
        // Cargamos los datos
        Dataset d = loader.load(ruta);
        System.out.println(d.size() + " samples, " + d.numFeatures() + " features");
    }
}
