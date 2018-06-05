package org.fx.login.feign;

import feign.Body;
import feign.Param;
import feign.RequestLine;
import org.fx.login.model.LoginModel;
import org.fx.urils.ResponseResult;

/**
 * @Headers("X-Ping: {token}")
 */
public interface LoginInterface {

    @RequestLine("POST /api/account/login")
    @Body("json={json}")
    ResponseResult<String> login(@Param("json") String json);

    @RequestLine("GET /api/account/sj/code/{token}")
    ResponseResult<LoginModel> sjCode(@Param("token") String token);

    @RequestLine("POST /api/account/update")
    @Body("pass={pass}&token={token}")
    ResponseResult<String> updatePWD(@Param("pass") String pass, @Param("token") String token);

    @RequestLine("POST /api/register/register")
    @Body("json={json}")
    ResponseResult<String> register(@Param("json") String json);

}
