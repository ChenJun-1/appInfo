package com.t201.cj.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.t201.cj.pojo.User;
import com.t201.cj.service.user.UserService;
import com.t201.cj.tools.Constants;;

@Controller
public class LoginController {
	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/login.html")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/dologin.html",method=RequestMethod.POST)
	public String doLogin(@RequestParam String userCode,
						  @RequestParam String userPassword,
						  HttpServletRequest request,
						  HttpSession session)throws Exception{
		logger.debug("doLogin=====>userCode:" + userCode + "userPassword:" + userPassword);
		//调用service方法，进行用户匹配
		User user = userService.login(userCode, userPassword);
		if (null != user) {	//登录成功
			session.setAttribute(Constants.USER_SESSION, user);
			return "redirect:/sys/main.html";
		}else {
			request.setAttribute("error", "用户名或者密码不正确");
		}
		return "login";
	}
	
	@RequestMapping(value="/logout.html")
	public String logout(HttpSession session){
		session.removeAttribute(Constants.USER_SESSION);
		return "login";
	}
	
	@RequestMapping(value="/sys/main.html")
	public String main(){
		return "frame";
	}
}
