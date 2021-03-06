package com.personal.web.controller;

import com.google.common.collect.Maps;
import com.personal.Constants;
import com.personal.entity.Photo;
import com.personal.entity.PhotoType;
import com.personal.entity.User;
import com.personal.service.PhotoService;
import com.personal.tools.Servlets;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    private static final String PAGE_SIZE = "8";
    private static final String PHOTO_VIEW_PAGE_SIZE = "6";
    private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

    static {
        sortTypes.put("auto", "自动");
        sortTypes.put("createDate", "创建时间");
    }

    @Inject
    private PhotoService photoService;

    @GetMapping
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
                       HttpServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX);
        Page<PhotoType> photoTypes = photoService.findPhotoTypeByPage(searchParams, pageNumber, pageSize, sortType);
        model.addAttribute("photoTypes", photoTypes);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, Constants.SEARCH_PREFIX));
        model.addAttribute("title", "相册列表");
        return "photo/photoList";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("photo", new Photo());
        model.addAttribute("action", "create");
        model.addAttribute("photoType", photoService.getPhotoTypeList());
        model.addAttribute("title", "添加相片");
        return "photo/photoForm";
    }

    @PostMapping("/create")
    public String create(RedirectAttributes redirectAttributes, HttpSession session, @Valid PhotoType type,
                         @RequestParam(value = "remark") String[] remarks, @RequestParam(value = "image") MultipartFile[] images)
            throws Exception {
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        Photo coverPhoto = null;
        byte[] image;
        for (int i = 0; i < images.length; i++) {
            MultipartFile file = images[i];
            if (!file.isEmpty()) {
                Photo photo = new Photo();
                photo.setSrcName(file.getOriginalFilename());
                photo.setType(type);
                image = photoService.ThumbnailsPhoto(file.getBytes());
                photo.setImage(image);
                photo.setRemark(remarks[i]);
                photo.setCreateDate(new Date());
                photo.setCreateUser(user);
                photoService.savePhoto(photo);
                if (coverPhoto == null) {
                    coverPhoto = photo;
                }
            }
        }
        PhotoType photoType = photoService.getPhotoType(type.getId());
        if (photoType.getCover() == null) {
            photoType.setId(type.getId());
            photoType.setCover(coverPhoto);
            photoService.savePhotoType(photoType);
        }
        redirectAttributes.addFlashAttribute("message", "图片上传成功");
        return "redirect:/photo/create?id=" + type.getId();
    }

    @GetMapping("/printImage/{photo_id}")
    public ResponseEntity<byte[]> printImage(@PathVariable(value = "photo_id") String photo_id) {
        Photo photo = photoService.getPhoto(photo_id);
        byte[] image = photo.getImage();
        HttpHeaders headers = new HttpHeaders();
        String extName = FilenameUtils.getExtension(photo.getSrcName());
        Constants.setImageContentType(extName, headers);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable(value = "id") String id,
                       @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "page.size", defaultValue = PHOTO_VIEW_PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
                       HttpServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX);
        Page<Photo> photos = photoService.findPhotoByPage(id, searchParams, pageNumber, pageSize, sortType);
        model.addAttribute("photos", photos);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, Constants.SEARCH_PREFIX));
        PhotoType photoType = photoService.getPhotoType(id);
        model.addAttribute("title", photoType.getName() + " - 列表浏览");
        model.addAttribute("viewType", "view2");
        model.addAttribute("id", id);
        model.addAttribute("photoType", photoService.getPhotoType(id));
        model.addAttribute("photoTypes", photoService.getPhotoTypeList());
        return "photo/photoView";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("id") String id) {
        photoService.deletePhoto(id);
        return null;
    }

    @PostMapping("/setCover")
    @ResponseBody
    public String setCover(@RequestParam("id") String id, @RequestParam("tid") String tid) {
        PhotoType photoType = photoService.getPhotoType(tid);
        Photo photo = photoService.getPhoto(id);
        photoType.setCover(photo);
        photoService.savePhotoType(photoType);
        return null;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable(value = "id") String id) throws Exception {
        Map<String, Object> resultMap = photoService.zipFile(id);
        File zipFile = (File) resultMap.get("zipFile");
        String zipFileName = (String) resultMap.get("zipFileName");
        byte[] response = (byte[]) resultMap.get("response");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(zipFile.length());
        headers.setContentDispositionFormData("attachment", new String(zipFileName.getBytes(), "ISO8859-1"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @PostMapping("/removeAll/{id}")
    @ResponseBody
    public String removeAll(@PathVariable(value = "id") String id) {
        PhotoType photoType = photoService.getPhotoType(id);
        photoType.setCover(null);
        photoService.savePhotoType(photoType);
        photoService.deletePhotoAllById(id);
        return null;
    }

    // 分类的添加修改

    @GetMapping(value = "/createType")
    public String createTypeForm(Model model) {
        model.addAttribute("photoType", new PhotoType());
        model.addAttribute("action", "createType");
        model.addAttribute("title", "添加图片分类");
        return "photo/photoTypeForm";
    }

    @PostMapping("/createType")
    public String createType(@Valid PhotoType photoType, RedirectAttributes redirectAttributes) {
        photoType.setCover(null);
        photoType.setCreateDate(new Date());
        photoService.savePhotoType(photoType);
        redirectAttributes.addFlashAttribute("message", "创建分类成功");
        return "redirect:/photo/";
    }

    @GetMapping("/updateType/{id}")
    public String updateTypeForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("photoType", photoService.getPhotoType(id));
        model.addAttribute("action", "updateType");
        model.addAttribute("title", "更新图片分类");
        return "photo/photoTypeForm";
    }

    @PostMapping("/updateType")
    public String updateType(@Valid @ModelAttribute("photoType") PhotoType photoType,
                             RedirectAttributes redirectAttributes) {
        String coverId = photoType.getCover().getId();
        if (StringUtils.isBlank(coverId) || StringUtils.isEmpty(coverId)) {
            photoType.setCover(null);
        }
        photoService.savePhotoType(photoType);
        redirectAttributes.addFlashAttribute("message", "更新分类成功");
        return "redirect:/photo/";
    }
}
