package org.ungkom.features.commands

import com.jagrosh.jdautilities.command.Command
import java.awt.Color

import net.dv8tion.jda.api.Permission;
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder


class MobilePay : Command() {
    init {
        name = "mobile_pay"
        help = "Viser mobile pay linket samt QR-koden"
        botPermissions = arrayOf(Permission.MESSAGE_EMBED_LINKS)
        guildOnly = false
    }
    override fun execute(event: CommandEvent) {
        val embed: EmbedBuilder = EmbedBuilder()
        embed.setTitle("Betaling af kontigent", "https://mobilepay.dk/box?CWdpCOACfkVtEqLiFALfQxRhA")
        embed.setColor(Color.RED)
        embed.setDescription("Du kan vælge at skrive nummeret\n**9657HM**\nI dit mobile pay \"betal til\" felt.\nEller du kan vælge at trykke på følgende link:\nhttps://mobilepay.dk/box?CWdpCOACfkVtEqLiFALfQxRhA\n\nELLER\ndu kan skanne QR koden med din mobil")
        embed.setFooter("Denne besked er produceret af en automatisk Rød klient")
        embed.setImage("https://www.weircon.dk/qr_code.png")
        embed.setThumbnail("https://www.weircon.dk/hammerogsegl_dkp.png")
        event.channel.sendMessage(embed.build()).queue()
    }


}