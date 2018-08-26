<%@ page contentType="text/html; charset=utf-8" %>
<jsp:include page="checkLogin.jsp"></jsp:include>
<html>
<link href="style/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
}
-->
</style>

<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<body onLoad="MM_preloadImages('images/hjpzO.png','images/gzlglO.png','images/YWPZO.png','images/JDPZO.png','images/JsglO.png')">
<table width="1020" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="381"><img src="images/Main_01.png" name="Image1" width="381" height="20" ></td>
    <td width="403" background="images/Main_02.png">&nbsp;</td>
    <td width="68"><img src="images/Main_03.png" width="68" height="20"></td>
    <td width="168"><img src="images/Main_04.png" width="168" height="20"></td>
  </tr>
  <tr>
    <td><img src="images/Main_05.png" width="381" height="80"></td>
    <td colspan="2" background="images/Main_06.png"><table width="440" border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><a href="admin/Config.jsp" target="Bottom" onMouseOver="MM_swapImage('Image2','','images/hjpzO.png',1)" onMouseOut="MM_swapImgRestore()"><img src="images/hjpzN.png" name="Image2" width="60" height="60" border="0" id="Image2" ></a></td>
        <td><a href="processdefine/ProcessDef.jsp" target="Bottom" onMouseOver="MM_swapImage('Image3','','images/gzlglO.png',1)" onMouseOut="MM_swapImgRestore()"><img src="images/gzlglN.png" name="Image3" width="60" height="60" border="0" id="Image3"></a></td>
        <td><a href="admin/BizData.jsp" target="Bottom" onMouseOver="MM_swapImage('Image4','','images/YWPZO.png',1)" onMouseOut="MM_swapImgRestore()"><img src="images/YWPZN.png" name="Image4" width="60" height="60" border="0" id="Image4"></a></td>
        <td><a href="processdefine/Node/Node_SelectWorkflow.jsp" target="Bottom" onMouseOver="MM_swapImage('Image5','','images/JDPZO.png',1)" onMouseOut="MM_swapImgRestore()"><img src="images/JDPZN.png" name="Image5" width="60" height="60" border="0" id="Image5"></a></td>
        <td><a href="admin/ListRole.jsp" target="Bottom" onMouseOver="MM_swapImage('Image6','','images/JsglO.png',1)" onMouseOut="MM_swapImgRestore()"><img src="images/JsglN.png" name="Image6" width="60" height="60" border="0" id="Image6"></a></td>
      </tr>
    </table></td>
    <td><img src="images/Main_08.png" width="168" height="80"></td>
  </tr>

</table>
</body>
</html>
