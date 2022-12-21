package com.soolsul.soolsulserver.bar.businees.client;

import com.soolsul.soolsulserver.bar.common.dto.response.AddressConvertResponse;
import com.soolsul.soolsulserver.bar.common.dto.response.AddressLookupResponse;

public interface PlaceApiService {

    AddressLookupResponse searchAddress(String query);

    AddressConvertResponse convertAddress(double longitude, double latitude);
}
