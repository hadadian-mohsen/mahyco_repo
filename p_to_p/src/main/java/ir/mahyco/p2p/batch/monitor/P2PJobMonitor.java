package ir.mahyco.p2p.batch.monitor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;

public class P2PJobMonitor {
    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    JobExecution jobExecution;

    public JobInstance getJobState(Long jobId)
    {
        return jobExplorer.getJobExecution(jobId).getJobInstance();
    }
}
