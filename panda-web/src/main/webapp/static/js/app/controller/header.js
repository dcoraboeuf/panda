define(['jquery'], function ($) {

    $('#header-signin').click(function () {
        'login?callbackUrl={0}'.format(encodeURIComponent(location.href)).goto();
    });

});