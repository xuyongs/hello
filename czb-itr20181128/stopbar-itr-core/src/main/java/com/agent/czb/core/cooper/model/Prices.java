package com.agent.czb.core.cooper.model;

/**
 * Created by Administrator on 2016/1/7.
 */
public class Prices {
    private String id;
    private String carCode;
    private String totalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "id='" + id + '\'' +
                ", carCode='" + carCode + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }
}
