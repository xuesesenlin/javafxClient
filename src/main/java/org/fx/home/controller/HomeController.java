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

    //    关闭程序
    @FXML
    private void close(MouseEvent event) {
        AlertUtil util = new AlertUtil();
        boolean b = util.f_alert_confirmDialog("警告", "是否确定退出");
        if (b)
            FXRobotHelper.getStages().get(0).close();
    }

    //    最小化程序
    @FXML
    private void hide(MouseEvent event) {
        FXRobotHelper.getStages().get(0).setIconified(true);
    }
}
