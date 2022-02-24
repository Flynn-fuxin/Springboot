
package com.flynn.dto.punch;
import com.google.gson.annotations.SerializedName;
import lombok.Data;




@Data
public class Datum {

    @SerializedName("BeginDate")
    private String beginDate;
    @SerializedName("BeginDateWeek")
    private String beginDateWeek;
    @SerializedName("BeginIP")
    private String beginIP;
    @SerializedName("BeginRemark")
    private String beginRemark;
    @SerializedName("BuildingName")
    private String buildingName;
    @SerializedName("CityName")
    private String cityName;
    @SerializedName("EndDate")
    private String endDate;
    @SerializedName("EndIP")
    private String endIP;
    @SerializedName("EndRemark")
    private String endRemark;
    @SerializedName("Id")
    private Long id;
    @SerializedName("Remark")
    private String remark;
    @SerializedName("Time")
    private String time;

}
