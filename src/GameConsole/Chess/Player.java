package GameConsole.Chess;

public class Player {
    private String namePlayer;
    private int gameWins, gameLosses, playerScore, totalPlayers;
    private boolean playerWon;


    public Player(String namePlayer){

    }

    //getter for player attributes

    public String getNamePlayer(){
        return namePlayer;
    }
    public int getGameWins(){
        return gameWins;
    }
    public int getGameLoses(){
        return gameLosses;
    }
    public int getPlayerScore(){
        return playerScore;
    }




}
