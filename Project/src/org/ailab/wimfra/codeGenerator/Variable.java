package org.ailab.wimfra.codeGenerator;

/**
 * User: Lu Timgming
 * Date: 2009-12-30
 * Time: 20:09:13
 */
public class Variable {
    String name;
    String type;
    String dbType;
    String length;
    String defaultValue;
    String notNull;
    String constraint;
    String autoInc;
    String comment;

    public Variable(String name, String type, String dbType, String length, String defaultValue, String canBeNull, String constraint, String autoInc, String comment) {
        this.name = name;
        this.type = type;
        this.dbType = dbType;
        this.length = length;
        this.defaultValue = defaultValue;
        this.notNull = canBeNull;
        this.constraint = constraint;
        this.autoInc = autoInc;
        this.comment = comment;
    }

    public String getUpInitialName() {
        return Parser.upInitial(name);
    }
}
