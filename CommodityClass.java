package com.example.android.salesmanager;

/**
 * Created by raj garg on 11-05-2019.
 */

public class CommodityClass {
    String CommodityName;
    int quantity;
    int profit;

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
