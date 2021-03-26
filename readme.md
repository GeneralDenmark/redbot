# Write a message bot
This bot does an action or writes a message when called.

### Help
```
usage: main.py [-h] -m MESSAGE -c CHANNEL [CHANNEL ...] [-v] [-d] [-g GUILDS [GUILDS ...]] [-b [{InspiroBot} ...]]

optional arguments:
  -h, --help            show this help message and exit
  -v, --verbose         If the program is verbose or nutt
  -d, --dummy           Executes everything but does not write in discord channel
  -g GUILDS [GUILDS ...], --guilds GUILDS [GUILDS ...]
                        What guilds to search for, can make the bot faster.
  -b [{InspiroBot} ...], --bonus [{InspiroBot} ...]
                        If the message should be supplied with preprogrammed features

required arguments:
  -m MESSAGE, --message MESSAGE
                        The message to produce.
  -c CHANNEL [CHANNEL ...], --channel CHANNEL [CHANNEL ...]
                        The id for the channel to exec this in

```



