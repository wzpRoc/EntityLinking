package %packagePrefix%.%tableName%;

import %packagePrefix%.%tableName%.%tableName_upInitial%;
import %packagePrefix%.%tableName%.%tableName_upInitial%BL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: %user%
 * 创建日期: %date%
 * 功能描述：%tableComment%业务逻辑
 */


public class %tableName_upInitial%DetailAction extends BaseDetailAction {
    public %tableName_upInitial%DetailAction() {
        dto = new %tableName_upInitial%();
        bl = new %tableName_upInitial%BL();
    }
}