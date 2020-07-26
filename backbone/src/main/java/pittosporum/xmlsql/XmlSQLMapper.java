package pittosporum.xmlsql;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import pittosporum.utils.CommonUtil;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
@Slf4j
public final class XmlSQLMapper {
    @Setter@Getter
    private static LinkedHashMap<String, List<XmlSQLTemplate>> catalogMap;

    public static String receiveSql(String catalogStr, String key){
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
}
