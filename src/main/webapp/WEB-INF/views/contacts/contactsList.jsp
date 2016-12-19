<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/menu.jsp" %>


<div class="container-fluid">
    <h1>联系人浏览</h1>
    <div class="row-fluid">
        <div class="span3">
            <div class="well sidebar-nav">
                <ul class="nav nav-list">
                    <li <c:if test="${empty param['search_EQ_type.id'] }">class="active"</c:if>><a
                            href="${ctx }/contacts/">所有分类</a></li>
                    <c:forEach var="contactsType" items="${contactsTypes }">
                        <li style="width:92%;float:left;"
                            <c:if test="${param['search_EQ_type.id'] eq contactsType.id }">class="active"</c:if>>
                            <a href="${ctx }/contacts?search_EQ_type.id=${contactsType.id}">${contactsType.name}(${contactsType.count })</a>
                        </li>
                        <li>
                            <c:if test="${!contactsType.isDefault }">
                                <a href="${ctx }/contacts/updateType/${contactsType.id}" title="修改分类"><i
                                        class="icon-pencil"></i></a>
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
            <c:if test="${not empty message}">
                <div id="message" class="alert alert-success">
                    <button data-dismiss="alert" class="close">×</button>
                        ${message}
                </div>
            </c:if>

            <form class="navbar-search pull-left" action="">
                <input type="text" name="search_LIKE_name" class="search-query"
                       placeholder="搜索联系人姓名" value="${param.search_LIKE_name}">
            </form>
            <tags:sort/>

            <table id="contentTable"
                   class="table table-striped table-hover table-bordered">
                <thead>
                <td>姓名</td>
                <td>手机号码</td>
                <td>分类</td>
                <td>添加时间</td>
                <td>创建人</td>
                <td>管理</td>
                </thead>
                <tbody>
                <c:forEach var="contact" items="${contacts.content }">
                    <tr>
                        <td><a href="${ctx }/contacts/view/${contact.id}">${contact.name }</a></td>
                        <td>${contact.mobile }</td>
                        <td><a href="${ctx }/contacts?search_EQ_type.id=${contact.type.id}">${contact.type.name }</a>
                        </td>
                        <td><fmt:formatDate value="${contact.createDate}"
                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${contact.createUser.nickname }</td>
                        <td>
                            <a href="${ctx}/contacts/update/${contact.id}" title="修改""><i class="icon-pencil"></i></a>
                            <a href="${ctx}/contacts/delete/${contact.id}" onclick="return confirm('确认删除?');"
                               title="删除"><i class="icon-trash"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <tags:pagination paginationSize="5" page="${contacts }"/>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>