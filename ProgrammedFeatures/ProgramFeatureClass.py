from abc import ABC

from discord import TextChannel
from discord.ext.commands import Bot


class Feature(ABC):
    def __init__(self, discord_bot: Bot, config):
        self.discord_bot = discord_bot
        self.config = config

    async def execute(self, channel):
        if self.config['dummy']:
            self._dummy(channel)
        else:
            await self._exec(channel)

    async def _exec(self, channel: TextChannel):
        raise NotImplementedError("Must override method")

    def _dummy(self, channel):
        raise NotImplementedError("Must override method")

    def __str__(self):
        return self._get_name()

    @staticmethod
    def _get_name():
        raise NotImplementedError("Must override method")
