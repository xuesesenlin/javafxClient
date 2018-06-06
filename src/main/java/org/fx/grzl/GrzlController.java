package org.fx.grzl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.fx.feign.AccountInterface;
import org.fx.feign.PersionInterface;
import org.fx.login.model.LoginModel;
import org.fx.urils.*;

import java.util.logging.Logger;

public class GrzlController {
    private static Logger logger = Logger.getLogger(GrzlController.class.toString());
    private AccountInterface accountInterface = FeignUtil.feign()
            .target(AccountInterface.class, new FeignRequest().URL());
    private PersionInterface persionInterface = FeignUtil.feign()
            .target(PersionInterface.class, new FeignRequest().URL());
    private ObjectMapper objectMapper = new ObjectMapper();

    //    点击个人资料
    public void grzl() {
        HBox hBox = new HBox();
        ObservableList<Node> children = hBox.getChildren();
        Label label = new Label("修改密码:");
        children.add(label);
        TextField textField = new TextField();
        children.add(textField);
        Button button = new Button("确定");
        button.setOnAction(o -> {
            new GrzlController().updatePWD(o, textField);
        });
        children.add(button);
        Label label2 = new Label("");
        children.add(label2);
        Label label21 = new Label("修改地址:");
        children.add(label21);
        TextField textField2 = new TextField();
        children.add(textField2);
        Button button2 = new Button("确定");
        button2.setOnAction(o -> {
            new GrzlController().updateAdderss(o, textField2);
        });
        children.add(button2);
        Label label3 = new Label("");
        children.add(label3);
        Label label31 = new Label("修改电话:");
        children.add(label31);
        TextField textField3 = new TextField();
        children.add(textField3);
        Button button3 = new Button("确定");
        button3.setOnAction(o -> {
            new GrzlController().updatePhone(o, textField2);
        });
        children.add(button3);

//        商家编码
        Label label1 = new Label("商家编码:");
        children.add(label1);
        try {
            ResponseResult<LoginModel> result = FeignUtil.feign()
                    .target(AccountInterface.class, new FeignRequest().URL())
                    .sjCode(StaticToken.getToken());
            if (result.isSuccess()) {
//                textField2.setText(result.getData().ge);
                Label label123 = new Label(result.getData().getCoding());
                children.add(label123);
            } else {
                Label label123 = new Label("获取商家码失败，可以尝试从新打开该页面或尝试从新登陆");
                children.add(label123);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Label label123 = new Label("获取商家码失败，可以尝试从新打开该页面或尝试从新登陆");
            children.add(label123);
        }
        try {

            Parent root = FXRobotHelper.getStages().get(0).getScene().getRoot();
            Parent root2 = FXMLLoader.load(getClass().getResource("/home/fxml/index.fxml"));
            AnchorPane lookup = (AnchorPane) root.lookup("#bodys");
            lookup.getChildren().addAll(root2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    点击确定按钮修改密码
    private void updatePWD(ActionEvent event, TextField textField) {
        String text = textField.getText();
        AlertUtil alertUtil = new AlertUtil();
        text = Base64Util.encode(text);
        ResponseResult<String> result = accountInterface.updatePWD(text, StaticToken.getToken());
        if (result.isSuccess()) {
            alertUtil.f_alert_informationDialog("提示", "修改密码成功");
////            自动注销
            ObservableList<Stage> stages = FXRobotHelper.getStages();
            stages.get(0).close();
        } else {
            StaticToken.setToken(result.getData());
            alertUtil.f_alert_informationDialog("警告", result.getMessage());
        }
    }

    //    点击确定按钮修改地址
    private void updateAdderss(ActionEvent event, TextField textField) {
        String text = textField.getText();
        AlertUtil alertUtil = new AlertUtil();
        text = Base64Util.encode(text);
        ResponseResult<String> result = persionInterface.updateAdderss(text, StaticToken.getToken());
        if (result.isSuccess()) {
            alertUtil.f_alert_informationDialog("提示", "地址修改成功");
        } else {
            StaticToken.setToken(result.getData());
            alertUtil.f_alert_informationDialog("警告", result.getMessage());
        }
    }

    //    点击确定按钮修改电话
    private void updatePhone(ActionEvent event, TextField textField) {
        String text = textField.getText();
        AlertUtil alertUtil = new AlertUtil();
        text = Base64Util.encode(text);
        ResponseResult<String> result = persionInterface.updatePhone(text, StaticToken.getToken());
        if (result.isSuccess()) {
            alertUtil.f_alert_informationDialog("提示", "电话修改成功");
        } else {
            StaticToken.setToken(result.getData());
            alertUtil.f_alert_informationDialog("警告", result.getMessage());
        }
    }
}
