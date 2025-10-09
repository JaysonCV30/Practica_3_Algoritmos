package GUI;

import Logica.CobroLogico;
import Logica.Simulador;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SimuladorMain extends Application {

    private Label lblTiempo;
    private HBox panelFilas;
    private Simulador simulador;
    private CobroGrafico grafico;
    private CobroLogico logico;

    @Override
    public void start(Stage stage) {
        simulador = new Simulador(true);
        
        lblTiempo = new Label("Tiempo: 0");
        panelFilas = new HBox(10);
        
        grafico = new CobroGrafico(lblTiempo, panelFilas);
        logico = new CobroLogico(simulador, grafico);
        
        Button btnEjecutar = new Button("Ejecutar Simulacion");
        btnEjecutar.setOnAction(e -> {
            Thread hiloSimulacion = new Thread(logico);
            hiloSimulacion.setDaemon(true);
            hiloSimulacion.start();
        });

        VBox root = new VBox(20, btnEjecutar, lblTiempo, panelFilas);
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Simulador de Filas - Costco");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}