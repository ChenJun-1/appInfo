package com.t201.cj.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.t201.cj.pojo.Role;
import com.t201.cj.pojo.User;
import com.t201.cj.service.role.RoleService;
import com.t201.cj.service.user.UserService;
import com.t201.cj.tools.Constants;
import com.t201.cj.tools.PageSupport;

@Controller
@RequestMapping("sys/user")
public class UserController {
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	@RequestMapping(value="/pwdsave.html",method=RequestMethod.POST)
	public String pwdSave(@RequestParam String newpassword,HttpSession session){
		logger.debug("pwdSave  newpassword " + newpassword);
		if (userService.updatePwd(((User)session.getAttribute(Constants.USER_SESSION)).getId().toString(), newpassword)) {
			session.removeAttribute(Constants.USER_SESSION);
			return "login";
		}
		return "pwdmodify";
	}
	
	@RequestMapping(value="/pwdmodify.html")
	public String pwdModify(){
		return "pwdmodify";
	}
	
	@RequestMapping(value="pwdmodify.json",method=RequestMethod.POST)
	@ResponseBody
	public Object pwdModifyCheck(@RequestParam String oldpassword,HttpSession session){
		logger.debug("getPwdByUserId oldPwd:"+oldpassword);
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(session.getAttribute(Constants.USER_SESSION) == null){
			resultMap.put("result", "sessionerror");
		}else if (((User)session.getAttribute(Constants.USER_SESSION)).getUserPassword().equals(oldpassword)) {
			resultMap.put("result", "true");
		}else {
			resultMap.put("result", "false");
		}
		return resultMap;
	}
	
