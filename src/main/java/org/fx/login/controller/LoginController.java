package org.fx.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.robot.impl.FXRobotHelper;
import feign.FeignException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.fx.feign.AccountInterface;
import org.fx.home.view.HomeView;
import org.fx.login.model.LoginModel;
import org.fx.urils.*;

import static javafx.stage.StageStyle.TRANSPARENT;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class LoginController {

    private AccountInterface accountInterface = FeignUtil.feign()
            .target(AccountInterface.class, new FeignRequest().URL());
    private ObjectMapper objectMapper = new ObjectMapper();
    private static String string_title = "xxx系统";

    @FXML
    private TextField account;
    @FXML
    private PasswordField password;
    @FXML
    private Label error;
    @FXML
    private Label title;
    @FXML
    private Button login_button;

    //    关闭程序
    @FXML
    private void close(MouseEvent event) {
        AlertUtil util = new AlertUtil();
        boolean b = util.f_alert_confirmDialog("警告", "是否确定退出");
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

    //    忘记密码
    @FXML
    private void forgetPassword(MouseEvent event) {
        StageUtils.close();
        Stage stage = new Stage();
        Pane pane = new Pane();
        Label label = new Label("维护中");
        pane.getChildren().add(label);
        Scene scene = new Scene(pane,100,20);
        stage.setScene(scene);
//        stage.initStyle(TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("提示");
        Screen screen2 = Screen.getPrimary();
        Rectangle2D bounds = screen2.getVisualBounds();
        stage.setX(bounds.getMaxX()-200);
        stage.setY(bounds.getMaxY()-100);
        stage.setWidth(200);
        stage.setHeight(100);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    //    注册
    @FXML
    private void register(MouseEvent event) {
        StageUtils.close();
        Stage stage = new Stage();
        Pane pane = new Pane();
        Label label = new Label("维护中");
        pane.getChildren().add(label);
        Scene scene = new Scene(pane,100,20);
        stage.setScene(scene);
//        stage.initStyle(TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("提示");
        Screen screen2 = Screen.getPrimary();
        Rectangle2D bounds = screen2.getVisualBounds();
        stage.setX(bounds.getMaxX()-200);
        stage.setY(bounds.getMaxY()-100);
        stage.setWidth(200);
        stage.setHeight(100);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    //    重置
    @FXML
    private void reset(MouseEvent event) {
        account.setText("xuesemofa@163.com");
        password.setText("1234567aA");
        error.setText(null);
    }

    //    登录
    @FXML
    private void login(MouseEvent event) {
        error.setText(null);
        title.setText("正在登录中...");
        login_button.setDisable(true);
        String accountText = account.getText();
        String passwordText = password.getText();
        if (accountText.trim().equals("") || passwordText.trim().equals("")) {
            error.setText("账户密码不能为空");
            title.setText(string_title);
            login_button.setDisable(false);
        } else {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Platform.runLater(() -> {
                        LoginModel model = new LoginModel();
                        model.setUsername(accountText);
                        model.setPassword(Base64Util.encode(passwordText));
                        model.setTypes(1);
                        try {
                            String json = objectMapper.writeValueAsString(model);
                            ResponseResult<String> result = accountInterface.login(json);
                            if (result.isSuccess()) {
                                StaticToken.setToken(result.getData());
                                new HomeView().init();
                            } else {
                                error.setText(result.getMessage());
                                title.setText(string_title);
                                login_button.setDisable(false);
                            }
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            error.setText("数据转换错误");
                            title.setText(string_title);
                            login_button.setDisable(false);
                        } catch (FeignException e) {
                            e.printStackTrace();
                            error.setText("远程服务器错误");
                            title.setText(string_title);
                            login_button.setDisable(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                            error.setText("跳转错误");
                            title.setText(string_title);
                            login_button.setDisable(false);
                        }
                    });
                    return null;
                }
            };
            new Thread(task).start();
        }
    }
}
