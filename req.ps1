cd C:\Users\86158\eclipse-workspace\ShoppingWeb
git commit -am "newbuild"
git push -u origin master
$user = 'lzq434718971' 
$pass = '765346' 
$pair = "$($user):$($pass)" 
$encodedCreds = [System.Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes($pair)) 
$basicAuthValue = "Basic $encodedCreds" 
$Headers = @{ Authorization = $basicAuthValue }
Invoke-WebRequest -Uri 'http://47.106.15.81:8081/job/webshop/build?token=lzq' -Headers $Headers