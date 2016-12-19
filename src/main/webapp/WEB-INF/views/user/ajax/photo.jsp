<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>

<c:forEach var="photo" items="${photos.content }">
	<span class="span4">
		<p class="text-info">${photo.createUser.nickname }</p>
		<p class="text-info"><fmt:formatDate value="${photo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
		<p class="text-info">上传了图片至&nbsp;&nbsp;<a href="${ctx }/photo/view/${photo.type.id}">${photo.type.name }</a></p>
			<p><a class="image-popup-no-margins" href="${ctx }/photo/printImage/${photo.id}">
			<img title="${photo.remark }" class="img-polaroid" src="${ctx }/photo/printImage/${photo.id}"
                 style="width:300px;height:225px"/>
			</a></p>
        </p>
	</span>
</c:forEach>

<script type="text/javascript">
    $(document).ready(function () {
        $('.image-popup-no-margins').magnificPopup({
            type: 'image',
            closeOnContentClick: true,
            closeBtnInside: false,
            fixedContentPos: true,
            mainClass: 'mfp-no-margins mfp-with-zoom',
            image: {
                verticalFit: true
            },
            zoom: {
                enabled: true,
                duration: 300
            }
        });
    });
</script>