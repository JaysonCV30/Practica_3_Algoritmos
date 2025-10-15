package GUI;

import Logica.CobroLogico;
import Logica.Simulador;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SimuladorMain extends Application {

    @Override
    public void start(Stage stage) {
        Label lblModo = new Label("Modo: Ninguno");

        Button btnFilaUnica = new Button("Simulación Fila Única");
        btnFilaUnica.setOnAction(e -> {
            lblModo.setText("Modo: Fila Única");
            lanzarSimulacion(true, "Simulación Fila Única");
        });

        Button btnFilas = new Button("Simulación Múltiples Filas");
        btnFilas.setOnAction(e -> {
            lblModo.setText("Modo: Múltiples Filas");
            lanzarSimulacion(false, "Simulación Múltiples Filas");
        });

        VBox centro = new VBox(20, btnFilaUnica, btnFilas, lblModo);
        centro.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(centro, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Panel de Control - Simulador de Filas");
        stage.show();
    }

    private void lanzarSimulacion(boolean modoFilaUnica, String tituloVentana) {
        Stage nuevaVentana = new Stage();
        Label lblTiempo = new Label("Tiempo: 0");
        HBox panelFilas = new HBox(10);
        HBox panelSalida = new HBox(10);
        Label lblSalida = new Label("Salida:");

        Simulador simulador = new Simulador(modoFilaUnica);
        CobroGrafico grafico = new CobroGrafico(lblTiempo, panelFilas, panelSalida);
        CobroLogico logico = new CobroLogico(simulador, grafico);

        grafico.getTablaClientes().setPrefHeight(250);
        grafico.getTablaClientes().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        grafico.getTablaClientes().setVisible(false);

        Thread hiloSimulacion = new Thread(logico);
        hiloSimulacion.setDaemon(true);
        hiloSimulacion.start();

        VBox centro = new VBox(20, lblTiempo, panelFilas);
        VBox salidaVisual = new VBox(5, lblSalida, panelSalida);
        salidaVisual.setStyle("-fx-alignment: center-right;");

        BorderPane root = new BorderPane();
        root.setCenter(centro);
        root.setRight(salidaVisual);
        root.setBottom(grafico.getTablaClientes());

        Scene scene = new Scene(root, 1500, 900);
        nuevaVentana.setScene(scene);
        nuevaVentana.setTitle(tituloVentana);
        nuevaVentana.show();
        nuevaVentana.setOnCloseRequest(event -> {
            hiloSimulacion.interrupt(); // Detiene el hilo de simulación
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Simulación finalizada");
            alerta.setHeaderText(null);
            alerta.setContentText("La simulación '" + tituloVentana + "' ha terminado.");
            alerta.show();
            System.out.println("Simulacion '" + tituloVentana + "' finalizada.");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
