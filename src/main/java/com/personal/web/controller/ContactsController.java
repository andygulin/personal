package com.personal.web.controller;

import com.google.common.collect.Maps;
import com.personal.Constants;
import com.personal.entity.Contacts;
import com.personal.entity.ContactsType;
import com.personal.entity.User;
import com.personal.service.ContactsService;
import com.personal.tools.Servlets;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

    private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

    static {
        sortTypes.put("auto", "自动");
        sortTypes.put("createDate", "创建时间");
    }

    @Inject
    private ContactsService contactsService;

    @GetMapping
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "page.size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
                       HttpServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX);
        Page<Contacts> contacts = contactsService.findContactsByPage(searchParams, pageNumber, pageSize, sortType);
        model.addAttribute("contacts", contacts);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, Constants.SEARCH_PREFIX));
        model.addAttribute("title", "联系人列表");
        model.addAttribute("contactsTypes", contactsService.getContactsTypeCount());
        return "contacts/contactsList";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("contacts", new Contacts());
        model.addAttribute("action", "create");
        model.addAttribute("contactsType", contactsService.getContactsTypeList());
        model.addAttribute("title", "添加联系人");
        return "contacts/contactsForm";
    }

    @PostMapping("/create")
    public String create(@Valid Contacts contacts, RedirectAttributes redirectAttributes, HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        contacts.setCreateDate(new Date());
        contacts.setCreateUser(user);
        contactsService.saveContacts(contacts);
        redirectAttributes.addFlashAttribute("message", "创建联系人成功");
        return "redirect:/contacts/";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("contacts", contactsService.getContacts(id));
        model.addAttribute("action", "update");
        model.addAttribute("contactsType", contactsService.getContactsTypeList());
        model.addAttribute("title", "更新联系人");
        return "contacts/contactsForm";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("contacts") Contacts contacts, RedirectAttributes redirectAttributes) {
        contactsService.saveContacts(contacts);
        redirectAttributes.addFlashAttribute("message", "更新联系人成功");
        return "redirect:/contacts/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        contactsService.deleteContacts(id);
        redirectAttributes.addFlashAttribute("message", "删除联系人成功");
        return "redirect:/contacts/";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") String id, Model model) {
        Contacts contacts = contactsService.getContacts(id);
        model.addAttribute("contactsTypes", contactsService.getContactsTypeCount());
        model.addAttribute("contacts", contacts);
        model.addAttribute("title", "查看联系人-" + contacts.getName());
        return "contacts/contactsView";
    }

    // 分类的添加修改

    @GetMapping("/createType")
    public String createTypeForm(Model model) {
        model.addAttribute("contactsType", new ContactsType());
        model.addAttribute("action", "createType");
        model.addAttribute("title", "添加联系人分类");
        return "contacts/contactsTypeForm";
    }

    @PostMapping("/createType")
    public String createType(@Valid ContactsType contactsType, RedirectAttributes redirectAttributes) {
        contactsService.saveContactsType(contactsType);
        redirectAttributes.addFlashAttribute("message", "创建分类成功");
        return "redirect:/contacts/";
    }

    @GetMapping("/updateType/{id}")
    public String updateTypeForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("contactsType", contactsService.getContactsType(id));
        model.addAttribute("action", "updateType");
        model.addAttribute("title", "更新联系人分类");
        return "contacts/contactsTypeForm";
    }

    @PostMapping("/updateType")
    public String updateType(@Valid @ModelAttribute("contactsType") ContactsType contactsType,
                             RedirectAttributes redirectAttributes) {
        contactsService.saveContactsType(contactsType);
        redirectAttributes.addFlashAttribute("message", "更新分类成功");
        return "redirect:/contacts/";
    }
}
