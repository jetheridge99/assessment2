import java.util.Scanner;

public class ConvexHull {
    static public int loadPoints(double[] xVal, double[] yVal, int maxPoints){


        Scanner kbd = new Scanner(System.in);
        int i = 0;
        while(i<2*maxPoints){
        System.out.println("X value");
        xVal[i] = kbd.nextDouble();
        System.out.println("Y value");
        yVal[i] = kbd.nextDouble();
        if (xVal[i]<0 || yVal[i]<0) {
            return i-1;//-1 because otherwise the -ve value will be considered a point
        }
        i++;
        }
        System.out.println("Max input points reached");
        return i;
    }

    static boolean checkDuplicates(int pointCount, double xVal[], double yVal[]) {
        for (int i = pointCount; i >= 1; i--) {
            for (int j = 0; j < i; j++) {
            if (j!=i){
                if (xVal[i]==xVal[j] && yVal[i] == yVal[j]){
                    System.err.println("Two points are the same");
                    return true;
                }
            }
            }
        }
        return false;
    }

    static void computeConvexHull(int pointCount, double xVal[], double yVal[])
    {
        double m, c;
        for (int i = pointCount;i>0;i--){
            for (int j = pointCount; j > 0; j--){
                int above = 0, below = 0;
                m = (yVal[j]-yVal[i])/(xVal[j]-xVal[i]);
                if (m == Double.POSITIVE_INFINITY || m == Double.NEGATIVE_INFINITY){
                    for (int k = pointCount; k>-1;k--){//between pointCount and 0
                        //line x = c, x[i] and x[j] are the same values
                        //above:
                        if (xVal[i] < xVal[k]){
                            above++;
                        }
                        //below:
                        else if (xVal[i] > xVal[k]){
                           below++;
                        }
                    }
                }
                else{
                    for (int k = pointCount;k>-1;k--){//between pointCount and 0
                        c = yVal[i] - (m*xVal[i]);
                        //above:
                        if (yVal[k] > (m*xVal[k] + c)){
                            above++;
                        }
                        //below:
                        else if (yVal[k] < (m*xVal[k] + c)){
                            below++;
                        }
                    }
                }

                if (above>0 && below ==0 || above == 0 && below>0){
                    System.out.println("edge between (" + xVal[i] + ", " + yVal[i] + ") and (" + xVal[i] + ", " + yVal[i] + ") is on the convex hull");

                }

            }
        }
    }
}
