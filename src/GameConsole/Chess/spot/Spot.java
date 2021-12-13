package GameConsole.Chess.spot;

public class Spot  {
    //X
    private int column;
    //Y
    private int row;

    public Spot(int column, int row)  {
        try{
            setInitialSpot(column,row);
        }catch(SpotException se){
            System.out.println(se.getMessage());
            System.out.println(se.getStackTrace());
        }

    }



    //only used to set initial spot
    //also check if spot is valid
    public void setInitialSpot(int column, int row) throws SpotException{
        //TODO check if spot is already taken. (initially, so basically a bug)

        if (column>=8||column<0){
            throw new SpotException("Column number has to be between 0-7");
        }
        if (row>=8||row<0){
            throw new SpotException("row number has to be between 0-7");
        }

        this.column=column;
        this.row=row;

    }

    @Override
    public String toString() {
        return String.format("(%d,%d)",column,row);
    }
}
