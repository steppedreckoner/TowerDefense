NOT A FINISHED PRODUCT - STILL IN ACTIVE DEVELOPMENT

Note: If features aren't working as described, check the commit messages. I've started keeping commit
messages in the readme, but some earlier changes weren't tracked.

This project aims to both demonstrate and expand my programming ability. I initially began development 
in conjunction with a tutorial (found at https://www.youtube.com/channel/UC0MHs87ehhLOtPQf_bAWDEA), but
I have since diverged, including the following.

-Loading maps from a file on beginning of a new game.
-Toggleable menus for in game and editor.
-Pathfinder class to handle enemy navigation. This will eventually allow for enemies to navigate around
 player placed obstacles.
-Start and end tiles determined on loading map, no longer have to be hardcoded.
-Navigation done with Dijkstra's algorithm. Allows for reliable navigation of maps with 
 tracks wider than 1 tile.
-Reworked the game map to be static.
-Enums for easier selection of enemies and projectiles.


Instructions:

PLAY: Begins a new game. Enemy type cannot currently be changed. Select a tower type with either the tower
menu ('T') or with the hotkeys (1, 2 or 3). Once a type is selected, place towers by left-clicking. Cancel
a placement by right-clicking Towers can be deleted with 80% refund by hovering over a tower and pressing
'D'. Time can be accelerated/decelerated with the right/left arrow key respectively (Note: fast forwarding 
may cause enemies to fly off the course. This will be addressed eventually).

EDITOR: Loads a map with all grass tiles. Tile type can be modified by clicking on a tile. Switch the type
that clicking changes to by pressing the numbers 1-5 (1: Grass, 2: Dirt, 3: Water, 4: Start, 5: Goal). Press 
'M' to access the editor menu. Both the save and load buttons require interaction with the console. 

QUIT: Quits.


Most Recent Changes:
-Pausing reworked so that enemies, towers and projectiles are still drawn while game is paused.
-First version of tower menu added (Press 'T' and click on the base of the tower you want to place)
-Towers only placed when selected from tower menu or by hotkey
-Tower to be placed is drawn at mouse location until placed (left-click) or canceled (right click).
-Some code cleanup.


To Do:

-All file IO done through in game menus (no need for console interaction).

-(DONE) Add pause function.

-(PARTIAL) Improved saving and loading. Play button loads a map from a file, and new maps can be loaded after 
 beginning a game. 

-(DONE) Tower selection menu and tower placement with mouse. (T button shows menu to select towertype from?).

-(DONE) Tower about to be placed shown at mouse cursor.

-More tower and projectile types, with unlock progression.
--Rocket tower
--Multi-Target tower
--Railgun/Laser tower

-Player controlled AOE actions (eg set all enemies on fire within radius x of mouse click)

-Level up of towers.

-One tower can shoot multiple types of projectiles. Perhaps "special shots" will be unlocked at a 
 certain level.
 
-(DONE) Manage deletion of "dead" projectiles (Use CopyOnWriteArrayList like with enemies)

-(DONE) Towers can be removed by player, probably with partial refund.

-(PARTIAL) More enemy types with level progression for the same enemy type.

-Slowing of enemies is temporary.

-Lives and money displayed on-screen.

-(DONE) Losing all lives causes a game over screen with an option to reset.

-Checks made to see if enemy is on a certain square have tolerances that scale with time mult (currently static).

-Balancing

-Other


Old Changes:
-Dead projectiles now deleted from tower's projectile list.
-Pressing 'M' while in editor shows editor menu. Allows for saving, loading and returning to the main menu.
-TileType selection while in editor handled with number 1-5
-User prompted for map name to load upon new game.
-Leveler.GetMapArray returns an int[][].
-Pressing 'P' in game pauses and brings up the pause menu.
-Added pauseDraw methods to Wave and WaveManager classes so enemies don't disappear while game is paused.
-Removed redundant readme.