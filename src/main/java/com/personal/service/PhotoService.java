package com.personal.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.personal.Constants;
import com.personal.entity.Photo;
import com.personal.entity.PhotoType;
import com.personal.repository.PhotoRepository;
import com.personal.repository.PhotoTypeRepository;
import com.personal.tools.DynamicSpecifications;
import com.personal.tools.SearchFilter;
import com.personal.tools.SearchFilter.Operator;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.zeroturnaround.zip.ZipUtil;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PhotoService {

    @Inject
    private PhotoRepository photoRepository;
    // PhotoType
    @Inject
    private PhotoTypeRepository photoTypeRepository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Photo> getPhotoList() {
        return photoRepository.findAll();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Photo getPhoto(String id) {
        return photoRepository.findOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Photo> batchSavePhoto(List<Photo> photos) {
        return photoRepository.save(photos);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePhoto(String id) {
        photoRepository.delete(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Photo> getPhotoListById(String id) {
        return photoRepository.getListById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePhotoAllById(String id) {
        photoRepository.deleteAllById(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Page<Photo> findPhotoByPage(String id, Map<String, Object> searchParams, int pageNumber, int pageSize,
                                       String sortType) {
        PageRequest pageRequest = buildPhotoPageRequest(pageNumber, pageSize, sortType);
        Specification<Photo> spec = buildPhotoSpecification(id, searchParams);
        return photoRepository.findAll(spec, pageRequest);
    }

    private PageRequest buildPhotoPageRequest(int pageNumber, int pageSize, String sortType) {
        Sort sort = null;
        if ("auto".equals(sortType)) {
            sort = new Sort(Direction.DESC, "id");
        } else if ("createDate".equals(sortType)) {
            sort = new Sort(Direction.DESC, "createDate");
        }
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    private Specification<Photo> buildPhotoSpecification(String id, Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put("type.id", new SearchFilter("type.id", Operator.EQ, id));
        Specification<Photo> spec = DynamicSpecifications.bySearchFilter(filters.values(), Photo.class);
        return spec;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Page<Photo> getPhotoListOrderByCreateDate(int page, int pageSize) {
        return photoRepository.findAll(new PageRequest(page, pageSize, new Sort(Direction.DESC, "createDate")));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> zipFile(String id) throws Exception {
        List<Photo> photos = photoRepository.getDistinctListById(id);
        PhotoType photoType = photoTypeRepository.findOne(id);
        List<File> files = Lists.newArrayList();
        File file;
        for (Photo photo : photos) {
            byte[] image = photo.getImage();
            String filename = Constants.SYSTEM_TEMP_PATH + photo.getSrcName();
            file = new File(filename);
            FileCopyUtils.copy(image, file);
            files.add(file);
        }
        File[] fileArray = files.toArray(new File[files.size()]);
        String zipFileName = photoType.getName() + Constants.ZIP_FILE_EXT;
        File zipFile = new File(Constants.SYSTEM_TEMP_PATH + zipFileName);
        ZipUtil.packEntries(fileArray, zipFile);
        byte[] response = FileCopyUtils.copyToByteArray(zipFile);
        Map<String, Object> returnMap = Maps.newHashMap();
        returnMap.put("zipFile", zipFile);
        returnMap.put("zipFileName", photoType.getName() + Constants.ZIP_FILE_EXT);
        returnMap.put("response", response);
        return returnMap;
    }

    public byte[] ThumbnailsPhoto(byte[] image) {
        byte[] in = image;
        File out = new File(Constants.SYSTEM_TEMP_PATH + UUID.randomUUID() + Constants.DEFAULT_IMAGE_EXT);
        byte[] photo = null;
        try {
            FileCopyUtils.copy(in, out);
            Thumbnails.of(out).size(Constants.DEFAULT_IMAGE_WIDTH, Constants.DEFAULT_IMAGE_HEIGHT).toFile(out);
            photo = FileCopyUtils.copyToByteArray(out);
        } catch (IOException e) {
        }
        return photo;
    }

    public byte[] ThumbnailsPhoto(File file) {
        byte[] photo = null;
        try {
            Thumbnails.of(file).size(Constants.DEFAULT_IMAGE_WIDTH, Constants.DEFAULT_IMAGE_HEIGHT).toFile(file);
            photo = FileCopyUtils.copyToByteArray(file);
        } catch (IOException e) {
        }
        return photo;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<PhotoType> getPhotoTypeList() {
        List<PhotoType> photoTypes = photoTypeRepository.findAll();
        if (CollectionUtils.isNotEmpty(photoTypes)) {
            for (PhotoType photoType : photoTypes) {
                String id = photoType.getId();
                Long count = photoTypeRepository.getPhotoTypeCount(id);
                photoType.setCount(count);
            }
        }
        return photoTypes;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PhotoType getPhotoType(String id) {
        return photoTypeRepository.findOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public PhotoType savePhotoType(PhotoType photoType) {
        return photoTypeRepository.save(photoType);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<PhotoType> batchSavePhotoType(List<PhotoType> photoTypes) {
        return photoTypeRepository.save(photoTypes);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePhotoType(String id) {
        photoTypeRepository.delete(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Page<PhotoType> findPhotoTypeByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
                                               String sortType) {
        PageRequest pageRequest = buildPhotoTypePageRequest(pageNumber, pageSize, sortType);
        Specification<PhotoType> spec = buildPhotoTypeSpecification(searchParams);
        Page<PhotoType> pages = photoTypeRepository.findAll(spec, pageRequest);
        List<PhotoType> photoTypes = pages.getContent();
        for (PhotoType photoType : photoTypes) {
            String id = photoType.getId();
            Long count = photoTypeRepository.getPhotoTypeCount(id);
            photoType.setCount(count);
        }
        return pages;
    }

    private PageRequest buildPhotoTypePageRequest(int pageNumber, int pageSize, String sortType) {
        Sort sort = null;
        if ("auto".equals(sortType)) {
            sort = new Sort(Direction.DESC, "id");
        } else if ("createDate".equals(sortType)) {
            sort = new Sort(Direction.DESC, "createDate");
        }
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    private Specification<PhotoType> buildPhotoTypeSpecification(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<PhotoType> spec = DynamicSpecifications.bySearchFilter(filters.values(), PhotoType.class);
        return spec;
    }

    @Transactional(rollbackFor = Exception.class)
    public void setCover(String id, String tid) {
        photoTypeRepository.setCover(id, tid);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PhotoType getPhotoTypeByName(String name) {
        return photoTypeRepository.getPhotoTypeByName(name);
    }
}
