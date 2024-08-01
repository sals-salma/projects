package learn.gomoku;

import learn.gomoku.players.HumanPlayer;
import learn.gomoku.ui.GameController;

import javax.sound.midi.Soundbank;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        GameController gamePlay = new GameController();
        gamePlay.run();
    }
}
