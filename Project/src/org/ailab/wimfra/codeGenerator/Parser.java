package org.ailab.wimfra.codeGenerator;


import org.ailab.wimfra.util.FileUtil;
import org.ailab.wimfra.util.time.TimeUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Timgming
 * Date: 2009-12-30
 * Time: 20:07:39
 * Desc: 解析器
 */
public class Parser {
    public static String packagePrefix;
    public static String user;
    String tableName;
    String tableName_upInitial;
    String tableComment;
    String keyFieldName;
    String keyFieldName_upInitial;
    String tableHeadCells;
    String tableDataCells;


    // 字段列表
    ArrayList<Variable> variableList;
    // 索引列表
    ArrayList<String> indexList;

    public final int IDX_NAME = 0;
    public final int IDX_TYPE = 1;
    public final int IDX_LENGTH = 2;
    public final int IDX_DEFAULT = 3;
    public final int IDX_NOT_NULL = 4;
    public final int IDX_CONSTRAINT = 5;
    public final int IDX_AUTOINC = 6;
    public final int IDX_COMMENT = 7;


    static {
        List<String> lineList = null;
        try {
            String path = FileUtil.getPathFromResource("codeGenerator/config.cfg");
            lineList = FileUtil.readLines(path);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        packagePrefix = lineList.get(0).split("=")[1].trim();
        user = lineList.get(1).split("=")[1].trim();
    }

    public void input2data(String s_input) {
        cells2data(s_input);
    }


    /**
     * 从excel表格得到数据库列定义
     *
     * @param s_input
     */
    public void cells2data(String s_input) {
        // 变量列表（每个变量代表一个数据库列）
        variableList = new ArrayList<Variable>();

        // 按行分割
        String[] lines = s_input.split("\n");

        // 确定分隔符
        String spliter;
        if (lines[0].indexOf("\t") == -1) {
            spliter = " ";
        } else {
            spliter = "\t";
        }

        // 第一行，表名及表描述
        lines[0] = lines[0].replaceAll("'", "");
        tableName = lines[0].split(spliter)[0];
        tableName_upInitial = upInitial(tableName);
        tableComment = lines[0].split(spliter)[1];

        boolean toCreateIndex = false;

        // 从第二行开始，得到表字段定义
        int i_line;
        for (i_line = 2; i_line < lines.length; i_line++) {
            String line = lines[i_line];
            if (line == null) {
                continue;
            }

            line = line.trim();
            if (line.length() == 0) {
                continue;
            }

            // 如果读取到"INDEXIES"，那么开始处理索引
            if ("INDEXIES".equals(line)) {
                toCreateIndex = true;
                break;
            }

            line = line.replaceAll("'", "");
            line = line.replaceAll("\\(", " ");
            String[] tokens;
            tokens = line.split(spliter);

            // 字段名
            String name = tokens[IDX_NAME];

            // 字段类型
            String dbType = tokens[IDX_TYPE];

            // 字段长度
            String length = null;
            if (tokens.length > IDX_LENGTH) {
                length = tokens[IDX_LENGTH];
            }

            // 字段默认值
            String defaultValue = null;
            if (tokens.length > IDX_DEFAULT) {
                defaultValue = "".equals(tokens[IDX_DEFAULT]) ? null : tokens[IDX_DEFAULT];
            }

            // 是否可为空
            String notNull = null;
            if (tokens.length > IDX_NOT_NULL) {
                notNull = tokens[IDX_NOT_NULL];
            }

            // 约束
            String constraint = null;
            if (tokens.length > IDX_CONSTRAINT) {
                constraint = tokens[IDX_CONSTRAINT];
            }

            // 约束
            String autoInc = null;
            if (tokens.length > IDX_AUTOINC) {
                autoInc = tokens[IDX_AUTOINC];
            }

            // 注释
            String comment = "";
            if (tokens.length > IDX_COMMENT) {
                comment = tokens[IDX_COMMENT];
            }

            // 主键
            if ("primary key".equalsIgnoreCase(constraint)) {
                keyFieldName = name;
                keyFieldName_upInitial = upInitial(keyFieldName);
            }

            // 得到java类型
            String type = dbType;
            type = type.replaceAll(" auto_increment", "");
            type = type.replaceAll(" unsigned", "");
            type = type.replaceAll("tinyint", "int");
            type = type.replaceAll("smallint", "int");
            type = type.replaceAll("bigint", "long");
            type = type.replaceAll("varchar", "String");
            type = type.replaceAll("varbinary", "String");
            type = type.replaceAll("datetime", "String");
            type = type.replaceAll("decimal","int");
            type = type.replaceAll("mediumtext","String");
            type = type.replaceAll("date", "String");
            type = type.replaceAll("text", "String");
            type = type.replaceAll("bool", "boolean");

            variableList.add(new Variable(name, type, dbType, length, defaultValue, notNull, constraint, autoInc, comment));
        }


        // 处理索引
        indexList = new ArrayList<String>();
        i_line++;
        for (; i_line < lines.length; i_line++) {
            String line = lines[i_line];
            if (line == null || line.length() == 0) {
                continue;
            }

            line = line.trim();

            indexList.add(line);
        }

        // 处理列表页数据表格
        tableHeadCells = "";
        tableDataCells = "";
        for(int i=0;i<variableList.size();i++){
            final Variable variable = variableList.get(i);
            tableHeadCells +=
                    "                    <th width=\"10%\">\n" +
                    "                        " + variable.comment + "\n" +
                    "                    </th>\n";
            tableDataCells +=
                    "                    <td>\n" +
                    "                        <%="+tableName+".get" + variable.getUpInitialName() + "()%>\n" +
                    "                    </td>\n";
        }

    }


    /**
     * 生成建表语句
     *
     * @return
     */
    public String gen_sql() {
        StringBuffer sb = new StringBuffer();
        sb.append("# DROP TABLE ").append(tableName).append(";\n");
        sb.append("CREATE TABLE ").append(tableName).append("(\n");

        // 对字段循环处理
        for (int i = 0; i < variableList.size(); i++) {
            Variable v = variableList.get(i);

            // 名字
            sb.append("\t").append(v.name);

            // 字段类型
            sb.append("\t").append(v.dbType);

            // 字段长度
            if ("varchar".equalsIgnoreCase(v.dbType)
                    || "varbinary".equalsIgnoreCase(v.dbType)
                    || "char".equalsIgnoreCase(v.dbType)
                    || "int".equalsIgnoreCase(v.dbType)
                    || "tinyint".equalsIgnoreCase(v.dbType)
                    || "bigint".equalsIgnoreCase(v.dbType)
                    ) {
                if(v.length!=null && !"".equals(v.length.trim())){
                    sb.append("(").append(v.length).append(")");
                }
            }

            // 是否可为空
            if ("yes".equalsIgnoreCase(v.notNull)) {
                v.notNull = "NOT NULL";
            } else if ("no".equalsIgnoreCase(v.notNull)) {
                v.notNull = "";
            }
            sb.append("\t").append(v.notNull);

            // 默认值
            if (v.defaultValue != null) {
                sb.append("\tDEFAULT '").append(v.defaultValue).append("'");
            }

            // 约束
            sb.append("\t").append(v.constraint);

            // 自动增长
            if ("yes".equalsIgnoreCase(v.autoInc)) {
                sb.append("\t").append("auto_increment");
            }

            // 注释
            sb.append("\tcomment '").append(v.comment).append("'");

            if (i < variableList.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append(");\n");

        // 处理索引
        if (indexList != null && indexList.size() > 0) {
            sb.append("\n");

            for (String fieldName : indexList) {
                sb.append("CREATE INDEX IDX_" + fieldName + " ON " + tableName + "(" + fieldName + ");\n");
            }
        }

        return sb.toString();
    }



    /**
     * 生成DTO
     *
     * @return
     */
    public String gen_dto() throws Exception {
        StringBuffer sb_dec = new StringBuffer();
        StringBuffer sb_func = new StringBuffer();

        String path = FileUtil.getPathFromResource("codeGenerator/template/dto.tpl");
        String template = FileUtil.readFile(path);
        sb_dec.append(template
                .replaceAll("%date%", TimeUtil.getYyyy_mm_dd())
                .replaceAll("%packagePrefix%", packagePrefix)
                .replaceAll("%user%", user)
                .replaceAll("%tableComment%", tableComment)
                .replaceAll("%tableName%", tableName)
                .replaceAll("%tableName_upInitial%", tableName_upInitial)
                .trim()
        ).append("\n");

        for (int i = 0; i < variableList.size(); i++) {
            Variable v = variableList.get(i);
            String name_upInitial = upInitial(v.name);

            sb_dec.append("\t// ").append(v.comment).append("\n");
            sb_dec.append("\tprotected ").append(v.type).append(" ").append(v.name).append(";\n\n");

            sb_func.append("\t/**\n")
                    .append("\t * 得到").append(v.comment).append("\n")
                    .append("\t * @return ").append(v.comment).append("\n")
                    .append("\t */\n")
                    .append("\tpublic ").append(v.type).append(" ").append("get").append(name_upInitial).append("() {\n")
                    .append("\t\treturn ").append(v.name).append(";\n")
                    .append("\t}\n\n");

            sb_func.append("\t/**\n")
                    .append("\t * 设置").append(v.comment).append("\n")
                    .append("\t * @param ").append(v.name).append(" ").append(v.comment).append("\n")
                    .append("\t */\n")
                    .append("\tpublic void set").append(name_upInitial).append("(").append(v.type).append(" ").append(v.name).append(") {\n")
                    .append("\t\tthis.").append(v.name).append(" = ").append(v.name).append(";\n")
                    .append("\t}\n\n");
        }

        return sb_dec.append(sb_func).append("}\n").toString();
    }

    public String gen_by_template(String templateFile) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb_resultSetToDto = new StringBuffer();
        StringBuffer sb_sql_insert = new StringBuffer("INSERT INTO ").append(tableName).append(" (");
        // insert语句中的问号
        StringBuffer sb_insert_qm = new StringBuffer();
        StringBuffer sb_sql_update = new StringBuffer("UPDATE ").append(tableName).append(" SET ");
        StringBuffer sb_insert_para = new StringBuffer();
        StringBuffer sb_update_para = new StringBuffer();
        StringBuffer tr_String = new StringBuffer();
        StringBuffer data_td_str = new StringBuffer();

        for (int i = 0; i < variableList.size(); i++) {
            Variable v = variableList.get(i);
            String name_upInitial = upInitial(v.name);
            String type_upInitial = upInitial(v.type);

            // ListJsp中表格的生成
            if(tr_String.length()>0) {
                tr_String.append("\t\t\t\t\t");
            }
            tr_String.append("<td>");
            tr_String.append("<%=").append(tableName).append(".get").append(name_upInitial).append("()%>");
            tr_String.append("</td>\n");

            // DetailJsp中表格的生成

            if (data_td_str.length()>0) {
                data_td_str.append("\t\t\t\t");
            }
            data_td_str.append("<tr>\n");
            data_td_str.append("\t\t\t\t\t<td class=\"label\">").append(v.comment).append(":</td>\n");
            data_td_str.append("\t\t\t\t\t<td class=\"value\">").append("<s:textfield name=\"dto.").append(v.name).append("\"/></td>\n");
            data_td_str.append("\t\t\t\t</tr>\n");

            if (sb_update_para.length() > 0) {
                sb_update_para.append("\t\t");
            }
            if (sb_resultSetToDto.length() > 0) {
                sb_resultSetToDto.append("\t\t");
            }
            if (sb_insert_para.length() > 0) {
                sb_insert_para.append("\t\t");
            }

            if (i != 0) {
                sb_sql_update.append(v.name).append(" = ?");
                if("char".equals(v.type)){
                    sb_update_para.append("paramList.add(String.valueOf(dto.get").append(name_upInitial).append("()));\n");
                }if("date".equals(v.type) || "datetime".equals(v.type)){
                    sb_update_para.append("paramList.add(\"\".equals(dto.get").append(name_upInitial).append("())?null:dto.get").append(name_upInitial).append("());\n");
                }else {
                    sb_update_para.append("paramList.add(dto.get").append(name_upInitial).append("());\n");
                }
            }

            sb_sql_insert.append(v.name);
            sb_insert_qm.append("?");
            if("char".equals(v.type)){
                sb_insert_para.append("paramList.add(String.valueOf(dto.get").append(name_upInitial).append("()));\n");
            }if("date".equals(v.type) || "datetime".equals(v.type)){
                sb_insert_para.append("paramList.add(\"\".equals(dto.get").append(name_upInitial).append("())?null:dto.get").append(name_upInitial).append("());\n");
            }else {
                sb_insert_para.append("paramList.add(dto.get").append(name_upInitial).append("());\n");
            }

            if("char".equals(v.type)){
                sb_resultSetToDto.append("dto.set").append(name_upInitial).append("(getChar(res, \"").append(v.name).append("\"));\n");
            } else {
                sb_resultSetToDto.append("dto.set").append(name_upInitial).append("(res.get").append(type_upInitial).append("(\"").append(v.name).append("\"));\n");
            }

            if (sb_insert_para.length() > 0) {
                sb_sql_insert.append(", ");
                sb_insert_qm.append(", ");
            }

            if (i != 0 && i != variableList.size() - 1) {
                sb_sql_update.append(", ");
            }
        }


        if (", ".equals(sb_sql_insert.substring(sb_sql_insert.length() - 2, sb_sql_insert.length()))) {
            sb_sql_insert.setLength(sb_sql_insert.length() - 2);
        }
        if (", ".equals(sb_insert_qm.substring(sb_insert_qm.length() - 2, sb_insert_qm.length()))) {
            sb_insert_qm.setLength(sb_insert_qm.length() - 2);
        }
        if (", ".equals(sb_sql_update.substring(sb_sql_update.length() - 2, sb_sql_update.length()))) {
            sb_sql_update.setLength(sb_sql_update.length() - 2);
        }

        sb_sql_insert.append(")VALUES(").append(sb_insert_qm).append(")");

        if (keyFieldName == null) {
            keyFieldName = variableList.get(0).name;
        }

        sb_sql_update.append(" WHERE ").append(keyFieldName).append(" = ?");
        sb_update_para.append("\n\t\tparamList.add(dto.get").append(keyFieldName_upInitial).append("());\n");

        File tf = new File(templateFile);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(tf));
            //reader.skip(1);
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                //System.out.println(line);
                line = line
                        .replaceAll("%packagePrefix%", packagePrefix)
                        .replaceAll("%user%", user)
                        .replaceAll("%date%", TimeUtil.getYyyy_mm_dd())
                        .replaceAll("%tableComment%", tableComment)
                        .replaceAll("%tableName%", tableName)
                        .replaceAll("%tableName_upInitial%", tableName_upInitial)
                        .replaceAll("%resultSetToDto%", sb_resultSetToDto.toString())
                        .replaceAll("%sql_insert%", sb_sql_insert.toString())
                        .replaceAll("%insert_para%", sb_insert_para.toString())
                        .replaceAll("%sql_update%", sb_sql_update.toString())
                        .replaceAll("%update_para%", sb_update_para.toString())
                        .replaceAll("%keyFieldName%", keyFieldName)
                        .replaceAll("%keyFieldName_upInitial%", keyFieldName_upInitial)
                        .replaceAll("%tableHeadCells%", tableHeadCells)
                        .replaceAll("%tableDataCells%", tableDataCells)
                        .replaceAll("%getKey%","get"+keyFieldName_upInitial+"()")
                        .replaceAll("%data_td_string%",data_td_str.toString())
                        .replaceAll("%tr_String%",tr_String.toString())
                        //.replaceAll("%%", )
                        ;

                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public static String upInitial(String s) {
        char ci = s.charAt(0);
        ci = Character.toUpperCase(ci);

        return ci + s.substring(1);
    }

    public String getKeyFieldName_upInitial() {
        return keyFieldName_upInitial;
    }

    /**
     * @return the tableName_upInitial
     */
    public String getTableName_upInitial() {
        return tableName_upInitial;
    }
    
    
    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    public String getTableHeadCells(){
        return tableHeadCells;
    }


    public static String readFile(String path) {
        StringBuffer sb = new StringBuffer();
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            //reader.skip(1);
            String strLine;
            while (true) {
                strLine = reader.readLine();
                //System.out.println(strLine);
                if (strLine == null) break;
                sb.append(strLine).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
