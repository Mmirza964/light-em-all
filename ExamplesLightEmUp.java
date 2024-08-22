import tester.Tester;
import java.util.ArrayList;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;
import java.util.HashMap;

// holds all of the examples and tests
class ExamplesLightEmAll {
  // Examples of LightEmAll
  LightEmAll lea1; // big grid with no seed
  LightEmAll lea2; // manually created board used to test createBoard
  LightEmAll lea3; // big grid with seed
  LightEmAll lea4; // small grid with seed
  LightEmAll lea5; // uses Kruskal to generate the board
  LightEmAll lea6; // uses Kruskal and seeded random to generate the board

  // Examples of GamePiece
  GamePiece gp1;
  GamePiece gp2;
  GamePiece gp3;
  GamePiece gp4;
  GamePiece gp5;
  GamePiece gp6;
  GamePiece gp7;
  GamePiece gp8;
  GamePiece gp9;
  GamePiece gp10;
  GamePiece gp11;

  // examples of Edge
  Edge edge1;
  Edge edge2;
  Edge edge3;
  Edge edge4;
  Edge edge5;

  // example of WeightComparator
  WeightComparator wc;

  // example of UnionFind
  UnionFind uf;

  // sets the values of the examples
  void reset() {
    lea1 = new LightEmAll(400, 400, 40);
    lea2 = new LightEmAll();
    lea3 = new LightEmAll(600, 600, 30, new Random(1));
    lea4 = new LightEmAll(150, 150, 50, new Random(1));
    lea5 = new LightEmAll(300, 300, 30, true);
    lea6 = new LightEmAll(180, 180, 60, new Random(1), true);

    gp1 = new GamePiece(1, 2, true, false, false, false);
    gp2 = new GamePiece(0, 0, false, true, false, false);
    gp3 = new GamePiece(1, 0, false, false, true, false);
    gp4 = new GamePiece(2, 2, false, false, false, true);
    gp5 = new GamePiece(5, 2, true, true, true, false);
    gp6 = new GamePiece(1, 2, true, true, false, true);
    gp7 = new GamePiece(1, 2, true, false, true, true);
    gp8 = new GamePiece(1, 2, false, true, true, true);
    gp9 = new GamePiece(1, 2, true, true, false, false);
    gp10 = new GamePiece(1, 2, false, false, true, true);
    gp11 = new GamePiece(1, 2, true, true, true, true);

    edge1 = new Edge(gp1, gp2, 10);
    edge2 = new Edge(gp2, gp3, 20);
    edge3 = new Edge(gp3, gp4, 10);
    edge4 = new Edge(gp4, gp5, 40);
    edge5 = new Edge(gp5, gp1, 50);

    wc = new WeightComparator();
    uf = new UnionFind();
  }

  // runs the bigBang
  void testBigBang(Tester t) {
    reset();
    lea5.bigBang(600, 600, 1);
  }

  // tests createBoard
  void testCreateBoard(Tester t) {
    // resets the values
    reset();

    // manually builds the expected output of createBoard
    ArrayList<ArrayList<GamePiece>> board = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<GamePiece> row1 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> row2 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> row3 = new ArrayList<GamePiece>();

    row1.add(new GamePiece(0, 0, false, true, false, false));
    row1.add(new GamePiece(0, 1, true, true, false, true));
    row1.add(new GamePiece(0, 2, true, false, false, false));
    row2.add(new GamePiece(1, 0, false, true, false, false));
    GamePiece gp = new GamePiece(1, 1, true, true, true, true);
    gp.togglePowerStation();
    row2.add(gp);
    row2.add(new GamePiece(1, 2, true, false, false, false));
    row3.add(new GamePiece(2, 0, false, true, false, false));
    row3.add(new GamePiece(2, 1, true, true, true, false));
    row3.add(new GamePiece(2, 2, true, false, false, false));

    board.add(row1);
    board.add(row2);
    board.add(row3);

    // tests if the expected is the same board as the LightEmAll object
    // no method is called because createBoard is called in the constructor
    // of LightEmAll
    t.checkExpect(lea2.board, board);
  }

