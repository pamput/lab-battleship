package net.thoughtmachine;

import net.thoughtmachine.game.Game;
import net.thoughtmachine.parser.GameInputParser;
import net.thoughtmachine.parser.ParsedResult;
import net.thoughtmachine.printer.BoardPrinter;

import java.io.IOException;
import java.io.InputStream;

public class Application {

    private static final String DEFAULT_INPUT = "/input.txt";

    public void loadInput(String input) {
        try {

            InputStream in = getClass().getResourceAsStream(input);

            GameInputParser parser = new GameInputParser();

            ParsedResult result = parser.parseInput(in);

            Game game = new Game(result.getBoard());
            game.execute(result.getActions());

            BoardPrinter printer = new BoardPrinter();
            printer.print(game.getBoard(), System.out);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadInput() {
        loadInput(DEFAULT_INPUT);
    }

    public static void main(String... args) {
        Application app = new Application();

        if (args != null && args.length > 0) {
            app.loadInput(args[0]);
        } else {
            app.loadInput();
        }
    }
}
