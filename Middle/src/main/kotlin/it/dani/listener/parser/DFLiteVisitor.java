package it.dani.listener.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DFLiteParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DFLiteVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code programStatement}
	 * labeled alternative in {@link DFLiteParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgramStatement(DFLiteParser.ProgramStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DFLiteParser#separator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeparator(DFLiteParser.SeparatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandGet}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandGet(DFLiteParser.CommandGetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandSet}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandSet(DFLiteParser.CommandSetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandLst}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandLst(DFLiteParser.CommandLstContext ctx);
}