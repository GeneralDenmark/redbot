import discord
from discord.ext import commands
import pathlib

bot = commands.Bot(command_prefix='!', description="What")


def write_in_channel(str):
    return {
        "717064505762381876": {
            "description": """
Kære kammerater!

Vi har brug for din støtte! 
Hver måned er jeg blevet bedt om at sende denne besked ud til jer alle sammen, og pænt minde jer om at betale kontigent.

Hvad i betaler, er op til jer, hvad end i kan udvære.

Overfør pengende til Mobile pay boksen:
https://mobilepay.dk/box?CWdpCOACfkVtEqLiFALfQxRhA

Skriv jeres navn i kommentarfeltet,


og fortsæt den gode kamp!

Kærligst LAG-gruppen
            """,
            "footer": 'Denne besked er produceret af en automatisk Rød klient',
            "title": 'Det er tid til at betale kontigent!',
            'color': discord.Color.red(),
            'image': 'https://dkp.dk/wp-content/uploads/2017/09/logohammerogsegl.png',
            'url': 'https://mobilepay.dk/box?CWdpCOACfkVtEqLiFALfQxRhA'
        },
    }.get(str, None)


# Events
@bot.event
async def on_ready():
    guild: discord.Guild
    try:
        for guild in bot.guilds:
            channel: discord.TextChannel
            for channel in guild.channels:
                if type(channel) == discord.TextChannel:
                    msg = write_in_channel(str(channel.id))
                    if msg is not None:
                        if type(msg) == str:
                            await channel.send(msg)
                        else:
                            embed = discord.Embed(**msg)
                            if msg.get('image', None) is not None:
                                embed.set_image(url=msg.get('image'))
                            if msg.get('footer', None) is not None:
                                embed.set_footer(text=msg.get('footer'))
                            await channel.send(embed=embed)
    finally:
        await bot.logout()

with open(pathlib.Path(__file__).parent.joinpath('token.conf').absolute(), 'r') as f:
    bot.run(f.read())
