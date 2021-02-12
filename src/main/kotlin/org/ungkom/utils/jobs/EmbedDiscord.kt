package org.ungkom.utils.jobs

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.ungkom.utils.Bot
import java.awt.Color

class EmbedDiscord : Job {
    override fun execute(context: JobExecutionContext?) {
        val bot: JDA = Bot.bot
        val channel = bot.getTextChannelById(717064505762381876)
        if (channel != null) {
            if (channel.canTalk()) {
                val pingRole = bot.getRoleById(715197071950348288)
                channel.sendMessage("${pingRole?.asMention}").queue()
                val embed: EmbedBuilder = EmbedBuilder()
                embed.setTitle("Det er tid til at betale kontigent!", "https://mobilepay.dk/box?CWdpCOACfkVtEqLiFALfQxRhA")
                embed.setColor(Color.RED)
                embed.setDescription("Kære kammerater!\n" +
                        "\n" +
                        "Vi har brug for din støtte! \n" +
                        "Hver måned er jeg blevet bedt om at sende denne besked ud til jer alle sammen, og pænt minde jer om at betale kontigent.\n" +
                        "\n" +
                        "Hvad i betaler, er op til jer, hvad end i kan udvære.\n" +
                        "\n" +
                        "Overfør pengende til Mobile pay boksen:\n" +
                        "https://mobilepay.dk/box?CWdpCOACfkVtEqLiFALfQxRhA\n" +
                        "\n" +
                        "og fortsæt den gode kamp!\n" +
                        "\n" +
                        "Kærligst LAG-gruppen")
                embed.setFooter("Denne besked er produceret af en automatisk Rød klient")
                embed.setImage("https://www.weircon.dk/qr_code.png")
                embed.setThumbnail("https://www.weircon.dk/hammerogsegl_dkp.png")
                channel.sendMessage(embed.build()).queue()
            }
        }
    }

}