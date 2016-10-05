/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import java.util.Map;
import robertli.zero.entity.ValueConfig;

/**
 *
 * @author Robert Li
 */
public interface ValueConfigDao extends GenericDao<ValueConfig, Integer> {

    public List<String> getDomainList();

    public List<String> getActionList(String domain);

    public List<ValueConfig> getValueConfigList(String domain, String action);

    public List<String> getValueList(String domain, String action, String keyname);

    public void insertValueConfig(String domain, String action, String keyname, String value);

    public void deleteValueConfig(String domain, String action);
    
    public void updateValueConfig(String domain, String action, Map<String, List<String>> valueConfigMap);
}
