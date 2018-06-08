package org.fx.ddgl.model;

/**
 * 订单资料
 * customer_order_table
 */
public class OrderModel {
    //主键
    private String uuid;
    //商品ID
    private String spid;
    //购买数量
    private String sl;
    //配送费
    private Double psf;
    //优惠券ID
    private String yhqid;
    //总价
    private String zj;
    //配送地址
    private String address;
    //接收电话
    private String phone;
    //订单编号
    private String ddbh;
    //创建时间
    private String cjTime;
    //创建人
    private String account;
    //商家id
    private String busid;
    //订单状态  -1:最新订单 0:未完成，1：已完成，2：已关闭
    private int type;

    public OrderModel() {
    }

    public OrderModel(String uuid, String spid, String sl, Double psf, String yhqid, String zj, String address, String phone, String ddbh, String cjTime, String account, String busid, int type) {
        this.uuid = uuid;
        this.spid = spid;
        this.sl = sl;
        this.psf = psf;
        this.yhqid = yhqid;
        this.zj = zj;
        this.address = address;
        this.phone = phone;
        this.ddbh = ddbh;
        this.cjTime = cjTime;
        this.account = account;
        this.busid = busid;
        this.type = type;
    }

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

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public Double getPsf() {
        return psf;
    }

    public void setPsf(Double psf) {
        this.psf = psf;
    }

    public String getYhqid() {
        return yhqid;
    }

    public void setYhqid(String yhqid) {
        this.yhqid = yhqid;
    }

    public String getZj() {
        return zj;
    }

    public void setZj(String zj) {
        this.zj = zj;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public String getCjTime() {
        return cjTime;
    }

    public void setCjTime(String cjTime) {
        this.cjTime = cjTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "uuid='" + uuid + '\'' +
                ", spid='" + spid + '\'' +
                ", sl='" + sl + '\'' +
                ", psf=" + psf +
                ", yhqid='" + yhqid + '\'' +
                ", zj='" + zj + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", ddbh='" + ddbh + '\'' +
                ", cjTime='" + cjTime + '\'' +
                ", account='" + account + '\'' +
                ", busid='" + busid + '\'' +
                ", type=" + type +
                '}';
    }
}
