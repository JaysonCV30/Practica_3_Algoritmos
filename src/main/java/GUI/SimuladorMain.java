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
    private HBox panelSalida;
    private Simulador simulador;
    private CobroGrafico grafico;
    private CobroLogico logico;

    @Override
    public void start(Stage stage) {
        simulador = new Simulador(true);

        lblTiempo = new Label("Tiempo: 0");
        panelFilas = new HBox(10);
        panelSalida = new HBox(10);

        Label lblSalida = new Label("Salida:");

        Button btnEjecutar = new Button("Ejecutar Simulación");
        btnEjecutar.setOnAction(e -> {
            Thread hiloSimulacion = new Thread(logico);
            hiloSimulacion.setDaemon(true);
            hiloSimulacion.start();
        });

        grafico = new CobroGrafico(lblTiempo, panelFilas, panelSalida);
        grafico.getTablaClientes().setPrefHeight(250);
        grafico.getTablaClientes().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        grafico.getTablaClientes().setVisible(false);
        logico = new CobroLogico(simulador, grafico);

        BorderPane root = new BorderPane();

        VBox centro = new VBox(20, btnEjecutar, lblTiempo, panelFilas);
        VBox salidaVisual = new VBox(5, lblSalida, panelSalida);
        salidaVisual.setStyle("-fx-alignment: center-right;");

        root.setCenter(centro);
        root.setRight(salidaVisual);
        root.setBottom(grafico.getTablaClientes());
        Scene scene = new Scene(root, 1500, 900);
        stage.setScene(scene);
        stage.setTitle("Simulador de Filas - Costco");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
