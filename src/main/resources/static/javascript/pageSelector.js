function setPageSelector()
{
  let previousButton=document.getElementById("previous");
  let jumpButton=document.getElementById("jumpto");
  let nextButton=document.getElementById("next");
  let pagebox=document.getElementById("pagebox");
  let link="window.location.href=\"http://"+window.location.host+window.location.pathname;
  link=removeLFTail(link);
  let page=getUrlParam("page");
  if(page==null)
  {
    page=0;
  }
  if(page==0)
  {
    previousButton.style="display:none";
  }
  let params=getAllUrlParam();
  let paramstr;
  params["page"]=parseInt(page)-1;
  paramstr=mapToGetParam(params);
  previousButton.onclick=Function(link+paramstr+"\";");
  params["page"]=parseInt(page)+1;
  paramstr=mapToGetParam(params);
  nextButton.onclick=Function(link+paramstr+"\";");
  jumpButton.onclick=jumptopage;
  limitInputToPositiveInt(pagebox);
}
function jumptopage()
{
  let link="http://"+window.location.host+window.location.pathname;
  link=removeLFTail(link);
  link += "?page=";
  let pagebox=document.getElementById("pagebox");
  let page=pagebox.value;
  if(page==""||page<1||isNaN(parseInt(page)))
  {
    page=1;
  }
  window.location.href=link+(parseInt(page)-1);
}
