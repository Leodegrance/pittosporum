package com.pittosporum.utils;

import com.pittosporum.xmlsql.XMLSQLParse;
import com.pittosporum.xmlsql.XmlSQLMapper;
import com.pittosporum.xmlsql.XmlSQLTemplate;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public final class CommonLoader {
    private static final String SQL_TEMPLATE_FOLDER_PATH = "sqlTemplate";

    public static void loadXmlTemplateToMap() throws IOException, ParserConfigurationException, SAXException {
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        if (currentClassLoader != null) {
            String rootPath = currentClassLoader.getResource(SQL_TEMPLATE_FOLDER_PATH).getPath();

            File file = new File(rootPath);
            if (file == null) {
                return;
            }

            File[] listFiles = file.listFiles();
            List<File> list = Arrays.stream(listFiles).filter(i -> i.getName().endsWith(".xml")).collect(Collectors.toList());

            XMLSQLParse xmlsqlParse = new XMLSQLParse();
            LinkedHashMap<String, List<XmlSQLTemplate>> catalogMap = new LinkedHashMap<>();
            for (File f : list) {
                String fileUri = f.getAbsolutePath();
                xmlsqlParse.parse(fileUri);
                String catalogStr = xmlsqlParse.getCatalog();
                List<XmlSQLTemplate> sqlTemplateList = xmlsqlParse.getXmlSQLTemplateList();
                if (!StringUtils.isEmpty(catalogStr) && !CommonUtil.isEmpty(sqlTemplateList)){
                    catalogMap.put(catalogStr, sqlTemplateList);
                }
            }

            XmlSQLMapper.initDynamicSqlTemplate(catalogMap);
        }
    }
}
