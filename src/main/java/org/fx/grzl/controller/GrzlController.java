package org.fx.grzl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.fx.feign.AccountInterface;
import org.fx.feign.PersionInterface;
import org.fx.urils.*;

import java.util.logging.Logger;

public class GrzlController {

    private static Logger logger = Logger.getLogger(GrzlController.class.toString());
    private AccountInterface accountInterface = FeignUtil.feign()
            .target(AccountInterface.class, new FeignRequest().URL());
    private PersionInterface persionInterface = FeignUtil.feign()
            .target(PersionInterface.class, new FeignRequest().URL());
    private ObjectMapper objectMapper = new ObjectMapper();

    @FXML
    private TextField dz;
    @FXML
    private TextField dh;
    @FXML
    private TextField mm;
    @FXML
    private TextField bm;

    //    点击确定按钮修改密码
    @FXML
    private void updatePWD(MouseEvent event) {
        String text = mm.getText();
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
    @FXML
    private void updateAdderss(MouseEvent event) {
        String text = dz.getText();
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
    @FXML
    private void updatePhone(MouseEvent event) {
        String text = dh.getText();
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
