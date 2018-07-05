$username = "$prim"
$password = "JeaZWcxuvcXrS2K1G7eXgFkEx7SDlHtCBbZc25KRr9qXzxFMnktkWd9fhtuM"
$filePath = "D:\Users\vpalo\IdeaProjects\prim\build\libs\ROOT.war"
$apiUrl = "https://prim.scm.azurewebsites.net/api/wardeploy"
$base64AuthInfo = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes(("{0}:{1}" -f $username, $password)))
Invoke-RestMethod -Uri $apiUrl -Headers @{Authorization=("Basic {0}" -f $base64AuthInfo)} -Method POST -InFile $filePath -ContentType "multipart/form-data"