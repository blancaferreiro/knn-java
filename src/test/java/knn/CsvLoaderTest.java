package knn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CsvLoaderTest {

    @Test 
    // Carga la etiqueta en la primera columna sin cabecera
    // @TempDir Path dir = JUnit crea una carpeta temporal y la entrega en dir, cuando el test termina lo borra
    void loadsWithoutHeader(@TempDir Path dir) throws IOException{
        // arrange: construye ruta para archivo dentro de la carpeta temporal y crea el archivo con ese texto (dos muestras, con etiqueta 1 y 2 y sus caract. respectivas)
        Path f = dir.resolve("mini.csv");
        Files.writeString(f, "1,14.2,1.7\n2,13.0,2.1\n");
        // act: carga el archivo con la configuración de wine, sin cabecera, etiqueta en la columna 0 y sin ignorar columnas
        Dataset d = new CsvLoader(",", false, 0, Set.of()).load(f);
        // assert: assertEquals(esperado, real) -> si coinciden sigue, si no el test falla y dice que resultado esperaba
        assertEquals(2, d.size());
        assertEquals(2, d.numFeatures());
        assertEquals("1", d.labels()[0]);
        assertEquals(14.2, d.features()[0][0], 1e-9);
    }

    @Test
    // Cabecera, columna de id ignorada y etiqueta en la segunda columna
    void loadsWithHeatherAndIgnoreColumn(@TempDir Path dir) throws IOException{
        // arrange:
        Path f = dir.resolve("mini2.csv");
        Files.writeString(f, "id,diagnosis,a,b\n101,M,1.5,2.5\n102,B,3.5,4.5\n");
        // act:
        Dataset d = new CsvLoader(",", true, 1, Set.of(0)).load(f);
        // assert:
        assertEquals(2, d.size());
        assertEquals(2, d.numFeatures());
        assertEquals("M", d.labels()[0]);
        assertEquals(3.5, d.features()[1][0], 1e-9);
        assertEquals("a", d.featuresNames().get(0));
    }
}
