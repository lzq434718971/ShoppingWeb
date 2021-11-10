function setCountSetter()
{
    let addCount=document.getElementById("addCount");
    let subCount=document.getElementById("subCount");
    addCount.onclick=function(){onChangeCount(1)};
    subCount.onclick=function(){onChangeCount(-1)}
}
function onChangeCount(offerset)
{
    let countBox=document.getElementById("countBox");
    countBox.value=parseInt(countBox.value)+offerset;
    if(parseInt(countBox.value)<1||countBox.value=="NaN")
    {
        countBox.value=1;
    }
}

function onAddToCart()
{
    let button=document.getElementById("addToCart");
    button.disabled=true;
    let count=document.getElementById("countBox").value;
    let stock=document.getElementById("stock").innerHTML;
    if(count>parseInt(stock))
    {
        alert("没有这么多的库存!");
    }
    let url="http://"+window.location.host+window.location.pathname;
    url=url.replace(/([\w\W]+)\/$/,"$1");
    let data=new FormData();
    data.append("count",count);
    let httpRequest = new XMLHttpRequest();
    httpRequest.open('POST',url,true);
    httpRequest.send(data);
}

function listenToServer(userName)
{
    let websocket=createSocket("/socket/addToCart/"+userName);

    console.log(userName+" socket");

    websocket.onmessage=function(event)
    {
        if(event.data=="addToCart")
        {
            let tmp="http://"+window.location.host+window.location.pathname;
            tmp=removeLFTail(tmp);
            location.reload();
        }
    }
}