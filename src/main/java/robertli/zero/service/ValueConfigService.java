/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import java.util.Map;
import robertli.zero.entity.ValueConfig;

/**
 * This service is design for save values for each web pages<br>
 *
 *
 * @version 1.0.1 2016-10-12
 * @author Robert Li
 */
public interface ValueConfigService {

    public List<String> getNamespaceList();

    public List<String> getPageNameList(String namespace);

    public List<ValueConfig> getValueConfigList(String namespace, String pageName);

    public List<String> getValList(String namespace, String pageName, String name);

    public boolean insertValueConfig(String namespace, String pageName, String name, String val);

    public boolean updateValueConfig(String namespace, String pageName, Map<String, List<String>> valueConfigMap);
}
