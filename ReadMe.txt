TOWER DEFENSE


This game is still in development. Some core features are still missing, but it's getting closer!




To prospective employers,

This project aims to both demonstrate and expand my programming ability. I initially began development in
conjunction with a tutorial (found at https://www.youtube.com/channel/UC0MHs87ehhLOtPQf_bAWDEA), but I have
since diverged heavily. Just about every part of the game has been customized or modified in some way. 

I know that this ReadMe is not managed perfectly. While I am certainly striving make it useful, I really want 
to demonstrate that I take documentation seriously. 

I appreciate you taking the time to look at this!

Greg Cousins



A few of my own major implementations
-Loading maps from a file on beginning of a new game.
-Loading and saving maps done with JFileChooser. No longer need to interact with console.
-Player leveling and unlocks.
-Enemy leveling.
-Area of Effect (AOE) actions that affect enemies/towers within a certain radius.
-Toggleable menus for in game and editor.
-Pathfinder class to handle enemy navigation. This will eventually allow for enemies to navigate around
 player placed obstacles.
-Start and end tiles determined on loading map, no longer have to be hardcoded.
-Navigation done with Dijkstra's algorithm. Allows for reliable navigation of maps with 
 tracks wider than 1 tile.
-Static game map allows for easier navigation
-Enums for easier selection of AOEs, enemies and projectiles.
-Everything in the "Old Changes" section.


Note: If features aren't working as described, check the commit messages. I've started keeping commit
messages in the readme, but some earlier changes were only tracked in the commits.


Instructions:

PLAY: Begins a new game. Player is prompted to choose a map. If you don't have any, one can easily and quickly
be made in the editor. Alternatively, you can comment/uncomment lines 64 - 71 in the StateManager class to load
the default map by canceling the file selection. Note the map must have a path from start to end, otherwise
the game will crash. Enemy type cannot currently be changed. Select a tower type with either the tower menu 
(T) or with the hotkeys (1 - 4). Once a type is selected, place towers by left-clicking. Cancel a placement
by right-clicking Towers can be deleted with 80% refund by hovering over a tower and pressing 'D'. Time can be 
accelerated/decelerated with the right/left arrow key respectively (Note: fast forwarding may cause enemies to
fly off the course. This will be addressed eventually).	Reset the time multiplier with the up arrow. Access AOE
Fire Strike by pressing 'Q' and clicking on the map. Fire Strike has a 4 second duration and has a 10 second 
cooldown. Get AOE Tower Buff by pressing 'W'. This will increase the rate of fire for all towers in its range.
Get Slow AOE by pressing 'E'. This will slow in-range enemies. With the addition of player levels, certain
towers and AOEs are locked until you reach a high enough player level. So, if you can't place a tower/AOE, 
that's (hopefully) why!

EDITOR: Loads a map with all grass tiles. Tile type can be modified by clicking on a tile. Switch the type
that clicking changes to by pressing the numbers 1-5 (1: Grass, 2: Dirt, 3: Water, 4: Start, 5: Goal). Press 
'M' to access the editor menu, which allows for saving and loading maps. Note: If you're saving from the
editor, make sure to include ".txt" file extension. It is currently not checked nor automatically added.

QUIT: Quits.


Most Recent Changes:
-Tower can now be leveled up. Leveling up increases fire rate and damage.
--Leveling up costs money (will later require certain amount of tower exp, too)
-Rocket tower nuke special shot unlocked at tower level 5.




TowerDefense Tropes:
-Space
-Medieval/Castles
-Bloons
-Fantasy
-Minimalist
-Scary Monsters
-Military


Known Issues:
-Weird occasional bug where a rocket might get really close to, but not hit a target, and then get stuck.
-Deleting a tower deletes all active projectiles fired by that tower (might keep this).
-Slow AOE continues to affect enemies that have moved out of range.
-Loading invalid maps crashes the game.
-Map files won't save unless ".txt" is manually entered.
-(FIXED) Items are drawn over game pause menu.


To Do:

-(PARTIAL) Level up of towers.
--Costs "tower exp" 
--(DONE) Costs money.
--Clicking on/hovering over a tower displays range and other info.
--Exp bar for towers.

-Add impact effect for nukes

-Make enemy list static

-Fix rocket tower barrel texture (off center by one pixel).

-(DONE) Clean up projectile collision. Make sure they're hitting the ~center of the target before doing damage. 

-Ensure setting of Game States to null is done efficiently/correctly.

-(DONE) In game instructions.

-Game pause menu is not clickable if a tower or AOE is selected.

-Rework Game update method so that enemies are drawn above towers, but AOEs are still above enemies.

-Clean up update method in Player class.

-Clean up isInRange methods.

-(DONE) Use helper class Clock to handle pausing more elegantly.

-(PARTIAL) Player levels
--(DONE) Exp bar to show progress to next level
--(PARTIAL) Unlock different towers, projectiles and AOE
--Unlock different tower, projectile and AOE upgrades
--Unlock player buffs (e.g. increase starting cash)
--Different maps have different difficulty (harder maps require higher player level)
--Player profiles can be saved and loaded

-Resizable Window.

-Lives, cash and wave have change animation (probably flashing).

-Buttons change color when hovered over/pressed.

-Floating notification on lives/cash change (e.g. place a tower that costs $40, and a little "-$40" floats up
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

-(PARTIAL) Change hardcoded constants to final variables.

-(DONE) Add pause function.

-(PARTIAL) Improved saving and loading. Play button loads a map from a file, and new maps can be loaded after 
 beginning a game. 

-(DONE) Tower selection menu and tower placement with mouse. (T button shows menu to select towertype from?).

-(DONE) Tower about to be placed shown at mouse cursor.

-(PARTIAL) More tower and projectile types, with unlock progression.
--(DONE) Rocket tower (Homing with splash damage)
--Multi-Target tower (archer tower? upgrade increases number of targets. store targets in array. on upgrade, replace array with one of bigger size).
--Railgun/Laser tower (Hits all targets in its path)

-(DONE) Projectiles originate from the end of the gun barrel (currently spawn not quite at center of tower).

-(DONE) Player controlled AOE actions (eg set all enemies on fire within radius x of mouse click)
--(DONE) Fire
--(DONE) Slow
--(DONE) Tower buffs

-(PARTIAL) One tower can shoot multiple types of projectiles. Perhaps "special shots" will be unlocked at a 
 certain level.
 
-(DONE) Manage deletion of "dead" projectiles (Use CopyOnWriteArrayList like with enemies)

-(DONE) Towers can be removed by player, probably with partial refund.

-(PARTIAL) More enemy types with level progression for the same enemy type.

-(DONE) Slowing of enemies is temporary.

-(PARTIAL) Lives and money displayed on-screen (Works, but I might try and make it look better).

-(DONE) Losing all lives causes a game over screen with an option to reset.

-(DONE) Checks made to see if enemy is on a certain square have tolerances that scale with time mult (currently static).

-Sound

-Balancing

-Other


Old Changes:
-Added Nuke special shot to rocket towers. It deals 50% damage to all enemies in the wave, and shoots every 6th 
 shot (5 normal shots between nukes).
-Checks made to see if enemy is on a certain square now have tolerances that scale with time mult.

-Ice projectiles now "hit" any enemy they collide with.
-Red bullets do the same.
-Balancing tweaks
-Some code cleanup/commenting

-Improved collision detection for projectiles.
--Now target the center of the enemy
--Collision method in projectile class
--Can be overridden for special projectile collisions (e.g. rocket detect collision based on tip of 
  rocket and hitbox).
-Overloaded projectile.calculateVelocity. Now to be used in subclasses.
-Rocket projectiles now target where they think the enemy will be.
-Rocket towers angle reflects their projectiles new targeting.

-Added new tower Rocket Launcher.
-Added new projectile Rocket.
--Does splash damage.
--Reacquires target if current target dies.
--Homing.
--Limited acceleration.

-Added game instructions (accessible at main menu or in game pause menu).

-Player level now written to screen.
-Player level exp bar shows progress towards next level.
-Player now gains +1 life upon leveling up.
-TowerUI is now static so that it can interact appropriately with player level.
-Tower Menu doesn't draw buttons for locked towers.
-UI.isButtonClicked checks to see if button is null before checking if its clicked.

-Fixed game pause menu bug (menu drawn behind enemies)

-Added player levels with some basic unlocks.
-Reworked Enemy classes so that leveling up of enemies is handled more easily.
-Added more UFO levels (now 1-5).
-Added more enemy waves (now 12, 2 per UFO level + 2 super waves!)
-FileChooser class will now work with player profiles once they're implemented (game saves).

-Added TowerBuff AOE.
-Added Slow AOE.
-Updated main menu.
-Fixed border on reset button.
-Fixed pause glitch.

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