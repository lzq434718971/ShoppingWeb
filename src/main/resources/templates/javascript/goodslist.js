//向后台请求一页商品数据
//根据后台返回的数据生成list
function showList(listjson)
{
  let outerList=document.getElementById("goodslist");
  let list=JSON.parse(listjson);
  for(let i=0;i<list.length;i++)
  {
      let entry=document.createElement("li");
      let img=document.createElement("img");
      let name=document.createElement("p");
      let price=document.createElement("label");
      let stock=document.createElement("label");
      img.src="http://47.106.15.81:8080/img/public/goodsImg/"+list[i].name+".png";
      name.innerHTML=list[i].name;
      price.innerHTML="价格："+list[i].price;
      stock.innerHTML="库存:"+list[i].stock;
      entry.appendChild(img);
      entry.appendChild(name);
      entry.appendChild(price);
      entry.appendChild(stock);
      outerList.appendChild(entry);

      entry.style="cursor:pointer";
      entry.onclick=function()
      {
        let url="http://"+window.location.host+"/goodsDetail/"+list[i].name;
        console.log(url);
        window.location.href=url;
      }
  }
}

function setSearch()
{
  let searchButton=document.getElementById("searchButton");
  let searchBar=document.getElementById("searchBar");
  let sortParam=document.getElementById("sortParam");
  let sortWay=document.getElementById("sortWay");
  let params=getAllUrlParam();
  if(typeof params.hasOwnProperty("keyword"))
  {
    searchBar.value="";
  }
  else
  {
    searchBar.value=params["keyword"];
  }
  if(typeof params.hasOwnProperty(sortParam))
  {
    sortParam.value="name";
  }
  else
  {
    sortParam.value=params["sortParam"];
  }
  if(typeof params.hasOwnProperty("sortWay"))
  {
    sortWay.value="false"
  }
  else
  {
    sortWay.value=params["sortWay"];
  }
  searchButton.onclick=function()
  {
    let url="http://"+window.location.host+window.location.pathname;
    params["keyword"]=searchBar.value;
    params["sortParam"]=sortParam.value;
    params["sortWay"]=sortWay.value;
    url=removeLFTail(url);
    url+=mapToGetParam(params);
    window.location.href=url;
  }
}
