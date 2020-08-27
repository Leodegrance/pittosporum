package com.pittosporum.utils;

import com.pittosporum.xmlsql.XmlSQLMapper;
import com.pittosporum.xmlsql.XmlSQLParse;
import com.pittosporum.xmlsql.XmlSQLTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;
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

    public static void copyFolderToDir(){
        try {
            String current = System.getProperty("java.class.path");
            log.info("current ----->>>>>" + current);
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ResourceUtils.CLASSPATH_URL_PREFIX
                    + "/sqlTemplate/**/*.xml");

            for (Resource resource : resources){
                log.info("resources ----->>>>>" + resource);
                String filename = resource.getFilename();
                ResourceUtil.copyFileToDir(SQL_TEMPLATE_FOLDER_PATH, filename);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void loadXmlTemplateToMap() throws IOException, ParserConfigurationException, SAXException {
            log.info("xml template path " + ResourceUtil.DEFAULT_TMP_DIR_PATH + "/" + SQL_TEMPLATE_FOLDER_PATH);
            String rootPath = ResourceUtil.DEFAULT_TMP_DIR_PATH + "/" + SQL_TEMPLATE_FOLDER_PATH;
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
