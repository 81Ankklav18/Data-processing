package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Kursovaya.ReadCSV;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    @FXML
    Button button = new Button();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void show() throws IOException {
        Stage stageMenu = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        stageMenu.setTitle("Simple Graphics");

        stageMenu.setScene(new Scene(root, 600, 600));

        stageMenu.show();
    }

    public void newLFWindow(ActionEvent actionEvent) throws IOException {
        LinearFunction lf = new LinearFunction();
        lf.show();
    }

    public void newJRWindow(ActionEvent actionEvent) throws IOException {
        JavaRandom jr = new JavaRandom();
        jr.show();
    }

    public void newRDWindow(ActionEvent actionEvent) throws IOException {
        ReadDat rd = new ReadDat();
        rd.show();
    }

    public void newPGWindow(ActionEvent actionEvent) throws IOException {
        Polygarmonic pg = new Polygarmonic();
        pg.show();
    }

    public void newCWindow(ActionEvent actionEvent) throws IOException {
        Cardio c = new Cardio();
        c.show();
    }

    public void newFlWindow(ActionEvent actionEvent) throws IOException {
        Filtr f = new Filtr();
        f.show();
    }

    public void newWavWindow(ActionEvent actionEvent) throws IOException {
        ReadWav w = new ReadWav();
        w.show();
    }

    public void newAntiTrend(ActionEvent actionEvent) throws IOException {
        AntiTrend a = new AntiTrend();
        a.show();
    }

    public void newReadCSV(ActionEvent actionEvent) throws IOException {
        ReadCSV rcsv = new ReadCSV();
        rcsv.show();
    }
}
