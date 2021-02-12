package org.ungkom.utils

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.examples.command.AboutCommand
import com.jagrosh.jdautilities.examples.command.PingCommand
import com.jagrosh.jdautilities.examples.command.ShutdownCommand
import io.ebean.DB
import io.ebean.Database
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import org.ungkom.features.commands.HelpMessage
import org.ungkom.features.commands.MobilePay
import org.ungkom.features.commands.Sherlock
import org.ungkom.features.listeners.Listeners
import java.awt.Color
import java.util.EnumSet




class Bot(
        val token: String,
        val owner: String,
        val prefix: String,
        vararg val values: String,
        val database: Database = DB.getDefault(),
) {
    companion object {
        lateinit var bot: JDA
    }

    fun buildBot() {
        // the second is the bot's owner's id

        val ownerId = owner
        // define an eventwaiter, dont forget to add this to the JDABuilder!
        val waiter = EventWaiter()

        // define a command client
        val client = CommandClientBuilder()

        // The default is "Type !!help" (or whatver prefix you set)
        client.useDefaultGame()

        // sets the owner of the bot
        client.setOwnerId(ownerId)
        if (values.isNotEmpty()) {
            client.setCoOwnerIds(*values)
        }

        // sets emojis used throughout the bot on successes, warnings, and failures
        client.setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26")

        // sets the bot prefix
        client.setPrefix(prefix)

        val commands: ArrayList<Command> = arrayListOf(
            MobilePay(),
            Sherlock(),
        )
        // adds commands
        commands.forEach{
            client.addCommand(it)
        }
        client.setHelpConsumer {HelpMessage(it, prefix, owner, commands)}
        // start getting a bot account set up
        val builder = JDABuilder.createDefault(token) // set the game for when the bot is loading
            .setStatus(OnlineStatus.DO_NOT_DISTURB)
            .setActivity(Activity.playing("indlæser...")) // add the listeners
            .setEnabledIntents(EnumSet.allOf(GatewayIntent::class.java))
            .addEventListeners(waiter, client.build()) // start it up!
        val jda = builder.build()
        jda.awaitReady()
        jda.addEventListener(Listeners())
        bot = jda
        println("Bot er bygget færdig")
    }

}
