package net.panda.web.support;


public abstract class AbstractController {

	protected final ErrorHandler errorHandler;

	public AbstractController(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

}
