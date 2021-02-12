package org.ungkom.features.commands

import com.jagrosh.jdautilities.command.Command.Category;
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.User
import java.awt.Color
import java.util.*
import kotlin.collections.ArrayList

fun HelpMessage(event: CommandEvent, prefix: String, ownerId: String, commands: ArrayList<Command>) {
    val embed: EmbedBuilder = EmbedBuilder()
    var category: Category? = null
    embed.setTitle("Hjælpe besked!")
    embed.setColor(Color.RED)
    embed.descriptionBuilder
        .append("Jeg er en automatisk Rød klient.")
        .append("\n")
        .append("Dette kan du bruge mig til:")

    commands.forEach {
        if (!it.isHidden && (!it.isOwnerCommand || event.isOwner)) {
            if (!Objects.equals(category, it.category)) {
                category = it.category
                embed.descriptionBuilder
                    .append("\n\n   __${if (category == null) "Ingen kategori" else category!!.name}__:\n")
            }
            embed.descriptionBuilder
                .append("\n`$prefix${it.name}${if(it.arguments == null) "`" else " ${it.arguments}`" }")
                .append(" - ${it.help}")
        }
    }
    val owner: User? = event.jda.getUserById(ownerId)
    if (owner != null) embed.setFooter("For ekstra hjælp kontakt **${owner.name}**#${owner.discriminator}")
    embed.setThumbnail("https://www.weircon.dk/hammerogsegl_dkp.png")

    event.channel.sendMessage(embed.build()).queue()
}