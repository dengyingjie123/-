/* 时间：2002/08/27
* 功能：验证表单函数
* 程序：张显达
* 修改：王琳
* 主要验证项目：
* CharType,表单数据类型 "C" 为字符串，"N" 为数字,"D"为日期类型。
* MaxLength(最大长度) 整型数字 0表示不限制最大长度
* Precision(精度) 整型数字 表示保留小数位的长度,0表示是整数
* DefiniteLengthMark(定长标志) 整型数字   0（定长不允许空）/1（不定长不可空）/2（不定长允许空）/3（定长允许空）
* Describe(表单描述) 比如：电话号码，用户姓名等等,必须写
 */
function checkFormItem(FormItemName,CharType,MaxLength,Precision,DefiniteLengthMark,Describe){
  try {
    var TValue;
    TValue=FormItemName.value;
    //得到文本框去掉头尾空格的值
    TValue=TValue.replace(/(^\s*)|(\s*$)/g, "");
    //去掉文本框头尾空格
    FormItemName.value=TValue;
    //允许空，只要文本框为空返回true
    if((DefiniteLengthMark==2||DefiniteLengthMark==3)&&(TValue.length==0)) {
      return(true);
    }
    //不允许空，只要文本框为空返回false
    if((DefiniteLengthMark==0||DefiniteLengthMark==1)&&(TValue.length==0)) {
      alert(Describe+"不允许为空！");
			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
			return(false);
    }
    //检查字符串(长度和定长)。
    if(CharType=="C") {
      if((DefiniteLengthMark==0||DefiniteLengthMark==3) && getStrLength(TValue)!=MaxLength) {
        alert(Describe+"规定长度是"+MaxLength+"，请检查！");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();	
        return(false);
      }
      if(MaxLength!=0&&getStrLength(TValue)>MaxLength && (DefiniteLengthMark==1||DefiniteLengthMark==2)) {
        alert(Describe+"超过最大长度，允许的最大长度是"+MaxLength+"！(汉字最大长度为"+MaxLength/2+")");
  			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
    }
    //检查数字类型(长度，精度，定长)
    if(CharType=="N") {
      //检查整型数字
      if(Precision==0&&!isInteger(TValue)) {
        alert(Describe+"项填写必须输入数字，请检查！");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision==0&&isInteger(TValue)&&MaxLength!=0&&(DefiniteLengthMark==0||DefiniteLengthMark==3)&&getStrLength(TValue)!=MaxLength) {
        alert(Describe+"项长度必须是"+MaxLength+"，请检查！");
  			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision==0&&isInteger(TValue)&&MaxLength!=0&&(DefiniteLengthMark==1||DefiniteLengthMark==2)&&getStrLength(TValue)>MaxLength) {

        alert(Describe+"项输入数字长度不能超过"+MaxLength+"，请检查！");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      //检查整型数字结束

      //检查实数
      if(Precision!=0&&!isFloat(TValue)) {
        alert(Describe+"项必须输入数字，请检查！");
  			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision!=0&&isFloat(TValue)&&MaxLength!=0&&(DefiniteLengthMark==0||DefiniteLengthMark==3)&&getStrLength(TValue.replace("." , ""))!=MaxLength) {

        alert(Describe+"项输入数字长度必须是"+MaxLength+"，请检查！");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision!=0&&isFloat(TValue)&&MaxLength!=0&&(DefiniteLengthMark==1||DefiniteLengthMark==2)&&getStrLength(TValue.replace("." , "")) > MaxLength) {

        alert(Describe+"项输入数字长度不能超过"+MaxLength+"，请检查！");
  			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision!=0&&isFloat(TValue)&&!checkPrecision(TValue,Precision)) {

        alert(Describe+"项输入数字应该保留"+Precision+"位小数，请检查！");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision!=0&&isFloat(TValue)&&checkPrecision(TValue,Precision)) {
        if((TValue.indexOf(".")!=-1 && TValue.indexOf(".")+Precision>MaxLength)||(TValue.indexOf(".")==-1 && TValue.length+Precision>MaxLength)) {
          alert(Describe+"项输入数字的整数部分最长不应超过"+(MaxLength-Precision)+"位，请检查！");
	  			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
          return false;
        }
      }

    }
    /*验证日期类型张显达书写于2003年1月5日*/
    if(CharType=="D") {
      //首先整理日期的格式
      var i;
      var strYear;
      var strMonth;
      var strDate;
      i=((TValue.indexOf("/")==-1) ? (TValue.indexOf("-")==-1 ? TValue.indexOf(".") : TValue.indexOf("-") ) : TValue.indexOf("/"));
      //年
      if( i != -1 ) {
        strYear=TValue.substring(0,i);
        if ( strYear.length != 4 ) {
		      alert(Describe+"格式不合法,请用4位数字表示年！");	
					if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
          return false;
        }
        TValue=TValue.substring(i+1,TValue.length);
        i=((TValue.indexOf("/")==-1) ? (TValue.indexOf("-")==-1 ? TValue.indexOf(".") : TValue.indexOf("-") ) : TValue.indexOf("/"));
        //月
        if( i != -1 ) {
          strMonth=TValue.substring(0,i);
          if( strMonth.length>2 ){
	          alert(Describe+"格式不合法,请用2位数字表示月！");
						if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
            return false;
          } else if ( strMonth.length > 0 ) {
            strMonth=("0" + strMonth ).substring( strMonth.length - 1 , strMonth.length + 1 );
          } else {
	          alert(Describe+"格式不合法");
						if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
            return false;
          }
          TValue=TValue.substring( i + 1 , TValue.length );
          //日
          strDate=TValue;
          if( strDate.length>2 ){
	          alert(Describe+"格式不合法,请用2位数字表示日！");	
						if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
            return false;
          } else if ( strDate.length > 0 ) {
            strDay=("0" + strDate ).substring( strDate.length -1 , strDate.length + 1 );
          } else {
            alert(Describe+"格式不合法");
						if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
            return false;
          }
          TValue = strYear + strMonth + strDay;

        } else {
					alert(Describe+"格式不合法");	
					if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
          return false;
        }

      }else{
        if( TValue.length != 8 ) {
          alert(Describe+"格式不合法");
					if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
          return false;
        }
      }

      //得到年月日的值
      strYear =  TValue.substr(0 , 4);
      strMonth = TValue.substr(4 , 2);
      strDate = TValue.substr(6 , 2);
      //给页面元素正确显示
      FormItemName.value = strYear + "/" + strMonth + "/" + strDate;
      //判断年
      var intYear;
      intYear=parseInt(getDelLeftZeroStr(strYear));
      if(intYear!=NaN&&intYear>1900&&intYear<2100){
        //return true;
      }else{
        alert(Describe+" 日期格式年不合法");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return false;
      }
      //判断月
      var intMonth;
      intMonth=parseInt(getDelLeftZeroStr(strMonth));
      if(intMonth!=NaN&&intMonth>=1&&intMonth<=12){
        //return true;
      }else{
        alert(Describe+" 日期格式月不合法");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return false;
      }
      var intDate=parseInt(getDelLeftZeroStr(strDate));
      var intMaxDateValue;
      switch(intMonth){
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        intMaxDateValue=31;
        break;
      case 4:
      case 6:
      case 9:
      case 11:
        intMaxDateValue=30;
        break;
      case 2:
        //润年的情况
        if((intYear%4==0&&intYear%100!=0)||(intYear%100==0&&intYear%400==0)){
          intMaxDateValue=29;
          break;
        }else{
          intMaxDateValue=28;
          break;
        }
      default:
        break;
      }
      if(intDate!=NaN&&intDate>=1&&intDate<=intMaxDateValue){
      //return true;
      }else{
        alert(Describe+" 日期格式日不合法");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return false;
      }
    }
    return(true);
  } catch( e ) {
    alert(Describe+"发生异常错误，请检查！");
    var blFlag=confirm("忽略" + Describe + "的错误吗？可能会造成数据不完整或者错误！" );
    if ( blFlag == true ) {
      return ( true );
    } else {
      return(false);
    }
  }
}


/* 得到字符串的长度  */
/*
function getStrLength(str)
{
 var sLength,i;
 sLength=0;
 for(i=0;i<str.length;i++)
 {
  if(Math.abs(str.charCodeAt(i))<=255)
   sLength=sLength+1;
  else
   sLength=sLength+2;
 }
 return(sLength);

}*/

/* 得到字符串的长度
* 修改：王琳
* 时间：2002/08/27
*/
function getStrLength(str)
{
  var winnt_chinese,sLength,i;
  var TestString="同方";
  if  (TestString.length==2){
    winnt_chinese =true;
  }else{
    winnt_chinese =false;
  }
  if (winnt_chinese){
    var l, t, c,kk;
    l = str.length;
    t = l;
    for (kk=0;kk<l;kk++)
    {
      c = str.charCodeAt(kk);
      if (c < 0){
        c = c + 65536;
      }
      if (c > 255){
        t = t + 1;
      }
    }
    sLength = t;
  }else{
    sLength = str.length;
  }
  return(sLength);
}


/* 验证整数  */
function isInteger(str)
{
  if(isNaN(str))
  {
    return(false);
  }
  else
  {
    if(str.search("[.*]")!=-1)
    {
      return(false);
    }
  }
  return(true);
}
/* 验证实型数字  */
function isFloat(str)
{
  //alert(isNaN(parseFloat(str)));
//  return(!isNaN(parseFloat(str)));
  return(!isNaN(str));
}
/* 验证精度  */
function checkPrecision(str,n) {
  try{
    var tureorfalse,i,PrecisionLength;
    PrecisionLength=0;
    if(!isNaN(parseFloat(str))) {
      for(i=0;i<str.length;i++) {
        if(str.charAt(i)==".")  {
        PrecisionLength=str.length-i-1;
        break;
      }
    }
    } else {
      return(false);
    }
    if(PrecisionLength<=n) {
      return(true);
    } else {
      return(false);
    }
  } catch( e ){
    return (false);
  }
}
/* 增加：黄绍进
* 时间：2002/09/16
*/

/* 检查两文本框不能同时为空
* 不同时为空为true 反之为false并提示
*/
function checkNotEmptySameTime(text1Name,text2Name,text1Describe,text2Describe){
  if (text1Name.value==""&&text2Name.value==""){
    alert(text1Describe+"和"+text2Describe+"不能同时为空！");
    return false;
  }
  else{return true;}
}
/* 检查两文本框同时只能填写一个*/
function checkWriteOneSameTime(text1Name,text2Name,text1Describe,text2Describe){
  if (text1Name.value!=""&&text2Name.value!=""){
    alert(text1Describe+"和"+text2Describe+"不能同时填写！");
    return false;
  }
  else{
    return true;
  }
}

/*得去掉开头所有的0的字符串，返回值为字符串*/
/*王琳 2003/4/10*/
function getDelLeftZeroStr(str){
  var strReturnvalue="";
  var i=0;
  var strTempvalue="";
  var blHaveCheckNotZero=false;

  if(isNaN(str)) {
    return("");
  }else{
    for (i=0;i<str.length;i++){
      //逐位取值
      strTempvalue=str.substring(i,i+1);
      if (strTempvalue!="0"){
            strReturnvalue=strReturnvalue+strTempvalue;
                blHaveCheckNotZero=true;
          }
          //只要检测到不是0的字符就切断判断，返回字符串
          if (blHaveCheckNotZero){
            strReturnvalue=strReturnvalue+str.substring(i+1,str.length);
            break;
          }
    }
  }
  return(strReturnvalue);
}

/*判断是不是月初第一天 程序张显达2004/06/07*/
function isMonthBegin(strDate,describe) {
  if(strDate.substring(6,8)!='01') {
    alert(describe+"不是本月第一天!");
    return false;
  }
  return true;
}

/*判断是不是月末最后一天 程序张显达2004/06/07*/
function isMonthEnd(strDate,describe) {
  var intYear = parseInt(strDate.substring(0,4));
  var strMonth = strDate.substring(4,6);
  if(strMonth.substring(0,1)=="0") strMonth = strDate.substring(5,6);
  var intMonth = parseInt(strMonth);
  var strDay = strDate.substring(6,8);
  var theDayMax="0";
  switch(intMonth) {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12: {theDayMax="31";break;}
    case 4:
    case 6:
    case 9:
    case 11: {theDayMax="30";break;}	
    case 2:
    if((intYear%4==0&&intYear%100!=0)||(intYear%100==0&&intYear%400==0)) {
       theDayMax = "29";
    } else {
       theDayMax = "28";
    }
    break;
  }
  if(strDay!=theDayMax) {
    
    alert(describe+"不是月末最后一天!");
    return false;	
  }
  return true;
  
}