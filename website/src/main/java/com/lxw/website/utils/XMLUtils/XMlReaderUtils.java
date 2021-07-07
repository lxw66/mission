package com.lxw.website.utils.XMLUtils;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** xml解析工具累
 * @author LXW
 * @date 2021年05月25日 14:34
 */
public class XMlReaderUtils {
    /**
     *Dom
     * Dom解析是将xml文件全部载入到内存，组装成一颗dom树，然后通过节点以及节点之间的关系来解析xml文件,与平台无关,
     * java提供的一种基础的解析XML文件的API,理解较简单，但是由于整个文档都需要载入内存,不适用于文档较大时。
     * @author LXW
     * @date 2021/5/25 15:11
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public Map<String, Object> domReadXml(File file) throws ParserConfigurationException, IOException, SAXException {
        Map<String, Object> map=new HashMap<>();
        Map<String, Object> childMap=new HashMap<>();
        //
        DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document=documentBuilder.parse(file);
        //获取
        NodeList nodeListodeList=document.getElementsByTagName("student");
        Node node=null;
        for (int i=0;i<nodeListodeList.getLength();i++){
                node=nodeListodeList.item(i);
                //获取属性
                NamedNodeMap namedNodeMap=node.getAttributes();
                String id=namedNodeMap.getNamedItem("id").getTextContent();
                //获取book结点的子节点,包含了Test类型的换行
                NodeList childNodeList = node.getChildNodes();
                for(int j=0;j<childNodeList.getLength();j++){
                        Node childNode=childNodeList.item(j);
                        //判断是否位element  防止#text 换行
                        if(childNode instanceof  Element){
                            childMap.put(childNode.getNodeName(),childNode.getTextContent());
                        }
                }
            map.put(id,childMap);
        }
        return map;
    }
        /**SAX方式解析XML
         *基于事件驱动,逐条解析,适用于只处理xml数据，不易编码,而且很难同时访问同一个文档中的多处不同数据
         * @author LXW
         * @date 2021/5/26 16:34
         * @param file
         * @return java.util.Map<java.lang.String,java.lang.Object>
         */
        public Map<String, Object>  saxReadXml(File file) throws ParserConfigurationException, SAXException, IOException {
            Map<String, Object> map=new HashMap<>();
            SAXParserFactory sParserFactory = SAXParserFactory.newInstance();
            SAXParser parser = sParserFactory.newSAXParser();

            SAXHandler handler = new SAXHandler();
            parser.parse(file,handler);
            return handler.getMap();
        }


    public static void main(String[] args) throws Exception {
            File file=new File("E:/test.xml");
            XMlReaderUtils xMlReaderUtils=new XMlReaderUtils();
            xMlReaderUtils.saxReadXml(file);
    }
}
