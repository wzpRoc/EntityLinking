package org.ailab.wimfra.frontend;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.ailab.wimfra.util.Config;

import java.io.IOException;
import java.util.Map;

/**
 * FreeMarker user-defined directive that progressively transforms
 * the output of its nested content to Select-case.
 */
public class CtxDirective implements TemplateDirectiveModel {
    public void execute(Environment env,
                        Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        env.getOut().write(Config.getContextPath());
        env.getOut().flush();
    }

}