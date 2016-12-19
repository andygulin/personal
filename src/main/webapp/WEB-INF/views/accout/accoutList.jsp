<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/menu.jsp" %>


<div class="container">
    <h1>帐号浏览</h1>
    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">
            <button data-dismiss="alert" class="close">×</button>
                ${message}
        </div>
    </c:if>

    <form class="navbar-search pull-left" action="">
        <input type="text" name="search_LIKE_username" class="search-query"
               placeholder="搜索用户名" value="${param.search_LIKE_username}">
    </form>
    <tags:sort/>

    <table id="contentTable"
           class="table table-striped table-hover table-bordered">
        <thead>
        <td>用户名</td>
        <td>密码</td>
        <td>备注</td>
        <td>添加时间</td>
        <td>创建人</td>
        <td>管理</td>
        </thead>
        <tbody>
        <c:forEach var="accout" items="${accouts.content }">
            <tr>
                <td>${accout.username }</td>
                <td>${accout.password }</td>
                <td>${accout.remark }</td>
                <td><fmt:formatDate value="${accout.createDate}"
                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${accout.createUser.nickname }</td>
                <td>
                    <a href="${ctx}/accout/update/${accout.id}" title="修改"><i class="icon-pencil"></i></a>
                    <a href="${ctx}/accout/delete/${accout.id}" onclick="return confirm('确认删除?');" title="删除"><i
                            class="icon-trash"></i></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<tags:pagination paginationSize="5" page="${accouts }"/>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>