package com.pear.commons.tools.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * CustomizedPropertyPlaceholderConfigurer
 *
 * @author littlehui
 * @date 2014/9/22
 * @since V1.0
 */
public class ContextPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private Properties ctxProps = new Properties();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        for (Object key : props.keySet()) {
            ctxProps.setProperty((String) key, props.getProperty((String) key));
        }
    }

    public String getProps(String key) {
        return ctxProps.getProperty(key);
    }
}
