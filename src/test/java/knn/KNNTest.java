package knn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class KNNTest {
    
    @Test 
    void euclideanDistanceOfTheThreeFourFiveTriangle(){
        double d = KNN.euclideanDistance(new double[] {0, 0}, new double[] {3, 4});
        assertEquals(5.0, d, 1e-9);
    }

    @Test 
    void distanceOfPointWithItselfIsZero(){
        double[] p = {1, 2, 3};
        double d = KNN.euclideanDistance(p, p);
        assertEquals(0.0, d, 1e-9);
    }

    @Test 
    void distanceIsSymmetric(){
        double[] a = {1, 2};
        double[] b = {4, 6};
        
        double ab = KNN.euclideanDistance(a, b);
        double ba = KNN.euclideanDistance(b, a);

        assertEquals(ab, ba, 1e-9);
    }


    @Test 
    void fitRejectsDifferentLengths(){
        KNN knn = new KNN(1);
        double[][] features = {{0, 0}, {1, 1}};
        String[] labels = {"A"};
        assertThrows(IllegalArgumentException.class, () -> knn.fit(features, labels));
    }

    @Test 
    void fitRejectsKLargerThanSamples(){
        KNN knn = new KNN(5);
        double[][] features = {{0, 0}, {1, 1}};
        String[] labels = {"A", "B"};
        // assertThrows comprueba que al ejecutar lo que va después de () ->, se lanza esa excepción
        assertThrows(IllegalArgumentException.class, () -> knn.fit(features, labels));
    }

    @Test 
    void (){
        KNN knn = new KNN(3);
        knn.fit(new double[][] {{0, 0}, {0, 1}, {5, 5}, {5, 6}}, new String[] {, "B"});
        knn.predict(new double[] {0, 0.5});
    }
}