	@RequestMapping(value="/view.json",method=RequestMethod.GET)
	@ResponseBody
	public User view(@RequestParam String id){
		logger.debug("view id ============== " + id);
		User user = new User();
		try {
			user = userService.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	@RequestMapping(value="/deluser.json",method=RequestMethod.GET)
	@ResponseBody
	public Object delUser(String id,
			HttpServletRequest request){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		User user = userService.getUserById(id);
		String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles"); 
		String fileName = null;
		File file = null;
		if (user != null) {	//判断用户是否存在
			if (user.getIdPicPath() != null && user.getIdPicPath() != "") {
				 fileName = path + File.separator + user.getIdPicPath();
				 file = new File(fileName);
				 if (file.exists()) {
					 file.delete();
				}
				logger.debug("delUser====>user.getIdPicPath()、file.exists():" + file.exists());
			}
			if (user.getWorkPicPath() != null && user.getWorkPicPath() != "") {
				 fileName = path + File.separator + user.getWorkPicPath();
				 file = new File(fileName);
				 if (file.exists()) {
					 file.delete();
				}
				logger.debug("delUser====>user.getWorkPicPath()、file.exists():" + file.exists());
			}
			if (userService.delUser(Integer.parseInt(id))) {
				resultMap.put("delResult", "true");
			}else {
				resultMap.put("delResult", "false");
			}
		}else {
			resultMap.put("delResult", "notexist");
		}
		return resultMap;
	}
	
	@RequestMapping(value="/modifysave.html",method=RequestMethod.POST)
	public String modifySave(User user,
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(value="attachs",required=false)MultipartFile[] attachs){
		logger.debug("modifyUserSave id===================== "+user.getId());
		String idPicPath = null;
		String workPicPath = null;
		String errorInfo = null;
		boolean flag = true;
		String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles"); 
		logger.info("uploadFile path ============== > "+path);
		if(attachs != null){
			for(int i = 0;i < attachs.length ;i++){
				MultipartFile attach = attachs[i];
				if(!attach.isEmpty()){
					if(i == 0){
						errorInfo = "uploadFileError";
					}else if(i == 1){
						errorInfo = "uploadWpError";
		        	}
					String oldFileName = attach.getOriginalFilename();//原文件名
					logger.info("uploadFile oldFileName ============== > "+oldFileName);
					String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀     
			        logger.debug("uploadFile prefix============> " + prefix);
					int filesize = 500000;
					logger.debug("uploadFile size============> " + attach.getSize());
			        if(attach.getSize() >  filesize){//上传大小不得超过 500k
		            	request.setAttribute(errorInfo, " * 上传大小不得超过 500k");
		            	flag = false;
		            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
		            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
		            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";  
		                logger.debug("new fileName======== " + attach.getName());
		                File targetFile = new File(path, fileName);  
		                if(!targetFile.exists()){  
		                    targetFile.mkdirs();  
		                }  
		                //保存  
		                try {  
		                	attach.transferTo(targetFile);  
		                } catch (Exception e) {  
		                    e.printStackTrace();  
		                    request.setAttribute(errorInfo, " * 上传失败！");
		                    flag = false;
		                } 
		                if(i == 0){
		                	 idPicPath = /*path+File.separator+*/fileName;
		                }else if(i == 1){
		                	 workPicPath = /*path+File.separator+*/fileName;
		                }
		                logger.debug("idPicPath: " + idPicPath);
		                logger.debug("workPicPath: " + workPicPath);
		            }else{
		            	request.setAttribute(errorInfo, " * 上传图片格式不正确");
		            	flag = false;
		            }
				}
			}
		}
		if(flag){
			user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
			user.setModifyDate(new Date());
			user.setIdPicPath(idPicPath);
			user.setWorkPicPath(workPicPath);
			try {
				if(userService.modify(user)){
					return "redirect:/sys/user/userlist.html";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "usermodify";
	}
	
	@RequestMapping(value="modify.html",method=RequestMethod.GET)
	public String modify(@RequestParam String uid,Model model){
		logger.debug("getUserById uid ============ " + uid);
		User user = userService.getUserById(uid);
		model.addAttribute(user);
		return "usermodify";
	}
	
	@RequestMapping(value="/addsave.html",method=RequestMethod.POST)
	public String addUserSave(User user,
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(value="attachs",required=false)MultipartFile[] attachs) throws Exception{
		String idPicPath = null;
		String workPicPath = null;
		String errorInfo = null;
		boolean flag = true;
		String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles"); 
		logger.info("uploadFile path ============== > "+path);
		for(int i = 0;i < attachs.length ;i++){
			MultipartFile attach = attachs[i];
			if(!attach.isEmpty()){
				if(i == 0){
					errorInfo = "uploadFileError";
				}else if(i == 1){
					errorInfo = "uploadWpError";
	        	}
				String oldFileName = attach.getOriginalFilename();//原文件名
				logger.info("uploadFile oldFileName ============== > "+oldFileName);
				String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀     
		        logger.debug("uploadFile prefix============> " + prefix);
				int filesize = 500000;
				logger.debug("uploadFile size============> " + attach.getSize());
		        if(attach.getSize() >  filesize){//上传大小不得超过 500k
	            	request.setAttribute(errorInfo, " * 上传大小不得超过 500k");
	            	flag = false;
	            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
	            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
	            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";  
	                logger.debug("new fileName======== " + attach.getName());
	                File targetFile = new File(path, fileName);  
	                if(!targetFile.exists()){  
	                    targetFile.mkdirs();  
	                }  
	                //保存  
	                try {  
	                	attach.transferTo(targetFile);  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                    request.setAttribute(errorInfo, " * 上传失败！");
	                    flag = false;
	                }  
	                if(i == 0){
	                	 idPicPath = /*path+File.separator+*/fileName;
	                }else if(i == 1){
	                	 workPicPath = /*path+File.separator+*/fileName;
	                }
	                logger.debug("idPicPath: " + idPicPath);
	                logger.debug("workPicPath: " + workPicPath);
	                
	            }else{
	            	request.setAttribute(errorInfo, " * 上传图片格式不正确");
	            	flag = false;
	            }
			}
		}
		if(flag){
			user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
			user.setCreationDate(new Date());
			user.setIdPicPath(idPicPath);
			user.setWorkPicPath(workPicPath);
			try {
				if(userService.add(user)){
					return "redirect:/sys/user/userlist.html";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "useradd";
	}
	
	@RequestMapping(value="/rolelist",method=RequestMethod.GET)
	@ResponseBody
	public Object getRoleList() throws Exception{
		List<Role> list = roleService.getRoleList();
		logger.debug("getRoleList======>list.size()" + list.size());
		return list;
	}
	
	@RequestMapping(value="/ucexist",method=RequestMethod.GET)
	@ResponseBody
	public Object userCodeIdExit(@RequestParam String userCode) throws Exception{
		logger.debug("userCodeIsExit userCode:"+userCode);
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isNullOrEmpty(userCode.trim())) {
			resultMap.put("userCode", "exist");
		}else {
			User user = userService.selectUserCodeExist(userCode);
			if (null != user) {
				resultMap.put("userCode", "exist");
			}else {
				resultMap.put("userCode", "noexist");
			}
		}
		return resultMap;
	}
	
	@RequestMapping(value="/add.html",method=RequestMethod.GET)
	public String addUser(){
		return "useradd";
	}
	
	@RequestMapping("/userlist.html")
	public String userList(Model model,
			@RequestParam(value="queryname",required=false) String queryUserName,
			@RequestParam(value="queryUserRole",required=false) String queryUserRole,
			@RequestParam(value="pageIndex",required=false) String pageIndex) throws Exception{
		logger.info("getUserList ---- > queryUserName: " + queryUserName);
		logger.info("getUserList ---- > queryUserRole: " + queryUserRole);
		logger.info("getUserList ---- > pageIndex: " + pageIndex);
		int _queryUserRole = 0;		
		List<User> userList = null;
		//设置页面容量
    	int pageSize = Constants.pageSize;
    	//当前页码
    	int currentPageNo = 1;
	
		if(queryUserName == null){
			queryUserName = "";
		}
		if(queryUserRole != null && !queryUserRole.equals("")){
			_queryUserRole = Integer.parseInt(queryUserRole);
		}
		
    	if(pageIndex != null){
    		try{
    			currentPageNo = Integer.valueOf(pageIndex);
    		}catch(NumberFormatException e){
    			return "redirect:/user/syserror.html";
    		}
    	}	
    	//总数量（表）	
    	int totalCount	= userService.getUserCount(queryUserName,_queryUserRole);
    	//总页数
    	PageSupport pages=new PageSupport();
    	pages.setCurrentPageNo(currentPageNo);
    	pages.setPageSize(pageSize);
    	pages.setTotalCount(totalCount);
    	int totalPageCount = pages.getTotalPageCount();
    	//控制首页和尾页
    	if(currentPageNo < 1){
    		currentPageNo = 1;
    	}else if(currentPageNo > totalPageCount){
    		currentPageNo = totalPageCount;
    	}
		userList = userService.getUserList(queryUserName,_queryUserRole,currentPageNo,pageSize);
		logger.info("getUserList ---- > userList.size(): " + userList.size());
		model.addAttribute("userList", userList);
		List<Role> roleList = null;
		roleList = roleService.getRoleList();
		model.addAttribute("roleList", roleList);
		model.addAttribute("queryUserName", queryUserName);
		model.addAttribute("queryUserRole", queryUserRole);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentPageNo", currentPageNo);
		return "userlist";
	}
	
}
