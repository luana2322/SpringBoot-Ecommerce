$('document').ready(function () {
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (category, status) {
            $('#nameupdate').val(category.name);
        });
        $('#editModal').modal();
    });
});