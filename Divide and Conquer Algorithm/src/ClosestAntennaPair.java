import java.util.Arrays;

public class ClosestAntennaPair {

    private double closestDistance = Double.POSITIVE_INFINITY;
    private long counter = 0;

    public ClosestAntennaPair(Point2D[] aPoints, Point2D[] bPoints) {

        //Base Case
        int m = aPoints.length; // a-length
        int n = bPoints.length; // b-length

        if (((m == 0) || (n == 0))) return;

        // sort a by x
        Point2D[] aPointsSortedByX = new Point2D[m];
        for (int i = 0; i < m; i++) aPointsSortedByX[i] = aPoints[i];
        Arrays.sort(aPointsSortedByX, Point2D.Y_ORDER);
        Arrays.sort(aPointsSortedByX, Point2D.X_ORDER);


        // sort b by x
        Point2D[] bPointsSortedByX = new Point2D[n];
        for (int i = 0; i < n; i++) bPointsSortedByX[i] = bPoints[i];
        Arrays.sort(bPointsSortedByX, Point2D.Y_ORDER);
        Arrays.sort(bPointsSortedByX, Point2D.X_ORDER);

        // sort a by y
        Point2D[] aPointsSortedByY = new Point2D[m];
        for (int i = 0; i<m; i++){
            aPointsSortedByY[i] = aPointsSortedByX[i];
        }
        Point2D[] auxA = new Point2D[m];

        // sort b by y
        Point2D[] bPointsSortedByY = new Point2D[n];
        for (int i = 0; i<n; i++){
            bPointsSortedByY[i] = bPointsSortedByX[i];
        }
        Point2D[] auxB = new Point2D[n];

        // Recursive Step
        closest(aPointsSortedByX, bPointsSortedByX, aPointsSortedByY,bPointsSortedByY, auxA, auxB, 0, 0, m-1, n-1);
    }

    public double closest(Point2D[] aPointsSortedByX, Point2D[] bPointsSortedByX, Point2D[] aPointsSortedByY, Point2D[] bPointsSortedByY, Point2D[] auxA, Point2D[] auxB, int lowA, int lowB, int highA, int highB) {
        // please do not delete/modify the next line!
        counter++;

        // Insert your solution here and modify the return statement.

        // BaseCase
        if ((highA < lowA) || (highB < lowB)) {
            if (aPointsSortedByX.length != 0){
                Arrays.sort(aPointsSortedByY, lowA, highA+1, Point2D.Y_ORDER);
            }

            if (bPointsSortedByX.length != 0){
                Arrays.sort(bPointsSortedByY, lowB, highB+1, Point2D.Y_ORDER);
            }
            return Double.POSITIVE_INFINITY;
        }

        // sort by both (4 parameters)
         //brute force when a.length = 1

        if (highA == lowA){
            for (int i=lowB; i <=highB; i++){
                if (aPointsSortedByX[lowA].distanceTo(bPointsSortedByX[i]) < closestDistance){
                    closestDistance = aPointsSortedByX[lowA].distanceTo(bPointsSortedByX[i]);
                }
            }
            //sort by b
            Arrays.sort(bPointsSortedByY, lowB, highB+1, Point2D.Y_ORDER);
            return closestDistance;
        }

        // brute force when b.length = 1

        if (highB == lowB){
            for (int i=lowA; i<=highA; i++){
                if (bPointsSortedByX[lowB].distanceTo(aPointsSortedByX[i]) < closestDistance){
                    closestDistance = bPointsSortedByX[lowB].distanceTo(aPointsSortedByX[i]);
                }
            }
            // sort A
            Arrays.sort(aPointsSortedByY, lowA, highA+1, Point2D.Y_ORDER);
            return closestDistance;
        }

        // make a median for both A and B
        int mid = lowA + (highA - lowA) / 2;
        Point2D medianA = aPointsSortedByX[mid];
        int midB = lowB-1;

        // for loop or binary search to know which points are on the left or right
        // low b to high b
        for (int i=lowB; i<highB; i++){
            if (bPointsSortedByX[i].x() < medianA.x()){
                midB +=1;
            }
        }

        // Recursive Step
        double delta1 = closest(aPointsSortedByX, bPointsSortedByX, aPointsSortedByY, bPointsSortedByY, auxA, auxB, lowA, lowB, mid, midB);
        double delta2 = closest(aPointsSortedByX, bPointsSortedByX, aPointsSortedByY, bPointsSortedByY, auxA, auxB, mid+1, midB+1, highA, highB);
        double delta = Math.min(delta1,delta2);

        // Merge in A, and B
        merge(aPointsSortedByY, auxA, lowA, mid, highA);
        merge(bPointsSortedByY, auxB, lowB, midB, highB);

        // Points in range for A
        int s = 0;
        for (int i = lowA; i<=highA; i++){
            if (Math.abs(aPointsSortedByY[i].x() - medianA.x()) < delta){
                auxA[s++] = aPointsSortedByY[i];
            }
        }

        // Points in range for B
        int t = 0;
        for (int i = lowB; i<=highB; i++){
            if (Math.abs(bPointsSortedByY[i].x() - medianA.x()) < delta){
                auxB[t++] = bPointsSortedByY[i];
            }
        }

        // Compare
        for (int i =0; i<s; i++){
            for (int j=0; (j<t) && (auxB[j].y() - auxA[i].y() < delta); j++ ){
                double distance = auxB[j].distanceTo(auxA[i]);
                if (distance < delta){
                    delta = distance;
                    if (distance< closestDistance) closestDistance = delta;
                }
            }
        }
        if (delta<closestDistance){
            closestDistance = delta;
        }
        return delta;
    }

    public double distance() {
        return closestDistance;
    }

    public long getCounter() {
        return counter;
    }

    // stably merge a[low .. mid] with a[mid+1 ..high] using aux[low .. high]
    // precondition: a[low .. mid] and a[mid+1 .. high] are sorted subarrays, namely sorted by y coordinate
    // this is the same as in ClosestPair
    private static void merge(Point2D[] a, Point2D[] aux, int low, int mid, int high) {
        // copy to aux[]
        // note this wipes out any values that were previously in aux in the [low,high] range we're currently using

        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) a[k] = aux[j++];   // already finished with the low list ?  then dump the rest of high list
            else if (j > high) a[k] = aux[i++];   // already finished with the high list ?  then dump the rest of low list
            else if (aux[i].compareByY(aux[j]) < 0)
                a[k] = aux[i++]; // aux[i] should be in front of aux[j] ? position and increment the pointer
            else a[k] = aux[j++];
        }
    }
}
