package com.personal.web.controller;

import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.personal.entity.Article;
import com.personal.entity.ArticleType;
import com.personal.entity.User;
import com.personal.service.ArticleService;
import com.personal.tools.Servlets;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Inject
	private ArticleService articleService;

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("createDate", "创建时间");
	}

	private static final String PAGE_SIZE = "20";

	@GetMapping
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX);
		Page<Article> articles = articleService.findAricleByPage(searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("articles", articles);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("searchParams",
				Servlets.encodeParameterStringWithPrefix(searchParams, Constants.SEARCH_PREFIX));
		model.addAttribute("title", "文章列表");
		model.addAttribute("articleTypes", articleService.getArticleTypeCount());
		return "article/articleList";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("article", new Article());
		model.addAttribute("action", "create");
		model.addAttribute("articleType", articleService.getArticleTypeList());
		model.addAttribute("title", "添加文章");
		return "article/articleForm";
	}

	@PostMapping("/create")
	public String create(@Valid Article article, RedirectAttributes redirectAttributes, HttpSession session) {
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		article.setCreateDate(new Date());
		article.setCreateUser(user);
		articleService.saveArticle(article);
		redirectAttributes.addFlashAttribute("message", "创建文章成功");
		return "redirect:/article/";
	}

	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("article", articleService.getArticle(id));
		model.addAttribute("action", "update");
		model.addAttribute("articleType", articleService.getArticleTypeList());
		model.addAttribute("title", "更新文章");
		return "article/articleForm";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("article") Article article, RedirectAttributes redirectAttributes) {
		articleService.saveArticle(article);
		redirectAttributes.addFlashAttribute("message", "更新文章成功");
		return "redirect:/article/";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		articleService.deleteArticle(id);
		redirectAttributes.addFlashAttribute("message", "删除文章成功");
		return "redirect:/article/";
	}

	@GetMapping("/view/{id}")
	public String view(@PathVariable("id") String id, Model model) {
		Article article = articleService.getArticle(id);
		model.addAttribute("articleTypes", articleService.getArticleTypeCount());
		model.addAttribute("article", article);
		model.addAttribute("title", "查看文章-" + article.getTitle());
		return "article/articleView";
	}

	@GetMapping("/printImage/{article_id}")
	public ResponseEntity<byte[]> printImage(@PathVariable(value = "article_id") String article_id) {
		Article article = articleService.getArticle(article_id);
		byte[] image = article.getImage();
		HttpHeaders headers = new HttpHeaders();
		String extName = FilenameUtils.getExtension(article.getSrcName());
		if (extName.equals("png")) {
			headers.setContentType(MediaType.IMAGE_PNG);
		} else if (extName.equals("gif")) {
			headers.setContentType(MediaType.IMAGE_GIF);
		} else {
			headers.setContentType(MediaType.IMAGE_JPEG);
		}
		return new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);
	}

	// 分类的添加修改

	@GetMapping("/createType")
	public String createTypeForm(Model model) {
		model.addAttribute("articleType", new ArticleType());
		model.addAttribute("action", "createType");
		model.addAttribute("title", "添加文章分类");
		return "article/articleTypeForm";
	}

	@PostMapping("/createType")
	public String createType(@Valid ArticleType articleType, RedirectAttributes redirectAttributes) {
		articleService.saveArticleType(articleType);
		redirectAttributes.addFlashAttribute("message", "创建分类成功");
		return "redirect:/article/";
	}

	@GetMapping("/updateType/{id}")
	public String updateTypeForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("articleType", articleService.getArticleType(id));
		model.addAttribute("action", "updateType");
		model.addAttribute("title", "更新分类文章");
		return "article/articleTypeForm";
	}

	@PostMapping("/updateType")
	public String updateType(@Valid @ModelAttribute("articleType") ArticleType articleType,
			RedirectAttributes redirectAttributes) {
		articleService.saveArticleType(articleType);
		redirectAttributes.addFlashAttribute("message", "更新分类成功");
		return "redirect:/article/";
	}
}
