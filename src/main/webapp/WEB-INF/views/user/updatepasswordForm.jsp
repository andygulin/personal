<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/menu.jsp" %>

<div class="container" style="margin-top: 100px">
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-${messageType }">
			<button data-dismiss="alert" class="close">×</button>
			${message}
		</div>
	</c:if>
	<form id="loginForm" action="${ctx}/user/updatepassword" method="post"
		class="form-horizontal">
		<fieldset>
			<legend>
				<small>修改密码</small>
			</legend>
			<div class="control-group">
				<label for="user_password" class="control-label">原密码:</label>
				<div class="controls">
					<input type="password" id="password1" name="password1"
						class="input-large required" minlength="4" />
				</div>
			</div>
			<div class="control-group">
				<label for="user_password" class="control-label">新密码:</label>
				<div class="controls">
					<input type="password" id="password2" name="password2"
						class="input-large required" minlength="4" />
				</div>
			</div>
			<div class="control-group">
				<label for="user_password" class="control-label">重复新密码:</label>
				<div class="controls">
					<input type="password" id="password22" name="password22"
						class="input-large required" minlength="4" equalTo="#password2" />
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="提交" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="返回" onclick="history.back()" />
			</div>
		</fieldset>
	</form>
</div>
<script>
	$(document).ready(function() {
		$("#password1").val("");
		$("#password2").val("");
		$("#password22").val("");
		$("#password1").focus();
		$("#loginForm").validate();
	});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>