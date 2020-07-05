package pittosporum.core;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public final class ProfileMapper{
    public static Map<Integer, DataBaseProfile> profileMap = new HashMap<>();

    public static void initProfileMap(List<DataBaseProfile> dataBaseProfiles){
        if (dataBaseProfiles == null || dataBaseProfiles.isEmpty()){
            return;
        }

        for (DataBaseProfile i : dataBaseProfiles){
            int profileId = i.getId();
            profileMap.put(profileId, i);
        }
    }

    public static DataBaseProfile getDataBaseProfileByProfileId(String id){
        if (StringUtils.isEmpty(id)){
            return null;
        }

        return profileMap.get(id);
    }

    public static String getProfileNameById(Integer id){
        if (id == null){
            return null;
        }

        DataBaseProfile val = profileMap.get(id);

        return val != null ? val.getProfileName() : "";
    }

    private ProfileMapper(){}
}
