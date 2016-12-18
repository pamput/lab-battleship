package net.thoughtmachine;

import net.thoughtmachine.game.Game;
import net.thoughtmachine.parser.GameInputParser;
import net.thoughtmachine.parser.ParsedResult;
import net.thoughtmachine.printer.BoardPrinter;

import java.io.*;
import java.net.URISyntaxException;

public class Application {

    private static final String DEFAULT_INPUT = "/input.txt";
    private static final String DEFAULT_OUTPUT = "/output.txt";


    public void loadInput(String input, String output) throws IOException, URISyntaxException {

        File outFile = new File(output);
        if (!outFile.exists()) {
            outFile.createNewFile();
        }

        InputStream in = new FileInputStream(input);
        OutputStream out = new FileOutputStream(outFile);

        loadInput(in, out);
    }

    public void loadInput(String input) {
        InputStream in = getClass().getResourceAsStream(input);
        loadInput(in, System.out);
    }

    public void loadInput() throws IOException, URISyntaxException {
        loadInput(getClass().getResourceAsStream(DEFAULT_INPUT), System.out);
    }

    public void loadInput(InputStream in, OutputStream out) {
        try {

            GameInputParser parser = new GameInputParser();

            ParsedResult result = parser.parseInput(in);

            Game game = new Game(result.getBoard());
            game.execute(result.getActions());

            BoardPrinter printer = new BoardPrinter();
            printer.print(game.getBoard(), out);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String... args) throws IOException, URISyntaxException {
        Application app = new Application();

        if (args != null && args.length > 0) {

            if (args.length == 1) {
                app.loadInput(args[0]);
            } else {
                app.loadInput(args[0], args[1]);
            }

        } else {
            app.loadInput();
        }
    }
}
