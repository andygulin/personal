package com.personal.web.controller;

import com.personal.Constants;
import com.personal.entity.*;
import com.personal.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final int PAGE = 0;
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

    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session) {
        Object user = session.getAttribute(Constants.SESSION_USER);
        if (user != null) {
            return "redirect:/user/index";
        }
        model.addAttribute("title", "用户登录");
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String userLogin(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes, HttpSession session) {
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

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("title", "个人中心");
        return "/user/indexFrame";
    }

    @GetMapping("/ajaxAccout")
    public String ajaxAccout(@RequestParam(value = "size", required = false, defaultValue = "5") int size,
                             Model model) {
        Page<Accout> accouts = accoutService.getListOrderByCreateDate(PAGE, size);
        model.addAttribute("accouts", accouts);
        return "user/ajax/accout";
    }

    @GetMapping("/ajaxArticle")
    public String ajaxArticle(@RequestParam(value = "size", required = false, defaultValue = "5") int size,
                              Model model) {
        Page<Article> articles = articleService.getArticleListOrderByCreateDate(PAGE, size);
        model.addAttribute("articles", articles);

        return "user/ajax/article";
    }

    @GetMapping("/ajaxContacts")
    public String ajaxContacts(@RequestParam(value = "size", required = false, defaultValue = "5") int size,
                               Model model) {
        Page<Contacts> contacts = contactsService.getArticleListOrderByCreateDate(PAGE, size);
        model.addAttribute("contacts", contacts);
        return "user/ajax/contacts";
    }

    @GetMapping("/ajaxPhoto")
    public String ajaxPhoto(@RequestParam(value = "size", required = false, defaultValue = "6") int size, Model model) {
        Page<Photo> photos = photoService.getPhotoListOrderByCreateDate(PAGE, size);
        model.addAttribute("photos", photos);
        return "user/ajax/photo";
    }

    @GetMapping("/logout")
    public String userLogout(RedirectAttributes redirectAttributes, HttpSession session) {
        session.removeAttribute(Constants.SESSION_USER);
        redirectAttributes.addFlashAttribute("message", "你已成功退出");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/user/login";
    }

    @GetMapping("/updatepassword")
    public String updatepasswordForm(Model model) {
        model.addAttribute("title", "修改密码");
        return "user/updatepasswordForm";
    }

    @PostMapping("/updatepassword")
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
