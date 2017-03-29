/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import robertli.zero.entity.FileRecord;

/**
 *
 * @version 2017-03-20 1.0.1
 * @author Robert Li
 */
public interface FileRecordDao extends GenericDao<FileRecord, String> {

    public FileRecord saveFileRecord(String url);

    /**
     * This function is used to update the tables which has FileRecord. If the
     * newUrl is equals to the currentRecord, it return currentRecord, else it
     * delete currentRecord and return a new record.
     *
     * @param currentRecord
     * @param newUrl
     * @return
     */
    public FileRecord replaceFileRecord(FileRecord currentRecord, String newUrl);
}
