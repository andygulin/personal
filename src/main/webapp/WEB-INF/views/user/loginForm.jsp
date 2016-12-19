<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container" style="margin-top: 100px">
    <c:if test="${not empty message}">
        <div id="message" class="alert alert-${messageType }">
            <button data-dismiss="alert" class="close">×</button>
                ${message}
        </div>
    </c:if>
    <form id="loginForm" action="${ctx}/user/login" method="post"
          class="form-horizontal">
        <fieldset>
            <legend>
                <small>用户登录</small>
            </legend>
            <div class="control-group">
                <label for="user_username" class="control-label">用户名:</label>
                <div class="controls">
                    <input type="text" id="username" name="username"
                           class="input-large required" minlength="4"/>
                </div>
            </div>
            <div class="control-group">
                <label for="user_password" class="control-label">密码:</label>
                <div class="controls">
                    <input type="password" id="password" name="password"
                           class="input-large required" minlength="4"/>
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
        $("#username").val("");
        $("#password").val("");
        $("#username").focus();
        $("#loginForm").validate();
    });
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>