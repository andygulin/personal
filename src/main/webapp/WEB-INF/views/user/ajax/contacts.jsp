<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>

<c:forEach var="contact" items="${contacts.content }">
	<p class="text-info">${contact.createUser.nickname }&nbsp;在&nbsp;
	<fmt:formatDate value="${contact.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
	更新了联系人
		<p style="margin-left:50px;" class="muted">
			姓名：<a href="${ctx }/contacts/view/${article.id}">${contact.name }</a>
			手机号码：${contact.mobile }
			分类：<a href="${ctx }/contacts?search_EQ_type.id=${contact.type.id}">${contact.type.name }</a>
		</p>
	</p>
</c:forEach>