// Generated from C:/Users/Daniele/Documents/MEGA/Progetti/SelfHome/New/SelfHome/Middle/grammar\DFLite.g4 by ANTLR 4.9.2
package it.dani.selfhome.listener.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DFLiteParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		TIMESELECTOR=18, SEL=19, END_COMMAND=20, GRAPH_BRACKET_OP=21, GRAPH_BRACKET_CL=22, 
		ROUND_BRACKET_OP=23, ROUND_BRACKET_CL=24, NOT=25, SUM=26, SUB=27, MUL=28, 
		DIV=29, LOGIC_OR=30, LOGIC_AND=31, COMP_LT=32, COMP_GT=33, COMP_LE=34, 
		COMP_GE=35, COMP_EE=36, NUMBER=37, WORD=38, SPACE=39, NL=40, WS=41;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_ifStatement = 2, RULE_block = 3, 
		RULE_filter = 4, RULE_lineCommand = 5, RULE_separator = 6, RULE_logicOperatorOr = 7, 
		RULE_logicOperatorAnd = 8, RULE_comparator = 9, RULE_sum = 10, RULE_mulDiv = 11, 
		RULE_term = 12, RULE_item = 13, RULE_command = 14, RULE_listCommand = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "ifStatement", "block", "filter", "lineCommand", 
			"separator", "logicOperatorOr", "logicOperatorAnd", "comparator", "sum", 
			"mulDiv", "term", "item", "command", "listCommand"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'repeat'", "'if'", "'else'", "'isOneOfThese['", "','", "']'", 
			"'GET'", "'SET'", "'TGL'", "'NGR'", "'DGR'", "'ADG'", "'DDG'", "'sleep'", 
			"'LST'", "'LGR'", "'LDG'", null, null, "';'", "'{'", "'}'", "'('", "')'", 
			"'!'", "'+'", "'-'", "'*'", "'/'", "'||'", "'&&'", "'<'", "'>'", "'<='", 
			"'>='", "'=='", null, null, "' '", "'\n'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "TIMESELECTOR", "SEL", "END_COMMAND", 
			"GRAPH_BRACKET_OP", "GRAPH_BRACKET_CL", "ROUND_BRACKET_OP", "ROUND_BRACKET_CL", 
			"NOT", "SUM", "SUB", "MUL", "DIV", "LOGIC_OR", "LOGIC_AND", "COMP_LT", 
			"COMP_GT", "COMP_LE", "COMP_GE", "COMP_EE", "NUMBER", "WORD", "SPACE", 
			"NL", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "DFLite.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DFLiteParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	 
		public ProgramContext() { }
		public void copyFrom(ProgramContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ProgramStatementContext extends ProgramContext {
		public StatementContext s;
		public ListCommandContext l;
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<ListCommandContext> listCommand() {
			return getRuleContexts(ListCommandContext.class);
		}
		public ListCommandContext listCommand(int i) {
			return getRuleContext(ListCommandContext.class,i);
		}
		public List<SeparatorContext> separator() {
			return getRuleContexts(SeparatorContext.class);
		}
		public SeparatorContext separator(int i) {
			return getRuleContext(SeparatorContext.class,i);
		}
		public ProgramStatementContext(ProgramContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitProgramStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			_localctx = new ProgramStatementContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__3) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << ROUND_BRACKET_OP) | (1L << NOT) | (1L << NUMBER))) != 0)) {
				{
				{
				setState(34);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
				case T__1:
				case T__3:
				case T__6:
				case T__7:
				case T__8:
				case T__9:
				case T__10:
				case T__11:
				case T__12:
				case T__13:
				case ROUND_BRACKET_OP:
				case NOT:
				case NUMBER:
					{
					setState(32);
					((ProgramStatementContext)_localctx).s = statement();
					}
					break;
				case T__14:
				case T__15:
				case T__16:
					{
					setState(33);
					((ProgramStatementContext)_localctx).l = listCommand();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE || _la==NL) {
					{
					{
					setState(36);
					separator();
					}
					}
					setState(41);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(46);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StatementRepeatContext extends StatementContext {
		public FilterContext f;
		public BlockContext b;
		public TerminalNode ROUND_BRACKET_OP() { return getToken(DFLiteParser.ROUND_BRACKET_OP, 0); }
		public TerminalNode ROUND_BRACKET_CL() { return getToken(DFLiteParser.ROUND_BRACKET_CL, 0); }
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public List<SeparatorContext> separator() {
			return getRuleContexts(SeparatorContext.class);
		}
		public SeparatorContext separator(int i) {
			return getRuleContext(SeparatorContext.class,i);
		}
		public StatementRepeatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitStatementRepeat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StatementLineCommandContext extends StatementContext {
		public LineCommandContext l;
		public LineCommandContext lineCommand() {
			return getRuleContext(LineCommandContext.class,0);
		}
		public StatementLineCommandContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitStatementLineCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StatementIfContext extends StatementContext {
		public IfStatementContext i;
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public StatementIfContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitStatementIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		int _la;
		try {
			setState(79);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
			case T__6:
			case T__7:
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case ROUND_BRACKET_OP:
			case NOT:
			case NUMBER:
				_localctx = new StatementLineCommandContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(47);
				((StatementLineCommandContext)_localctx).l = lineCommand();
				}
				break;
			case T__0:
				_localctx = new StatementRepeatContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				match(T__0);
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE) {
					{
					{
					setState(49);
					match(SPACE);
					}
					}
					setState(54);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(55);
				match(ROUND_BRACKET_OP);
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE) {
					{
					{
					setState(56);
					match(SPACE);
					}
					}
					setState(61);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(62);
				((StatementRepeatContext)_localctx).f = filter();
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE) {
					{
					{
					setState(63);
					match(SPACE);
					}
					}
					setState(68);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(69);
				match(ROUND_BRACKET_CL);
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE || _la==NL) {
					{
					{
					setState(70);
					separator();
					}
					}
					setState(75);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(76);
				((StatementRepeatContext)_localctx).b = block();
				}
				break;
			case T__1:
				_localctx = new StatementIfContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(78);
				((StatementIfContext)_localctx).i = ifStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
	 
		public IfStatementContext() { }
		public void copyFrom(IfStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IfStatementIfContext extends IfStatementContext {
		public FilterContext f;
		public BlockContext b;
		public IfStatementContext i;
		public BlockContext b2;
		public TerminalNode ROUND_BRACKET_OP() { return getToken(DFLiteParser.ROUND_BRACKET_OP, 0); }
		public TerminalNode ROUND_BRACKET_CL() { return getToken(DFLiteParser.ROUND_BRACKET_CL, 0); }
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public List<SeparatorContext> separator() {
			return getRuleContexts(SeparatorContext.class);
		}
		public SeparatorContext separator(int i) {
			return getRuleContext(SeparatorContext.class,i);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public IfStatementIfContext(IfStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitIfStatementIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_ifStatement);
		int _la;
		try {
			_localctx = new IfStatementIfContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(T__1);
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SPACE) {
				{
				{
				setState(82);
				match(SPACE);
				}
				}
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(88);
			match(ROUND_BRACKET_OP);
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SPACE) {
				{
				{
				setState(89);
				match(SPACE);
				}
				}
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(95);
			((IfStatementIfContext)_localctx).f = filter();
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SPACE) {
				{
				{
				setState(96);
				match(SPACE);
				}
				}
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(102);
			match(ROUND_BRACKET_CL);
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SPACE || _la==NL) {
				{
				{
				setState(103);
				separator();
				}
				}
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(109);
			((IfStatementIfContext)_localctx).b = block();
			setState(127);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE || _la==NL) {
					{
					{
					setState(110);
					separator();
					}
					}
					setState(115);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(116);
				match(T__2);
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE || _la==NL) {
					{
					{
					setState(117);
					separator();
					}
					}
					setState(122);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(125);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__1:
					{
					setState(123);
					((IfStatementIfContext)_localctx).i = ifStatement();
					}
					break;
				case GRAPH_BRACKET_OP:
					{
					setState(124);
					((IfStatementIfContext)_localctx).b2 = block();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
	 
		public BlockContext() { }
		public void copyFrom(BlockContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BlockStatementContext extends BlockContext {
		public StatementContext sta;
		public TerminalNode GRAPH_BRACKET_OP() { return getToken(DFLiteParser.GRAPH_BRACKET_OP, 0); }
		public TerminalNode GRAPH_BRACKET_CL() { return getToken(DFLiteParser.GRAPH_BRACKET_CL, 0); }
		public List<SeparatorContext> separator() {
			return getRuleContexts(SeparatorContext.class);
		}
		public SeparatorContext separator(int i) {
			return getRuleContext(SeparatorContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockStatementContext(BlockContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitBlockStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_block);
		int _la;
		try {
			_localctx = new BlockStatementContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(GRAPH_BRACKET_OP);
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SPACE || _la==NL) {
				{
				{
				setState(130);
				separator();
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__3) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << ROUND_BRACKET_OP) | (1L << NOT) | (1L << NUMBER))) != 0)) {
				{
				{
				setState(136);
				((BlockStatementContext)_localctx).sta = statement();
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE || _la==NL) {
					{
					{
					setState(137);
					separator();
					}
					}
					setState(142);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(148);
			match(GRAPH_BRACKET_CL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FilterContext extends ParserRuleContext {
		public FilterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filter; }
	 
		public FilterContext() { }
		public void copyFrom(FilterContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FilterCompContext extends FilterContext {
		public LogicOperatorOrContext c;
		public LogicOperatorOrContext logicOperatorOr() {
			return getRuleContext(LogicOperatorOrContext.class,0);
		}
		public FilterCompContext(FilterContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitFilterComp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FilterContext filter() throws RecognitionException {
		FilterContext _localctx = new FilterContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_filter);
		try {
			_localctx = new FilterCompContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			((FilterCompContext)_localctx).c = logicOperatorOr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineCommandContext extends ParserRuleContext {
		public LineCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lineCommand; }
	 
		public LineCommandContext() { }
		public void copyFrom(LineCommandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LineComContext extends LineCommandContext {
		public LogicOperatorOrContext c;
		public TerminalNode END_COMMAND() { return getToken(DFLiteParser.END_COMMAND, 0); }
		public LogicOperatorOrContext logicOperatorOr() {
			return getRuleContext(LogicOperatorOrContext.class,0);
		}
		public LineComContext(LineCommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitLineCom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineCommandContext lineCommand() throws RecognitionException {
		LineCommandContext _localctx = new LineCommandContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_lineCommand);
		try {
			_localctx = new LineComContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			((LineComContext)_localctx).c = logicOperatorOr(0);
			setState(153);
			match(END_COMMAND);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SeparatorContext extends ParserRuleContext {
		public TerminalNode SPACE() { return getToken(DFLiteParser.SPACE, 0); }
		public TerminalNode NL() { return getToken(DFLiteParser.NL, 0); }
		public SeparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_separator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitSeparator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SeparatorContext separator() throws RecognitionException {
		SeparatorContext _localctx = new SeparatorContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_separator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			_la = _input.LA(1);
			if ( !(_la==SPACE || _la==NL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicOperatorOrContext extends ParserRuleContext {
		public LogicOperatorOrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicOperatorOr; }
	 
		public LogicOperatorOrContext() { }
		public void copyFrom(LogicOperatorOrContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LogicOrContext extends LogicOperatorOrContext {
		public LogicOperatorOrContext ls;
		public LogicOperatorAndContext ld;
		public TerminalNode LOGIC_OR() { return getToken(DFLiteParser.LOGIC_OR, 0); }
		public LogicOperatorOrContext logicOperatorOr() {
			return getRuleContext(LogicOperatorOrContext.class,0);
		}
		public LogicOperatorAndContext logicOperatorAnd() {
			return getRuleContext(LogicOperatorAndContext.class,0);
		}
		public LogicOrContext(LogicOperatorOrContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitLogicOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicLoginAndContext extends LogicOperatorOrContext {
		public LogicOperatorAndContext l;
		public LogicOperatorAndContext logicOperatorAnd() {
			return getRuleContext(LogicOperatorAndContext.class,0);
		}
		public LogicLoginAndContext(LogicOperatorOrContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitLogicLoginAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicOperatorOrContext logicOperatorOr() throws RecognitionException {
		return logicOperatorOr(0);
	}

	private LogicOperatorOrContext logicOperatorOr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicOperatorOrContext _localctx = new LogicOperatorOrContext(_ctx, _parentState);
		LogicOperatorOrContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_logicOperatorOr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new LogicLoginAndContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(158);
			((LogicLoginAndContext)_localctx).l = logicOperatorAnd(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(165);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LogicOrContext(new LogicOperatorOrContext(_parentctx, _parentState));
					((LogicOrContext)_localctx).ls = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_logicOperatorOr);
					setState(160);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(161);
					match(LOGIC_OR);
					setState(162);
					((LogicOrContext)_localctx).ld = logicOperatorAnd(0);
					}
					} 
				}
				setState(167);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LogicOperatorAndContext extends ParserRuleContext {
		public LogicOperatorAndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicOperatorAnd; }
	 
		public LogicOperatorAndContext() { }
		public void copyFrom(LogicOperatorAndContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LogicAndContext extends LogicOperatorAndContext {
		public LogicOperatorAndContext l;
		public ComparatorContext c;
		public TerminalNode LOGIC_AND() { return getToken(DFLiteParser.LOGIC_AND, 0); }
		public LogicOperatorAndContext logicOperatorAnd() {
			return getRuleContext(LogicOperatorAndContext.class,0);
		}
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public LogicAndContext(LogicOperatorAndContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitLogicAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicComparatorContext extends LogicOperatorAndContext {
		public ComparatorContext c;
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public LogicComparatorContext(LogicOperatorAndContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitLogicComparator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicOperatorAndContext logicOperatorAnd() throws RecognitionException {
		return logicOperatorAnd(0);
	}

	private LogicOperatorAndContext logicOperatorAnd(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicOperatorAndContext _localctx = new LogicOperatorAndContext(_ctx, _parentState);
		LogicOperatorAndContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_logicOperatorAnd, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new LogicComparatorContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(169);
			((LogicComparatorContext)_localctx).c = comparator(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(176);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LogicAndContext(new LogicOperatorAndContext(_parentctx, _parentState));
					((LogicAndContext)_localctx).l = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_logicOperatorAnd);
					setState(171);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(172);
					match(LOGIC_AND);
					setState(173);
					((LogicAndContext)_localctx).c = comparator(0);
					}
					} 
				}
				setState(178);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ComparatorContext extends ParserRuleContext {
		public ComparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparator; }
	 
		public ComparatorContext() { }
		public void copyFrom(ComparatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ComparatorEEContext extends ComparatorContext {
		public ComparatorContext c;
		public Token op;
		public SumContext s;
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public TerminalNode COMP_EE() { return getToken(DFLiteParser.COMP_EE, 0); }
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public ComparatorEEContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitComparatorEE(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ComparatorSumContext extends ComparatorContext {
		public SumContext s;
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public ComparatorSumContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitComparatorSum(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ComparatorTimeContext extends ComparatorContext {
		public Token sel;
		public Token val;
		public TerminalNode TIMESELECTOR() { return getToken(DFLiteParser.TIMESELECTOR, 0); }
		public TerminalNode NUMBER() { return getToken(DFLiteParser.NUMBER, 0); }
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public ComparatorTimeContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitComparatorTime(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ComparatorGEContext extends ComparatorContext {
		public ComparatorContext c;
		public Token op;
		public SumContext s;
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public TerminalNode COMP_GE() { return getToken(DFLiteParser.COMP_GE, 0); }
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public ComparatorGEContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitComparatorGE(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ComparatorGTContext extends ComparatorContext {
		public ComparatorContext c;
		public Token op;
		public SumContext s;
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public TerminalNode COMP_GT() { return getToken(DFLiteParser.COMP_GT, 0); }
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public ComparatorGTContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitComparatorGT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ComparatorLEContext extends ComparatorContext {
		public ComparatorContext c;
		public Token op;
		public SumContext s;
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public TerminalNode COMP_LE() { return getToken(DFLiteParser.COMP_LE, 0); }
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public ComparatorLEContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitComparatorLE(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ComparatorLTContext extends ComparatorContext {
		public ComparatorContext c;
		public Token op;
		public SumContext s;
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public TerminalNode COMP_LT() { return getToken(DFLiteParser.COMP_LT, 0); }
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public ComparatorLTContext(ComparatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitComparatorLT(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparatorContext comparator() throws RecognitionException {
		return comparator(0);
	}

	private ComparatorContext comparator(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ComparatorContext _localctx = new ComparatorContext(_ctx, _parentState);
		ComparatorContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_comparator, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__7:
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case ROUND_BRACKET_OP:
			case NOT:
			case NUMBER:
				{
				_localctx = new ComparatorSumContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(180);
				((ComparatorSumContext)_localctx).s = sum(0);
				}
				break;
			case T__3:
				{
				_localctx = new ComparatorTimeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(181);
				match(T__3);
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE) {
					{
					{
					setState(182);
					match(SPACE);
					}
					}
					setState(187);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(188);
				((ComparatorTimeContext)_localctx).sel = match(TIMESELECTOR);
				setState(192);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE) {
					{
					{
					setState(189);
					match(SPACE);
					}
					}
					setState(194);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(195);
				match(T__4);
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE) {
					{
					{
					setState(196);
					match(SPACE);
					}
					}
					setState(201);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(202);
				((ComparatorTimeContext)_localctx).val = match(NUMBER);
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE) {
					{
					{
					setState(203);
					match(SPACE);
					}
					}
					setState(208);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(209);
				match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(289);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(287);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
					case 1:
						{
						_localctx = new ComparatorLTContext(new ComparatorContext(_parentctx, _parentState));
						((ComparatorLTContext)_localctx).c = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_comparator);
						setState(212);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(216);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(213);
							match(SPACE);
							}
							}
							setState(218);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(219);
						((ComparatorLTContext)_localctx).op = match(COMP_LT);
						setState(223);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(220);
							match(SPACE);
							}
							}
							setState(225);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(226);
						((ComparatorLTContext)_localctx).s = sum(0);
						}
						break;
					case 2:
						{
						_localctx = new ComparatorGTContext(new ComparatorContext(_parentctx, _parentState));
						((ComparatorGTContext)_localctx).c = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_comparator);
						setState(227);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(231);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(228);
							match(SPACE);
							}
							}
							setState(233);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(234);
						((ComparatorGTContext)_localctx).op = match(COMP_GT);
						setState(238);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(235);
							match(SPACE);
							}
							}
							setState(240);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(241);
						((ComparatorGTContext)_localctx).s = sum(0);
						}
						break;
					case 3:
						{
						_localctx = new ComparatorLEContext(new ComparatorContext(_parentctx, _parentState));
						((ComparatorLEContext)_localctx).c = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_comparator);
						setState(242);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(246);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(243);
							match(SPACE);
							}
							}
							setState(248);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(249);
						((ComparatorLEContext)_localctx).op = match(COMP_LE);
						setState(253);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(250);
							match(SPACE);
							}
							}
							setState(255);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(256);
						((ComparatorLEContext)_localctx).s = sum(0);
						}
						break;
					case 4:
						{
						_localctx = new ComparatorGEContext(new ComparatorContext(_parentctx, _parentState));
						((ComparatorGEContext)_localctx).c = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_comparator);
						setState(257);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(261);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(258);
							match(SPACE);
							}
							}
							setState(263);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(264);
						((ComparatorGEContext)_localctx).op = match(COMP_GE);
						setState(268);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(265);
							match(SPACE);
							}
							}
							setState(270);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(271);
						((ComparatorGEContext)_localctx).s = sum(0);
						}
						break;
					case 5:
						{
						_localctx = new ComparatorEEContext(new ComparatorContext(_parentctx, _parentState));
						((ComparatorEEContext)_localctx).c = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_comparator);
						setState(272);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(276);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(273);
							match(SPACE);
							}
							}
							setState(278);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(279);
						((ComparatorEEContext)_localctx).op = match(COMP_EE);
						setState(283);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(280);
							match(SPACE);
							}
							}
							setState(285);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(286);
						((ComparatorEEContext)_localctx).s = sum(0);
						}
						break;
					}
					} 
				}
				setState(291);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class SumContext extends ParserRuleContext {
		public SumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sum; }
	 
		public SumContext() { }
		public void copyFrom(SumContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SumAddContext extends SumContext {
		public SumContext f;
		public Token op;
		public MulDivContext s;
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public TerminalNode SUM() { return getToken(DFLiteParser.SUM, 0); }
		public MulDivContext mulDiv() {
			return getRuleContext(MulDivContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public SumAddContext(SumContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitSumAdd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SumMinuContext extends SumContext {
		public SumContext f;
		public Token op;
		public MulDivContext s;
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public TerminalNode SUB() { return getToken(DFLiteParser.SUB, 0); }
		public MulDivContext mulDiv() {
			return getRuleContext(MulDivContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public SumMinuContext(SumContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitSumMinu(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SumMulDivContext extends SumContext {
		public MulDivContext a;
		public MulDivContext mulDiv() {
			return getRuleContext(MulDivContext.class,0);
		}
		public SumMulDivContext(SumContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitSumMulDiv(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SumContext sum() throws RecognitionException {
		return sum(0);
	}

	private SumContext sum(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SumContext _localctx = new SumContext(_ctx, _parentState);
		SumContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_sum, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new SumMulDivContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(293);
			((SumMulDivContext)_localctx).a = mulDiv(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(327);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(325);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
					case 1:
						{
						_localctx = new SumAddContext(new SumContext(_parentctx, _parentState));
						((SumAddContext)_localctx).f = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_sum);
						setState(295);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(299);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(296);
							match(SPACE);
							}
							}
							setState(301);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(302);
						((SumAddContext)_localctx).op = match(SUM);
						setState(306);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(303);
							match(SPACE);
							}
							}
							setState(308);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(309);
						((SumAddContext)_localctx).s = mulDiv(0);
						}
						break;
					case 2:
						{
						_localctx = new SumMinuContext(new SumContext(_parentctx, _parentState));
						((SumMinuContext)_localctx).f = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_sum);
						setState(310);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(314);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(311);
							match(SPACE);
							}
							}
							setState(316);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(317);
						((SumMinuContext)_localctx).op = match(SUB);
						setState(321);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(318);
							match(SPACE);
							}
							}
							setState(323);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(324);
						((SumMinuContext)_localctx).s = mulDiv(0);
						}
						break;
					}
					} 
				}
				setState(329);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class MulDivContext extends ParserRuleContext {
		public MulDivContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mulDiv; }
	 
		public MulDivContext() { }
		public void copyFrom(MulDivContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MulDivDivContext extends MulDivContext {
		public MulDivContext f;
		public Token op;
		public TermContext s;
		public MulDivContext mulDiv() {
			return getRuleContext(MulDivContext.class,0);
		}
		public TerminalNode DIV() { return getToken(DFLiteParser.DIV, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public MulDivDivContext(MulDivContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitMulDivDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MulDivMulContext extends MulDivContext {
		public MulDivContext f;
		public Token op;
		public TermContext s;
		public MulDivContext mulDiv() {
			return getRuleContext(MulDivContext.class,0);
		}
		public TerminalNode MUL() { return getToken(DFLiteParser.MUL, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public MulDivMulContext(MulDivContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitMulDivMul(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MulDivTermContext extends MulDivContext {
		public TermContext t;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public MulDivTermContext(MulDivContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitMulDivTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MulDivContext mulDiv() throws RecognitionException {
		return mulDiv(0);
	}

	private MulDivContext mulDiv(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MulDivContext _localctx = new MulDivContext(_ctx, _parentState);
		MulDivContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_mulDiv, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new MulDivTermContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(331);
			((MulDivTermContext)_localctx).t = term();
			}
			_ctx.stop = _input.LT(-1);
			setState(365);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(363);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
					case 1:
						{
						_localctx = new MulDivMulContext(new MulDivContext(_parentctx, _parentState));
						((MulDivMulContext)_localctx).f = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_mulDiv);
						setState(333);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(337);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(334);
							match(SPACE);
							}
							}
							setState(339);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(340);
						((MulDivMulContext)_localctx).op = match(MUL);
						setState(344);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(341);
							match(SPACE);
							}
							}
							setState(346);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(347);
						((MulDivMulContext)_localctx).s = term();
						}
						break;
					case 2:
						{
						_localctx = new MulDivDivContext(new MulDivContext(_parentctx, _parentState));
						((MulDivDivContext)_localctx).f = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_mulDiv);
						setState(348);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(352);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(349);
							match(SPACE);
							}
							}
							setState(354);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(355);
						((MulDivDivContext)_localctx).op = match(DIV);
						setState(359);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SPACE) {
							{
							{
							setState(356);
							match(SPACE);
							}
							}
							setState(361);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(362);
						((MulDivDivContext)_localctx).s = term();
						}
						break;
					}
					} 
				}
				setState(367);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	 
		public TermContext() { }
		public void copyFrom(TermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TermBraContext extends TermContext {
		public LogicOperatorOrContext ex;
		public TerminalNode ROUND_BRACKET_OP() { return getToken(DFLiteParser.ROUND_BRACKET_OP, 0); }
		public TerminalNode ROUND_BRACKET_CL() { return getToken(DFLiteParser.ROUND_BRACKET_CL, 0); }
		public LogicOperatorOrContext logicOperatorOr() {
			return getRuleContext(LogicOperatorOrContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public TermBraContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitTermBra(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TermItemContext extends TermContext {
		public ItemContext i;
		public ItemContext item() {
			return getRuleContext(ItemContext.class,0);
		}
		public TermItemContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitTermItem(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TermNotTermContext extends TermContext {
		public TermContext i;
		public TerminalNode NOT() { return getToken(DFLiteParser.NOT, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TermNotTermContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitTermNotTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_term);
		int _la;
		try {
			setState(387);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__7:
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case NUMBER:
				_localctx = new TermItemContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(368);
				((TermItemContext)_localctx).i = item();
				}
				break;
			case NOT:
				_localctx = new TermNotTermContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(369);
				match(NOT);
				setState(370);
				((TermNotTermContext)_localctx).i = term();
				}
				break;
			case ROUND_BRACKET_OP:
				_localctx = new TermBraContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(371);
				match(ROUND_BRACKET_OP);
				setState(375);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE) {
					{
					{
					setState(372);
					match(SPACE);
					}
					}
					setState(377);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(378);
				((TermBraContext)_localctx).ex = logicOperatorOr(0);
				setState(382);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE) {
					{
					{
					setState(379);
					match(SPACE);
					}
					}
					setState(384);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(385);
				match(ROUND_BRACKET_CL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ItemContext extends ParserRuleContext {
		public ItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_item; }
	 
		public ItemContext() { }
		public void copyFrom(ItemContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ItemNumberContext extends ItemContext {
		public Token i;
		public TerminalNode NUMBER() { return getToken(DFLiteParser.NUMBER, 0); }
		public ItemNumberContext(ItemContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitItemNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ItemCommandContext extends ItemContext {
		public CommandContext c;
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public ItemCommandContext(ItemContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitItemCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ItemContext item() throws RecognitionException {
		ItemContext _localctx = new ItemContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_item);
		try {
			setState(391);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMBER:
				_localctx = new ItemNumberContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(389);
				((ItemNumberContext)_localctx).i = match(NUMBER);
				}
				break;
			case T__6:
			case T__7:
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
				_localctx = new ItemCommandContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(390);
				((ItemCommandContext)_localctx).c = command();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
	 
		public CommandContext() { }
		public void copyFrom(CommandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CommandSleepContext extends CommandContext {
		public TermContext value;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandSleepContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandSleep(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommandAddDeviceToGroupContext extends CommandContext {
		public Token nameD;
		public Token nameG;
		public List<TerminalNode> WORD() { return getTokens(DFLiteParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(DFLiteParser.WORD, i);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandAddDeviceToGroupContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandAddDeviceToGroup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommandToggleContext extends CommandContext {
		public Token sel;
		public Token name;
		public TerminalNode SEL() { return getToken(DFLiteParser.SEL, 0); }
		public TerminalNode WORD() { return getToken(DFLiteParser.WORD, 0); }
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandToggleContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandToggle(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommandGetContext extends CommandContext {
		public Token sel;
		public Token name;
		public TerminalNode SEL() { return getToken(DFLiteParser.SEL, 0); }
		public TerminalNode WORD() { return getToken(DFLiteParser.WORD, 0); }
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandGetContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandGet(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommandDelDeviceFromGroupContext extends CommandContext {
		public Token nameD;
		public Token nameG;
		public List<TerminalNode> WORD() { return getTokens(DFLiteParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(DFLiteParser.WORD, i);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandDelDeviceFromGroupContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandDelDeviceFromGroup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommandNewGroupContext extends CommandContext {
		public Token name;
		public TerminalNode WORD() { return getToken(DFLiteParser.WORD, 0); }
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandNewGroupContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandNewGroup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommandSetContext extends CommandContext {
		public Token sel;
		public Token name;
		public TermContext value;
		public TerminalNode SEL() { return getToken(DFLiteParser.SEL, 0); }
		public TerminalNode WORD() { return getToken(DFLiteParser.WORD, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandSetContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandSet(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommandDelGroupContext extends CommandContext {
		public Token name;
		public TerminalNode WORD() { return getToken(DFLiteParser.WORD, 0); }
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandDelGroupContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandDelGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_command);
		int _la;
		try {
			setState(485);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				_localctx = new CommandGetContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(393);
				match(T__6);
				setState(395); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(394);
					match(SPACE);
					}
					}
					setState(397); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(399);
				((CommandGetContext)_localctx).sel = match(SEL);
				setState(401); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(400);
					match(SPACE);
					}
					}
					setState(403); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(405);
				((CommandGetContext)_localctx).name = match(WORD);
				}
				break;
			case T__7:
				_localctx = new CommandSetContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(406);
				match(T__7);
				setState(408); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(407);
					match(SPACE);
					}
					}
					setState(410); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(412);
				((CommandSetContext)_localctx).sel = match(SEL);
				setState(414); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(413);
					match(SPACE);
					}
					}
					setState(416); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(418);
				((CommandSetContext)_localctx).name = match(WORD);
				setState(420); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(419);
					match(SPACE);
					}
					}
					setState(422); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(424);
				((CommandSetContext)_localctx).value = term();
				}
				break;
			case T__8:
				_localctx = new CommandToggleContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(425);
				match(T__8);
				setState(427); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(426);
					match(SPACE);
					}
					}
					setState(429); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(431);
				((CommandToggleContext)_localctx).sel = match(SEL);
				setState(433); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(432);
					match(SPACE);
					}
					}
					setState(435); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(437);
				((CommandToggleContext)_localctx).name = match(WORD);
				}
				break;
			case T__9:
				_localctx = new CommandNewGroupContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(438);
				match(T__9);
				setState(440); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(439);
					match(SPACE);
					}
					}
					setState(442); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(444);
				((CommandNewGroupContext)_localctx).name = match(WORD);
				}
				break;
			case T__10:
				_localctx = new CommandDelGroupContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(445);
				match(T__10);
				setState(447); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(446);
					match(SPACE);
					}
					}
					setState(449); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(451);
				((CommandDelGroupContext)_localctx).name = match(WORD);
				}
				break;
			case T__11:
				_localctx = new CommandAddDeviceToGroupContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(452);
				match(T__11);
				setState(454); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(453);
					match(SPACE);
					}
					}
					setState(456); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(458);
				((CommandAddDeviceToGroupContext)_localctx).nameD = match(WORD);
				setState(460); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(459);
					match(SPACE);
					}
					}
					setState(462); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(464);
				((CommandAddDeviceToGroupContext)_localctx).nameG = match(WORD);
				}
				break;
			case T__12:
				_localctx = new CommandDelDeviceFromGroupContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(465);
				match(T__12);
				setState(467); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(466);
					match(SPACE);
					}
					}
					setState(469); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(471);
				((CommandDelDeviceFromGroupContext)_localctx).nameD = match(WORD);
				setState(473); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(472);
					match(SPACE);
					}
					}
					setState(475); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(477);
				((CommandDelDeviceFromGroupContext)_localctx).nameG = match(WORD);
				}
				break;
			case T__13:
				_localctx = new CommandSleepContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(478);
				match(T__13);
				setState(480); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(479);
					match(SPACE);
					}
					}
					setState(482); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(484);
				((CommandSleepContext)_localctx).value = term();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListCommandContext extends ParserRuleContext {
		public ListCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listCommand; }
	 
		public ListCommandContext() { }
		public void copyFrom(ListCommandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CommandListDeviceInGroupContext extends ListCommandContext {
		public Token name;
		public TerminalNode WORD() { return getToken(DFLiteParser.WORD, 0); }
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandListDeviceInGroupContext(ListCommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandListDeviceInGroup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommandListGroupContext extends ListCommandContext {
		public CommandListGroupContext(ListCommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandListGroup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommandListContext extends ListCommandContext {
		public CommandListContext(ListCommandContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListCommandContext listCommand() throws RecognitionException {
		ListCommandContext _localctx = new ListCommandContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_listCommand);
		int _la;
		try {
			setState(496);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
				_localctx = new CommandListContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(487);
				match(T__14);
				}
				break;
			case T__15:
				_localctx = new CommandListGroupContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(488);
				match(T__15);
				}
				break;
			case T__16:
				_localctx = new CommandListDeviceInGroupContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(489);
				match(T__16);
				setState(491); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(490);
					match(SPACE);
					}
					}
					setState(493); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(495);
				((CommandListDeviceInGroupContext)_localctx).name = match(WORD);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return logicOperatorOr_sempred((LogicOperatorOrContext)_localctx, predIndex);
		case 8:
			return logicOperatorAnd_sempred((LogicOperatorAndContext)_localctx, predIndex);
		case 9:
			return comparator_sempred((ComparatorContext)_localctx, predIndex);
		case 10:
			return sum_sempred((SumContext)_localctx, predIndex);
		case 11:
			return mulDiv_sempred((MulDivContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean logicOperatorOr_sempred(LogicOperatorOrContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean logicOperatorAnd_sempred(LogicOperatorAndContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean comparator_sempred(ComparatorContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 3);
		case 6:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean sum_sempred(SumContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return precpred(_ctx, 2);
		case 8:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean mulDiv_sempred(MulDivContext _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 2);
		case 10:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3+\u01f5\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\5"+
		"\2%\n\2\3\2\7\2(\n\2\f\2\16\2+\13\2\7\2-\n\2\f\2\16\2\60\13\2\3\3\3\3"+
		"\3\3\7\3\65\n\3\f\3\16\38\13\3\3\3\3\3\7\3<\n\3\f\3\16\3?\13\3\3\3\3\3"+
		"\7\3C\n\3\f\3\16\3F\13\3\3\3\3\3\7\3J\n\3\f\3\16\3M\13\3\3\3\3\3\3\3\5"+
		"\3R\n\3\3\4\3\4\7\4V\n\4\f\4\16\4Y\13\4\3\4\3\4\7\4]\n\4\f\4\16\4`\13"+
		"\4\3\4\3\4\7\4d\n\4\f\4\16\4g\13\4\3\4\3\4\7\4k\n\4\f\4\16\4n\13\4\3\4"+
		"\3\4\7\4r\n\4\f\4\16\4u\13\4\3\4\3\4\7\4y\n\4\f\4\16\4|\13\4\3\4\3\4\5"+
		"\4\u0080\n\4\5\4\u0082\n\4\3\5\3\5\7\5\u0086\n\5\f\5\16\5\u0089\13\5\3"+
		"\5\3\5\7\5\u008d\n\5\f\5\16\5\u0090\13\5\7\5\u0092\n\5\f\5\16\5\u0095"+
		"\13\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\7\t"+
		"\u00a6\n\t\f\t\16\t\u00a9\13\t\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u00b1\n\n\f"+
		"\n\16\n\u00b4\13\n\3\13\3\13\3\13\3\13\7\13\u00ba\n\13\f\13\16\13\u00bd"+
		"\13\13\3\13\3\13\7\13\u00c1\n\13\f\13\16\13\u00c4\13\13\3\13\3\13\7\13"+
		"\u00c8\n\13\f\13\16\13\u00cb\13\13\3\13\3\13\7\13\u00cf\n\13\f\13\16\13"+
		"\u00d2\13\13\3\13\5\13\u00d5\n\13\3\13\3\13\7\13\u00d9\n\13\f\13\16\13"+
		"\u00dc\13\13\3\13\3\13\7\13\u00e0\n\13\f\13\16\13\u00e3\13\13\3\13\3\13"+
		"\3\13\7\13\u00e8\n\13\f\13\16\13\u00eb\13\13\3\13\3\13\7\13\u00ef\n\13"+
		"\f\13\16\13\u00f2\13\13\3\13\3\13\3\13\7\13\u00f7\n\13\f\13\16\13\u00fa"+
		"\13\13\3\13\3\13\7\13\u00fe\n\13\f\13\16\13\u0101\13\13\3\13\3\13\3\13"+
		"\7\13\u0106\n\13\f\13\16\13\u0109\13\13\3\13\3\13\7\13\u010d\n\13\f\13"+
		"\16\13\u0110\13\13\3\13\3\13\3\13\7\13\u0115\n\13\f\13\16\13\u0118\13"+
		"\13\3\13\3\13\7\13\u011c\n\13\f\13\16\13\u011f\13\13\3\13\7\13\u0122\n"+
		"\13\f\13\16\13\u0125\13\13\3\f\3\f\3\f\3\f\3\f\7\f\u012c\n\f\f\f\16\f"+
		"\u012f\13\f\3\f\3\f\7\f\u0133\n\f\f\f\16\f\u0136\13\f\3\f\3\f\3\f\7\f"+
		"\u013b\n\f\f\f\16\f\u013e\13\f\3\f\3\f\7\f\u0142\n\f\f\f\16\f\u0145\13"+
		"\f\3\f\7\f\u0148\n\f\f\f\16\f\u014b\13\f\3\r\3\r\3\r\3\r\3\r\7\r\u0152"+
		"\n\r\f\r\16\r\u0155\13\r\3\r\3\r\7\r\u0159\n\r\f\r\16\r\u015c\13\r\3\r"+
		"\3\r\3\r\7\r\u0161\n\r\f\r\16\r\u0164\13\r\3\r\3\r\7\r\u0168\n\r\f\r\16"+
		"\r\u016b\13\r\3\r\7\r\u016e\n\r\f\r\16\r\u0171\13\r\3\16\3\16\3\16\3\16"+
		"\3\16\7\16\u0178\n\16\f\16\16\16\u017b\13\16\3\16\3\16\7\16\u017f\n\16"+
		"\f\16\16\16\u0182\13\16\3\16\3\16\5\16\u0186\n\16\3\17\3\17\5\17\u018a"+
		"\n\17\3\20\3\20\6\20\u018e\n\20\r\20\16\20\u018f\3\20\3\20\6\20\u0194"+
		"\n\20\r\20\16\20\u0195\3\20\3\20\3\20\6\20\u019b\n\20\r\20\16\20\u019c"+
		"\3\20\3\20\6\20\u01a1\n\20\r\20\16\20\u01a2\3\20\3\20\6\20\u01a7\n\20"+
		"\r\20\16\20\u01a8\3\20\3\20\3\20\6\20\u01ae\n\20\r\20\16\20\u01af\3\20"+
		"\3\20\6\20\u01b4\n\20\r\20\16\20\u01b5\3\20\3\20\3\20\6\20\u01bb\n\20"+
		"\r\20\16\20\u01bc\3\20\3\20\3\20\6\20\u01c2\n\20\r\20\16\20\u01c3\3\20"+
		"\3\20\3\20\6\20\u01c9\n\20\r\20\16\20\u01ca\3\20\3\20\6\20\u01cf\n\20"+
		"\r\20\16\20\u01d0\3\20\3\20\3\20\6\20\u01d6\n\20\r\20\16\20\u01d7\3\20"+
		"\3\20\6\20\u01dc\n\20\r\20\16\20\u01dd\3\20\3\20\3\20\6\20\u01e3\n\20"+
		"\r\20\16\20\u01e4\3\20\5\20\u01e8\n\20\3\21\3\21\3\21\3\21\6\21\u01ee"+
		"\n\21\r\21\16\21\u01ef\3\21\5\21\u01f3\n\21\3\21\2\7\20\22\24\26\30\22"+
		"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\3\3\2)*\2\u0237\2.\3\2\2\2"+
		"\4Q\3\2\2\2\6S\3\2\2\2\b\u0083\3\2\2\2\n\u0098\3\2\2\2\f\u009a\3\2\2\2"+
		"\16\u009d\3\2\2\2\20\u009f\3\2\2\2\22\u00aa\3\2\2\2\24\u00d4\3\2\2\2\26"+
		"\u0126\3\2\2\2\30\u014c\3\2\2\2\32\u0185\3\2\2\2\34\u0189\3\2\2\2\36\u01e7"+
		"\3\2\2\2 \u01f2\3\2\2\2\"%\5\4\3\2#%\5 \21\2$\"\3\2\2\2$#\3\2\2\2%)\3"+
		"\2\2\2&(\5\16\b\2\'&\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*-\3\2\2\2"+
		"+)\3\2\2\2,$\3\2\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2/\3\3\2\2\2\60.\3"+
		"\2\2\2\61R\5\f\7\2\62\66\7\3\2\2\63\65\7)\2\2\64\63\3\2\2\2\658\3\2\2"+
		"\2\66\64\3\2\2\2\66\67\3\2\2\2\679\3\2\2\28\66\3\2\2\29=\7\31\2\2:<\7"+
		")\2\2;:\3\2\2\2<?\3\2\2\2=;\3\2\2\2=>\3\2\2\2>@\3\2\2\2?=\3\2\2\2@D\5"+
		"\n\6\2AC\7)\2\2BA\3\2\2\2CF\3\2\2\2DB\3\2\2\2DE\3\2\2\2EG\3\2\2\2FD\3"+
		"\2\2\2GK\7\32\2\2HJ\5\16\b\2IH\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL\3\2\2\2L"+
		"N\3\2\2\2MK\3\2\2\2NO\5\b\5\2OR\3\2\2\2PR\5\6\4\2Q\61\3\2\2\2Q\62\3\2"+
		"\2\2QP\3\2\2\2R\5\3\2\2\2SW\7\4\2\2TV\7)\2\2UT\3\2\2\2VY\3\2\2\2WU\3\2"+
		"\2\2WX\3\2\2\2XZ\3\2\2\2YW\3\2\2\2Z^\7\31\2\2[]\7)\2\2\\[\3\2\2\2]`\3"+
		"\2\2\2^\\\3\2\2\2^_\3\2\2\2_a\3\2\2\2`^\3\2\2\2ae\5\n\6\2bd\7)\2\2cb\3"+
		"\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2fh\3\2\2\2ge\3\2\2\2hl\7\32\2\2ik"+
		"\5\16\b\2ji\3\2\2\2kn\3\2\2\2lj\3\2\2\2lm\3\2\2\2mo\3\2\2\2nl\3\2\2\2"+
		"o\u0081\5\b\5\2pr\5\16\b\2qp\3\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2\2\2tv\3"+
		"\2\2\2us\3\2\2\2vz\7\5\2\2wy\5\16\b\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{"+
		"\3\2\2\2{\177\3\2\2\2|z\3\2\2\2}\u0080\5\6\4\2~\u0080\5\b\5\2\177}\3\2"+
		"\2\2\177~\3\2\2\2\u0080\u0082\3\2\2\2\u0081s\3\2\2\2\u0081\u0082\3\2\2"+
		"\2\u0082\7\3\2\2\2\u0083\u0087\7\27\2\2\u0084\u0086\5\16\b\2\u0085\u0084"+
		"\3\2\2\2\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088"+
		"\u0093\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008e\5\4\3\2\u008b\u008d\5\16"+
		"\b\2\u008c\u008b\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e"+
		"\u008f\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u008a\3\2"+
		"\2\2\u0092\u0095\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094"+
		"\u0096\3\2\2\2\u0095\u0093\3\2\2\2\u0096\u0097\7\30\2\2\u0097\t\3\2\2"+
		"\2\u0098\u0099\5\20\t\2\u0099\13\3\2\2\2\u009a\u009b\5\20\t\2\u009b\u009c"+
		"\7\26\2\2\u009c\r\3\2\2\2\u009d\u009e\t\2\2\2\u009e\17\3\2\2\2\u009f\u00a0"+
		"\b\t\1\2\u00a0\u00a1\5\22\n\2\u00a1\u00a7\3\2\2\2\u00a2\u00a3\f\3\2\2"+
		"\u00a3\u00a4\7 \2\2\u00a4\u00a6\5\22\n\2\u00a5\u00a2\3\2\2\2\u00a6\u00a9"+
		"\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\21\3\2\2\2\u00a9"+
		"\u00a7\3\2\2\2\u00aa\u00ab\b\n\1\2\u00ab\u00ac\5\24\13\2\u00ac\u00b2\3"+
		"\2\2\2\u00ad\u00ae\f\3\2\2\u00ae\u00af\7!\2\2\u00af\u00b1\5\24\13\2\u00b0"+
		"\u00ad\3\2\2\2\u00b1\u00b4\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2"+
		"\2\2\u00b3\23\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5\u00b6\b\13\1\2\u00b6\u00d5"+
		"\5\26\f\2\u00b7\u00bb\7\6\2\2\u00b8\u00ba\7)\2\2\u00b9\u00b8\3\2\2\2\u00ba"+
		"\u00bd\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00be\3\2"+
		"\2\2\u00bd\u00bb\3\2\2\2\u00be\u00c2\7\24\2\2\u00bf\u00c1\7)\2\2\u00c0"+
		"\u00bf\3\2\2\2\u00c1\u00c4\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2"+
		"\2\2\u00c3\u00c5\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c5\u00c9\7\7\2\2\u00c6"+
		"\u00c8\7)\2\2\u00c7\u00c6\3\2\2\2\u00c8\u00cb\3\2\2\2\u00c9\u00c7\3\2"+
		"\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cc\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cc"+
		"\u00d0\7\'\2\2\u00cd\u00cf\7)\2\2\u00ce\u00cd\3\2\2\2\u00cf\u00d2\3\2"+
		"\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2\2\2\u00d2"+
		"\u00d0\3\2\2\2\u00d3\u00d5\7\b\2\2\u00d4\u00b5\3\2\2\2\u00d4\u00b7\3\2"+
		"\2\2\u00d5\u0123\3\2\2\2\u00d6\u00da\f\b\2\2\u00d7\u00d9\7)\2\2\u00d8"+
		"\u00d7\3\2\2\2\u00d9\u00dc\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2"+
		"\2\2\u00db\u00dd\3\2\2\2\u00dc\u00da\3\2\2\2\u00dd\u00e1\7\"\2\2\u00de"+
		"\u00e0\7)\2\2\u00df\u00de\3\2\2\2\u00e0\u00e3\3\2\2\2\u00e1\u00df\3\2"+
		"\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e4\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4"+
		"\u0122\5\26\f\2\u00e5\u00e9\f\7\2\2\u00e6\u00e8\7)\2\2\u00e7\u00e6\3\2"+
		"\2\2\u00e8\u00eb\3\2\2\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea"+
		"\u00ec\3\2\2\2\u00eb\u00e9\3\2\2\2\u00ec\u00f0\7#\2\2\u00ed\u00ef\7)\2"+
		"\2\u00ee\u00ed\3\2\2\2\u00ef\u00f2\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f0\u00f1"+
		"\3\2\2\2\u00f1\u00f3\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f3\u0122\5\26\f\2"+
		"\u00f4\u00f8\f\6\2\2\u00f5\u00f7\7)\2\2\u00f6\u00f5\3\2\2\2\u00f7\u00fa"+
		"\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fb\3\2\2\2\u00fa"+
		"\u00f8\3\2\2\2\u00fb\u00ff\7$\2\2\u00fc\u00fe\7)\2\2\u00fd\u00fc\3\2\2"+
		"\2\u00fe\u0101\3\2\2\2\u00ff\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0102"+
		"\3\2\2\2\u0101\u00ff\3\2\2\2\u0102\u0122\5\26\f\2\u0103\u0107\f\5\2\2"+
		"\u0104\u0106\7)\2\2\u0105\u0104\3\2\2\2\u0106\u0109\3\2\2\2\u0107\u0105"+
		"\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u010a\3\2\2\2\u0109\u0107\3\2\2\2\u010a"+
		"\u010e\7%\2\2\u010b\u010d\7)\2\2\u010c\u010b\3\2\2\2\u010d\u0110\3\2\2"+
		"\2\u010e\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0111\3\2\2\2\u0110\u010e"+
		"\3\2\2\2\u0111\u0122\5\26\f\2\u0112\u0116\f\4\2\2\u0113\u0115\7)\2\2\u0114"+
		"\u0113\3\2\2\2\u0115\u0118\3\2\2\2\u0116\u0114\3\2\2\2\u0116\u0117\3\2"+
		"\2\2\u0117\u0119\3\2\2\2\u0118\u0116\3\2\2\2\u0119\u011d\7&\2\2\u011a"+
		"\u011c\7)\2\2\u011b\u011a\3\2\2\2\u011c\u011f\3\2\2\2\u011d\u011b\3\2"+
		"\2\2\u011d\u011e\3\2\2\2\u011e\u0120\3\2\2\2\u011f\u011d\3\2\2\2\u0120"+
		"\u0122\5\26\f\2\u0121\u00d6\3\2\2\2\u0121\u00e5\3\2\2\2\u0121\u00f4\3"+
		"\2\2\2\u0121\u0103\3\2\2\2\u0121\u0112\3\2\2\2\u0122\u0125\3\2\2\2\u0123"+
		"\u0121\3\2\2\2\u0123\u0124\3\2\2\2\u0124\25\3\2\2\2\u0125\u0123\3\2\2"+
		"\2\u0126\u0127\b\f\1\2\u0127\u0128\5\30\r\2\u0128\u0149\3\2\2\2\u0129"+
		"\u012d\f\4\2\2\u012a\u012c\7)\2\2\u012b\u012a\3\2\2\2\u012c\u012f\3\2"+
		"\2\2\u012d\u012b\3\2\2\2\u012d\u012e\3\2\2\2\u012e\u0130\3\2\2\2\u012f"+
		"\u012d\3\2\2\2\u0130\u0134\7\34\2\2\u0131\u0133\7)\2\2\u0132\u0131\3\2"+
		"\2\2\u0133\u0136\3\2\2\2\u0134\u0132\3\2\2\2\u0134\u0135\3\2\2\2\u0135"+
		"\u0137\3\2\2\2\u0136\u0134\3\2\2\2\u0137\u0148\5\30\r\2\u0138\u013c\f"+
		"\3\2\2\u0139\u013b\7)\2\2\u013a\u0139\3\2\2\2\u013b\u013e\3\2\2\2\u013c"+
		"\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d\u013f\3\2\2\2\u013e\u013c\3\2"+
		"\2\2\u013f\u0143\7\35\2\2\u0140\u0142\7)\2\2\u0141\u0140\3\2\2\2\u0142"+
		"\u0145\3\2\2\2\u0143\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0146\3\2"+
		"\2\2\u0145\u0143\3\2\2\2\u0146\u0148\5\30\r\2\u0147\u0129\3\2\2\2\u0147"+
		"\u0138\3\2\2\2\u0148\u014b\3\2\2\2\u0149\u0147\3\2\2\2\u0149\u014a\3\2"+
		"\2\2\u014a\27\3\2\2\2\u014b\u0149\3\2\2\2\u014c\u014d\b\r\1\2\u014d\u014e"+
		"\5\32\16\2\u014e\u016f\3\2\2\2\u014f\u0153\f\4\2\2\u0150\u0152\7)\2\2"+
		"\u0151\u0150\3\2\2\2\u0152\u0155\3\2\2\2\u0153\u0151\3\2\2\2\u0153\u0154"+
		"\3\2\2\2\u0154\u0156\3\2\2\2\u0155\u0153\3\2\2\2\u0156\u015a\7\36\2\2"+
		"\u0157\u0159\7)\2\2\u0158\u0157\3\2\2\2\u0159\u015c\3\2\2\2\u015a\u0158"+
		"\3\2\2\2\u015a\u015b\3\2\2\2\u015b\u015d\3\2\2\2\u015c\u015a\3\2\2\2\u015d"+
		"\u016e\5\32\16\2\u015e\u0162\f\3\2\2\u015f\u0161\7)\2\2\u0160\u015f\3"+
		"\2\2\2\u0161\u0164\3\2\2\2\u0162\u0160\3\2\2\2\u0162\u0163\3\2\2\2\u0163"+
		"\u0165\3\2\2\2\u0164\u0162\3\2\2\2\u0165\u0169\7\37\2\2\u0166\u0168\7"+
		")\2\2\u0167\u0166\3\2\2\2\u0168\u016b\3\2\2\2\u0169\u0167\3\2\2\2\u0169"+
		"\u016a\3\2\2\2\u016a\u016c\3\2\2\2\u016b\u0169\3\2\2\2\u016c\u016e\5\32"+
		"\16\2\u016d\u014f\3\2\2\2\u016d\u015e\3\2\2\2\u016e\u0171\3\2\2\2\u016f"+
		"\u016d\3\2\2\2\u016f\u0170\3\2\2\2\u0170\31\3\2\2\2\u0171\u016f\3\2\2"+
		"\2\u0172\u0186\5\34\17\2\u0173\u0174\7\33\2\2\u0174\u0186\5\32\16\2\u0175"+
		"\u0179\7\31\2\2\u0176\u0178\7)\2\2\u0177\u0176\3\2\2\2\u0178\u017b\3\2"+
		"\2\2\u0179\u0177\3\2\2\2\u0179\u017a\3\2\2\2\u017a\u017c\3\2\2\2\u017b"+
		"\u0179\3\2\2\2\u017c\u0180\5\20\t\2\u017d\u017f\7)\2\2\u017e\u017d\3\2"+
		"\2\2\u017f\u0182\3\2\2\2\u0180\u017e\3\2\2\2\u0180\u0181\3\2\2\2\u0181"+
		"\u0183\3\2\2\2\u0182\u0180\3\2\2\2\u0183\u0184\7\32\2\2\u0184\u0186\3"+
		"\2\2\2\u0185\u0172\3\2\2\2\u0185\u0173\3\2\2\2\u0185\u0175\3\2\2\2\u0186"+
		"\33\3\2\2\2\u0187\u018a\7\'\2\2\u0188\u018a\5\36\20\2\u0189\u0187\3\2"+
		"\2\2\u0189\u0188\3\2\2\2\u018a\35\3\2\2\2\u018b\u018d\7\t\2\2\u018c\u018e"+
		"\7)\2\2\u018d\u018c\3\2\2\2\u018e\u018f\3\2\2\2\u018f\u018d\3\2\2\2\u018f"+
		"\u0190\3\2\2\2\u0190\u0191\3\2\2\2\u0191\u0193\7\25\2\2\u0192\u0194\7"+
		")\2\2\u0193\u0192\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u0193\3\2\2\2\u0195"+
		"\u0196\3\2\2\2\u0196\u0197\3\2\2\2\u0197\u01e8\7(\2\2\u0198\u019a\7\n"+
		"\2\2\u0199\u019b\7)\2\2\u019a\u0199\3\2\2\2\u019b\u019c\3\2\2\2\u019c"+
		"\u019a\3\2\2\2\u019c\u019d\3\2\2\2\u019d\u019e\3\2\2\2\u019e\u01a0\7\25"+
		"\2\2\u019f\u01a1\7)\2\2\u01a0\u019f\3\2\2\2\u01a1\u01a2\3\2\2\2\u01a2"+
		"\u01a0\3\2\2\2\u01a2\u01a3\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4\u01a6\7("+
		"\2\2\u01a5\u01a7\7)\2\2\u01a6\u01a5\3\2\2\2\u01a7\u01a8\3\2\2\2\u01a8"+
		"\u01a6\3\2\2\2\u01a8\u01a9\3\2\2\2\u01a9\u01aa\3\2\2\2\u01aa\u01e8\5\32"+
		"\16\2\u01ab\u01ad\7\13\2\2\u01ac\u01ae\7)\2\2\u01ad\u01ac\3\2\2\2\u01ae"+
		"\u01af\3\2\2\2\u01af\u01ad\3\2\2\2\u01af\u01b0\3\2\2\2\u01b0\u01b1\3\2"+
		"\2\2\u01b1\u01b3\7\25\2\2\u01b2\u01b4\7)\2\2\u01b3\u01b2\3\2\2\2\u01b4"+
		"\u01b5\3\2\2\2\u01b5\u01b3\3\2\2\2\u01b5\u01b6\3\2\2\2\u01b6\u01b7\3\2"+
		"\2\2\u01b7\u01e8\7(\2\2\u01b8\u01ba\7\f\2\2\u01b9\u01bb\7)\2\2\u01ba\u01b9"+
		"\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01ba\3\2\2\2\u01bc\u01bd\3\2\2\2\u01bd"+
		"\u01be\3\2\2\2\u01be\u01e8\7(\2\2\u01bf\u01c1\7\r\2\2\u01c0\u01c2\7)\2"+
		"\2\u01c1\u01c0\3\2\2\2\u01c2\u01c3\3\2\2\2\u01c3\u01c1\3\2\2\2\u01c3\u01c4"+
		"\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c5\u01e8\7(\2\2\u01c6\u01c8\7\16\2\2\u01c7"+
		"\u01c9\7)\2\2\u01c8\u01c7\3\2\2\2\u01c9\u01ca\3\2\2\2\u01ca\u01c8\3\2"+
		"\2\2\u01ca\u01cb\3\2\2\2\u01cb\u01cc\3\2\2\2\u01cc\u01ce\7(\2\2\u01cd"+
		"\u01cf\7)\2\2\u01ce\u01cd\3\2\2\2\u01cf\u01d0\3\2\2\2\u01d0\u01ce\3\2"+
		"\2\2\u01d0\u01d1\3\2\2\2\u01d1\u01d2\3\2\2\2\u01d2\u01e8\7(\2\2\u01d3"+
		"\u01d5\7\17\2\2\u01d4\u01d6\7)\2\2\u01d5\u01d4\3\2\2\2\u01d6\u01d7\3\2"+
		"\2\2\u01d7\u01d5\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9"+
		"\u01db\7(\2\2\u01da\u01dc\7)\2\2\u01db\u01da\3\2\2\2\u01dc\u01dd\3\2\2"+
		"\2\u01dd\u01db\3\2\2\2\u01dd\u01de\3\2\2\2\u01de\u01df\3\2\2\2\u01df\u01e8"+
		"\7(\2\2\u01e0\u01e2\7\20\2\2\u01e1\u01e3\7)\2\2\u01e2\u01e1\3\2\2\2\u01e3"+
		"\u01e4\3\2\2\2\u01e4\u01e2\3\2\2\2\u01e4\u01e5\3\2\2\2\u01e5\u01e6\3\2"+
		"\2\2\u01e6\u01e8\5\32\16\2\u01e7\u018b\3\2\2\2\u01e7\u0198\3\2\2\2\u01e7"+
		"\u01ab\3\2\2\2\u01e7\u01b8\3\2\2\2\u01e7\u01bf\3\2\2\2\u01e7\u01c6\3\2"+
		"\2\2\u01e7\u01d3\3\2\2\2\u01e7\u01e0\3\2\2\2\u01e8\37\3\2\2\2\u01e9\u01f3"+
		"\7\21\2\2\u01ea\u01f3\7\22\2\2\u01eb\u01ed\7\23\2\2\u01ec\u01ee\7)\2\2"+
		"\u01ed\u01ec\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef\u01ed\3\2\2\2\u01ef\u01f0"+
		"\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\u01f3\7(\2\2\u01f2\u01e9\3\2\2\2\u01f2"+
		"\u01ea\3\2\2\2\u01f2\u01eb\3\2\2\2\u01f3!\3\2\2\2I$).\66=DKQW^elsz\177"+
		"\u0081\u0087\u008e\u0093\u00a7\u00b2\u00bb\u00c2\u00c9\u00d0\u00d4\u00da"+
		"\u00e1\u00e9\u00f0\u00f8\u00ff\u0107\u010e\u0116\u011d\u0121\u0123\u012d"+
		"\u0134\u013c\u0143\u0147\u0149\u0153\u015a\u0162\u0169\u016d\u016f\u0179"+
		"\u0180\u0185\u0189\u018f\u0195\u019c\u01a2\u01a8\u01af\u01b5\u01bc\u01c3"+
		"\u01ca\u01d0\u01d7\u01dd\u01e4\u01e7\u01ef\u01f2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}