package it.polimi.myshelfie.model.goals;


public enum CommonGoalType {

    SIXGROUPSOFTWO("Six groups each containing at least " +
            "2 tiles of the same type (not necessarily " +
            "in the depicted shape). " +
            "The tiles of one group can be different " +
            "from those of another group."),
    FOURGROUPSOFFOUR("Four groups each containing at least " +
            "4 tiles of the same type (not necessarily " +
            "in the depicted shape). " +
            "The tiles of one group can be different " +
            "from those of another group. "),
    CORNER("Four tiles of the same type in the four " +
            "corners of the bookshelf. "),
    TWOSQUARES("Two groups each containing 4 tiles of " +
            "the same type in a 2x2 square. The tiles " +
            "of one square can be different from " +
            "those of the other square. "),
    THREECOLUMNS("Three columns each formed by 6 tiles " +
            "of maximum three different types. One " +
            "column can show the same or a different " +
            "combination of another column. "),
    EIGHTTILES("Eight tiles of the same type. There’s no " +
            "restriction about the position of these " +
            "tiles. "),
    DIAGONAL("Five tiles of the same type forming a" +
            "diagonal. "),
    FOURLINES("Four lines each formed by 5 tiles of " +
            "maximum three different types. One " +
            "line can show the same or a different " +
            "combination of another line. "),
    TWOCOLUMNS("Two columns each formed by 6 " +
            "different types of tiles. "),
    TWOLINES("Two lines each formed by 5 different " +
            "types of tiles. One line can show the " +
            "same or a different combination of the " +
            "other line. "),
    X("Five tiles of the same type forming an X. "),
    STAIRS("Five columns of increasing or decreasing " +
            "height. Starting from the first column on " +
            "the left or on the right, each next column " +
            "must be made of exactly one more tile. " +
            "Tiles can be of any type. ");

    private final String description;

    public String getDescription() {
        return description;
    }

    CommonGoalType(String description){
        this.description = description;
    }
}
