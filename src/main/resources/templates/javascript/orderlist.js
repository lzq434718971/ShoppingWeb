function showList(listjson)
{
  let outerList=document.getElementById("orderlist");
  let list=JSON.parse(listjson);
  for(let i=0;i<list.length;i++)
  {
    let entry=document.createElement("tr");
    let orderId=document.createElement("td");
    let goodsName=document.createElement("td");
    let count=document.createElement("td");
    let cbsurround=document.createElement("td");
    let checkbox=document.createElement("input");
    orderId.innerHTML=list[i].orderId;
    goodsName.innerHTML=list[i].goodsName;
    goodsName.onclick=function()
    {
      let url="http://"+window.location.host+"/goodsDetail/"+list[i].goodsName;
      console.log(url);
      window.location.href=url;
    }
    goodsName.className="goodsLink";
    count.innerHTML=list[i].count;
    checkbox.type="checkbox";
    checkbox.name="orderselection";
    checkbox.value=orderId.innerHTML;
    cbsurround.appendChild(checkbox);
    entry.appendChild(cbsurround);
    entry.appendChild(orderId);
    entry.appendChild(goodsName);
    entry.appendChild(count);
    outerList.appendChild(entry);
  }
}
//获取所有订单号
function getOrderIdList()
{
  let checkedOrder=document.getElementsByName("orderselection");
  let list=new Array();
  for(i=0;i<checkedOrder.length;i++)
  {
    if(checkedOrder[i].checked)
    {
      list.push(checkedOrder[i].value);
    }
  }
  return list;
}
//支付订单按钮动作
function onPayOrder(event)
{
  let button=document.getElementById(event.srcElement.id);
  button.disabled=true;
  //获取所有订单号(checkbox)
  let list=getOrderIdList();
  let url="http://"+window.location.host+window.location.pathname;
  url=url.replace(/([\w\W]+)\/$/,"$1");
  url+="/payQRCode";
  let tempForm = document.createElement("form");
  tempForm.method = "post";
  tempForm.action=url;
  let hideInput=document.createElement("input");
  hideInput.type = "hidden";
  hideInput.name = "orderIdList";
  hideInput.value=list;
  tempForm.appendChild(hideInput);
  //重定向到模拟支付页面
  document.body.appendChild(tempForm);
  tempForm.submit()
  document.body.removeChild(tempForm);
}
//删除订单按钮动作
function onDeleteOrder(event)
{
  let button=document.getElementById(event.srcElement.id);
  button.disabled=true;
  let url="http://"+window.location.host+window.location.pathname;
  url=url.replace(/([\w\W]+)\/$/,"$1");
  url+="/cancelOrder";
  let data=new FormData();
  let list=getOrderIdList();
  data.append("orderIdList",list);
  let httpRequest = new XMLHttpRequest();
  httpRequest.open('POST',url,true);
  httpRequest.send(data);
}

//配置订单操作的两个按钮
//listjson是当前页面中的订单信息
function setPayAndCancelButton(listjson)
{
  let cancelButton=document.getElementById("cancelbutton");
  cancelButton.onclick=onDeleteOrder;
  let payButton=document.getElementById("paybutton");
  payButton.onclick=onPayOrder;
}

function listenToServer(userName)
{
  let websocket=createSocket("/socket/cancelOrder/"+userName);

  websocket.onmessage=function(event)
  {
    if(event.data=="cancelOrder")
    {
      location.reload();
    }
  }
}
