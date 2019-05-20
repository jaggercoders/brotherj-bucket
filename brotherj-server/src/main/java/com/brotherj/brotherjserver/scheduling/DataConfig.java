package com.brotherj.brotherjserver.scheduling;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description：
 *
 * @author wangjie
 */
@Component
public class DataConfig {


    private final JobScheduledAnnotationBeanPostProcessor processor;

    private Map<String, Object> dataMap = new ConcurrentHashMap<>();

    public static final String JOB1_CRON = "job1_cron";
    public static final String JOB1_ENABLE = "job1_enable";

    public static final String JOB2_CRON = "job2_cron";
    public static final String JOB2_ENABLE = "job2_enable";
    private boolean init = false;

    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);

    @Autowired
    public DataConfig(JobScheduledAnnotationBeanPostProcessor processor) {
        this.processor = processor;
    }

    private void init() {
        List<DataKeyValue> dataKeyValues = getDataKeyValues();
        for (DataKeyValue dataKeyValue : dataKeyValues) {
            dataMap.put(dataKeyValue.getConfigKey(), dataKeyValue.getConfigValue());
        }

        //每10秒检查一个次配置数据是否有变化
        executorService.scheduleAtFixedRate(new DataChecker(), 0, 10, TimeUnit.SECONDS);
    }

    private List<DataKeyValue> getDataKeyValues() {
        String sql = "select config_key configKey,config_value configValue from data_config";
        return null;
    }

    @Setter
    @Getter
    private static class DataKeyValue {
        private String configKey;
        private String configValue;
    }


    public String getString(String key) {
        if (!init) {
            this.init();
            init = true;
        }
        return (String) dataMap.get(key);
    }

    /**
     * 检查数据是否变化，如果变化了则重新注册调度任务
     */
    class DataChecker implements Runnable {
        @Override
        public void run() {
            List<DataKeyValue> dataKeyValues = getDataKeyValues();
            for (DataKeyValue config : dataKeyValues) {
                String oldConfigValue = (String) dataMap.get(config.getConfigKey());
                String newConfigValue = config.getConfigValue();
                if (!StringUtils.isEmpty(oldConfigValue) &&
                        !StringUtils.isEmpty(newConfigValue) &&
                        !newConfigValue.equals(oldConfigValue)) {
                    //更新map中的键值
                    dataMap.put(config.getConfigKey(), config.getConfigValue());

                    //重新注册调度任务，正在执行的任务线程会被中断
                    processor.reRegister();
                    break;
                }
            }
        }
    }
}
