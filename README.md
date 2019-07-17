# Update 2019-07-17:
I have chosen to restart developing the devils game (some day). There are some features that will be much easier to integrate by starting from the beginning. The next version of the devil's game will hav a new github repository, so this will be marked as deprecated and may be deleted at some point. But until then this version will get fixes and updates for new Quests.

# The Devil's Game / Des Teufels Spiel
This android-application is for everybody who loves to party and play drinking games. This App is for everyone who got bored by existing games and apps, like I did, and who wants to have a game that can be played anywhere and anytime.

The main reason I published this game is because it is really fun to play, but not perfect and my time for improvements is limited. I decided to publish it so that everybody who has good ideas can look at the code and maybe make improvements or add new cool features and quests.

## Features
* language support - ATM all the text and quests are in German language, but I tried to support Multilanguage everywhere so that the interface and quests can be adapted quickly. It all lies on the translation, somebody will have to do that at some point (maybe I will do it myself, but somehow I don't find spare time atm).
* supporting different quests depending on friendship level, public or private place, different requirements, and more

## Starting the game
Before the game can start some players have to be created. For that a name and sex is required. After creation the players can be selected. At least two players have to be selected for a new game. The following Settings are used to determine which quests can be used in the game or should better left out. Depending on the settings more or less private or embarrassing quests will be activated for the game. After that the game can be started.

Every round is beginning with a player and a quest chosen carefully (although it seems randomly). Whatever the quest is, it has to be accomplished to gain points and get to a higher level. Most of the time the player will have to drink, or drink in a certain way, or a certain amount, or do something funny, gets dared ... (we all know the long list of possibilities of this games). At the beginning all quests wil be harmless, but with increasing (drinking) level more quests will be activated.

## Implementation
The Applikation is implemented with Android Studio (well, I started with the SDK for Eclipse before Andoid Studio was *the thing*) in classic Java (as far as I have learned). The Application starts with the MainActivity Class. From this class the SettingsActivity from that one the PlayActivity are created. The game-package with the GameDataManager is responsible for managing most of the gameplay. For different states of the Game the State-Pattern is used (see Interface GameState) so that the same buttons and classes can do different things depending on the current state. The game-logic mainly is implemented inside the data-package where all the data is available. DateStorage is implemented in the dba-package. Settings are stored via SharedPreferences, User-Settings are stored in a SQLiteDatabase. All the quests are stored in an xml-File. On start-up all the content of the file is made into Objects stored in the game-package. Both ways of importing xml-files (ContentHandler and XmlPullParser) are supported and implemented through the TwoWayHandler-Interface. For the xml-Import to work properly, the xml-file has to validate against the quest_schema.xsd file (ingame-validation is not implemented).

## TODO
On the TODO-List are features and improvements that may or may not be implemented in the far future.
* sliding/flipping instead of using buttons to navigate and doing stuff
* more up-to-date user design
* settings to deactivate certain categories or customise balancing
* load custom quests
* more quests

## History
Once upon a time, when I was young, we played the classic Polish Drinking Game that you can easily find on Google and that was almost impossible to win (I finished twice - and I am proud of it). But this game was very static and there were so much more fun things to do - so we adapted the game and made our own printable drinking game. We called it "Teufelsdrinkspiel" (meaning "Devils Drinking Game") and it was definitely more fun than the classic Polish Game. But it also was static and predictable, and the number of quests was limited and prewritten. So I began to create a game that can contain lots of quests, chooses player at random, and has a good balance of drinking and doing other things. This game now exits since 2014 and it is not perfect, but definitely playable and a lot of fun when your focus is drinking, but simply drinking is to boring.
