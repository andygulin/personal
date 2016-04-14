<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/menu.jsp"%>


<div class="container">
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>
			${message}
		</div>
	</c:if>
	<h1>相册浏览</h1>
	<form class="navbar-search pull-left" action="">
		<input type="text" name="search_LIKE_name" class="search-query"
			placeholder="搜索相册名称" value="${param.search_LIKE_name}">
	</form>
	<tags:sort />
</div>

<div class="container">
	<div class="row">
		<c:forEach var="photoType" items="${photoTypes.content }">
			<div class="span3" style="padding-top: 20px; padding-bottom: 20px;">
				<c:if test="${photoType.count>0 }">
					<a href="${ctx }/photo/view/${photoType.id}" title="${photoType.name }"><img
						class="img-polaroid" src="${ctx }/photo/printImage/${photoType.cover.id}" style="width:213px;height:160px" /></a>
				</c:if>
				<c:if test="${photoType.count==0 }">
					<a href="${ctx }/photo/create?id=${photoType.id}" title="${photoType.name }"><img
						class="img-polaroid" src="${ctx }/static/nophoto.jpg" /></a>
				</c:if>
				<p class="text-center" style="margin-top:5px;">
				${photoType.name }(${photoType.count })&nbsp;
				<c:if test="${photoType.count>0 }"><a href="${ctx }/photo/download/${photoType.id}" title="打包下载该相册"><i class="icon-share"></i></a></c:if>
				<c:if test="${!photoType.isDefault }">
				<a href="${ctx }/photo/updateType/${photoType.id}" title="修改相册名称"><i class="icon-pencil"></i></a>
				</c:if>
				<c:if test="${photoType.count>0 }"><a href="javascript:;" onclick="deleteAll('${photoType.id}');" title="清空该相册"><i class="icon-remove"></i></a></c:if>
				</p>
			</div>
		</c:forEach>
	</div>
	<tags:pagination paginationSize="5" page="${photoTypes }" />
</div>
<script type="text/javascript">
function deleteAll(id){
	if(confirm("确认清空?")){
		$.post("${ctx}/photo/removeAll/"+id,{d:new Date().getTime()},function(data){window.location.reload();});
	}
}
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>