This game is still in development. Some core features are still missing, but it's getting closer!

Note: If features aren't working as described, check the commit messages. I've started keeping commit
messages in the readme, but some earlier changes were only tracked in the commits.

This project aims to both demonstrate and expand my programming ability. I initially began development 
in conjunction with a tutorial (found at https://www.youtube.com/channel/UC0MHs87ehhLOtPQf_bAWDEA), but
I have since diverged, including the following.

-Loading maps from a file on beginning of a new game.
-Loading and saving maps done with JFileChooser. No longer need to interact with console.
-Area of Effect (AOE) actions that affect enemies/towers within a certain radius.
-Toggleable menus for in game and editor.
-Pathfinder class to handle enemy navigation. This will eventually allow for enemies to navigate around
 player placed obstacles.
-Start and end tiles determined on loading map, no longer have to be hardcoded.
-Navigation done with Dijkstra's algorithm. Allows for reliable navigation of maps with 
 tracks wider than 1 tile.
-Reworked the game map to be static.
-Enums for easier selection of AOEs, enemies and projectiles.


Instructions:

PLAY: Begins a new game. Player is prompted to choose a map. If you don't have any, one can easily and quickly
be made in the editor. Alternatively, you can comment/uncomment lines 64 - 71 in the StateManager class to load
the default map by canceling the file selection. Note the map must have a path from start to end, otherwise
the game will crash. Enemy type cannot currently be changed. Select a tower type with either the tower menu 
(T) or with the hotkeys (1, 2 or 3). Once a type is selected, place towers by left-clicking. Cancel a placement
by right-clicking Towers can be deleted with 80% refund by hovering over a tower and pressing 'D'. Time can be 
accelerated/decelerated with the right/left arrow key respectively (Note: fast forwarding may cause enemies to
fly off the course. This will be addressed eventually).	Access AOE Fire Strike by pressing 'Q' and clicking on
the map. Fire Strike has a 4 second duration and has a 10 second cooldown. Get AOE Tower Buff by pressing 'W'.
This will increase the rate of fire for all towers in its range. Get Slow AOE by pressing 'E'. This will slow
down in-range enemies.

EDITOR: Loads a map with all grass tiles. Tile type can be modified by clicking on a tile. Switch the type
that clicking changes to by pressing the numbers 1-5 (1: Grass, 2: Dirt, 3: Water, 4: Start, 5: Goal). Press 
'M' to access the editor menu, which allows for saving and loading maps. Note: If you're saving from the editor,
make sure to include ".txt" file extension. It is currently not checked nor automatically added.

QUIT: Quits.


Most Recent Changes:
-Added TowerBuff AOE.
-Added Slow AOE.
-Updated main menu.
-Fixed border on reset button.
-Fixed pause glitch. 

To Do:

-Clean up isInRange methods.

-(DONE) Use helper class Clock to handle pausing more elegantly.

-Player levels
--Exp bar to show progress to next level
--Unlock different towers, projectiles and AOE
--Unlock different tower, projectile and AOE upgrades
--Unlock player buffs (e.g. increase starting cash)
--Different maps have different difficulty (harder maps require higher player level)
--Player profiles can be saved and loaded

-Lives, cash and wave have change animation (probably flashing).

-Floating notification on lives/cash change (e.g. place a tower that costs 40, and a little "-$40" floats up
 from where you placed the tower).

-Difficulty selection.

-Multiple map campaigns.

-Player placeable obstacles that cost money.

-Enemy pathfinding updated to account for path alterations.

-Pathfinder class doesn't crash game if a path doesn't exist.

-Allow enemies to move in any direction.

-(DONE) Fix border on Game Over reset button.

-(DONE) Change font of "Tower Defense" on main menu background.

-Saving/loading games.

-Wave information saved in map files.

-New Game option in game menu.

-(DONE) All file IO done through in game menus (no need for console interaction).

-Maps automatically saved with correct extension. 

-Change hardcoded constants to final variables.

-(DONE) Add pause function.

-(PARTIAL) Improved saving and loading. Play button loads a map from a file, and new maps can be loaded after 
 beginning a game. 

-(DONE) Tower selection menu and tower placement with mouse. (T button shows menu to select towertype from?).

-(DONE) Tower about to be placed shown at mouse cursor.

-More tower and projectile types, with unlock progression.
--Rocket tower
--Multi-Target tower
--Railgun/Laser tower

-Projectiles originate from the end of the gun barrel (currently spawn at center of tower).

-(DONE) Player controlled AOE actions (eg set all enemies on fire within radius x of mouse click)
--(DONE) Fire
--(DONE) Slow
--(DONE) Tower buffs

-Level up of towers.

-One tower can shoot multiple types of projectiles. Perhaps "special shots" will be unlocked at a 
 certain level.
 
-(DONE) Manage deletion of "dead" projectiles (Use CopyOnWriteArrayList like with enemies)

-(DONE) Towers can be removed by player, probably with partial refund.

-(PARTIAL) More enemy types with level progression for the same enemy type.

-(DONE) Slowing of enemies is temporary.

-(PARTIAL) Lives and money displayed on-screen (Works, but I might try and make it look better).

-(DONE) Losing all lives causes a game over screen with an option to reset.

-Checks made to see if enemy is on a certain square have tolerances that scale with time mult (currently static).

-Sound

-Balancing

-Other


Old Changes:
-Implemented AOE player action.
--Dynamic cooldown and placement drawing.
-Pausing now done through helpers.Clock (set Delta to 0 for everything).
-Fixed bug when trying to place a tower on an occupied tile (would charge money but not place tower).
-Changed Artist.DrawQuadTex* methods to explicitly draw on a white tile.
-Player info (lives, cash, wave#) now in color!

-Maps now stored in maps folder

-Cash and lives now displayed on-screen.
-Saved and loaded map names displayed in editor.
-Ice tower slowing reworked. Now temporary.
-Increased starting cash.
-Updated JAR.
-Included 3 more example maps.
-Updated readme.

-Use JFileChooser for all file IO
-Added executable jar with some example maps.

-Pausing reworked so that enemies, towers and projectiles are still drawn while game is paused.
-First version of tower menu added (Press 'T' and click on the base of the tower you want to place)
-Towers only placed when selected from tower menu or by hotkey
-Tower to be placed is drawn at mouse location until placed (left-click) or canceled (right click).
-Some code cleanup.

-Dead projectiles now deleted from tower's projectile list.
-Pressing 'M' while in editor shows editor menu. Allows for saving, loading and returning to the main menu.
-TileType selection while in editor handled with number 1-5
-User prompted for map name to load upon new game.
-Leveler.GetMapArray returns an int[][].
-Pressing 'P' in game pauses and brings up the pause menu.
-Added pauseDraw methods to Wave and WaveManager classes so enemies don't disappear while game is paused.
-Removed redundant readme.