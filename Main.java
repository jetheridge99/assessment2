import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.text.Text;



public class Main extends Application {
    static int pointCount = 0;
    static int maxPoints = 70;
    static double[] xVal = new double[maxPoints];
    static  double[] yVal = new double[maxPoints];
    public static void main(String[] args) {

       pointCount = ConvexHull.loadPoints(xVal,yVal ,maxPoints);

        for (int i = 0; i<maxPoints; i++){
            System.out.println("xVal[" + xVal[i] + "], yVal[" + yVal[i] + "]");
        }

        if (ConvexHull.checkDuplicates(pointCount,xVal,yVal)){
            System.err.println("Two points are the same");
            System.exit(1);
        }
        ConvexHull.computeConvexHull(pointCount, xVal, yVal);
        Application.launch(args);
    }


        @SuppressWarnings("unchecked")
        public void start(Stage stage) {

        Pane root = new Pane();



            for(int i = 0; i < Main.pointCount+1;i++ ){
                Circle point = new Circle();
                Text text = new Text();
                point.setCenterX(xVal[i]*10);
                point.setCenterY(yVal[i]*6);
                point.setRadius(10);
                point.setFill(Color.RED);
                text.setText(""+i+"");
                text.setX(xVal[i]*10);
                text.setY(yVal[i]*6);
                root.getChildren().addAll(point, text);
            }

            for (int i = pointCount;i>-1;i--){
                for (int j = pointCount; j > -1; j--){
                    int above = 0, below = 0;
                    Double m,c;
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
                       //Line
                        Line edge = new Line();
                        edge.setStartX(xVal[i]*10);
                        edge.setStartY(yVal[i]*6);
                        edge.setEndX(xVal[j]*10);
                        edge.setEndY(yVal[j]*6);
                        edge.setStroke(Color.DARKGREEN);
                        edge.setStrokeWidth(6.0);

                        root.getChildren().addAll(edge);

                    }

                }
            }


            for (int i = 0; i<pointCount;i++){
                Line path= new Line();
                path.setStartX(xVal[i]*10);
                path.setStartY(yVal[i]*6);
                path.setEndX(xVal[i+1]*10);
                path.setEndY(yVal[i+1]*6);
                path.setStroke(Color.LIGHTGREEN);
                path.setStrokeWidth(2.0);
                root.getChildren().addAll(path);
            }



            stage.setTitle("Convex Hull");

            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.setWidth(1500);
            stage.setHeight(800);
            stage.sizeToScene();
            stage.show();

        }

    }


