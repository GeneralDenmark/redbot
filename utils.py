from argparse import Action

from ProgrammedFeatures.ProgramFeatureClass import Feature


class ProgrammedAction(Action):
    """
    Argparse action for handling Enums
    """
    def __init__(self, **kwargs):
        # Pop off the type value
        choices: [Feature] = kwargs.pop("choices", None)
        choices: list
        # Ensure an Enum subclass is provided
        if choices is None:
            raise ValueError("type must be assigned an Enum when using EnumAction")
        if len([x for x in choices if not issubclass(x, Feature)]) > 0:
            print(choices)
            raise ValueError("Not all choices are of type Feature")

        kwargs.setdefault("choices", tuple(e._get_name() for e in choices))

        super(ProgrammedAction, self).__init__(**kwargs)

        self._choices = choices

    def __call__(self, parser, namespace, values, option_string=None):
        # Convert value back into an Enum
        chosen = [x for x in self._choices if x._get_name() in values]
        setattr(namespace, self.dest, chosen)
