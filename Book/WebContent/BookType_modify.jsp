<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.domain.BookType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    BookType bookType = (BookType)request.getAttribute("bookType");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改图书类型</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    return true; 
}
 </script>
</HEAD>
<BODY>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="BookType/BookType_ModifyBookType.action" method="post" onsubmit="return checkForm();" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' bgcolor='#CCCCCC' class="tablewidth">
  <tr>
    <td width=30%>图书类别:</td>
    <td width=70%><input id="bookType.bookTypeId" name="bookType.bookTypeId" type="text" value="<%=bookType.getBookTypeId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>类别名称:</td>
    <td width=70%><input id="bookType.bookTypeName" name="bookType.bookTypeName" type="text" size="18" value='<%=bookType.getBookTypeName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>可借阅天数:</td>
    <td width=70%><input id="bookType.days" name="bookType.days" type="text" size="8" value='<%=bookType.getDays() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
