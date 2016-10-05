/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.dao.ValueConfigDao;
import robertli.zero.entity.ValueConfig;
import robertli.zero.service.ValueConfigService;

public class ValueConfigServiceImpl implements ValueConfigService {

    @Resource
    private ValueConfigDao valueConfigDao;

    @Override
    public List<String> getDomainList() {
        return valueConfigDao.getDomainList();
    }

    @Override
    public List<String> getActionList(String domain) {
        return valueConfigDao.getActionList(domain);
    }

    @Override
    public List<ValueConfig> getValueConfigList(String domain, String action) {
        return valueConfigDao.getValueConfigList(domain, action);
    }

    @Override
    public List<String> getValueList(String domain, String action, String keyname) {
        return valueConfigDao.getValueList(domain, action, keyname);
    }

    @Override
    public boolean insertValueConfig(String domain, String action, String keyname, String value) {
        try {
            valueConfigDao.insertValueConfig(domain, action, keyname, value);
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return true;
        }
        return false;
    }

    @Override
    public boolean updateValueConfig(String domain, String action, Map<String, List<String>> valueConfigMap) {
        try {
            valueConfigDao.updateValueConfig(domain, action, valueConfigMap);
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return true;
        }
        return false;
    }

}
