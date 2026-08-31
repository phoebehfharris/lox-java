package xyz.phoebeharris.lox;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import xyz.phoebeharris.lox.Token;
import xyz.phoebeharris.lox.TokenType;

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

        assertEquals(tokens.get(0).type, TokenType.LEFT_PAREN);
    }
}
