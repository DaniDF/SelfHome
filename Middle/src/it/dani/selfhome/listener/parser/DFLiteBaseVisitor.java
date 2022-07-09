// Generated from C:/Users/Daniele/Documents/MEGA/Progetti/SelfHome/New/SelfHome/Middle/grammar\DFLite.g4 by ANTLR 4.9.2
package it.dani.selfhome.listener.parser;
import it.dani.selfhome.icontroller.IControllerInterface;
import it.dani.selfhome.model.Device;
import it.dani.selfhome.model.DeviceState;
import it.dani.selfhome.model.Group;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This class provides an empty implementation of {@link DFLiteVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 */
public class DFLiteBaseVisitor extends AbstractParseTreeVisitor<Optional<Object>> implements DFLiteVisitor<Optional<Object>> {

	private final IControllerInterface controller;

	public DFLiteBaseVisitor(@NotNull IControllerInterface controller)
	{
		this.controller = controller;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitProgramStatement(DFLiteParser.ProgramStatementContext ctx) {
		Optional<Object> result = Optional.empty();

		for(ParseTree e : ctx.children)
		{
			Optional<Object> temp = this.visit(e);
			if(temp.isPresent()) result = temp;
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitStatementLineCommand(DFLiteParser.StatementLineCommandContext ctx) {
		return this.visit(ctx.l);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitStatementRepeat(DFLiteParser.StatementRepeatContext ctx) {
		Optional<Object> result = Optional.empty();

		Optional<Object> partial = this.visit(ctx.f);
		int repetition = 0;
		if(partial.isPresent() && partial.get().getClass().equals(Boolean.class))
		{
			repetition = ((Boolean) partial.get())? 1:0;
		}
		else if(partial.isPresent() && partial.get().getClass().equals(Integer.class))
		{
			repetition = (Integer) partial.get();
		}

		for(int count = 0; partial.isPresent() && count < repetition; count++)
		{
			result = this.visit(ctx.b);
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitStatementIf(DFLiteParser.StatementIfContext ctx) {
		return this.visit(ctx.i);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitIfStatementIf(DFLiteParser.IfStatementIfContext ctx) {
		Optional<Object> result = Optional.empty();

		Optional<Object> partial = this.visit(ctx.f);

		if(partial.isPresent() && (partial.get().getClass().equals(Integer.class) && !partial.get().equals(0) ||
				partial.get().getClass().equals(Boolean.class) && (Boolean) partial.get()))
		{
			result = this.visit(ctx.b);
		}
		else if(ctx.i != null && !ctx.i.isEmpty())
		{
			result = this.visit(ctx.i);
		}
		else if(ctx.b2 != null && !ctx.b2.isEmpty())
		{
			result = this.visit(ctx.b2);
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitBlockStatement(DFLiteParser.BlockStatementContext ctx) {
		Optional<Object> result = Optional.empty();

		for(DFLiteParser.StatementContext s : ctx.statement())
		{
			result = this.visit(s);
		}
		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitFilterComp(DFLiteParser.FilterCompContext ctx) {
		return this.visit(ctx.c);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitLineCom(DFLiteParser.LineComContext ctx) {
		return this.visit(ctx.c);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitSeparator(DFLiteParser.SeparatorContext ctx) {
		return Optional.empty();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitComparatorEE(DFLiteParser.ComparatorEEContext ctx) {
		Optional<Object> result = Optional.empty();
		Optional<Object> term1 = this.visit(ctx.c);
		Optional<Object> term2 = this.visit(ctx.s);

		if(term1.isPresent() && term1.get().getClass().equals(Integer.class) &&
				term2.isPresent() && term2.get().getClass().equals(Integer.class))
		{
			result = Optional.of(term1.get() == term2.get());
		}

		return result;
	}

	@Override
	public Optional<Object> visitLogicLoginAnd(DFLiteParser.LogicLoginAndContext ctx) {
		return this.visit(ctx.l);
	}

	@Override
	public Optional<Object> visitLogicOr(DFLiteParser.LogicOrContext ctx) {
		Optional<Object> term1O = this.visit(ctx.ls);
		Optional<Object> term2O = this.visit(ctx.ld);
		boolean term1 = term1O.isPresent() &&
				((term1O.get().getClass().equals(Integer.class) && (Integer) term1O.get() != 0)  ||
						(term1O.get().getClass().equals(Boolean.class) && (Boolean) term1O.get()));

		boolean term2 = term2O.isPresent() &&
				((term2O.get().getClass().equals(Integer.class) && (Integer) term2O.get() != 0)  ||
						(term2O.get().getClass().equals(Boolean.class) && (Boolean) term2O.get()));

		return Optional.of(term1 || term2);
	}

	@Override
	public Optional<Object> visitLogicComparator(DFLiteParser.LogicComparatorContext ctx) {
		return this.visit(ctx.c);
	}

	@Override
	public Optional<Object> visitLogicAnd(DFLiteParser.LogicAndContext ctx) {
		Optional<Object> term1O = this.visit(ctx.l);
		Optional<Object> term2O = this.visit(ctx.c);
		boolean term1 = term1O.isPresent() &&
				((term1O.get().getClass().equals(Integer.class) && (Integer) term1O.get() != 0)  ||
						(term1O.get().getClass().equals(Boolean.class) && (Boolean) term1O.get()));

		boolean term2 = term2O.isPresent() &&
				((term2O.get().getClass().equals(Integer.class) && (Integer) term2O.get() != 0)  ||
						(term2O.get().getClass().equals(Boolean.class) && (Boolean) term2O.get()));

		return Optional.of(term1 && term2);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitComparatorSum(DFLiteParser.ComparatorSumContext ctx) {
		return this.visit(ctx.s);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitComparatorGE(DFLiteParser.ComparatorGEContext ctx) {
		Optional<Object> result = Optional.empty();
		Optional<Object> term1 = this.visit(ctx.c);
		Optional<Object> term2 = this.visit(ctx.s);

		if(term1.isPresent() && term1.get().getClass().equals(Integer.class) &&
				term2.isPresent() && term2.get().getClass().equals(Integer.class))
		{
			result = Optional.of((Integer)term1.get() >= (Integer) term2.get());
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitComparatorGT(DFLiteParser.ComparatorGTContext ctx) {
		Optional<Object> result = Optional.empty();
		Optional<Object> term1 = this.visit(ctx.c);
		Optional<Object> term2 = this.visit(ctx.s);

		if(term1.isPresent() && term1.get().getClass().equals(Integer.class) &&
				term2.isPresent() && term2.get().getClass().equals(Integer.class))
		{
			result = Optional.of((Integer)term1.get() > (Integer) term2.get());
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitComparatorLE(DFLiteParser.ComparatorLEContext ctx) {
		Optional<Object> result = Optional.empty();
		Optional<Object> term1 = this.visit(ctx.c);
		Optional<Object> term2 = this.visit(ctx.s);

		if(term1.isPresent() && term1.get().getClass().equals(Integer.class) &&
				term2.isPresent() && term2.get().getClass().equals(Integer.class))
		{
			result = Optional.of((Integer)term1.get() <= (Integer) term2.get());
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitComparatorLT(DFLiteParser.ComparatorLTContext ctx) {
		Optional<Object> result = Optional.empty();
		Optional<Object> term1 = this.visit(ctx.c);
		Optional<Object> term2 = this.visit(ctx.s);

		if(term1.isPresent() && term1.get().getClass().equals(Integer.class) &&
				term2.isPresent() && term2.get().getClass().equals(Integer.class))
		{
			result = Optional.of((Integer)term1.get() < (Integer) term2.get());
		}

		return result;
	}

	@Override
	public Optional<Object> visitComparatorTime(DFLiteParser.ComparatorTimeContext ctx) {
		int value = Integer.parseInt(ctx.val.getText());
		Optional<Object> result = Optional.empty();

		if(ctx.sel.getText().equals("YEAR"))
		{
			result = Optional.of(LocalDate.now().getYear() == value);
		}
		else if(ctx.sel.getText().equals("MONTH"))
		{
			result = Optional.of(LocalDate.now().getMonthValue() == value);
		}
		else if(ctx.sel.getText().equals("DAY"))
		{
			result = Optional.of(LocalDate.now().getDayOfMonth() == value);
		}
		else if(ctx.sel.getText().equals("DOW"))
		{
			result = Optional.of(LocalDate.now().getDayOfWeek().getValue() == value);
		}
		else if(ctx.sel.getText().equals("HOUR"))
		{
			result = Optional.of(LocalDateTime.now().getHour() == value);
		}
		else if(ctx.sel.getText().equals("MIN"))
		{
			result = Optional.of(LocalDateTime.now().getMinute() == value);
		}
		else if(ctx.sel.getText().equals("SEC"))
		{
			result = Optional.of(LocalDateTime.now().getSecond() == value);
		}
		else if(ctx.sel.getText().equals("MILS"))
		{
			result = Optional.of((LocalDateTime.now().getNano()/1000000) == value);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitSumMulDiv(DFLiteParser.SumMulDivContext ctx) {
		return this.visit(ctx.a);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitSumAdd(DFLiteParser.SumAddContext ctx) {
		Optional<Object> result = Optional.empty();
		Optional<Object> term1 = this.visit(ctx.f);
		Optional<Object> term2 = this.visit(ctx.s);

		if(term1.isPresent() && term1.get().getClass().equals(Integer.class) &&
				term2.isPresent() && term2.get().getClass().equals(Integer.class))
		{
			result = Optional.of((Integer)term1.get() + (Integer) term2.get());
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitSumMinu(DFLiteParser.SumMinuContext ctx) {
		Optional<Object> result = Optional.empty();
		Optional<Object> term1 = this.visit(ctx.f);
		Optional<Object> term2 = this.visit(ctx.s);

		if(term1.isPresent() && term1.get().getClass().equals(Integer.class) &&
				term2.isPresent() && term2.get().getClass().equals(Integer.class))
		{
			result = Optional.of((Integer)term1.get() - (Integer) term2.get());
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitMulDivDiv(DFLiteParser.MulDivDivContext ctx) {
		Optional<Object> result = Optional.empty();
		Optional<Object> term1 = this.visit(ctx.f);
		Optional<Object> term2 = this.visit(ctx.s);

		if(term1.isPresent() && term1.get().getClass().equals(Integer.class) &&
				term2.isPresent() && term2.get().getClass().equals(Integer.class))
		{
			result = Optional.of((Integer)term1.get() / (Integer) term2.get());
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitMulDivMul(DFLiteParser.MulDivMulContext ctx) {
		Optional<Object> result = Optional.empty();
		Optional<Object> term1 = this.visit(ctx.f);
		Optional<Object> term2 = this.visit(ctx.s);

		if(term1.isPresent() && term1.get().getClass().equals(Integer.class) &&
				term2.isPresent() && term2.get().getClass().equals(Integer.class))
		{
			result = Optional.of((Integer)term1.get() * (Integer) term2.get());
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitMulDivTerm(DFLiteParser.MulDivTermContext ctx) {
		return this.visit(ctx.t);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitTermItem(DFLiteParser.TermItemContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Optional<Object> visitTermNotTerm(DFLiteParser.TermNotTermContext ctx) {
		Optional<Object> result = this.visit(ctx.i);

		if(result.isPresent())
		{
			Class<?> classR = result.get().getClass();
			if(classR.equals(Integer.class))
			{
				Integer temp = (Integer) result.get();
				result = Optional.of((temp == 0)? 1:0);
			}
			else if(classR.equals(Boolean.class))
			{
				Boolean temp = (Boolean) result.get();
				result = Optional.of(!temp);
			}
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitTermBra(DFLiteParser.TermBraContext ctx) {
		return this.visit(ctx.ex);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitItemNumber(DFLiteParser.ItemNumberContext ctx) {
		return Optional.of(Integer.parseInt(ctx.i.getText()));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitItemCommand(DFLiteParser.ItemCommandContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitCommandGet(DFLiteParser.CommandGetContext ctx) {
		Optional<Object> result = Optional.empty();

		if(ctx.sel.getText().equals("DISP"))
		{
			result = Optional.of(this.controller.getState(this.cleanName(ctx.name.getText())).getState());
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitCommandSet(DFLiteParser.CommandSetContext ctx) {
		String sel = ctx.sel.getText();
		Optional<Object> result = Optional.empty();
		Optional<Object> temp = this.visit(ctx.value);

		if(temp.isPresent())
		{
			BiFunction<String,DeviceState,Boolean> controllerFunction;
			if(sel.equals("GRUP"))
			{
				controllerFunction = this.controller::setStateGroup;
			}
			else
			{
				controllerFunction = this.controller::setState;
			}

			if(temp.get().getClass().equals(Integer.class))
			{
				int value = (Integer) (temp.get());
				result = Optional.of(controllerFunction.apply(this.cleanName(ctx.name.getText()),new DeviceState(value)));
			}
			else if(temp.get().getClass().equals(Boolean.class))
			{
				int value = ((Boolean) temp.get())? 0:1;
				result = Optional.of(controllerFunction.apply(this.cleanName(ctx.name.getText()),new DeviceState(value)));
			}
		}

		return result;
	}
	@Override public Optional<Object> visitCommandToggle(DFLiteParser.CommandToggleContext ctx) {
		String sel = ctx.sel.getText();
		boolean result = false;

		if(sel.equals("GRUP"))
		{
			for(Group g : this.controller.getGroups())
			{
				if(g.getName().equals(this.cleanName(ctx.name.getText())))
				{
					result = this.controller.toggleGroupState(g.getName());
				}
			}
		}
		else
		{
			result = this.controller.toggleState(this.cleanName(ctx.name.getText()));
		}

		return Optional.of(result);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitCommandNewGroup(DFLiteParser.CommandNewGroupContext ctx) {
		return Optional.of(this.controller.addNewGroup(this.cleanName(ctx.name.getText())));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitCommandDelGroup(DFLiteParser.CommandDelGroupContext ctx) {
		return Optional.of(this.controller.delGroup(this.cleanName(ctx.name.getText())));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitCommandAddDeviceToGroup(DFLiteParser.CommandAddDeviceToGroupContext ctx) {
		return Optional.of(this.controller.addDeviceToGroup(this.cleanName(ctx.nameD.getText()),this.cleanName(ctx.nameG.getText())));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitCommandDelDeviceFromGroup(DFLiteParser.CommandDelDeviceFromGroupContext ctx) {
		return Optional.of(this.controller.delDeviceFromGroup(this.cleanName(ctx.nameD.getText()),this.cleanName(ctx.nameG.getText())));
	}

	@Override
	public Optional<Object> visitCommandList(DFLiteParser.CommandListContext ctx) {
		return Optional.of(this.formatString(this.controller.getDevices(), Device::getName));
	}

	@Override
	public Optional<Object> visitCommandListGroup(DFLiteParser.CommandListGroupContext ctx) {
		return Optional.of(this.formatString(this.controller.getGroups(), Group::getName));
	}

	@Override
	public Optional<Object> visitCommandListDeviceInGroup(DFLiteParser.CommandListDeviceInGroupContext ctx) {
		Set<Device> devices = new TreeSet<>();

		for(Group g : this.controller.getGroups())
		{
			if(g.getName().equals(this.cleanName(ctx.name.getText())))
			{
				devices.addAll(g.getDevices());
			}
		}

		return Optional.of(this.formatString(devices, Device::getName));
	}

	@Override
	public Optional<Object> visitCommandSleep(DFLiteParser.CommandSleepContext ctx) {
		Optional<Object> result = this.visit(ctx.value);

		if(result.isPresent())
		{
			try {
				Thread.sleep((Integer) result.get());
			}
			catch (InterruptedException | ClassCastException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private String cleanName(@NotNull String name)
	{
		return name.replace("<","").replace(">","");
	}

	private <T> String formatString(Set<T> set, Function<T,String> function)
	{
		StringBuilder result = new StringBuilder();

		for(T i : set)
		{
			if(result.length() != 0) result.append(";");
			result.append(function.apply(i));
		}

		return result.toString();
	}
}