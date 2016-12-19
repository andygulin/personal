<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>

<c:forEach var="article" items="${articles.content }">
    <p class="text-info">${article.createUser.nickname }&nbsp;在&nbsp;
            <fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
        更新了文章
    <p style="margin-left:50px;" class="muted">
        标题：<a href="${ctx }/article/view/${article.id}">${article.title }</a>
        分类：<a href="${ctx }/article?search_EQ_type.id=${article.type.id}">${article.type.name }</a>
    </p>
    </p>
</c:forEach>