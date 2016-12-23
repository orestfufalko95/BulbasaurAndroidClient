package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

import java.util.List;

/**
 * Created by orestfufalko on 04.12.2016.
 */

public class SignUpResponseDTO {

    private boolean isSuccessful;
    private List<PropertyInfo> propertyInfos = null;

    /**
     *
     * @return
     * The isSuccessful
     */
    public boolean isIsSuccessful() {
        return isSuccessful;
    }

    /**
     *
     * @param isSuccessful
     * The isSuccessful
     */
    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    /**
     *
     * @return
     * The propertyInfos
     */
    public List<PropertyInfo> getPropertyInfos() {
        return propertyInfos;
    }

    /**
     *
     * @param propertyInfos
     * The propertyInfos
     */
    public void setPropertyInfos(List<PropertyInfo> propertyInfos) {
        this.propertyInfos = propertyInfos;
    }

}
