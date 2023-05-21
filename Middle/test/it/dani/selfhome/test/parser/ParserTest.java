package it.dani.selfhome.test.parser;

import it.dani.selfhome.controller.Controller;
import it.dani.selfhome.listener.parser.DFLiteBaseVisitor;
import it.dani.selfhome.listener.parser.DFLiteLexer;
import it.dani.selfhome.listener.parser.DFLiteParser;
import it.dani.selfhome.test.controller.ControllerEmulator;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.Optional;

public class ParserTest {
    public static void main(String[] args)
    {
        try
        {
            CharStream charStream = CharStreams.fromStream(System.in);
            DFLiteLexer lexer = new DFLiteLexer(charStream);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            DFLiteParser parser = new DFLiteParser(tokenStream);
            ParseTree tree = parser.program();
            DFLiteBaseVisitor visitor = new DFLiteBaseVisitor(new Controller("10.0.0.106",4001));

            Optional<Object> result = visitor.visit(tree);
            result.ifPresent(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
