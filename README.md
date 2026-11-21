# knn-java

Clasificador k-vecinos más cercanos (k-NN) construido desde cero en Java, sin librerías de aprendizaje automático.

El objetivo es entender el algoritmo de principio a fin (carga de datos, normalización, predicción y evaluación) y comprobar que el mismo código funciona con conjuntos de datos muy distintos sin modificar el algoritmo.

> **Estado:** trabajo en progreso. Consulta la hoja de ruta para ver qué está hecho y qué falta.

## ¿Por qué desde cero?

Llamar a `fit` y `predict` de una librería oculta justo lo importante. Implementar cada pieza a mano deja a la vista las decisiones de diseño: cómo se calculan las distancias, por qué hay que escalar las variables, cómo se resuelven los empates y cómo evaluar un modelo sin filtrar información del conjunto de test.

## Decisiones de diseño

- **Independiente del dataset:** `Dataset` solo contiene números y etiquetas. Cambiar de dataset implica cambiar la configuración de `CsvLoader`, no el algoritmo.
- **Sin fuga de datos (data leakage):** `Normalizer` calcula el mínimo y el máximo solo con el conjunto de entrenamiento y después los aplica a entrenamiento y test.
- **Con tests:** cada componente tiene pruebas unitarias (JUnit 5), incluidos casos límite como columnas constantes y parámetros inválidos.

## Estructura

```
knn-java/
├── data/                  # datasets (CSV)
├── src/main/java/knn/
│   ├── Dataset.java       # contenedor de variables + etiquetas
│   ├── CsvLoader.java     # lector de CSV configurable
│   ├── Normalizer.java    # escalado min-max, ajustado con el conjunto de entrenamiento
│   ├── KNN.java           # clasificador k-NN (distancia euclídea, voto por mayoría)
│   └── Main.java
└── src/test/java/knn/     # tests con JUnit 5
```

## Primeros pasos

### Requisitos

- JDK 21 o superior
- Maven 3.9 o superior

### Compilar, probar y ejecutar

```bash
git clone https://github.com/blancaferreiro/knn-java.git
cd knn-java
mvn test
```

Para ejecutar el programa (hazlo desde la raíz del proyecto, porque la ruta del dataset es relativa):

```bash
mvn compile
java -cp target/classes knn.Main
```

Por ahora, `Main` carga el dataset Wine y muestra sus dimensiones.

### Cargar un dataset

El cargador se configura con el separador, si hay cabecera, la columna de la etiqueta y las columnas que se ignoran:

```java
// Wine: separado por comas, sin cabecera, etiqueta en la columna 0, sin columnas ignoradas
Dataset wine = new CsvLoader(",", false, 0, Set.of()).load(Path.of("data/wine.csv"));
```

## Fases y hoja de ruta

El proyecto avanza en tres fases. Cada una añade un dataset más difícil para poner a prueba el diseño.

### Fase 1: Wine (en curso)

Dataset [Wine](https://archive.ics.uci.edu/dataset/109/wine) de UCI: 178 muestras, 13 variables numéricas, clasificación multiclase. Sirve para construir y validar el flujo completo sobre datos limpios.

- [x] Cargador de CSV configurable, con tests
- [x] Normalizador min-max ajustado solo con el conjunto de entrenamiento
- [ ] Clasificador k-NN (distancia, vecinos más cercanos, voto por mayoría)
- [ ] Evaluador (partición entrenamiento/test, precisión, matriz de confusión)
- [ ] Experimentos: precisión para distintos valores de k, con y sin normalización, frente a un clasificador base de clase mayoritaria

### Fase 2: Breast Cancer Wisconsin (planificada)

Mismo algoritmo, otro fichero: clases binarias, una columna de identificador que hay que ignorar y cabecera. Solo cambia la configuración del cargador. Es la prueba de que el algoritmo no depende del tema.

- [ ] Cargar y clasificar el dataset sin modificar el algoritmo
- [ ] Validación cruzada

### Fase 3: abandono de clientes, *churn* (planificada)

La fase más difícil. Los datos de churn suelen mezclar columnas numéricas y de texto, tener valores ausentes y clases desbalanceadas, de modo que la precisión por sí sola engaña. El dataset aún no está elegido.

- [ ] Hacer que k-NN pueda usar columnas de texto: como la distancia solo opera con números, se codificarán como columnas binarias (one-hot)
- [ ] Tratamiento de valores ausentes
- [ ] Métricas para clases desbalanceadas: precisión (*precision*), exhaustividad (*recall*) y F1, junto con la matriz de confusión

## Resultados

Se publicarán cuando termine el evaluador, empezando por Wine.

## Fuentes de datos

- **Wine:** UCI Machine Learning Repository, [Wine dataset](https://archive.ics.uci.edu/dataset/109/wine). Consulta la página del dataset para ver la licencia y cómo citarlo.

Los datasets de las otras fases se añadirán aquí cuando se incorporen.

## Autora

Blanca Ferreiro ([@blancaferreiro](https://github.com/blancaferreiro))