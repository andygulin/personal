package com.personal.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.personal.Constants;
import com.personal.entity.Accout;
import com.personal.entity.Article;
import com.personal.entity.Contacts;
import com.personal.entity.Photo;
import com.personal.entity.User;
import com.personal.service.AccoutService;
import com.personal.service.ArticleService;
import com.personal.service.ContactsService;
import com.personal.service.PhotoService;
import com.personal.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Inject
	private UserService userService;
	@Inject
	private AccoutService accoutService;
	@Inject
	private ArticleService articleService;
	@Inject
	private PhotoService photoService;
	@Inject
	private ContactsService contactsService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm(Model model, HttpSession session) {
		Object user = session.getAttribute(Constants.SESSION_USER);
		if (user != null) {
			return "redirect:/user/index";
		}
		model.addAttribute("title", "用户登录");
		return "user/loginForm";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes,
			Model model, HttpSession session) {
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		User currentUser = userService.check(user);
		if (currentUser == null) {
			redirectAttributes.addFlashAttribute("message", "用户名或密码错误");
			redirectAttributes.addFlashAttribute("messageType", "error");
			return "redirect:/user/login";
		} else {
			session.setAttribute(Constants.SESSION_USER, currentUser);
			userService.updateLastLoginDate(currentUser);
			return "redirect:/user/index";
		}
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("title", "个人中心");
		return "/user/indexFrame";
	}

	private static final int PAGE = 0;

	@RequestMapping(value = "/ajaxAccout", method = RequestMethod.GET)
	public String ajaxAccout(@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			Model model) {
		Page<Accout> accouts = accoutService.getListOrderByCreateDate(PAGE, size);
		model.addAttribute("accouts", accouts);
		return "user/ajax/accout";
	}

	@RequestMapping(value = "/ajaxArticle", method = RequestMethod.GET)
	public String ajaxArticle(@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			Model model) {
		Page<Article> articles = articleService.getArticleListOrderByCreateDate(PAGE, size);
		model.addAttribute("articles", articles);

		return "user/ajax/article";
	}

	@RequestMapping(value = "/ajaxContacts", method = RequestMethod.GET)
	public String ajaxContacts(@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			Model model) {
		Page<Contacts> contacts = contactsService.getArticleListOrderByCreateDate(PAGE, size);
		model.addAttribute("contacts", contacts);
		return "user/ajax/contacts";
	}

	@RequestMapping(value = "/ajaxPhoto", method = RequestMethod.GET)
	public String ajaxPhoto(@RequestParam(value = "size", required = false, defaultValue = "6") int size, Model model) {
		Page<Photo> photos = photoService.getPhotoListOrderByCreateDate(PAGE, size);
		model.addAttribute("photos", photos);
		return "user/ajax/photo";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String userLogout(RedirectAttributes redirectAttributes, HttpSession session) {
		session.removeAttribute(Constants.SESSION_USER);
		redirectAttributes.addFlashAttribute("message", "你已成功退出");
		redirectAttributes.addFlashAttribute("messageType", "success");
		return "redirect:/user/login";
	}

	@RequestMapping(value = "/updatepassword", method = RequestMethod.GET)
	public String updatepasswordForm(Model model) {
		model.addAttribute("title", "修改密码");
		return "user/updatepasswordForm";
	}

	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
	public String updatepassword(@RequestParam("password1") String password1,
			@RequestParam("password2") String password2, RedirectAttributes redirectAttributes, HttpSession session) {
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		String currentPassword = user.getPassword();// 现在的密码
		String oldPassword = DigestUtils.md5Hex(password1);// form提交的原密码
		if (!currentPassword.equals(oldPassword)) {
			redirectAttributes.addFlashAttribute("message", "原密码不正确，请重新输入");
			redirectAttributes.addFlashAttribute("messageType", "error");
			return "redirect:/user/updatepassword";
		}
		String newPassword = DigestUtils.md5Hex(password2);// form提交的新密码
		user.setPassword(newPassword);
		userService.save(user);

		session.removeAttribute(Constants.SESSION_USER);
		redirectAttributes.addFlashAttribute("message", "密码修改成功，请重新登录");
		redirectAttributes.addFlashAttribute("messageType", "success");
		return "redirect:/user/login";
	}

}