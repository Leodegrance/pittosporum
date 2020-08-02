package com.pittosporum.xmlsql;

import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.utils.CommonUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.core.TemplateClassResolver;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
@Slf4j
public final class XmlSQLMapper {
    @Setter@Getter
    private static LinkedHashMap<String, List<XmlSQLTemplate>> catalogMap;

    private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

    public static void initDynamicSqlTemplate(LinkedHashMap<String, List<XmlSQLTemplate>> linkedHashMap){
        XmlSQLMapper.catalogMap = linkedHashMap;

        StringTemplateLoader loader = new StringTemplateLoader();

        for (Map.Entry<String, List<XmlSQLTemplate>> entry : linkedHashMap.entrySet()){
            List<XmlSQLTemplate> list = entry.getValue();
            for (XmlSQLTemplate i : list){
                if (isDynamicSql(i.getSql()) && "select".equals(i.getDmlType())) {
                    loader.putTemplate(getKey(i.getCatalog(), i.getSqlKey()), i.getSql());
                }
            }
        }

        cfg.setTemplateLoader(loader);
        cfg.setNewBuiltinClassResolver(TemplateClassResolver.SAFER_RESOLVER);
    }

    private static String getKey(String catalog, String key) {
        return catalog + ".-salt-." + key;
    }

    private String getCacheKey(String catalog, String key) {
        return catalog + ".-salt-." + key;
    }

    private static boolean isDynamicSql(String rawSqlStat) {
        return !StringUtils.isEmpty(rawSqlStat)
                && (rawSqlStat.indexOf("<#") != -1 && rawSqlStat.indexOf("</#") != -1
                || rawSqlStat.indexOf("${") != -1);
    }

    private static String getSql(String catalogStr, String key){
        if (StringUtils.isEmpty(catalogStr) || StringUtils.isEmpty(key)){
            log.info("catalogStr or key is empty !!");
            return null;
        }

        if (catalogMap == null || CommonUtil.isEmpty(catalogMap)){
            log.info("catalogMap is empty !!");
            return null;
        }

        List<XmlSQLTemplate> xmlSQLTemplateList = catalogMap.get(catalogStr);
        if (CommonUtil.isEmpty(xmlSQLTemplateList)){
            log.info("xml sql template is empty!!");
            return null;
        }

        return xmlSQLTemplateList.stream()
                .filter(i -> i.getSqlKey().equals(key)).findFirst().orElse(new XmlSQLTemplate()).getSql();
    }

    public static String receiveSql(String catalog, String key){
        return receiveSql(catalog, key, new QueryParam());
    }

    public static String receiveSql(String catalog, String key, QueryParam queryParam) {
        String sqlStat = getSql(catalog, key);
        try {
            if (isDynamicSql(sqlStat)) {
                StringWriter writer = new StringWriter();
                Template temp = cfg.getTemplate(getKey(catalog, key));
                temp.process(queryParam.getFilterParams(), writer);
                writer.flush();
                sqlStat = writer.toString();
            }
        }catch (IOException | TemplateException e) {
            log.error(e.getMessage(), e);
        }
        return sqlStat;
    }
}
