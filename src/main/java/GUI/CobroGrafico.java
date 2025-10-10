package GUI;

import Logica.Caja;
import Logica.Cliente;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CobroGrafico {

    private Label lblTiempo;
    private HBox panelCajas;
    private HBox panelSalida;

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
            ImageView imagenSalida = new ImageView(new Image(getClass().getResource("/Cliente_Saliendo.png").toExternalForm()));
            imagenSalida.setFitWidth(70);
            imagenSalida.setFitHeight(70);
            panelSalida.getChildren().add(imagenSalida);

            PauseTransition delay = new PauseTransition(Duration.seconds(3)); // visible por 3 segundos
            delay.setOnFinished(event -> panelSalida.getChildren().remove(imagenSalida));
            delay.play();
        });
    }

    public void actualizarVista(List<Caja> cajas, int tiempoActual) {
        Platform.runLater(() -> {
            panelCajas.getChildren().clear();
            for (Caja caja : cajas) {
                VBox cajaVisual = new VBox(5);
                cajaVisual.setStyle("-fx-alignment: center;");

                // Etiqueta de título
                Label lblTitulo = new Label("Caja " + (caja.getId() + 1));
                lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                // Estadísticas en tiempo real
                double tiempoVisible = caja.estaAbierta()
                        ? caja.getTiempoAbiertaActual(tiempoActual) + caja.getTiempoAbiertaTotal()
                        : caja.getTiempoAbiertaTotal();
                Label lblTiempoAbierta = new Label("Abierta: " + String.format("%.2f", tiempoVisible) + " seg");
                Label lblClientes = new Label("Atendidos: " + caja.getClientesAtendidos());

                ImageView imagenCaja = new ImageView(new Image(getClass().getResource(
                        caja.estaAbierta() ? "/Cajero_Abierto_gif.gif" : "/Cajero_Cerrado.png"
                ).toExternalForm()));
                imagenCaja.setFitWidth(90);
                imagenCaja.setFitHeight(90);

                VBox colaVisual = new VBox(3);
                colaVisual.setStyle("-fx-alignment: center;");
                for (Cliente cliente : caja.getColaClientes().obtenerElementos()) {
                    ImageView imagenCliente = new ImageView(new Image(getClass().getResource("/Cliente_gif.gif").toExternalForm()));
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
