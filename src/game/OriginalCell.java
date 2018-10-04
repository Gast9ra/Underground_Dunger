package game;

public interface OriginalCell {

    //need function because it's easier not save last position player
    Boolean getCanMove();

    //if cell need change position or anyone cell
    void update(Map card);

    String getClassName();

    }
