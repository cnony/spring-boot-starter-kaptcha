package com.google.code.kaptcha.spring.boot;

import javax.servlet.ServletException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.servlet.KaptchaServlet;

@Configuration
@ConditionalOnClass({ KaptchaServlet.class })
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaAutoConfiguration {

	//验证码
	@Bean
	@ConditionalOnMissingBean(name = "kaptchaServlet")
    public ServletRegistrationBean servletRegistrationBean(KaptchaProperties properties ) throws ServletException{  
		 ServletRegistrationBean registrationBean = new ServletRegistrationBean();
		 
		 KaptchaServlet kaptchaServlet = new KaptchaServlet();
		 
		 registrationBean.setServlet(kaptchaServlet);
		 
		 //默认参数
		 registrationBean.addInitParameter(Constants.KAPTCHA_BORDER, "no");
		 registrationBean.addInitParameter(Constants.KAPTCHA_BORDER_COLOR, "black");
		 registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
		 registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");
		 registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
		 
		if(CollectionUtils.isEmpty(properties.getParameters())) {
			registrationBean.getInitParameters().putAll(properties.getParameters());
		}
		
		registrationBean.addUrlMappings(properties.getPattern());
		 
		 
        return registrationBean;  
    }  
}