  // tests randomizeTilesSeeded method
  void testRandomizeTilesSeeded(Tester t) {
    // resets values
    reset();

    // manually builds the board to check with the actual value of the method
    ArrayList<ArrayList<GamePiece>> board = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<GamePiece> row1 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> row2 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> row3 = new ArrayList<GamePiece>();

    row1.add(new GamePiece(0, 0, true, false, false, false));
    row1.add(new GamePiece(0, 1, true, true, false, true));
    row1.add(new GamePiece(0, 2, false, false, true, false));
    row2.add(new GamePiece(1, 0, false, false, false, true));
    GamePiece gp = new GamePiece(1, 1, true, true, true, true);
    gp.togglePowerStation();
    row2.add(gp);
    row2.add(new GamePiece(1, 2, true, false, false, false));
    row3.add(new GamePiece(2, 0, false, false, false, true));
    row3.add(new GamePiece(2, 1, true, true, false, true));
    row3.add(new GamePiece(2, 2, false, false, false, true));

    board.add(row1);
    board.add(row2);
    board.add(row3);

    // tests if the expected board is equal to the LightEmAll object
    // randomizeTilesSeeded isn't called in this test because it is called
    // in the constructor of the object
    t.checkExpect(this.lea4.board, board);
  }

  // tests togglePowerStation
  void testTogglePowerStation(Tester t) {
    // resets the values
    reset();

    // tests that gp1 is false before calling the method
    t.checkExpect(gp1.powerStation, false);
    gp1.togglePowerStation();
    // test that gp1 is true after calling the method
    t.checkExpect(gp1.powerStation, true);
    gp1.togglePowerStation();
    // tests that gp1 is false after calling the method again
    t.checkExpect(gp1.powerStation, false);
  }

  // tests togglePower
  void testTogglePower(Tester t) {
    // resets the values
    reset();

    // tests that gp1 is false before calling the method
    t.checkExpect(gp1.powered, false);
    gp1.togglePower();
    // test that gp1 is true after calling the method
    t.checkExpect(gp1.powered, true);
    gp1.togglePower();
    // tests that gp1 is false after calling the method again
    t.checkExpect(gp1.powered, false);
  }

  // tests rotateTile method
  void testRotateTile(Tester t) {
    // resets the values
    reset();

    // tests rotating a wire with one value set to true
    t.checkExpect(gp1.left, true);
    t.checkExpect(gp1.right, false);
    t.checkExpect(gp1.top, false);
    t.checkExpect(gp1.bottom, false);
    gp1.rotateTile();
    t.checkExpect(gp1.left, false);
    t.checkExpect(gp1.right, false);
    t.checkExpect(gp1.top, true);
    t.checkExpect(gp1.bottom, false);

    // tests rotating a wire with three values set to true
    t.checkExpect(gp5.left, true);
    t.checkExpect(gp5.right, true);
    t.checkExpect(gp5.top, true);
    t.checkExpect(gp5.bottom, false);
    gp5.rotateTile();
    t.checkExpect(gp5.left, false);
    t.checkExpect(gp5.right, true);
    t.checkExpect(gp5.top, true);
    t.checkExpect(gp5.bottom, true);

    // tests rotating a wire with two values set to true
    t.checkExpect(gp9.left, true);
    t.checkExpect(gp9.right, true);
    t.checkExpect(gp9.top, false);
    t.checkExpect(gp9.bottom, false);
    gp9.rotateTile();
    t.checkExpect(gp9.left, false);
    t.checkExpect(gp9.right, false);
    t.checkExpect(gp9.top, true);
    t.checkExpect(gp9.bottom, true);

    // tests rotating a wire with all values set to true
    t.checkExpect(gp11.left, true);
    t.checkExpect(gp11.right, true);
    t.checkExpect(gp11.top, true);
    t.checkExpect(gp11.bottom, true);
    gp11.rotateTile();
    t.checkExpect(gp11.left, true);
    t.checkExpect(gp11.right, true);
    t.checkExpect(gp11.top, true);
    t.checkExpect(gp11.bottom, true);
  }

