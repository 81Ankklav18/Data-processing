package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    String s1 = "";
    String s2 = "";
    @FXML
    LineChart<Number, Number> chart1;
    @FXML
    LineChart<Number, Number> chart2;
    @FXML
    Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();

        final Random random = new Random();
        series1.setName("Series 1");
        series2.setName("Series 2");
        for (int i = 0; i < 10; i++) {
            series1.getData().add(new XYChart.Data<>(i, random.nextInt(100) + 100));
        }
        //chart1.getData().add(series1);

        int mid = 0;
        for (int i = 0; i < series1.getData().size(); i++)
        {
            mid += (int)series1.getData().get(i).getYValue();
        }
        mid /= 10;

        for (int i = 0; i < 10; i++) {
            series2.getData().add(new XYChart.Data<>(i, (int)series1.getData().get(i).getYValue() - mid));
        }
        chart1.getData().addAll(series1, series2);



        /*spike*/
        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        XYChart.Series<Number, Number> series4 = new XYChart.Series<>();
        XYChart.Series<Number, Number> series5 = new XYChart.Series<>();

        final Random random1 = new Random();
        series3.setName("Series 3");
        series4.setName("Series 4");
        series5.setName("Series 5");
        for (int i = 0; i < 20; i++) {
            series3.getData().add(new XYChart.Data<>(i, random1.nextInt(100) + 100));
        }
        //chart1.getData().add(series1);

        for (int i = 0; i < 20; i++) {
            series4.getData().add(new XYChart.Data<>(i, (int)series3.getData().get(i).getYValue()));
        }

        for (int i = 0; i < 6; i++) {
            int indx = random1.nextInt(19);
            series4.getData().remove(indx);
            series4.getData().add(indx, new XYChart.Data<>(indx, random1.nextInt(100) + random1.nextInt(1000)+201));
        }

        for (int i = 0; i < 20; i++) {
            series5.getData().add(new XYChart.Data<>(i, (int)series4.getData().get(i).getYValue()));
        }

        for (int i = 0; i < 20; i++)
        {
            if ((int)series5.getData().get(i).getYValue() > 200 && i != 0 && i != 9)
            {
                series5.getData().remove(i);
                series5.getData().add(i, new XYChart.Data<>(i, ((int)series5.getData().get(i-1).getYValue()+(int)series5.getData().get(i+1).getYValue())/2));
            }
        }

        chart2.getData().addAll(series3, series4, series5);

        /*spike*/

        series1.getNode().setOnMouseEntered(onMouseEnteredSeriesListener);
        //caption.setText(s1 + ", " + s2);

        series1.getNode().setOnMouseExited(onMouseExitedSeriesListener);
    }

    EventHandler<MouseEvent> onMouseEnteredSeriesListener =
            (MouseEvent event) -> {
                ((Node)(event.getSource())).setCursor(Cursor.HAND);

                s1 = Double.toString(event.getSceneX());
                s2 = Double.toString(event.getSceneY());
                label.setText(s1 + ", " + s2);

            };
    EventHandler<MouseEvent> onMouseExitedSeriesListener =
            (MouseEvent event) -> {
                ((Node)(event.getSource())).setCursor(Cursor.DEFAULT);
            };


}