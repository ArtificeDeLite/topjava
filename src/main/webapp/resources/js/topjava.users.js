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
        successNoty(enabled ? "User enabled" : "User disabled");
        $('#' + id).css('color', enabled ? "black" : "#a8a8a8");
    });
}