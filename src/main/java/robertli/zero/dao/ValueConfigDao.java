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

    public List<String> getNamespaceList();

    public List<String> getPageNameList(String namespace);

    public List<ValueConfig> getValueConfigList(String namespace, String pageName);

    public List<String> getValList(String namespace, String pageName, String name);

    public void insertValueConfig(String namespace, String pageName, String name, String val);

    public void deleteValueConfig(String namespace, String pageName);

    public void updateValueConfig(String namespace, String pageName, Map<String, List<String>> valueConfigMap);
}
