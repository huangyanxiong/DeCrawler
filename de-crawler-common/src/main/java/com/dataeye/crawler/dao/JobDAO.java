package com.dataeye.crawler.dao;

import com.dataeye.crawler.thrift.Job;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by shelocks on 17/3/16.
 */
@RegisterMapper(JobMapper.class)
public interface JobDAO {

  @SqlQuery(" select * from job_info where id = :id ")
  Job findJobById(@Bind("id") int id);

  @SqlQuery(" select * from job_info where is_load = 0 and is_enable = 1")
  List<Job> listJobInfoByLoadType();

  @SqlQuery(" select * from job_info where is_enable = 1")
  List<Job> listJobInfo();

  @SqlQuery(" select * from job_info where job_name = :jobName ")
  List<Job> listJobByName(@Bind("jobName") String jobName);

  @SqlQuery(" select * from job_info where job_id = :jobId and job_name = :jobName ")
  Job getJobByIdAndName(@Bind("jobId") String jobId, @Bind("jobName") String jobName);

  @SqlBatch(" update job_info set is_load = 1 where id in ( :id ) ")
  void updateJob2Load(@Bind("id") List<Integer> ids);

  @SqlBatch(" update job_info set is_load = 1 where id = :id ")
  @BatchChunkSize(100)
  void updateJob2LoadBean(@BindBean List<Job> jobList);

  @SqlUpdate(" insert into job_info (job_id,job_name,level,cron,url,is_load,platform,account) values (:jobId,:jobName,:level,:cron,:url,0,:platform,:account) ")
  Integer addJobInfo(@BindBean Job job);

  @SqlUpdate(" update job_info set level=:level,cron=:cron,url=:url,is_load=0,platform=:platform,account=:platform where job_id=:jobId and job_name=:jobName ")
  Integer updateJobInfo(@BindBean Job job);

  @SqlUpdate(" delete from job_info where job_id = :jobId and job_name = :jobName ")
  Integer delJobInfo(@Bind("jobId") String jobId, @Bind("jobName") String jobName);

}
