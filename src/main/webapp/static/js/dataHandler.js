function menuHandling() {
    $('#menuHandler').on('click', function() {
        $('#pageMenu').addClass('menu-opened');
    });
    $('.menu-overlay').on('click', function() {
        $('#pageMenu').removeClass('menu-opened');
    });
}

menuHandling();

$('#shoppingCartHandler').on('click', function(e) {
    $('.shopping-cart-content').toggleClass('opened');
});

function dataHandler(ev) {ev.preventDefault();
    let data = this.href.split('?')[1].split("&");
    let action = data[0].split('=')[1];
    let id = data[1].split('=')[1];
    let removeAll = false;
    if (data.length > 2){
        removeAll = true;
    }
    $.ajax({
        type: "POST",
        url: "/shopping-cart",
        data: JSON.stringify({
            action: action,
            id: id,
            removeAll: removeAll
        }),
        dataType : 'json',
        success: function (data) {
            let $Quantity = $('.quantity-icon');
            let $TotalPrice = $('.cart-price-num');
            let totalPrice = parseFloat($('.shopping-cart .cart-price-num').text());
            let changePrice = parseFloat(data.price);
            let $itemQuantity = $('#product-' + data.id + ' .item-quantity'),
                $shoppingCartList = $('.shopping-cart-list');
            let quantity = parseInt($('.shopping-cart #product-' + data.id + ' .item-quantity').text());
            if (data.action === 'add') {
                $Quantity.text(parseInt($Quantity.text().split(" ")[0]) + 1);
                $TotalPrice.each(function() {
                    $(this).text(Math.round(totalPrice + changePrice));
                });
                if ($('#product-'+ data.id + '.cart-item').length === 0) {
                    $shoppingCartList.append(
                        `<li class="cart-item" id="product-${id}">
                            <div class="shopping-cart-item-thumb" style="background-image: url(/static/img/product_${data.id}.png);"></div>
                            <div class="shopping-cart-item-details">
                            <h5 class="item-name" >${data.name}"</h5>
                            <small class="item-price" >${data.price} USD"</small>
                            </div>
                            <div class="shopping-cart-item-quantity">
                            <a href="/shopping-cart?action=remove&id=${data.id}" data-id=""class="minus-button data-handler-button data-handler-button">-</a>
                            <span class="item-quantity">1</span>
                            <a href="/shopping-cart?action=add&id=${data.id}" class="add-button data-handler-button data-handler-button">+</a>
                            </div>
                            <a class="fa fa-times shopping-cart-remove-item data-handler-button" href="/shopping-cart?action=remove&id=${data.id}&all=true"></a>
                        </li>`)
                    $shoppingCartList.find('#product-' + data.id + ' .data-handler-button').on('click', dataHandler)
                } else {
                    $itemQuantity.each(function() {
                        $(this).text(quantity + 1);
                    });
                }
            } else if (data.action === 'remove') {
                if (!data.removeAll) {
                    $Quantity.text(parseInt($Quantity.text()) - 1);
                    $TotalPrice.each(function() {
                        $(this).text(Math.round(totalPrice - changePrice));
                    });
                    if (quantity === 1) {
                        $('#product-' + data.id + '.cart-item').remove();
                    }
                    $itemQuantity.each(function() {
                        $(this).text(quantity - 1);
                    });
                } else {
                    $Quantity.text(parseInt($Quantity.text()) - quantity);
                    $TotalPrice.each(function() {
                        $(this).text(Math.round(totalPrice - (changePrice * quantity)));
                    });
                    $itemQuantity.text(0);
                    $('#product-' + data.id + '.cart-item').remove();
                }
            }
        }
    })
}

$('.data-handler-button').on('click',dataHandler);

$(document).ready(function () {
    $("#register").click(function (event) {
        $("#message").removeClass("alert-danger");
        $("#message").text("");
        let password = $("#password").val();
        let password_confirm = $("#password-confirm").val();
        if (!(password === password_confirm)) {
            event.preventDefault();
            $("#message").toggleClass("alert-danger");
            $("#message").text("Passwords don't match!")
        } else {
            event.preventDefault();
            let validationUrl = "/check-username";
            let username = $("#username").val();
            let email = $("#email").val();
            $.post(validationUrl, JSON.stringify({"username": username, "email": email}), function (response) {
                if (response === "ok") {
                    console.log(response);
                    $("#registerForm")[0].submit();
                } else if (response === "both") {
                    $("#message").toggleClass("alert-danger");
                    $("#message").text("Username and email is already taken")
                } else if (response === "username") {
                    $("#message").toggleClass("alert-danger");
                    $("#message").text("Username is already taken")
                } else if (response === "email") {
                    $("#message").toggleClass("alert-danger");
                    $("#message").text("Email address is already taken")
                }
            })
        }
    })
});