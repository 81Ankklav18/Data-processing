package sample.Kursovaya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import sample.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Logic extends ReadCSV implements Cloneable {
    private ArrayList<String> data;
    private String csvpath;

    Logic(String csvpath) throws FileNotFoundException {
        this.csvpath = csvpath;
        data = getData(csvpath);
    }

    /*Logic(String csvpath) throws FileNotFoundException {
        data = getData(csvpath);
    }*/

    Logic(ArrayList<String> arrayList) {
        data = arrayList;
    }

    Logic() {/*...*/}

    public XYChart.Series<Double, Double> getDifference() {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        for (int i = 4; i + 24 <= data.size() - 1; i += 6) {
            series.getData().add(new XYChart.Data<>((double) i,
                    Double.valueOf(data.get(i + 6)) -
                            Double.valueOf(data.get(i + 12))));
        }

        return series;
    }

    public XYChart.Series<Double, Double> createLine(int firstIndex, int secondIndex) throws FileNotFoundException {
        XYChart.Series<Double, Double> strseries = new XYChart.Series<>();
        for (int i = 4; i + 24 <= data.size() - 1; i += 6) {
            strseries.getData().add(new XYChart.Data<>((double)(i+2)/6, Double.valueOf(data.get(i + 6))));
        }

        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        double k = (strseries.getData().get(secondIndex).getYValue() - strseries.getData().get(firstIndex).getYValue()) / (secondIndex - firstIndex);
        double b = (secondIndex * strseries.getData().get(firstIndex).getYValue() - firstIndex * strseries.getData().get(secondIndex).getYValue()) / (secondIndex - firstIndex);

        double res;

        for (int i = 0; i < strseries.getData().size() + 20; i++) {
            series.getData().add(new XYChart.Data<>((double) i, k * i + b));
        }

        //lch.getData().add(series);

        return series;
    }

    public XYChart.Series<Double, Double> createMinLine(int window) throws FileNotFoundException {
        XYChart.Series<String, Number> strseries = new XYChart.Series<>();

        for (int i = 4; i + 24 <= data.size() - 1; i += 6) {
            strseries.getData().add(new XYChart.Data<>(data.get(i + 2), Double.valueOf(data.get(i + 6))));
        }

        double firstValue = 0.d, secondValue = 0.d;

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lch = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        double currVal;
        double min = Double.POSITIVE_INFINITY;
        double fisrtIndex = 0.d, secondIndex = 0.d;
        for (int i = 0; i < strseries.getData().size(); i++) {
            currVal = strseries.getData().get(i).getYValue().doubleValue();
            if (currVal < min) {
                firstValue = strseries.getData().get(i).getYValue().doubleValue();
                fisrtIndex = i;
                min = firstValue;
            }
        }
        min = Double.POSITIVE_INFINITY;
        for (int i = (int)fisrtIndex + window; i < strseries.getData().size(); i++) {
            currVal = strseries.getData().get(i).getYValue().doubleValue();
            if (currVal < min) {
                secondValue = strseries.getData().get(i).getYValue().doubleValue();
                secondIndex = i;
                min = secondValue;
            }
        }

        double k = (secondValue - firstValue) / (secondIndex - fisrtIndex);
        double b = (secondIndex * firstValue - fisrtIndex * secondValue) / (secondIndex - fisrtIndex);

        for (int i = 0; i < strseries.getData().size() + 20; i++) {
            series.getData().add(new XYChart.Data<>((double) i, k * i + b));
        }

        //lch.getData().add(series);

        return series;
    }

    public XYChart.Series<Double, Double> createMaxLine(int window) throws FileNotFoundException {
        XYChart.Series<String, Number> strseries = new XYChart.Series<>();

        for (int i = 4; i + 24 <= data.size() - 1; i += 6) {
            strseries.getData().add(new XYChart.Data<>(data.get(i + 2), Double.valueOf(data.get(i + 6))));
        }

        double firstValue = 0.d, secondValue = 0.d;

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lch = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        double currVal;
        double max = Double.NEGATIVE_INFINITY;
        double fisrtIndex = 0.d, secondIndex = 0.d;
        for (int i = 0; i < strseries.getData().size(); i++) {
            currVal = strseries.getData().get(i).getYValue().doubleValue();
            if (currVal > max) {
                firstValue = strseries.getData().get(i).getYValue().doubleValue();
                fisrtIndex = i;
                max = firstValue;
            }
        }
        max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < (int)fisrtIndex - window; i++) {
            currVal = strseries.getData().get(i).getYValue().doubleValue();
            if (currVal > max) {
                secondValue = strseries.getData().get(i).getYValue().doubleValue();
                secondIndex = i;
                max = secondValue;
            }
        }

        double k = (secondValue - firstValue) / (secondIndex - fisrtIndex);
        double b = (secondIndex * firstValue - fisrtIndex * secondValue) / (secondIndex - fisrtIndex);

        for (int i = 0; i < strseries.getData().size() + 20; i++) {
            series.getData().add(new XYChart.Data<>((double) i, k * i + b));
        }

        //lch.getData().add(series);

        return series;
    }

    public XYChart.Series<String, Number> createBarChartSeries() throws FileNotFoundException {
        XYChart.Series bseries1 = new XYChart.Series<>();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Dollars");
        BarChart<String, Number> bch = new BarChart<>(xAxis, yAxis);

        for (int i = 4; i + 24 <= data.size() - 1; i += 6) {
            bseries1.getData().add(new XYChart.Data<>(data.get(i + 2), Double.valueOf(data.get(i + 6))));
        }

//        for (int i = 0; i < N; i++)
//        {
//            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf(rand.nextInt(highvalue)+lowvalue)));
//        }
        //series1.setName(csvpath);
        //printLine(bch, series1, series2);

        //bch.getData().add(bseries1);
        return bseries1;
    }

    public XYChart.Series<String, Number> createDensity() throws FileNotFoundException {
        XYChart.Series<String, Number> bseries1 = new XYChart.Series<>();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Dollars");
        BarChart<String, Number> bch = new BarChart<>(xAxis, yAxis);

        for (int i = 4; i + 24 <= data.size() - 1; i += 6) {
            bseries1.getData().add(new XYChart.Data<>(
                    String.valueOf(Double.valueOf(data.get(i + 6)).intValue()),
                    Double.valueOf(data.get(i + 6))));
        }

        int currVal;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < bseries1.getData().size(); i++) {
            currVal = bseries1.getData().get(i).getYValue().intValue();
            if (currVal > max) {
                max = currVal;
            }
        }
        double[] counts = new double[max + 1];

        for (int i = 0; i < bseries1.getData().size(); i++) {
            counts[bseries1.getData().get(i).getYValue().intValue()]++;
        }

        for (int i = 0; i < bseries1.getData().size(); i++) {
            bseries1.getData().set(
                    i,
                    new XYChart.Data<>(bseries1.getData().get(i).getXValue(),
                            counts[bseries1.getData().get(i).getYValue().intValue()]));
        }
