<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/menu.jsp" %>

<div class="container">
    <h1>${title }</h1>
    <p class="text-right">
        相册切换：
        <select id="selectPhotoType" style="width:200px;">
            <c:forEach var="pt" items="${photoTypes }">
                <option
                        <c:if test="${pt.id eq id }">selected="selected"</c:if>
                        value="${pt.id }">${pt.name }(${pt.count })
                </option>
            </c:forEach>
        </select>
    </p>
    <div class="row">
        <c:forEach var="pt" items="${photos.content }">
			<span id="photo_${pt.id }" class="span4">
				<p>
				<a class="image-popup-no-margins" href="${ctx }/photo/printImage/${pt.id}">
				<img title="${pt.remark }" class="img-polaroid" src="${ctx }/photo/printImage/${pt.id}"
                     style="width:300px;height:225px"/></a></p>
				<p>创建时间：<fmt:formatDate value="${pt.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
				<p>创建人：${pt.createUser.nickname }</p>
				<p class="text-right">
					<c:if test="${photoType.cover.id eq pt.id }"><a title="当前封面"><i class="icon-ok"></i></a></c:if>
					<c:if test="${photoType.cover.id ne pt.id }">
                        <a href="javascript:;" onclick="setCover('${pt.id}')" title="设为封面"><i class="icon-home"></i></a>
                        <a href="javascript:;" onclick="delPhoto('${pt.id}');" title="删除"><i class="icon-trash"></i></a>
                    </c:if>
				</p>
			</span>
        </c:forEach>
    </div>
    <tags:pagination paginationSize="5" page="${photos }"/>
</div>
<script type="text/javascript">
    function delPhoto(id) {
        if (confirm("确认删除?")) {
            $.post("${ctx}/photo/delete", {
                id: id
            }, function (data) {
                $("#photo_" + id).remove();
            });
        }
    }
    function setCover(id) {
        if (confirm("确认更改?")) {
            $.post("${ctx}/photo/setCover", {
                id: id,
                tid: '${photoType.id}'
            }, function (data) {
                window.location.reload();
            });
        }
    }
    $(document).ready(function () {
        $("#selectPhotoType").select2();
        $("#selectPhotoType").change(function () {
            window.location.href = "${ctx}/photo/view/" + $(this).val();
        });

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
<%@ include file="/WEB-INF/views/common/footer.jsp" %>