package GUI;

import Logica.Caja;
import Logica.Cliente;
import Logica.ColaSimple;
import Logica.Simulador;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SimuladorControl {

    @FXML
    private HBox panelFilas;

    @FXML
    private TableView<Caja> tablaResultados;
    @FXML
    private TableColumn<Caja, Integer> colId;
    @FXML
    private TableColumn<Caja, Integer> colClientes;
    @FXML
    private TableColumn<Caja, Double> colTiempo;

    private Simulador simulador;

    @FXML
    public void ejecutarSimulacion() {
        simulador = new Simulador(true); // true = fila única, false = múltiples filas
        simulador.ejecutarSimulacion();
        actualizarVista();
        mostrarResultados();
    }

    private void actualizarVista() {
        panelFilas.getChildren().clear();

        for (Caja caja : simulador.getCajas()) {
            VBox cajaVisual = new VBox();
            cajaVisual.setSpacing(5);

            Image imagenCaja = new Image(getClass().getResource(
                caja.estaAbierta() ? "/assets/Cajero_Abierto_gif.gif" : "/assets/Cajero_Cerrado.png"
            ).toExternalForm());

            ImageView imagenCajaView = new ImageView(imagenCaja);
            imagenCajaView.setFitWidth(80);
            imagenCajaView.setFitHeight(80);
            cajaVisual.getChildren().add(imagenCajaView);

            for (Cliente cliente : caja.getColaClientes().obtenerElementos()) {
                Image imagenCliente = new Image(getClass().getResource("/assets/Cliente_gif.gif").toExternalForm());
                ImageView imagenClienteView = new ImageView(imagenCliente);
                imagenClienteView.setFitWidth(60);
                imagenClienteView.setFitHeight(60);
                cajaVisual.getChildren().add(imagenClienteView);
            }

            panelFilas.getChildren().add(cajaVisual);
        }
    }

    private void mostrarResultados() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colClientes.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getClientesAtendidos()).asObject());
        colTiempo.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getTiempoTotalPagos()).asObject());

        tablaResultados.setItems(FXCollections.observableArrayList(simulador.getCajas()));
    }
}