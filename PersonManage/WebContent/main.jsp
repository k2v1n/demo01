<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
		<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
		<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript">
			$(function(){
				var treeData=[{
					text:"人力资源管理系统",
					children:[{
						text:"部门管理",
						attributes:{
							url:"DepartmentManage.jsp"
						}
					},{
						text:"员工管理",
						attributes:{
							url:"EmployeeManage.jsp"
						}
					},{
						text:"薪酬管理",
						attributes:{
							url:"PayManage.jsp"
						}
					}]
				}];
				//实例化树型
				$("#tree").tree({
					data:treeData,
					lines:true,
					onClick:function(node){
						if(node.attributes){
							openTab(node.text,node.attributes.url);
						}
					}
				})
				//新增tab
				function openTab(text,url){
					if($("#tabs").tabs('exists',text)){
						$("#tabs").tabs('select',text);
					}else{
						var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%;' src="+url+"></iframe>";
						$("#tabs").tabs('add',{
							title:text,
							closable:true,
							content:content			
						});				
					}
				}
			});
		</script>
<title>人力资源管理系统主界面</title>
<%
	//权限验证
	if(session.getAttribute("currentUser")==null){
		response.sendRedirect("index.jsp");
		return;
	}
%>
</head>
<body class="easyui-layout">
        <div data-options="region:'north'" style="height:100px;background-color:#F9F7F8">
        	<img alt="" src="images/main.jpg" style="height:98px;width:700px;float:left;"  >
        	<div style="margin:60px 50px 20px;float:right;">当前登录用户：&nbsp;<font color="red">${currentUser.userName }</font></div>
        </div>
        <div data-options="region:'south'" style="height:30px;padding:5px;" align="center"  >版权所有&nbsp;&nbsp;<a href="http://www.java1234.com">www.Java1234.com</a></div>
        <div data-options="region:'west',split:true" title="导航菜单" style="width:200px;">
        	<ul id="tree" class="easyui-tree" ></ul>
        </div>
        <div data-options="region:'center'">
            <div class="easyui-tabs" fit="true" border="false" id="tabs" >
				<div title="首页" style="padding:10px">
					<div align="center" style="padding-top:100px;"><font color="red" size="7">欢迎使用</font></div>
				</div>
			</div>	
        </div>
</body>
</html>