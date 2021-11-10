function listenToServer(userName)
{
    let websocket=createSocket("/socket/payConfirmed/"+userName);

    websocket.onmessage=function(event)
    {
        if(event.data=="payConfirmed")
        {
            let pre="http://"+window.location.host;
            let tmp=pre+window.location.pathname;
            tmp=removeLFTail(tmp);
            window.location.href=tmp+"/../paySuccess";
        }
    }
}