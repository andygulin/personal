<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/menu.jsp"%>
<div class="container marketing">
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>
			${message}
		</div>
	</c:if>
	<form id="photoForm" action="${ctx}/photo/${action}" method="post"
		class="form-horizontal" enctype="multipart/form-data">
		<fieldset>
			<legend>
				<small>照片管理</small>
			</legend>
			<div class="control-group">
				<label for="user_password" class="control-label">相册:</label>
				<div class="controls">
					<select id="photoType" name="id" style="width: 200px;">
						<c:forEach var="pt" items="${photoType }">
							<option value="${pt.id }" <c:if test="${(not empty param.id) and (param.id eq pt.id) }">selected="selected"</c:if>>${pt.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label for="user_password" class="control-label">图片上传:</label>
				<div class="controls">
					<div class="span10" style="margin-left:0px;">
					<c:forEach var="i" begin="1" end="9">
						<div class="fileupload fileupload-new span3" data-provides="fileupload" style="margin-left:0px;margin-right:20px;">
							<div class="fileupload-preview thumbnail"
								style="width: 200px; height: 150px;"></div>
							<div>
								<span class="btn btn-file"><span class="fileupload-new">选择图片</span><span class="fileupload-exists">修改图片</span><input
									type="file" name="image" /></span> <a href="#" class="btn fileupload-exists"
									data-dismiss="fileupload">删除图片</a>
							</div>
							<div style="padding:2px;"></div>
							<input type="text" name="remark"  value="" class="input-large" placeholder="输入图片描述..."/>
						</div>
					</c:forEach>
					</div>
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
		$("#photoForm").validate();
		$("#photoType").select2();
		$("#photoFrom input[name='image']").val("");
		$("#photoFrom input[name='remark']").val("");
	});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>