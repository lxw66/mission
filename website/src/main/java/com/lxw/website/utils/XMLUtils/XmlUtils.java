package com.lxw.website.utils.XMLUtils;

import com.sun.beans.decoder.DocumentHandler;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.swing.event.DocumentListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * @author LXW    生成xml
 * @date 2021年05月14日 15:38
 */
@Slf4j
@Component
public  class XmlUtils {

    /**
     *   Dom  方式生成xml
     * @author LXW
     * @date 2021/5/14 15:40
     * @param file
     *
     * 1.创建一个DocumentBuilderFactory对象，静态方法newInstance()
     * 2.创建一个DocumentBuilder对象，DocumentBuilderFactory实例的newDocumentBuilder()方法
     * 3.通过DocumentBuilder对象调用newDocument方法，返回一个Document对象
     * 4.通过Document对象调用creatElement方法，创建一个节点元素，返回Element对象
     * 5.通过Element对象的appendChild方法向该元素添加子元素
     * 6.通过Element对象的setAttribute方法，给元素添加属性
     * ……
     * 7.通过TransformerFactory的静态方法newInstance()，创建TransformerFactory对象
     * 8.通过TransformerFactory的newTransformer(),创建Transformer对象
     * 9.Transformer对象的setOutputProperty(OutputKeys.INDENT,”yes”)方法可以用来换行(可选)
     * 10.调用Transformer的transform()方法将创建的XML转换成Result,Result可通过new File()构建输出文件
     *
     */
    public void domXml(File file)  {
        try {
            //1.创建一个DocumentBuilderFactory对象，静态方法newInstance()
            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
           // 2.创建一个DocumentBuilder对象，DocumentBuilderFactory实例的newDocumentBuilder()方法
            DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
            //3.通过DocumentBuilder对象调用newDocument方法，返回一个Document对象
            Document document=documentBuilder.newDocument();
            //standalone  用来表示该文件是否呼叫其它外部的文件。若值是 ”yes” 表示没有呼叫外部文件，若值是 ”no” 则表示有呼叫外部文件。默认值是 “yes”。
            document.setXmlStandalone(true);
            //创建节点
            Element listElement=document.createElement("Lists");
            Element enElement=document.createElement("entitys");
           for(int i=0;i<=10;i++){
               Element element=document.createElement("entitys"+i);
               //创建属性
               element.setAttribute("name","name"+i);
               element.setAttribute("sex","sex"+i);
               //写节点之间的字
               element.setTextContent(String.valueOf(i));
               enElement.appendChild(element);
           }
            enElement.setAttribute("num","2");
            enElement.setAttribute("sex","2");
            listElement.appendChild(enElement);
            document.appendChild(listElement);
            //7.通过TransformerFactory的静态方法newInstance()，创建TransformerFactory对象
            TransformerFactory transformerFactory=TransformerFactory.newInstance();
            //8.通过TransformerFactory的newTransformer(),创建Transformer对象
            Transformer transformer=transformerFactory.newTransformer();
            //换行         Transformer对象的setOutputProperty(OutputKeys.INDENT,”yes”)方法可以用来换行(可选)
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            //9调用Transformer的transform()方法将创建的XML转换成Result,Result可通过new File()构建输出文件
            transformer.transform(new DOMSource(document),new StreamResult(file));


        }catch (ParserConfigurationException e){
            log.info("创建DocumentBuilder对象失败"+e.toString());
        }catch (TransformerConfigurationException e){
            log.info("创建transformer对象失败"+e.toString());
        } catch (TransformerException e) {
            log.info("转换xml"+e.toString());
        }
    }

