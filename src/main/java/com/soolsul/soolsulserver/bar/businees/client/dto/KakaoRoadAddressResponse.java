package com.soolsul.soolsulserver.bar.businees.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoRoadAddressResponse {

    private String addressName;

    @JsonProperty("region_1depth_name")
    private String region1DepthName;

    @JsonProperty("region_2depth_name")
    private String region2DepthName;

    @JsonProperty("region_3depth_name")
    private String region3DepthName;

    @JsonProperty("region_3depth_h_name")
    private String region3DepthHName;

    private String roadName;

    private String mainBuildingNo;

    private String subBuildingNo;

    private KakaoRoadAddressResponse(String addressName, String region1DepthName, String region2DepthName, String region3DepthName, String region3DepthHName, String roadName, String mainBuildingNo, String subBuildingNo) {
        this.addressName = addressName;
        this.region1DepthName = region1DepthName;
        this.region2DepthName = region2DepthName;
        this.region3DepthName = region3DepthName;
        this.region3DepthHName = region3DepthHName;
        this.roadName = roadName;
        this.mainBuildingNo = mainBuildingNo;
        this.subBuildingNo = subBuildingNo;
    }

    public static KakaoRoadAddressResponse defaultResponse() {
        return new KakaoRoadAddressResponse("addressName", "region1DepthName", "region2DepthName",
                "region3DepthName", "region3DepthHName", "roadName", "mainBuildingNo", "subBuildingNo"
        );
    }
}
