package it.dani.listener.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DFLiteParser}.
 */
public interface DFLiteListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code programStatement}
	 * labeled alternative in {@link DFLiteParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgramStatement(DFLiteParser.ProgramStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code programStatement}
	 * labeled alternative in {@link DFLiteParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgramStatement(DFLiteParser.ProgramStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DFLiteParser#separator}.
	 * @param ctx the parse tree
	 */
	void enterSeparator(DFLiteParser.SeparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DFLiteParser#separator}.
	 * @param ctx the parse tree
	 */
	void exitSeparator(DFLiteParser.SeparatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code commandGet}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommandGet(DFLiteParser.CommandGetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code commandGet}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommandGet(DFLiteParser.CommandGetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code commandSet}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommandSet(DFLiteParser.CommandSetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code commandSet}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommandSet(DFLiteParser.CommandSetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code commandLst}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommandLst(DFLiteParser.CommandLstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code commandLst}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommandLst(DFLiteParser.CommandLstContext ctx);
}