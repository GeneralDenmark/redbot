import json
import pathlib

from discord import TextChannel, Embed

from ProgrammedFeatures.ProgramFeatureClass import Feature


class SendEmbed(Feature):
    """
    Send a Discord Embed. Requires that the message is a path to a json file,
    that contains the relevant information to generate a discord Embed.
    """

    def __init__(self, bot, config):
        super().__init__(bot, config)

    async def execute(self, channel):
        json_file = pathlib.Path(self.config['message'])
        if not json_file.exists():
            raise Exception(f'No file exists with the name {json_file.absolute().__str__()}')
        with open(json_file, 'r') as file:
            embed_json: dict = json.load(file)

            self.embed = Embed(**embed_json)
            if 'image' in embed_json.keys():
                self.embed.set_image(url=embed_json.get('image'))
            if 'footer' in embed_json.keys():
                self.embed.set_footer(text=embed_json.get('footer'))
            if 'thumbnail' in embed_json.keys():
                self.embed.set_thumbnail(url=embed_json.get('thumbnail'))
        await super().execute(channel)

    async def _exec(self, channel: TextChannel, config=None):
        await channel.send(embed=self.embed)

    def _dummy(self, channel, config=None):
        print(f"Sending: {self.embed.__str__()} to channel {channel.name}")

    @staticmethod
    def _get_name():
        return 'SendEmbed'

    @staticmethod
    def _get_description():
        return """
Send a Discord Embed. Requires that the message is a path to a json file, 
that contains the relevant information to generate a discord Embed.
        """


class SendPlainMessage(Feature):
    """Just send what ever is in the message option as a message to the discord user."""

    def __init__(self, bot, config):
        super().__init__(bot, config)

    async def _exec(self, channel: TextChannel):
        await channel.send(self.config['message'])

    def _dummy(self, channel):
        print(f"Sends: {self.config['message']} to {channel}")

    @staticmethod
    def _get_name():
        return 'SendPlainMessage'

    @staticmethod
    def _get_description():
        return """Just send what ever is in the message option as a message to the discord user."""
