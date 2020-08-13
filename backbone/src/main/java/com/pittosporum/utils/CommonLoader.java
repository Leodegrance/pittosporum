package com.pittosporum.utils;

import com.pittosporum.xmlsql.XmlSQLMapper;
import com.pittosporum.xmlsql.XmlSQLParse;
import com.pittosporum.xmlsql.XmlSQLTemplate;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public final class CommonLoader {
    private static final String SQL_TEMPLATE_FOLDER_PATH = "sqlTemplate";
    private static final String BATCH_JOB_FOLDER_PATH = "/application/src/main/java/com/pittosporum/batchjob";

    public static void copyFolderToDir(){
        try {
            ResourceUtil.copyFileToDir(SQL_TEMPLATE_FOLDER_PATH, "quartzSqlTemplate.xml");
            ResourceUtil.copyFileToDir(SQL_TEMPLATE_FOLDER_PATH, "storeSqlTemplate.xml");
            ResourceUtil.copyFileToDir(SQL_TEMPLATE_FOLDER_PATH, "userSqlTemplate.xml");
            ResourceUtil.copyFileToDir(BATCH_JOB_FOLDER_PATH, "PatchDataJobHandler.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadXmlTemplateToMap() throws IOException, ParserConfigurationException, SAXException {
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        if (currentClassLoader != null) {
            String rootPath = currentClassLoader.getResource(SQL_TEMPLATE_FOLDER_PATH).getPath();
            File file = new File(rootPath);
            if (file == null) {
                return;
            }

            File[] listFiles = file.listFiles();

            log.info("current file loading=>>>>>>>>>>>>>" + listFiles.length);

            List<File> list = Arrays.stream(listFiles).filter(i -> i.getName().endsWith(".xml")).collect(Collectors.toList());

            log.info("current file loading=>>>>>>size>>>>>>>" + list.size());

            XmlSQLParse xmlsqlParse = new XmlSQLParse();
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

            log.info("catalogMap=======>>>>>>>>>>>>>>>." + catalogMap);
            XmlSQLMapper.initDynamicSqlTemplate(catalogMap);
        }
    }
}
