package org.ailab.tem.wim.country;

import org.ailab.tem.wim.country.Country;
import org.ailab.tem.wim.country.CountryBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-12-04
 * 功能描述：国家和地区业务逻辑
 */


public class CountryDetailAction extends BaseDetailAction {
    public CountryDetailAction() {
        dto = new Country();
        bl = new CountryBL();
    }
}
