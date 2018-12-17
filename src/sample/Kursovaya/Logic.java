package sample.Kursovaya;

import javafx.scene.chart.*;
import sample.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Logic extends Line {

    public Logic() {
        super(0);
    }

    LineChart<Double, Double> getDifference(LineChart<Double, Double> lch) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        for (int i = 0; i < lch.getData().get(0).getData().size() - 1; i++) {
            series.getData().add(new XYChart.Data<>((double)i,
                    lch.getData().get(0).getData().get(i).getYValue() -
                            lch.getData().get(0).getData().get(i+1).getYValue()));
        }

        return lch;
    }

    LineChart<Number, Number> createLine(String firstData, String secondData, BarChart<String, Number> bch) {
        Number firstValue = 0, secondValue = 0;

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lch = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        int fisrtIndex = 0, secondIndex = 0;

        for (int i = 0; i < bch.getData().get(0).getData().size(); i++) {
            if (bch.getData().get(0).getData().get(i).getXValue() == firstData) {
                firstValue = bch.getData().get(0).getData().get(i).getYValue();
                fisrtIndex = i;
            }
            if (bch.getData().get(0).getData().get(i).getXValue() == secondData) {
                firstValue = bch.getData().get(0).getData().get(i).getYValue();
                secondIndex = i;
            }
        }

        double k = ((Double) secondValue - (Double) firstValue) / (secondIndex - fisrtIndex);
        double b = (secondIndex * (Double) firstValue - fisrtIndex * (Double) secondValue) / (secondIndex - fisrtIndex);

        double res;

        for (int i = 0; i < bch.getData().get(0).getData().size(); i++) {
            series.getData().add(new XYChart.Data<>(i, k * i + b));
        }

        lch.getData().add(series);

        return lch;
    }

    BarChart<String, Number> createBarChart(String csvpath) throws FileNotFoundException {
        XYChart.Series bseries1 = new XYChart.Series<>();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Dollars");
        BarChart<String, Number> bch = new BarChart<>(xAxis, yAxis);

        Scanner scanner = new Scanner(new File(csvpath));
        scanner.useDelimiter("(;)|(\\r\\n)");
        ArrayList<String> data = new ArrayList<String>();
        while (scanner.hasNext()) {
            data.add(scanner.next());
            //System.out.print(scanner.next()+"|");
        }
        scanner.close();

        for (int i = 4; i + 24 <= data.size() - 1; i += 6) {
            bseries1.getData().add(new XYChart.Data<>(data.get(i + 2), data.get(i + 6)));
        }

//        for (int i = 0; i < N; i++)
//        {
//            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf(rand.nextInt(highvalue)+lowvalue)));
//        }
        //series1.setName(csvpath);
        //printLine(bch, series1, series2);

        bch.getData().add(bseries1);
        return bch;
    }
}
