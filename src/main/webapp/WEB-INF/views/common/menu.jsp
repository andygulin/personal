<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse"
                    data-target=".nav-collapse">
                <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a class="brand" href="${ctx }/user/index">个人中心</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="dropdown"><a href="#" class="dropdown-toggle"
                                            data-toggle="dropdown">帐号管理<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="${ctx }/accout">查看帐号</a></li>
                            <li><a href="${ctx }/accout/create">添加帐号</a></li>
                        </ul>
                    </li>
                    <li class="dropdown"><a href="#" class="dropdown-toggle"
                                            data-toggle="dropdown">文档管理<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="${ctx }/article">查看文档</a></li>
                            <li><a href="${ctx }/article/create">添加文档</a></li>
                            <li><a href="${ctx }/article/createType">添加文档分类</a></li>
                        </ul>
                    </li>
                    <li class="dropdown"><a href="#" class="dropdown-toggle"
                                            data-toggle="dropdown">相册管理<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="${ctx }/photo">查看相册</a></li>
                            <li><a href="${ctx }/photo/create">添加相册</a></li>
                            <li><a href="${ctx }/photo/createType">添加相册分类</a></li>
                        </ul>
                    </li>
                    <li class="dropdown"><a href="#" class="dropdown-toggle"
                                            data-toggle="dropdown">联系人管理<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="${ctx }/contacts">查看联系人</a></li>
                            <li><a href="${ctx }/contacts/create">添加联系人</a></li>
                            <li><a href="${ctx }/contacts/createType">添加联系人分类</a></li>
                        </ul>
                    </li>
                    <li class="dropdown"><a href="#" class="dropdown-toggle"
                                            data-toggle="dropdown">用户管理<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="${ctx }/user/updatepassword">修改密码</a></li>
                            <li><a href="${ctx }/user/logout">退出</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<br/>
<br/>
<br/>