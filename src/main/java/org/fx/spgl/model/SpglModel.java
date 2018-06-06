package org.fx.spgl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
//忽略没有的字段
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpglModel implements Serializable {

    //商品主键
    private String uuid;
    //商品名称
    private String cname;
    //商品价格
    private Double jg;
    //商品单位
    private String dw;
    //商品规格
    private String ge;
    //商品主图
    private String zt;
    //商品品牌
    private String pp;
    //商品详情
    private String xq;
    //商品销量
    private String xl;
    //所属商家
    private String busid;
    //商品数量
    private int sl;
    //商品类目
    private String lm;
    //商品上架、下架 0:上架，1：下架
    private int sxj;

    private String sxj_string;

    public int getSxj() {
        return sxj;
    }

    public void setSxj(int sxj) {
        this.sxj = sxj;
        this.sxj_string = sxj == 0 ? "否" : "是";
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Double getJg() {
        return jg;
    }

    public void setJg(Double jg) {
        this.jg = jg;
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

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getLm() {
        return lm;
    }

    public void setLm(String lm) {
        this.lm = lm;
    }

    public String getSxj_string() {
        return sxj_string;
    }

    public SpglModel() {
        super();
    }

    public SpglModel(String uuid, String cname, Double jg, String dw, String ge, String zt, String pp, String xq, String xl, String busid, int sl, String lm, int sxj) {
        this.uuid = uuid;
        this.cname = cname;
        this.jg = jg;
        this.dw = dw;
        this.ge = ge;
        this.zt = zt;
        this.pp = pp;
        this.xq = xq;
        this.xl = xl;
        this.busid = busid;
        this.sl = sl;
        this.lm = lm;
        this.sxj = sxj;
    }

    @Override
    public String toString() {
        return "SpglModel{" +
                "uuid='" + uuid + '\'' +
                ", cname='" + cname + '\'' +
                ", jg=" + jg +
                ", dw='" + dw + '\'' +
                ", ge='" + ge + '\'' +
                ", zt='" + zt + '\'' +
                ", pp='" + pp + '\'' +
                ", xq='" + xq + '\'' +
                ", xl='" + xl + '\'' +
                ", busid='" + busid + '\'' +
                ", sl=" + sl +
                ", lm='" + lm + '\'' +
                ", sxj=" + sxj +
                '}';
    }
}
