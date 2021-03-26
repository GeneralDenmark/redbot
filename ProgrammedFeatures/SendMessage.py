import json
import pathlib

from discord import TextChannel, Embed

from ProgrammedFeatures.ProgramFeatureClass import Feature


class SendEmbed(Feature):
    def __init__(self, bot, config):
        super().__init__(bot, config)

    def execute(self, channel):
        json_file = pathlib.Path(self.config['message'])
        if not json_file.exists() and self.config['verbose']:
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

    async def _exec(self, channel: TextChannel, config=None):
        await channel.send(embed=self.embed)

    def _dummy(self, channel, config=None):
        print(f"Sending: {self.embed.__str__()} to channel {channel.name}")

    @staticmethod
    def _get_name():
        return 'SendEmbed'


class SendPlainMessage(Feature):
    def __init__(self, bot, config):
        super().__init__(bot, config)

    async def _exec(self, channel: TextChannel):
        await channel.send(self.config['message'])

    def _dummy(self, channel):
        print(f"Sends: {self.config['message']} to {channel}")

    @staticmethod
    def _get_name():
        return 'SendPlainMessage'
