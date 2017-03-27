package com.dataeye.crawler.dao;

import com.dataeye.crawler.thrift.Job;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yann on 2017/3/17.
 */
public class JobMapper implements ResultSetMapper<Job> {

    @Override
    public Job map(int index, ResultSet rs, StatementContext context) throws SQLException {
        return new Job(
            rs.getInt("id"),
            rs.getString("cron"),
            rs.getString("job_name"),
            rs.getString("job_id"),
            rs.getString("url"),
            rs.getInt("level"),
            rs.getString("platform"),
            rs.getString("account"));
    }
}