  // tests drawTile method. Used tileImage that was given in the prompt to test
  // did not test tileImage because no changes were made to it
  void testDrawTile(Tester t) {
    reset();
    // tests drawing a tile with an unpowered wire
    t.checkExpect(gp1.drawTile(40, 5), gp1.tileImage(40, 5, Color.GRAY, false));
    // tests drawing a tile with a powered wire
    gp1.togglePower();
    t.checkExpect(gp1.drawTile(40, 5), gp1.tileImage(40, 5, Color.YELLOW, false));
    // tests drawing a tile with a powered wire and a power station
    gp1.togglePowerStation();
    t.checkExpect(gp1.drawTile(40, 5), gp1.tileImage(40, 5, Color.YELLOW, true));
    // tests drawing a tile with a unpowered wire and a power station
    gp1.togglePower();
    t.checkExpect(gp1.drawTile(40, 5), gp1.tileImage(40, 5, Color.GRAY, true));

    // all of the test below test the same things as the tests above but with other
    // wires
    // tests a t-wire
    t.checkExpect(gp5.drawTile(40, 5), gp5.tileImage(40, 5, Color.GRAY, false));
    gp5.togglePower();
    t.checkExpect(gp5.drawTile(40, 5), gp5.tileImage(40, 5, Color.YELLOW, false));
    gp5.togglePowerStation();
    t.checkExpect(gp5.drawTile(40, 5), gp5.tileImage(40, 5, Color.YELLOW, true));
    gp5.togglePower();
    t.checkExpect(gp5.drawTile(40, 5), gp5.tileImage(40, 5, Color.GRAY, true));
    // tests a straight wire
    t.checkExpect(gp9.drawTile(40, 5), gp9.tileImage(40, 5, Color.GRAY, false));
    gp9.togglePower();
    t.checkExpect(gp9.drawTile(40, 5), gp9.tileImage(40, 5, Color.YELLOW, false));
    gp9.togglePowerStation();
    t.checkExpect(gp9.drawTile(40, 5), gp9.tileImage(40, 5, Color.YELLOW, true));
    gp9.togglePower();
    t.checkExpect(gp9.drawTile(40, 5), gp9.tileImage(40, 5, Color.GRAY, true));
    // tests a cross wire
    t.checkExpect(gp11.drawTile(40, 5), gp11.tileImage(40, 5, Color.GRAY, false));
    gp11.togglePower();
    t.checkExpect(gp11.drawTile(40, 5), gp11.tileImage(40, 5, Color.YELLOW, false));
    gp11.togglePowerStation();
    t.checkExpect(gp11.drawTile(40, 5), gp11.tileImage(40, 5, Color.YELLOW, true));
    gp11.togglePower();
    t.checkExpect(gp11.drawTile(40, 5), gp11.tileImage(40, 5, Color.GRAY, true));
  }

  // tests gameOver method
  void testGameOver(Tester t) {
    // resets values
    reset();
    // tests that all the wires are not powered
    t.checkExpect(lea4.gameOver(), false);
    // manually makes all of the wires powered
    lea4.board.get(0).get(0).powered = true;
    lea4.board.get(0).get(1).powered = true;
    lea4.board.get(0).get(2).powered = true;
    lea4.board.get(1).get(0).powered = true;
    lea4.board.get(1).get(1).powered = true;
    lea4.board.get(1).get(2).powered = true;
    lea4.board.get(2).get(0).powered = true;
    lea4.board.get(2).get(1).powered = true;
    lea4.board.get(2).get(2).powered = true;
    // tests that all wires are powered and the game is over
    t.checkExpect(lea4.gameOver(), true);
  }

  // tests lastScene method
  void testLastScene(Tester t) {
    // resets values
    reset();
    // makes scene before calling last scene
    WorldScene scene = lea4.startingScene;
    t.checkExpect(lea4.startingScene, scene);
    scene.placeImageXY(new TextImage("You Won!", 20, FontStyle.BOLD, Color.GREEN), 75, 75);
    // tests that scene is the same as the lastScene for lea4
    t.checkExpect(lea4.lastScene("Gameover"), scene);
  }

