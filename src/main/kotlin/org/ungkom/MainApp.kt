package org.ungkom

import com.google.gson.Gson
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.ungkom.domain.Guild
import org.ungkom.domain.Member
import org.ungkom.domain.RelGuildMember
import org.ungkom.domain.query.QGuild
import org.ungkom.domain.query.QMember
import org.ungkom.domain.query.QRelGuildMember
import org.ungkom.utils.Bot
import org.ungkom.utils.Config
import org.ungkom.utils.Scheduler
import org.ungkom.utils.getResourcesFile
import org.ungkom.utils.jobs.EmbedDiscord
import java.io.BufferedReader
import java.io.File
import java.security.InvalidParameterException

fun main() {
    val gson = Gson()
    val bufferReader: BufferedReader = File(getResourcesFile("config.json")).bufferedReader()
    val inputString = bufferReader.use {it.readText()}

    val config = gson.fromJson(inputString, Config::class.java)

    if (config.token == null) throw InvalidParameterException("Require token to run bot")
    if (config.owner == null) throw InvalidParameterException("Require owner to run bot")


    val bot = Bot(
        config.token!!,
        config.owner!!,
        "!",
        )
    bot.buildBot()

    Bot.bot.guilds.forEach{ it ->
        val guild: Guild = QGuild().discord_id.equalTo(it.id).findOne() ?: Guild(it.id)
        guild.save()
        val members: MutableList<Member> = mutableListOf()

        println(it.defaultChannel?.members)
        it.members.toList().filter { th -> !th.user.isBot }.forEach{ th ->
            val m = QMember().discord_id.equalTo(th.id).findOne() ?: Member(th.id)
            m.save()
            members.add(m)
        }
        members.toList().forEach{ tt ->
            val rel = QRelGuildMember().guild.equalTo(guild).and().member.equalTo(tt).findOne() ?: RelGuildMember(guild, tt)
            rel.save()
        }

    }

    val sc = Scheduler()
    val realTime = "0 0 10 1 * ?"
    // val testTime = "*/5 * * * * ?"
    sc.createNewJob(EmbedDiscord().javaClass, "Test", realTime)
}