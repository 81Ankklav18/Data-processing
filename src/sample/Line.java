package sample;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.math.RoundingMode;
import java.util.Random;

public class Line {
    protected int N;
    private XYChart.Series<Double, Double> series;

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

    public void printLineFlB(LineChart<Float, Byte> ch, XYChart.Series<Float, Byte> ser) {
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

    public void printLine(BarChart<Double, Double> ch, XYChart.Series<Double, Double> ser,
                          XYChart.Series<Double, Double> ser2) {
        ch.getData().clear();
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

    public XYChart.Series<Double, Double> CrossCorrelation(XYChart.Series<Double, Double> series1, XYChart.Series<Double, Double> series2) {
        /*double avg1 = 0;
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
        }*/

        XYChart.Series<Double, Double> series3 = new XYChart.Series<>();

        for (int i = series1.getData().size(); i > 0; i--){
            series3.getData().add(new XYChart.Data<>(Double.valueOf(series3.getData().size()-i), series1.getData().get(i-1).getYValue()));
        }

        return Convalution(series2, series3);
    }

    public XYChart.Series<Double, Double> SelfCorrelation(XYChart.Series<Double, Double> series1, XYChart.Series<Double, Double> series2) {
        XYChart.Series<Double, Double> series3 = new XYChart.Series<>();
        double avgs1 = 0.d;
        double avgs2 = 0.d;

        for (int i = 0; i > series1.getData().size(); i++){
            avgs1 += series1.getData().get(i).getYValue();
            avgs2 += series2.getData().get(i).getYValue();
        }
        avgs1 /= series1.getData().size();
        avgs2 /= series2.getData().size();

        double corell = 0.d;
        for (int i = 0; i < series1.getData().size() - 1; i++){
            corell = 0.d;
            for (int j = 0; j < series1.getData().size() - 1 - i; j++){
                corell += (series1.getData().get(j).getYValue() - avgs1) * (series2.getData().get(j+i).getYValue() - avgs2);
            }
            corell /= series1.getData().size();
            series3.getData().add(new XYChart.Data<>((double)i, corell));
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

    public XYChart.Series<Double, Double> Furie(XYChart.Series<Double, Double> series1) {
        double[] re = new double[N];
        double[] im = new double[N];

        series = new XYChart.Series<>();

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                re[i] += series1.getData().get(j).getYValue() * Math.cos((2 * Math.PI * i * j) / N);
                im[i] += series1.getData().get(j).getYValue() * Math.sin((2 * Math.PI * i * j) / N);
            }
            re[i] /= N;
            im[i] /= N;
        }

        for (int i = 0; i < N ; i++)
        {
            series.getData().add(new XYChart.Data<>((double) i, Math.sqrt(Math.pow(re[i], 2) + Math.pow(im[i], 2))));
        }
        return series;
    }

    public XYChart.Series<Double, Double> ReversFurie(XYChart.Series<Double, Double> series1) {
        double[] re = new double[N];
        double[] im = new double[N];


        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                re[i] += series1.getData().get(j).getYValue() * Math.cos((2 * Math.PI * i * j) / N);
                im[i] += series1.getData().get(j).getYValue() * Math.sin((2 * Math.PI * i * j) / N);
            }
            re[i] /= N;
            im[i] /= N;
        }

        for (int i = 0; i < N; i++)
        {
            series.getData().add(new XYChart.Data<>((double) i, re[i]+im[i]));
        }
        XYChart.Series<Double, Double> series3;
        series3 = new XYChart.Series<>();
        double[] re1 = new double[N];
        double[] im1 = new double[N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                re1[i] += series.getData().get(j).getYValue() * Math.cos((2 * Math.PI * i * j) / N);
                im1[i] += series.getData().get(j).getYValue() * Math.sin((2 * Math.PI * i * j) / N);
            }
        }

