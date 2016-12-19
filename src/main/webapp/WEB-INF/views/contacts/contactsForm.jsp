<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/menu.jsp" %>
<div class="container marketing">
    <form id="contactsForm" action="${ctx}/contacts/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${contacts.id}"/>
        <fieldset>
            <legend>
                <small>联系人管理</small>
            </legend>
            <div class="control-group">
                <label for="contacts_name" class="control-label">姓名:</label>
                <div class="controls">
                    <input type="text" id="name" name="name" value="${contacts.name}" class="input-large required"/>
                </div>
            </div>
            <div class="control-group">
                <label for="contacts_sex" class="control-label">性别:</label>
                <div class="controls">
                    <select id="sex" name="sex" style="width:200px;">
                        <option value="1" <c:if test="${contacts.sex==1 }">selected="selected"</c:if>>男</option>
                        <option value="0" <c:if test="${contacts.sex==0 }">selected="selected"</c:if>>女</option>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label for="contacts_contactsType" class="control-label">联系人分类:</label>
                <div class="controls">
                    <select id="contactsType" name="type.id" style="width:200px;">
                        <c:forEach var="at" items="${contactsType }">
                            <option
                                    <c:if test="${(not empty contacts.type.id) and (contacts.type.id eq at.id)}">selected="selected"</c:if>
                                    value="${at.id }">${at.name }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label for="contacts_mobile" class="control-label">手机号码:</label>
                <div class="controls">
                    <input type="text" id="mobile" name="mobile" value="${contacts.mobile}"
                           class="input-large required"/>
                </div>
            </div>
            <div class="control-group">
                <label for="contacts_tel" class="control-label">固定电话:</label>
                <div class="controls">
                    <input type="text" id="tel" name="tel" value="${contacts.tel}" class="input-large required"/>
                </div>
            </div>
            <div class="control-group">
                <label for="contacts_address" class="control-label">联系地址:</label>
                <div class="controls">
                    <input type="text" id="address" name="address" value="${contacts.address}"
                           class="input-large required" style="width:500px;"/>
                </div>
            </div>
            <div class="control-group">
                <label for="contacts_remark" class="control-label">备注:</label>
                <div class="controls">
                    <input type="text" id="remark" name="remark" value="${contacts.remark}" class="input-large required"
                           style="width:500px;"/>
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
        $("#contactsType").select2();
        $("#sex").select2();
    });
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>