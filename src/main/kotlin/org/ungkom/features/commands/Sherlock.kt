package org.ungkom.features.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.User
import org.ungkom.utils.checkPermissions
import org.ungkom.utils.getProjectAbsolutePath
import org.ungkom.utils.getProjectBeforeAbsolutePath
import org.ungkom.utils.getResourcePath
import java.io.File

class Sherlock : Command() {
    init {
        category = Category("ungkom værktøj")
        name = "sherlock"
        help = "Sender en fil genereret af sherlock"
        arguments = "brugernavn"

    }

    override fun execute(event: CommandEvent?) {
        if (event!!.member.user.isBot) return
        if(0 > checkPermissions(event, "bot-dev", true)) return
        println(event.args)
        if (event.args.isEmpty()) {
            event.channel.sendMessage("For at bruge denne kommando, skal du give et bruger navn eg. umbalumba").queue()
            return;
        }
        val items = event.args.split(" ")
        println(items)
        println(items.size)
        if (items.toList().size > 1)
            event.channel.sendMessage("For nu kan jeg kun tage et brugernavn ad gangen, jeg vælger derfor det første: \"${items[0]}\"").queue()

        val userName = items[0]

        val msg = event.channel?.sendMessage("Kører Sherlock på $userName, dette kan tage lidt tid. ")
        msg?.queue()
        val commandLine = "${getResourcePath().toString()}/sherlock/venv/bin/python ${getResourcePath().toString()}/sherlock/sherlock $userName --timeout 10 -o ${getResourcePath().toString()}/sherlockoutputs/output-$userName.txt"
        Runtime.getRuntime()
            .exec(commandLine)
            .waitFor()

        event.channel?.sendFile(File("${getResourcePath().toAbsolutePath()}/sherlockoutputs/output-$userName.txt"))?.queue()
    }
}