package xyz.phoebeharris.lox;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    public void testScanSuccess() {
        String source = """
40 == 2 * 2 * (6 + 4)
""";

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        assertEquals(new Expr.Binary(
                new Expr.Literal(40.0),
                new Token(TokenType.EQUAL_EQUAL, "==", null, 1),
                new Expr.Binary(
                        new Expr.Binary(
                                new Expr.Literal(2.0),
                                new Token(TokenType.STAR, "*", null, 1),
                                new Expr.Literal(2.0)),
                        new Token(TokenType.STAR, "*", null, 1),
                        new Expr.Grouping(
                                new Expr.Binary(
                                        new Expr.Literal(6.0),
                                        new Token(TokenType.PLUS, "+", null, 1),
                                        new Expr.Literal(4.0))))),
                expression);
    }

    // TODO: Won't run yet until we implement assignment
    //@Test
    //public void testAssignmentAssociativity() {
    //    String source = "1 = 1 = 1";

    //    Scanner scanner = new Scanner(source);
    //    List<Token> tokens = scanner.scanTokens();
    //    Parser parser = new Parser(tokens);
    //    Expr expression = parser.parse();

    //    assertEquals(
    //        new Expr.Binary(
    //            new Expr.Literal(1.0),
    //            new Token(TokenType.EQUAL, "=", null, 1),
    //            new Expr.Binary(
    //                new Expr.Literal(1.0),
    //                new Token(TokenType.EQUAL, "=", null, 1),
    //                new Expr.Literal(1.0))
    //        ),
    //        expression
    //    );
    //}

    @Test
    public void testParseCommaOp() {
        String source = "1 == 1, 2 == 2";

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        assertEquals(
            new Expr.Binary(
                new Expr.Binary(
                    new Expr.Literal(1.0),
                    new Token(TokenType.EQUAL_EQUAL, "==", null, 1),
                    new Expr.Literal(1.0)
                ),
                new Token(TokenType.COMMA, ",", null, 1),
                new Expr.Binary(
                    new Expr.Literal(2.0),
                    new Token(TokenType.EQUAL_EQUAL, "==", null, 1),
                    new Expr.Literal(2.0)
                )

            ),
            expression
        );
    }

    @Test
    public void testParseFail() {
        String source = "a =";

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        assertNull(expression);
    }
}
