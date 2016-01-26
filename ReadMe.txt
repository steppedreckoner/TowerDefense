NOT A FINISHED PRODUCT - STILL IN ACTIVE DEVELOPMENT

This project aims to both demonstrate and expand my programming ability. I initially began development 
in conjunction with a tutorial (found at https://www.youtube.com/channel/UC0MHs87ehhLOtPQf_bAWDEA), but
I have since diverged, including the following.

-Pathfinder class to handle enemy navigation
-Start and end tiles determined on loading map, no longer have to be hardcoded
-Navigation done with Dijkstra's algorithm. Allows for reliable navigation of maps with 
 tracks wider than 1 tile.
-Reworked the game map to be static
-Enums for easier selection of enemies and projectiles

Instructions:

PLAY: Begins a new game. Enemy type can be changed by pressing 1, 2 or 3. Note that each wave will be 
all the same type (type change will happen on the next wave). Place different towers with R, T or I.
each has a cost, and will not be placed if you don't have enough money. 

EDITOR: Loads a map with all grass tiles. Tile type can be modifed by clicking on a tile. Switch the type
that clicking changes to by pressing the right arrow key (default is grass, so initially clicking will
look like it's doing nothing). Save and Load features (S and L) are currently buggy, and probably won't work.

QUIT: Quits.

TO DO:

-Improved saving and loading. Play button loads a map from a file, and new maps can be loaded after 
beginning a game. 

-Tower selection menu and tower placement with mouse.

-More tower and projectile types, with unlock progression

-More enemy types with level progression for the same enemy type.

-No lives left causes a game over screen with option to reset

-Balancing

-Other