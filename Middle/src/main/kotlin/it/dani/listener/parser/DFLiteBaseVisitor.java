package it.dani.listener.parser;

import it.dani.icontroller.Controller;
import it.dani.model.Device;
import it.dani.model.DeviceState;
import it.dani.selfhome.DeviceStateChangeException;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * This class provides an empty implementation of {@link DFLiteVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 */
@SuppressWarnings("CheckReturnValue")
public class DFLiteBaseVisitor extends AbstractParseTreeVisitor<Optional<Object>> implements DFLiteVisitor<Optional<Object>> {
	private final Controller controller;

	public DFLiteBaseVisitor(@NotNull Controller controller)
	{
		this.controller = controller;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitProgramStatement(DFLiteParser.ProgramStatementContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitSeparator(DFLiteParser.SeparatorContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitCommandGet(DFLiteParser.CommandGetContext ctx) {
		String deviceName = this.cleanName(ctx.name.getText());

		DeviceState deviceState = null;

		for(Device device : this.controller.getDevs()) {
			if(device.getName().equals(deviceName)) {
				deviceState = device.getState();
			}
		}

		return Optional.ofNullable(deviceState);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitCommandSet(DFLiteParser.CommandSetContext ctx) {
		String deviceName = this.cleanName(ctx.name.getText());
		DeviceState deviceState = new DeviceState(ctx.value.getText());

		boolean response = false;

		for(Device device : this.controller.getDevs()) {
			if(device.getName().equals(deviceName)) {
				try {
					device.setState(deviceState);
					response = true;
				} catch (DeviceStateChangeException e) {
					System.err.println("Error changing ${this.name} into $value error = ${e.message}");
					response = e.getResponse();
				}
			}
		}
		return Optional.of(response);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Optional<Object> visitCommandLst(DFLiteParser.CommandLstContext ctx) {
		StringBuilder result = new StringBuilder();

		for(Device device : this.controller.getDevices()) {
			if(result.length() != 0) result.append(";");
			result.append(device.getName());
		}

		return Optional.of(result.toString());
	}

	private String cleanName(@NotNull String name)
	{
		return name.replace("<","").replace(">","");
	}
}