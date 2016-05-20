package org.ailab.webInformationExtraction;

import org.htmlparser.util.Translate;


/**
 * User: Lu Tingming
 * Time: 2010-11-11 20:53:54
 * Desc: HTML工具
 */
public class HtmlUtil {
    /**
     * 将html转换成文本
     * @param html
     * @return
     */
    public static String htmlToText(String html) {
        return htmlToText(html, true);
    }

    /**
     * 将html转换成文本
     * @param html
     * @param eolToSpace 是否将换行转换为空格
     * @return
     */
    public static String htmlToText(String html, boolean eolToSpace) {
        if (html == null) return "";

        // 半方大的空白做空格处理
        html = html.replace("&#8194;", " ");
        String text = html.trim();

        // 去除<!-- -->
        int startIdx = 0;
        while (true) {
            startIdx = text.indexOf("<!--", startIdx);
            if (startIdx == -1) {
                break;
            } else {
                int endIdx = text.indexOf("-->", startIdx + 4);
                if (endIdx == -1) {
                    //不匹配
                    break;
                } else {
                    text = text.substring(0, startIdx) + text.substring(endIdx + 3);
                }
            }
        }

        // 编码转换为字符，如&nbsp;
        text = Translate.decode(text);
        text = text.replace(" ", " ");
        text = text.replace("\t", " ");
        text = text.replace("–", "-");
        text = text.replace("&endash;", "-");
        text = text.replace("&#xA;", "\r");
        text = text.replace("%2A", "*");
        //text = text.replaceAll("&#x9;", " ");
        text = text.trim();

        // 换行转空格
        if (eolToSpace) {
            text = text.replace("\\r\\n", " ");
            text = text.replace('\r', ' ');
            text = text.replace('\n', ' ');
            text = text.replace("\\r", " ");
            text = text.replace("\\n", " ");
        }

        // 两个空格转换为一个空格
        text = text.replaceAll("[ \t]{2,}", " ");
        while (true) {
            int idx = text.indexOf("  ");
            if (idx == -1) {
                break;
            } else {
                text = text.substring(0, idx) + text.substring(idx + 1);
            }
        }

        return text;
    }

    /**
	 * 把输入HTML文本中的标签转化为小写
	 * @param srcText
	 * @return
	 */
	public static String changeTagToLowCase(String srcText) {
		boolean aFlg = false;// 判斷"<>"用;
		int intCount = 0;// 计算"<>"用;
		String strCurrentLine = null;// 当前行
		String[] dataSave = srcText.split("\r\n");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dataSave.length; i++) {
			aFlg = false;
			intCount = 0;
			for (int j = 0; j < dataSave[i].length(); j++) {
				char cByte = dataSave[i].charAt(j);
				if (cByte == '<' && intCount == 0) {
					aFlg = true;
				}
				if (cByte == '<') {
					++intCount;
				}
				if (cByte == '>') {
					--intCount;
				}
				if (cByte == '>' && intCount == 0) {
					aFlg = false;
				}

				if (aFlg && intCount > 0) {
					// 转成小写
					if (cByte >= 'A' && cByte <= 'Z') { // 大写: cByte >= 'a'
						cByte += 32; // 大写: cByte -= 32;
						sb.append(cByte);
						continue;
					} else {
						sb.append(cByte);
						continue;
					}
				}
				sb.append(cByte);
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}


    public static boolean isAbsoluteUrl(String url) {
        if(url == null) {
            return false;
        } else {
            return url.contains("://");
        }
    }


    /**
     * 移除HTML标签
     * @param html
     * @return
     */
    public static String removeHTMLTags(String html) {
        if(html == null) {
            return null;
        }
        if("".equals(html)) {
            return html;
        }

        String temp = html;
        // 判断是否包含尖括号
        if(html.indexOf('<')>=0 && html.indexOf('>')>=0) {
            // 替换完整的HTML标签
            temp = temp.replaceAll("</?[a-z].*>", "");
        }

        if(temp.indexOf('<')>=0) {
            // 替换不完整的HTML标签
            temp = temp.replace("<div ", "");
        }

        return temp;
    }


    public static void main(String[] args) throws Exception {
        String s = "雅阁 的人还关注\n" +
                "<ul class=\"starListUl slist ulist \"><li><div class=\"fl\"><p><a href=\"http://data.auto.sina.com.cn/159\" target=\"_blank\"><img width=\"98\" height=\"64\" src=\"http://i1.sinaimg.cn/qc/photo_auto/photo/43/37/7192/7192_140.jpg\" /></a></p></div><div class";

        s = removeHTMLTags(s);

        System.out.println(s);
    }
}
