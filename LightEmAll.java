import java.awt.Color;
import java.util.ArrayList;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.util.Random;

class LightEmAll extends World {
  ArrayList<GamePiece> nodes;
  ArrayList<Edge> mst;

  ArrayList<ArrayList<GamePiece>> board;
  WorldScene startingScene;
  Random rand;

  int width;
  int height;
  int size;
  int row;
  int col;
  int powerRow;
  int powerCol;
  int radius;
  
  int timer;
  int clicks;
  
  boolean usesKruskal;
  
  LightEmAll(int width, int height, int size, boolean usesKruskal) {
    this.board = new ArrayList<>();
    this.width = width;
    this.height = height;
    this.size = size;
    this.row = height / size;
    this.col = width / size;
    this.powerCol = 0;
    this.powerRow = 0;
    this.radius = 5;
    this.startingScene = new WorldScene(width, height);
    this.rand = new Random();
    this.nodes = new ArrayList<>();
    this.mst = new ArrayList<>();
    this.usesKruskal = usesKruskal;
    this.timer = 0;
    this.clicks = 0;
    
    createBoardKruskal();
    generateMST();
    connectNodes();
    randomizeTiles();

  }
  
  LightEmAll(int width, int height, int size, Random rand, boolean usesKruskal) {
    this.board = new ArrayList<>();
    this.width = width;
    this.height = height;
    this.size = size;
    this.row = height / size;
    this.col = width / size;
    this.powerCol = 0;
    this.powerRow = 0;
    this.radius = 5;
    this.startingScene = new WorldScene(width, height);
    this.rand = rand;
    this.nodes = new ArrayList<>();
    this.mst = new ArrayList<>();
    this.usesKruskal = usesKruskal;
    this.timer = 0;
    this.clicks = 0;
    
    
    
    createBoardKruskal();
    generateMSTSeeded();
    connectNodes();
    randomizeTilesSeeded();
   
  }

  LightEmAll(int width, int height, int size) {
    this.board = new ArrayList<>();
    this.width = width;
    this.height = height;
    this.size = size;
    this.row = height / size;
    this.col = width / size;
    this.powerCol = this.col / 2;
    this.powerRow = this.row / 2;
    this.radius = 5;
    this.startingScene = new WorldScene(width, height);
    this.rand = new Random();
    this.nodes = new ArrayList<>();
    this.mst = new ArrayList<>();
    this.usesKruskal = false;
    this.timer = 0;
    this.clicks = 0;
    
    createBoard();
    randomizeTiles();
    
  }
  

  LightEmAll(int width, int height, int size, Random rand) {
    this.board = new ArrayList<>();
    this.width = width;
    this.height = height;
    this.size = size;
    this.row = height / size;
    this.col = width / size;
    this.powerCol = this.col / 2;
    this.powerRow = this.row / 2;
    this.radius = 5;
    this.startingScene = new WorldScene(width, height);
    this.rand = rand;
    this.nodes = new ArrayList<>();
    this.mst = new ArrayList<>();
    this.usesKruskal = false;
    this.timer = 0;
    this.clicks = 0;
    
    
    createBoard();
    randomizeTilesSeeded();
  }

