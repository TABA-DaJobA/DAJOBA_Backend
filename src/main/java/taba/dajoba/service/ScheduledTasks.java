package taba.dajoba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private SelfIntroService selfIntroService;

    @Scheduled (fixedRate = 5000)
    //@Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void dailyUpdateTask() {
        selfIntroService.dailyUpdate();
    }
}
