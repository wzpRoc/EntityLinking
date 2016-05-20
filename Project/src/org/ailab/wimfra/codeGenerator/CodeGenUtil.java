package org.ailab.wimfra.codeGenerator;

import org.ailab.entityLinking_old.wim.entity.Entity;

import java.lang.reflect.Field;

/**
 * User: Lu Tingming
 * Date: 15-6-14
 * Time: 下午8:37
 * Desc:
 */
public class CodeGenUtil {
    public static void genByTemplate(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder sb_resultSetToDto = new StringBuilder();
        for(int i=0; i<fields.length; i++) {
            Field field = fields[i];
            String name  = field.getName();
            String name_upInitial = upInitial(name);
            String type = lastAfter(field.getType().getName(), ".");
            String type_upInitial = upInitial(type);

            sb_resultSetToDto.append("dto.set").append(name_upInitial).append("(res.get").append(type_upInitial).append("(\"").append(name).append("\"));\n");
        }

        System.out.println(sb_resultSetToDto);
    }

    private static String upInitial(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private static String lastAfter(String str, String afterWhat) {
        int lastIdx = str.lastIndexOf(afterWhat);
        if(lastIdx<0) {
            return str;
        } else {
            return str.substring(lastIdx);
        }
    }

    public static void main(String[] args) {
        genByTemplate(Entity.class);
    }
}
