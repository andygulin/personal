<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/menu.jsp"%>


<div class="container-fluid">
	<h1>联系人浏览</h1>
	<div class="row-fluid">
		<div class="span3">
			<div class="well sidebar-nav">
				<ul class="nav nav-list">
					<li <c:if test="${empty param['search_EQ_type.id'] }">class="active"</c:if>><a href="${ctx }/contacts/">所有分类</a></li>
					<c:forEach var="contactsType" items="${contactsTypes }">
						<li style="width:92%;float:left;" <c:if test="${param['search_EQ_type.id'] eq contactsType.id }">class="active"</c:if>>
						<a href="${ctx }/contacts?search_EQ_type.id=${contactsType.id}">${contactsType.name}(${contactsType.count })</a>
						</li>
						<li>
						<c:if test="${!contactsType.isDefault }">
						<a href="${ctx }/contacts/updateType/${contactsType.id}" title="修改分类"><i class="icon-pencil"></i></a>
						</c:if>
						</li>
						<c:if test="${contactsType.isDefault }">
						<a title="不能修改默认分类"><i class="icon-pencil"></i></a>
						</c:if>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="span8">
			<h2>姓名：${contacts.name }</h2>
			<h4>性别：<c:if test="${contacts.sex==1 }">男</c:if><c:if test="${contacts.sex==0 }">女</c:if></h4>
			<h4>手机号码：${contacts.mobile }</h4>
			<h4>固定电话：${contacts.tel }</h4>
			<h4>联系地址：${contacts.address }</h4>
			<h4>备注：${contacts.remark }</h4>
			<h5>创建时间：<fmt:formatDate value="${contacts.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></h5>
			<h5>创建人：${contacts.createUser.nickname }</h5>
			<p class="text-right"><a href="javascript:;" onclick="window.history.go(-1);">返回上一页</a></p>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/common/footer.jsp"%>