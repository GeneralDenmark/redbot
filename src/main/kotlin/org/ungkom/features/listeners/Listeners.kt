package org.ungkom.features.listeners

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.Member as dMember
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.MessageUpdateEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.ungkom.domain.Guild
import org.ungkom.domain.Member
import org.ungkom.domain.Messages
import org.ungkom.domain.RelGuildMember
import org.ungkom.domain.query.QGuild
import org.ungkom.domain.query.QMember
import org.ungkom.domain.query.QMessages
import org.ungkom.domain.query.QRelGuildMember


class Listeners : ListenerAdapter() {
    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        val jda: JDA = event.jda
        val author: User = event.author
        //Ignore the message if it was sent by a bot.
        if (author.isBot)
            return


        val message: Message = event.message

        val guild: Guild = QGuild().discord_id.equalTo(event.guild.id).findOne() ?: Guild(event.guild.id)
        guild.save()

        val aut: Member = QMember().discord_id.equalTo(message.author.id).findOne() ?: Member(message.author.id)
        aut.save()

        val relGuildMember: RelGuildMember = QRelGuildMember().guild.equalTo(guild)
            .and().member.equalTo(aut).findOne() ?: RelGuildMember(guild, aut)
        relGuildMember.save()

        val ma: Messages = Messages(
            author = aut,
            discord_id= message.id,
            body = message.contentRaw,
            guild = guild
        )
        ma.save()
    }

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        val jda: JDA = event.jda
        val mem: dMember = event.member
        if (mem.user.isBot)
            return
        val guild: Guild = QGuild().discord_id.equalTo(event.guild.id).findOne() ?: Guild(event.guild.id)
        guild.save()
        val member: Member = QMember().discord_id.equalTo(mem.id).findOne() ?: Member(mem.id)
        member.save()

        val rgm = QRelGuildMember().guild.equalTo(guild).and().member.equalTo(member).findOne() ?:
        RelGuildMember(guild, member)
        rgm.save()
    }

    override fun onGuildMemberRemove(event: GuildMemberRemoveEvent) {
        val jda: JDA = event.jda
        val user: User = event.user
        if (user.isBot)
            return
        val mem = QMember().discord_id.equalTo(user.id).findOne() ?: Member(user.id)
        mem.save()
        QRelGuildMember().guild.discord_id.equalTo(event.guild.id).and().member.equalTo(mem).delete()
    }

    override fun onGuildMessageUpdate(event: GuildMessageUpdateEvent) {
        val jda: JDA = event.jda
        val author: User = event.author
        //Ignore the message if it was sent by a bot.
        if (author.isBot)
            return
        val message: Message = event.message

        val guild: Guild = QGuild().discord_id.equalTo(event.guild.id).findOne() ?: Guild(event.guild.id)
        guild.save()

        val aut: Member = QMember().discord_id.equalTo(message.author.id).findOne() ?: Member(message.author.id)
        aut.save()

        val relGuildMember: RelGuildMember = QRelGuildMember().guild.equalTo(guild)
            .and().member.equalTo(aut).findOne() ?: RelGuildMember(guild, aut)
        relGuildMember.save()
        val oldMsg = QMessages().discord_id.equalTo(message.id)

        val ma = Messages(
            author = aut,
            body = message.contentRaw,
            guild = guild,
            discord_id = message.id,
            parentMessage = if (oldMsg.exists()) oldMsg.findOne() else null
        )
        ma.save()
    }
}
