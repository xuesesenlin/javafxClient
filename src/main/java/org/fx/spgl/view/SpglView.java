package org.fx.spgl.view;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.fx.spgl.model.SpglModel;
import org.fx.urils.FeignRequest;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class SpglView {
    public void init() throws Exception {
        Parent root = FXRobotHelper.getStages().get(0).getScene().getRoot();
        Parent root2 = FXMLLoader.load(getClass().getResource("/spgl/fxml/index.fxml"));
        AnchorPane lookup = (AnchorPane) root.lookup("#bodys");
        AnchorPane node = (AnchorPane) root2.lookup("#spzl_index");
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

    public void update(SpglModel model) throws Exception {
        Parent root = FXRobotHelper.getStages().get(0).getScene().getRoot();
        Parent root2 = FXMLLoader.load(getClass().getResource("/spgl/fxml/update.fxml"));
        AnchorPane lookup = (AnchorPane) root.lookup("#bodys");
        Label label = (Label) root2.lookup("#uuid");
        label.setText(model.getUuid());
        TextField cname = (TextField) root2.lookup("#cname");
        cname.setText(model.getCname());
        TextField jg = (TextField) root2.lookup("#jg");
        jg.setText(model.getJg() + "");
        TextField dw = (TextField) root2.lookup("#dw");
        dw.setText(model.getDw());
        TextField ge = (TextField) root2.lookup("#ge");
        ge.setText(model.getGe());
        TextField pp = (TextField) root2.lookup("#pp");
        pp.setText(model.getPp());
        TextField xq = (TextField) root2.lookup("#xq");
        xq.setText(model.getXq());
        TextField sl = (TextField) root2.lookup("#sl");
        sl.setText(model.getSl() + "");
        ChoiceBox lm = (ChoiceBox) root2.lookup("#lm");
        lm.setValue(model.getLm());
        ChoiceBox sfxj = (ChoiceBox) root2.lookup("#sfxj");
        sfxj.setValue(model.getSxj_string());
        ImageView zt = (ImageView) root2.lookup("#zt");
        if (model.getZt() != null && !model.getZt().trim().equals(""))
            zt.setImage(new Image(new FeignRequest().URL() + "/commodity/IoReadImage/" + model.getZt()));
        zt.setId(model.getZt());
        AnchorPane node = (AnchorPane) root2.lookup("#spzl_update");
        node.setPrefWidth(lookup.getWidth());
        node.setPrefHeight(lookup.getHeight());
        lookup.getChildren().clear();
        lookup.getChildren().addAll(root2);
    }
}
