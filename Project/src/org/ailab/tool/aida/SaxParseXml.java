package org.ailab.tool.aida;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.StringUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;





/**
 * 功能描述:采用sax方式解析XML<br>
 * 
 * @author sxyx2008
 *
 */
public class SaxParseXml extends DefaultHandler{
    private int count;
    //存放遍历集合
    private List<Abstract> list;
    //构建Student对象
    private Abstract abst;
    //用来存放每次遍历后的元素名称(节点名称)
    private String tagName;
    Connection conn;
    
    public List<Abstract> getList() {
        return list;
    }


    public void setList(List<Abstract> list) {
        this.list = list;
    }


    public Abstract getStudent() {
        return abst;
    }


    public void setStudent(Abstract student) {
        this.abst = student;
    }


    public String getTagName() {
        return tagName;
    }


    public void setTagName(String tagName) {
        this.tagName = tagName;
    }


    //只调用一次  初始化list集合  
    @Override
    public void startDocument() throws SAXException {
        list=new ArrayList<Abstract>();
        count = 0;
    }
    
    
    //调用多次    开始解析
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if(qName.equals("doc")){
            abst=new Abstract();
        }
        this.tagName=qName;
    }
    
    
    //调用多次  
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if(qName.equals("doc")){
            if(StringUtil.isEmpty(this.abst.abst)
                    || this.abst.abst.startsWith("#REDIRECT ")) {
                // do nothing
            } else {
                this.list.add(this.abst);
                this.abst=null;
                count++;
                if(count%10000==0) {
                    System.out.println("processing "+count);
                }
            }
        }
        this.tagName=null;
    }
    
    
    //只调用一次
    @Override
    public void endDocument() throws SAXException {
        try {
            DBUtil.batchInsert("page_abst", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //调用多次
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if(this.tagName!=null){
            String data=new String(ch,start,length);
           
            if(this.tagName.equals("url")){
            	String url = data.replace("https://en.wikipedia.org/wzp.wiki/", "");
                if("_characters".equals(url)) {
                    System.out.println();
                }
                this.abst.setTitle(url);
            }
            else if(this.tagName.equals("abstract")){
                this.abst.setAbstract(data);
            }
        }
    }
}