//设置个人信息栏的显示（未登录显示登陆入口，已登录则显示用户信息）
//获取model的用户名信息检查登录状态
const firstPart="loginOrUserName";
const secondPart="registerOrLogouot";
function setUserState(username)
{
  if(username == '')
  {
    document.getElementById(firstPart).innerHTML="登陆";
    document.getElementById(firstPart).href="/login";
    document.getElementById(secondPart).innerHTML="注册";
    document.getElementById(secondPart).href="/register";
  }
  else
  {
    document.getElementById(firstPart).innerHTML=username;
    document.getElementById(firstPart).href="/user/"+username;
    document.getElementById(secondPart).innerHTML="登出";
    document.getElementById(secondPart).href="/logout";
  }
}
