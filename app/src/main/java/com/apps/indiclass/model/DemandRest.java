package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dell_Cleva on 13/11/2018.
 */

public class DemandRest {

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private List<DemandData> demandData;

    @Override
    public String toString() {
        return "DemandRest{" +
                "status=" + status +
                ", demandData=" + demandData +
                '}';
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<DemandData> getDemandData() {
        return demandData;
    }

    public void setDemandData(List<DemandData> demandData) {
        this.demandData = demandData;
    }
}
