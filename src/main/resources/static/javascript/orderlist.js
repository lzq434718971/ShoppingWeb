function showList(listjson)
{
  let outerList=document.getElementById("orderlist");
  let list=JSON.parse(listjson);
  for(let i=0;i<list.length;i++)
  {
    let entry=document.createElement("li");
    let orderId=document.createElement("p");
    let goodsName=document.createElement("p");
    let count=document.createElement("p");
    let checkbox=document.createElement("input");
    orderId.innerHTML=list[i].orderId;
    goodsName.innerHTML=list[i].goodsName;
    count.innerHTML=list[i].count;
    checkbox.type="checkbox";
    checkbox.name="orderselection";
    checkbox.value=orderId.innerHTML;
    entry.appendChild(checkbox);
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
function onPayOrder(listjson)
{
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
function onDeleteOrder()
{
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
