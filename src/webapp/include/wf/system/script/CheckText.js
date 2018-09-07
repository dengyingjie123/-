/* ʱ�䣺2002/08/27
* ���ܣ���֤������
* �������Դ�
* �޸ģ�����
* ��Ҫ��֤��Ŀ��
* CharType,���������� "C" Ϊ�ַ�����"N" Ϊ����,"D"Ϊ�������͡�
* MaxLength(��󳤶�) �������� 0��ʾ��������󳤶�
* Precision(����) �������� ��ʾ����С��λ�ĳ���,0��ʾ������
* DefiniteLengthMark(������־) ��������   0������������գ�/1�����������ɿգ�/2������������գ�/3����������գ�
* Describe(������) ���磺�绰���룬�û������ȵ�,����д
 */
function checkFormItem(FormItemName,CharType,MaxLength,Precision,DefiniteLengthMark,Describe){
  try {
    var TValue;
    TValue=FormItemName.value;
    //�õ��ı���ȥ��ͷβ�ո��ֵ
    TValue=TValue.replace(/(^\s*)|(\s*$)/g, "");
    //ȥ���ı���ͷβ�ո�
    FormItemName.value=TValue;
    //����գ�ֻҪ�ı���Ϊ�շ���true
    if((DefiniteLengthMark==2||DefiniteLengthMark==3)&&(TValue.length==0)) {
      return(true);
    }
    //������գ�ֻҪ�ı���Ϊ�շ���false
    if((DefiniteLengthMark==0||DefiniteLengthMark==1)&&(TValue.length==0)) {
      alert(Describe+"������Ϊ�գ�");
			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
			return(false);
    }
    //����ַ���(���ȺͶ���)��
    if(CharType=="C") {
      if((DefiniteLengthMark==0||DefiniteLengthMark==3) && getStrLength(TValue)!=MaxLength) {
        alert(Describe+"�涨������"+MaxLength+"�����飡");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();	
        return(false);
      }
      if(MaxLength!=0&&getStrLength(TValue)>MaxLength && (DefiniteLengthMark==1||DefiniteLengthMark==2)) {
        alert(Describe+"������󳤶ȣ��������󳤶���"+MaxLength+"��(������󳤶�Ϊ"+MaxLength/2+")");
  			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
    }
    //�����������(���ȣ����ȣ�����)
    if(CharType=="N") {
      //�����������
      if(Precision==0&&!isInteger(TValue)) {
        alert(Describe+"����д�����������֣����飡");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision==0&&isInteger(TValue)&&MaxLength!=0&&(DefiniteLengthMark==0||DefiniteLengthMark==3)&&getStrLength(TValue)!=MaxLength) {
        alert(Describe+"��ȱ�����"+MaxLength+"�����飡");
  			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision==0&&isInteger(TValue)&&MaxLength!=0&&(DefiniteLengthMark==1||DefiniteLengthMark==2)&&getStrLength(TValue)>MaxLength) {

        alert(Describe+"���������ֳ��Ȳ��ܳ���"+MaxLength+"�����飡");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      //����������ֽ���

      //���ʵ��
      if(Precision!=0&&!isFloat(TValue)) {
        alert(Describe+"������������֣����飡");
  			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision!=0&&isFloat(TValue)&&MaxLength!=0&&(DefiniteLengthMark==0||DefiniteLengthMark==3)&&getStrLength(TValue.replace("." , ""))!=MaxLength) {

        alert(Describe+"���������ֳ��ȱ�����"+MaxLength+"�����飡");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision!=0&&isFloat(TValue)&&MaxLength!=0&&(DefiniteLengthMark==1||DefiniteLengthMark==2)&&getStrLength(TValue.replace("." , "")) > MaxLength) {

        alert(Describe+"���������ֳ��Ȳ��ܳ���"+MaxLength+"�����飡");
  			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision!=0&&isFloat(TValue)&&!checkPrecision(TValue,Precision)) {

        alert(Describe+"����������Ӧ�ñ���"+Precision+"λС�������飡");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return(false);
      }
      if(Precision!=0&&isFloat(TValue)&&checkPrecision(TValue,Precision)) {
        if((TValue.indexOf(".")!=-1 && TValue.indexOf(".")+Precision>MaxLength)||(TValue.indexOf(".")==-1 && TValue.length+Precision>MaxLength)) {
          alert(Describe+"���������ֵ������������Ӧ����"+(MaxLength-Precision)+"λ�����飡");
	  			if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
          return false;
        }
      }

    }
    /*��֤�����������Դ���д��2003��1��5��*/
    if(CharType=="D") {
      //�����������ڵĸ�ʽ
      var i;
      var strYear;
      var strMonth;
      var strDate;
      i=((TValue.indexOf("/")==-1) ? (TValue.indexOf("-")==-1 ? TValue.indexOf(".") : TValue.indexOf("-") ) : TValue.indexOf("/"));
      //��
      if( i != -1 ) {
        strYear=TValue.substring(0,i);
        if ( strYear.length != 4 ) {
		      alert(Describe+"��ʽ���Ϸ�,����4λ���ֱ�ʾ�꣡");	
					if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
          return false;
        }
        TValue=TValue.substring(i+1,TValue.length);
        i=((TValue.indexOf("/")==-1) ? (TValue.indexOf("-")==-1 ? TValue.indexOf(".") : TValue.indexOf("-") ) : TValue.indexOf("/"));
        //��
        if( i != -1 ) {
          strMonth=TValue.substring(0,i);
          if( strMonth.length>2 ){
	          alert(Describe+"��ʽ���Ϸ�,����2λ���ֱ�ʾ�£�");
						if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
            return false;
          } else if ( strMonth.length > 0 ) {
            strMonth=("0" + strMonth ).substring( strMonth.length - 1 , strMonth.length + 1 );
          } else {
	          alert(Describe+"��ʽ���Ϸ�");
						if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
            return false;
          }
          TValue=TValue.substring( i + 1 , TValue.length );
          //��
          strDate=TValue;
          if( strDate.length>2 ){
	          alert(Describe+"��ʽ���Ϸ�,����2λ���ֱ�ʾ�գ�");	
						if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
            return false;
          } else if ( strDate.length > 0 ) {
            strDay=("0" + strDate ).substring( strDate.length -1 , strDate.length + 1 );
          } else {
            alert(Describe+"��ʽ���Ϸ�");
						if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
            return false;
          }
          TValue = strYear + strMonth + strDay;

        } else {
					alert(Describe+"��ʽ���Ϸ�");	
					if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
          return false;
        }

      }else{
        if( TValue.length != 8 ) {
          alert(Describe+"��ʽ���Ϸ�");
					if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
          return false;
        }
      }

      //�õ������յ�ֵ
      strYear =  TValue.substr(0 , 4);
      strMonth = TValue.substr(4 , 2);
      strDate = TValue.substr(6 , 2);
      //��ҳ��Ԫ����ȷ��ʾ
      FormItemName.value = strYear + "/" + strMonth + "/" + strDate;
      //�ж���
      var intYear;
      intYear=parseInt(getDelLeftZeroStr(strYear));
      if(intYear!=NaN&&intYear>1900&&intYear<2100){
        //return true;
      }else{
        alert(Describe+" ���ڸ�ʽ�겻�Ϸ�");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return false;
      }
      //�ж���
      var intMonth;
      intMonth=parseInt(getDelLeftZeroStr(strMonth));
      if(intMonth!=NaN&&intMonth>=1&&intMonth<=12){
        //return true;
      }else{
        alert(Describe+" ���ڸ�ʽ�²��Ϸ�");
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
        //��������
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
        alert(Describe+" ���ڸ�ʽ�ղ��Ϸ�");
				if((FormItemName.type!="hidden")&&(!FormItemName.readOnly)&&(!FormItemName.disabled))FormItemName.focus();
        return false;
      }
    }
    return(true);
  } catch( e ) {
    alert(Describe+"�����쳣�������飡");
    var blFlag=confirm("����" + Describe + "�Ĵ����𣿿��ܻ�������ݲ��������ߴ���" );
    if ( blFlag == true ) {
      return ( true );
    } else {
      return(false);
    }
  }
}


/* �õ��ַ����ĳ���  */
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

/* �õ��ַ����ĳ���
* �޸ģ�����
* ʱ�䣺2002/08/27
*/
function getStrLength(str)
{
  var winnt_chinese,sLength,i;
  var TestString="ͬ��";
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


/* ��֤����  */
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
/* ��֤ʵ������  */
function isFloat(str)
{
  //alert(isNaN(parseFloat(str)));
//  return(!isNaN(parseFloat(str)));
  return(!isNaN(str));
}
/* ��֤����  */
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
/* ���ӣ����ܽ�
* ʱ�䣺2002/09/16
*/

/* ������ı�����ͬʱΪ��
* ��ͬʱΪ��Ϊtrue ��֮Ϊfalse����ʾ
*/
function checkNotEmptySameTime(text1Name,text2Name,text1Describe,text2Describe){
  if (text1Name.value==""&&text2Name.value==""){
    alert(text1Describe+"��"+text2Describe+"����ͬʱΪ�գ�");
    return false;
  }
  else{return true;}
}
/* ������ı���ͬʱֻ����дһ��*/
function checkWriteOneSameTime(text1Name,text2Name,text1Describe,text2Describe){
  if (text1Name.value!=""&&text2Name.value!=""){
    alert(text1Describe+"��"+text2Describe+"����ͬʱ��д��");
    return false;
  }
  else{
    return true;
  }
}

/*��ȥ����ͷ���е�0���ַ���������ֵΪ�ַ���*/
/*���� 2003/4/10*/
function getDelLeftZeroStr(str){
  var strReturnvalue="";
  var i=0;
  var strTempvalue="";
  var blHaveCheckNotZero=false;

  if(isNaN(str)) {
    return("");
  }else{
    for (i=0;i<str.length;i++){
      //��λȡֵ
      strTempvalue=str.substring(i,i+1);
      if (strTempvalue!="0"){
            strReturnvalue=strReturnvalue+strTempvalue;
                blHaveCheckNotZero=true;
          }
          //ֻҪ��⵽����0���ַ����ж��жϣ������ַ���
          if (blHaveCheckNotZero){
            strReturnvalue=strReturnvalue+str.substring(i+1,str.length);
            break;
          }
    }
  }
  return(strReturnvalue);
}

/*�ж��ǲ����³���һ�� �������Դ�2004/06/07*/
function isMonthBegin(strDate,describe) {
  if(strDate.substring(6,8)!='01') {
    alert(describe+"���Ǳ��µ�һ��!");
    return false;
  }
  return true;
}

/*�ж��ǲ�����ĩ���һ�� �������Դ�2004/06/07*/
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
    
    alert(describe+"������ĩ���һ��!");
    return false;	
  }
  return true;
  
}