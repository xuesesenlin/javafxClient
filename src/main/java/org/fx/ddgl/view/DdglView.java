package org.fx.ddgl.view;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class DdglView {

    public void init() throws Exception {
        Parent root = FXRobotHelper.getStages().get(0).getScene().getRoot();
        Parent root2 = FXMLLoader.load(getClass().getResource("/ddgl/fxml/index.fxml"));
        AnchorPane lookup = (AnchorPane) root.lookup("#bodys");
        AnchorPane node = (AnchorPane) root2.lookup("#ddgl_index");
        node.setPrefWidth(lookup.getWidth());
        node.setPrefHeight(lookup.getHeight());
        lookup.getChildren().clear();
        lookup.getChildren().addAll(root2);
    }

    public void add() throws Exception {
        Parent root = FXRobotHelper.getStages().get(0).getScene().getRoot();
        Parent root2 = FXMLLoader.load(getClass().getResource("/spgl/fxml/add.fxml"));
        AnchorPane lookup = (AnchorPane) root.lookup("#bodys");
        AnchorPane node = (AnchorPane) root2.lookup("#spzl_add");
        node.setPrefWidth(lookup.getWidth());
        node.setPrefHeight(lookup.getHeight());
        lookup.getChildren().clear();
        lookup.getChildren().addAll(root2);
    }

    public void update(String uuid) throws Exception {
        Parent root = FXRobotHelper.getStages().get(0).getScene().getRoot();
        Parent root2 = FXMLLoader.load(getClass().getResource("/spgl/fxml/update.fxml"));
        AnchorPane lookup = (AnchorPane) root.lookup("#bodys");
        Label label = (Label) root2.lookup("#uuid");
        label.setText(uuid);
        AnchorPane node = (AnchorPane) root2.lookup("#spzl_update");
        node.setPrefWidth(lookup.getWidth());
        node.setPrefHeight(lookup.getHeight());
        lookup.getChildren().clear();
        lookup.getChildren().addAll(root2);
    }
}
