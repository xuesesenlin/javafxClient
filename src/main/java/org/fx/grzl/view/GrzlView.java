package org.fx.grzl.view;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class GrzlView {
    public void init() throws Exception {
        Parent root = FXRobotHelper.getStages().get(0).getScene().getRoot();
        Parent root2 = FXMLLoader.load(getClass().getResource("/grzl/fxml/index.fxml"));
        AnchorPane lookup = (AnchorPane) root.lookup("#bodys");
        lookup.getChildren().clear();
        lookup.getChildren().addAll(root2);

    }
}
