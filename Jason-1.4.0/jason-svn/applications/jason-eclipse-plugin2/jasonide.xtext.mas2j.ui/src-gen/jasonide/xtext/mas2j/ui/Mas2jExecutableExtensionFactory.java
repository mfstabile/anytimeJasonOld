/*
 * generated by Xtext
 */
package jasonide.xtext.mas2j.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class Mas2jExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return jasonide.xtext.mas2j.ui.internal.Mas2jActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return jasonide.xtext.mas2j.ui.internal.Mas2jActivator.getInstance().getInjector("jasonide.xtext.mas2j.Mas2j");
	}
	
}