namespace java com.dataeye.crawler.thrift

struct Worker {
    //worker的标识
    1: required string id,
    //worker所属的业务
    2: required string biz
}

struct Task {
    //任务的标识
    1: required string id,
    //任务的优先级
    2: required i32 level,
    // 需要爬去的url
    3: required string url,
    //任务所属的worker
    4: required Worker work,
    //额外的参数
    5: optional map<string,string> extras
}

struct Job {
    1: required i32 id,
    2: required string cron,
    3: required string jobName,
    4: required string jobId,
    5: required string url,
    6: required i32 level,
    7: required string platform,
    8: required string account
}

service ScheduleService {
    /**
    *  获取调度的任务
    **/
    Task poll(1:required Worker worker),

    /**
    * 将任务推送给调度器
    **/
    bool push(1:required Task task),

    /**
     * 列出所有work
    **/
    map<string, set<Worker>> listWorks(),

    /**
     * 添加Job
    **/
    bool addJob(1:required Job job),

    /**
     * 更新Job
    **/
    bool updateJob(1:required Job job),

    /**
     * 删除Job
    **/
    bool delJob(1:required string jobId, 2:required string jobName),

    /**
     * 根据jobName获取所有job
    **/
    list<Job> listJobByName(1:required string jobName),

    /**
     * 根据jobName和jobId获取具体job
    **/
    Job getJobByIdAndName(1:required string jobId, 2:required string jobName),

    /**
    * 测试方法
    **/
    string hello()

}