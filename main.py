import argparse
import pathlib

import discord
from discord.ext import commands

from ProgrammedFeatures.Inspiro import Inspiro
from ProgrammedFeatures.ProgramFeatureClass import Feature
from ProgrammedFeatures.SendMessage import SendEmbed, SendPlainMessage
from utils import SelectProgrammableAction, DescribeProgrammableAction

bot = commands.Bot(command_prefix='!', description="What")

programmable_features: [Feature] = [
    Inspiro
]

args = argparse.ArgumentParser()
required_args = args.add_argument_group(title='required arguments')
required_args.add_argument('-m', '--message', required=True, help="The message to produce.")
required_args.add_argument('-c', '--channel', nargs="+", required=True,
                           help="The id for the channel to exec this in")

args.add_argument('-v', '--verbose', required=False, action='store_true',
                  help="If the program is verbose or nutt")

args.add_argument('-d', '--dummy', required=False, action='store_true',
                  help="Executes everything but does not write in discord channel")
args.add_argument('-g', '--guilds', required=False, nargs="+",
                  help="What guilds to search for, can make the bot faster.")
args.add_argument('-b', '--bonus', nargs='*', required=False, choices=programmable_features,
                  action=SelectProgrammableAction, help="If the message should be supplied with preprogrammed features")
args.add_argument('--describe', required=False, action=DescribeProgrammableAction, choices=programmable_features,
                  help="Describes the bonus features, and exit")
config = args.parse_args()
config = config.__dict__
config['root'] = pathlib.Path(__file__).parent
config['tmp_folder'] = pathlib.Path(config['root']).joinpath('tmp_folder')


# Events
@bot.event
async def on_ready():
    if config['verbose']:
        print('Bot is ready!!! :)')
    guild: discord.Guild
    try:
        for guild in bot.guilds:
            if config['guilds'] is not None and str(guild.id) not in config['guilds']:
                if config['verbose']:
                    print(f"guild ({guild.name}) not in ({config['guilds']})")
                continue
            channel: discord.TextChannel
            if config['verbose']:
                print(f"In guild: {guild.name}")
            for channel in guild.channels:
                if type(channel) == discord.TextChannel and str(channel.id) in config['channel']:
                    if config['verbose']:
                        print(f"Found match :)")
                    if config['message'].endswith('.json'):
                        await SendEmbed(bot, config).execute(channel)
                    else:
                        await SendPlainMessage(bot, config).execute(channel)
                    if config['bonus'] is not None:
                        for bonus in config['bonus']:
                            await bonus(bot, config).execute(channel)

    finally:
        await bot.logout()


if __name__ == '__main__':

    with open(config['root'].joinpath('token.conf').absolute(), 'r') as f:
        if config['verbose']:
            print('starting bot!!!')
        bot.run(f.read())
