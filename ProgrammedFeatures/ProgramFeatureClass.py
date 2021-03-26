from abc import ABC

from discord import TextChannel
from discord.ext.commands import Bot


class Feature(ABC):
    """
    Feature is an abstract class that takes.
    It defines some abstract functions that is needed to execute the children of this class.

    Also making sure that _get_name has been overwritten
    so that we can provide the feature to the user via console input.
    """

    def __init__(self, discord_bot: Bot, config):
        self.discord_bot = discord_bot
        self.config = config

    async def execute(self, channel):
        """The execute function decides to run either the dummy or the exec method,
         depending on if the option dummy is set"""
        if self.config['dummy']:
            self._dummy(channel)
        else:
            await self._exec(channel)

    async def _exec(self, channel: TextChannel):
        """Should be the method that changes stuff."""
        raise NotImplementedError("Must override method")

    def _dummy(self, channel):
        """Should be the method that emulates changing stuff"""
        raise NotImplementedError("Must override method")

    def __str__(self):
        return self._get_name()

    @staticmethod
    def _get_description():
        """Should be filled in to allow for more in depth explanation to the user from the terminal"""
        raise NotImplementedError("Must override method")

    @staticmethod
    def _get_name():
        """Hardcode the name of the method here. This is used to provide the feature as a choice via the terminal"""
        raise NotImplementedError("Must override method")
