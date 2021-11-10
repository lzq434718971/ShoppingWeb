function getUrlParam(name)
{
    let reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    let r=window.location.search.substr(1).match(reg);
    if(r!=null)
    {
      return unescape(r[2]);
    }else
    {
      return null;
    }
}

/**
 * 返回一个对象，对象中包含所有get参数
 */
function getAllUrlParam()
{
    let url=location.search;
    let params=new Object();
    if(url.indexOf("?")!=-1)
    {
        let str=url.substr(1);
        strs=str.split("&");
        for(let i=0;i<strs.length;i++)
        {
            let keyvalue=strs[i].split("=");
            params[keyvalue[0]]=decodeURI(keyvalue[1]);
        }
    }
    return params;
}

/**
 * 将储存key-value的对象转换为get参数
 * @param {*} map 
 */
function mapToGetParam(map)
{
    let param="?";
    for(let key in map)
    {
        param+=key+"="+map[key]+"&";
    }
    param=param.replace(/([\w\W]+)\&$/,"$1");
    return param;
}

function judgeNumber(code) {
    if (code >= 48 & code <= 57) {
        return true;
    }
    else {
        return false;
    }
}
function judgeChar(code) {
    if ((code >= 65 & code <= 90) || (code >= 97 & code <= 122)) {
        return true;
    }
    else {
        return false;
    }
}

function setHomeImage()
{
    //设置点击图片返回首页
    let img=document.getElementById("homeImg");
    img.onclick=function()
    {
      let url="http://"+window.location.host;
      console.log(url);
      window.location.href=url;
    }
    img.style="cursor:pointer";
}

/**
 * 获取服务器上指定url处的websocket
 * @param {String} url 以“/”开头
 */
function createSocket(url)
{
    let websocket = null;
    let port=8080;

    //判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://"+window.location.host+url);
    } else {
        alert('Not support websocket')
    }

    window.onbeforeunload = function()
    {
        console.log(url+":close");
        websocket.close();
    }

    websocket.onerror=function()
    {
        alert(url+":socket出错");
    }

    websocket.onopen=function()
    {
        console.log("open:"+url);
    }

    return websocket;
}

/**
 * 移除url尾部的左斜杠
 * @param {String} url 
 * @returns 
 */
function removeLFTail(url)
{
    return url.replace(/([\w\W]+)\/$/,"$1");
}

function limitInputToPositiveInt(input)
{
    input.value=1;
    input.onkeyup=function()
    {
        this.value=this.value.replace(/[^0-9]/g,'');
    }

    input.onafterpaste=function()
    {
        this.value=this.value.replace(/[^0-9]/g,'');
    }
}
