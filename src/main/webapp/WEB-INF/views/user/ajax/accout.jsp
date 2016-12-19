<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>

<c:forEach var="accout" items="${accouts.content }">
    <p class="text-info">${accout.createUser.nickname }&nbsp;在&nbsp;
            <fmt:formatDate value="${accout.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
        更新了帐号
    <p style="margin-left:50px;" class="muted">
        用户名：<a href="${ctx }/accout?search_LIKE_username=${accout.username}">${accout.username }</a>
        密码：${accout.password }
    </p>
    </p>
</c:forEach>