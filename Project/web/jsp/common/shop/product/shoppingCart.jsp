<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div id="shoppingCart">
    <div id="cart" onclick="toggleShoppingProducts()">
        <span>购物车</span>
        <span id="productSum"></span>
    </div>


    <div id="cartProducts">
        <div id="viewAll" >
            <a href="orderProductList">去购物车结算</a>
        </div>
        <ul id="allCartProducts">
        </ul>
    </div>
</div>

</body>
</html>