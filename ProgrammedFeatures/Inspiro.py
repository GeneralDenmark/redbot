import subprocess

from discord import File

from ProgrammedFeatures.ProgramFeatureClass import Feature


class Inspiro(Feature):
    """
    Inspiro, downloads a randomly generated inspirational poster from https://inspirobot.me/
    and posts it.
    """

    def __init__(self, bot, config):
        super().__init__(bot, config)

    async def _exec(self, channel):
        subprocess.getoutput(
            'curl "https://inspirobot.me/api?generate=true" | xargs curl -o ' + self.config['tmp_folder'].joinpath(
                'tmp.jpg').__str__())
        vvv = File(self.config['tmp_folder'].joinpath('tmp.jpg').__str__())
        await channel.send(file=vvv)
        vvv.close()

    def _dummy(self, channel, config=None):
        print(f"Sends file inspiro file")

    @staticmethod
    def _get_description():
        return """
Inspiro, downloads a randomly generated inspirational poster from https://inspirobot.me/ and posts it.       
        """

    @staticmethod
    def _get_name():
        return 'Inspiro'