  void testonMouse(Tester t) {
    reset();
    // checks what directions a piece can connect to
    t.checkExpect(this.lea4.board.get(0).get(0).top, false);
    t.checkExpect(this.lea4.board.get(0).get(0).bottom, false);
    t.checkExpect(this.lea4.board.get(0).get(0).left, true);
    t.checkExpect(this.lea4.board.get(0).get(0).right, false);
    // calls on mouse click
    this.lea4.onMouseClicked(new Posn(1, 1), "LeftButton");
    // checks to make sure the directions have changed clockwise
    t.checkExpect(this.lea4.board.get(0).get(0).top, true);
    t.checkExpect(this.lea4.board.get(0).get(0).bottom, false);
    t.checkExpect(this.lea4.board.get(0).get(0).left, false);
    t.checkExpect(this.lea4.board.get(0).get(0).right, false);
    // checks what directions a piece can connect to
    t.checkExpect(this.lea4.board.get(0).get(1).top, false);
    t.checkExpect(this.lea4.board.get(0).get(1).bottom, true);
    t.checkExpect(this.lea4.board.get(0).get(1).left, true);
    t.checkExpect(this.lea4.board.get(0).get(1).right, true);
    // calls on mouse click
    this.lea4.onMouseClicked(new Posn(60, 10), "LeftButton");
    // checks to make sure the directions have changed clockwise
    t.checkExpect(this.lea4.board.get(0).get(1).top, true);
    t.checkExpect(this.lea4.board.get(0).get(1).bottom, true);
    t.checkExpect(this.lea4.board.get(0).get(1).left, true);
    t.checkExpect(this.lea4.board.get(0).get(1).right, false);
    // checks what directions a piece can connect to
    t.checkExpect(this.lea4.board.get(1).get(1).top, true);
    t.checkExpect(this.lea4.board.get(1).get(1).bottom, true);
    t.checkExpect(this.lea4.board.get(1).get(1).left, true);
    t.checkExpect(this.lea4.board.get(1).get(1).right, true);
    // calls on mouse click
    this.lea4.onMouseClicked(new Posn(60, 10), "LeftButton");
    // checks to make sure the directions have changed clockwise
    t.checkExpect(this.lea4.board.get(1).get(1).top, true);
    t.checkExpect(this.lea4.board.get(1).get(1).bottom, true);
    t.checkExpect(this.lea4.board.get(1).get(1).left, true);
    t.checkExpect(this.lea4.board.get(1).get(1).right, true);
  }

  // test move power method
  void testmovePower(Tester t) {
    reset();
    // checks coordinates of power grid
    t.checkExpect(this.lea4.powerCol, 1);
    t.checkExpect(this.lea4.powerRow, 1);
    // moves power grid up
    this.lea4.movePower("up");
    // checks to make sure the coordinates change
    t.checkExpect(this.lea4.powerCol, 1);
    t.checkExpect(this.lea4.powerRow, 0);
    // checks coodrinates of power grid
    t.checkExpect(this.lea4.powerCol, 1);
    t.checkExpect(this.lea4.powerRow, 0);
    // tries to move power grid in a direction it cant be moved
    this.lea4.movePower("right");
    // checks coodinates didnt change
    t.checkExpect(this.lea4.powerCol, 1);
    t.checkExpect(this.lea4.powerRow, 0);
  }

  // tests light up board method
  void testlightUpBoard(Tester t) {
    reset();
    // manually sets all power in grid to false
    this.lea4.board.get(0).get(0).powered = false;
    this.lea4.board.get(0).get(1).powered = false;
    this.lea4.board.get(0).get(2).powered = false;
    this.lea4.board.get(1).get(0).powered = false;
    this.lea4.board.get(1).get(1).powered = false;
    this.lea4.board.get(1).get(2).powered = false;
    this.lea4.board.get(2).get(0).powered = false;
    this.lea4.board.get(2).get(1).powered = false;
    this.lea4.board.get(2).get(2).powered = false;
    // calls light up board
    this.lea4.lightUpBoard();
    // checks above neighbor is lit
    t.checkExpect(this.lea4.board.get(0).get(1).powered, true);
    // checks cell power grid is on is lit
    t.checkExpect(this.lea4.board.get(1).get(1).powered, true);
    // tests right neighbor is list
    t.checkExpect(this.lea4.board.get(1).get(2).powered, true);
  }