  // Constructor is used to test createBoard without the tiles
  // randomized
  LightEmAll() {
    this.board = new ArrayList<>();
    this.width = 120;
    this.height = 120;
    this.size = 40;
    this.row = 3;
    this.col = 3;
    this.powerCol = 1;
    this.powerRow = 1;
    this.radius = 5;
    this.timer = 0;
    this.clicks = 0;
    this.startingScene = new WorldScene(120, 120);
    this.rand = new Random();
    createBoard();
  }

  
  void buildWelcomeScene() {
    WorldImage welcomeMsg = new TextImage("Welcome to Minesweeper!", 20, FontStyle.BOLD, 
        Color.BLACK);
    WorldImage selectMsg = new TextImage("Please select one of the levels below:", 20, 
        FontStyle.BOLD, Color.BLACK);
    WorldImage easyLevel = new TextImage("Easy", 20, FontStyle.BOLD, Color.BLACK);
    WorldImage mediumLevel = new TextImage("Medium", 20, FontStyle.BOLD, Color.BLACK);
    WorldImage hardLevel = new TextImage("Hard", 20, FontStyle.BOLD, Color.BLACK);
    
    WorldImage easyBox = new OverlayImage(easyLevel, new RectangleImage(100, 50, 
        OutlineMode.SOLID, Color.RED));
    
    WorldImage mediumBox = new OverlayImage(mediumLevel, new RectangleImage(100, 50, 
        OutlineMode.SOLID, Color.RED));
    WorldImage hardBox = new OverlayImage(hardLevel, new RectangleImage(100, 50, 
        OutlineMode.SOLID, Color.RED));
    
    WorldImage backGround = new OverlayOffsetImage(new OverlayOffsetImage(welcomeMsg, 
        0, 30, selectMsg), 0, 50, 
        new RectangleImage(500, 300, OutlineMode.SOLID, Color.LIGHT_GRAY));
    WorldImage boxes = new OverlayOffsetImage(new OverlayOffsetImage(easyBox, 120, 
        0, mediumBox), 180, 0, hardBox);
    WorldImage welcomeScreen = new OverlayOffsetImage(boxes, 0, -30, backGround);
    
    this.startingScene.placeImageXY(welcomeScreen, 250, 150);
  }
  
  public void onTick() {
    this.timer = this.timer + 1;

  }
  
  
  
  // creates a board of game pieces
  void createBoard() {
    for (int i = 0; i < this.row; i++) {
      ArrayList<GamePiece> row = new ArrayList<GamePiece>();
      for (int j = 0; j < this.col; j++) {
        if (i == this.powerRow && j == this.powerCol) {
          GamePiece powerStation = new GamePiece(i, j, true, true, true, true);
          powerStation.togglePowerStation();
          row.add(powerStation);
        } else if (j == 0) {
          row.add(new GamePiece(i, j, false, true, false, false));
        } else if (j == this.col - 1) {
          row.add(new GamePiece(i, j, true, false, false, false));
        } else if (i == 0 && j == this.powerCol) {
          row.add(new GamePiece(i, j, true, true, false, true));
        } else if (i == this.row - 1 && j == this.powerCol) {
          row.add(new GamePiece(i, j, true, true, true, false));
        } else if (j == this.powerCol) {
          row.add(new GamePiece(i, j, true, true, true, true));
        } else {
          row.add(new GamePiece(i, j, true, true, false, false));
        }
      }
      this.board.add(row);
    }
  }

