package org.ungkom

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.ungkom.domain.Guild
import org.ungkom.domain.Member
import org.ungkom.domain.RelGuildMember
import org.ungkom.domain.query.QGuild
import org.ungkom.domain.query.QMember
import org.ungkom.domain.query.QRelGuildMember
import org.ungkom.utils.Bot
import org.ungkom.utils.Scheduler
import org.ungkom.utils.jobs.EmbedDiscord

fun main() {
    val bot = Bot(
        "xxx",
        "xxx",
            "xxx"
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
    val testTime = "*/5 * * * * ?"
    sc.createNewJob(EmbedDiscord().javaClass, "Test", realTime)
}









/*

    bot(token) {
        prefix {
            "!"
        }

        configure {
            allowMentionPrefix = true
            generateCommandDocs = false
            showStartupLog = true
            recommendCommands = false
            commandReaction = null
            theme = Color.RED
        }

        mentionEmbed {
            title = "RødFront bot"
            description = "Ping mig én gang til, _I dare you_\n\n Skriv `!hjælp` for at få mine kommandoer."
            color = it.discord.configuration.theme
            }
        permissions {
            true
        }
        presence {
            watching("Marxisme og Leninisme")
        }
        intents {
            +Intent.GuildMessages
            +Intent.DirectMessageTyping
            +Intent.DirectMessages
            +Intent.DirectMessagesReactions
            +Intent.GuildBans
            +Intent.GuildEmojis
            +Intent.GuildInvites
            +Intent.GuildMessageTyping
            +Intent.GuildMessageReactions
        }
        onStart {
            api.guilds.toList().forEach { it ->
                var members: MutableList<Member> = emptyList<Member>().toMutableList()
                var guild: Guild;
                if (!QGuild().discord_id.equalTo(it.id.value).exists()) {
                    guild = Guild(it.id.value)
                    guild.save()
                    it.members.toList().forEach { th ->
                        val m = QMember().discord_id.equalTo(th.id.value).findOne() ?: Member(th.id.value)
                        m.save()
                        members.add(m)
                    }

                } else {
                    guild = QGuild().discord_id.equalTo(it.id.value).findOne() ?: throw Exception()
                    it.members.toList().forEach{ th ->
                        val m = QMember().discord_id.equalTo(th.id.value).findOne() ?: Member(th.id.value)
                        m.save()
                        members.add(m)
                    }
                }
                members.forEach{ bob ->
                        try {
                            val t = RelGuildMember(guild, bob)
                            t.save()
                        } catch (ignored: Exception) { }
                }
            }
        }
    }
}*/
