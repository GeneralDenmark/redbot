package org.ungkom.utils

import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Role

fun checkPermissions(event: CommandEvent?, requiredRole: String?, onlyGuild: Boolean): Int {
    if (requiredRole != null) {
        val author: Member = event!!.member
        val mustHaveRole: MutableList<Role> = event.guild!!.getRolesByName(requiredRole, true)
        if (author.roles.none{ it in mustHaveRole }) {
            event.channel.sendMessage("Du har ikke ${if (requiredRole.length > 1) "en af de" else "den" } rigtige ${if (requiredRole.length > 1) "roller" else "rolle"} $requiredRole til at kunne kalde denne kommando").queue()
            return -1
        }
    }

    if (onlyGuild) {
        if (event!!.guild == null) {
            event.channel.sendMessage("Denne kommando kræver at du bruger den i en server :frowning:").queue()
            return -1
        }
    }
    return 1
}