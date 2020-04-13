var mealAjaxUrl = "ajax/profile/meals/";

$.ajaxSetup({
    converters: {
        "text json": function (data) {
            let result = JSON.parse(data);
            if (Array.isArray(result)) {
                result.forEach(function (value) {
                    if (value.hasOwnProperty("dateTime")) {
                        value["dateTime"] = value["dateTime"].replace('T', ' ').substring(0, 16);
                    }
                });
            } else {
                if (result.hasOwnProperty("dateTime")) {
                    result["dateTime"] = result["dateTime"].replace('T', ' ').substring(0, 16);
                }
            }
            return result;
        }
    }
});

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (data.excess) {
                    $(row).attr("data-mealExcess", true);
                }
            }
        }),
        updateTable: updateFilteredTable
    });
});