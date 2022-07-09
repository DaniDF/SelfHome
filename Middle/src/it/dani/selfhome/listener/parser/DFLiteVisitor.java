// Generated from C:/Users/Daniele/Documents/MEGA/Progetti/SelfHome/New/SelfHome/Middle/grammar\DFLite.g4 by ANTLR 4.9.2
package it.dani.selfhome.listener.parser;
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
	 * Visit a parse tree produced by the {@code statementLineCommand}
	 * labeled alternative in {@link DFLiteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementLineCommand(DFLiteParser.StatementLineCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementRepeat}
	 * labeled alternative in {@link DFLiteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementRepeat(DFLiteParser.StatementRepeatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementIf}
	 * labeled alternative in {@link DFLiteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementIf(DFLiteParser.StatementIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStatementIf}
	 * labeled alternative in {@link DFLiteParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatementIf(DFLiteParser.IfStatementIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStatement}
	 * labeled alternative in {@link DFLiteParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(DFLiteParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterComp}
	 * labeled alternative in {@link DFLiteParser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterComp(DFLiteParser.FilterCompContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lineCom}
	 * labeled alternative in {@link DFLiteParser#lineCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineCom(DFLiteParser.LineComContext ctx);
	/**
	 * Visit a parse tree produced by {@link DFLiteParser#separator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeparator(DFLiteParser.SeparatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicOr}
	 * labeled alternative in {@link DFLiteParser#logicOperatorOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicOr(DFLiteParser.LogicOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicLoginAnd}
	 * labeled alternative in {@link DFLiteParser#logicOperatorOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicLoginAnd(DFLiteParser.LogicLoginAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicAnd}
	 * labeled alternative in {@link DFLiteParser#logicOperatorAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicAnd(DFLiteParser.LogicAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicComparator}
	 * labeled alternative in {@link DFLiteParser#logicOperatorAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicComparator(DFLiteParser.LogicComparatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparatorEE}
	 * labeled alternative in {@link DFLiteParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparatorEE(DFLiteParser.ComparatorEEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparatorSum}
	 * labeled alternative in {@link DFLiteParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparatorSum(DFLiteParser.ComparatorSumContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparatorTime}
	 * labeled alternative in {@link DFLiteParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparatorTime(DFLiteParser.ComparatorTimeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparatorGE}
	 * labeled alternative in {@link DFLiteParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparatorGE(DFLiteParser.ComparatorGEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparatorGT}
	 * labeled alternative in {@link DFLiteParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparatorGT(DFLiteParser.ComparatorGTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparatorLE}
	 * labeled alternative in {@link DFLiteParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparatorLE(DFLiteParser.ComparatorLEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparatorLT}
	 * labeled alternative in {@link DFLiteParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparatorLT(DFLiteParser.ComparatorLTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sumAdd}
	 * labeled alternative in {@link DFLiteParser#sum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumAdd(DFLiteParser.SumAddContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sumMinu}
	 * labeled alternative in {@link DFLiteParser#sum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumMinu(DFLiteParser.SumMinuContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sumMulDiv}
	 * labeled alternative in {@link DFLiteParser#sum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumMulDiv(DFLiteParser.SumMulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDivDiv}
	 * labeled alternative in {@link DFLiteParser#mulDiv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivDiv(DFLiteParser.MulDivDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDivMul}
	 * labeled alternative in {@link DFLiteParser#mulDiv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivMul(DFLiteParser.MulDivMulContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDivTerm}
	 * labeled alternative in {@link DFLiteParser#mulDiv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivTerm(DFLiteParser.MulDivTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termItem}
	 * labeled alternative in {@link DFLiteParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermItem(DFLiteParser.TermItemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termNotTerm}
	 * labeled alternative in {@link DFLiteParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermNotTerm(DFLiteParser.TermNotTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termBra}
	 * labeled alternative in {@link DFLiteParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermBra(DFLiteParser.TermBraContext ctx);
	/**
	 * Visit a parse tree produced by the {@code itemNumber}
	 * labeled alternative in {@link DFLiteParser#item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItemNumber(DFLiteParser.ItemNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code itemCommand}
	 * labeled alternative in {@link DFLiteParser#item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItemCommand(DFLiteParser.ItemCommandContext ctx);
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
	 * Visit a parse tree produced by the {@code commandToggle}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandToggle(DFLiteParser.CommandToggleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandNewGroup}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandNewGroup(DFLiteParser.CommandNewGroupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandDelGroup}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandDelGroup(DFLiteParser.CommandDelGroupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandAddDeviceToGroup}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandAddDeviceToGroup(DFLiteParser.CommandAddDeviceToGroupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandDelDeviceFromGroup}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandDelDeviceFromGroup(DFLiteParser.CommandDelDeviceFromGroupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandSleep}
	 * labeled alternative in {@link DFLiteParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandSleep(DFLiteParser.CommandSleepContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandList}
	 * labeled alternative in {@link DFLiteParser#listCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandList(DFLiteParser.CommandListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandListGroup}
	 * labeled alternative in {@link DFLiteParser#listCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandListGroup(DFLiteParser.CommandListGroupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commandListDeviceInGroup}
	 * labeled alternative in {@link DFLiteParser#listCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandListDeviceInGroup(DFLiteParser.CommandListDeviceInGroupContext ctx);
}