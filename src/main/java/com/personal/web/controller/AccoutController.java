package com.personal.web.controller;

import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.personal.Constants;
import com.personal.entity.Accout;
import com.personal.entity.User;
import com.personal.service.AccoutService;
import com.personal.tools.Servlets;

@Controller
@RequestMapping("/accout")
public class AccoutController {

	@Inject
	private AccoutService accoutService;

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("createDate", "创建时间");
	}

	@GetMapping
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX);
		Page<Accout> accouts = accoutService.findByPage(searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("accouts", accouts);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("searchParams",
				Servlets.encodeParameterStringWithPrefix(searchParams, Constants.SEARCH_PREFIX));
		model.addAttribute("title", "帐号列表");
		return "accout/accoutList";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("accout", new Accout());
		model.addAttribute("action", "create");
		model.addAttribute("title", "添加帐号");
		return "accout/accoutForm";
	}

	@PostMapping("/create")
	public String create(@Valid Accout accout, RedirectAttributes redirectAttributes, HttpSession session) {
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		accout.setCreateDate(new Date());
		accout.setCreateUser(user);
		accoutService.save(accout);
		redirectAttributes.addFlashAttribute("message", "创建帐号成功");
		return "redirect:/accout/";
	}

	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("accout", accoutService.get(id));
		model.addAttribute("action", "update");
		model.addAttribute("title", "更新帐号");
		return "accout/accoutForm";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("accout") Accout accout, RedirectAttributes redirectAttributes) {
		accoutService.save(accout);
		redirectAttributes.addFlashAttribute("message", "更新帐号成功");
		return "redirect:/accout/";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		accoutService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除帐号成功");
		return "redirect:/accout/";
	}
}
