package org.fx.home.controller;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.fx.feign.AccountInterface;
import org.fx.feign.OrderInterface;
import org.fx.feign.PersionInterface;
import org.fx.grzl.view.GrzlView;
import org.fx.spgl.view.SpglView;
import org.fx.urils.*;

import java.util.Timer;

import static javafx.stage.StageStyle.TRANSPARENT;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class HomeController {

    private AlertUtil alert = new AlertUtil();
    private AccountInterface accountInterface = FeignUtil.feign()
            .target(AccountInterface.class, new FeignRequest().URL());
    private PersionInterface persionInterface = FeignUtil.feign()
            .target(PersionInterface.class, new FeignRequest().URL());
    private OrderInterface orderInterface = FeignUtil.feign()
            .target(OrderInterface.class, new FeignRequest().URL());

    //    关闭程序
    @FXML
    private void close(MouseEvent event) {
        boolean b = alert.f_alert_confirmDialog("警告", "是否确定退出");
        if (b) {
            //            停止所有线程
            System.exit(0);
//            只是关闭所有页面
//            ObservableList<Stage> stages = FXRobotHelper.getStages();
//            for (int i = stages.size() - 1; i >= 0; i--) {
//                stages.get(i).close();
//            }
        }
    }

    //    最小化程序
    @FXML
    private void hide(MouseEvent event) {
        FXRobotHelper.getStages().get(0).setIconified(true);
    }

    @FXML
    private void grzl(MouseEvent event) throws Exception {
        new GrzlView().init();
    }

    @FXML
    private void spgl(MouseEvent event) throws Exception {
        new SpglView().init();
    }

    //    实时获取有无最新订单
    public void sssx() {
        Timer timer = new Timer();
        timer.schedule(
                new java.util.TimerTask() {
                    public void run() {
                        Platform.runLater(() -> {
                            //        最新订单提醒
                            ResponseResult<String> result = orderInterface.findByType(StaticToken.getToken());
                            if (result.isSuccess()) {
                                Stage stage = new Stage();
                                Pane pane = new Pane();
                                Label label = new Label("您有新订单，请注意刷新");
                                pane.getChildren().add(label);
                                Scene scene = new Scene(pane, 100, 20);
                                stage.setScene(scene);
                                stage.initStyle(TRANSPARENT);
                                stage.setResizable(false);
                                stage.setTitle("提示");
                                Screen screen2 = Screen.getPrimary();
                                Rectangle2D bounds = screen2.getVisualBounds();
                                stage.setX(bounds.getMaxX() - 200);
                                stage.setY(bounds.getMaxY() - 100);
                                stage.setWidth(200);
                                stage.setHeight(100);
                                stage.setAlwaysOnTop(true);
                                stage.show();
//                                定时关闭
                                Timer timer2 = new Timer();
                                timer2.schedule(new java.util.TimerTask() {
                                    public void run() {
                                        Platform.runLater(() -> {
                                            System.out.println("提醒窗口关闭");
                                            if (stage.isShowing()) {
                                                stage.close();
                                                timer2.cancel();
                                            } else
                                                timer2.cancel();
                                        });
                                    }
                                }, 5000, 5000);
                            }
                        });
                    }
                }, 0, 15 * 1000);
    }
}