  // tests getNeighbors
  void testgetNeighbors(Tester t) {
    reset();
    // tests game piece with no neighbors
    t.checkExpect(this.lea4.getNeighbors(this.lea4.board.get(0).get(0)), new ArrayList<>());
    // tests game piece with neighbors
    ArrayList<GamePiece> temp = new ArrayList<>();
    temp.add(this.lea4.board.get(0).get(1));
    temp.add(this.lea4.board.get(1).get(2));
    t.checkExpect(this.lea4.getNeighbors(this.lea4.board.get(1).get(1)), temp);
  }

  // tests makeScene method
  void testMakeScene(Tester t) {
    // resets values
    reset();
    // manually builds the scene
    WorldScene scene = new WorldScene(150, 150);
    for (int i = 0; i < lea4.row; i++) {
      for (int j = 0; j < lea4.col; j++) {
        GamePiece gp = lea4.board.get(j).get(i);
        WorldImage image = gp.drawTile(50, 2);
        scene.placeImageXY(image, (i * 50) + 25, (j * 50) + 55);

      }
    }
    WorldImage bg = new RectangleImage(260,25, OutlineMode.SOLID, Color.WHITE);
    WorldImage timer = new TextImage("Timer: " + lea4.timer, 15, FontStyle.BOLD,
        Color.BLACK);
    WorldImage clicks =  new TextImage("Moves: " + lea4.clicks, 15, FontStyle.BOLD,
        Color.BLACK);
    scene.placeImageXY(bg, 50, 15);
    scene.placeImageXY(timer, 40, 15);
    scene.placeImageXY(clicks, 135, 15);
    // tests that the scene is the same is makeScene
    t.checkExpect(lea4.makeScene(), scene);
  }

  // test onTick method
  void testonTick(Tester t) {
    // resets values
    reset();
    // tests adding one to the timer
    t.checkExpect(this.lea4.timer, 0);
    this.lea4.onTick();
    t.checkExpect(this.lea4.timer, 1);
  }
  
  // tests compare method in comparator class
  boolean testCompare(Tester t) {
    reset();
    // tests when one edge has a higher weight than the other
    return t.checkExpect(wc.compare(edge4, edge1), 30)
        // tests when one edge has the same weight as another
        && t.checkExpect(wc.compare(edge1, edge3), 0)
        // tests when one edge has a lower weight than the other
        && t.checkExpect(wc.compare(edge1, edge5), -40);
  }

  // tests makeSet method
  void testMakeSet(Tester t) {
    // resets values
    reset();
    // checks that HashMap is empty before calling method
    t.checkExpect(uf.parent, new HashMap<>());
    // creates a local HashMap puts gp1 in it
    HashMap<GamePiece, GamePiece> test = new HashMap<>();
    test.put(gp1, gp1);
    // call the method on uf
    uf.makeSet(gp1);
    // tests that the mutation of the method matches the test HashMap
    t.checkExpect(uf.parent, test);
  }

  // tests find method
  void testFind(Tester t) {
    // resets values
    reset();
    // makes sets for testing
    uf.makeSet(gp1);
    uf.makeSet(gp2);
    // tests that the root of a GamePiece in a set is itself
    t.checkExpect(uf.find(gp1), gp1);
    // same test but with a different GamePiece
    t.checkExpect(uf.find(gp2), gp2);
    // combines two GamePieces for testing
    uf.union(gp1, gp2);
    // checks that root of a union is the second GamePiece
    t.checkExpect(uf.find(gp1), gp2);
  }

  // tests union method
  void testUnion(Tester t) {
    // resets values;
    reset();
    // checks combing two distinct GamePieces
    uf.union(gp1, gp2);
    t.checkExpect(uf.find(gp1), uf.find(gp2));
    // checks combing two GamePieces that are already in a set
    uf.union(gp1, gp2);
    t.checkExpect(uf.find(gp1), uf.find(gp1));
    // tests combining two sets to make a larger one
    uf.union(gp3, gp4);
    uf.union(gp1, gp3);
    t.checkExpect(uf.find(gp2), uf.find(gp4));
    // tests combing two of the same GamePieces
    uf.union(gp5, gp5);
    t.checkExpect(uf.find(gp5), uf.find(gp5));
  }

