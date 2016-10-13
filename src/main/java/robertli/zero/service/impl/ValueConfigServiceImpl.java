/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.dao.ValueConfigDao;
import robertli.zero.entity.ValueConfig;
import robertli.zero.service.ValueConfigService;

/**
 *
 * @version 1.0 2016-10-05
 * @author Robert Li
 */
@Component("valueConfigService")
public class ValueConfigServiceImpl implements ValueConfigService {

    @Resource
    private ValueConfigDao valueConfigDao;

    @Override
    public List<String> getNamespaceList() {
        return valueConfigDao.getNamespaceList();
    }

    @Override
    public List<String> getPageNameList(String namespace) {
        return valueConfigDao.getPageNameList(namespace);
    }

    @Override
    public List<ValueConfig> getValueConfigList(String namespace, String pageName) {
        return valueConfigDao.getValueConfigList(namespace, pageName);
    }

    @Override
    public List<String> getValList(String namespace, String pageName, String name) {
        return valueConfigDao.getValList(namespace, pageName, name);
    }

    @Override
    public boolean insertValueConfig(String namespace, String pageName, String name, String val) {
        try {
            valueConfigDao.insertValueConfig(namespace, pageName, name, val);
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return true;
        }
        return false;
    }

    @Override
    public boolean updateValueConfig(String namespace, String pageName, Map<String, List<String>> valueConfigMap) {
        try {
            valueConfigDao.updateValueConfig(namespace, pageName, valueConfigMap);
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return true;
        }
        return false;
    }

}
