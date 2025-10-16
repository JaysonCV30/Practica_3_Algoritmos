package GUI;

import Logica.Caja;
import Logica.Cliente;
import Logica.ColaSimple;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CobroGrafico {

    private Label lblTiempo;
    private HBox panelCajas;
    private HBox panelSalida;
    private TableView<Cliente> tablaClientes = new TableView<>();
    private static final Image IMG_CAJERO_ABIERTO = new Image(CobroGrafico.class.getResource("/Cajero_Abierto_gif.gif").toExternalForm());
    private static final Image IMG_CAJERO_CERRADO = new Image(CobroGrafico.class.getResource("/Cajero_Cerrado.png").toExternalForm());
    private static final Image IMG_CLIENTE = new Image(CobroGrafico.class.getResource("/Cliente_gif.gif").toExternalForm());
    private static final Image IMG_SALIDA = new Image(CobroGrafico.class.getResource("/Cliente_Saliendo.png").toExternalForm());

    public CobroGrafico(Label lblTiempo, HBox panelCajas, HBox panelSalida) {
        this.lblTiempo = lblTiempo;
        this.panelCajas = panelCajas;
        this.panelSalida = panelSalida;
    }

    public void actualizarTiempo(int tiempo) {
        Platform.runLater(() -> lblTiempo.setText("Tiempo: " + tiempo));
    }

    public void mostrarClienteSaliendo(Cliente cliente) {
        Platform.runLater(() -> {
            ImageView imagenSalida = new ImageView(IMG_SALIDA);
            imagenSalida.setFitWidth(70);
            imagenSalida.setFitHeight(70);
            panelSalida.getChildren().add(imagenSalida);

            PauseTransition delay = new PauseTransition(Duration.seconds(1)); // visible por 1 segundo
            delay.setOnFinished(event -> panelSalida.getChildren().remove(imagenSalida));
            delay.play();
        });
    }

    public void mostrarEstadisticasClientes(ColaSimple<Cliente> clientes) {
        Platform.runLater(() -> {
            tablaClientes.getColumns().clear();

            TableColumn<Cliente, Integer> colId = new TableColumn<>("ID");
            colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

            TableColumn<Cliente, Double> colLlegada = new TableColumn<>("Llegada");
            colLlegada.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTiempoLlegada()).asObject());

            TableColumn<Cliente, Double> colEspera = new TableColumn<>("Espera");
            colEspera.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTiempoEsperaCalculado()).asObject());

            TableColumn<Cliente, Double> colPago = new TableColumn<>("Pago");
            colPago.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getDuracionPago()).asObject());

            tablaClientes.getColumns().addAll(colId, colLlegada, colEspera, colPago);
            tablaClientes.setItems(FXCollections.observableArrayList(clientes.obtenerElementos()));
        });
    }

    public TableView<Cliente> getTablaClientes() {
        return tablaClientes;
    }

    public void actualizarVista(Caja[] cajas, int tiempoActual) {
        Platform.runLater(() -> {
            panelCajas.setStyle("-fx-alignment: top-center;");
            panelCajas.getChildren().clear();
            for (Caja caja : cajas) {
                VBox cajaVisual = new VBox(5);
                cajaVisual.setStyle("-fx-alignment: center;");
                cajaVisual.setPrefHeight(300);
                cajaVisual.setMaxHeight(300);
                cajaVisual.setMinHeight(300);

                // Etiqueta de título
                Label lblTitulo = new Label("Caja " + (caja.getId() + 1));
                lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                // Estadísticas en tiempo real
                double tiempoVisible = caja.estaAbierta()
                        ? caja.getTiempoAbiertaActual(tiempoActual) + caja.getTiempoAbiertaTotal()
                        : caja.getTiempoAbiertaTotal();
                Label lblTiempoAbierta = new Label("Abierta: " + String.format("%.2f", tiempoVisible) + " seg");
                Label lblClientes = new Label("Atendidos: " + caja.getClientesAtendidos());

                ImageView imagenCaja = new ImageView(caja.estaAbierta() ? IMG_CAJERO_ABIERTO : IMG_CAJERO_CERRADO);
                imagenCaja.setFitWidth(90);
                imagenCaja.setFitHeight(90);

                VBox colaVisual = new VBox(3);
                colaVisual.setPrefHeight(120);
                colaVisual.setMaxHeight(120);
                colaVisual.setMinHeight(120);
                colaVisual.setStyle("-fx-alignment: top-center;");
                List<Cliente> clientes = caja.getColaClientes().obtenerElementos();
                for (int i = 0; i < Math.min(clientes.size(), 4); i++) {
                    Cliente cliente = clientes.get(i);
                    ImageView imagenCliente = new ImageView(IMG_CLIENTE);
                    imagenCliente.setFitWidth(90);
                    imagenCliente.setFitHeight(90);
                    colaVisual.getChildren().add(imagenCliente);
                }

                cajaVisual.getChildren().addAll(lblTitulo, lblTiempoAbierta, lblClientes, imagenCaja, colaVisual);
                panelCajas.getChildren().add(cajaVisual);
            }
        });
    }
}