  // tests createBoardKruskal
  void testCreateBoardKruskal(Tester t) {
    // resets values
    reset();
    // manually builds the board
    GamePiece g1 = new GamePiece(0, 0, false, false, true, false);
    g1.togglePowerStation();
    g1.togglePower();
    GamePiece g2 = new GamePiece(0, 1, true, true, false, true);
    GamePiece g3 = new GamePiece(0, 2, false, false, false, true);
    GamePiece g4 = new GamePiece(1, 0, true, false, true, false);
    GamePiece g5 = new GamePiece(1, 1, true, false, true, true);
    GamePiece g6 = new GamePiece(1, 2, false, true, false, true);
    GamePiece g7 = new GamePiece(2, 0, true, false, true, false);
    GamePiece g8 = new GamePiece(2, 1, false, false, true, false);
    GamePiece g9 = new GamePiece(2, 2, false, true, false, false);
    ArrayList<GamePiece> row1 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> row2 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> row3 = new ArrayList<GamePiece>();
    row1.add(g1);
    row1.add(g2);
    row1.add(g3);
    row2.add(g4);
    row2.add(g5);
    row2.add(g6);
    row3.add(g7);
    row3.add(g8);
    row3.add(g9);
    ArrayList<ArrayList<GamePiece>> board = new ArrayList<ArrayList<GamePiece>>();
    board.add(row1);
    board.add(row2);
    board.add(row3);

    // no call of the method is necessary because the method is called when constructing
    // the LightEmAll object
    // tests that the GamePieces were added to the nodes list
    t.checkExpect(lea6.nodes.size(), 9);
    // tests to make sure the 3 X 3 board is made correctly
    t.checkExpect(lea6.board.size(), 3);
    // tests that LightEmAll board is the same as the manually created board
    t.checkExpect(lea6.board, board);
  }

  // tests generateMSTSeeded method
  void testGenerateMSTSeeded(Tester t) {
    // resets values
    reset();

    // checks there is correct number of edges
    t.checkExpect(lea6.mst.size(), (lea6.nodes.size()) - 1);

    // makes empty list which will have visited things in it
    ArrayList<GamePiece> visited = new ArrayList<GamePiece>();
    // makes empty list which will have things to visit in it
    ArrayList<GamePiece> toVisit = new ArrayList<GamePiece>();

    // Start with the first node
    toVisit.add(lea6.nodes.get(0));

    // checks to see if every node is visited and keeps going
    // until every node is visited
    while (!toVisit.isEmpty()) {
      GamePiece current = toVisit.remove(0);
      visited.add(current);
      // Iterate over all edges in MST
      for (Edge e : lea6.mst) {
        // check if current is starting point of edge and
        // ending node has not been visited
        if (e.fromNode.equals(current) && !visited.contains(e.toNode)) {
          toVisit.add(e.toNode);
        }
        // check if current is ending point of edge and
        // starting node has not been visited
        else if (e.toNode.equals(current) && !visited.contains(e.fromNode)) {
          // adds unvisied node to toVisit
          toVisit.add(e.fromNode);
        }
      }
    }
    // Check if all nodes were visited
    t.checkExpect(visited.size(), lea6.nodes.size());
  }

  // tests connectNodes
  void testConnectNodes(Tester t) {
    // resets values
    reset();
    // making board manually
    GamePiece topLeft = lea6.board.get(0).get(0);
    GamePiece topRight = lea6.board.get(0).get(1);
    GamePiece bottomLeft = lea6.board.get(1).get(0);
    GamePiece bottomRight = lea6.board.get(1).get(1);

    // manually makes edges
    Edge e1 = new Edge(topRight, topLeft, 5);
    Edge e2 = new Edge(topRight, bottomRight, 40);

    // adds edges to MST
    this.lea6.mst.add(e1);
    this.lea6.mst.add(e2);

    // calls connect nodes on lea3
    this.lea6.connectNodes();

    // checks that all nodes are now connected
    t.checkExpect(topLeft.right, true);
    t.checkExpect(topRight.left, true);
    t.checkExpect(topRight.bottom, true);
    t.checkExpect(bottomRight.top, true);
    t.checkExpect(bottomLeft.top, true);
  }
}