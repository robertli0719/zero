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
 * @version 1.0 2016-10-04
 * @author Robert Li
 */
public interface ValueConfigService {

    public List<String> getDomainList();

    public List<String> getActionList(String domain);

    public List<ValueConfig> getValueConfigList(String domain, String action);

    public List<String> getValueList(String domain, String action, String keyname);

    public boolean insertValueConfig(String domain, String action, String keyname, String value);

    public boolean updateValueConfig(String domain, String action, Map<String, List<String>> valueConfigMap);
}
