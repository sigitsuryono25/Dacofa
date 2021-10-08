package com.surelabsid.lti.dacofa.model

import com.surelabsid.lti.dacofa.database.DetailTangkapan
import com.surelabsid.lti.dacofa.database.HeaderLokasi

class SyncModel {
    var userid: String? = null
    var detail: List<DetailTangkapan>? = null
    var lokasi: List<HeaderLokasi>? = null
}