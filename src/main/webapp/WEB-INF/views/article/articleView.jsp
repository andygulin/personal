<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/menu.jsp"%>


<div class="container-fluid">
	<h1>文章浏览</h1>
	<div class="row-fluid">
		<div class="span3">
			<div class="well sidebar-nav">
				<ul class="nav nav-list">
					<li <c:if test="${empty param['search_EQ_type.id'] }">class="active"</c:if>><a href="${ctx }/article/">所有分类</a></li>
					<c:forEach var="articleType" items="${articleTypes }">
						<li style="width:92%;float:left;" <c:if test="${param['search_EQ_type.id'] eq articleType.id }">class="active"</c:if>>
						<a href="${ctx }/article?search_EQ_type.id=${articleType.id}">${articleType.name}(${articleType.count })</a>
						</li>
						<li>
						<c:if test="${!articleType.isDefault }">
						<a href="${ctx }/article/updateType/${articleType.id}" title="修改分类"><i class="icon-pencil"></i></a>
						</c:if>
						</li>
						<c:if test="${articleType.isDefault }">
						<a title="不能修改默认分类"><i class="icon-pencil"></i></a>
						</c:if>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="span8">
			
			<h2>标题：${article.title }</h2>
			<h4>分类：${article.type.name }</h4>
			<h4>正文</h4>
			<p style="line-height: 25px;">${article.content }</p>
			<c:if test="${not empty article.image }">
				<p><img src="${ctx }/article/printImage/${article.id}" /></p>
			</c:if>
			<h5>创建时间：<fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></h5>
			<h5>创建人：${article.createUser.nickname }</h5>
			<p class="text-right"><a href="javascript:;" onclick="window.history.go(-1);">返回上一页</a></p>
			
		</div>
	</div>
	<%@ include file="/WEB-INF/views/common/footer.jsp"%>