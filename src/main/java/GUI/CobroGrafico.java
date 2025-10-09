package GUI;

import Logica.Caja;
import Logica.Cliente;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.util.List;
import javafx.scene.layout.VBox;

public class CobroGrafico {
    private Label lblTiempo;
    private HBox panelCajas;

    public CobroGrafico(Label lblTiempo, HBox panelCajas) {
        this.lblTiempo = lblTiempo;
        this.panelCajas = panelCajas;
    }

    public void actualizarTiempo(int tiempo) {
        Platform.runLater(() -> lblTiempo.setText("Tiempo: " + tiempo));
    }

    public void actualizarVista(List<Caja> cajas) {
        Platform.runLater(() -> {
            panelCajas.getChildren().clear();
            for (Caja caja : cajas) {
                VBox cajaVisual = new VBox(5);

                ImageView imagenCaja = new ImageView(new Image(getClass().getResource(
                    caja.estaAbierta() ? "/Cajero_Abierto_gif.gif" : "/Cajero_Cerrado.png"
                ).toExternalForm()));
                imagenCaja.setFitWidth(80);
                imagenCaja.setFitHeight(80);
                cajaVisual.getChildren().add(imagenCaja);

                for (Cliente cliente : caja.getColaClientes().obtenerElementos()) {
                    ImageView imagenCliente = new ImageView(new Image(getClass().getResource("/Cliente_gif.gif").toExternalForm()));
                    imagenCliente.setFitWidth(60);
                    imagenCliente.setFitHeight(60);
                    cajaVisual.getChildren().add(imagenCliente);
                }

                panelCajas.getChildren().add(cajaVisual);
            }
        });
    }
}