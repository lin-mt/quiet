package com.gitee.quiet.common.service.json.jackson.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gitee.quiet.common.service.json.jackson.modifier.QuietDeserializerModifier;
import com.gitee.quiet.common.service.json.jackson.modifier.QuietSerializerModifier;

/**
 * QuietSimpleModule.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietSimpleModule extends SimpleModule {
    
    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.addBeanDeserializerModifier(new QuietDeserializerModifier());
        context.addBeanSerializerModifier(new QuietSerializerModifier());
    }
    
}
