package org.ailab.wimfra.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 功能：去除不相关的段落
 */
public class DeletePara {
	private final static String path = Config.getClasses_dir()+"file/nonRelativePara.txt";	
	private static HashSet<String> errorTextSet = new HashSet<String>();
	static {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * 初始化不相关段落
	 */
	private static void init() throws Exception {
		List<String> list = FileUtil.readLines(path);
        for (String aList : list) {
            String str = aList.trim();
            errorTextSet.add(str);
        }
	}

	/**
	 * 把不相关段落替换为空
	 * @throws ClassNotFoundException
	 * @throws java.sql.SQLException
	 */
	public String replace(String text) {
		String newText = "";
		boolean changed = false;
		if (text.contains("\n")) {				
			String[] paras = text.split("\n");
			for (int i = 0; i < paras.length; i++) {
				String p = paras[i].trim();
				if (paraInSet(p)) {
					paras[i] = "";
					changed = true;
				}
				if (paraHasRelative(p)) {
					paras[i] = "";
					changed = true;
				}
				newText += paras[i];
				if (!paras[i].equalsIgnoreCase("")) {
					newText += "\n";
				}
			}
		} else {
			String p = text;
			if (paraInSet(p)) {			
				newText = "";
				changed = true;
			}
			if (paraHasRelative(p)) {
				newText = "";	
				changed = true;
			}
		}
		if(changed)
			return newText;
		else
			return text;
	}	
	
	private boolean paraHasRelative(String p) {
        return p.contains("条相关");
    }

	private boolean paraInSet(String p) {
        for (String anErrorTextSet : errorTextSet) {
            String item = anErrorTextSet.trim();
            if (item.equalsIgnoreCase(p.trim())) {
                return true;
            }
        }
		return false;
	}
	/**
	 * 处理摘要的段落，把最长段落返回
	 * @param abst
	 * @return
	 */
	public String processAbst(String abst){
		if(abst.trim().equalsIgnoreCase("")){
			return "";
		}
		String res = "";		
		if (abst.contains("\n")) {				
			String[] paras = abst.split("\n");
            for (String para : paras) {
                String p = para.trim();
                if (p.length() > res.length())
                    res = p;
            }
		} else {
			if(res.equalsIgnoreCase("......")) {
                res = "";
            }
			res = abst;
		}
		return res;
	}
}
