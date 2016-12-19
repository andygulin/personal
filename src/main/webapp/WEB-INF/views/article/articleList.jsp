<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/menu.jsp" %>


<div class="container-fluid">
    <h1>文章浏览</h1>
    <div class="row-fluid">
        <div class="span3">
            <div class="well sidebar-nav">
                <ul class="nav nav-list">
                    <li <c:if test="${empty param['search_EQ_type.id'] }">class="active"</c:if>><a
                            href="${ctx }/article/">所有分类</a></li>
                    <c:forEach var="articleType" items="${articleTypes }">
                        <li style="width:92%;float:left;"
                            <c:if test="${param['search_EQ_type.id'] eq articleType.id }">class="active"</c:if>>
                            <a href="${ctx }/article?search_EQ_type.id=${articleType.id}">${articleType.name}(${articleType.count })</a>
                        </li>
                        <li>
                            <c:if test="${!articleType.isDefault }">
                                <a href="${ctx }/article/updateType/${articleType.id}" title="修改分类"><i
                                        class="icon-pencil"></i></a>
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
            <c:if test="${not empty message}">
                <div id="message" class="alert alert-success">
                    <button data-dismiss="alert" class="close">×</button>
                        ${message}
                </div>
            </c:if>

            <form class="navbar-search pull-left" action="">
                <input type="text" name="search_LIKE_title" class="search-query"
                       placeholder="搜索文章标题" value="${param.search_LIKE_title}">
            </form>
            <tags:sort/>

            <table id="contentTable"
                   class="table table-striped table-hover table-bordered">
                <thead>
                <td>标题</td>
                <td>分类</td>
                <td>是否有图</td>
                <td>添加时间</td>
                <td>创建人</td>
                <td>管理</td>
                </thead>
                <tbody>
                <c:forEach var="article" items="${articles.content }">
                    <tr>
                        <td><a href="${ctx }/article/view/${article.id}">${article.title }</a></td>
                        <td><a href="${ctx }/article?search_EQ_type.id=${article.type.id}">${article.type.name }</a>
                        </td>
                        <td><c:if test="${empty article.srcName }"><span style="color:#CCC;">无图说个JB</span></c:if><c:if
                                test="${not empty article.srcName }"><b>有图有真相</b></c:if></td>
                        <td><fmt:formatDate value="${article.createDate}"
                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${article.createUser.nickname }</td>
                        <td>
                            <a href="${ctx}/article/update/${article.id}" title="修改""><i class="icon-pencil"></i></a>
                            <a href="${ctx}/article/delete/${article.id}" onclick="return confirm('确认删除?');" title="删除"><i
                                    class="icon-trash"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <tags:pagination paginationSize="5" page="${articles }"/>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>