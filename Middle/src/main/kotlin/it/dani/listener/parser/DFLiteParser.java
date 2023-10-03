package it.dani.listener.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class DFLiteParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, NUMBER=6, WORD=7, SPACE=8, NL=9, 
		WS=10;
	public static final int
		RULE_program = 0, RULE_separator = 1, RULE_command = 2, RULE_argument = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "separator", "command", "argument"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'GET'", "'SET'", "'LST'", "'BRIGH'", "'STATE'", null, null, "' '", 
			"'\\n'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "NUMBER", "WORD", "SPACE", "NL", 
			"WS"
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

	@SuppressWarnings("CheckReturnValue")
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
	@SuppressWarnings("CheckReturnValue")
	public static class ProgramStatementContext extends ProgramContext {
		public CommandContext c;
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public List<SeparatorContext> separator() {
			return getRuleContexts(SeparatorContext.class);
		}
		public SeparatorContext separator(int i) {
			return getRuleContext(SeparatorContext.class,i);
		}
		public ProgramStatementContext(ProgramContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).enterProgramStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).exitProgramStatement(this);
		}
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
			setState(17);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 14L) != 0)) {
				{
				{
				setState(8);
				((ProgramStatementContext)_localctx).c = command();
				setState(12);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SPACE || _la==NL) {
					{
					{
					setState(9);
					separator();
					}
					}
					setState(14);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(19);
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

	@SuppressWarnings("CheckReturnValue")
	public static class SeparatorContext extends ParserRuleContext {
		public TerminalNode SPACE() { return getToken(DFLiteParser.SPACE, 0); }
		public TerminalNode NL() { return getToken(DFLiteParser.NL, 0); }
		public SeparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_separator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).enterSeparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).exitSeparator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitSeparator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SeparatorContext separator() throws RecognitionException {
		SeparatorContext _localctx = new SeparatorContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_separator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
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

	@SuppressWarnings("CheckReturnValue")
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
	@SuppressWarnings("CheckReturnValue")
	public static class CommandGetContext extends CommandContext {
		public ArgumentContext arg;
		public Token name;
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public TerminalNode WORD() { return getToken(DFLiteParser.WORD, 0); }
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandGetContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).enterCommandGet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).exitCommandGet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandGet(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CommandSetContext extends CommandContext {
		public ArgumentContext arg;
		public Token name;
		public Token value;
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public TerminalNode WORD() { return getToken(DFLiteParser.WORD, 0); }
		public TerminalNode NUMBER() { return getToken(DFLiteParser.NUMBER, 0); }
		public List<TerminalNode> SPACE() { return getTokens(DFLiteParser.SPACE); }
		public TerminalNode SPACE(int i) {
			return getToken(DFLiteParser.SPACE, i);
		}
		public CommandSetContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).enterCommandSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).exitCommandSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandSet(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CommandLstContext extends CommandContext {
		public CommandLstContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).enterCommandLst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).exitCommandLst(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitCommandLst(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_command);
		int _la;
		try {
			setState(57);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				_localctx = new CommandGetContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(22);
				match(T__0);
				setState(24); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(23);
					match(SPACE);
					}
					}
					setState(26); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(28);
				((CommandGetContext)_localctx).arg = argument();
				setState(30); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(29);
					match(SPACE);
					}
					}
					setState(32); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(34);
				((CommandGetContext)_localctx).name = match(WORD);
				}
				break;
			case T__1:
				_localctx = new CommandSetContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(36);
				match(T__1);
				setState(38); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(37);
					match(SPACE);
					}
					}
					setState(40); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(42);
				((CommandSetContext)_localctx).arg = argument();
				setState(44); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(43);
					match(SPACE);
					}
					}
					setState(46); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(48);
				((CommandSetContext)_localctx).name = match(WORD);
				setState(50); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(49);
					match(SPACE);
					}
					}
					setState(52); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SPACE );
				setState(54);
				((CommandSetContext)_localctx).value = match(NUMBER);
				}
				break;
			case T__2:
				_localctx = new CommandLstContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(56);
				match(T__2);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentContext extends ParserRuleContext {
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
	 
		public ArgumentContext() { }
		public void copyFrom(ArgumentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentStaContext extends ArgumentContext {
		public ArgumentStaContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).enterArgumentSta(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).exitArgumentSta(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitArgumentSta(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentBriContext extends ArgumentContext {
		public ArgumentBriContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).enterArgumentBri(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DFLiteListener ) ((DFLiteListener)listener).exitArgumentBri(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DFLiteVisitor ) return ((DFLiteVisitor<? extends T>)visitor).visitArgumentBri(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_argument);
		try {
			setState(61);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
				_localctx = new ArgumentBriContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(59);
				match(T__3);
				}
				break;
			case T__4:
				_localctx = new ArgumentStaContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(60);
				match(T__4);
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

	public static final String _serializedATN =
		"\u0004\u0001\n@\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0001\u0000\u0005"+
		"\u0000\u000b\b\u0000\n\u0000\f\u0000\u000e\t\u0000\u0005\u0000\u0010\b"+
		"\u0000\n\u0000\f\u0000\u0013\t\u0000\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0004\u0002\u0019\b\u0002\u000b\u0002\f\u0002\u001a\u0001"+
		"\u0002\u0001\u0002\u0004\u0002\u001f\b\u0002\u000b\u0002\f\u0002 \u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002\'\b\u0002\u000b"+
		"\u0002\f\u0002(\u0001\u0002\u0001\u0002\u0004\u0002-\b\u0002\u000b\u0002"+
		"\f\u0002.\u0001\u0002\u0001\u0002\u0004\u00023\b\u0002\u000b\u0002\f\u0002"+
		"4\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002:\b\u0002\u0001\u0003"+
		"\u0001\u0003\u0003\u0003>\b\u0003\u0001\u0003\u0000\u0000\u0004\u0000"+
		"\u0002\u0004\u0006\u0000\u0001\u0001\u0000\b\tE\u0000\u0011\u0001\u0000"+
		"\u0000\u0000\u0002\u0014\u0001\u0000\u0000\u0000\u00049\u0001\u0000\u0000"+
		"\u0000\u0006=\u0001\u0000\u0000\u0000\b\f\u0003\u0004\u0002\u0000\t\u000b"+
		"\u0003\u0002\u0001\u0000\n\t\u0001\u0000\u0000\u0000\u000b\u000e\u0001"+
		"\u0000\u0000\u0000\f\n\u0001\u0000\u0000\u0000\f\r\u0001\u0000\u0000\u0000"+
		"\r\u0010\u0001\u0000\u0000\u0000\u000e\f\u0001\u0000\u0000\u0000\u000f"+
		"\b\u0001\u0000\u0000\u0000\u0010\u0013\u0001\u0000\u0000\u0000\u0011\u000f"+
		"\u0001\u0000\u0000\u0000\u0011\u0012\u0001\u0000\u0000\u0000\u0012\u0001"+
		"\u0001\u0000\u0000\u0000\u0013\u0011\u0001\u0000\u0000\u0000\u0014\u0015"+
		"\u0007\u0000\u0000\u0000\u0015\u0003\u0001\u0000\u0000\u0000\u0016\u0018"+
		"\u0005\u0001\u0000\u0000\u0017\u0019\u0005\b\u0000\u0000\u0018\u0017\u0001"+
		"\u0000\u0000\u0000\u0019\u001a\u0001\u0000\u0000\u0000\u001a\u0018\u0001"+
		"\u0000\u0000\u0000\u001a\u001b\u0001\u0000\u0000\u0000\u001b\u001c\u0001"+
		"\u0000\u0000\u0000\u001c\u001e\u0003\u0006\u0003\u0000\u001d\u001f\u0005"+
		"\b\u0000\u0000\u001e\u001d\u0001\u0000\u0000\u0000\u001f \u0001\u0000"+
		"\u0000\u0000 \u001e\u0001\u0000\u0000\u0000 !\u0001\u0000\u0000\u0000"+
		"!\"\u0001\u0000\u0000\u0000\"#\u0005\u0007\u0000\u0000#:\u0001\u0000\u0000"+
		"\u0000$&\u0005\u0002\u0000\u0000%\'\u0005\b\u0000\u0000&%\u0001\u0000"+
		"\u0000\u0000\'(\u0001\u0000\u0000\u0000(&\u0001\u0000\u0000\u0000()\u0001"+
		"\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000*,\u0003\u0006\u0003\u0000"+
		"+-\u0005\b\u0000\u0000,+\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000"+
		".,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000/0\u0001\u0000\u0000"+
		"\u000002\u0005\u0007\u0000\u000013\u0005\b\u0000\u000021\u0001\u0000\u0000"+
		"\u000034\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u000045\u0001\u0000"+
		"\u0000\u000056\u0001\u0000\u0000\u000067\u0005\u0006\u0000\u00007:\u0001"+
		"\u0000\u0000\u00008:\u0005\u0003\u0000\u00009\u0016\u0001\u0000\u0000"+
		"\u00009$\u0001\u0000\u0000\u000098\u0001\u0000\u0000\u0000:\u0005\u0001"+
		"\u0000\u0000\u0000;>\u0005\u0004\u0000\u0000<>\u0005\u0005\u0000\u0000"+
		"=;\u0001\u0000\u0000\u0000=<\u0001\u0000\u0000\u0000>\u0007\u0001\u0000"+
		"\u0000\u0000\t\f\u0011\u001a (.49=";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}