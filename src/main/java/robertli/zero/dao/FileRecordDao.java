/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.FileRecord;

/**
 *
 * @author Robert Li
 */
public interface FileRecordDao extends GenericDao<FileRecord, String> {

    public FileRecord saveFileRecord(String name, String type);

    /**
     * list the FileRecord which has been mark removed.
     *
     * @param lifeMinute the life time in minute
     * @return List of FileRecord
     */
    public List<FileRecord> listOverdueFileRecord(final int lifeMinute);
}