        for (int i = 0; i < N; i++)
        {
            series3.getData().add(new XYChart.Data<>((double) i, re1[i] + im1[i]));
        }
        return series3;
    }

    XYChart.Series<Double, Double> Convalution(XYChart.Series<Double, Double> peek, XYChart.Series<Double, Double> h) {
        series = new XYChart.Series<Double, Double>();
        double resault = 0;
        int N = peek.getData().size();
        int M = h.getData().size();

        for (int i = 0; i < N + M - 1; i++) {
            for (int j = 0; j < M; j++) {
                if (i - j > 0 && i - j < N) {
                    resault += peek.getData().get(i - j).getYValue() * h.getData().get(j).getYValue();
                }
            }
            series.getData().add(new XYChart.Data<>((double) i, resault));
            resault = 0;
        }
        return series;
    }

    public XYChart.Series<Double, Double> LPF(int fcut, double dt, int m) {
        series = new XYChart.Series<>();
        double d[] = {0.35_57_70_19d, 0.24_36_98_30d, 0.07_21_14_97d, 0.00_63_01_65d};
        double lpw[] = new double[2 * m + 1];

        //прямоугольная
        double arg = 2 * fcut * dt;
        lpw[0] = arg;
        arg *= Math.PI;

        for (int i = 1; i <= m; i++) {
            lpw[i] = (Math.sin(arg * i)) / (Math.PI * i);
        }

        //трапеция
        lpw[m] /= 2;

        //окно Поттера p310
        double sumg = lpw[0];
        double sum;
        for (int i = 1; i <= m; i++) {
            sum = d[0];
            arg = (Math.PI * i) / m;
            for (int k = 1; k <= 3; k++) {
                sum += 2 * d[k] * Math.cos(arg * k);
            }
            lpw[i] *= sum;
            sumg += 2 * lpw[i];
        }

        for (int i = 0; i <= m; i++) {
            lpw[i] /= sumg;
        }

        for (int i = -m; i <= m; i++) {
            series.getData().add(new XYChart.Data<>((double) i, lpw[Math.abs(i)]));
        }

        return series;
    }

    public XYChart.Series<Double, Double> HPF(XYChart.Series<Double, Double> series3, int fcut, double dt, int m) {
        XYChart.Series<Double, Double> series2 = new XYChart.Series<Double, Double>();
        double resault = 0;
        N = m;

        for (int i = 0; i <= 2 * m; i++) {
            if (i == m) {
                series2.getData().add(new XYChart.Data<>((double) i, 1 - series3.getData().get(i).getYValue()));
            } else {
                series2.getData().add(new XYChart.Data<>((double) i, -series3.getData().get(i).getYValue()));
            }
        }

        return series2;
    }

    public XYChart.Series<Double, Double> BPF(XYChart.Series<Double, Double> series3,
                                              XYChart.Series<Double, Double> series4,
                                              int fcut, double dt, int m) {
        XYChart.Series<Double, Double> series2 = new XYChart.Series<Double, Double>();

        double resault = 0;
        N = m;

        for (int i = 0; i <= 2 * m; i++) {

            series2.getData().add(new XYChart.Data<>((double) i,
                    series4.getData().get(i).getYValue() - series3.getData().get(i).getYValue()));
        }

        return series2;
    }

    public XYChart.Series<Double, Double> BSF(XYChart.Series<Double, Double> series3,
                                              XYChart.Series<Double, Double> series4,
                                              int fcut, double dt, int m) {
        XYChart.Series<Double, Double> series2 = new XYChart.Series<Double, Double>();

        double resault = 0;
        N = m;

        for (int i = 0; i <= 2 * m; i++) {
            if (i == m) {
                series2.getData().add(new XYChart.Data<>((double) i,
                        1 + series3.getData().get(i).getYValue() - series4.getData().get(i).getYValue()));
            } else {
                series2.getData().add(new XYChart.Data<>((double) i,
                        series3.getData().get(i).getYValue() - series4.getData().get(i).getYValue()));
            }
        }

        return series2;
    }

    public XYChart.Series<Float, Float> FurieFl(XYChart.Series<Float, Float> series1) {
        float[] re = new float[N];
        float[] im = new float[N];

        XYChart.Series<Float, Float> series2;
        series2 = new XYChart.Series<>();
        float val;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                re[i] += series1.getData().get(j).getYValue() * Math.cos((2 * Math.PI * i * j) / N);
                im[i] += series1.getData().get(j).getYValue() * Math.sin((2 * Math.PI * i * j) / N);
            }
            re[i] /= N;
            im[i] /= N;
        }

        for (int i = 0; i < N; i++)
        {
            series2.getData().add(new XYChart.Data<>((float) i, (float)((Math.sqrt(Math.pow(re[i], 2) + Math.pow(im[i], 2))))));
        }
        return series2;
    }

    public XYChart.Series<Float, Float> ReversFurieFl(XYChart.Series<Float, Float> series1) {
        float[] re = new float[N];
        float[] im = new float[N];

        XYChart.Series<Float, Float> series2;
        series2 = new XYChart.Series<>();
        float val;

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                re[i] += series1.getData().get(j).getYValue() * Math.cos((2 * Math.PI * i * j) / N);
                im[i] += series1.getData().get(j).getYValue() * Math.sin((2 * Math.PI * i * j) / N);
            }
            re[i] /= N;
            im[i] /= N;
        }

        for (int i = 0; i < N; i++)
        {
            series2.getData().add(new XYChart.Data<>((float) i, re[i]+im[i]));
        }
        XYChart.Series<Float, Float> series3;
        series3 = new XYChart.Series<>();
        float[] re1 = new float[N];
        float[] im1 = new float[N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                re1[i] += series2.getData().get(j).getYValue() * Math.cos((2 * Math.PI * i * j) / N);
                im1[i] += series2.getData().get(j).getYValue() * Math.sin((2 * Math.PI * i * j) / N);
            }
        }

        for (int i = 0; i < N; i++)
        {
            series3.getData().add(new XYChart.Data<>((float) i, (float)re1[i] + im1[i]));
        }
        return series3;
    }

    public double max(XYChart.Series<Double, Double> series1){
        double max = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < series1.getData().size(); i++){
            if (series1.getData().get(i).getYValue() > max){
                max = series1.getData().get(i).getYValue();
            }
        }

        return max;
    }

    public double min(XYChart.Series<Double, Double> series1){
        double min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < series1.getData().size(); i++){
            if (series1.getData().get(i).getYValue() < min){
                min = series1.getData().get(i).getYValue();
            }
        }

        return min;
    }

    public double average(XYChart.Series<Double, Double> series1){
        double average = 0.d;

        for (int i = 0; i < series1.getData().size(); i++){
            average += series1.getData().get(i).getYValue();
        }
        average /= series1.getData().size();

        return average;
    }

    public double dispersion(XYChart.Series<Double, Double> series1){
        double dispersion = 0.d;
        double avg = average(series1);

        for (int i = 0; i < series1.getData().size(); i++){
            dispersion += Math.pow(series1.getData().get(i).getYValue() - avg, 2);
        }
        dispersion /= series1.getData().size();

        return dispersion;
    }

    public double averagesquare(XYChart.Series<Double, Double> series1){
        double averagesquare = 0.d;

        for (int i = 0; i < series1.getData().size(); i++){
            averagesquare += Math.pow(series1.getData().get(i).getYValue(), 2);
        }
        averagesquare /= series1.getData().size();

        return averagesquare;
    }

    public double standartdeviation(XYChart.Series<Double, Double> series1){
        return Math.sqrt(dispersion(series1));
    }

    public double averagesquaredeviation(XYChart.Series<Double, Double> series1){
        return Math.sqrt(averagesquare(series1));
    }

    public double centralmoment3(XYChart.Series<Double, Double> series1){
        double centralmoment3 = 0.d;
        double avg = average(series1);

        for (int i = 0; i < series1.getData().size(); i++){
            centralmoment3 += Math.pow(series1.getData().get(i).getYValue() - avg, 3);
        }
        centralmoment3 /= series1.getData().size();

        return centralmoment3;
    }

    public double coeffasimmetria(XYChart.Series<Double, Double> series1){
        return (centralmoment3(series1))/(Math.pow(standartdeviation(series1), 3));
    }

    public double centralmoment4(XYChart.Series<Double, Double> series1){
        double centralmoment4 = 0.d;
        double avg = average(series1);
        int result = 0;

        for (int i = 0; i < series1.getData().size(); i++){
            centralmoment4 += Math.pow(series1.getData().get(i).getYValue() - avg, 4);
        }
        centralmoment4 /= series1.getData().size();

        return centralmoment4;
    }

    public double coeffexcess(XYChart.Series<Double, Double> series1){
        return (centralmoment4(series1))/(Math.pow(standartdeviation(series1), 4));
    }
}
