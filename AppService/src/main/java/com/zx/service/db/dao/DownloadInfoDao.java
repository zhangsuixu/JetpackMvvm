package com.zx.service.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zx.service.entity.po.DownloadInfoPO;

import java.util.List;

@Dao
public interface DownloadInfoDao {

    @Insert
    void insert(DownloadInfoPO student);

    @Delete
    void delete(DownloadInfoPO student);

    @Update
    void update(DownloadInfoPO student);

    @Query("SELECT * FROM download_info")
    List<DownloadInfoPO> queryDownloadInfoList();
}
