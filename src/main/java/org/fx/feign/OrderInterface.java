package org.fx.feign;

import feign.Param;
import feign.RequestLine;
import org.fx.urils.ResponseResult;

/**
 * @Headers("X-Ping: {token}")
 */
public interface OrderInterface {

    //    查询最新订单
    @RequestLine("GET /api/order/order/findByType?token={token}")
    ResponseResult<String> findByType(@Param("token") String token);

    //    1:完成  2：关闭 0:未完成
    @RequestLine("PUT /api/order/order/UpdateOrDel?uuid={uuid}&type={type}&token={token}")
    ResponseResult<String> update(@Param("uuid") String uuid, @Param("type") int type, @Param("token") String token);

    //    分页查询
    @RequestLine("GET /api/order/page/{pageNum}/{pageSize}/{type}?token={token}")
    ResponseResult<String> page(@Param("pageNum") int pageNum,
                                @Param("pageSize") int pageSize,
                                @Param("type") int type,
                                @Param("token") String token);

    //    根据订单查询商品
    @RequestLine("GET /api/order/pageSp/{pageNum}/{pageSize}/{orid}?token={token}")
    ResponseResult<String> getSp(@Param("pageNum") int pageNum,
                                 @Param("pageSize") int pageSize,
                                 @Param("orid") String orid,
                                 @Param("token") String token);

}
