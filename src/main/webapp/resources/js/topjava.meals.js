// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
            datatableApi: $("#datatable").DataTable({
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
                        "desc"
                    ]
                ]
            })
        }
    );
});

function updateTable() {
    filterTable(startDate.value, endDate.value, startTime.value, endTime.value);
}

function filterTable(startDate, endDate, startTime, endTime) {
    $.get(
        context.ajaxUrl + "filter",
        {
            "startDate": startDate,
            "endDate": endDate,
            "startTime": startTime,
            "endTime": endTime
        },

        function (data) {
            context.datatableApi.clear().rows.add(data).draw();
        });
}

function resetFilter(startDate, endDate, startTime, endTime) {
    startDate.value = "";
    endDate.value = "";
    startTime.value = "";
    endTime.value = "";
    filterTable(startDate.value, endDate.value, startTime.value, endTime.value);
}