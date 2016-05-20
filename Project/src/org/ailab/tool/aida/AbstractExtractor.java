package org.ailab.tool.aida;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: robby
 * Date: 15-12-29
 * Time: 下午8:46
 * To change this template use File | Settings | File Templates.
 */
public class AbstractExtractor {
    public static void main(String[] args) {
        SAXParser parser = null;
        try {
            //构建SAXParser
            parser = SAXParserFactory.newInstance().newSAXParser();
            //实例化  DefaultHandler对象
            SaxParseXml parseXml=new SaxParseXml();
            //得到xml文件夹
            File xmlfold = new File("D:\\Wikipedia\\en20151102\\abstracts");
            //获得文件夹下面的所有文件
            File[] filelist = xmlfold.listFiles();
            //遍历每一个xml文件，用sax解析
//			for(int i=0; i<filelist.length; i++)
			for(int i=0; i<1; i++)
            {
                File file = filelist[i];
//                file = new File("C:/Users/robby/Desktop/abstracts/enwiki-20151102-abstract13.xml");
                System.out.println(file.getAbsolutePath());

                //加载资源文件 转化为一个输入流
                InputStream stream = new FileInputStream(file);
                //调用parse()方法
                parser.parse(stream, parseXml);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}