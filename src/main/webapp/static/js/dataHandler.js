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

$('.data-handler-button').on('click', function (ev) {ev.preventDefault();}
    $.ajax({
            url: "/shoppingcart",
            success: function () {

            }
        }

    )
)

console.log( "ready!" );