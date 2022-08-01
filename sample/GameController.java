package sample;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private Canvas gameCanvas;
    public AnchorPane gameAnchor;
    KeyPolling keys = KeyPolling.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
