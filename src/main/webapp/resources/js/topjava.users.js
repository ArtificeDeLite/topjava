// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});

function updateTable() {
    $.get(context.ajaxUrl, drawTable);
}

function changeEnabled(ctx, enabled) {
    let id = $(ctx).closest('tr').attr('id');

    $.ajax({
        type: "POST",
        url: context.ajaxUrl + id,
        data: {"enabled": enabled}
    }).done(function () {
        if (enabled) {
            successNoty("User enabled");
            $('#' + id).css('color', "black");
        } else {
            successNoty("User diasabled");
            $('#' + id).css('color', "#a8a8a8");
        }
    });
}