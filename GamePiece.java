import java.awt.Color;
import javalib.worldimages.*;

class GamePiece {
  int row;
  int col;
  
  boolean left;
  boolean right;
  boolean top;
  boolean bottom;
  
  boolean powerStation;
  boolean powered;
  
  GamePiece(int row, int col, boolean left, boolean right, boolean top, boolean bottom) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
    this.powerStation = false;
    this.powered = false;
  }
  
  // toggles if the tile is holding the powerStation or not
  void togglePowerStation() {
    this.powerStation = !this.powerStation;
  }
  
  // toggles if the cell is powered or not
  void togglePower() {
    this.powered = !this.powered;
  }
  
  // calls drawTile with a yellow wire if the tile is powered and gray if the tile is not
  WorldImage drawTile(int size, int wireWidth) {
    if (this.powered) {
      return tileImage(size, wireWidth, Color.YELLOW, this.powerStation);
    } else {
      return tileImage(size, wireWidth, Color.GRAY, this.powerStation);
    }
  }
  
  // draws the tile
  WorldImage tileImage(int size, int wireWidth, Color wireColor, 
      boolean hasPowerStation) {
    WorldImage image = new OverlayImage(
        new RectangleImage(wireWidth, wireWidth, OutlineMode.SOLID, wireColor),
        new RectangleImage(size, size, OutlineMode.SOLID, Color.DARK_GRAY));
    WorldImage vWire = new RectangleImage(wireWidth, (size + 1) / 2, 
        OutlineMode.SOLID, wireColor);
    WorldImage hWire = new RectangleImage((size + 1) / 2, wireWidth, 
        OutlineMode.SOLID, wireColor);
 
    if (this.top) {
      image = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.TOP, 
          vWire, 0, 0, image);
    }
    if (this.right) {
      image = new OverlayOffsetAlign(AlignModeX.RIGHT, AlignModeY.MIDDLE, 
          hWire, 0, 0, image);
    }
    if (this.bottom) {
      image = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.BOTTOM, 
          vWire, 0, 0, image);
    }
    if (this.left) {
      image = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE, 
          hWire, 0, 0, image);
    }
    if (hasPowerStation) {
      image = new OverlayImage(
                  new OverlayImage(
                      new StarImage(size / 3, 7, OutlineMode.OUTLINE, 
                          new Color(255, 128, 0)),
                      new StarImage(size / 3, 7, OutlineMode.SOLID, 
                          new Color(0, 255, 255))),
                  image);
    }
    return image;
  }
  
  // rotates the wire ninety degrees clock-wise
  public void rotateTile() {
    GamePiece temp = new GamePiece(this.row,this.col,this.left, 
        this.right, this.top, this.bottom);
    this.right = temp.top;
    this.bottom = temp.right;
    this.left = temp.bottom;
    this.top = temp.left;
    
  }
}