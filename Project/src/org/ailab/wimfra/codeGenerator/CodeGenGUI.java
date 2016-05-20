package org.ailab.wimfra.codeGenerator;


import org.ailab.wimfra.util.BrowseUtil;
import org.ailab.wimfra.util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * User: Lu Timgming
 * Date: 2009-12-30
 * Time: 19:29:12
 */
public class CodeGenGUI implements ActionListener {
    private static final String mac = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
    private static final String metal = "javax.swing.plaf.metal.MetalLookAndFeel";
    private static final String motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    private static final String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    Parser parser = new Parser();

    JFrame mf;
    JTextArea ta_input;
    JTextArea ta_data;
    JTextArea ta_output;
    JComboBox cb_codeType;

    // 文件输出流
    FileWriter fwDto, fwBl;
    BufferedWriter bwDto, bwBl;

    FileWriter  fJSPWriter, fCSSWriter, fJSWriter;
    BufferedWriter  bJSPWriter, bCSSWriter, bJSWriter;
    /**
     * 创建用户界面
     *
     * @throws Exception
     */
    public void createGui() throws Exception {
        UIManager.setLookAndFeel(windows);

        mf = new JFrame("Code Generator");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxX = screenSize.width;
        int maxY = screenSize.height - 20;

        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mf.setLocation(0, -5);

        ta_input = new JTextArea();
        ta_input.setRows(30);
        JScrollPane jsp_input = new JScrollPane(ta_input);
        ta_data = new JTextArea();
        ta_output = new JTextArea();
        JScrollPane jsp_output = new JScrollPane(ta_output);


        JPanel pnl_cmd = new JPanel();

        JButton btn_all = new JButton("All");
        btn_all.setActionCommand("All");
        pnl_cmd.add(btn_all);
        btn_all.addActionListener(this);

        JButton btn_createTable = new JButton("Create Table");
        btn_createTable.setActionCommand("Create Table");
        pnl_cmd.add(btn_createTable);
        btn_createTable.addActionListener(this);

        JButton btn_dto = new JButton(" DTO ");
        btn_dto.setActionCommand("DTO");
        pnl_cmd.add(btn_dto);
        btn_dto.addActionListener(this);

        JButton btn_dao = new JButton(" DAO ");
        btn_dao.setActionCommand("DAO");
        pnl_cmd.add(btn_dao);
        btn_dao.addActionListener(this);

        JButton btn_bl = new JButton("Business Logic");
        btn_bl.setActionCommand("Business Logic");
        pnl_cmd.add(btn_bl);
        btn_bl.addActionListener(this);

        JSplitPane jsp_left_top = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jsp_input, pnl_cmd);
        JSplitPane jsp_left = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jsp_left_top, ta_data);
        JSplitPane jsp_whole = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp_left, jsp_output);

        mf.add(jsp_whole);


        mf.setSize(maxX, maxY);
        mf.setVisible(true);

        jsp_whole.setDividerLocation(0.4);


    }


    /**
     * 写DTO文件
     *
     * @param text
     */
    public void genDtoFile(String text) {
        // 文件io初始化
        try {
            fwDto = new FileWriter("output/" + parser.getTableName_upInitial() + ".java");
            bwDto = new BufferedWriter(fwDto);

            bwDto.write(text);

            bwDto.close();
            fwDto.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 写BL文件
     *
     * @param text
     */
    public void genBlFile(String text) {
        // 文件io初始化
        try {
            fwBl = new FileWriter("output/" + parser.getTableName_upInitial() + "Bl.java");
            bwBl = new BufferedWriter(fwBl);

            bwBl.write(text);

            bwBl.close();
            fwBl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 事件处理
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        try {
            String txt_output = null;
            parser.input2data(ta_input.getText());

            if ("All".equals(e.getActionCommand())) {
                // 创建目录
                File folder = new File("output\\" + parser.tableName);
                folder.mkdirs();

                // 写建表语句
                FileUtil.write(parser.gen_sql(), folder.getAbsolutePath() + "\\" + parser.tableName + ".sql");

                // 创建代码目录
                File packageFolder = new File(folder.getAbsolutePath() + "\\src\\" + Parser.packagePrefix.replace('.', '\\') + "\\" + parser.tableName);
                packageFolder.mkdirs();
                File jspFolder = new File(folder.getAbsolutePath() + "\\web\\jsp\\" + parser.tableName);
                jspFolder.mkdirs();

                // 写文件
				FileUtil.write(
						parser.gen_dto(),
						packageFolder.getAbsolutePath() + "\\"
								+ parser.getTableName_upInitial() + ".java");
				FileUtil.write(
						parser.gen_by_template(FileUtil
								.getPathFromResource("codeGenerator/template/dao.tpl")),
						packageFolder.getAbsolutePath() + "\\"
								+ parser.getTableName_upInitial() + "DAO.java");
				FileUtil.write(
						parser.gen_by_template(FileUtil
								.getPathFromResource("codeGenerator/template/bl.tpl")),
						packageFolder.getAbsolutePath() + "\\"
								+ parser.getTableName_upInitial() + "BL.java");
				FileUtil.write(
						parser.gen_by_template(FileUtil
								.getPathFromResource("codeGenerator/template/listAction.tpl")),
						packageFolder.getAbsolutePath() + "\\"
								+ parser.getTableName_upInitial()
								+ "ListAction.java");
				FileUtil.write(
						parser.gen_by_template(FileUtil
								.getPathFromResource("codeGenerator/template/detailAction.tpl")),
						packageFolder.getAbsolutePath() + "\\"
								+ parser.getTableName_upInitial()
								+ "DetailAction.java");
				FileUtil.write(
						parser.gen_by_template(FileUtil
								.getPathFromResource("codeGenerator/template/listCondition.tpl")),
						packageFolder.getAbsolutePath() + "\\"
								+ parser.getTableName_upInitial()
								+ "ListCondition.java");

				FileUtil.write(
						parser.gen_by_template(FileUtil
								.getPathFromResource("codeGenerator/template/listJsp.tpl")),
                        jspFolder.getAbsolutePath() + "\\"
								+ parser.getTableName() + "List.jsp");

                FileUtil.write(
                        parser.gen_by_template(FileUtil
                                .getPathFromResource("codeGenerator/template/detailJsp.tpl")),
                        jspFolder.getAbsolutePath() + "\\"
                                + parser.getTableName() + "Detail.jsp");
                FileUtil.write(
                        parser.gen_by_template(FileUtil
                                .getPathFromResource("codeGenerator/template/struts.tpl")),
                        folder.getAbsolutePath() + "\\"
                                + parser.getTableName() + ".struts.xml");
				

				FileUtil.write("", jspFolder.getAbsolutePath() + "\\"
						+ parser.getTableName() + ".css");
				FileUtil.write("", jspFolder.getAbsolutePath() + "\\"
						+ parser.getTableName() + ".js");
				// 打开结果
                BrowseUtil.browseLocalFile(folder.getAbsolutePath());
            } else if ("DTO".equals(e.getActionCommand())) {
                txt_output = parser.gen_dto();
            } else if ("DAO".equals(e.getActionCommand())) {
                txt_output = parser.gen_by_template(FileUtil.getPathFromResource("codeGenerator/template/dao.tpl"));
            } else if ("Business Logic".equals(e.getActionCommand())) {
                txt_output = parser.gen_by_template(FileUtil.getPathFromResource("codeGenerator/template/bl.tpl"));
            } else if ("Create Table".equals(e.getActionCommand())) {
                txt_output = parser.gen_sql();
            }

            ta_output.setText(txt_output);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        System.out.println("parser.getTableName():"+parser.getTableName());
        System.out.println("parser.keyFieldName:"+parser.keyFieldName);
        System.out.println("parser.keyFieldName_upInitial:"+parser.keyFieldName_upInitial);
        for(int i=0;i<parser.variableList.size();i++){
            System.out.print(parser.variableList.get(i)+" ");
        }
    }


    /**
     * 入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        CodeGenGUI mf = new CodeGenGUI();
        mf.createGui();

    }
}
