package knn;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class NormalizerTest {
    
    @Test 
    void scalesEachColumnToZeroOne(){
        double[][] train = {{0, 10}, {5, 20}, {10, 30}};
        Normalizer normalizer = new Normalizer();

        normalizer.fit(train);
        double[][] result = normalizer.transform(train);

        assertArrayEquals(new double[] {0, 0}, result[0], 1e-9);
        assertArrayEquals(new double[] {0.5, 0.5}, result[1], 1e-9);
        assertArrayEquals(new double[] {1, 1}, result[2], 1e-9);    
    }

    @Test
    void constantColumnBecomesZero(){
        double[][] train = {{3, 1}, {3, 2}, {3, 3}};
        Normalizer normalizer = new Normalizer();

        normalizer.fit(train);
        double[][] result = normalizer.transform(train);
        
        assertArrayEquals(new double[] {0, 0}, result[0], 1e-9);
        assertArrayEquals(new double[] {0, 0.5}, result[1], 1e-9);
        assertArrayEquals(new double[] {0, 1}, result[2], 1e-9);  
    }

    @Test 
    void transformDoesNotModifyInput(){
        double[][] train = { {0, 10}, {5, 20}, {10, 30} };
        Normalizer normalizer = new Normalizer();

        normalizer.fit(train);
        normalizer.transform(train);
        
        assertArrayEquals(new double[] {5, 20}, train[1], 1e-9);
        assertArrayEquals(new double[] {0, 10}, train[0], 1e-9);
        assertArrayEquals(new double[] {10, 30}, train[2], 1e-9);
    }
}
