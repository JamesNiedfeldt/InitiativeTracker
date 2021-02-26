# Initiative Tracker
---
This is a tool designed for DMs of Dungeons and Dragons to track combat encounters with greater ease. It can track turn order, modify player/enemy stats, and save/load encounters for further use among other features.

---
### Requirements
* Java 8 to run
* [Apache Ant](https://ant.apache.org/) to build

---
### Compiling and Running
When in the directory of `build.exe` you can run the following:
* `ant clean-build` - cleans and builds the program and produces an executable .jar file in `dist/jar`
* `ant run` - runs the .jar file found in `dist/jar`
* `ant` - cleans, builds, then runs program

---
### Using
##### Adding players and enemies
* Individual players/enemies can be added by pressing the 'Add Player...' button. 
    * This will bring up a dialog box prompting you to fill out its stats. No field may be blank, and each number field must contain a nonnegative number.
    * The 'Dex' field refers to the players' dexterity score (not multiplier) and is used for calculating turn order if there is a tie in initiative score.
* Enemies may be added in bulk by pressing the 'Add Monsters...' button. 
    * This will bring up a dialog box prompting you to fill out the stat blocks of the enemy. No field may be blank, and each number field must contain a nonnegative number.
    * The 'Dex' field refers to the players' dexterity score (not multiplier) and is used for calculating turn order if there is a tie in initiative score.
    * Multiple enemies of the same type will be created and added to the encounter, with their initiative scores and HP randomized based on the stats provided.
##### Editing players
* Pressing the 'Edit Player...' button will open a dialog box with the stats of the selected player. All stats present when adding a new player may be edited and the same rules apply.
##### Deleting players
* Pressing the 'Delete Player' button will open a dialog box asking you to confirm deletion of the selected player. This will completely remove them from the encounter.
* Pressing the 'Delete All' button will open a dialog box asking you to confirm deletion of every player in the encounter. All players will be completely removed from the encounter.
* Checking the 'Do not delete' checkbox on a selected player will prevent that player from being deleted from either deletion method.
##### Using the turn order list
* The turn order of the encounter is displayed on the left side of the window in descending order. Clicking on a player in the turn list will select that player, displaying their stats on the right side of the screen.
* Pressing the 'Sort' button will sort all of the players by their initiative scores in descending order. If there is a tie in initiative score, players' dexterity scores will be used instead.
* Pressing the 'Next Turn' button will advance the player list. The player at the top of the list is the active player.
##### Player stats
* When a player is selected, their stats will be displayed on the right side of the screen. This includes current and total HP, AC, and conditions.
* HP is displayed as a progress bar. When a player has temporary HP the progress bar will be blue, otherwise it will be green.
    * To modify a player's HP (taking damage or healing), enter the value in the text box directly below their HP bar, then press the corresponding button. HP will be automatically substracted, but never added, to a player's temporary HP.
    * Modifying a player's temporary HP must be done through the 'Edit Player' window.
* Conditions a player has are displayed in a list.
    * To add a condition, select it from the 'Add' dropdown menu. In it are preset conditions as well as a custom condition that opens a dialog box to enter a custom name. A player cannot have more than one condition with the same name.
    * To remove a condition, select it from the list and press the 'Remove' button.
##### Saving/loading encounters
* The 'Save...' and 'Load...' buttons at the top of the window let you save and load encounter states as .csv files. All stat and turn order information is saved, as well as what players have been marked as 'Do not delete'.