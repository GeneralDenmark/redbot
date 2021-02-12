package org.ungkom.utils
import org.quartz.*
import org.quartz.CronScheduleBuilder.cronSchedule
import org.quartz.JobBuilder.newJob
import org.quartz.Scheduler
import org.quartz.impl.StdSchedulerFactory
import java.util.*
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder.newTrigger


class Scheduler(
    private val factory: SchedulerFactory = StdSchedulerFactory()
) {
    fun createNewJob(jvaClass: Class<Job>, group: String, cronExpression: String, start: Boolean = true) {

        val scheduler: Scheduler = factory.scheduler
        if (start) {
            scheduler.start()
            val job = newJob(jvaClass)
                .withIdentity(jvaClass.name, group)
                .build()

            val trigger: CronTrigger = newTrigger()
                .withIdentity("ct_$group", group)
                .withSchedule(cronSchedule(cronExpression))
                .build()
            scheduler.scheduleJob(job, trigger)
        } else {
            scheduler.shutdown()
        }
    }
}