  // randomizes the rotation of all tiles in the board
  void randomizeTiles() {
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.col; j++) {
        int numTimesRotate = this.rand.nextInt(4);
        for (int k = 0; k < numTimesRotate; k++) {
          this.board.get(i).get(j).rotateTile();
        }
      }
    }
  }

  // randomizes the rotation of all tiles in the board using a seeded random
  // object
  void randomizeTilesSeeded() {
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.col; j++) {
        int numTimesRotate = this.rand.nextInt(4);
        for (int k = 0; k < numTimesRotate; k++) {
          this.board.get(i).get(j).rotateTile();
        }
      }
    }
  }

  // draws the game
  public WorldScene makeScene() {
    int y = (this.size / 2) + 30;
    int x = this.size / 2;
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.col; j++) {
        WorldImage image = this.board.get(j).get(i).drawTile(this.size, 2);
        this.startingScene.placeImageXY(image, (i * this.size) + x, (j * this.size) + y);
        
      }
    }
    
    WorldImage bg = new RectangleImage(260,25, OutlineMode.SOLID, Color.WHITE);
    WorldImage timer = new TextImage("Timer: " + this.timer, 15, FontStyle.BOLD,
        Color.BLACK);
    WorldImage clicks =  new TextImage("Moves: " + this.clicks, 15, FontStyle.BOLD,
        Color.BLACK);
    this.startingScene.placeImageXY(bg, 50, 15);
    this.startingScene.placeImageXY(timer, 40, 15);
    this.startingScene.placeImageXY(clicks, 135, 15);

    
    
    lightUpBoard();
    return this.startingScene;
  }

  // mutates the game based on the click of the mouse
  public void onMouseClicked(Posn mousePos, String buttonclicked) {
    int col = mousePos.x  / this.size;
    int row = (mousePos.y - 30) / this.size;
    if (buttonclicked.equals("LeftButton")) {
      this.board.get(row).get(col).rotateTile();
      this.clicks++;
    }
    lightUpBoard();
  }

  // mutates the game based on the key pressed
  public void onKeyEvent(String key) {
    this.movePower(key);
    
    lightUpBoard();
  }

  // moves the power station depending on the button clicked by the user
  void movePower(String buttonclicked) {
    int col = this.powerCol;
    int row = this.powerRow;
    if (buttonclicked.equals("left") && (this.powerCol != 0)) {
      if (this.board.get(row).get(col).left && this.board.get(row).get(col - 1).right) {
        this.board.get(row).get(col).togglePowerStation();
        this.board.get(row).get(col - 1).togglePowerStation();
        this.powerCol = col - 1;
        this.powerRow = row;
      }
    }

    if (buttonclicked.equals("right") && (this.powerCol != this.board.get(0).size())) {
      if (this.board.get(row).get(col).right && this.board.get(row).get(col + 1).left) {
        this.board.get(row).get(col).togglePowerStation();
        this.board.get(row).get(col + 1).togglePowerStation();
        this.powerCol = col + 1;
        this.powerRow = row;
      }
    }

    if (buttonclicked.equals("down") && (this.powerRow != this.board.size())) {
      if (this.board.get(row).get(col).bottom && this.board.get(row + 1).get(col).top) {
        this.powerCol = col;
        this.powerRow = row + 1;
        this.board.get(row).get(col).togglePowerStation();
        this.board.get(row + 1).get(col).togglePowerStation();
      }
    }

    if (buttonclicked.equals("up") && (this.powerRow != 0)) {
      if (this.board.get(row).get(col).top && this.board.get(row - 1).get(col).bottom) {
        this.powerCol = col;
        this.powerRow = row - 1;
        this.board.get(row).get(col).togglePowerStation();
        this.board.get(row - 1).get(col).togglePowerStation();
      }
    }
    

  }

  // uses breadth-first search to light up the board
  void lightUpBoard() {
    // resets all of the values to false
    for (ArrayList<GamePiece> row : this.board) {
      for (GamePiece tile : row) {
        tile.powered = false;
      }
    }

    ArrayList<GamePiece> queue = new ArrayList<>();
    GamePiece start = board.get(powerRow).get(powerCol);
    start.powered = true;
    queue.add(start);

    while (!queue.isEmpty()) {
      GamePiece current = queue.remove(0);
      ArrayList<GamePiece> neighbors = getNeighbors(current);
      for (GamePiece neighbor : neighbors) {
        if (!neighbor.powered) {
          neighbor.powered = true;
          queue.add(neighbor);
        }
      }
    }
    if (this.gameOver()) {
      this.lastScene("Gameover");
    }
  }

  // gets the neighbors of the given gamePiece
  ArrayList<GamePiece> getNeighbors(GamePiece piece) {
    ArrayList<GamePiece> neighbors = new ArrayList<>();
    int r = piece.row;
    int c = piece.col;
    if (piece.top && r > 0 && board.get(r - 1).get(c).bottom) {
      neighbors.add(board.get(r - 1).get(c));
    }

    if (piece.right && c < col - 1 && board.get(r).get(c + 1).left) {
      neighbors.add(board.get(r).get(c + 1));
    }

    if (piece.bottom && r < row - 1 && board.get(r + 1).get(c).top) {
      neighbors.add(board.get(r + 1).get(c));
    }

    if (piece.left && c > 0 && board.get(r).get(c - 1).right) {
      neighbors.add(board.get(r).get(c - 1));
    }
    return neighbors;
  }

  // returns a whether
  boolean gameOver() {
    boolean gameOver = true;
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.col; j++) {
        if (!(this.board.get(i).get(j).powered)) {
          gameOver = false;
        }
      }
    }
    return gameOver;
  }

  // draws the lastScene if the user has won the game
  public WorldScene lastScene(String msg) {
    this.startingScene.placeImageXY(new TextImage("You Win!", 20, FontStyle.BOLD, Color.GREEN),
        this.width / 2, this.height / 2);
    return this.startingScene;
  }
  
  // creates a board to set up the mst connections using Kruskal's algorithm
  void createBoardKruskal() {
    this.board = new ArrayList<>();
    for (int i = 0; i < this.row; i++) {
      ArrayList<GamePiece> rowList = new ArrayList<>();
      for (int j = 0; j < this.col; j++) {
        GamePiece piece = new GamePiece(i, j, false, false, false, false);
        if (i == 0 && j == 0) {
          piece.togglePowerStation();
          piece.togglePower();
        }
        rowList.add(piece);
        this.nodes.add(piece);
      }
      this.board.add(rowList);
    }
  }

  // creates a minimum spanning tree
  void generateMST() {
    ArrayList<Edge> edges = new ArrayList<>();
    UnionFind uf = new UnionFind();

    for (GamePiece node : this.nodes) {
      uf.makeSet(node);
      int nodeRow = node.row;
      int nodeCol = node.col;

      if (nodeCol + 1 < this.col) {
        edges.add(new Edge(node, this.board.get(nodeRow).get(nodeCol + 1), this.rand.nextInt(100)));
      }
      if (nodeRow + 1 < this.row) {
        edges.add(new Edge(node, this.board.get(nodeRow + 1).get(nodeCol), this.rand.nextInt(100)));
      }
    }

    // sorts the edges by weight
    edges.sort(new WeightComparator());

    for (Edge edge : edges) {
      if (uf.find(edge.fromNode) != uf.find(edge.toNode)) {
        this.mst.add(edge);
        uf.union(edge.fromNode, edge.toNode);
      }
    }
  }
  
  //creates a minimum spanning tree using a seeded random
  void generateMSTSeeded() {
    ArrayList<Edge> edges = new ArrayList<>();
    UnionFind uf = new UnionFind();

    for (GamePiece node : this.nodes) {
      uf.makeSet(node);
      int nodeRow = node.row;
      int nodeCol = node.col;

      if (nodeCol + 1 < this.col) {
        edges.add(new Edge(node, this.board.get(nodeRow).get(nodeCol + 1), this.rand.nextInt(100)));
      }
      if (nodeRow + 1 < this.row) {
        edges.add(new Edge(node, this.board.get(nodeRow + 1).get(nodeCol), this.rand.nextInt(100)));
      }
    }

    // sorts the edges by weight
    edges.sort(new WeightComparator());

    for (Edge edge : edges) {
      if (uf.find(edge.fromNode) != uf.find(edge.toNode)) {
        this.mst.add(edge);
        uf.union(edge.fromNode, edge.toNode);
      }
    }
  }

  // connects the nodes of the mst together in order to it possible to win the gam 
  void connectNodes() {
    for (Edge edge : this.mst) {
      GamePiece from = edge.fromNode;
      GamePiece to = edge.toNode;
      if (from.row == to.row) {
        if (from.col < to.col) {
          from.right = true;
          to.left = true;
        }
        else {
          from.left = true;
          to.right = true;
        }
      }
      else if (from.col == to.col) {
        if (from.row < to.row) {
          from.bottom = true;
          to.top = true;
        }
        else {
          from.top = true;
          to.bottom = true;
        }
      }
    }
  }
}