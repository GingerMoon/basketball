package basketball.interceptor;

import java.util.Iterator;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import basketball.annotation.Layout;
import basketball.domain.Player;
import basketball.domain.Player;
import basketball.domain.PlayerUserDetails;


public class ThymeleafLayoutInterceptor extends HandlerInterceptorAdapter {

	private static final String DEFAULT_LAYOUT = "blank";
	private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";

	private String defaultLayout() {
		return DEFAULT_LAYOUT;
	}
	
	private String viewAttributeName = DEFAULT_VIEW_ATTRIBUTE_NAME;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		if (modelAndView == null || !modelAndView.hasView()) {
			return;
		}
		String originalViewName = modelAndView.getViewName();
		if (isRedirectOrForward(originalViewName)) {
			return;
		}
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			String layoutName = getLayoutName(handlerMethod);
			modelAndView.setViewName(layoutName);
			modelAndView.addObject(this.viewAttributeName, originalViewName);
			
			Player account = null;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(principal instanceof PlayerUserDetails) {
				account = ((PlayerUserDetails)principal).getPlayer();
			} else {
				account = new Player();
			}
			modelAndView.addObject("account", account);
		}
	}

	private boolean isRedirectOrForward(String viewName) {
		return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
	}

	private String getLayoutName(HandlerMethod handlerMethod) {
		Layout layout = getMethodOrTypeAnnotation(handlerMethod);
		if (layout == null) {
			return defaultLayout();
		} else {
			return layout.value();
		}
	}

	private Layout getMethodOrTypeAnnotation(HandlerMethod handlerMethod) {
		Layout layout = handlerMethod.getMethodAnnotation(Layout.class);
		if (layout == null) {
			return handlerMethod.getBeanType().getAnnotation(Layout.class);
		}
		return layout;
	}
}