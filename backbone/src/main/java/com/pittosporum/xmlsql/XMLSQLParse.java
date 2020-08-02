package com.pittosporum.xmlsql;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jinhua
 */

@Slf4j
public class XMLSQLParse extends DefaultHandler {
    private static final String ELEM_SQLS       = "sqls";
    private static final String ELEM_SQL        = "sql";
    private static final String ATTR_CATALOG    = "catalog";
    private static final String ATTR_KEY        = "key";
    private static final String ATTR_DML_TYPE        = "dmlType";
    private static final String ATTR_REMARK        = "remark";

    @Getter
    private String catalog;

    private String key;

    private String dmlType;

    private String remark;

    private StringBuilder sqlAt;

    @Getter
    List<XmlSQLTemplate> xmlSQLTemplateList;

    public void parse(String fileUri) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        SAXParser sp = spf.newSAXParser();
        sp.parse(fileUri, this);
    }

    // Document Events - START
    @Override
    public void startDocument() throws SAXException {
        xmlSQLTemplateList = new ArrayList<>();
        sqlAt = new StringBuilder();
    }

    // Element Events - START
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase(ELEM_SQLS)) {
            catalog = attributes.getValue(ATTR_CATALOG);
            if (catalog == null) {
                catalog = "default";
            }
        } else if (qName.equalsIgnoreCase(ELEM_SQL)) {
            sqlAt.setLength(0);
            key = attributes.getValue(ATTR_KEY);
            dmlType = attributes.getValue(ATTR_DML_TYPE);
            remark = attributes.getValue(ATTR_REMARK);
        }
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sqlAt.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase(ELEM_SQL)) {
            XmlSQLTemplate xmlSQLTemplate = new XmlSQLTemplate();
            xmlSQLTemplate.setSqlKey(key);
            xmlSQLTemplate.setCatalog(catalog);
            xmlSQLTemplate.setDmlType(dmlType);
            xmlSQLTemplate.setRemark(remark);
            xmlSQLTemplate.setSql(sqlAt.toString());
            xmlSQLTemplateList.add(xmlSQLTemplate);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        log.info(xmlSQLTemplateList.toString());
    }
}
