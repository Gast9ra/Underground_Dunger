package game;

public interface OriginalCell {

    //need function because it's easier not save last position player
    boolean getCanMove();

    //link on map

    //link on game class for event

    //if cell need change position or anyone cell
    void update();

    String getClassName();

    }

