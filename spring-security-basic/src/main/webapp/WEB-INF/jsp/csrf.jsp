<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        <title>CSRF Example</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    </head>
    <body>
        <h1>Welcome!</h1>
        
        <%--<p>Click <a href="transfer?accountNo=123456&amount=100">here</a> to see kittens.</p>--%>
        <%--<img src="transfer?accountNo=123456&amount=100">--%>

        <button onclick="send()">BUT</button>

    <script>
        function send() {
            $.ajax({
                type: "POST",
                data: '{ "accountNo": "1234", "amount": "100"}',
                url: "transfer",
                contentType: 'application/json'
            });
            }

    </script>


    </body>
</html>