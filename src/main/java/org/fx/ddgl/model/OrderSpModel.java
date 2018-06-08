package org.fx.ddgl.model;

import java.io.Serializable;

/**
 * @author ld
 * @name
 * @table ordersp_table
 * @remarks
 */
public class OrderSpModel implements Serializable {

    private String uuid;
    //    商品id
    private String spid;
    //    购买数量
    private String spsl;
    //    购买时单价
    private String spdj;
    //    购买时总价
    private String spzj;
    //商品单位
    private String dw;
    //商品规格
    private String ge;
    //商品品牌
    private String pp;
    //    订单id
    private String orderid;
    //    状态
    private String zt;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getSpsl() {
        return spsl;
    }

    public void setSpsl(String spsl) {
        this.spsl = spsl;
    }

    public String getSpdj() {
        return spdj;
    }

    public void setSpdj(String spdj) {
        this.spdj = spdj;
    }

    public String getSpzj() {
        return spzj;
    }

    public void setSpzj(String spzj) {
        this.spzj = spzj;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getGe() {
        return ge;
    }

    public void setGe(String ge) {
        this.ge = ge;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public OrderSpModel() {
        super();
    }

    public OrderSpModel(String uuid, String spid, String spsl, String spdj, String spzj, String dw, String ge, String pp, String orderid, String zt) {
        this.uuid = uuid;
        this.spid = spid;
        this.spsl = spsl;
        this.spdj = spdj;
        this.spzj = spzj;
        this.dw = dw;
        this.ge = ge;
        this.pp = pp;
        this.orderid = orderid;
        this.zt = zt;
    }

    @Override
    public String toString() {
        return "OrderSpModel{" +
                "uuid='" + uuid + '\'' +
                ", spid='" + spid + '\'' +
                ", spsl='" + spsl + '\'' +
                ", spdj='" + spdj + '\'' +
                ", spzj='" + spzj + '\'' +
                ", dw='" + dw + '\'' +
                ", ge='" + ge + '\'' +
                ", pp='" + pp + '\'' +
                ", orderid='" + orderid + '\'' +
                ", zt='" + zt + '\'' +
                '}';
    }
}
