<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/menu.jsp" %>
<div class="container marketing">
    <form id="contactsForm" action="${ctx}/contacts/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${contactsType.id}"/>
        <input type="hidden" name="isDefault" value="${contactsType.isDefault}"/>
        <fieldset>
            <legend>
                <small>联系人分类管理</small>
            </legend>
            <div class="control-group">
                <label for="article_title" class="control-label">分类名称:</label>
                <div class="controls">
                    <input type="text" id="name" name="name" value="${contactsType.name}" class="input-large required"/>
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
        $("#name").focus();
        $("#contactsForm").validate();
    });
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>