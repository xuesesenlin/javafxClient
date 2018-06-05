package org.fx.home.controller;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.fx.urils.AlertUtil;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class HomeController {

    private AlertUtil alert = new AlertUtil();

    //    关闭程序
    @FXML
    private void close(MouseEvent event) {
        boolean b = alert.f_alert_confirmDialog("警告", "是否确定退出");
        if (b)
            FXRobotHelper.getStages().get(0).close();
    }

    //    最小化程序
    @FXML
    private void hide(MouseEvent event) {
        FXRobotHelper.getStages().get(0).setIconified(true);
    }

    @FXML
    private void grzl(MouseEvent event){
        alert.f_alert_informationDialog("提示","123");
    }
}
