package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.Random;

public class Line {
    protected int N;

    Random random = new Random();

    public Line(int N) {
        this.N = N;
    }

    //метод отрисовки графиков
    public void printLine(LineChart<Double, Double> ch, XYChart.Series<Double, Double> ser) {
        ch.getData().clear();
        ch.setCreateSymbols(false);
        ch.getData().add(ser);
    }

    public void printLineFl(LineChart<Float, Float> ch, XYChart.Series<Float, Float> ser) {
        ch.getData().clear();
        ch.setCreateSymbols(false);
        ch.getData().add(ser);
    }

    //перегрузка; метода отрисовки 2х графиков
    public void printLine(LineChart<Double, Double> ch, XYChart.Series<Double, Double> ser,
                          XYChart.Series<Double, Double> ser2) {
        ch.getData().clear();
        ch.setCreateSymbols(false);
        ch.getData().addAll(ser, ser2);
    }

    //перегрузка; метод отрисовки 3х графиков
    public void printLine(LineChart<Double, Double> ch, XYChart.Series<Double, Double> ser,
                          XYChart.Series<Double, Double> ser2,
                          XYChart.Series<Double, Double> ser3) {
        ch.getData().clear();
        ch.setCreateSymbols(false);
        ch.getData().addAll(ser, ser2, ser3);
    }

    //перегрузка; метод отрисовки 4х графиков
    public void printLine(LineChart<Double, Double> ch, XYChart.Series<Double, Double> ser,
                          XYChart.Series<Double, Double> ser2,
                          XYChart.Series<Double, Double> ser3,
                          XYChart.Series<Double, Double> ser4) {
        ch.getData().clear();
        ch.setCreateSymbols(false);
        ch.getData().addAll(ser, ser2, ser3, ser4);
    }

    public static XYChart.Series<Double, Double> operationSum(XYChart.Series<Double, Double> series1,
                                                              XYChart.Series<Double, Double> series2) {
        for (int i = 0; i < series1.getData().size(); i++) {
            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), series1.getData().get(i).getYValue() +
                    series2.getData().get(i).getYValue()));
        }

        return series1;
    }

    public XYChart.Series<Double, Double> shift(XYChart.Series<Double, Double> series1, int shift) {
        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();

        for (int i = 0; i < N; i++) {
            series2.getData().add(new XYChart.Data<>(Double.valueOf(i), series1.getData().get(i).getYValue() + shift));
        }

        return series2;
    }

    public XYChart.Series<Double, Double> shift(XYChart.Series<Double, Double> series1, int shift, int size) {
        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();

        for (int i = 0; i < size; i++) {
            series2.getData().add(new XYChart.Data<>(Double.valueOf(i), series1.getData().get(i).getYValue() + shift));
        }

        return series2;
    }

    public XYChart.Series<Double, Double> spike(XYChart.Series<Double, Double> series1, int countOfPoints, int difference) {
        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();
        double max = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < N; i++) {
            if (max < series1.getData().get(i).getYValue()) {
                max = series1.getData().get(i).getYValue();
            }
            series2.getData().add(new XYChart.Data<>(Double.valueOf(i), series1.getData().get(i).getYValue()));
        }

        int index = 0;
        double dif = 0;
        for (int i = 0; i < countOfPoints; i++) {
            index = random.nextInt(N);
            dif = max + difference;
            //series2.getData().remove(index);
            series2.getData().set(index, new XYChart.Data<>(Double.valueOf(index),
                    series1.getData().get(index).getYValue() + (random.nextInt() * 2 - 1) * dif));
        }

        return series2;
    }

    public XYChart.Series<Double, Double> AutoCorrelation(XYChart.Series<Double, Double> series1) {
        double avg = 0;

        for (int i = 0; i < N; i++) {
            avg += series1.getData().get(i).getYValue();
        }
        avg /= N;

        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();
        double val = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - i; j++) {
                val += ((series1.getData().get(j).getYValue() - avg) * (series1.getData().get(i + j).getYValue() - avg)) /
                        ((series1.getData().get(j).getYValue() - avg) * (series1.getData().get(j).getYValue() - avg));
            }
            series2.getData().add(new XYChart.Data<>(Double.valueOf(i), val));
        }

        return series2;
    }

    public XYChart.Series<Double, Double> AutoCov(XYChart.Series<Double, Double> series1) {
        double avg = 0;

        for (int i = 0; i < N; i++) {
            avg += series1.getData().get(i).getYValue();
        }
        avg /= N;

        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();
        double val = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - i; j++) {
                val += ((series1.getData().get(j).getYValue() - avg) * (series1.getData().get(i + j).getYValue() - avg)) / N;
            }
            series2.getData().add(new XYChart.Data<>(Double.valueOf(i), val));
        }

        return series2;
    }

    public XYChart.Series<Double, Double> MutualCorrelation(XYChart.Series<Double, Double> series1, XYChart.Series<Double, Double> series2) {
        double avg1 = 0;
        double avg2 = 0;

        for (int i = 0; i < N; i++) {
            avg1 += series1.getData().get(i).getYValue();
            avg2 += series2.getData().get(i).getYValue();
        }
        avg1 /= N;
        avg2 /= N;

        XYChart.Series<Double, Double> series3 = new XYChart.Series<>();
        double val = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - i; j++) {
                val += ((series1.getData().get(j).getYValue() - avg1) * (series2.getData().get(i + j).getYValue() - avg2)) / N;
            }
            series3.getData().add(new XYChart.Data<>(Double.valueOf(i), val));
        }

        return series3;
    }

    public XYChart.Series<Double, Double> AntiShift(XYChart.Series<Double, Double> series1) {
        double avg = 0;

        for (int i = 0; i < N; i++) {
            avg += series1.getData().get(i).getYValue();
        }
        avg /= N;

        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();

        for (int i = 0; i < N; i++) {
            series2.getData().add(new XYChart.Data<>(Double.valueOf(i), series1.getData().get(i).getYValue() - avg));
        }

        return series2;
    }

    public XYChart.Series<Double, Double> AntiSpike(XYChart.Series<Double, Double> series1, double k, double b) {
        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();

        for (int i = 0; i < N; i++)
        {
            series2.getData().add(i, new XYChart.Data<>(Double.valueOf(i),
                    (series1.getData().get(i).getYValue())));
        }

        for (int i = 1; i < N-1; i++) {
            if (Math.abs(series2.getData().get(i).getYValue()) > Math.abs(series1.getData().get(N-1).getYValue() * k + b)) {
                series2.getData().set(i, new XYChart.Data<>(Double.valueOf(i),
                        (series2.getData().get(i - 1).getYValue() + series2.getData().get(i+1).getYValue()) / 2));
            }
        }
        return series2;
    }
}
