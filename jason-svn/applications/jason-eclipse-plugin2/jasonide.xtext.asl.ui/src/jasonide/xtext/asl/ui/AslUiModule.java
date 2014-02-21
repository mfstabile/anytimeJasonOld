/*
 * generated by Xtext
 */
package jasonide.xtext.asl.ui;

import jasonide.xtext.asl.ui.outline.AslOutlineTreeProvider;


import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

import com.google.inject.Binder;

/**
 * Use this class to register components to be used within the IDE.
 */
public class AslUiModule extends jasonide.xtext.asl.ui.AbstractAslUiModule {
	public AslUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration () {
	    return AslHighlightingConfiguration.class;
	}
	
	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator(){
		return AslHighlightingCalculator.class;
	}
	
	public Class<? extends IOutlineTreeProvider> bindIOutlineTreeProvider() {
	    return AslOutlineTreeProvider.class;
	}
	
	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		/*binder.bind(String.class).annotatedWith(
						com.google.inject.name.Names.named(
						(XtextContentAssistProcessor.COMPLETION_AUTO_ACTIVATION_CHARS))).toInstance(" ");*/
	}
}
