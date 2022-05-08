package cn.edu.jxnu.service.config;

import cn.edu.jxnu.service.quartz.DiaryScoreRefreshJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetailFactoryBean diaryScoreRefreshDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(DiaryScoreRefreshJob.class);
        factoryBean.setName("diaryScoreRefreshJob");
        factoryBean.setGroup("clouddrawingGroup");
        // 任务是否永久保存
        factoryBean.setDurability(true);
        // 任务是否可恢复
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail diaryScoreRefreshDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(diaryScoreRefreshDetail);
        factoryBean.setName("diaryScoreRefreshTrigger");
        factoryBean.setGroup("clouddrawingTriggerGroup");
        // 间隔时间
        factoryBean.setRepeatInterval(3000); // ms单位
        factoryBean.setJobDataMap(new JobDataMap());
        return factoryBean;
    }

}
