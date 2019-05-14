package com.example.android.salesmanager;

import java.util.List;

/**
 * Created by raj garg on 11-05-2019.
 */

public class CommodityClass {
    String CommodityName;
    int quantity;
    int profit;
    List<String> time;

    public CommodityClass(String commodityName, int quantity, int profit, List<String> time) {
        CommodityName = commodityName;
        this.quantity = quantity;
        this.profit = profit;
        this.time = time;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public CommodityClass() {
    }

    public CommodityClass(String commodityName, int quantity, int profit) {
        CommodityName = commodityName;
        this.quantity = quantity;
        this.profit = profit;
    }

    public String getCommodityName() {
        return CommodityName;
    }

    public void setCommodityName(String commodityName) {
        CommodityName = commodityName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }
}
