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

$('.data-handler-button').on('click', function (ev) {ev.preventDefault();
    var data = this.href.split('?')[1].split("&");
    var action = data[0].split('=')[1];
    var id = data[1].split('=')[1];
    var removeAll = false;
    if (data.length > 2){
        removeAll = data[2].split('=')[1];
    }

    $.ajax({
        type: "GET",
        url: "/shopping-cart",
        data: JSON.stringify({
                action: action,
                id: id,
                removeAll: removeAll
            }),
        dataType : 'json',
        success: function () {
            alert('sikerült!');
        }
        }
    )
});

console.log( "ready!" );