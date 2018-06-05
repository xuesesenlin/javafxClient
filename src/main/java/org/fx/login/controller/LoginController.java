package org.fx.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.robot.impl.FXRobotHelper;
import feign.FeignException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.fx.home.view.HomeView;
import org.fx.login.feign.LoginInterface;
import org.fx.login.model.LoginModel;
import org.fx.urils.*;

import java.util.logging.Logger;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class LoginController {

    private AlertUtil alert = new AlertUtil();
    private static Logger logger = Logger.getLogger(LoginController.class.toString());

    private LoginInterface loginInterface = FeignUtil.feign()
            .target(LoginInterface.class, new FeignRequest().URL());
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
        if (b)
            FXRobotHelper.getStages().get(0).close();
    }

    //    最小化程序
    @FXML
    private void hide(MouseEvent event) {
        FXRobotHelper.getStages().get(0).setIconified(true);
    }

    //    忘记密码
    @FXML
    private void forgetPassword(MouseEvent event) {
        alert.f_alert_informationDialog("提示", "维护中");
    }

    //    注册
    @FXML
    private void register(MouseEvent event) {
        alert.f_alert_informationDialog("提示", "维护中");
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
                            ResponseResult<String> result = loginInterface.login(json);
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
