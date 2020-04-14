var mealAjaxUrl = "ajax/profile/meals/";

$.ajaxSetup({
    converters: {
        "text json": function (data) {
            let result = JSON.parse(data);
            $(result).each(function () {
                this.dateTime = this.dateTime.replace('T', ' ').substring(0, 16);
            });
            return result;
        }
    }
});

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
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
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });
});

$.datetimepicker.setLocale(navigator.language);

$("#dateTime").datetimepicker({
    format: 'Y-m-d H:i'
});


$('#startDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    onShow:function( ct ){
        this.setOptions({
            maxDate:jQuery('#endDate').val()?jQuery('#endDate').val():false
        })
    }
});

$('#endDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    onShow:function( ct ){
        this.setOptions({
            minDate:jQuery('#startDate').val()?jQuery('#startDate').val():false
        })
    }
});

$('#startTime').datetimepicker({
    datepicker: false,
    format: 'H:i',
    onShow:function( ct ){
        this.setOptions({
            maxTime:jQuery('#endTime').val()?jQuery('#endTime').val():false
        })
    }
});

$('#endTime').datetimepicker({
    datepicker: false,
    format: 'H:i',
    onShow:function( ct ){
        this.setOptions({
            minTime:jQuery('#startTime').val()?jQuery('#startTime').val():false
        })
    }
});