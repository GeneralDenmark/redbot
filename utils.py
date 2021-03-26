from argparse import Action

from ProgrammedFeatures.ProgramFeatureClass import Feature


class SelectProgrammableAction(Action):
    """
    Argparse action for handeling a list of classes with the type Feature.
    """

    def __init__(self, **kwargs):
        # Pop off the type value
        choices: [Feature] = kwargs.pop("choices", None)
        choices: list
        # Ensure an Enum subclass is provided
        if choices is None:
            raise ValueError("choices must have something in it")
        if len([x for x in choices if not issubclass(x, Feature)]) > 0:
            raise ValueError("Not all choices are of type Feature")

        kwargs.setdefault("choices", tuple(e._get_name() for e in choices))

        super(SelectProgrammableAction, self).__init__(**kwargs)

        self._choices = choices

    def __call__(self, parser, namespace, values, option_string=None):
        # Convert value back into an Enum
        chosen = [x for x in self._choices if x._get_name() in values]
        setattr(namespace, self.dest, chosen)


class DescribeProgrammableAction(Action):
    """
    Argparse action for handeling a list of classes with the type Feature.
    """

    def __init__(self, **kwargs):
        # Pop off the type value
        choices: [Feature] = kwargs.pop("choices", None)
        choices: list
        # Ensure an Enum subclass is provided
        if choices is None:
            raise ValueError("choices must have something in it")
        if len([x for x in choices if not issubclass(x, Feature)]) > 0:
            raise ValueError("Not all choices are of type Feature")

        kwargs.setdefault("choices", tuple(e._get_name() for e in choices))

        super(DescribeProgrammableAction, self).__init__(**kwargs)

        self._choices = choices

    def __call__(self, parser, namespace, values, option_string=None):
        # Convert value back into an Enum
        chosen = [x for x in self._choices if x._get_name() in values]
        print('The following is a description of what is queried: ')
        for chose in chosen:
            print(f"{chose._get_name()}: {chose._get_description()}")
        exit(0)
