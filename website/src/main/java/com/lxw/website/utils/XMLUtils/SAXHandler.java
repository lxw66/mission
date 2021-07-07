package com.lxw.website.utils.XMLUtils;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LXW
 * @date 2021年05月26日 16:56
 */
public class SAXHandler extends DefaultHandler {
    private Map<String, Object> map;
    private Map<String, Object> childMap;

    private String content = null;//存放节点
    private String id=null;

    @Override
    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        childMap=new HashMap<>();
        map=new HashMap<>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(qName.equals("student")){
            id=attributes.getValue("id");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if(!content.trim().equals("")){
            childMap.put(qName,content);
        }
        if(qName.equals("student")){
            map.put(id,childMap);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        content = new String(ch, start, length);
    }

    public Map<String, Object> getMap(){
        return map;
    }
}
