package org.fx.feign;

import feign.Body;
import feign.Param;
import feign.RequestLine;
import org.fx.urils.ResponseResult;

/**
 * @Headers("X-Ping: {token}")
 */
public interface SpglInterface {

    @RequestLine("POST /api/commodity/commodity")
    @Body("json={json}")
    ResponseResult<String> add(@Param("json") String json);

    @RequestLine("GET /api/commodity/del/{uuid}?token={token}")
    ResponseResult<String> del(@Param("uuid") String uuid, @Param("token") String token);

    @RequestLine("POST /api/commodity/update")
    @Body("json={json}")
    ResponseResult<String> update(@Param("json") String json);

    @RequestLine("GET /api/commodity/commodity/page/{pageNow}/{pageSize}?token={token}")
    ResponseResult<String> page(@Param("pageNow") int pageNow, @Param("pageSize") int pageSize, @Param("token") String token);

    @RequestLine("GET /api/category/findAll")
    ResponseResult<String> spfl();

}
