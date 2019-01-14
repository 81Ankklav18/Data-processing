package sample.Kursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CourseProject extends Logic implements Initializable {
    private String csvpath;

    public void initialize() {/*...*/}

    @FXML
    BarChart<String, Number> bchart;
    @FXML
    LineChart<Double, Double> chart1;
    @FXML
    ComboBox<String> combo;
    @FXML
    Label comboLabel;
    @FXML
    TextField tfWindow;
    @FXML
    TextField tfF;
    @FXML
    TextField tfS;

    @FXML
    TextField itoa;
    @FXML
    TextField itob;
    @FXML
    TextField itoc;
    @FXML
    TextField itod;

    @FXML
    RadioButton NetflixRB;
    @FXML
    RadioButton GoogleRB;
    @FXML
    RadioButton AppleRB;
    @FXML
    RadioButton MicrosoftRB;
    @FXML
    RadioButton ItoRB;

    @FXML
    Label min;
    @FXML
    Label max;
    @FXML
    Label average;
    @FXML
    Label dispersion;
    @FXML
    Label averagesquare;
    @FXML
    Label standartdeviation;
    @FXML
    Label averagesquaredeviation;
    @FXML
    Label centralmoment3;
    @FXML
    Label coeffasimmetria;
    @FXML
    Label centralmoment4;
    @FXML
    Label coeffexcess;

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CourseProject.fxml"));
        stageLF.setTitle("Simple Graphics");
        stageLF.setScene(new Scene(root, 1000, 600));

        stageLF.show();
    }

    public void lineChartInit() {
        bchart.setVisible(false);
        chart1.getData().clear();
        chart1.setVisible(true);
        chart1.setCreateSymbols(false);
        chart1.setLegendVisible(false);
    }

    public void barChartInit() {
        chart1.setVisible(false);
        bchart.getData().clear();
        bchart.setVisible(true);
    }

    public void createBarChart(ActionEvent actionEvent) throws FileNotFoundException {
        barChartInit();
        bchart.getData().add(createBarChartSeries());
    }

    public void createLine(ActionEvent actionEvent) throws IOException {
        lineChartInit();
        chart1.getData().addAll(getSeries(csvpath), createLine(Integer.valueOf(tfF.getText()), Integer.valueOf(tfS.getText())));
    }

    public void Furie(ActionEvent actionEvent) throws IOException {
        lineChartInit();
        chart1.getData().add(printFurieCSV(csvpath));
    }

    public void Density(ActionEvent actionEvent) throws FileNotFoundException {
        barChartInit();
        bchart.getData().add(createDensity());
    }

    public void Difference(ActionEvent actionEvent) {
        lineChartInit();
        chart1.getData().add(getDifference());
    }

    public void ComboAction(ActionEvent actionEvent) throws FileNotFoundException {
        comboLabel.setText("Selected" + combo.getValue());
        csvpath = combo.getValue();
        setCVSPath(csvpath);
    }

    public void Hatch(ActionEvent actionEvent) {
        lineChartInit();
        chart1.getData().addAll(createHatch());
    }

    public void Window(ActionEvent actionEvent) {
        lineChartInit();
        chart1.getData().add(createAverageWindow(Integer.valueOf(tfWindow.getText())));
    }

    public void ItoProcess(ActionEvent actionEvent) throws IOException {
        lineChartInit();
        chart1.getData().add(ItoProcess(size()
                , Double.valueOf(itoa.getText())
                , Double.valueOf(itoc.getText())
                , Double.valueOf(itob.getText())
                , Double.valueOf(itod.getText())));
    }

    public void CrossCorellation(ActionEvent actionEvent) throws IOException {
        lineChartInit();

        if (NetflixRB.isSelected()) {
            chart1.getData().add(CrossCorrelation(getSeries("src/netflix5.csv"), getSeries(csvpath)));
        }
        if (GoogleRB.isSelected()) {
            chart1.getData().add(CrossCorrelation(getSeries("src/google5.csv"), getSeries(csvpath)));
        }
        if (AppleRB.isSelected()) {
            chart1.getData().add(CrossCorrelation(getSeries("src/apple5.csv"), getSeries(csvpath)));
        }
        if (MicrosoftRB.isSelected()) {
            chart1.getData().add(CrossCorrelation(getSeries("src/microsoft5.csv"), getSeries(csvpath)));
        }
        if (ItoRB.isSelected()) {
            chart1.getData().add(SelfCorrelation(
                    ItoProcess(size()
                            , Double.valueOf(itoa.getText())
                            , Double.valueOf(itoc.getText())
                            , Double.valueOf(itob.getText())
                            , Double.valueOf(itod.getText()))
                    , getSeries(csvpath)));
        }
    }

    public void Statistics(ActionEvent actionEvent) throws IOException {
        min.setText("Минимум:" + min(getSeries(csvpath)));
        max.setText("Максимум:" + max(getSeries(csvpath)));
        average.setText("Среднее значение:" + average(getSeries(csvpath)));
        dispersion.setText("Дисперсия:" + dispersion(getSeries(csvpath)));
        averagesquare.setText("Средний квадрат:" + averagesquare(getSeries(csvpath)));
        standartdeviation.setText("Стандартное отклонение:" + standartdeviation(getSeries(csvpath)));
        averagesquaredeviation.setText("Среднеквадратичное отклонение:" + averagesquaredeviation(getSeries(csvpath)));
        centralmoment3.setText("Центральный момент 3-го порядка:" + centralmoment3(getSeries(csvpath)));
        coeffasimmetria.setText("Коэффициент асимметрии:" + coeffasimmetria(getSeries(csvpath)));
        centralmoment4.setText("Центральный момент 4-го порядка:" + centralmoment4(getSeries(csvpath)));
        coeffexcess.setText("Коэффициент эксцесса:" + coeffexcess(getSeries(csvpath)));
    }

    public void AutoLine(ActionEvent actionEvent) throws IOException {
        lineChartInit();
        chart1.getData().addAll(getSeries(csvpath),
                createMinLine(Integer.valueOf(tfWindow.getText())),
                createMaxLine(Integer.valueOf(tfWindow.getText())));
    }
}
