package org.ailab.wimfra.core;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-7-5
 * Time: 下午8:05
 */
public class SysMessage {
    protected String module;
    protected SysMessageType type;
    protected Object data;

    public SysMessage(String module, SysMessageType type) {
        this.module = module;
        this.type = type;
    }

    public SysMessage(String module, SysMessageType type, Object data) {
        this.module = module;
        this.type = type;
        this.data = data;
    }

    public SysMessageType getType() {
        return type;
    }

    public void setType(SysMessageType type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