//        for (int i = 0; i < N; i++)
//        {
//            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf(rand.nextInt(highvalue)+lowvalue)));
//        }
        //series1.setName(csvpath);
        //printLine(bch, series1, series2);

        //bch.getData().add(bseries1);
        return bseries1;
    }

    public XYChart.Series<Double, Double> createAverageWindow(int window) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        XYChart.Series<Double, Double> series1 = new XYChart.Series<>();
        double avg;

        for (int i = 4; i + 24 <= data.size() - 1; i += 6) {
            series.getData().add(new XYChart.Data<>(
                    (double) i,
                    Double.valueOf(data.get(i + 6))));
        }

        for (int i = series.getData().size() - 1; i >= window; i--) {
            avg = 0.d;
            if (i > window - 1) {
                for (int j = i; j > i - window; j--) {
                    avg += series.getData().get(j).getYValue();
                }
                avg /= window;
                series1.getData().add(new XYChart.Data<Double, Double>((double) i, avg));
            }
        }

        return series1;
    }

    public ObservableList<XYChart.Series<Double, Double>> createHatch() {
        ObservableList<XYChart.Series<Double, Double>> chartData = FXCollections.<XYChart.Series<Double, Double>>observableArrayList();
        XYChart.Series<Double, Double> series1 = new XYChart.Series<>();
        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();
        XYChart.Series<Double, Double> series3;
        double diff = 0.d;

        for (int i = 4; i + 24 <= data.size() - 1; i += 6) {
            series1.getData().add(new XYChart.Data<Double, Double>(
                    (double) i,
                    Double.valueOf(data.get(i + 5))));
            series2.getData().add(new XYChart.Data<Double, Double>(
                    (double) i,
                    Double.valueOf(data.get(i + 4))));
        }

        for (int i = 0; i < series1.getData().size(); i++) {
            series3 = new XYChart.Series<>();
            series3.getData().addAll(
                    new XYChart.Data<Double, Double>(
                            (double) i,
                            series2.getData().get(i).getYValue()),
                    new XYChart.Data<Double, Double>(
                            (double) i,
                            series1.getData().get(i).getYValue()));
            chartData.add(series3);
        }

        return chartData;
    }

    public XYChart.Series<Double, Double> ItoProcess(int N, double a, double c, double b, double d) {
        XYChart.Series<Double, Double> series1 = new XYChart.Series<>();
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            double randval = c * rand.nextDouble();
            series1.getData().add(new XYChart.Data<Double, Double>(
                    (double) i
                    , (double) a * c + i * (Math.exp(b * i)) + (d * Math.sin(i / d)) * (randval))
            );
        }

        return series1;
    }

    int size() throws IOException {
        return getSeries(csvpath).getData().size();
    }

    void setCVSPath(String newCSVPath) throws FileNotFoundException {
        this.csvpath = newCSVPath;
        data = getData(csvpath);
    }
}
