# SelfLearningTicTacToe

This Tic-Tac-Toe game is in progress. There are currently 4 ways to run it. Bot vs User, User VS Bot, and Bot vs Bot. The idea behind the game is that when a move is made, it will be saved with a reference to the moves that it lead to. This means that the game starts as a one node tree that starts developing children nodes as more games are run. Since data structures use volatile memory, the nodes of the trees are store in external JSON files. These JSON files are then accessed and updated with every new game.

The nodes/JSON files have information representing the state of the Tic-Tac-Toe board and children representing future moves that were completed in past games.  In addition to this, each child node has information indicating the percetange of X or O winning or trying. 

Current Status 5/10/20: Bots are learning from previous moves and making better decisions as more games are run; However, in situation where the opponent is about to win, the bot choses to follow the the path that will lead to it winning without taking into account that it about to lose.  This means that a bot vs bot games will play againts eachother without trying to sabotage eachothers game. This functionality has yet to be implemented and is skewing the result. 



SMART = Learning from previous games
DUMB = Randomly generated positions

The following are the results after running each situation 10k times:

SMART X VS SMART O 10k games (Folder: LearningXVSLearningO)

Amount of X WINS: 4889
Amount of O WINS: 3344
Amount of TIES: 1767

DUMB X vs DUMB O 10k games

Amount of X WINS: 5788
Amount of O WINS: 2971
Amount of TIES: 1241

SMART X VS DUMB O 10k games (Folder: LearningXVSRandomO)

Amount of X WINS: 6637
Amount of O WINS: 2044
Amount of TIES: 1319

DUMB X VS SMART O 10k games (Folder: RandomXVsLearningO)

Amount of X WINS: 4509
Amount of O WINS: 3920
Amount of TIES: 1571


