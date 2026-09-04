package xyz.phoebeharris.lox;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {
    @Test
    public void testScanSuccess() {
        String source = """
            // this is a comment
            (( )){} // grouping stuff
        !*+-/=<> <= == // operators
                  """;

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertIterableEquals(new ArrayList<Token>(List.of(
                                                          new Token(TokenType.LEFT_PAREN, "(", null, 2),
                                                          new Token(TokenType.LEFT_PAREN, "(", null, 2),
                                                          new Token(TokenType.RIGHT_PAREN, ")", null, 2),
                                                          new Token(TokenType.RIGHT_PAREN, ")", null, 2),
                                                          new Token(TokenType.LEFT_BRACE, "{", null, 2),
                                                          new Token(TokenType.RIGHT_BRACE, "}", null, 2),
                                                          new Token(TokenType.BANG, "!", null, 3),
                                                          new Token(TokenType.STAR, "*", null, 3),
                                                          new Token(TokenType.PLUS, "+", null, 3),
                                                          new Token(TokenType.MINUS, "-", null, 3),
                                                          new Token(TokenType.SLASH, "/", null, 3),
                                                          new Token(TokenType.EQUAL, "=", null, 3),
                                                          new Token(TokenType.LESS, "<", null, 3),
                                                          new Token(TokenType.GREATER, ">", null, 3),
                                                          new Token(TokenType.LESS_EQUAL, "<=", null, 3),
                                                          new Token(TokenType.EQUAL_EQUAL, "==", null, 3),
                                                          new Token(TokenType.EOF, "", null, 4))),
                             tokens);
    }

    @Test
    public void testScanFail() {
        String source = "\""; // Invalid string termination
	
        Scanner scanner = new Scanner(source);

        System.out.println("SHOULD ERROR: \"Unterminated string.\"");
        scanner.scanTokens();
    }

    @Test
    public void testString() {
        String source = "\"Mew\"";

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        // Should be: String token
        assertIterableEquals(new ArrayList<Token>(List.of(
                new Token(TokenType.STRING, "\"Mew\"", new String("Mew"), 1),
                new Token(TokenType.EOF, "", null, 1))),
                tokens);

    }

    @Test
    public void testMultilineComment() {
        String source = """
                /* Nest one*/
                test_token
                /*/*Nest two*/*/
                """;

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertIterableEquals(new ArrayList<Token>(List.of(
                new Token(TokenType.IDENTIFIER, "test_token", null, 2),
                new Token(TokenType.EOF, "", null, 4))),
                tokens);
    }

    @Test
    public void testMultilineCommentFail() {
        String source = "/*";

        Scanner scanner = new Scanner(source);

        System.out.println("SHOULD ERROR: \"Unterminated multiline comment.\"");
        scanner.scanTokens();
    }
}
