package com.dataeye.crawler.service;

import com.dataeye.crawler.dao.JobDAO;
import com.dataeye.crawler.db.DaoManager;
import com.dataeye.crawler.thrift.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by yann on 2017/3/13.
 */
public class JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobService.class);

    private static volatile boolean initQuery = true;

    private static JobDAO jobDao = DaoManager.getInstance().getDao(JobDAO.class);

    public static List<Job> getJobList(){
        List<Job> jobList = null;
        if (initQuery){
            jobList = jobDao.listJobInfo();
            initQuery = false;
        } else {
            jobList = jobDao.listJobInfoByLoadType();
        }
        return jobList;
    }

    public static void updateJob2Load(List<Job> jobList){
        jobDao.updateJob2LoadBean(jobList);
    }

    public static boolean addJobInfo(Job job){
        return jobDao.addJobInfo(job) > 0;
    }

    public static boolean updateJobInfo(Job job){
        return jobDao.updateJobInfo(job) > 0;
    }

    public static boolean delJobInfo(String jobId, String jobName){
        return jobDao.delJobInfo(jobId, jobName) > 0;
    }

    public static List<Job> listJobByName(String jobName){
        return jobDao.listJobByName(jobName);
    }

    public static Job getJobByIdAndName(String jobId, String jobName){
        return jobDao.getJobByIdAndName(jobId, jobName);
    }
}
