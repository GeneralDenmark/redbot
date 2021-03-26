from abc import abstractmethod, ABC

from discord import TextChannel
from discord.ext.commands import Bot


class Feature(ABC):
    def __init__(self, discord_bot: Bot):
        self.discord_bot = discord_bot

    async def execute(self, channel, config: dict):
        if config['dummy']:
            self._dummy(channel, config)
        else:
            await self._exec(channel, config)

    async def _exec(self, channel: TextChannel, config: dict):
        raise NotImplementedError("Must override method")

    def _dummy(self, channel, config: dict):
        raise NotImplementedError("Must override method")

    def __str__(self):
        return self._get_name()

    @staticmethod
    def _get_name():
        raise NotImplementedError("Must override method")
