<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/menu.jsp" %>
<div class="container marketing">
    <form id="accoutForm" action="${ctx}/accout/${action}" method="post"
          class="form-horizontal">
        <input type="hidden" name="id" value="${accout.id}"/>
        <fieldset>
            <legend>
                <small>帐号管理</small>
            </legend>
            <div class="control-group">
                <label for="accout_username" class="control-label">用户名:</label>
                <div class="controls">
                    <input type="text" id="username" name="username"
                           value="${accout.username}" class="input-large required"
                           minlength="3"/>
                </div>
            </div>
            <div class="control-group">
                <label for="user_password" class="control-label">密码:</label>
                <div class="controls">
                    <input type="text" id="username" name="password"
                           value="${accout.password}" class="input-large required"
                           minlength="3"/>
                </div>
            </div>
            <div class="control-group">
                <label for="description" class="control-label">帐号备注:</label>
                <div class="controls">
                    <textarea id="remark" name="remark" class="input-large">${accout.remark}</textarea>
                </div>
            </div>
            <div class="form-actions">
                <input id="submit_btn" class="btn btn-primary" type="submit"
                       value="提交"/>&nbsp; <input id="cancel_btn" class="btn"
                                                 type="button" value="返回" onclick="history.back()"/>
            </div>
        </fieldset>
    </form>
</div>
<script>
    $(document).ready(function () {
        $("#username").focus();
        $("#accoutForm").validate();
    });
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>