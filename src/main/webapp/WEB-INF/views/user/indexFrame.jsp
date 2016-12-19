<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/menu.jsp" %>

<div class="container">
    <h1>最新更新</h1>
    <div class="row"><h3>最新更新的帐号信息</h3></div>
    <div class="row" id="rowAccout"><img src="${ctx }/static/loading.gif"/></div>
    <p class="text-right"><a href="${ctx }/accout">查看更多帐号信息...</a></p>

    <div class="row"><h3>最新更新的文章信息</h3></div>
    <div class="row" id="rowArticle"><img src="${ctx }/static/loading.gif"/></div>
    <p class="text-right"><a href="${ctx }/article">查看更多文章信息...</a></p>

    <div class="row"><h3>最新更新的联系人信息</h3></div>
    <div class="row" id="rowContacts"><img src="${ctx }/static/loading.gif"/></div>
    <p class="text-right"><a href="${ctx }/contacts">查看更多联系人信息...</a></p>

    <div class="row"><h3>最新更新的图片信息</h3></div>
    <div class="row" id="rowPhoto"><img src="${ctx }/static/loading.gif"/></div>
    <p class="text-right"><a href="${ctx }/photo">查看更多图片信息...</a></p>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $.get("${ctx}/user/ajaxArticle", {}, function (data) {
            $("#rowArticle").html(data);
        });
        $.get("${ctx}/user/ajaxAccout", {}, function (data) {
            $("#rowAccout").html(data);
        });
        $.get("${ctx}/user/ajaxPhoto", {}, function (data) {
            $("#rowPhoto").html(data);
        });
        $.get("${ctx}/user/ajaxContacts", {}, function (data) {
            $("#rowContacts").html(data);
        });
    });
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>