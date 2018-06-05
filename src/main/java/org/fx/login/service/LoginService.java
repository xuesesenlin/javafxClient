package org.fx.login.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.fx.login.feign.LoginInterface;
import org.fx.login.model.LoginModel;
import org.fx.urils.FeignRequest;
import org.fx.urils.FeignUtil;
import org.fx.urils.ResponseResult;

//此service必须是javafx.concurrent包
public class LoginService {

    private LoginInterface loginInterface = FeignUtil.feign()
            .target(LoginInterface.class, new FeignRequest().URL());
    private ObjectMapper objectMapper = new ObjectMapper();

    public ResponseResult<String> login(LoginModel model) {
        ResponseResult<String> result = new ResponseResult<>();

        Service<ResponseResult<String>> service = new Service<ResponseResult<String>>() {
            @Override
            protected Task<ResponseResult<String>> createTask() {
                return new Task<ResponseResult<String>>() {
                    @Override
                    protected ResponseResult<String> call() throws Exception {
                        String json = objectMapper.writeValueAsString(model);
                        System.out.println("开始查询数据");
                        ResponseResult<String> login = loginInterface.login(json);
                        System.out.println(login);
                        return login;
                    }
                };
            }
        };
        service.start();
//        失败
        service.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                result.setSuccess(false);
                result.setMessage("远程调用失败");
            }
        });
//        成功
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                result.setSuccess(true);
                result.setMessage("远程调用成功");
            }
        });
        return result;
    }

}