    /**
     *  dom4j生成xml
     * @author LXW
     * @date 2021/5/14 16:21
     * @param file
     * 1.通过DocumentHelper的静态方法createDocument创建一个Document对象
     * 2.创建节点:Document对象的addElement方法 返回Element对象
     * 3.节点添加属性:Element对象的addAttribute方法
     * 4.节点添加内容:Element对象的setText、add方法
     * 5.创建XMLWriter对象 (new) (可使用输出流、OutputFormat作为参数,分别给出输出地址和格式)
     * 6.XMLWriter对象的setEscapeText方法可以设置字符是否转义，默认true(转义)
     * 7.调用XMLWriter对象的write方法,Document对象作为参数
     * 8.关闭XMLWriter对象，以及相关资源的close操作
     */
    public void dom4jxml(File file) throws IOException, DocumentException {
        //使用dom4j中的Document  而不是w3
        org.dom4j.Document document= DocumentHelper.createDocument();
        //设置节点
        org.dom4j.Element element=document.addElement("normal");
        //element.addAttribute("name","二中");
        element.setText("12345");
        //
        /*org.dom4j.Element stuElement=element.addElement("Students");
        stuElement.addAttribute("num","10");
        stuElement.addAttribute("sname","二中");
        for(int i=0;i<=10;i++){
            org.dom4j.Element sElement=stuElement.addElement("stu"+i);
            sElement.addAttribute("num",String.valueOf(i));
            sElement.setText("学生"+i);
        }*/
        //5.创建XMLWriter对象 (new) (可使用输出流、OutputFormat作为参数,分别给出输出地址和格式)
        XMLWriter xmlWriter=new XMLWriter(new FileOutputStream(file), OutputFormat.createCompactFormat());
        xmlWriter.setEscapeText(false);//字符是否转义,默认true
        //7.调用XMLWriter对象的write方法,Document对象作为参数
        xmlWriter.write(document);
        //8.关闭XMLWriter对象，以及相关资源的close操作
        xmlWriter.close();

    }
    /**   sax  生成xml
     *1.用ArrayList存储xml所需的对象集合
     *2.通过SAXTransformerFactory的静态方法newInstance()创建一个SAXTransformerFactory对象
     *3.通过SAXTransformerFactory对象的newTransformerHandler()创建一个TransformerHandler对象
     *4.通过TransformerHandler对象的getTransformer()创建一个Transformer对象
     * 5.Transformer对象的setOutputProperty(OutputKeys.INDENT,”yes”)可以实现换行(可选)
     * 6.Transformer对象的setOutputProperty(OutputKeys.ENCODING,”编码名称”)可以实现编码格式(可选)
     * 7.创建Result对象，并使用TransformerHandler对象的setResult(Result)方法使其与TransformerHandler关联
     * 8.使用TransformerHandler对象的进行xml文件内容的编写(遍历ArrayList创建各个节点)
     * startDocument();//开始文档
     * endDocument();//结束文档
     * startElement(String uri,String localName,String qName,Attributes atts)；//元素标签开始
     * endElement(String uri,String localName,String qName,Attributes atts)；//元素标签结束
     * @author LXW
     * @date 2021/5/25 13:18
     * @param file
     */
    public void saxXml(File file) throws TransformerConfigurationException, SAXException {
        //通过SAXTransformerFactory的静态方法newInstance()创建一个SAXTransformerFactory对象
        SAXTransformerFactory saxTransformerFactory=(SAXTransformerFactory) SAXTransformerFactory.newInstance();
        //通过SAXTransformerFactory对象的newTransformerHandler()创建一个TransformerHandler对象
        TransformerHandler transformerHandler=saxTransformerFactory.newTransformerHandler();
        //通过TransformerHandler对象的getTransformer()创建一个Transformer对象
        Transformer transformer=transformerHandler.getTransformer();
        //Transformer对象的setOutputProperty(OutputKeys.INDENT,”yes”)可以实现换行(可选)
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        //Transformer对象的setOutputProperty(OutputKeys.ENCODING,”编码名称”)可以实现编码格式(可选)
        transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
        //创建Result对象，并使用TransformerHandler对象的setResult(Result)方法使其与TransformerHandler关联
        StreamResult streamResult=new StreamResult(file);
        transformerHandler.setResult(streamResult);
        //startDocument();//开始文档
        transformerHandler.startDocument();
        //startElement(String uri,String localName,String qName,Attributes atts)；//元素标签开始
        transformerHandler.startElement("","","schools",null);
        transformerHandler.startElement("","","students",null);
        for (int i=0;i<=3;i++){
            AttributesImpl  attributes=new AttributesImpl();//设置节点属性
            attributes.addAttribute("","","id","",i+"");
            transformerHandler.startElement("","","student",attributes);

                transformerHandler.startElement("","","name",null);
                 transformerHandler.characters(("姓名-"+i).toCharArray(),0,("姓名-"+i).length());////元素标签内容
                transformerHandler.endElement("","","name");
            transformerHandler.endElement("","","student");
        }
        transformerHandler.endElement("","","students");
        //endElement(String uri,String localName,String qName,Attributes atts)；//元素标签结束
        transformerHandler.endElement("","","schools");
        transformerHandler.endDocument();
    }
    /** JDOM  生成xml
     * pom添加依赖
     * 1.创建Document对象 (new)
     * 2.创建Element对象 (new) 并向其中添加属性/内容/子节点
     * 3.向Document对象中添加Element节点 addContent/setContent
     * (或者创建Document对象时将Element对象作为参数等)
     * 4.创建XMLOutputter对象 (new) 并调用output方法生成xml文档
     *
     * @author LXW
     * @date 2021/5/25 13:49
     * @param file
     */
    public void jDomXml(File file) throws IOException {
        //1.创建Document对象 (new)
        org.jdom2.Document document=new org.jdom2.Document();
        //创建Element对象 (new) 并向其中添加属性/内容/子节点
        org.jdom2.Element element1=new org.jdom2.Element("rss");
        element1.setAttribute("ver","lalal");//属性

        org.jdom2.Element element2=new org.jdom2.Element("students");
        for (int i=0;i<=3;i++){
            org.jdom2.Element element3=new org.jdom2.Element("student");
            element3.setAttribute("id",i+"");
            org.jdom2.Element element4=new org.jdom2.Element("name");
            element4.setText("学生-"+i);//内容
            org.jdom2.Element element5=new org.jdom2.Element("age");
            element5.setAttribute("attr",i+"");
            element5.setText("年纪-"+i);
            element3.addContent(element4);
            element3.addContent(element5);
            element2.addContent(element3);
        }
        element1.addContent(element2);
        document.setRootElement(element1);

        Format format = Format.getCompactFormat();
        // 设置换行Tab或空格
        format.setIndent("	");
        format.setEncoding("UTF-8");

        XMLOutputter xmlOutputter=new XMLOutputter(format);
        xmlOutputter.output(document,new FileWriter(file));
    }

}