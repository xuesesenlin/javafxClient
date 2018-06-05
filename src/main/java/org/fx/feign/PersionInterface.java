package org.fx.feign;

import feign.Body;
import feign.Param;
import feign.RequestLine;
import org.fx.urils.ResponseResult;

/**
 * @Headers("X-Ping: {token}")
 */
public interface PersionInterface {
    @RequestLine("POST /api/persion/updateAddress")
    @Body("address={address}&token={token}")
    ResponseResult<String> updateAdderss(@Param("address") String address, @Param("token") String token);

    @RequestLine("POST /api/persion/updatePhone")
    @Body("phone={phone}&token={token}")
    ResponseResult<String> updatePhone(@Param("phone") String phone, @Param("token") String token);
}
