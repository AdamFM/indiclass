package com.apps.indiclass.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell_Cleva on 19/11/2018.
 */

public class StaticDRest {
    @SerializedName("response")
    private StaticDRes staticDRes;

    public StaticDRes getStaticDRes() {
        return staticDRes;
    }

    public void setStaticDRes(StaticDRes staticDRes) {
        this.staticDRes = staticDRes;
    }
}
