<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/menu.jsp" %>
<div class="container marketing">
    <form id="articleForm" action="${ctx}/article/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${article.id}"/>
        <fieldset>
            <legend>
                <small>文章管理</small>
            </legend>
            <div class="control-group">
                <label for="article_title" class="control-label">文章标题:</label>
                <div class="controls">
                    <input type="text" id="title" name="title" value="${article.title}" class="input-large required"
                           style="width:500px;"/>
                </div>
            </div>
            <div class="control-group">
                <label for="article_articleType" class="control-label">文章分类:</label>
                <div class="controls">
                    <select id="articleType" name="type.id" style="width:200px;">
                        <c:forEach var="at" items="${articleType }">
                            <option
                                    <c:if test="${(not empty article.type.id) and (article.type.id eq at.id)}">selected="selected"</c:if>
                                    value="${at.id }">${at.name }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label for="description" class="control-label">正文内容:</label>
                <div class="controls">
                    <textarea id="content" name="content" class="ckeditor">${article.content}</textarea>
                </div>
            </div>
            <div class="form-actions">
                <input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;
                <input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
            </div>
        </fieldset>
    </form>
</div>
<script>
    $(document).ready(function () {
        $("#title").focus();
        $("#articleForm").validate();
        $("#articleType").select2();
    });
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>