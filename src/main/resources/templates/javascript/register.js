//发送获取验证码的请求
function requestForValidationCode()
{
  let url="http://"+window.location.host+"/validation?email=";
  let emailbox=document.getElementsByName("email")[0];
  let requestButton=document.getElementById("requestValidation");
  let validationButtonHint=document.getElementById("validationButtonHint");
  url+=emailbox.value;
  //发送get请求
  let httpRequest = new XMLHttpRequest();
  httpRequest.open('GET',url,true);
  httpRequest.send();
  requestButton.disabled=true;
  validationButtonHint.style="display:block";
  setInterval( "releaseValidationRequest()", 60000 );
}
function releaseValidationRequest()
{
  let validationButtonHint=document.getElementById("validationButtonHint");
  let requestButton=document.getElementById("requestValidation");
  requestButton.disabled=false;
  validationButtonHint.style="display:none";
}
