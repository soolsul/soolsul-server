package com.soolsul.soolsulserver.curation.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class CurationNotFoundException extends SoolsulBusinessException {

    private static final String CURATION_NOT_FOUND_MESSAGE_FORMAT = "해당 큐레이션을 조회할 수 없습니다.(curationId : %s)";

    public CurationNotFoundException(String curationId) {
        super(String.format(CURATION_NOT_FOUND_MESSAGE_FORMAT, curationId));
    }

}
